package org.rangiffler.page.component;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.rangiffler.page.BaseComponent;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class WorldMapComponent extends BaseComponent<WorldMapComponent> {

    private final SelenideElement zoomIcon = $("svg[data-testid='ZoomInIcon']");
    private final SelenideElement worldIcon = $("svg[data-testid='PublicIcon']");

    public WorldMapComponent() {
        super($x("//figure[@class='worldmap__figure-container']"));
    }

    @Override
    public WorldMapComponent checkThatComponentDisplayed() {
        self.shouldBe(Condition.visible);
        zoomIcon.shouldBe(Condition.visible);
        return this;
    }
}
