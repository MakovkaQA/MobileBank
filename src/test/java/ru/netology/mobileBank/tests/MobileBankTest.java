package ru.netology.mobileBank.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.mobileBank.data.DataHelper;
import ru.netology.mobileBank.pages.CodeEntryPage;
import ru.netology.mobileBank.pages.LoginPage;
import ru.netology.mobileBank.pages.PersonalAreaPage;

import static com.codeborne.selenide.Selenide.open;

public class MobileBankTest {

    LoginPage loginPage;
    PersonalAreaPage personalAreaPage;

    @BeforeEach
    void setup() {
        //Configuration.holdBrowserOpen = true;
        loginPage = open("http://localhost:9999", LoginPage.class);
        DataHelper.AuthInfo authInfo = DataHelper.getAuthInfo();
        CodeEntryPage codeEntry = loginPage.validLogin(authInfo);
        DataHelper.VerificationCode verificationCode = DataHelper.getVerificationCode(authInfo);
        personalAreaPage = codeEntry.validVerify(verificationCode);
    }

    @Test
    public void validTransferFullSumFrom2To1CardTest() {
        DataHelper.Card firstCard = DataHelper.getFirstCard();
        DataHelper.Card secondCard = DataHelper.getSecondCard();
        int amount = personalAreaPage.validFullAmount(personalAreaPage.getCardBalance(secondCard));
        Integer expectedFirstCardBalance = personalAreaPage.getCardBalance(firstCard) + amount;
        Integer expectedSecondCardBalance = personalAreaPage.getCardBalance(secondCard) - amount;
        var putMoneyPage = personalAreaPage.addMoneyForFirstCard();
        personalAreaPage = putMoneyPage.validTransfer(amount, secondCard, firstCard);

        Integer actualFirstCardBalance = personalAreaPage.getCardBalance(firstCard);
        Integer actualSecondCardBalance = personalAreaPage.getCardBalance(secondCard);

        Assertions.assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    public void validTransferFullSumFrom1To2CardTest() {
        DataHelper.Card firstCard = DataHelper.getFirstCard();
        DataHelper.Card secondCard = DataHelper.getSecondCard();
        int amount = personalAreaPage.validFullAmount(personalAreaPage.getCardBalance(firstCard));
        Integer expectedFirstCardBalance = personalAreaPage.getCardBalance(firstCard) - amount;
        Integer expectedSecondCardBalance = personalAreaPage.getCardBalance(secondCard) + amount;
        var putMoneyPage = personalAreaPage.addMoneyForSecondCard();
        personalAreaPage = putMoneyPage.validTransfer(amount, firstCard, secondCard);

        Integer actualFirstCardBalance = personalAreaPage.getCardBalance(firstCard);
        Integer actualSecondCardBalance = personalAreaPage.getCardBalance(secondCard);

        Assertions.assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    public void validTransferFrom2To1CardTest() {
        DataHelper.Card firstCard = DataHelper.getFirstCard();
        DataHelper.Card secondCard = DataHelper.getSecondCard();
        int amount = personalAreaPage.validAmount(personalAreaPage.getCardBalance(secondCard));
        Integer expectedFirstCardBalance = personalAreaPage.getCardBalance(firstCard) + amount;
        Integer expectedSecondCardBalance = personalAreaPage.getCardBalance(secondCard) - amount;
        var putMoneyPage = personalAreaPage.addMoneyForFirstCard();
        personalAreaPage = putMoneyPage.validTransfer(amount, secondCard, firstCard);

        Integer actualFirstCardBalance = personalAreaPage.getCardBalance(firstCard);
        Integer actualSecondCardBalance = personalAreaPage.getCardBalance(secondCard);

        Assertions.assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    public void validTransferFrom1To2CardTest() {
        DataHelper.Card firstCard = DataHelper.getFirstCard();
        DataHelper.Card secondCard = DataHelper.getSecondCard();
        int amount = personalAreaPage.validAmount(personalAreaPage.getCardBalance(firstCard));
        Integer expectedFirstCardBalance = personalAreaPage.getCardBalance(firstCard) - amount;
        Integer expectedSecondCardBalance = personalAreaPage.getCardBalance(secondCard) + amount;
        var putMoneyPage = personalAreaPage.addMoneyForSecondCard();
        putMoneyPage.validTransfer(amount, firstCard, secondCard);

        Integer actualFirstCardBalance = personalAreaPage.getCardBalance(firstCard);
        Integer actualSecondCardBalance = personalAreaPage.getCardBalance(secondCard);

        Assertions.assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    public void invalidTransferFrom2To1CardTest() {
        DataHelper.Card firstCard = DataHelper.getFirstCard();
        DataHelper.Card secondCard = DataHelper.getSecondCard();
        int amount = personalAreaPage.invalidAmount(personalAreaPage.getCardBalance(secondCard));
        var putMoneyPage = personalAreaPage.addMoneyForFirstCard();
        putMoneyPage.transferWithError(amount, secondCard, firstCard);
    }

    @Test
    public void invalidTransferFrom1To2CardTest() {
        DataHelper.Card firstCard = DataHelper.getFirstCard();
        DataHelper.Card secondCard = DataHelper.getSecondCard();
        int amount = personalAreaPage.invalidAmount(personalAreaPage.getCardBalance(firstCard));
        var putMoneyPage = personalAreaPage.addMoneyForSecondCard();
        putMoneyPage.transferWithError(amount, firstCard, secondCard);
    }

    @Test
    public void invalidNullTransferFrom2To1CardTest() {
        DataHelper.Card firstCard = DataHelper.getFirstCard();
        DataHelper.Card secondCard = DataHelper.getSecondCard();
        var putMoneyPage = personalAreaPage.addMoneyForFirstCard();
        putMoneyPage.transferWithError(0, secondCard, firstCard);
    }

    @Test
    public void invalidNullTransferFrom1To2CardTest() {
        DataHelper.Card firstCard = DataHelper.getFirstCard();
        DataHelper.Card secondCard = DataHelper.getSecondCard();
        var putMoneyPage = personalAreaPage.addMoneyForSecondCard();
        putMoneyPage.transferWithError(0, firstCard, secondCard);
    }

    @Test
    public void invalidTransferWithoutData() {
        var putMoneyPage = personalAreaPage.addMoneyForFirstCard();
        putMoneyPage.transferWithoutData();
    }

    @Test
    public void cancelTransferWithoutDataTest() {
        var putMoneyPage = personalAreaPage.addMoneyForSecondCard();
        putMoneyPage.cancelTransferWithoutData();
    }

    @Test
    public void cancelTransferTest() {
        DataHelper.Card firstCard = DataHelper.getFirstCard();
        DataHelper.Card secondCard = DataHelper.getSecondCard();
        int amount = personalAreaPage.validAmount(personalAreaPage.getCardBalance(firstCard));
        var putMoneyPage = personalAreaPage.addMoneyForSecondCard();
        putMoneyPage.cancelTransfer(amount, firstCard, secondCard);
    }
}
