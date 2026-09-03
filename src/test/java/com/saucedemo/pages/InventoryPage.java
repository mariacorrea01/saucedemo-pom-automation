package com.saucedemo.pages;

import com.saucedemo.pages.base.BasePage;
import com.saucedemo.pages.components.HeaderComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Random;

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

    public HeaderComponent header() {
        return header;
    }

    public boolean isLoaded(){
        return isDisplayed(pageTitle)&&"Products".equals(getText(pageTitle));
    }

    public int getCartItemCount() {
        try {
            String badgeText = getText(shoppingCartBadge);
            return Integer.parseInt(badgeText);
        } catch (Exception exception)
        {
            return 0;
        }
    }

    public CartPage goToCart(){
        click(shoppingCartIcon);
        return new CartPage(driver);
    }

    public void addProductToCartByName(String productName){
        String productSlug = productName.toLowerCase().replace(" ","-");
        WebElement addToCartButton = driver.findElement(By.id("add-to-cart-"+productSlug));
        click(addToCartButton);
    }

    public String addRandomProductToCart(){
        List<WebElement> names= waitForVisibility(inventoryItemNames);
        String chosenProductName = names.get(random.nextInt(names.size())).getText();
        addProductToCartByName(chosenProductName);
        return chosenProductName;
    }
}
