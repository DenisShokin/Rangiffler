package org.rangiffler.test.web.friends;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.jupiter.annotation.Friend;
import org.rangiffler.jupiter.annotation.GeneratePhoto;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.model.PhotoJson;
import org.rangiffler.model.UserJson;
import org.rangiffler.page.FriendsTravelsPage;
import org.rangiffler.page.component.HeaderComponent;
import org.rangiffler.test.web.BaseWebTest;

@DisplayName("Friend travels")
public class FriendsTravelsTest extends BaseWebTest {

    private HeaderComponent headerComponent = new HeaderComponent();

    @Test
    @ApiLogin(user = @GenerateUser(
            friends = @Friend(photos = @GeneratePhoto))
    )
    @AllureId("1201")
    void checkFriendTravel(UserJson user) {
        final UserJson friend = user.getFriends().get(0);
        final PhotoJson friendsPhoto = friend.getPhotos().get(0);

        Allure.step("open page", () -> Selenide.open(CFG.getFrontUrl()));
        FriendsTravelsPage friendsTravelsPage = headerComponent
                .checkThatComponentDisplayed()
                .checkPhotosCount(0)
                .goToFriendsTravelsPage();
        friendsTravelsPage.checkThatPageLoaded()
                .getImagesList()
                .checkImagesListContainsPhotos(friendsPhoto);
    }

    @Test
    @ApiLogin(user = @GenerateUser(photos = @GeneratePhoto,
            friends = @Friend)
    )
    @AllureId("1202")
    void checkFriendTravelWithoutPhotos() {
        Allure.step("open page", () -> Selenide.open(CFG.getFrontUrl()));
        FriendsTravelsPage friendsTravelsPage = headerComponent
                .checkThatComponentDisplayed()
                .checkFriendsCount(1)
                .goToFriendsTravelsPage();
        friendsTravelsPage.checkThatPageLoaded()
                .getImagesList()
                .checkThatComponentIsHidden();
    }

}