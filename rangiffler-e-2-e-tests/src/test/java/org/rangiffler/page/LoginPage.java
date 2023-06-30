package org.rangiffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.rangiffler.config.Config;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage<LoginPage> {

    public static final String URL = Config.getConfig().getAuthUrl() + "/login";
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

    @Step("Fill login form")
    public LoginPage fillLoginForm(String username, String password) {
        usernameInput.val(username);
        passwordInput.val(password);
        signUpBtn.click();
        return this;
    }

    @Step("Fill login form and go to main page")
    public MainPage successFillRegistrationForm(String username, String password, String passwordSubmit) {
        usernameInput.val(username);
        passwordInput.val(password);
        signUpBtn.click();
        return new MainPage();
    }

    public LoginPage checkErrorMessage(String expectedMessage) {
        errorForm.shouldHave(text(expectedMessage));
        return this;
    }

}
