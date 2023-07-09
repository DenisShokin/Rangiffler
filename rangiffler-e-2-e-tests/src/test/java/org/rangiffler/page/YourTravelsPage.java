package org.rangiffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.rangiffler.condition.PhotosCondition;
import org.rangiffler.model.PhotoJson;
import org.rangiffler.page.component.HeaderComponent;
import org.rangiffler.page.component.ImagesListComponent;
import org.rangiffler.page.component.WorldMapComponent;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class YourTravelsPage extends BasePage<YourTravelsPage> {

    private final HeaderComponent headerComponent = new HeaderComponent();
    private final WorldMapComponent worldMapComponent = new WorldMapComponent();
    private final ImagesListComponent imagesListComponent = new ImagesListComponent();
    private final SelenideElement photoList = $x("//div/main//ul");
    private final ElementsCollection photoListItems = $$x("//div/main//ul/li");

    public WorldMapComponent getWorldMap() {
        return worldMapComponent;
    }

    public ImagesListComponent getImagesList() {
        return imagesListComponent;
    }

    public HeaderComponent getHeader() {
        return headerComponent;
    }

    @Override
    public YourTravelsPage checkThatPageLoaded() {
        worldMapComponent.checkThatComponentDisplayed();
        return this;
    }

    public void checkImagesListContainsPhotos(PhotoJson...photos) {
        photoListItems.shouldHave(PhotosCondition.photos(photos));
    }

}
