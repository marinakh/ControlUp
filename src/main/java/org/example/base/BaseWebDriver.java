package org.example.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;

import java.time.Duration;

public class BaseWebDriver {

    @Getter
    private final WebDriver webDriver;

    public BaseWebDriver(){
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();

        // For the issue of SocketException connection reset
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));

        webDriver.manage().window().fullscreen();
    }

    /**
     * Quits the driver, closing every associated window
     */
    public void quitDriver(){
        webDriver.quit();
    }

}
