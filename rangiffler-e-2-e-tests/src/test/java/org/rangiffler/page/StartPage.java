package org.rangiffler.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.rangiffler.config.Config;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class StartPage extends BasePage<StartPage> {

    public static final String URL = Config.getConfig().getFrontUrl();
    private final SelenideElement header = $("div h1");
    private final SelenideElement loginButton = $x("//a[text()='Login']");
    private final SelenideElement registerButton = $x("//a[text()='Register']");

    @Override
    public StartPage checkThatPageLoaded() {
        header.shouldHave(text("Be like Rangiffler"));
        return this;
    }

    @Step("Нажать кнопку Логин")
    public LoginPage goToLogin() {
        loginButton.click();
        return new LoginPage();
    }

    @Step("Нажать кнопку Регистрация")
    public RegistrationPage goToRegister() {
        registerButton.click();
        return new RegistrationPage();
    }

    @Step("Перейти на стартовую страницу")
    public StartPage open() {
        Selenide.open(URL);
        return new StartPage();
    }

}
