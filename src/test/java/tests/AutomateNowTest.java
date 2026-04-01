package tests;

import core.DriverFactory;
import enums.AutomationAnswer;
import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.AutomateNowPage;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AutomateNowTest {

    private AutomateNowPage page;

    @BeforeEach
    void setUp() {
        page = new AutomateNowPage();
        page.open("https://practice-automation.com/form-fields/");
    }

    @Test
    @Description("Отправка валидной формы. ОР: отображается алерт об успешной отправке")
    void submitValidForm_showsSuccessAlertMessage() {
        step("Заполнить форму валидными данными", () -> {
            page.enterName("Test")
                    .enterPassword("qwerty")
                    .selectFavoriteDrinks()
                    .selectFavoriteColor()
                    .selectAutomationValue(AutomationAnswer.YES)
                    .enterEmail("test@example.com")
                    .fillMessageWithAutomationToolsInfo()
                    .clickSubmit();
        });

        step("Проверить текст алерта об успешной отправке", () -> {
            String alertText = page.getAlertTextAndAccept();
            assertEquals("Message received!", alertText,
                    "Текст алерта должен соответствовать ожидаемому");
        });
    }

    @Test
    @Description("Имя в форме не указано. ОР - алерт с текстом \"Message received!\" отсутствует")
    void submitForm_withoutName_alertIsNotShown() {
        step("Заполнить форму без имени", () -> {
            page.enterPassword("qwerty")
                    .selectFavoriteDrinks()
                    .selectFavoriteColor()
                    .selectAutomationValue(AutomationAnswer.YES)
                    .enterEmail("test@example.com")
                    .fillMessageWithAutomationToolsInfo()
                    .clickSubmit();
        });

        step("Проверить, что алерт не появился", () ->
                assertFalse(page.isAlertPresent(),
                        "Алерт не должен появляться при незаполненном поле Name")
        );
    }

    @AfterEach
    void tearDown() {
        DriverFactory.quitDriver();
    }
}
