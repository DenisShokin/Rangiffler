package org.rangiffler.service;

import jakarta.annotation.Nonnull;
import org.rangiffler.model.UserJson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class UserService {

    private final WebClient webClient;
    private final String rangifflerUserDataBaseUri;

    public UserService(WebClient webClient, @Value("${rangiffler-users.base-uri}") String rangifflerUserdataBaseUri) {
        this.webClient = webClient;
        this.rangifflerUserDataBaseUri = rangifflerUserdataBaseUri;
    }

    public List<UserJson> getUserFriends(@Nonnull String currentUser) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", currentUser);
        URI uri = UriComponentsBuilder.fromHttpUrl(rangifflerUserDataBaseUri + "/friends").queryParams(params).build().toUri();
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UserJson>>() {
                })
                .block();
    }
}

