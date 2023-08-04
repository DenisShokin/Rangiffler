package org.rangiffler.page.component;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.rangiffler.condition.PhotoCondition;
import org.rangiffler.condition.PhotosCondition;
import org.rangiffler.model.PhotoJson;
import org.rangiffler.page.BaseComponent;
import org.rangiffler.page.PhotoPage;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class ImagesListComponent extends BaseComponent<ImagesListComponent> {

    private final ElementsCollection photoListItems = $$x("//div/main//ul/li");
    private final SelenideElement photoList = $x("//div/main//ul");

    public ImagesListComponent() {
        super($x("//main//ul"));
    }

    @Override
    public ImagesListComponent checkThatComponentDisplayed() {
        self.shouldBe(visible);
        return this;
    }

    @Step("Проверить, что в таблице содержатся фотографии")
    public void checkImagesListContainsPhotos(PhotoJson... photos) {
        photoListItems.shouldHave(PhotosCondition.photos(photos));
    }

    @Step("Нажать на фотографию")
    public PhotoPage clickToImage(PhotoJson photo) {
        photoListItems.shouldHave(PhotoCondition.photo(photo)).first().click();
        return new PhotoPage();
    }

    @Step("Проверить, что таблица с изображениями скрыта")
    public void checkThatComponentIsHidden() {
        self.shouldBe(hidden);
    }

}
