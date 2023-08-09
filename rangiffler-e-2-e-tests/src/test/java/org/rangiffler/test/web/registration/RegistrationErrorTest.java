package org.rangiffler.test.web.registration;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.rangiffler.db.entity.user.UserEntity;
import org.rangiffler.jupiter.annotation.GenerateUserAuthData;
import org.rangiffler.jupiter.extension.GenerateUserAuthDataExtension;
import org.rangiffler.page.RegistrationPage;
import org.rangiffler.page.StartPage;
import org.rangiffler.test.web.BaseWebTest;

import java.util.stream.Stream;

import static io.qameta.allure.Allure.step;

@DisplayName("[WEB] Регистрация. Негативные сценарии")
@ExtendWith(GenerateUserAuthDataExtension.class)
public class RegistrationErrorTest extends BaseWebTest {

    private StartPage startPage = new StartPage();
    private RegistrationPage registrationPage = new RegistrationPage();

    @BeforeEach
    void setUp() {
        step(OPEN_MAIN_PAGE_STEP, () -> Selenide.open(CFG.getFrontUrl()));
        startPage
                .checkThatPageLoaded()
                .goToRegister();
    }

    @AllureId("201")
    @Tag("WEB")
    @ParameterizedTest(name = "WEB: Пользователь должен получить сообщение об ошибке : {3} при вводе некорректных данных")
    @MethodSource("provideErrorRegistration")
    public void errorMessageShouldBeVisible(String login, String password, String passwordSubmit, String expectedErrorMessage) {
        registrationPage
                .checkThatPageLoaded()
                .fillRegistrationForm(login, password, passwordSubmit)
                .checkErrorMessage(expectedErrorMessage);
    }

    @Test
    @GenerateUserAuthData(password = "12345")
    @AllureId("204")
    public void errorMessageShouldBeVisibleInCaseThatUsernameAlreadyExists(UserEntity user) {
        registrationPage
                .checkThatPageLoaded()
                .fillRegistrationForm(user.getUsername(), "123456", "123456")
                .checkErrorMessage("Username `" + user.getUsername() + "` already exists");
    }


    private static Stream<Arguments> provideErrorRegistration() {
        return Stream.of(
                Arguments.of("wdfsdasfs", "123", "12345", "Passwords should be equal"),
                Arguments.of("wdfsdadfdaasfs", "1", "1", "Allowed password length should be from 3 to 12 characters"),
                Arguments.of("wd", "123", "123", "Allowed username length should be from 3 to 50 characters"));
    }
}
