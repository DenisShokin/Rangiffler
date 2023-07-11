package org.rangiffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class DeleteFriendPage extends BasePage<DeleteFriendPage>{

    private final SelenideElement deleteMessage = $x("//form//p");
    private final SelenideElement deleteBtn = $("button[type='submit']");

    @Override
    public DeleteFriendPage checkThatPageLoaded() {
        deleteMessage.shouldHave(Condition.text("Delete friend?"));
        return this;
    }

    @Step("Click Delete button")
    public void deleteButtonClick(){
        deleteBtn.click();
    }
}
