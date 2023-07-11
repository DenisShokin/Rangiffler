package org.rangiffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.rangiffler.model.UserJson;

import static com.codeborne.selenide.Selenide.$;

public class PeopleAroundPage extends BasePage<PeopleAroundPage> {

    private final SelenideElement allPeopleTable = $("table[aria-label='all people table']");

    @Override
    public PeopleAroundPage checkThatPageLoaded() {
        allPeopleTable.shouldBe(Condition.visible);
        return this;
    }

    @Step("Remove friends")
    public PeopleAroundPage deleteFriends(UserJson... friends) {
        for (UserJson friend : friends) {
            SelenideElement row = allPeopleTable.findAll("tr").findBy(Condition.text(friend.getUsername()));
            row.find(By.cssSelector("button[aria-label='Remove friend']")).click();
            DeleteFriendPage deleteFriendPage = new DeleteFriendPage();
            deleteFriendPage.checkThatPageLoaded()
                    .deleteButtonClick();
        }
        return new PeopleAroundPage();
    }

}
