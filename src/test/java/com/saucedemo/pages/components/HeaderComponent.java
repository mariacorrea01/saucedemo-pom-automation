package com.saucedemo.pages.components;

import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Represents the top header/hamburger menu that appears on every
 * authenticated page (Inventory, Cart, Checkout steps).
 *
 * It is intentionally NOT a standalone "page" (the user never navigates
 * directly to it) but a reusable component that any authenticated page
 * object can compose, avoiding duplicated locators for the same menu
 * across multiple page classes.
 */
public class HeaderComponent extends BasePage {
    @FindBy(id = "react-burger-menu-btn")
        private WebElement menuButton;

    @FindBy(id="logout_sidebar_link")
    private WebElement logoutLink;

    public HeaderComponent(WebDriver driver){
        super(driver);

    }
    /** Opens the hamburger menu and waits for the logout link to appear. */
    public void  openMenu(){
        click(menuButton);
        waitForVisibility(logoutLink);
    }
    /**
     * Opens the menu and clicks "Logout".
     *
     * @return the LoginPage the user lands on after logging out
     */
    public LoginPage logout(){
        openMenu();
        click(logoutLink);
        return new LoginPage(driver);
    }
}
