package com.saucedemo.pages.components;

import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeaderComponent extends BasePage {
    @FindBy(id = "react-burger-menu-btn")
        private WebElement menuButton;

    @FindBy(id="logout_sidebar_link")
    private WebElement logoutLink;

    public HeaderComponent(WebDriver driver){
        super(driver);

    }

    public void  openMenu(){
        click(menuButton);
        waitForVisibility(logoutLink);
    }

    public LoginPage logout(){
        openMenu();
        click(logoutLink);
        return new LoginPage(driver);
    }
}
