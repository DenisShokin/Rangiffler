package org.rangiffler.test.web.registration;

import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.rangiffler.db.dao.user.RangifflerUsersDAO;
import org.rangiffler.db.dao.user.RangifflerUsersDAOHibernate;
import org.rangiffler.db.entity.user.UserEntity;
import org.rangiffler.page.RegistrationPage;
import org.rangiffler.page.StartPage;
import org.rangiffler.test.web.BaseWebTest;

import static io.qameta.allure.Allure.step;

@DisplayName("[WEB] Регистрация")
public class RegistrationNewUserTest extends BaseWebTest {
    private StartPage startPage = new StartPage();
    private RegistrationPage registrationPage = new RegistrationPage();
    private String username;
    private RangifflerUsersDAO usersDAO = new RangifflerUsersDAOHibernate();
    private final Faker faker = new Faker();
    private static final String TEST_PWD = "12345";

    @BeforeEach
    void setUp() {
        username = faker.name().username();

        step(OPEN_MAIN_PAGE_STEP, () -> Selenide.open(CFG.getFrontUrl()));
        startPage
                .checkThatPageLoaded()
                .goToRegister();
    }

    @AfterEach
    void cleanUp() {
        UserEntity user = usersDAO.getUserByUsername(username);
        usersDAO.removeUser(user);
    }

    @Test
    @AllureId("101")
    @Tag("WEB")
    @DisplayName("WEB: Пользователь должен иметь возможность создать аккаунт в системе")
    public void successRegistration() {
        registrationPage
                .checkThatPageLoaded()
                .successFillRegistrationForm(username, TEST_PWD, TEST_PWD)
                .checkThatPageLoaded();
    }

}
