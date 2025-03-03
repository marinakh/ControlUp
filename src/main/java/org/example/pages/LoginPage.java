package org.example.pages;

import org.example.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    //Method to enter username
    public void enterUsername(String user) {
        WebElement userName = webDriver.findElement(By.id("user-name"));
        userName.sendKeys(user);
    }

    //Method to enter password
    public void enterPassword(String pass) {
        WebElement password = webDriver.findElement(By.id("password"));
        password.sendKeys(pass);
    }

    //Method to click on Login button
    public InventoryPage clickLogin() {
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        loginButton.click();
        return new InventoryPage(webDriver);
    }
}
