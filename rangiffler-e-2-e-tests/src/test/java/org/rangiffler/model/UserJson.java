package org.rangiffler.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserJson {

  @JsonProperty("id")
  private UUID id;

  @JsonProperty("username")
  private String username;

  private transient String password;

  @JsonProperty("firstName")
  private String firstName;

  @JsonProperty("lastName")
  private String lastLame;

  @JsonProperty("avatar")
  private String avatar;

  @JsonProperty("friendStatus")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private FriendStatus friendStatus;

  public UserJson() {
  }

  private transient List<PhotoJson> photos = new ArrayList<>();

  public List<PhotoJson> getPhotos() {
    return photos;
  }

  public void setPhotos(List<PhotoJson> photos) {
    this.photos = photos;
  }
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastLame() {
    return lastLame;
  }

  public void setLastLame(String lastLame) {
    this.lastLame = lastLame;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public FriendStatus getFriendStatus() {
    return friendStatus;
  }

  public void setFriendStatus(FriendStatus friendStatus) {
    this.friendStatus = friendStatus;
  }
}

