package com.saucedemo.pages;

import com.saucedemo.pages.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * Page Object for the /cart.html screen.
 */
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

    /** @return true if the cart currently has no products */
    public boolean isCartEmpty(){
        return driver.findElements(By.className("cart_item")).isEmpty();
    }

    /** @return how many products are currently listed in the cart */
    public int getCartItemCount() {
        if (cartItems.isEmpty()) {
            return 0;
        } else {
            return cartItems.size();
        }
    }
    /**
     * Converts the list of WebElements (the product names shown in the
     * cart) into a plain list of Strings, so tests can compare product
     * names without dealing with Selenium objects directly.
     *
     * @return the names of every product currently in the cart
     */
    public List<String> getCartItemNames() {
        List<String> names = new ArrayList<>();
        for(WebElement item : cartItemNames){
            names.add(item.getText());
        }
        return names;
    }
    /**
     * Removes a specific product from the cart by building its
     * "Remove" button id dynamically from the product's name.
     *
     * @param productName the exact product name as shown on the page
     */
    public void removeItemByName(String productName){
        String product = productName.toLowerCase().replace(" ","-");
        WebElement removeButton= driver.findElement(By.id("remove-"+product));
        click(removeButton);
    }
    /**
     * Clicks the "Checkout" button.
     *
     * @return the CheckoutStepOnePage the user lands on
     */
    public CheckoutStepOnePage proceedToCheckout(){
        click(checkoutButton);
        return new CheckoutStepOnePage(driver);
    }
    /**
     * Removes every product currently listed in the cart, one by one,
     * re-reading the product names each time to avoid
     * StaleElementReferenceException once an item is removed and the
     * list shrinks.
     */
    public void removeAllItemsFromCart(){
        List<String> productNames = getCartItemNames();
        for(String productName : productNames){
            removeItemByName(productName);
        }
    }
}
