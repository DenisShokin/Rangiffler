package org.rangiffler.jupiter.extension;

import com.google.common.base.Stopwatch;
import org.rangiffler.api.AuthRestClient;
import org.rangiffler.api.PhotoRestClient;
import org.rangiffler.api.UserdataRestClient;
import org.rangiffler.jupiter.annotation.GeneratePhoto;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.model.PhotoJson;
import org.rangiffler.model.UserJson;
import org.rangiffler.utils.DataUtils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;

public class GenerateUserService {

    private static final AuthRestClient authClient = new AuthRestClient();
    private static final UserdataRestClient userdataClient = new UserdataRestClient();
    private static final PhotoRestClient photoClient = new PhotoRestClient();
    private static final GeneratePhotoService generatePhotoService = new GeneratePhotoService();


    public UserJson generateUser(@Nonnull GenerateUser annotation) {
        UserJson user = createRandomUser();

        addPhotoIfPresent(user, annotation.photos());
        return user;
    }

    private UserJson createRandomUser() {
        final String username = DataUtils.generateRandomUsername();
        final String password = DataUtils.generateRandomPassword();
        authClient.register(username, password);
        UserJson user = waitWhileUserToBeConsumed(username, 5000L);
        user.setPassword(password);
        return user;
    }

    private void addPhotoIfPresent(UserJson targetUser, GeneratePhoto[] photos) {
        if (isNotEmpty(photos)) {
            List<PhotoJson> photoList = new ArrayList<>();
            for (GeneratePhoto photo : photos) {
                PhotoJson photoJson = generatePhotoService.generatePhoto(photo, targetUser.getUsername());
                photoClient.addPhoto(photoJson);
                photoList.add(photoJson);
            }
            targetUser.setPhotos(photoList);
        }
    }


    private UserJson waitWhileUserToBeConsumed(String username, long maxWaitTime) {
        Stopwatch sw = Stopwatch.createStarted();
        while (sw.elapsed(TimeUnit.MILLISECONDS) < maxWaitTime) {
            UserJson userJson = userdataClient.currentUser(username);
            if (userJson != null) {
                return userJson;
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        throw new IllegalStateException("Can`t obtain user from rangiffler-userdata");
    }

}
