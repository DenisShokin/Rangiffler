package org.rangiffler.test.web.login;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.page.StartPage;
import org.rangiffler.page.YourTravelsPage;
import org.rangiffler.test.web.BaseWebTest;

@DisplayName("Выход из системы")
public class LogoutTest extends BaseWebTest {

    private StartPage startPage = new StartPage();
    private YourTravelsPage yourTravelsPage = new YourTravelsPage();

    @AllureId("701")
    @ApiLogin(user = @GenerateUser)
    @Test
    @Tag("WEB")
    @DisplayName("WEB: Пользователь должен получить иметь возможность выйти из системы")
    void logoutTest() {
        Allure.step("Открыть страницу", () -> Selenide.open(CFG.getFrontUrl()));
        startPage = yourTravelsPage
                .checkThatPageLoaded()
                .getHeader()
                .logout();
        startPage.checkThatPageLoaded();
    }

}
