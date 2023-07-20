package org.rangiffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.rangiffler.condition.FriendsCondition;
import org.rangiffler.model.UserJson;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

public class FriendsPage extends BasePage<FriendsPage> {
    private final SelenideElement closeIcon = $("svg[data-testid='CloseIcon']");
    private final SelenideElement friendsTable = $("table[aria-label='friends table']");
    private final ElementsCollection friendsTableRows = $$("table[aria-label='friends table'] tr");
    private final SelenideElement noFriendsYetLabel = $x("//div[text()='No friends yet']");

    @Override
    public FriendsPage checkThatPageLoaded() {
        closeIcon.shouldBe(Condition.visible);
        return this;
    }

    @Step("Проверить, что сообщение 'No friends yet' отображается")
    public void checkNoFriendsYetIsVisible() {
        noFriendsYetLabel.shouldBe(Condition.visible);
    }

    @Step("Проверить, что в таблице отображаются друзья")
    public void checkTableContainsFriends(UserJson... friends) {
        friendsTable.findAll("tr").shouldHave(FriendsCondition.friends(friends));
    }

    @Step("Удалить друзей")
    public void deleteFriends(UserJson... friends) {
        for (UserJson friend : friends) {
            SelenideElement row = friendsTableRows.findBy(Condition.text(friend.getUsername()));
            row.find(By.cssSelector("button[aria-label='Remove friend']")).click();
            DeleteFriendPage deleteFriendPage = new DeleteFriendPage();
            deleteFriendPage.checkThatPageLoaded()
                    .deleteButtonClick();
        }
    }

}
