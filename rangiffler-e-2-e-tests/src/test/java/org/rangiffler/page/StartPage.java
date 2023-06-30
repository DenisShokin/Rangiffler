package org.rangiffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class StartPage extends BasePage<StartPage> {
    //TODO: реализовать
    private final SelenideElement header = $("div h1");
    private final SelenideElement loginButton = $x("//a[text()='Login']");
    private final SelenideElement registerButton = $x("//a[text()='Register']");

    @Override
    public StartPage checkThatPageLoaded() {
        header.shouldHave(text("Be like Rangiffler"));
        return this;
    }

    @Step("Click Login")
    public LoginPage goToLogin() {
        loginButton.click();
        return new LoginPage();
    }

    @Step("Click Register")
    public LoginPage goToRegister() {
        registerButton.click();
        return new LoginPage();
    }

}
