package org.rangiffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class CongratulationsPage extends BasePage<CongratulationsPage> {

    private final SelenideElement header = $("p:nth-child(1)");
    private final SelenideElement loginButton = $x("//a[text()='Sign in!']");

    @Override
    public CongratulationsPage checkThatPageLoaded() {
        header.shouldHave(text("Congratulations! You've registered!"));
        return this;
    }

    @Step("Нажать кнопку Логина")
    public LoginPage clickLoginButton() {
        loginButton.click();
        return new LoginPage();
    }

}
