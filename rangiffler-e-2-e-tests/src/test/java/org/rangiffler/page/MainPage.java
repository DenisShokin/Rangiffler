package org.rangiffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage extends BasePage<MainPage> {

    private final SelenideElement header = $x("//h1");
    private final SelenideElement logoutButton = $("svg[data-testid='LogoutIcon']");

    @Override
    public MainPage checkThatPageLoaded() {
        header.shouldHave(text("Rangiffler"));
        return this;
    }

    public StartPage logout() {
        logoutButton.click();
        return new StartPage();
    }
}
