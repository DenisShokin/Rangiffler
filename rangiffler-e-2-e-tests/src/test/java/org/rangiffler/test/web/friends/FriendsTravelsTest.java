package org.rangiffler.test.web.friends;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
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

import static io.qameta.allure.Allure.step;

@DisplayName("Путешествия друзей (Friends Travels)")
public class FriendsTravelsTest extends BaseWebTest {

    private HeaderComponent headerComponent = new HeaderComponent();

    @Test
    @ApiLogin(user = @GenerateUser(
            friends = @Friend(photos = @GeneratePhoto))
    )
    @AllureId("1201")
    @Tag("WEB")
    @DisplayName("WEB: Пользователь должен видеть путешествия друзей")
    void checkFriendTravel(UserJson user) {
        final UserJson friend = user.getFriends().get(0);
        final PhotoJson friendsPhoto = friend.getPhotos().get(0);

        step("Открыть страницу", () -> Selenide.open(CFG.getFrontUrl()));
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
    @Tag("WEB")
    @DisplayName("WEB: Пользователь не видит список фото друзей если они не загружены")
    void checkFriendTravelWithoutPhotos() {
        step("Открыть страницу", () -> Selenide.open(CFG.getFrontUrl()));
        FriendsTravelsPage friendsTravelsPage = headerComponent
                .checkThatComponentDisplayed()
                .checkFriendsCount(1)
                .goToFriendsTravelsPage();
        friendsTravelsPage.checkThatPageLoaded()
                .getImagesList()
                .checkThatComponentIsHidden();
    }

}
