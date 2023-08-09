package org.rangiffler.api;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.rangiffler.model.UserJson;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class UserdataRestClient extends BaseRestClient {

    public UserdataRestClient() {
        super(CFG.getUserdataUrl());
    }

    private final UserdataService userdataService = retrofit.create(UserdataService.class);

    public UserJson currentUser(String username) {
        try {
            return userdataService.currentUser(username).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public UserJson addFriend(String username, String friendUsername) {
        try {
            UserJson friendJson = new UserJson();
            friendJson.setUsername(friendUsername);
            return userdataService.addFriend(username, friendJson).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public UserJson acceptInvitation(String username, String inviteUsername) {
        try {
            UserJson friendJson = new UserJson();
            friendJson.setUsername(inviteUsername);
            return userdataService.acceptInvitation(username, friendJson).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Step("rangiffler-users. Получить всех пользователей")
    public @Nonnull List<UserJson> getAllUsers(String username) {
        try {
            return Objects.requireNonNull(userdataService.getAllUsers(username).execute().body());
        } catch (IOException e) {
            Assertions.fail("Can`t execute api call to rangiffler-users: " + CFG.getUserdataUrl() + " " + e.getMessage());
            return null;
        }
    }

}
