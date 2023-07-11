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
import org.rangiffler.page.PeopleAroundPage;
import org.rangiffler.page.component.HeaderComponent;
import org.rangiffler.test.web.BaseWebTest;

@DisplayName("People around")
public class PeopleAroundTest extends BaseWebTest {

    private HeaderComponent headerComponent = new HeaderComponent();

    @Test
    @ApiLogin(user = @GenerateUser
            (friends = @Friend)
    )
    @AllureId("1101")
    void deleteFriendFromPeopleAroundPage(UserJson user) {
        final UserJson friend = user.getFriends().get(0);

        Allure.step("open page", () -> Selenide.open(CFG.getFrontUrl()));
        PeopleAroundPage peopleAroundPage = headerComponent
                .checkThatComponentDisplayed()
                .checkFriendsCount(1)
                .goToPeopleAroundPage();
        peopleAroundPage
                .checkThatPageLoaded()
                .deleteFriends(friend);
        headerComponent
                .refresh()
                .checkFriendsCount(0);
    }

    @Test
    @ApiLogin(user = @GenerateUser(incomeInvitations = @Friend))
    @AllureId("905")
    void acceptInvitationTest(UserJson user) {
        final UserJson incomeUser = user.getIncomeInvitations().get(0);

        Allure.step("open page", () -> Selenide.open(CFG.getFrontUrl()));
        PeopleAroundPage peopleAroundPage = headerComponent
                .checkThatComponentDisplayed()
                .checkFriendsCount(0)
                .goToPeopleAroundPage();
        peopleAroundPage
                .checkThatPageLoaded()
                .acceptInvites(incomeUser);
        headerComponent
                .checkThatComponentDisplayed()
                .refresh()
                .checkFriendsCount(1);
    }

    //test for decline invite
}
