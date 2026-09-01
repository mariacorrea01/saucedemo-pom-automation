package com.saucedemo.pages;

import com.saucedemo.pages.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(css=".cart_item .inventory_item_name")
    private List<WebElement> cartItemNames;

    public CartPage(WebDriver driver){
        super(driver);
    }

    public boolean isCartEmpty(){
        return driver.findElements(By.className("cart_item")).isEmpty();
    }

    public int getCartItemCount() {
        if (cartItems.isEmpty()) {
            return 0;
        } else {
            return cartItems.size();
        }
    }

    public List<String> getCartItemNames() {
        List<String> names = new ArrayList<>();
        for(WebElement item : cartItemNames){
            names.add(item.getText());
        }
        return names;
    }

    public void removeItemByName(String productName){
        String product = productName.toLowerCase().replace(" ","-");
        WebElement removeButton= driver.findElement(By.id("remove-"+product));
        click(removeButton);
    }

    public CheckoutStepOnePage proceedToCheckout(){
        click(checkoutButton);
        return new CheckoutStepOnePage(driver);
    }

    public void removeAllItemsFromCart(){
        List<String> productNames = getCartItemNames();
        for(String productName : productNames){
            removeItemByName(productName);
        }
    }
}
