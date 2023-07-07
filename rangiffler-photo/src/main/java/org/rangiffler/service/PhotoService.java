package org.rangiffler.service;

import jakarta.annotation.Nonnull;
import org.rangiffler.data.CountryEntity;
import org.rangiffler.data.PhotoEntity;
import org.rangiffler.data.repository.PhotoRepository;
import org.rangiffler.model.PhotoJson;
import org.rangiffler.model.UserJson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final WebClient webClient;
    private final String rangifflerUserDataBaseUri;


    public PhotoService(PhotoRepository photoRepository,
                        WebClient webClient, @Value("${rangiffler-users.base-uri}") String rangifflerUserDataBaseUri) {
        this.photoRepository = photoRepository;
        this.webClient = webClient;
        this.rangifflerUserDataBaseUri = rangifflerUserDataBaseUri;
    }

    public List<PhotoJson> getAllUserPhotos(String username) {
        Map<UUID, PhotoJson> result = new HashMap<>();
        for (PhotoEntity photo : photoRepository.findAllByUsername(username)) {
            if (!result.containsKey(photo.getId())) {
                result.put(photo.getId(), PhotoJson.fromEntity(photo));
            }
        }
        return new ArrayList<>(result.values());
    }

    public PhotoJson addPhoto(PhotoJson photoJson) {
        if (photoJson.getPhoto().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Photo must not be empty!");
        }
        PhotoEntity photoEntity = new PhotoEntity();
        UUID id = photoJson.getId() == null ? UUID.randomUUID() : photoJson.getId();

        photoEntity.setId(id);
        photoEntity.setDescription(photoJson.getDescription());
        photoEntity.setUsername(photoJson.getUsername());
        photoEntity.setPhoto(photoJson.getPhoto().getBytes());

        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setPhotoId(id);
        countryEntity.setCode(photoJson.getCountryJson().getCode());
        countryEntity.setName(photoJson.getCountryJson().getName());
        countryEntity.setPhoto(photoEntity);
        photoEntity.setCountry(countryEntity);

        return PhotoJson.fromEntity(photoRepository.save(photoEntity));
    }

    public void deletePhoto(UUID photoId) {
        Optional<PhotoEntity> photoById = photoRepository.findById(photoId);
        if (photoById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find photo by given id: " + photoId);
        } else {
            photoRepository.deleteById(photoId);
        }
    }

    public PhotoJson editPhoto(@Nonnull PhotoJson photoJson) {
        Optional<PhotoEntity> photoById = photoRepository.findById(photoJson.getId());
        if (photoById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can`t find photo by given id: " + photoJson.getId());
        } else {
            PhotoEntity photo = photoById.get();
            if (photo.getId().equals(photoJson.getId()) &&
                    photo.getUsername().equals(photoJson.getUsername())) {
                photo.setDescription(photoJson.getDescription());

                CountryEntity country = photo.getCountry();
                country.setName(photoJson.getCountryJson().getName());
                country.setCode(photoJson.getCountryJson().getCode());
                photo.setCountry(country);
                PhotoEntity saved = photoRepository.save(photo);
                return PhotoJson.fromEntity(saved);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Photo with id = " + photoJson.getPhoto() + "already connect with other username");
            }
        }
    }

    public List<PhotoJson> getAllFriendsPhotos(String currentUser) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", currentUser);
        URI uri = UriComponentsBuilder.fromHttpUrl(rangifflerUserDataBaseUri + "/friends").queryParams(params).build().toUri();
        List<UserJson> friends = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UserJson>>() {
                })
                .block();
        List<String> friendsUsername = friends.stream().map(UserJson::getUsername).toList();
        Map<UUID, PhotoJson> result = new HashMap<>();
        for (String user : friendsUsername) {
            for (PhotoEntity photo : photoRepository.findAllByUsername(user)) {
                if (!result.containsKey(photo.getId())) {
                    result.put(photo.getId(), PhotoJson.fromEntity(photo));
                }
            }
        }
        return new ArrayList<>(result.values());
    }
}
