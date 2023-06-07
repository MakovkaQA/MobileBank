package ru.netology.mobileBank.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.mobileBank.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PersonalAreaPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private SelenideElement firstCardButton = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] [data-test-id='action-deposit']");
    private SelenideElement secondCardButton = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] [data-test-id='action-deposit']");


    public PersonalAreaPage() {
        heading.shouldBe(visible);
    }

    public PutMoneyPage addMoneyForFirstCard() {
        firstCardButton.click();
        return new PutMoneyPage();
    }

    public PutMoneyPage addMoneyForSecondCard() {
        secondCardButton.click();
        return new PutMoneyPage();
    }

    public int getCardBalance(DataHelper.Card card) {
        String text = cards.findBy(Condition.text(card.getEncryptedNumber())).text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        int start = text.indexOf(balanceStart);
        int finish = text.indexOf(balanceFinish);
        String value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public int validFullAmount(int balance) {
        return balance;
    }

    public int validAmount(int balance) {
        return balance - 1;
    }

    public int invalidAmount(int balance) {
        return balance + 1;
    }

}
