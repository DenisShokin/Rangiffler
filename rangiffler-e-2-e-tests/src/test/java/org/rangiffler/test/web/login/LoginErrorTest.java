package org.rangiffler.test.web.login;

import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rangiffler.page.LoginPage;
import org.rangiffler.test.web.BaseWebTest;

public class LoginErrorTest extends BaseWebTest {
    private LoginPage page = new LoginPage();
    private final Faker faker = new Faker();
    @BeforeEach
    void setUp() {
        Selenide.open(LoginPage.URL);
    }

    @Test
    @AllureId("301")
    public void errorMessageShouldBeVisibleInCaseThatUserNotCreated() {
        page.checkThatPageLoaded()
                .fillLoginForm(faker.name().name(), String.valueOf(faker.number().randomNumber()))
                .checkErrorMessage("Неверные учетные данные пользователя");
    }

}
