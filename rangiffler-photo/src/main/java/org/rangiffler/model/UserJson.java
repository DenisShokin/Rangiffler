package org.rangiffler.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class UserJson {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("username")
    private String username;

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
}

