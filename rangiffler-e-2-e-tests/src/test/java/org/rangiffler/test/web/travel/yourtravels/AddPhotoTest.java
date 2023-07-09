package org.rangiffler.test.web.travel.yourtravels;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.jupiter.extension.GenerateUserAuthAndApiLoginExtension;
import org.rangiffler.model.CountryJson;
import org.rangiffler.model.PhotoJson;
import org.rangiffler.page.AddPhotoPage;
import org.rangiffler.page.YourTravelsPage;
import org.rangiffler.page.component.HeaderComponent;
import org.rangiffler.test.web.BaseWebTest;
import org.rangiffler.utils.ImageUtils;

@DisplayName("Your travel. Add photo")
@ExtendWith(GenerateUserAuthAndApiLoginExtension.class)
public class AddPhotoTest extends BaseWebTest {

    private HeaderComponent headerComponent = new HeaderComponent();
    private AddPhotoPage addPhotoPage = new AddPhotoPage();

    @ApiLogin(user = @GenerateUser)
    @AllureId("801")
    @ParameterizedTest(name = "add photo with extension - {0}")
    @ValueSource(strings =
            {"src/test/resources/testdata/country/china_1.jfif",
                    "src/test/resources/testdata/country/china_1.png",
                    "src/test/resources/testdata/country/china_1.gif"})
    void addPhotoWithDifferentExtension(String imagePath) {
        PhotoJson photo = new PhotoJson();
        photo.setPhoto(ImageUtils.getDataURI(imagePath));
        photo.setDescription("Description for photo");
        CountryJson country = new CountryJson();
        country.setCode("cn");
        country.setName("China");
        photo.setCountryJson(country);

        Allure.step("open page", () -> Selenide.open(CFG.getFrontUrl()));
        addPhotoPage = headerComponent
                .checkThatComponentDisplayed()
                .checkPhotosCount(0)
                .checkVisitedCountriesCount(0)
                .clickAddPhoto();
        addPhotoPage
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
        yourTravelsPage.checkImagesListContainsPhotos(photo);
    }

//    @Test
//    @AllureId("802")
//    void addTwoPhotoTravelForOneCountry() {
//
//    }
//
//    @Test
//    @AllureId("803")
//    void addPhotosForTwoCountry() {
//
//    }

}
