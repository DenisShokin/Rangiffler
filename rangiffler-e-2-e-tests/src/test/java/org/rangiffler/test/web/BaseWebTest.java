package org.rangiffler.test.web;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rangiffler.config.Config;
import org.rangiffler.jupiter.extension.ApiLoginExtension;

@ExtendWith(ApiLoginExtension.class)
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
}
