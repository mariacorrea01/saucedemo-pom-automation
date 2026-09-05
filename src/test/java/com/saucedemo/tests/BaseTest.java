package com.saucedemo.tests;

import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.utils.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
/**
 * Parent class for every test class in the suite.
 *
 * Responsibilities:
 *  - @BeforeMethod: start a fresh browser session before each test method,
 *    so tests never leak state (making them atomic/independent from
 *    one another).
 *  - @AfterMethod: close the browser after each test method.
 *  - Implements TestNG's ITestListener so every test's start/pass/fail
 *    outcome is logged to the console automatically, without adding
 *    logging code inside each individual test method. Registered in
 *    testng.xml via the &lt;listeners&gt; tag.
 */
public class BaseTest implements ITestListener {

    protected static final String STANDARD_USERNAME = "standard_user";
    protected static final String STANDARD_PASSWORD = "secret_sauce";
    protected WebDriver driver;
    protected LoginPage loginPage;

    /** Starts a fresh browser session and opens the login page before every test method. */
    @BeforeMethod
    public void setUp(){
        driver= DriverManager.getDriver();
        loginPage=new LoginPage(driver).open();
    }
    /** Closes the browser after every test method, regardless of the outcome. */
    @AfterMethod
    public void tearDown(){
        DriverManager.quitDriver();
    }
    /**
     * Convenience helper reused by every test class that needs to
     * start from an authenticated state.
     *
     * @return the InventoryPage the user lands on after logging in
     */

    protected InventoryPage loginAsStandardUser(){
        return loginPage.loginAs(STANDARD_USERNAME,STANDARD_PASSWORD);
    }

    @Override
    public void onTestStart(ITestResult result) {

        System.out.println("STARTING TEST: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        System.out.println("PASSED: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("FAILED: " + result.getName() + " - " + result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("SKIPPED: " + result.getName());
    }
}
