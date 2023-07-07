package org.rangiffler.test.web.travel;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.rangiffler.jupiter.annotation.GenerateUserAuthAndApiLogin;
import org.rangiffler.jupiter.extension.GenerateUserAuthAndApiLoginExtension;
import org.rangiffler.page.AddPhotoPage;
import org.rangiffler.page.MainPage;
import org.rangiffler.test.web.BaseWebTest;

@ExtendWith(GenerateUserAuthAndApiLoginExtension.class)
@DisplayName("Add travel photo")
@Execution(ExecutionMode.SAME_THREAD)
public class AddNewPhotoTest extends BaseWebTest {

    private MainPage mainPage = new MainPage();
    private AddPhotoPage addPhotoPage = new AddPhotoPage();
    private static final String TEST_PWD = "123456";

    @GenerateUserAuthAndApiLogin(password = TEST_PWD)
    @AllureId("701")
    @ParameterizedTest(name = "add photo with extension - {0}")
    @ValueSource(strings =
            {"src/test/resources/testdata/country/china_1.jfif",
    "src/test/resources/testdata/country/china_1.png",
            "src/test/resources/testdata/country/china_1.gif"})
    void addPhotoWithDifferentExtension(String imagePath) {
        Allure.step("open page", () -> Selenide.open(CFG.getFrontUrl()));
        addPhotoPage =
                mainPage.checkThatPageLoaded()
                .checkVisitedCountriesCount(0)
                .checkPhotosCount(0)
                .clickAddPhoto();
        mainPage = addPhotoPage
                .checkThatPageLoaded()
                .uploadPhoto(imagePath)
                .setDescription("Description for photo")
                .selectCountry("China")
                .save();
        mainPage.checkThatPageLoaded()
                .checkVisitedCountriesCount(1)
                .checkPhotosCount(1)
                .checkPhotoListIsVisible()
                .checkPhotosCountInList(1);
    }

//
//    @Test
//    void addTwoPhotoTravelForOneCountry() {
//
//    }
//
//    @Test
//    void addPhotosForTwoCountry() {
//
//    }
//
//    @Test
//    void updatePhoto() {
//
//    }
//
//    @Test
//    void deletePhoto() {
//
//    }

}
