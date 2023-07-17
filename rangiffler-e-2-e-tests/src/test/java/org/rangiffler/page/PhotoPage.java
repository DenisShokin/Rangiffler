package org.rangiffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.rangiffler.model.CountryJson;
import org.rangiffler.utils.FileResourcesUtils;

import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Condition.image;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class PhotoPage extends BasePage<PhotoPage> {
    private final SelenideElement uploadImageLabel = $x("//img[@alt='New image']");
    private final SelenideElement descriptionField = $x("//form//textarea[1]");
    private final SelenideElement saveBtn = $("button[type='submit']");
    private final SelenideElement closeBtn = $("svg[data-testid='CloseIcon']");
    private final SelenideElement editBtn = $("[data-testid='EditIcon']");
    private final SelenideElement deleteBtn = $("[data-testid='DeleteOutlineIcon']");
    private final SelenideElement inputPhoto = $("input[type='file']");
    private final SelenideElement countryDropDown = $x("//div[@role='button']");
    private final ElementsCollection countryListBox = Selenide.$$x("//ul[@role='listbox']/li");
    private final SelenideElement placeIcon = $x("//img//parent::div/p[1]");
    private final SelenideElement descriptionLabel = $x("//img//parent::div/p[2]");

    @Override
    public PhotoPage checkThatPageLoaded() {
        closeBtn.shouldBe(visible);
        return this;
    }

    @Step("Set description for photo")
    public PhotoPage setDescription(String description) {
        descriptionField.sendKeys(Keys.CONTROL + "A");
        descriptionField.sendKeys(Keys.BACK_SPACE);
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
    public PhotoPage uploadPhoto(String imagePath) {
        inputPhoto.uploadFile(FileResourcesUtils.getFileFromResource(imagePath));
        uploadImageLabel.shouldBe(image);
        return this;
    }

    @Step("Select country")
    public PhotoPage selectCountry(CountryJson country) {
        countryDropDown.click();
        SelenideElement element = countryListBox.
                filterBy(Condition.text(country.getName()))
                .find(Condition.attribute("data-value", country.getCode()));
        element.click();
        return this;
    }

    @Step("Edit photo")
    public PhotoPage editButtonClick() {
        editBtn.click();
        return this;
    }

    @Step("Delete photo")
    public DeletePhotoPage deleteButtonClick() {
        deleteBtn.click();
        return new DeletePhotoPage();
    }

    @Step("Check photo country")
    public PhotoPage checkPhotoCountry(String expectedCountry) {
        placeIcon.shouldHave(text(expectedCountry));
        return this;
    }

    @Step("Check photo description")
    public PhotoPage checkPhotoDescription(String expectedDescription) {
        descriptionLabel.shouldHave(text(expectedDescription));
        return this;
    }

    @Step("Click close button")
    public void clickCloseButton() {
        closeBtn.click();
    }

    @Step("Save button is disable")
    public void saveButtonIsDisable() {
        saveBtn.shouldBe(disabled);
    }

}
