package org.example.pages;

import org.example.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;


public class InventoryPage extends BasePage {
    public InventoryPage(WebDriver webDriver) {
        super(webDriver);
    }

    //Method to get number of  inventory items
    public int getInventoryItemsSize() {
        List<WebElement> inventoryItems = webDriver.findElements(By.className("inventory_item"));
        return inventoryItems.size();
    }

    //Method to add first inventory item to the shopping cart.
    public void addFirstInventoryItemToCart() {
        WebElement firstItem = webDriver.findElements(By.className("inventory_item")).getFirst();
        WebElement addToCart = firstItem.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        addToCart.click();
    }

    //Method to get number of  inventory items
    public int getCartBadgeSize() {
        WebElement  inventoryBag = webDriver.findElement(By.className("shopping_cart_badge"));
        String value = inventoryBag.getText();
        return Integer.parseInt(value);
    }

}
