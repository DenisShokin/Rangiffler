package org.rangiffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class ProfilePage extends BasePage<ProfilePage> {

    private final SelenideElement header = $(".form__header");
    @Override
    public ProfilePage checkThatPageLoaded() {
        header.shouldHave(text("Login to Rangiffler"));
        return this;
    }



}
