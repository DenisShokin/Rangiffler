package org.rangiffler.test.web.registration;

import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rangiffler.page.RegistrationPage;
import org.rangiffler.test.web.BaseWebTest;

public class RegistrationNewUserTest extends BaseWebTest {

    private RegistrationPage registrationPage = new RegistrationPage();
    private final Faker faker = new Faker();
    private static final String TEST_PWD = "12345";

    @BeforeEach
    void setUp() {
        Selenide.open(RegistrationPage.URL);
        registrationPage.checkThatPageLoaded();
    }

    @Test
    @AllureId("101")
    public void successRegistration() {
        registrationPage
                .successFillRegistrationForm(faker.name().username(), TEST_PWD, TEST_PWD)
                .checkThatPageLoaded();
    }

}
