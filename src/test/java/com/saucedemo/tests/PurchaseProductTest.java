package com.saucedemo.tests;

import com.saucedemo.data.CustomerInfo;
import com.saucedemo.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;
/**
 * Covers the complete purchase buy-flow requested in the assignment:
 * select a random product, add it to the cart, fill in the personal
 * information, and confirm the "Thank you for your order!" page is shown.
 */
public class PurchaseProductTest extends BaseTest {

    @Test(description = "A user can buy a randomly selected product end-to-end", groups = {"smoke"})    public void shouldCompletePurchaseOfRandomProduct() {
        InventoryPage inventoryPage = loginAsStandardUser();
        Assert.assertTrue(inventoryPage.isLoaded(),"Inventory page did not load after login");

        String selectedProduct = inventoryPage.addRandomProductToCart();

        Assert.assertEquals(inventoryPage.getCartItemCount(),1,
                "Cart badge should show exactly one item after adding a product");

        inventoryPage.goToCart();
        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.getCartItemNames().contains(selectedProduct),
                "The product added on the inventory page is missing from the cart");

        CheckoutStepOnePage checkoutStepOnePage = cartPage.proceedToCheckout();
        CheckoutStepTwoPage checkoutStepTwoPage = checkoutStepOnePage.fillPersonalInformationAndContinue(CustomerInfo.sampleCustomer());

        Assert.assertTrue(checkoutStepTwoPage.getOrderedItemNames().contains(selectedProduct),
                "Order overview does not list the purchased product");

        CheckoutCompletePage checkoutCompletePage = checkoutStepTwoPage.finishCheckout();
        Assert.assertTrue(checkoutCompletePage.isPurchaseConfirmed(),
                "Purchase was not confirmed with the expected message");
    }
}
