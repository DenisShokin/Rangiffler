package org.rangiffler.page;

import org.rangiffler.page.component.HeaderComponent;
import org.rangiffler.page.component.ImagesListComponent;
import org.rangiffler.page.component.WorldMapComponent;

public class YourTravelsPage extends BasePage<YourTravelsPage> {

    private final HeaderComponent headerComponent = new HeaderComponent();
    private final WorldMapComponent worldMapComponent = new WorldMapComponent();
    private final ImagesListComponent imagesListComponent = new ImagesListComponent();

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

}
