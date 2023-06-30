package org.rangiffler.service.api;

import jakarta.annotation.Nonnull;
import org.rangiffler.model.FriendStatus;
import org.rangiffler.model.UserJson;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class UserApiService {
    private final WebClient webClient;
    private final String rangifflerUserdataBaseUri;

    @Autowired
    public UserApiService(WebClient webClient,
                          @Value("${rangiffler-users.base-uri}") String rangifflerUserdataBaseUri) {
        this.webClient = webClient;
        this.rangifflerUserdataBaseUri = rangifflerUserdataBaseUri;
    }

    private final List<UserJson> allUsers = new ArrayList<>();

    public List<UserJson> getAllUsers(@Nonnull String username) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        URI uri = UriComponentsBuilder.fromHttpUrl(rangifflerUserdataBaseUri + "/allUsers").queryParams(params).build().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UserJson>>() {
                })
                .block();
    }

    public UserJson getCurrentUser(@Nonnull String username) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        URI uri = UriComponentsBuilder.fromHttpUrl(rangifflerUserdataBaseUri + "/currentUser").queryParams(params).build().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(UserJson.class)
                .block();
    }

    public List<UserJson> getFriends(@Nonnull String username) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        URI uri = UriComponentsBuilder.fromHttpUrl(rangifflerUserdataBaseUri + "/friends").queryParams(params).build().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UserJson>>() {
                })
                .block();
    }

    public UserJson sendInvitation(@Nonnull String username, UserJson user) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        URI uri = UriComponentsBuilder.fromHttpUrl(rangifflerUserdataBaseUri + "/addFriend")
                .queryParams(params).build().toUri();

        return webClient.post()
                .uri(uri)
                .body(Mono.just(user), UserJson.class)
                .retrieve()
                .bodyToMono(UserJson.class)
                .block();
    }

    public UserJson acceptInvitation(@Nonnull String username,
                                     @Nonnull UserJson invitation) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        URI uri = UriComponentsBuilder.fromHttpUrl(rangifflerUserdataBaseUri + "/acceptInvitation").queryParams(params).build().toUri();

        return webClient.post()
                .uri(uri)
                .body(Mono.just(invitation), UserJson.class)
                .retrieve()
                .bodyToMono(UserJson.class)
                .block();
    }

    // TODO: реализовать
    public UserJson declineInvitation(String username, UserJson invitation) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        URI uri = UriComponentsBuilder.fromHttpUrl(rangifflerUserdataBaseUri + "/declineInvitation").queryParams(params).build().toUri();

        return webClient.post()
                .uri(uri)
                .body(Mono.just(invitation), UserJson.class)
                .retrieve()
                .bodyToMono(UserJson.class)
                .block();
    }

    public UserJson removeUserFromFriends(String username, UserJson friend) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        params.add("friendUsername", friend.getUsername());
        URI uri = UriComponentsBuilder.fromHttpUrl(rangifflerUserdataBaseUri + "/removeFriend").queryParams(params).build().toUri();

        return webClient.delete()
                .uri(uri)
                .retrieve()
                .bodyToMono(UserJson.class)
                .block();
    }

    public UserJson updateCurrentUser(UserJson user) {
        return webClient.post()
                .uri(rangifflerUserdataBaseUri + "/updateUserInfo")
                .body(Mono.just(user), UserJson.class)
                .retrieve()
                .bodyToMono(UserJson.class)
                .block();
    }

    public List<UserJson> getInvitations(String username) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        URI uri = UriComponentsBuilder.fromHttpUrl(rangifflerUserdataBaseUri + "/invitations").queryParams(params).build().toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UserJson>>() {
                })
                .block();
    }

}
