package org.rangiffler.service.api;

import org.rangiffler.model.PhotoJson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
//@RequiredArgsConstructor
public class PhotoService {
    private final UserApiService userApiService;
    private final WebClient webClient;
    private final String rangifflerPhotoBaseUri;

    public PhotoService(UserApiService userApiService, WebClient webClient,
                        @Value("${rangiffler-photo.base-uri}") String rangifflerPhotoBaseUri) {
        this.userApiService = userApiService;
        this.webClient = webClient;
        this.rangifflerPhotoBaseUri = rangifflerPhotoBaseUri;
    }

    List<PhotoJson> mainUserPhotoList = new ArrayList<>();
    List<PhotoJson> allUsersPhotoList = new ArrayList<>();
    public PhotoJson addPhoto(String username, PhotoJson photo) {
        photo.setUsername(username);

        return webClient.post()
                .uri(rangifflerPhotoBaseUri + "/photos")
                .body(Mono.just(photo), PhotoJson.class)
                .retrieve()
                .bodyToMono(PhotoJson.class)
                .block();
    }

    public List<PhotoJson> getAllUserPhotos(String username) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        URI uri = UriComponentsBuilder.fromHttpUrl(rangifflerPhotoBaseUri + "/photos").queryParams(params).build().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PhotoJson>>() {
                })
                .block();
    }

    public PhotoJson editPhoto(PhotoJson photoJson) {
        PhotoJson photo = mainUserPhotoList.stream().filter(ph -> ph.getId().equals(photoJson.getId()))
                .findFirst().orElseThrow();
        photo.setDescription(photoJson.getDescription());
        photo.setCountryJson(photoJson.getCountryJson());
        return photo;
    }

    public List<PhotoJson> getAllFriendsPhotos() {
        return allUsersPhotoList;
    }

    public void deletePhoto(UUID photoId) {
        PhotoJson photoJson = mainUserPhotoList.stream().filter(ph -> ph.getId().equals(photoId))
                .findFirst().orElseThrow();
        mainUserPhotoList.remove(photoJson);
    }
}
