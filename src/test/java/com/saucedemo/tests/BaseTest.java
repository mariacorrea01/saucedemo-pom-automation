package com.saucedemo.tests;

import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.utils.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected static final String STANDARD_USERNAME = "standard_user";
    protected static final String STANDARD_PASSWORD = "secret_sauce";
    protected WebDriver driver;
    protected LoginPage loginPage;

    @BeforeMethod
    public void setUp(){
        driver= DriverManager.getDriver();
        loginPage=new LoginPage(driver).open();
    }

    @AfterMethod
    public void tearDown(){
        DriverManager.quitDriver();
    }

    protected InventoryPage loginAsStandardUser(){
        return loginPage.loginAs(STANDARD_USERNAME,STANDARD_PASSWORD);
    }
}
