package org.rangiffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.rangiffler.config.Config;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class RegistrationPage extends BasePage<RegistrationPage> {
    public static final String URL = Config.getConfig().getAuthUrl() + "/register";
    private final SelenideElement header = $(".form__header");
    private final SelenideElement usernameInput = $("#username");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement passwordSubmitInput = $("#passwordSubmit");
    private final SelenideElement signUpBtn = $("button[type='submit']");
    private final SelenideElement formError = $(".form__error");

    @Override
    public RegistrationPage checkThatPageLoaded() {
        header.shouldHave(text("Register to Rangiffler"));
        return this;
    }

    @Step("Заполнить форму регистрации")
    public RegistrationPage fillRegistrationForm(String username, String password, String passwordSubmit) {
        usernameInput.val(username);
        passwordInput.val(password);
        passwordSubmitInput.val(passwordSubmit);
        signUpBtn.click();
        return this;
    }

    @Step("Проверить текст ошибки")
    public RegistrationPage checkErrorMessage(String expectedMessage) {
        formError.shouldHave(text(expectedMessage));
        return this;
    }

    @Step("Заполнить форму регистрации и перейти на страницу Поздравления")
    public CongratulationsPage successFillRegistrationForm(String username, String password, String passwordSubmit) {
        usernameInput.val(username);
        passwordInput.val(password);
        passwordSubmitInput.val(passwordSubmit);
        signUpBtn.click();
        return new CongratulationsPage();
    }

}
