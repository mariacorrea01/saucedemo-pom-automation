package com.saucedemo.tests;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.InventoryPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
/**
 * Covers the "removing elements of the shopping cart" scenario requested
 * in the assignment: add 3 different products to the cart, go to the
 * cart page, remove all of them, and confirm the cart ends up empty.
 *
 * The list of products to add is supplied by a TestNG @DataProvider
 * instead of a hardcoded constant, so a different set of products
 * could be tested just by adding another data row, without touching
 * the test logic itself.
 */
public class RemoveCartItemsTest extends BaseTest{
    /**
     * @return a single data row containing the 3 products to add and
     *         then remove from the cart.
     */
    @DataProvider(name = "productSets")
    public Object[][] productSets() {
        return new Object[][]{
                {List.of("Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt")}
        };
    }
    @Test(dataProvider = "productSets",
            description = "A user can add three products and remove all of them from the cart",
            groups = {"regression"})
    public void shouldRemoveAllItemsFromCart(List<String> productsToAdd){
         InventoryPage inventoryPage= loginAsStandardUser();
         Assert.assertTrue(inventoryPage.isLoaded(),"Inventory page did not load after login");

         for(String productName : productsToAdd){
             inventoryPage.addProductToCartByName(productName);
         }
         Assert.assertEquals(inventoryPage.getCartItemCount(),3,
                 "Cart badge should show excatly 3 items after adding 3 products");

         CartPage cartPage = inventoryPage.goToCart();
         Assert.assertEquals(cartPage.getCartItemCount(), 3,
                 "Cart page should list exactly 3 products");
         cartPage.removeAllItemsFromCart();

         Assert.assertTrue(cartPage.isCartEmpty(),"Cart should be empty after removing every item");

     }
}
