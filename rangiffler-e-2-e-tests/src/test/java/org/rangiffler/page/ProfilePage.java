package org.rangiffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.rangiffler.utils.FileResourcesUtils;

import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ProfilePage extends BasePage<ProfilePage> {
    private final SelenideElement userNameLabel = $x("//p[text()='Username: ']");
    private final SelenideElement firstnameInput = $("input[name='firstName']");
    private final SelenideElement firstnameTextHelperLabel = $x("//label[text()='First name']//parent::div/p");
    private final SelenideElement lastNameInput = $("input[name='lastName']");
    private final SelenideElement lastnameTextHelperLabel = $x("//label[text()='Last name']//parent::div/p");
    private final SelenideElement saveBtn = $("button[type='submit']");
    private final SelenideElement addPhotoIcon = $("svg[data-testid='AddAPhotoIcon']");
    private final SelenideElement inputPhoto = $("input[type='file']");
    private final SelenideElement personIcon = $("svg[data-testid='PersonIcon']");
    private final SelenideElement closeIcon = $("svg[data-testid='CloseIcon']");


    @Override
    public ProfilePage checkThatPageLoaded() {
        userNameLabel.shouldHave(visible);
        return this;
    }

    @Step("Ввести Имя")
    public ProfilePage setFirstName(String firstName) {
        firstnameInput.val(firstName);
        return this;
    }

    @Step("Ввести Фамилию")
    public ProfilePage setLastName(String lastName) {
        lastNameInput.val(lastName);
        return this;
    }

    @Step("Сохранить изменения")
    public void save() {
        saveBtn.click();
        // sleep for replication updated data
        Selenide.sleep(4000);
    }

    @Step("Проверить имя")
    public ProfilePage checkFirstname(String firstname) {
        firstnameInput.shouldHave(Condition.value(firstname));
        return this;
    }

    @Step("Проверить фамилию")
    public ProfilePage checkLastname(String lastname) {
        lastNameInput.shouldHave(Condition.value(lastname));
        return this;
    }

    @Step("Проверить текст ошибки для поля Имя")
    public ProfilePage checkFirstnameErrorMessage(String expectedMessage) {
        firstnameTextHelperLabel.shouldHave(visible);
        firstnameTextHelperLabel.shouldHave(text(expectedMessage));
        saveBtn.shouldHave(disabled);
        return this;
    }

    @Step("Проверить текст ошибки для поля Фамилия")
    public ProfilePage checkLastnameErrorMessage(String expectedMessage) {
        lastnameTextHelperLabel.shouldHave(visible);
        lastnameTextHelperLabel.shouldHave(text(expectedMessage));
        saveBtn.shouldHave(disabled);
        return this;
    }

    @Step("Загрузить фото профиля")
    public ProfilePage uploadPhoto(String imagePath) {
        inputPhoto.uploadFile(FileResourcesUtils.getFileFromResource(imagePath));
        return this;
    }

    @Step("Проверить фото профиля")
    public ProfilePage checkPhoto(String lastname) {
        lastNameInput.shouldHave(Condition.value(lastname));
        return this;
    }

    @Step("Закрыть страницу")
    public void close() {
        closeIcon.click();
    }

}
