package org.rangiffler.test.web.travel.yourtravels;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.jupiter.annotation.GeneratePhoto;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.model.CountryJson;
import org.rangiffler.model.PhotoJson;
import org.rangiffler.model.UserJson;
import org.rangiffler.page.PhotoPage;
import org.rangiffler.page.YourTravelsPage;
import org.rangiffler.page.component.HeaderComponent;
import org.rangiffler.test.web.BaseWebTest;
import org.rangiffler.utils.ImageUtils;

@DisplayName("Ваши путешествия (Your travels). Добавление")
public class AddPhotoTest extends BaseWebTest {

    private HeaderComponent headerComponent = new HeaderComponent();
    private PhotoPage photoPage = new PhotoPage();
    private PhotoJson photo = new PhotoJson();
    private CountryJson country = new CountryJson();
    private static final String IMAGE_PATH = "testdata/country/china_1.jfif";

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
    @Tag("WEB")
    @DisplayName("WEB: Пользователь должен получить иметь возможность загрузить изображение с различными расширениями")
    @ParameterizedTest(name = "add photo with extension - {0}")
    @ValueSource(strings =
            {"testdata/country/china_4.jpeg",
                    "testdata/country/china_2.png",
                    "testdata/country/china_3.gif"
            })
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
                .selectCountry(photo.getCountryJson())
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
    @AllureId("802")
    @Test
    @Tag("WEB")
    @DisplayName("WEB: Путешествие не сохраняется без подверждения")
    void addPhotoWithoutClickSave() {
        photo.setPhoto(ImageUtils.getDataURI(IMAGE_PATH));

        Allure.step("Открыть страницу", () -> Selenide.open(CFG.getFrontUrl()));
        photoPage = headerComponent
                .checkThatComponentDisplayed()
                .checkPhotosCount(0)
                .checkVisitedCountriesCount(0)
                .clickAddPhoto();
        photoPage
                .checkThatPageLoaded()
                .uploadPhoto(IMAGE_PATH)
                .setDescription(photo.getDescription())
                .selectCountry(photo.getCountryJson())
                .clickCloseButton();
        YourTravelsPage yourTravelsPage = headerComponent
                .checkThatComponentDisplayed()
                .checkPhotosCount(0)
                .checkVisitedCountriesCount(0)
                .goToYourTravelsPage();
        yourTravelsPage.getImagesList()
                .checkThatComponentIsHidden();
    }

    @ApiLogin(user = @GenerateUser
            (photos = @GeneratePhoto)
    )
    @AllureId("803")
    @Test
    @Tag("WEB")
    @DisplayName("WEB: Пользователь должен иметь возможность добавлять несколько фотографий для одной страны")
    void addTwoPhotoForOneCountry(UserJson user) {
        final PhotoJson photo = user.getPhotos().get(0);
        PhotoJson secondPhoto = new PhotoJson();
        secondPhoto.setDescription(photo.getDescription());
        secondPhoto.setPhoto(ImageUtils.getDataURI(IMAGE_PATH));
        secondPhoto.setCountryJson(photo.getCountryJson());

        Allure.step("Открыть страницу", () -> Selenide.open(CFG.getFrontUrl()));

        photoPage = headerComponent
                .checkThatComponentDisplayed()
                .checkPhotosCount(1)
                .checkVisitedCountriesCount(1)
                .clickAddPhoto();
        photoPage
                .checkThatPageLoaded()
                .uploadPhoto(IMAGE_PATH)
                .setDescription(photo.getDescription())
                .selectCountry(photo.getCountryJson())
                .save();
        YourTravelsPage yourTravelsPage = headerComponent
                .checkThatComponentDisplayed()
                .checkPhotosCount(2)
                .checkVisitedCountriesCount(1)
                .goToYourTravelsPage();
        yourTravelsPage.getImagesList()
                .checkImagesListContainsPhotos(photo, secondPhoto);
    }

    @ApiLogin(user = @GenerateUser)
    @AllureId("804")
    @Test
    @Tag("WEB")
    @DisplayName("WEB: Пользователь должен получить иметь возможнсть добавлять путешествие без фото")
    void addPhotoCardWithoutImage() {
        Allure.step("Открыть страницу", () -> Selenide.open(CFG.getFrontUrl()));
        photoPage = headerComponent
                .checkThatComponentDisplayed()
                .clickAddPhoto();
        photoPage
                .checkThatPageLoaded()
                .setDescription(photo.getDescription())
                .selectCountry(photo.getCountryJson())
                .saveButtonIsDisable();
    }


}
