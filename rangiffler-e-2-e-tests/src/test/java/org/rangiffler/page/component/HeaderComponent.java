package org.rangiffler.page.component;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.rangiffler.page.BaseComponent;
import org.rangiffler.page.FriendsPage;
import org.rangiffler.page.FriendsTravelsPage;
import org.rangiffler.page.PeopleAroundPage;
import org.rangiffler.page.PhotoPage;
import org.rangiffler.page.ProfilePage;
import org.rangiffler.page.StartPage;
import org.rangiffler.page.YourTravelsPage;

import static com.codeborne.selenide.Condition.attributeMatching;
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

    @Step("Go to your travels page")
    public YourTravelsPage goToYourTravelsPage() {
        yourTravelsButton.click();
        yourTravelsButton.shouldHave(attributeMatching("aria-selected","true"));
        return new YourTravelsPage();
    }

    @Step("Go to friends travels page")
    public FriendsTravelsPage goToFriendsTravelsPage() {
        friendsTravelsButton.click();
        friendsTravelsButton.shouldHave(attributeMatching("aria-selected","true"));
        return new FriendsTravelsPage();
    }

    @Step("Go to people around page")
    public PeopleAroundPage goToPeopleAroundPage() {
        peopleAroundButton.click();
        peopleAroundButton.shouldHave(attributeMatching("aria-selected","true"));
        return new PeopleAroundPage();
    }

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
        $(String.format("img[alt='%s']", username)).click();
        return new ProfilePage();
    }

    @Step("Click Add Photo")
    public PhotoPage clickAddPhoto() {
        addPhotoButton.click();
        return new PhotoPage();
    }

    @Step("Click Your friends button")
    public FriendsPage clickFriendsButton() {
        yourFriends.click();
        return new FriendsPage();
    }

    @Step("Refresh")
    public HeaderComponent refresh() {
        Selenide.refresh();
        checkThatComponentDisplayed();
        return this;
    }

}
