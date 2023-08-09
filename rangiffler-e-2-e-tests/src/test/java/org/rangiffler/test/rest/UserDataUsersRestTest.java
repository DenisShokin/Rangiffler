package org.rangiffler.test.rest;

import io.qameta.allure.AllureId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rangiffler.api.UserdataRestClient;
import org.rangiffler.db.dao.userdata.RangifflerUsersDataDAOHibernate;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.jupiter.extension.GenerateUserExtension;
import org.rangiffler.model.FriendStatus;
import org.rangiffler.model.UserJson;

import java.util.List;
import java.util.stream.Collectors;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("[REST][rangiffler-users]: Пользователи")
@ExtendWith(GenerateUserExtension.class)
public class UserDataUsersRestTest extends BaseRestTest {

    private final UserdataRestClient userdataRestClient = new UserdataRestClient();
    private final RangifflerUsersDataDAOHibernate dataDAOHibernate = new RangifflerUsersDataDAOHibernate();

    @AllureId("20001")
    @DisplayName("REST: При запросе allUsers возвращаются все пользователи из rangiffler-users кроме текущего")
    @Test
    @Tag("REST")
    @GenerateUser
    void apiShouldReturnAllUsersExactCurrentUser(UserJson currentUser) {
        List<UserJson> apiUsers = userdataRestClient.getAllUsers(currentUser.getUsername());

        List<UserJson> dbUsers = dataDAOHibernate.findByUsernameNot(currentUser.getUsername())
                .stream().map(u -> UserJson.fromEntity(u, FriendStatus.NOT_FRIEND)).collect(Collectors.toList());

        step("Проверить, что ответ /allUsers содержит всех юзеров, кроме текущего, из БД", () -> Assertions
                .assertThat(apiUsers)
                .containsExactlyInAnyOrderElementsOf(dbUsers));
    }

    @Test
    @DisplayName("REST: Список всех пользователей системы не должен быть пустым")
    @AllureId("200002")
    @Tag("REST")
    @GenerateUser
    void allUsersTest(UserJson user) {
        final List<UserJson> allUsersResponse = userdataRestClient.getAllUsers(user.getUsername());

        step("Проверить, что список всех юзеров не пустой", () ->
                assertFalse(allUsersResponse.isEmpty())
        );
    }

}
