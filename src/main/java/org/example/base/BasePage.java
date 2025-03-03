package org.example.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected final WebDriver webDriver;
    protected WebDriverWait wait;

    public BasePage(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(this.webDriver, this); // Initializing all the web elements located by @FindBy
        wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(20));
    }
}
