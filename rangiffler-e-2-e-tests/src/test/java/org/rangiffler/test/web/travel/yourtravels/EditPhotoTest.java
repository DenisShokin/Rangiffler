package org.rangiffler.test.web.travel.yourtravels;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rangiffler.jupiter.annotation.ApiLogin;
import org.rangiffler.jupiter.annotation.GeneratePhoto;
import org.rangiffler.jupiter.annotation.GenerateUser;
import org.rangiffler.jupiter.extension.ApiLoginExtension;
import org.rangiffler.jupiter.extension.GenerateUserExtension;
import org.rangiffler.model.CountryJson;
import org.rangiffler.model.PhotoJson;
import org.rangiffler.model.UserJson;
import org.rangiffler.page.DeletePhotoPage;
import org.rangiffler.page.PhotoPage;
import org.rangiffler.page.YourTravelsPage;
import org.rangiffler.page.component.HeaderComponent;
import org.rangiffler.test.web.BaseWebTest;

import static io.qameta.allure.Allure.step;

@DisplayName("[WEB] Ваши путешествия (Your travels). Редактирование")
@ExtendWith({GenerateUserExtension.class, ApiLoginExtension.class})
public class EditPhotoTest extends BaseWebTest {

    private HeaderComponent headerComponent = new HeaderComponent();

    @Test
    @AllureId("901")
    @ApiLogin(user = @GenerateUser
            (photos = @GeneratePhoto)
    )
    @Tag("WEB")
    @DisplayName("WEB: Пользователь должен иметь возможность обновить запись о путешествии")
    void updatePhoto(UserJson user) {
        final PhotoJson photo = user.getPhotos().get(0);
        PhotoJson updatedPhoto = new PhotoJson();
        updatedPhoto.setDescription("Update description");
        CountryJson updatedCountry = new CountryJson();
        updatedPhoto.setPhoto(photo.getPhoto());
        updatedCountry.setName("Zimbabwe");
        updatedCountry.setCode("zw");
        updatedPhoto.setCountryJson(updatedCountry);

        step(OPEN_MAIN_PAGE_STEP, () -> Selenide.open(CFG.getFrontUrl()));

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
    @Tag("WEB")
    @DisplayName("WEB: Пользователь должен иметь возможность удалить путешествие")
    void deletePhoto(UserJson user) {
        final PhotoJson photo = user.getPhotos().get(0);

        step(OPEN_MAIN_PAGE_STEP, () -> Selenide.open(CFG.getFrontUrl()));

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
