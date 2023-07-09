package org.rangiffler.test.web.login;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.page.StartPage;
import org.rangiffler.page.YourTravelsPage;
import org.rangiffler.test.web.BaseWebTest;

@DisplayName("Logout")
public class LogoutTest extends BaseWebTest {

    private StartPage startPage = new StartPage();
    private YourTravelsPage yourTravelsPage = new YourTravelsPage();

    @AllureId("701")
    @ApiLogin(user = @GenerateUser)
    @Test
    void logoutTest() {
        Allure.step("open page", () -> Selenide.open(CFG.getFrontUrl()));
        startPage = yourTravelsPage
                .checkThatPageLoaded()
                .getHeader()
                .logout();
        startPage.checkThatPageLoaded();
    }

}
