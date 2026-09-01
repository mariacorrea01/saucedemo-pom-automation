package com.saucedemo.pages;

import com.saucedemo.pages.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutCompletePage extends BasePage {
    @FindBy(className = "complete-header")
    private WebElement completeHeaderText;

    public CheckoutCompletePage(WebDriver driver){
        super(driver);
    }

    public String getConfirmationHeaderText(){
        return getText(completeHeaderText);
    }

    public boolean isPurchaseConfirmed(){
        return "Thank you for your order!".equals(getConfirmationHeaderText());
    }
}
