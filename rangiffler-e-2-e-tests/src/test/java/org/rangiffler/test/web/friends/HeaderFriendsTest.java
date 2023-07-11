package org.rangiffler.test.web.friends;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.jupiter.annotation.Friend;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.model.UserJson;
import org.rangiffler.page.FriendsPage;
import org.rangiffler.page.component.HeaderComponent;
import org.rangiffler.test.web.BaseWebTest;

@DisplayName("Header. Friends")
public class HeaderFriendsTest extends BaseWebTest {

    private HeaderComponent headerComponent = new HeaderComponent();

    @Test
    @ApiLogin(user = @GenerateUser
            (friends = @Friend)
    )
    @AllureId("1001")
    void checkFriendsInHeader(UserJson user) {
        final UserJson friend = user.getFriends().get(0);

        Allure.step("open page", () -> Selenide.open(CFG.getFrontUrl()));
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
    void peopleWithoutFriends() {
        Allure.step("open page", () -> Selenide.open(CFG.getFrontUrl()));

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
    void deleteFriend(UserJson user) {
        final UserJson friend = user.getFriends().get(0);

        Allure.step("open page", () -> Selenide.open(CFG.getFrontUrl()));
        FriendsPage friendsPage = headerComponent
                .checkThatComponentDisplayed()
                .checkFriendsCount(1)
                .clickFriendsButton();
        friendsPage
                .checkThatPageLoaded()
                .deleteFriends(friend);
        headerComponent
                .checkFriendsCount(0);
    }

}
