package org.rangiffler.test.web.travel.yourtravels;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.model.CountryJson;
import org.rangiffler.model.PhotoJson;
import org.rangiffler.page.PhotoPage;
import org.rangiffler.page.YourTravelsPage;
import org.rangiffler.page.component.HeaderComponent;
import org.rangiffler.test.web.BaseWebTest;
import org.rangiffler.utils.ImageUtils;

@DisplayName("Your travels. Add photo")
public class AddPhotoTest extends BaseWebTest {

    private HeaderComponent headerComponent = new HeaderComponent();
    private PhotoPage photoPage = new PhotoPage();
    private PhotoJson photo = new PhotoJson();
    private CountryJson country = new CountryJson();

    @BeforeEach
    void setUp() {
        photo = new PhotoJson();
        photo.setDescription("Description for photo");
        country.setCode("cn");
        country.setName("China");
        photo.setCountryJson(country);
    }

    @ApiLogin(user = @GenerateUser)
    @AllureId("801")
    @ParameterizedTest(name = "add photo with extension - {0}")
    @ValueSource(strings =
            {"src/test/resources/testdata/country/china_1.jfif",
                    "src/test/resources/testdata/country/china_1.png",
                    "src/test/resources/testdata/country/china_1.gif"})
    void addPhotoWithDifferentExtension(String imagePath) {
        photo.setPhoto(ImageUtils.getDataURI(imagePath));

        Allure.step("open page", () -> Selenide.open(CFG.getFrontUrl()));
        photoPage = headerComponent
                .checkThatComponentDisplayed()
                .checkPhotosCount(0)
                .checkVisitedCountriesCount(0)
                .clickAddPhoto();
        photoPage
                .checkThatPageLoaded()
                .uploadPhoto(imagePath)
                .setDescription(photo.getDescription())
                .selectCountry(photo.getCountryJson().getName())
                .save();
        YourTravelsPage yourTravelsPage = headerComponent
                .checkThatComponentDisplayed()
                .checkPhotosCount(1)
                .checkVisitedCountriesCount(1)
                .goToYourTravelsPage();
        yourTravelsPage.getImagesList()
                .checkImagesListContainsPhotos(photo);
    }

    @ApiLogin(user = @GenerateUser)
    @AllureId("801")
    @Test
    void addPhotoWithoutClickSave() {
        final String imagePath = "src/test/resources/testdata/country/china_1.jfif";
        photo.setPhoto(ImageUtils.getDataURI(imagePath));

        Allure.step("open page", () -> Selenide.open(CFG.getFrontUrl()));
        photoPage = headerComponent
                .checkThatComponentDisplayed()
                .checkPhotosCount(0)
                .checkVisitedCountriesCount(0)
                .clickAddPhoto();
        photoPage
                .checkThatPageLoaded()
                .uploadPhoto(imagePath)
                .setDescription(photo.getDescription())
                .selectCountry(photo.getCountryJson().getName())
                .clickCloseButton();
        YourTravelsPage yourTravelsPage = headerComponent
                .checkThatComponentDisplayed()
                .checkPhotosCount(0)
                .checkVisitedCountriesCount(0)
                .goToYourTravelsPage();
        yourTravelsPage.getImagesList()
                .checkThatComponentIsHidden();
    }

}
