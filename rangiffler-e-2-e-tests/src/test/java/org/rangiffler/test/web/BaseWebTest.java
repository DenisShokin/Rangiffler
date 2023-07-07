package org.rangiffler.test.web;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.rangiffler.config.Config;

public class BaseWebTest {

    protected static final Config CFG = Config.getConfig();

    static {
        SelenideLogger.addListener("allureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false)
                .includeSelenideSteps(false)
        );

        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 20000L;
    }

    @AfterEach
    void closeBrowser() {
        Selenide.clearBrowserCookies();
        Selenide.closeWebDriver();
    }
}
