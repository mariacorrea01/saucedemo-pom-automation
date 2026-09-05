package com.saucedemo.pages;

import com.saucedemo.data.CustomerInfo;
import com.saucedemo.pages.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for the /checkout-step-one.html screen
 * ("Checkout: Your Information").
 */
public class CheckoutStepOnePage extends BasePage {

    @FindBy(id = "first-name")
    private WebElement firstNameInput;

    @FindBy(id = "last-name")
    private WebElement lastNameInput;

    @FindBy(id="postal-code")
    private WebElement postalCodeInput;

    @FindBy(id="continue")
    private  WebElement continueButton;

    public CheckoutStepOnePage(WebDriver driver){
        super(driver);
    }

    /**
     * Fills in the first name, last name and postal code fields, then
     * submits the form.
     *
     * @param customerInfo the personal data to fill the form with
     * @return the CheckoutStepTwoPage the user lands on
     */
    public CheckoutStepTwoPage fillPersonalInformationAndContinue (CustomerInfo customerInfo){
        type(firstNameInput,customerInfo.getFirstName());
        type(lastNameInput,customerInfo.getLastName());
        type(postalCodeInput,customerInfo.getPostalCode());
        click(continueButton);
        return  new  CheckoutStepTwoPage(driver);
    }
}
