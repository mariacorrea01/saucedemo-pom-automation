package com.saucedemo.pages.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {
    private final WebDriver driver;
    private  final WebDriverWait wait;

    public BasePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver,this);
    }

    private List<WebElement> waitForVisibility(List<WebElement> elements){
        return wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    private WebElement waitForVisibility(WebElement element){
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    private WebElement waitForClickability(WebElement element){
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    private  void click(WebElement element){
        waitForClickability(element).click();
    }

    private void type(WebElement element, String text){
        WebElement visibleElement = waitForVisibility(element);
        visibleElement.clear();
        visibleElement.sendKeys(text);
    }

    private String getText(WebElement element){
        return  waitForVisibility(element).getText();
    }

    private boolean isDisplayed(WebElement element){
        try{
            return element.isDisplayed();
        }catch (Exception e){
            return false;
        }
    }

    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }

    public String getPageTitle(){
        return driver.getTitle();
    }
}
