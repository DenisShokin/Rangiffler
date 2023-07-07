package org.rangiffler.test.web.login;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rangiffler.jupiter.annotation.GenerateUserAuthAndApiLogin;
import org.rangiffler.jupiter.extension.GenerateUserAuthAndApiLoginExtension;
import org.rangiffler.page.StartPage;
import org.rangiffler.page.YourTravelsPage;
import org.rangiffler.test.web.BaseWebTest;

@DisplayName("Logout")
@ExtendWith(GenerateUserAuthAndApiLoginExtension.class)
public class LogoutTest extends BaseWebTest {

    private StartPage startPage = new StartPage();
    private YourTravelsPage yourTravelsPage = new YourTravelsPage();
    private static final String TEST_PWD = "12345";

    @AllureId("701")
    @GenerateUserAuthAndApiLogin(password = TEST_PWD)
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
