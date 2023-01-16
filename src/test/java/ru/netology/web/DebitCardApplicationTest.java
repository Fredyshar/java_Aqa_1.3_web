package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class DebitCardApplicationTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999/");
    }

    @Test
    void happyPath() {
        //Configuration.holdBrowserOpen = true;
        $("[data-test-id='name'] input").sendKeys("Иванов Иван");
        $("[data-test-id='phone'] input").sendKeys("+70001112233");
        $("[data-test-id='agreement']").click();
        $("button").click();
        $("[data-test-id='order-success']").shouldHave(Condition.text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.")).shouldBe(Condition.visible);
    }
//_______________________________________________________________
    /* Not happy Path:*/

    @Test
    void dontPressIAgree() {

        $("[data-test-id='name'] input").sendKeys("Иванов Иван");
        $("[data-test-id='phone'] input").sendKeys("+70001112233");
        $("button").click();
        $("[data-test-id='order-success'].input_invalid").shouldNotBe(Condition.visible);
    }

    @Test
    void noName() {
        $("[data-test-id='name'] input").sendKeys("");
        $("[data-test-id='phone'] input").sendKeys("+70001112233");
        $("[data-test-id='agreement']").click();
        $("button").click();
        $("[data-test-id='name'].input_invalid").shouldHave(Condition.text("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    void noPhone() {
        $("[data-test-id='name'] input").sendKeys("Иванов Иван");
        $("[data-test-id='phone'] input").sendKeys("");
        $("[data-test-id='agreement']").click();
        $("button").click();
        $("[data-test-id='phone'].input_invalid").shouldHave(Condition.text("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    void requirementsOnlyTheFirstFieldIsHighlighted() {
        $("[data-test-id='name'] input").sendKeys("Ivanov Ivan");
        $("[data-test-id='phone'] input").sendKeys("+754");
        $("[data-test-id='agreement']").click();
        $("button").click();
        $("[data-test-id='name'].input_invalid").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(Condition.visible);
    }

    /* Name validation */

    @Test
    void nameinEnglish() {
        $("[data-test-id='name'] input").sendKeys("Ivanov Ivan");
        $("[data-test-id='phone'] input").sendKeys("+70001112233");
        $("[data-test-id='agreement']").click();
        $("button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(Condition.visible);
    }

    @Test
    void nameThroughHyphen() {
        $("[data-test-id='name'] input").sendKeys("Иван-Веня Иванов");
        $("[data-test-id='phone'] input").sendKeys("+70001112233");
        $("[data-test-id='agreement']").click();
        $("button").click();
        $("[data-test-id='order-success']").shouldHave(Condition.text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.")).shouldBe(Condition.visible);
    }

    @Test
    void surnameThroughHyphen() {
        $("[data-test-id='name'] input").sendKeys("Иван Иванов-Петров");
        $("[data-test-id='phone'] input").sendKeys("+70001112233");
        $("[data-test-id='agreement']").click();
        $("button").click();
        $("[data-test-id='order-success']").shouldHave(Condition.text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.")).shouldBe(Condition.visible);
    }

    @Test
    void nameWithRareLetter() {
        $("[data-test-id='name'] input").sendKeys("Фёдор Иванов");
        $("[data-test-id='phone'] input").sendKeys("+70001112233");
        $("[data-test-id='agreement']").click();
        $("button").click();
        $("[data-test-id='order-success']").shouldHave(Condition.text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.")).shouldBe(Condition.visible);
    }

    @Test
    void notInNameSurname() {

        $("[data-test-id='name'] input").sendKeys("Иван");
        $("[data-test-id='phone'] input").sendKeys("+70001112233");
        $("[data-test-id='agreement']").click();
        $("button").click();
        $("[data-test-id='name'] input__sub").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(Condition.visible);
    }

    /* Phone validation */

    @Test
    void manyDigitsInPhoneNumber() {
        $("[data-test-id='name'] input").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] input").sendKeys("+700011122331");
        $("[data-test-id='agreement']").click();
        $("button").click();
        $("[data-test-id='phone'].input_invalid").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(Condition.visible);
    }

    @Test
    void fewDigitsInPhoneNumber() {
        $("[data-test-id='name'] input").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] input").sendKeys("+7");
        $("[data-test-id='agreement']").click();
        $("button").click();
        $("[data-test-id='phone'].input_invalid").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(Condition.visible);
    }

    @Test
    void noPlus() {
        $("[data-test-id='name'] input").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] input").sendKeys("80001112233");
        $("[data-test-id='agreement']").click();
        $("button").click();
        $("[data-test-id='phone'].input_invalid").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(Condition.visible);
    }

    @Test
    void letteronPhoneNumber() {
        $("[data-test-id='name'] input").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] input").sendKeys("+7000111223f");
        $("[data-test-id='agreement']").click();
        $("button").click();
        $("[data-test-id='phone'].input_invalid").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(Condition.visible);
    }

    @Test
    void phoneNumberBegginWrong() {
        $("[data-test-id='name'] input").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] input").sendKeys("+50001112233");
        $("[data-test-id='agreement']").click();
        $("button").click();
        $("[data-test-id='phone'].input_invalid").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(Condition.visible);
    }
}