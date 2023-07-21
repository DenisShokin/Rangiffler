package org.rangiffler.test.web.profile;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.model.UserJson;
import org.rangiffler.page.ProfilePage;
import org.rangiffler.page.YourTravelsPage;
import org.rangiffler.test.web.BaseWebTest;

import static io.qameta.allure.Allure.step;

@DisplayName("Обновление профиля")
public class ProfileTest extends BaseWebTest {

    private YourTravelsPage yourTravelsPage = new YourTravelsPage();
    private ProfilePage profilePage = new ProfilePage();
    private static final String PROFILE_PHOTO_PATH = "testdata/cat_1.jfif";

    @BeforeEach
    void setUp() {
        step("Открыть страницу", () -> Selenide.open(CFG.getFrontUrl()));
    }

    @AllureId("501")
    @ApiLogin(user = @GenerateUser)
    @Tag("WEB")
    @DisplayName("WEB: Пользователь должен иметь возможность обновить данные своего профиля")
    @Test
    void updateUserProfile(UserJson user) {
        String firstname = user.getUsername() + " firstname";
        String lastname = user.getUsername() + " lastname";

        profilePage = yourTravelsPage
                .checkThatPageLoaded()
                .getHeader()
                .clickEmptyPhotoProfileButton();
        profilePage.checkThatPageLoaded()
                .setFirstName(firstname)
                .setLastName(lastname)
                .uploadPhoto(PROFILE_PHOTO_PATH)
                .save();
        profilePage = yourTravelsPage
                .checkThatPageLoaded()
                .getHeader()
                .clickProfileIcon(user.getUsername());

        profilePage.checkThatPageLoaded()
                .checkFirstname(firstname)
                .checkLastname(lastname);
    }

    @AllureId("502")
    @ApiLogin(user = @GenerateUser)
    @Test
    @Tag("WEB")
    @DisplayName("WEB: Обновление профиля не происходит без сохранения изменений")
    void updateUserProfileAndNotSaveChanges(UserJson user) {
        profilePage = yourTravelsPage
                .checkThatPageLoaded()
                .getHeader()
                .clickEmptyPhotoProfileButton();
        profilePage.checkThatPageLoaded()
                .setFirstName(user.getUsername() + " firstname")
                .setLastName(user.getUsername() + " lastname")
                .uploadPhoto(PROFILE_PHOTO_PATH)
                .close();
        profilePage = yourTravelsPage
                .checkThatPageLoaded()
                .getHeader()
                .clickEmptyPhotoProfileButton();

        profilePage.checkThatPageLoaded()
                .checkFirstname("")
                .checkLastname("");
    }

}
