package ru.netology.mobileBank.pages;

import com.codeborne.selenide.SelenideElement;
import ru.netology.mobileBank.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class PutMoneyPage {
    private SelenideElement header = $(byText("Пополнение карты"));
    private SelenideElement amount = $("[data-test-id=amount] input");
    private SelenideElement from = $("[data-test-id=from] input");
    private SelenideElement to = $("[data-test-id=to] input");
    private SelenideElement transfer = $("[data-test-id='action-transfer']");
    private SelenideElement cancel = $("[data-test-id='action-cancel']");
    private SelenideElement error = $("[data-test-id='error-notification'] .notification__title");

    public PutMoneyPage() {
        header.shouldBe(visible);
    }

    public void enterData(int amountOfMoney, DataHelper.Card cardFrom, DataHelper.Card cardTo) {
        amount.setValue(String.valueOf(amountOfMoney));
        from.setValue(cardFrom.getNumber());
        to.shouldHave(value(cardTo.getEncryptedNumber())).shouldBe(disabled);
    }

    public void transfer() {
        transfer.click();
    }

    public PersonalAreaPage validTransfer(int amountOfMoney, DataHelper.Card cardFrom, DataHelper.Card cardTo) {
        enterData(amountOfMoney, cardFrom, cardTo);
        transfer();
        return new PersonalAreaPage();
    }

    public void error() {
        error.shouldBe(visible, Duration.ofSeconds(10))
                .shouldHave(exactText("Ошибка"));
    }

    public void transferWithError(int amountOfMoney, DataHelper.Card cardFrom, DataHelper.Card cardTo) {
        enterData(amountOfMoney, cardFrom, cardTo);
        transfer();
        error();
    }

    public void transferWithoutData() {
        transfer();
        error();
    }

    public PersonalAreaPage cancelTransferWithoutData() {
        cancel.click();
        return new PersonalAreaPage();
    }

    public void cancelTransfer(int amountOfMoney, DataHelper.Card cardFrom, DataHelper.Card cardTo) {
        enterData(amountOfMoney, cardFrom, cardTo);
        cancelTransferWithoutData();
    }
}
