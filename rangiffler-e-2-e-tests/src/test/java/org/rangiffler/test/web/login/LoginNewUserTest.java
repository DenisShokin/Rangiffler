package org.rangiffler.test.web.login;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rangiffler.db.entity.user.UserEntity;
import org.rangiffler.jupiter.annotation.GenerateUserAuthData;
import org.rangiffler.jupiter.extension.GenerateUserAuthDataExtension;
import org.rangiffler.page.LoginPage;
import org.rangiffler.page.StartPage;
import org.rangiffler.page.YourTravelsPage;
import org.rangiffler.test.web.BaseWebTest;

import static io.qameta.allure.Allure.step;

@DisplayName("[WEB] Логин в систему")
@ExtendWith(GenerateUserAuthDataExtension.class)
public class LoginNewUserTest extends BaseWebTest {
    private StartPage startPage = new StartPage();
    private LoginPage loginPage = new LoginPage();
    private static final String TEST_PWD = "12345";

    @AllureId("401")
    @GenerateUserAuthData(password = TEST_PWD)
    @Test
    @Tag("WEB")
    @DisplayName("WEB: Пользователь должен успешно авторизоваться")
    void loginTest(UserEntity user) {
        step(OPEN_MAIN_PAGE_STEP, () -> Selenide.open(CFG.getFrontUrl()));
        loginPage = startPage
                .goToLogin()
                .checkThatPageLoaded();
        YourTravelsPage yourTravelsPage = loginPage
                .successFillLoginForm(user.getUsername(), TEST_PWD);
        yourTravelsPage.checkThatPageLoaded();
    }

}
