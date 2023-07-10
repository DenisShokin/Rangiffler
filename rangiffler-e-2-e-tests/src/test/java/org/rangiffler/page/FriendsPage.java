package org.rangiffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.rangiffler.condition.FriendsCondition;
import org.rangiffler.model.UserJson;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class FriendsPage extends BasePage<FriendsPage> {
    private final SelenideElement closeIcon = $("svg[data-testid='CloseIcon']");
    private final SelenideElement friendsTable = $("table[aria-label='friends table']");
    private final SelenideElement friendsTableItem = $("table[aria-label='friends table'] tr");
    private final SelenideElement noFriendsYetLabel = $x("//div[text()='No friends yet']");

    @Override
    public FriendsPage checkThatPageLoaded() {
        closeIcon.shouldBe(Condition.visible);
        return this;
    }

    @Step("Check message 'No friends yet' is visible")
    public void checkNoFriendsYetIsVisible() {
        noFriendsYetLabel.shouldBe(Condition.visible);
    }

    @Step("Check that friends table contains friends")
    public void checkTableContainsFriends(UserJson... friends) {
        friendsTable.findAll("tr").shouldHave(FriendsCondition.friends(friends));
    }

}
