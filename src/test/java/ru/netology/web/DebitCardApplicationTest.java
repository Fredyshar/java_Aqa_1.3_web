package ru.netology.web;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class DebitCardApplicationTest {
    @Test
    void happyPath() {
        open("http://localhost:9999/");
        $("[data-test-id='name'] input").sendKeys("Иванов Иван");
        $("[data-test-id='phone'] input").sendKeys("+70001112233");
        $("[data-test-id='agreement']").click();
        $("button").click();
        $("[data-test-id='order-success']").shouldHave(Condition.text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }
//_______________________________________________________________
    /* Not happy Path:*/

    @Test
    void dontPressIAgree() {
        open("http://localhost:9999/");
        $("[data-test-id='name'] input").sendKeys("Иванов Иван");
        $("[data-test-id='phone'] input").sendKeys("+70001112233");
        $("button").click();
        $("[data-test-id='order-success'].input_invalid").isDisplayed();
    }

    @Test
    void noName() {
        open("http://localhost:9999/");
        $("[data-test-id='name'] input").sendKeys("");
        $("[data-test-id='phone'] input").sendKeys("+70001112233");
        $("[data-test-id='agreement']").click();
        $("button").click();
        $("[data-test-id='name'].input_invalid").isDisplayed();
    }

    @Test
    void noPhone() {
        open("http://localhost:9999/");
        $("[data-test-id='name'] input").sendKeys("Иванов Иван");
        $("[data-test-id='phone'] input").sendKeys("");
        $("[data-test-id='agreement']").click();
        $("button").click();
        $("[data-test-id='phone'].input_invalid").isDisplayed();
    }
    @Test
    void requirementsOnlyTheFirstFieldIsHighlighted() {
        open("http://localhost:9999/");
        $("[data-test-id='name'] input").sendKeys("Ivanov Ivan");
        $("[data-test-id='phone'] input").sendKeys("+754");
        $("[data-test-id='agreement']").click();
        $("button").click();
        $("[data-test-id='name'].input_invalid").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        $("[data-test-id='phone'].input_invalid").shouldNotBe();
    }

    /* Name validation */

    @Test
    void nameinEnglish() {
        open("http://localhost:9999/");
        $("[data-test-id='name'] input").sendKeys("Ivanov Ivan");
        $("[data-test-id='phone'] input").sendKeys("+70001112233");
        $("[data-test-id='agreement']").click();
        $("button").click();
        $("[data-test-id='name'].input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void nameThroughHyphen() {
        open("http://localhost:9999/");
        $("[data-test-id='name'] input").sendKeys("Иван-Веня Иванов");
        $("[data-test-id='phone'] input").sendKeys("+70001112233");
        $("[data-test-id='agreement']").click();
        $("button").click();
        $("[data-test-id='order-success']").shouldHave(Condition.text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }
    @Test
    void surnameThroughHyphen() {
        open("http://localhost:9999/");
        $("[data-test-id='name'] input").sendKeys("Иван Иванов-Петров");
        $("[data-test-id='phone'] input").sendKeys("+70001112233");
        $("[data-test-id='agreement']").click();
        $("button").click();
        $("[data-test-id='order-success']").shouldHave(Condition.text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

//    @Test
//    void nameWithRareLetter() {
//        open("http://localhost:9999/");
//        $("[data-test-id='name'] input").sendKeys("Фёдор Иванов");
//        $("[data-test-id='phone'] input").sendKeys("+70001112233");
//        $("[data-test-id='agreement']").click();
//        $("button").click();
//        $("[data-test-id='order-success']").shouldHave(Condition.text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));

//    }
//    @Test
//    void notInNameSurname() {
//        open("http://localhost:9999/");
//        $("[data-test-id='name'] input").sendKeys("Иван");
//        $("[data-test-id='phone'] input").sendKeys("+70001112233");
//        $("[data-test-id='agreement']").click();
//        $("button").click();
//        $("[data-test-id='name'] input__sub").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
//
//
//    }

    /* Phone validation */

    @Test
    void manyDigitsInPhoneNumber() {
        open("http://localhost:9999/");
        $("[data-test-id='name'] input").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] input").sendKeys("+700011122331");
        $("[data-test-id='agreement']").click();
        $("button").click();
        $("[data-test-id='phone'].input_invalid").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void fewDigitsInPhoneNumber() {
        open("http://localhost:9999/");
        $("[data-test-id='name'] input").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] input").sendKeys("+7");
        $("[data-test-id='agreement']").click();
        $("button").click();
        $("[data-test-id='phone'].input_invalid").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
    @Test
    void noPlus() {
        open("http://localhost:9999/");
        $("[data-test-id='name'] input").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] input").sendKeys("80001112233");
        $("[data-test-id='agreement']").click();
        $("button").click();
        $("[data-test-id='phone'].input_invalid").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
    @Test
    void letteronPhoneNumber() {
        open("http://localhost:9999/");
        $("[data-test-id='name'] input").sendKeys("Иван Иванов");
        $("[data-test-id='phone'] input").sendKeys("+7000111223f");
        $("[data-test-id='agreement']").click();
        $("button").click();
        $("[data-test-id='phone'].input_invalid").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

//    @Test
//    void phoneNumberBegginWrong() {
//        open("http://localhost:9999/");
//        $("[data-test-id='name'] input").sendKeys("Иван Иванов");
//        $("[data-test-id='phone'] input").sendKeys("+50001112233");
//        $("[data-test-id='agreement']").click();
//        $("button").click();
//        $("[data-test-id='phone'].input_invalid").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
//    }
}