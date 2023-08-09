package org.rangiffler.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rangiffler.db.entity.userdata.UserDataEntity;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserJson {

  @JsonProperty("id")
  private UUID id;

  @JsonProperty("username")
  private String username;

  private transient String password;

  @JsonProperty("firstName")
  private String firstName;

  @JsonProperty("lastName")
  private String lastName;

  @JsonProperty("avatar")
  private String avatar;

  @JsonProperty("friendStatus")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private FriendStatus friendStatus;

  private transient List<PhotoJson> photos = new ArrayList<>();
  private transient List<UserJson> friends = new ArrayList<>();
  private transient List<UserJson> incomeInvitations = new ArrayList<>();

  public List<UserJson> getIncomeInvitations() {
    return incomeInvitations;
  }
  public void setIncomeInvitations(List<UserJson> incomeInvitations) {
    this.incomeInvitations = incomeInvitations;
  }

  public List<UserJson> getFriends() {
    return friends;
  }

  public void setFriends(List<UserJson> friends) {
    this.friends = friends;
  }

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

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastLame) {
    this.lastName = lastLame;
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

  public static UserJson fromEntity(UserDataEntity entity) {
    UserJson usr = new UserJson();
    byte[] avatar = entity.getAvatar();
    usr.setId(entity.getId());
    usr.setUsername(entity.getUsername());
    usr.setFirstName(entity.getFirstname());
    usr.setLastName(entity.getLastname());
    usr.setAvatar(avatar != null && avatar.length > 0 ? new String(entity.getAvatar(), StandardCharsets.UTF_8) : null);
    return usr;
  }

  public static UserJson fromEntity(UserDataEntity entity, FriendStatus friendStatus) {
    UserJson userJson = fromEntity(entity);
    userJson.setFriendStatus(friendStatus);
    return userJson;
  }

  @Override
  public String toString() {
    return "UserJson{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastLame='" + lastName + '\'' +
            ", avatar='" + avatar + '\'' +
            ", friendStatus=" + friendStatus +
            ", photos=" + photos +
            ", friends=" + friends +
            ", incomeInvitations=" + incomeInvitations +
            '}';
  }
}

