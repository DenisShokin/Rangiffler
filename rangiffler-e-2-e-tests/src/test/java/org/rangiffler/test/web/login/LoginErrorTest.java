package org.rangiffler.test.web.login;

import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rangiffler.db.entity.user.UserEntity;
import org.rangiffler.jupiter.annotation.GenerateUserAuthData;
import org.rangiffler.jupiter.extension.GenerateUserAuthDataExtension;
import org.rangiffler.page.LoginPage;
import org.rangiffler.page.StartPage;
import org.rangiffler.test.web.BaseWebTest;

import static io.qameta.allure.Allure.step;

@DisplayName("[WEB] Логин в систему. Негативные сценарии")
@ExtendWith(GenerateUserAuthDataExtension.class)
public class LoginErrorTest extends BaseWebTest {
    private StartPage startPage = new StartPage();
    private LoginPage loginPage = new LoginPage();
    private final Faker faker = new Faker();
    private static final String TEST_PWD = "12345";

    @BeforeEach
    void setUp() {
        step(OPEN_MAIN_PAGE_STEP, () -> Selenide.open(CFG.getFrontUrl()));
        startPage
                .checkThatPageLoaded()
                .goToLogin();
    }

    @Test
    @AllureId("301")
    @Tag("WEB")
    @DisplayName("WEB: Пользователь должен получить сообщение при вводе неверных учетных данных")
    public void errorMessageShouldBeVisibleInCaseThatUserNotCreated() {
        loginPage
                .checkThatPageLoaded()
                .fillLoginForm(faker.name().name(), String.valueOf(faker.number().randomNumber()))
                .checkErrorMessage("Неверные учетные данные пользователя");
    }

    @Test
    @GenerateUserAuthData(password = TEST_PWD, enabled = false)
    @AllureId("302")
    @Tag("WEB")
    @DisplayName("WEB: Пользователь должен получить сообщение при авторизации под отключенным аккаунтом")
    public void errorMessageShouldBeVisibleInCaseThatUserIsDisable(UserEntity user) {
        loginPage
                .checkThatPageLoaded()
                .fillLoginForm(user.getUsername(), TEST_PWD)
                .checkErrorMessage("Пользователь отключен");
    }

    @Test
    @GenerateUserAuthData(password = TEST_PWD, accountNonExpired = false)
    @AllureId("303")
    @Tag("WEB")
    @DisplayName("WEB: Пользователь должен получить сообщение при авторизации под просроченным аккаунтом")
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
    @Tag("WEB")
    @DisplayName("WEB: Пользователь должен получить сообщение при авторизации под заблокированным аккаунтом")
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
    @Tag("WEB")
    @DisplayName("WEB: Пользователь должен получить сообщение при авторизации под аккаунтом с просроченными правами")
    public void errorMessageShouldBeVisibleInCaseThatUserCredentialsExpired(UserEntity user) {
        loginPage
                .checkThatPageLoaded()
                .fillLoginForm(user.getUsername(), TEST_PWD)
                .checkErrorMessage("Срок действия учетных данных пользователя истек");
    }

}
