package org.rangiffler.test.web.travel.yourtravels;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.jupiter.annotation.GeneratePhoto;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.model.CountryJson;
import org.rangiffler.model.PhotoJson;
import org.rangiffler.model.UserJson;
import org.rangiffler.page.DeletePhotoPage;
import org.rangiffler.page.PhotoPage;
import org.rangiffler.page.YourTravelsPage;
import org.rangiffler.page.component.HeaderComponent;
import org.rangiffler.test.web.BaseWebTest;

@DisplayName("Your travels. Edit photo")
public class EditPhotoTest extends BaseWebTest {

    private HeaderComponent headerComponent = new HeaderComponent();

    @Test
    @AllureId("901")
    @ApiLogin(user = @GenerateUser
            (photos = @GeneratePhoto)
    )
    void updatePhoto(UserJson user) {
        final PhotoJson photo = user.getPhotos().get(0);
        PhotoJson updatedPhoto = new PhotoJson();
        updatedPhoto.setDescription("Update description");
        CountryJson updatedCountry = new CountryJson();
        updatedPhoto.setPhoto(photo.getPhoto());
        updatedCountry.setName("Zimbabwe");
        updatedCountry.setCode("zw");
        updatedPhoto.setCountryJson(updatedCountry);

        Allure.step("open page", () -> Selenide.open(CFG.getFrontUrl()));

        YourTravelsPage yourTravelsPage = headerComponent
                .checkThatComponentDisplayed()
                .goToYourTravelsPage();
        PhotoPage photoPage = yourTravelsPage
                .checkThatPageLoaded()
                .getImagesList()
                .clickToImage(photo);
        photoPage
                .checkThatPageLoaded()
                .editButtonClick()
                .setDescription(updatedPhoto.getDescription())
                .selectCountry(updatedCountry)
                .save();
        photoPage = yourTravelsPage
                .checkThatPageLoaded()
                .getImagesList()
                .clickToImage(updatedPhoto);
        photoPage
                .checkThatPageLoaded()
                .checkPhotoCountry(updatedCountry.getName())
                .checkPhotoDescription(updatedPhoto.getDescription());
    }

    @Test
    @AllureId("902")
    @ApiLogin(user = @GenerateUser
            (photos = @GeneratePhoto)
    )
    void deletePhoto(UserJson user) {
        final PhotoJson photo = user.getPhotos().get(0);

        Allure.step("open page", () -> Selenide.open(CFG.getFrontUrl()));

        YourTravelsPage yourTravelsPage = headerComponent
                .checkThatComponentDisplayed()
                .goToYourTravelsPage();
        PhotoPage photoPage = yourTravelsPage
                .checkThatPageLoaded()
                .getImagesList()
                .clickToImage(photo);
        DeletePhotoPage deletePhotoPage = photoPage
                .checkThatPageLoaded()
                .deleteButtonClick();
        deletePhotoPage.deleteButtonClick();
        yourTravelsPage.checkThatPageLoaded()
                .getImagesList()
                .checkThatComponentIsHidden();
    }

}
