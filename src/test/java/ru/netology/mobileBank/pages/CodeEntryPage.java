package ru.netology.mobileBank.pages;

import com.codeborne.selenide.SelenideElement;
import ru.netology.mobileBank.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CodeEntryPage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");

    public CodeEntryPage() {
        codeField.shouldBe(visible);
    }

    public PersonalAreaPage validVerify(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        return new PersonalAreaPage();
    }


}
