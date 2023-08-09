package org.rangiffler.test.web.login;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.jupiter.extension.ApiLoginExtension;
import org.rangiffler.jupiter.extension.GenerateUserExtension;
import org.rangiffler.page.StartPage;
import org.rangiffler.page.YourTravelsPage;
import org.rangiffler.test.web.BaseWebTest;

import static io.qameta.allure.Allure.step;

@DisplayName("[WEB] Выход из системы")
@ExtendWith({GenerateUserExtension.class, ApiLoginExtension.class})
public class LogoutTest extends BaseWebTest {

    private StartPage startPage = new StartPage();
    private YourTravelsPage yourTravelsPage = new YourTravelsPage();

    @AllureId("701")
    @ApiLogin(user = @GenerateUser)
    @Test
    @Tag("WEB")
    @DisplayName("WEB: Пользователь должен получить иметь возможность выйти из системы")
    void logoutTest() {
        step(OPEN_MAIN_PAGE_STEP, () -> Selenide.open(CFG.getFrontUrl()));
        startPage = yourTravelsPage
                .checkThatPageLoaded()
                .getHeader()
                .logout();
        startPage.checkThatPageLoaded();
    }

}
