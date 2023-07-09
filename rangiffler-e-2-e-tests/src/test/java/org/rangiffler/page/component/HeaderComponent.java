package org.rangiffler.page.component;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.rangiffler.page.AddPhotoPage;
import org.rangiffler.page.BaseComponent;
import org.rangiffler.page.ProfilePage;
import org.rangiffler.page.StartPage;
import org.rangiffler.page.YourTravelsPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class HeaderComponent extends BaseComponent<HeaderComponent> {

    public HeaderComponent() {
        super($x("//h1"));
    }
    private final SelenideElement yourTravelsButton = $x("//button[text()='Your travels']");
    private final SelenideElement friendsTravelsButton = $x("//button[text()='Friends travels']");
    private final SelenideElement peopleAroundButton = $x("//button[text()='People Around']");
    private final SelenideElement addPhotoButton = $x("//button[text()='Add photo']");
    private final SelenideElement profileEmptyIcon = $("svg[data-testid='PersonIcon']");
    private final SelenideElement logoutButton = $("svg[data-testid='LogoutIcon']");
    private final SelenideElement yourVisitedCountries = $("div[aria-label='Your visited countries']");
    private final SelenideElement yourPhotos = $("div[aria-label='Your photos']");
    private final SelenideElement yourFriends = $("div[aria-label='Your friends']");

    @Override
    public HeaderComponent checkThatComponentDisplayed() {
        self.shouldHave(text("Rangiffler"));
        logoutButton.shouldBe(visible);
        Selenide.sleep(3000);
        return this;
    }

    public YourTravelsPage goToYourTravelsPage() {
        yourTravelsButton.click();
        return new YourTravelsPage();
    }

//    public YourTravelsPage goToFriendsTravelsPage() {
//        friendsTravelsButton.click();
//        return new YourTravelsPage();
//    }
//
//    public YourTravelsPage goToPeopleAroundPage() {
//        peopleAroundButton.click();
//        return new YourTravelsPage();
//    }

    @Step("Logout from rangiffler")
    public StartPage logout() {
        logoutButton.click();
        return new StartPage();
    }

    @Step("Check count your visited countries")
    public HeaderComponent checkVisitedCountriesCount(int countCountry) {
        yourVisitedCountries.shouldHave(text(String.valueOf(countCountry)));
        return this;
    }

    @Step("Check count your photos")
    public HeaderComponent checkPhotosCount(int countPhoto) {
        yourPhotos.shouldHave(text(String.valueOf(countPhoto)));
        return this;
    }

    @Step("Check count your friends")
    public HeaderComponent checkFriendsCount(int countFriend) {
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
