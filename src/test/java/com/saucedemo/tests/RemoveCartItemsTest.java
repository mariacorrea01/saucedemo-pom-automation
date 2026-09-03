package com.saucedemo.tests;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.InventoryPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class RemoveCartItemsTest extends BaseTest{
     private static final List<String> PRODUCTS_TO_ADD = List.of(
             "Sauce Labs Backpack",
             "Sauce Labs Bike Light",
             "Sauce Labs Bolt T-Shirt"
     );

     @Test(description = "A user can add three products and remove all of them from the cart")
    public void shouldRemoveAllItemsFromCart(){
         InventoryPage inventoryPage= loginAsStandardUser();
         Assert.assertTrue(inventoryPage.isLoaded(),"Inventory page did not load after login");

         for(String productName : PRODUCTS_TO_ADD){
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
