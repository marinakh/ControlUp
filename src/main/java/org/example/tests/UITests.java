package org.example.tests;

import org.example.base.BaseTest;
import org.example.pages.InventoryPage;
import org.example.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UITests extends BaseTest {

    @Test
    public void VerifyInventoryItems() {
        WebDriver driver = baseWebDriver.getWebDriver();

        // navigate to
        driver.get("https://www.saucedemo.com");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        InventoryPage inventoryPage = loginPage.clickLogin();
        int numOfItems = inventoryPage.getInventoryItemsSize();
        Assert.assertEquals(numOfItems, 6, "Wrong number of inventory items");
    }

    @Test
    public void AddItemToCart() {
        WebDriver driver = baseWebDriver.getWebDriver();

        // navigate to
        driver.get("https://www.saucedemo.com");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        InventoryPage inventoryPage = loginPage.clickLogin();
        inventoryPage.addFirstInventoryItemToCart();
        int cardBadgeDisplay = inventoryPage.getCartBadgeSize();
        Assert.assertEquals(cardBadgeDisplay, 1, "Incorrect number of carr badge items");
    }

}
