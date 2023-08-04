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

    @Step("Перейти на страницу Ваши путешествия")
    public YourTravelsPage goToYourTravelsPage() {
        yourTravelsButton.click();
        yourTravelsButton.shouldHave(attributeMatching("aria-selected","true"));
        return new YourTravelsPage();
    }

    @Step("Перейти на страницу Путешествия друзей")
    public FriendsTravelsPage goToFriendsTravelsPage() {
        friendsTravelsButton.click();
        friendsTravelsButton.shouldHave(attributeMatching("aria-selected","true"));
        return new FriendsTravelsPage();
    }

    @Step("Перейти на страницу Люди вокруг")
    public PeopleAroundPage goToPeopleAroundPage() {
        peopleAroundButton.click();
        peopleAroundButton.shouldHave(attributeMatching("aria-selected","true"));
        return new PeopleAroundPage();
    }

    @Step("Нажать кнопку выхода из системы")
    public StartPage logout() {
        logoutButton.click();
        return new StartPage();
    }

    @Step("Проверить количество посещенных стран")
    public HeaderComponent checkVisitedCountriesCount(int countCountry) {
        yourVisitedCountries.shouldHave(text(String.valueOf(countCountry)));
        return this;
    }

    @Step("Проверить количество фотографий")
    public HeaderComponent checkPhotosCount(int countPhoto) {
        yourPhotos.shouldHave(text(String.valueOf(countPhoto)));
        return this;
    }

    @Step("Проверить количество друзей")
    public HeaderComponent checkFriendsCount(int countFriend) {
        yourFriends.shouldHave(text(String.valueOf(countFriend)));
        return this;
    }

    @Step("Нажать на профиль пользователя")
    public ProfilePage clickEmptyPhotoProfileButton() {
        profileEmptyIcon.click();
        return new ProfilePage();
    }

    @Step("Нажать на аватар профиля")
    public ProfilePage clickProfileIcon(String username) {
        $(String.format("img[alt='%s']", username)).click();
        return new ProfilePage();
    }

    @Step("Нажать кнопку Добавления фото")
    public PhotoPage clickAddPhoto() {
        addPhotoButton.click();
        return new PhotoPage();
    }

    @Step("Нажать на кнопку Друзья")
    public FriendsPage clickFriendsButton() {
        yourFriends.click();
        return new FriendsPage();
    }

    @Step("Обновить страницу")
    public HeaderComponent refresh() {
        Selenide.refresh();
        checkThatComponentDisplayed();
        return this;
    }

}
