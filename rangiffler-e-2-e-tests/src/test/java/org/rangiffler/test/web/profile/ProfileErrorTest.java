package org.rangiffler.test.web.profile;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rangiffler.jupiter.annotation.GenerateUserAuthAndApiLogin;
import org.rangiffler.jupiter.extension.GenerateUserAuthAndApiLoginExtension;
import org.rangiffler.page.ProfilePage;
import org.rangiffler.page.YourTravelsPage;
import org.rangiffler.test.web.BaseWebTest;

@DisplayName("Update profile error")
@ExtendWith(GenerateUserAuthAndApiLoginExtension.class)
public class ProfileErrorTest extends BaseWebTest {

    private ProfilePage profilePage = new ProfilePage();
    private YourTravelsPage yourTravelsPage = new YourTravelsPage();
    private static final String FIFTY_ONE_CHARACTERS = "123fsdfgsdfgsdfgsdfgsdfgsdfgsdfgsdfgwergrtehtrebgfa";
    private static final String TEST_PWD = "123456";

    @BeforeEach
    void setUp() {
        Allure.step("open page", () -> Selenide.open(CFG.getFrontUrl()));
    }

    @GenerateUserAuthAndApiLogin(password = TEST_PWD)
    @Test
    @AllureId("601")
    public void errorMessageShouldBeVisibleInCaseThatFirstnameMoreThan50Symbols() {
        profilePage = yourTravelsPage.checkThatPageLoaded()
                .getHeader()
                .clickEmptyPhotoProfileButton();
        profilePage.checkThatPageLoaded()
                .setFirstName(FIFTY_ONE_CHARACTERS)
                .checkFirstnameErrorMessage("Length of this field must be no longer than 50 characters");
    }

    @GenerateUserAuthAndApiLogin(password = TEST_PWD)
    @Test
    @AllureId("602")
    public void errorMessageShouldBeVisibleInCaseThatLastnameMoreThan50Symbols() {
        profilePage = yourTravelsPage.checkThatPageLoaded()
                .getHeader()
                .clickEmptyPhotoProfileButton();
        profilePage.checkThatPageLoaded()
                .setLastName(FIFTY_ONE_CHARACTERS)
                .checkLastnameErrorMessage("Length of this field must be no longer than 50 characters");
    }

}
