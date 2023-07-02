package org.rangiffler.test.web.registration;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rangiffler.page.RegistrationPage;
import org.rangiffler.page.StartPage;
import org.rangiffler.test.web.BaseWebTest;

public class RegistrationErrorTest extends BaseWebTest {

    private StartPage startPage = new StartPage();
    private RegistrationPage registrationPage = new RegistrationPage();

    @BeforeEach
    void setUp() {
        Selenide.open(CFG.getFrontUrl());
        startPage.checkThatPageLoaded()
                .goToRegister();
    }

    @Test
    @AllureId("201")
    public void errorMessageShouldBeVisibleInCaseThatPasswordsAreDifferent() {
        registrationPage.checkThatPageLoaded()
                .fillRegistrationForm("wdfsdasfs", "123", "12345")
                .checkErrorMessage("Passwords should be equal");
    }

    @Test
    @AllureId("202")
    public void errorMessageShouldBeVisibleInCaseThatPasswordsLessThan3Symbols() {
        registrationPage.checkThatPageLoaded()
                .fillRegistrationForm("wdfsdadfdaasfs", "1", "1")
                .checkErrorMessage("Allowed password length should be from 3 to 12 characters");
    }

    @Test
    @AllureId("203")
    public void errorMessageShouldBeVisibleInCaseThatUsernameLessThan3Symbols() {
        registrationPage.checkThatPageLoaded()
                .fillRegistrationForm("wd", "123", "123")
                .checkErrorMessage("Allowed username length should be from 3 to 50 characters");
    }

    @Test
    @AllureId("204")
    public void errorMessageShouldBeVisibleInCaseThatUsernameAlreadyExists() {
        // TODO
    }
}
