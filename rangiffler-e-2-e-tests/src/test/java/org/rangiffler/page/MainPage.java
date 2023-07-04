package org.rangiffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage extends BasePage<MainPage> {
    private final SelenideElement header = $x("//h1");
    private final SelenideElement addPhotoButton = $x("//button[text()='Add photo']");
    private final SelenideElement profileEmptyIcon = $("svg[data-testid='PersonIcon']");
    private final SelenideElement zoomIcon = $("svg[data-testid='ZoomInIcon']");
    private final SelenideElement worldIcon = $("svg[data-testid='PublicIcon']");
    private final SelenideElement logoutButton = $("svg[data-testid='LogoutIcon']");
    private final SelenideElement yourVisitedCountries = $("div[aria-label='Your visited countries']");
    private final SelenideElement yourPhotos = $("div[aria-label='Your photos']");
    private final SelenideElement yourFriends = $("div[aria-label='Your friends']");


    @Override
    public MainPage checkThatPageLoaded() {
        header.shouldHave(text("Rangiffler"));
        return this;
    }

    @Step("Logout from rangiffler")
    public StartPage logout() {
        logoutButton.click();
        return new StartPage();
    }

    @Step("Check count your visited countries")
    public MainPage checkVisitedCountriesCount(int countCountry) {
        yourVisitedCountries.shouldHave(text(String.valueOf(countCountry)));
        return this;
    }

    @Step("Check count your photos")
    public MainPage checkPhotosCount(int countPhoto) {
        yourPhotos.shouldHave(text(String.valueOf(countPhoto)));
        return this;
    }

    @Step("Check count your friends")
    public MainPage checkFriendsCount(int countFriend) {
        yourFriends.shouldHave(text(String.valueOf(countFriend)));
        return this;
    }

    @Step("Click to profile empty icon")
    public ProfilePage clickEmptyPhotoProfileButton() {
        profileEmptyIcon.click();
        return new ProfilePage();
    }

    @Step("Click to profile photo")
    public ProfilePage clickProfileIcon(String username) {
        $("img[alt='" + username + "']").click();
        return new ProfilePage();
    }

    @Step("Click Add Photo")
    public AddPhotoPage clickAddPhoto() {
        addPhotoButton.click();
        return new AddPhotoPage();
    }

}
