package com.saucedemo.pages;

import com.saucedemo.pages.base.BasePage;
import com.saucedemo.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
/**
 * Page Object for https://www.saucedemo.com/ (the login screen).
 */
public class LoginPage extends BasePage {

    @FindBy(id ="user-name")
    private WebElement userNameInput;

    @FindBy(id="password")
    private WebElement passwordInput;

    @FindBy(id="login-button")
    private WebElement loginButton;

    @FindBy(css = "h3[data-test='error']")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver){
        super(driver);
    }

    /**
     * Navigates to the application's base URL and waits until the
     * login form is visible.
     *
     * @return this same page, so the call can be chained
     */
    public LoginPage open(){
        driver.get(ConfigReader.getBaseUrl());
        waitForVisibility(userNameInput);
        return this;
    }
    /**
     * Fills in the username and password fields and submits the form.
     *
     * @param username the username to log in with
     * @param password the password to log in with
     * @return the InventoryPage the user lands on after a successful login
     */
    public InventoryPage loginAs(String username, String password){
        type(userNameInput,username);
        type(passwordInput,password);
        click(loginButton);
        return new InventoryPage(driver);
    }
    /** @return the error message text shown after a failed login attempt */
    public String getErrorMessage(){
        return getText(errorMessage);
    }
    /** @return true if the login button is currently visible */
    public boolean isLoginButtonDisplayed(){
        return  isDisplayed(loginButton);
    }
    /** @return true if the browser is currently on the login page */
    public boolean isAtLoginPage(){
        return  isDisplayed(loginButton)&& getCurrentUrl().equals(ConfigReader.getBaseUrl());
    }

}
