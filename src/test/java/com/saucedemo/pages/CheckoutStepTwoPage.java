package com.saucedemo.pages;

import com.saucedemo.pages.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
/**
 * Page Object for the /checkout-step-two.html screen
 * ("Checkout: Overview").
 */
public class CheckoutStepTwoPage extends BasePage {
    @FindBy(css = ".cart_item .inventory_item_name")
    private List<WebElement> orderedItemNames;

    @FindBy(id="finish")
    private WebElement finishButton;

    public CheckoutStepTwoPage(WebDriver driver){
        super(driver);
    }

    /**
     * Converts the list of WebElements (the product names shown in the
     * order overview) into a plain list of Strings.
     *
     * @return the names of every product listed in the order overview
     */
    public List<String> getOrderedItemNames(){
        List<String> names= new ArrayList<>();
        for(WebElement item: orderedItemNames){
            names.add(item.getText());
        }
        return names;
    }
    /**
     * Clicks the "Finish" button.
     *
     * @return the CheckoutCompletePage the user lands on
     */
    public CheckoutCompletePage finishCheckout(){
        click(finishButton);
        return new CheckoutCompletePage(driver);
    }
}
