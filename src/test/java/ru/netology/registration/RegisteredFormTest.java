package ru.netology.registration;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class RegisteredFormTest {

    LocalDate currentDate = LocalDate.now();
    LocalDate futureDate = currentDate.plusDays(5);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    String formattedDate = futureDate.format(formatter);


    @Test
    public void shouldFillRegistrationForm() {
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id=city] input")).sendKeys("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(formattedDate);
        $(By.cssSelector("[data-test-id=name] input")).sendKeys("Бакулина Наталья");
        $(By.cssSelector("[data-test-id=phone] input")).sendKeys("+74952592741");
        $(By.cssSelector("[data-test-id=agreement]")).click();
        $(By.cssSelector(".button")).click();
        $(".notification__content").shouldHave((Condition.text("Встреча успешно забронирована на " + formattedDate)), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @Test
    public void shouldSelectIncorrectCity() {
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id=city] input")).sendKeys("Серпухов");
        $("[data-test-id=date] input").doubleClick().sendKeys(formattedDate);
        $(By.cssSelector("[data-test-id=name] input")).sendKeys("Бакулина Наталья");
        $(By.cssSelector("[data-test-id=phone] input")).sendKeys("+74952592741");
        $(By.cssSelector("[data-test-id=agreement]")).click();
        $(By.cssSelector(".button")).click();
        $(".input__sub").shouldHave((Condition.text("Доставка в выбранный город недоступна")));
    }

    @Test
    public void shouldSendUnfilledCity() {
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id=city] input")).sendKeys("");
        $("[data-test-id=date] input").doubleClick().sendKeys(formattedDate);
        $(By.cssSelector("[data-test-id=name] input")).sendKeys("Бакулина Наталья");
        $(By.cssSelector("[data-test-id=phone] input")).sendKeys("+74952592741");
        $(By.cssSelector("[data-test-id=agreement]")).click();
        $(By.cssSelector(".button")).click();
        $(".input__sub").shouldHave((Condition.text("Поле обязательно для заполнения")));
    }

    @Test
    public void shouldSendIncorrectDate() {
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id=city] input")).sendKeys("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys("27.06.2023");
        $(By.cssSelector("[data-test-id=name] input")).sendKeys("Бакулина Наталья");
        $(By.cssSelector("[data-test-id=phone] input")).sendKeys("+74952592741");
        $(By.cssSelector("[data-test-id=agreement]")).click();
        $(By.cssSelector(".button")).click();
        $(".calendar-input .input__sub").shouldHave((Condition.text("Заказ на выбранную дату невозможен")));
    }

    @Test
    public void shouldSendUnfilledDate() {
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id=city] input")).sendKeys("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $(By.cssSelector("[data-test-id=name] input")).sendKeys("Бакулина Наталья");
        $(By.cssSelector("[data-test-id=phone] input")).sendKeys("+74952592741");
        $(By.cssSelector("[data-test-id=agreement]")).click();
        $(By.cssSelector(".button")).click();
        $(".calendar-input .input__sub").shouldHave((Condition.text("Неверно введена дата")));
    }

    @Test
    public void shouldSendIncorrectName() {
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id=city] input")).sendKeys("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(formattedDate);
        $(By.cssSelector("[data-test-id=name] input")).sendKeys("Bakulina Natalia");
        $(By.cssSelector("[data-test-id=phone] input")).sendKeys("+74952592741");
        $(By.cssSelector("[data-test-id=agreement]")).click();
        $(By.cssSelector(".button")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave((Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")));
    }

    @Test
    public void shouldSendUnfilledName() {
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id=city] input")).sendKeys("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(formattedDate);
        $(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        $(By.cssSelector("[data-test-id=phone] input")).sendKeys("+74952592741");
        $(By.cssSelector("[data-test-id=agreement]")).click();
        $(By.cssSelector(".button")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave((Condition.text("Поле обязательно для заполнения")));
    }

    @Test
    public void shouldEnterIncorrectPhone() {
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id=city] input")).sendKeys("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(formattedDate);
        $(By.cssSelector("[data-test-id=name] input")).sendKeys("Бакулина Наталья");
        $(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7495259274134");
        $(By.cssSelector("[data-test-id=agreement]")).click();
        $(By.cssSelector(".button")).click();
        $(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldEnterUnfilledPhone() {
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id=city] input")).sendKeys("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(formattedDate);
        $(By.cssSelector("[data-test-id=name] input")).sendKeys("Бакулина Наталья");
        $(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        $(By.cssSelector("[data-test-id=agreement]")).click();
        $(By.cssSelector(".button")).click();
        $(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldDoNotClickCheckbox() {
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id=city] input")).sendKeys("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(formattedDate);
        $(By.cssSelector("[data-test-id=name] input")).sendKeys("Бакулина Наталья");
        $(By.cssSelector("[data-test-id=phone] input")).sendKeys("+74952592741");
        $(By.cssSelector(".button")).click();
        $(By.cssSelector("[data-test-id=agreement].input_invalid")).shouldBe(Condition.visible).shouldHave(Condition.text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

}
