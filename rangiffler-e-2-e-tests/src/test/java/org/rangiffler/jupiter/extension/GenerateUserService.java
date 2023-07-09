package org.rangiffler.jupiter.extension;

import com.google.common.base.Stopwatch;
import org.rangiffler.api.AuthRestClient;
import org.rangiffler.api.UserdataRestClient;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.model.UserJson;
import org.rangiffler.utils.DataUtils;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

public class GenerateUserService {

    private static final AuthRestClient authClient = new AuthRestClient();
    private static final UserdataRestClient userdataClient = new UserdataRestClient();


    public UserJson generateUser(@Nonnull GenerateUser annotation) {
        UserJson user = createRandomUser();
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
