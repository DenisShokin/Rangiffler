package org.rangiffler.test.web.login;

import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rangiffler.db.entity.user.UserEntity;
import org.rangiffler.jupiter.annotation.GenerateUserAuthData;
import org.rangiffler.jupiter.extension.GenerateUserAuthDataExtension;
import org.rangiffler.page.LoginPage;
import org.rangiffler.page.StartPage;
import org.rangiffler.test.web.BaseWebTest;

@DisplayName("Error login")
@ExtendWith(GenerateUserAuthDataExtension.class)
public class LoginErrorTest extends BaseWebTest {
    private StartPage startPage = new StartPage();
    private LoginPage loginPage = new LoginPage();
    private final Faker faker = new Faker();
    private static final String TEST_PWD = "12345";

    @BeforeEach
    void setUp() {
        Selenide.open(CFG.getFrontUrl());
        startPage
                .checkThatPageLoaded()
                .goToLogin();
    }

    @Test
    @AllureId("301")
    public void errorMessageShouldBeVisibleInCaseThatUserNotCreated() {
        loginPage
                .checkThatPageLoaded()
                .fillLoginForm(faker.name().name(), String.valueOf(faker.number().randomNumber()))
                .checkErrorMessage("Неверные учетные данные пользователя");
    }

    @Test
    @GenerateUserAuthData(password = TEST_PWD, enabled = false)
    @AllureId("302")
    public void errorMessageShouldBeVisibleInCaseThatUserIsDisable(UserEntity user) {
        loginPage
                .checkThatPageLoaded()
                .fillLoginForm(user.getUsername(), TEST_PWD)
                .checkErrorMessage("Пользователь отключен");
    }

    @Test
    @GenerateUserAuthData(password = TEST_PWD, accountNonExpired = false)
    @AllureId("303")
    public void errorMessageShouldBeVisibleInCaseThatUserAccountIsExpired(UserEntity user) {
        loginPage
                .checkThatPageLoaded()
                .fillLoginForm(user.getUsername(), TEST_PWD)
                .checkErrorMessage("Срок действия учетной записи пользователя истек");
    }

    @Test
    @GenerateUserAuthData(
            password = TEST_PWD,
            accountNonLocked = false)
    @AllureId("304")
    public void errorMessageShouldBeVisibleInCaseThatUserAccountIsLock(UserEntity user) {
        loginPage
                .checkThatPageLoaded()
                .fillLoginForm(user.getUsername(), TEST_PWD)
                .checkErrorMessage("Учетная запись пользователя заблокирована");
    }

    @Test
    @GenerateUserAuthData(
            password = TEST_PWD,
            credentialsNonExpired = false)
    @AllureId("305")
    public void errorMessageShouldBeVisibleInCaseThatUserCredentialsExpired(UserEntity user) {
        loginPage
                .checkThatPageLoaded()
                .fillLoginForm(user.getUsername(), TEST_PWD)
                .checkErrorMessage("Срок действия учетных данных пользователя истек");
    }

}
