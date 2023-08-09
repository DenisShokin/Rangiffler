package org.rangiffler.test.web.friends;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.jupiter.annotation.Friend;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.jupiter.extension.ApiLoginExtension;
import org.rangiffler.jupiter.extension.GenerateUserExtension;
import org.rangiffler.model.UserJson;
import org.rangiffler.page.FriendsPage;
import org.rangiffler.page.component.HeaderComponent;
import org.rangiffler.test.web.BaseWebTest;

import static io.qameta.allure.Allure.step;

@DisplayName("[WEB] Header. Друзья")
@ExtendWith({GenerateUserExtension.class, ApiLoginExtension.class})
public class HeaderFriendsTest extends BaseWebTest {

    private HeaderComponent headerComponent = new HeaderComponent();

    @Test
    @ApiLogin(user = @GenerateUser
            (friends = @Friend)
    )
    @AllureId("1001")
    @Tag("WEB")
    @DisplayName("WEB: Пользователь должен видеть друзей в хедере")
    void checkFriendsInHeader(UserJson user) {
        final UserJson friend = user.getFriends().get(0);

        step(OPEN_MAIN_PAGE_STEP, () -> Selenide.open(CFG.getFrontUrl()));
        FriendsPage friendsPage = headerComponent
                .checkThatComponentDisplayed()
                .checkFriendsCount(1)
                .clickFriendsButton();
        friendsPage
                .checkThatPageLoaded()
                .checkTableContainsFriends(friend);
    }

    @Test
    @ApiLogin(user = @GenerateUser)
    @AllureId("1002")
    @Tag("WEB")
    @DisplayName("WEB: Пользователь должен видеть сообщение в хедере при отсутствии друзей")
    void peopleWithoutFriends() {
        step(OPEN_MAIN_PAGE_STEP, () -> Selenide.open(CFG.getFrontUrl()));

        FriendsPage friendsPage = headerComponent
                .checkThatComponentDisplayed()
                .checkFriendsCount(0)
                .clickFriendsButton();
        friendsPage.checkThatPageLoaded()
                .checkNoFriendsYetIsVisible();
    }

    @Test
    @ApiLogin(user = @GenerateUser
            (friends = @Friend)
    )
    @AllureId("1003")
    @Tag("WEB")
    @DisplayName("WEB: Пользователь должен иметь возможность удалить друга в хедере")
    void deleteFriend(UserJson user) {
        final UserJson friend = user.getFriends().get(0);

        step(OPEN_MAIN_PAGE_STEP, () -> Selenide.open(CFG.getFrontUrl()));
        FriendsPage friendsPage =
                headerComponent
                .checkThatComponentDisplayed()
                .checkFriendsCount(1)
                .clickFriendsButton();
        friendsPage
                .checkThatPageLoaded()
                .deleteFriends(friend);
        friendsPage = headerComponent
                .checkFriendsCount(0)
                .clickFriendsButton();
        friendsPage.checkThatPageLoaded()
                .checkNoFriendsYetIsVisible();
    }

}
