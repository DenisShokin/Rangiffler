package org.rangiffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.io.File;

import static com.codeborne.selenide.Condition.image;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class AddPhotoPage extends BasePage<AddPhotoPage> {
    private final SelenideElement uploadImageLabel = $x("//img[@alt='New image']");
    private final SelenideElement descriptionField = $x("//label[text()='Description']//parent::div//textarea[1]");
    private final SelenideElement saveBtn = $("button[type='submit']");
    private final SelenideElement inputPhoto = $("input[type='file']");
    private final SelenideElement countryDropDown = $x("//div[@role='button']");
    private final ElementsCollection countryListBox = Selenide.$$x("//ul[@role='listbox']/li");

    @Override
    public AddPhotoPage checkThatPageLoaded() {
        uploadImageLabel.shouldBe(visible);
        return this;
    }

    public AddPhotoPage setDescription(String description) {
        descriptionField.val(description);
        return this;
    }

    @Step("Save profile changes")
    public void save() {
        saveBtn.click();
        // sleep for replication updated data
        Selenide.sleep(4000);
    }

    @Step("Upload photo")
    public AddPhotoPage uploadPhoto(String imagePath) {
        inputPhoto.uploadFile(new File(imagePath));
        uploadImageLabel.shouldBe(image);
        return this;
    }

    @Step("Select country")
    public AddPhotoPage selectCountry(String country) {
        countryDropDown.click();
        SelenideElement element = countryListBox.find(Condition.text(country));
        element.click();
        return this;
    }


}
