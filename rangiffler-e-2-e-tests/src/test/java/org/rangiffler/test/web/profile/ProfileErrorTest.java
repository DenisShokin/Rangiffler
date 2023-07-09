package org.rangiffler.test.web.profile;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.page.ProfilePage;
import org.rangiffler.page.YourTravelsPage;
import org.rangiffler.test.web.BaseWebTest;

@DisplayName("Update profile error")
public class ProfileErrorTest extends BaseWebTest {

    private ProfilePage profilePage = new ProfilePage();
    private YourTravelsPage yourTravelsPage = new YourTravelsPage();
    private static final String FIFTY_ONE_CHARACTERS = "123fsdfgsdfgsdfgsdfgsdfgsdfgsdfgsdfgwergrtehtrebgfa";

    @BeforeEach
    void setUp() {
        Allure.step("open page", () -> Selenide.open(CFG.getFrontUrl()));
    }

    @Test
    @ApiLogin(user = @GenerateUser)
    @AllureId("601")
    public void errorMessageShouldBeVisibleInCaseThatFirstnameMoreThan50Symbols() {
        profilePage = yourTravelsPage.checkThatPageLoaded()
                .getHeader()
                .clickEmptyPhotoProfileButton();
        profilePage.checkThatPageLoaded()
                .setFirstName(FIFTY_ONE_CHARACTERS)
                .checkFirstnameErrorMessage("Length of this field must be no longer than 50 characters");
    }

    @Test
    @ApiLogin(user = @GenerateUser)
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
