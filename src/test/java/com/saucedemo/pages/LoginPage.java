package com.saucedemo.pages;

import com.saucedemo.pages.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
    private static final String URL ="https://www.saucedemo.com/";

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

    public LoginPage open(){
        driver.get(URL);
        waitForVisibility(userNameInput);
        return this;
    }

    public InventoryPage loginAs(String username, String password){
        type(userNameInput,username);
        type(passwordInput,password);
        click(loginButton);
        return new InventoryPage(driver);
    }

    public String getErrorMessage(){
        return getText(errorMessage);
    }

    public boolean isLoginButtonDisplayed(){
        return  isDisplayed(loginButton);
    }

    public boolean isAtLoginPage(){
        return  isDisplayed(loginButton)&& getCurrentUrl().equals(URL);
    }

}
