package org.rangiffler.test.web.friends;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.jupiter.annotation.Friend;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.model.UserJson;
import org.rangiffler.page.FriendsPage;
import org.rangiffler.page.PeopleAroundPage;
import org.rangiffler.page.component.HeaderComponent;
import org.rangiffler.test.web.BaseWebTest;

import static io.qameta.allure.Allure.step;

@DisplayName("Люди вокруг (People Around)")
public class PeopleAroundTest extends BaseWebTest {

    private HeaderComponent headerComponent = new HeaderComponent();

    @Test
    @ApiLogin(user = @GenerateUser
            (friends = @Friend)
    )
    @AllureId("1101")
    @Tag("WEB")
    @DisplayName("WEB: Пользователь должен иметь возможность удалить друга с вкладки People Around")
    void deleteFriendFromPeopleAroundPage(UserJson user) {
        final UserJson friend = user.getFriends().get(0);

        step("Открыть страницу", () -> Selenide.open(CFG.getFrontUrl()));
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
    @Tag("WEB")
    @DisplayName("WEB: Пользователь должен иметь возможность принять приглашение в друзья с вкладки People Around")
    void acceptInvitationTest(UserJson user) {
        final UserJson incomeUser = user.getIncomeInvitations().get(0);

        step("Открыть страницу", () -> Selenide.open(CFG.getFrontUrl()));
        PeopleAroundPage peopleAroundPage = headerComponent
                .checkThatComponentDisplayed()
                .checkFriendsCount(0)
                .goToPeopleAroundPage();
        peopleAroundPage
                .checkThatPageLoaded()
                .acceptInvites(incomeUser);
        FriendsPage friendsPage = headerComponent
                .checkThatComponentDisplayed()
                .refresh()
                .checkFriendsCount(1)
                .clickFriendsButton();
        friendsPage.checkThatPageLoaded()
                .checkTableContainsFriends(incomeUser);
    }

    @Test
    @ApiLogin(user = @GenerateUser(incomeInvitations = @Friend))
    @AllureId("906")
    @Tag("WEB")
    @DisplayName("WEB: Пользователь должен иметь возможность отклонить приглашение в друзья с вкладки People Around")
    void declineInvitationTest(UserJson user) {
        final UserJson incomeUser = user.getIncomeInvitations().get(0);

        step("Открыть страницу", () -> Selenide.open(CFG.getFrontUrl()));
        PeopleAroundPage peopleAroundPage = headerComponent
                .checkThatComponentDisplayed()
                .checkFriendsCount(0)
                .goToPeopleAroundPage();
        peopleAroundPage
                .checkThatPageLoaded()
                .declineInvites(incomeUser);
        FriendsPage friendsPage = headerComponent
                .checkThatComponentDisplayed()
                .refresh()
                .checkFriendsCount(0)
                .clickFriendsButton();
        friendsPage.checkThatPageLoaded()
                .checkNoFriendsYetIsVisible();
    }


}
