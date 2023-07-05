package org.rangiffler.test.web.profile;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rangiffler.db.entity.user.UserEntity;
import org.rangiffler.jupiter.annotation.GenerateUserAuthAndApiLogin;
import org.rangiffler.jupiter.extension.GenerateUserAuthAndApiLoginExtension;
import org.rangiffler.page.MainPage;
import org.rangiffler.page.ProfilePage;
import org.rangiffler.test.web.BaseWebTest;

@DisplayName("Update profile")
@ExtendWith(GenerateUserAuthAndApiLoginExtension.class)
public class ProfileTest extends BaseWebTest {

    private MainPage mainPage = new MainPage();
    private ProfilePage profilePage = new ProfilePage();
    private static final String TEST_PWD = "123456";
    private static final String PROFILE_PHOTO_PATH = "src/test/resources/testdata/cat_1.jfif";

    @BeforeEach
    void setUp() {
        Allure.step("open page", () -> Selenide.open(CFG.getFrontUrl()));
    }

    @GenerateUserAuthAndApiLogin(password = TEST_PWD)
    @AllureId("501")
    @Test
    void updateUserProfile(UserEntity user) {
        String firstname = user.getUsername() + " firstname";
        String lastname = user.getUsername() + " lastname";

        profilePage = mainPage.checkThatPageLoaded()
                .clickEmptyPhotoProfileButton();
        mainPage = profilePage.checkThatPageLoaded()
                .setFirstName(firstname)
                .setLastName(lastname)
                .uploadPhoto(PROFILE_PHOTO_PATH)
                .save();
        profilePage = mainPage.checkThatPageLoaded()
                .clickProfileIcon(user.getUsername());

        profilePage.checkThatPageLoaded()
                .checkFirstname(firstname)
                .checkLastname(lastname);
    }

    @GenerateUserAuthAndApiLogin(password = TEST_PWD)
    @AllureId("502")
    @Test
    void updateUserProfileAndNotSaveChanges(UserEntity user) {
        profilePage = mainPage.checkThatPageLoaded()
                .clickEmptyPhotoProfileButton();
        mainPage = profilePage.checkThatPageLoaded()
                .setFirstName(user.getUsername() + " firstname")
                .setLastName(user.getUsername() + " lastname")
                .uploadPhoto(PROFILE_PHOTO_PATH)
                .close();
        profilePage = mainPage.checkThatPageLoaded()
                .clickEmptyPhotoProfileButton();

        profilePage.checkThatPageLoaded()
                .checkFirstname("")
                .checkLastname("");
    }

}
