package org.rangiffler.test.web.profile;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rangiffler.jupiter.annotation.GenerateUserAuthAndApiLogin;
import org.rangiffler.jupiter.extension.GenerateUserAuthAndApiLoginExtension;
import org.rangiffler.page.MainPage;
import org.rangiffler.page.ProfilePage;
import org.rangiffler.test.web.BaseWebTest;

@DisplayName("Update profile error")
@ExtendWith(GenerateUserAuthAndApiLoginExtension.class)
public class ProfileErrorTest extends BaseWebTest {

    private MainPage mainPage = new MainPage();
    private ProfilePage profilePage = new ProfilePage();
    private static final String FIFTY_ONE_CHARACTERS = "123fsdfgsdfgsdfgsdfgsdfgsdfgsdfgsdfgwergrtehtrebgfa";
    private static final String TEST_PWD = "123456";

    @GenerateUserAuthAndApiLogin(password = TEST_PWD)
    @Test
    @AllureId("601")
    public void errorMessageShouldBeVisibleInCaseThatFirstnameMoreThan50Symbols() {
        Allure.step("open page", () -> Selenide.open(CFG.getFrontUrl()));

        profilePage = mainPage.checkThatPageLoaded()
                .clickEmptyPhotoProfileButton();
        profilePage.checkThatPageLoaded()
                .setFirstName(FIFTY_ONE_CHARACTERS)
                .checkFirstnameErrorMessage("Length of this field must be no longer than 50 characters");
    }

    @GenerateUserAuthAndApiLogin(password = TEST_PWD)
    @Test
    @AllureId("602")
    public void errorMessageShouldBeVisibleInCaseThatLastnameMoreThan50Symbols() {
        Allure.step("open page", () -> Selenide.open(CFG.getFrontUrl()));

        profilePage = mainPage.checkThatPageLoaded()
                .clickEmptyPhotoProfileButton();
        profilePage.checkThatPageLoaded()
                .setLastName(FIFTY_ONE_CHARACTERS)
                .checkLastnameErrorMessage("Length of this field must be no longer than 50 characters");
    }

}
