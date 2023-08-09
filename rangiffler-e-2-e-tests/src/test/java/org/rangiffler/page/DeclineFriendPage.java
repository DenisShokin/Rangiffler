package org.rangiffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class DeclineFriendPage extends BasePage<DeclineFriendPage>{

    private final SelenideElement declineMessage = $x("//form//p");
    private final SelenideElement declineBtn = $("button[type='submit']");

    @Override
    public DeclineFriendPage checkThatPageLoaded() {
        declineMessage.shouldHave(Condition.text("Decline friend?"));
        return this;
    }

    @Step("Нажать кнопку Отклонить приглашение")
    public void declineButtonClick(){
        declineBtn.click();
    }
}
