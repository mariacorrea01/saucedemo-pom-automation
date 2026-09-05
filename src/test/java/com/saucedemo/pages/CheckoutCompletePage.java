package com.saucedemo.pages;

import com.saucedemo.pages.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
/**
 * Page Object for the /checkout-complete.html screen
 * ("Thank you for your order!").
 */
public class CheckoutCompletePage extends BasePage {
    @FindBy(className = "complete-header")
    private WebElement completeHeaderText;

    public CheckoutCompletePage(WebDriver driver){
        super(driver);
    }

    /** @return the confirmation header text shown on this page */
    public String getConfirmationHeaderText(){
        return getText(completeHeaderText);
    }
    /** @return true if the purchase was confirmed with the expected message */
    public boolean isPurchaseConfirmed(){
        return "Thank you for your order!".equals(getConfirmationHeaderText());
    }
}
