package com.saucedemo.pages.base;

import com.saucedemo.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
/**
 * Parent class for every Page Object in the framework.
 *
 * It centralizes:
 *  - PageFactory initialization (so @FindBy fields work on every subclass)
 *  - Explicit-wait helper methods, so individual page objects never
 *    call Thread.sleep() or duplicate wait logic.
 *
 * Every concrete page (LoginPage, InventoryPage, CartPage, etc.) extends
 * this class instead of talking to WebDriver directly.
 */
public class BasePage {
    protected final WebDriver driver;
    private  final WebDriverWait wait;

    /**
     * @param driver the active WebDriver session, shared by every page
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWaitSeconds()));
        PageFactory.initElements(driver,this);
    }
    /** Waits until every element in the list is visible, then returns the list. */
    protected List<WebElement> waitForVisibility(List<WebElement> elements){
        return wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }
    /** Waits until the given element is clickable (visible and enabled), then returns it. */
    protected WebElement waitForVisibility(WebElement element){
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    /** Waits until the given element is clickable (visible and enabled), then returns it. */
    protected WebElement waitForClickability(WebElement element){
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    /** Waits for the element to be clickable, then clicks it. */
    protected  void click(WebElement element){
        waitForClickability(element).click();
    }
    /** Waits for the element to be visible, clears it, then types the given text. */
    protected void type(WebElement element, String text){
        WebElement visibleElement = waitForVisibility(element);
        visibleElement.clear();
        visibleElement.sendKeys(text);
    }
    /** Waits for the element to be visible, then returns its visible text. */
    protected String getText(WebElement element){

        return  waitForVisibility(element).getText();
    }
    /**
     * Safely checks whether an element is displayed, without throwing
     * an exception if the element doesn't exist in the DOM.
     *
     * @return true if the element is displayed, false otherwise (even
     *         if the element doesn't exist at all)
     */
    protected boolean isDisplayed(WebElement element){
        try{
            return element.isDisplayed();
        }catch (Exception e){
            return false;
        }
    }
    /** @return the browser's current URL */
    protected String getCurrentUrl(){
        return driver.getCurrentUrl();
    }
    /** @return the current page's title */
    protected String getPageTitle(){
        return driver.getTitle();
    }
}
