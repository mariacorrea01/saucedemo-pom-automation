package com.saucedemo.tests;

import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
/**
 * Covers the "logout" scenario requested in the assignment: log out from
 * an authenticated session and confirm the user is redirected back to
 * the login page.
 */
public class LogoutTest extends BaseTest{

    @Test(description = "A logged-in user is redirected to the login page after logging out", groups = {"smoke"})    public void shouldRedirectToLoginPageAfterLogout(){

        InventoryPage inventoryPage= loginAsStandardUser();
        Assert.assertTrue(inventoryPage.isLoaded(),"Inventory page did not load after login");

        LoginPage loginPageAfterLogout = inventoryPage.header().logout();

        Assert.assertTrue(loginPageAfterLogout.isAtLoginPage(),
                "User was not redirected back to the login page after logging out");
        Assert.assertTrue(loginPageAfterLogout.isLoginButtonDisplayed(),
                "Login button should be visible again after loggin out");
    }
}
