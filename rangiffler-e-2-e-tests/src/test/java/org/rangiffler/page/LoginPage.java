package org.rangiffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage<LoginPage> {

    private final SelenideElement header = $(".form__header");
    private final SelenideElement errorForm = $(".form__error");
    private final SelenideElement usernameInput = $("input[name='username']");
    private final SelenideElement passwordInput = $("input[name='password']");
    private final SelenideElement signUpBtn = $("button[type='submit']");

    @Override
    public LoginPage checkThatPageLoaded() {
        header.shouldHave(text("Login to Rangiffler"));
        return this;
    }

    @Step("Заполнить форму логина")
    public LoginPage fillLoginForm(String username, String password) {
        usernameInput.val(username);
        passwordInput.val(password);
        signUpBtn.click();
        return this;
    }

    @Step("Авторизоваться в системе")
    public YourTravelsPage successFillLoginForm(String username, String password) {
        usernameInput.val(username);
        passwordInput.val(password);
        signUpBtn.click();
        return new YourTravelsPage();
    }

    @Step("Проверить сообщение об ошибке")
    public LoginPage checkErrorMessage(String expectedMessage) {
        errorForm.shouldHave(text(expectedMessage));
        return this;
    }

}
