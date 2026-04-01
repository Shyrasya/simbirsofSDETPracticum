package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            WebDriverManager.getInstance(DriverManagerType.CHROME).setup();

            ChromeOptions options = new ChromeOptions();
            options.setPageLoadStrategy(PageLoadStrategy.EAGER);

            options.addArguments(
                    "--host-resolver-rules=" +
                            "MAP pagead2.googlesyndication.com 127.0.0.1," +
                            "MAP googleads.g.doubleclick.net 127.0.0.1," +
                            "MAP www.googleadservices.com 127.0.0.1," +
                            "MAP fonts.googleapis.com 127.0.0.1"
            );

            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
