package pages;

import core.BasePage;
import enums.AutomationAnswer;
import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class AutomateNowPage extends BasePage<AutomateNowPage> {

    @FindBy(id = "name-input")
    private WebElement nameInput;

    @FindBy(css = "#feedbackForm input[type='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//input[@type='checkbox' and @value='Milk']")
    private WebElement milkCheckBox;

    @FindBy(xpath = "//input[@type='checkbox' and @value='Coffee']")
    private WebElement coffeeCheckBox;

    @FindBy(css = "input[name='fav_color'][value='Yellow']")
    private WebElement yellowRadioBtn;

    @FindBy(id = "automation")
    private WebElement automationSelect;

    @FindBy(css = "input[name='email']")
    private WebElement emailInput;

    @FindBy(xpath = "//label[text()='Automation tools']/following-sibling::ul/li")
    private List<WebElement> automationTools;

    @FindBy(id = "message")
    private WebElement messageInput;

    @FindBy(xpath = "//button[text()='Submit']")
    private WebElement submitBtn;

    @Step("Ввести имя: {name}")
    public AutomateNowPage enterName(String name) {
        wait.until(ExpectedConditions.visibilityOf(nameInput));
        nameInput.clear();
        nameInput.sendKeys(name);
        return this;
    }

    @Step("Ввести пароль")
    public AutomateNowPage enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        passwordInput.clear();
        passwordInput.sendKeys(password);
        return this;
    }

    @Step("Выбрать любимые напитки: Milk и Coffee")
    public AutomateNowPage selectFavoriteDrinks() {
        if (!milkCheckBox.isSelected()) {
            milkCheckBox.click();
        }
        if (!coffeeCheckBox.isSelected()) {
            coffeeCheckBox.click();
        }
        return this;
    }

    @Step("Выбрать любимый цвет: Yellow")
    public AutomateNowPage selectFavoriteColor() {
        wait.until(ExpectedConditions.elementToBeClickable(yellowRadioBtn));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", yellowRadioBtn);

        if (!yellowRadioBtn.isSelected()) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();", yellowRadioBtn);
        }
        return this;
    }

    @Step("Выбрать случайное значение в поле Automation")
    public AutomateNowPage selectRandomAutomationValue() {
        Select select = new Select(automationSelect);
        AutomationAnswer[] answers = AutomationAnswer.values();
        AutomationAnswer random = answers[new Random().nextInt(answers.length)];
        select.selectByValue(random.getValue());
        return this;
    }

    @Step("Выбрать значение Automation: {answer}")
    public AutomateNowPage selectAutomationValue(AutomationAnswer answer) {
        Select select = new Select(automationSelect);
        select.selectByValue(answer.getValue());
        return this;
    }

    @Step("Ввести email: {email}")
    public AutomateNowPage enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
        return this;
    }

    @Step("Заполнить сообщение данными об инструментах автоматизации")
    public AutomateNowPage fillMessageWithAutomationToolsInfo() {
        wait.until(ExpectedConditions.visibilityOfAllElements(automationTools));
        int count = automationTools.size();
        String longestTool = "";
        int maxLength = 0;
        for (WebElement tool : automationTools) {
            String text = tool.getText();
            if (text.length() > maxLength) {
                maxLength = text.length();
                longestTool = text;
            }
        }
        String message = "Количество инструментов: " + count + ". Самый длинный инструмент по количеству символов: " + longestTool;
        messageInput.clear();
        messageInput.sendKeys(message);
        return this;
    }

    @Step("Нажать кнопку Submit")
    public AutomateNowPage clickSubmit() {
        wait.until(ExpectedConditions.visibilityOf(submitBtn));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", submitBtn
        );

        wait.until(ExpectedConditions.elementToBeClickable(submitBtn));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", submitBtn
        );
        return this;
    }


    @Step("Получить текст алерта и подтвердить его")
    public String getAlertTextAndAccept() {
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        String text = alert.getText();
        alert.accept();
        return text;
    }

    @Step("Проверить наличие алерта")
    public boolean isAlertPresent() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
