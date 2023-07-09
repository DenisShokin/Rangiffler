package org.rangiffler.page.component;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;
import org.rangiffler.condition.PhotosCondition;
import org.rangiffler.model.PhotoJson;
import org.rangiffler.page.BaseComponent;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class ImagesListComponent extends BaseComponent<ImagesListComponent> {

    private final ElementsCollection photoListItems = $$x("//div/main//ul/li");

    public ImagesListComponent() {
        super($x("//main//ul"));
    }

    @Override
    public ImagesListComponent checkThatComponentDisplayed() {
        self.shouldBe(Condition.visible);
        return this;
    }

    @Step("Check that images list contains photos")
    public void checkImagesListContainsPhotos(PhotoJson... photos) {
        photoListItems.shouldHave(PhotosCondition.photos(photos));
    }

}
