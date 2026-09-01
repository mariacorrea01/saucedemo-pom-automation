package com.saucedemo.pages;

import com.saucedemo.pages.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class CheckoutStepTwoPage extends BasePage {
    @FindBy(css = ".cart_item .inventory_item_name")
    private List<WebElement> orderedItemNames;

    @FindBy(id="finish")
    private WebElement finishButton;

    public CheckoutStepTwoPage(WebDriver driver){
        super(driver);
    }

    public List<String> getOrderedItemNames(){
        List<String> names= new ArrayList<>();
        for(WebElement item: orderedItemNames){
            names.add(item.getText());
        }
        return names;
    }

    public CheckoutCompletePage finishCheckout(){
        click(finishButton);
        return new CheckoutCompletePage(driver);
    }
}
