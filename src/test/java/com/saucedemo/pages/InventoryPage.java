package com.saucedemo.pages;

import com.saucedemo.pages.base.BasePage;
import com.saucedemo.pages.components.HeaderComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Random;

/**
 * Page Object for the /inventory.html screen (the product catalog shown
 * right after a successful login).
 */
public class InventoryPage extends BasePage {

    private final Random random = new Random();
    private final HeaderComponent header;


    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(css=".inventory_item_name")
    private List<WebElement> inventoryItemNames;

    @FindBy(id = "shopping_cart_container")
    private WebElement shoppingCartIcon;

    @FindBy(className = "shopping_cart_badge")
    private WebElement shoppingCartBadge;

    public InventoryPage(WebDriver driver){
        super(driver);
        this.header = new HeaderComponent(driver);
    }

    /** @return the header component (hamburger menu / logout) shared by this page */
    public HeaderComponent header() {
        return header;
    }
    /** @return true if the "Products" title is visible, confirming the page loaded */
    public boolean isLoaded(){
        return isDisplayed(pageTitle)&&"Products".equals(getText(pageTitle));
    }

    /** @return how many items the cart badge currently shows, or 0 if the cart is empty */
    public int getCartItemCount() {
        try {
            String badgeText = getText(shoppingCartBadge);
            return Integer.parseInt(badgeText);
        } catch (Exception exception)
        {
            return 0;
        }
    }

    /**
     * Clicks the shopping cart icon.
     *
     * @return the CartPage the user lands on
     */
    public CartPage goToCart(){
        click(shoppingCartIcon);
        return new CartPage(driver);
    }
    /**
     * Adds a specific product to the cart by building its "Add to
     * cart" button id dynamically from the product's name.
     *
     * @param productName the exact product name as shown on the page
     */
    public void addProductToCartByName(String productName){
        String productSlug = productName.toLowerCase().replace(" ","-");
        WebElement addToCartButton = driver.findElement(By.id("add-to-cart-"+productSlug));
        click(addToCartButton);
    }
    /**
     * Picks one product at random from the catalog, hovers over it
     * (using Selenium's Actions class) and adds it to the cart.
     *
     * @return the exact name of the product that was added, so callers
     *         can assert on it later in the flow (cart, checkout, etc.)
     */
    public String addRandomProductToCart() {
        List<WebElement> names = waitForVisibility(inventoryItemNames);
        WebElement chosenProductElement = names.get(random.nextInt(names.size()));
        new Actions(driver).moveToElement(chosenProductElement).perform();
        String chosenProductName = chosenProductElement.getText();
        addProductToCartByName(chosenProductName);
        return chosenProductName;
    }
}
