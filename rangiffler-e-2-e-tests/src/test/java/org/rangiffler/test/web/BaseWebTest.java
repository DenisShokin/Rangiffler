package org.rangiffler.test.web;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
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
    }
}
