package org.example.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.util.Optional;

public class BaseTest {
    protected BaseWebDriver baseWebDriver;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        createWebDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(Method method) {
        closeBrowser();
    }

    /**
     * Call this method when we need to run Web UI in our test method
     * @return The WebDriver
     */
    protected WebDriver createWebDriver() {
        closeBrowser();
        baseWebDriver = new BaseWebDriver();
        return baseWebDriver.getWebDriver();
    }

    private void closeBrowser() {
        Optional.ofNullable(baseWebDriver)
                .ifPresent(BaseWebDriver::quitDriver);
        baseWebDriver = null;
    }
}
