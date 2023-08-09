package org.rangiffler.jupiter.extension;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.rangiffler.api.AuthRestClient;
import org.rangiffler.api.PhotoRestClient;
import org.rangiffler.api.UserdataRestClient;
import org.rangiffler.jupiter.annotation.Friend;
import org.rangiffler.jupiter.annotation.GeneratePhoto;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.model.PhotoJson;
import org.rangiffler.model.UserJson;
import org.rangiffler.utils.DataUtils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;

public class GenerateUserService {

    private static final AuthRestClient authClient = new AuthRestClient();
    private static final UserdataRestClient userdataClient = new UserdataRestClient();
    private static final PhotoRestClient photoClient = new PhotoRestClient();
    private static final GeneratePhotoService generatePhotoService = new GeneratePhotoService();

    public UserJson generateUser(@Nonnull GenerateUser annotation) {
        UserJson user = Allure.step("Создать тестового пользователя", this::createRandomUser);
        addPhotoIfPresent(user, annotation.photos());
        addFriendsIfPresent(user, annotation.friends());
        addIncomeInvitationsIfPresent(user, annotation.incomeInvitations());
        return user;
    }

    @Step
    @Attachment
    private UserJson createRandomUser() {
        final String username = DataUtils.generateRandomUsername();
        final String password = DataUtils.generateRandomPassword();
        authClient.register(username, password);
        UserJson user = userdataClient.currentUser(username);
        user.setPassword(password);
        return user;
    }

    private void addPhotoIfPresent(UserJson targetUser, GeneratePhoto[] photos) {
        if (isNotEmpty(photos)) {
            List<PhotoJson> photoList = new ArrayList<>();
            for (GeneratePhoto photo : photos) {
                PhotoJson photoJson = Allure.step("Добавить фото путешествия", () ->
                        generatePhotoService.generatePhoto(photo, targetUser.getUsername()));
                photoClient.addPhoto(photoJson);
                photoList.add(photoJson);
            }
            targetUser.setPhotos(photoList);
        }
    }

    private void addFriendsIfPresent(UserJson targetUser, Friend[] friends) {
        if (isNotEmpty(friends)) {
            List<UserJson> friendsList = new ArrayList<>();
            for (Friend friend : friends) {
                UserJson friendJson = Allure.step("Добавить друга пользователю", this::createRandomUser);
                userdataClient.addFriend(targetUser.getUsername(), friendJson.getUsername());
                userdataClient.acceptInvitation(friendJson.getUsername(), targetUser.getUsername());
                addPhotoIfPresent(friendJson, friend.photos());
                friendsList.add(friendJson);
            }
            targetUser.setFriends(friendsList);
        }
    }

    private void addIncomeInvitationsIfPresent(UserJson targetUser, Friend[] incomeInvitations) {
        if (isNotEmpty(incomeInvitations)) {
            for (int i = 0; i < incomeInvitations.length; i++) {
                UserJson friendJson = Allure.step("Добавить входящее предложение о дружбе", this::createRandomUser);
                userdataClient.addFriend(friendJson.getUsername(), targetUser.getUsername());
                targetUser.getIncomeInvitations().add(friendJson);
            }
        }
    }
}
