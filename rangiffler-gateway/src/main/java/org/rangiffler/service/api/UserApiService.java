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
import java.util.stream.Collectors;

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

  public List<UserJson> getFriends() {
    return allUsers.stream().filter(user -> user.getFriendStatus().equals(FriendStatus.FRIEND))
        .collect(Collectors.toList());
  }

  public UserJson sendInvitation(UserJson user) {
    UserJson userJson = allUsers.stream().filter(u -> u.getId().equals(user.getId())).findFirst()
        .orElseThrow();
    userJson.setFriendStatus(FriendStatus.INVITATION_SENT);
    return userJson;
  }

  public UserJson acceptInvitation(UserJson friend) {
    UserJson userJson = allUsers.stream().filter(u -> u.getId().equals(friend.getId())).findFirst()
        .orElseThrow();
    userJson.setFriendStatus(FriendStatus.FRIEND);
    return userJson;
  }

  public UserJson declineInvitation(UserJson friend) {
    UserJson userJson = allUsers.stream().filter(u -> u.getId().equals(friend.getId())).findFirst()
        .orElseThrow();
    userJson.setFriendStatus(FriendStatus.NOT_FRIEND);
    return userJson;
  }


  public UserJson removeUserFromFriends(UserJson friend) {
    UserJson userJson = allUsers.stream().filter(u -> u.getId().equals(friend.getId())).findFirst()
        .orElseThrow();
    userJson.setFriendStatus(FriendStatus.NOT_FRIEND);
    return userJson;
  }

  public UserJson updateCurrentUser(UserJson user) {
    return webClient.post()
            .uri(rangifflerUserdataBaseUri + "/updateUserInfo")
            .body(Mono.just(user), UserJson.class)
            .retrieve()
            .bodyToMono(UserJson.class)
            .block();
  }

  public List<UserJson> getInvitations() {
    return allUsers.stream()
        .filter(user -> user.getFriendStatus().equals(FriendStatus.INVITATION_RECEIVED))
        .collect(Collectors.toList());
  }
}
