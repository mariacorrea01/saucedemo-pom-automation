package com.saucedemo.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Creates and closes the WebDriver (Chrome browser) used by the tests.
 *
 * Kept as a small utility class with only static methods, since the
 * whole framework only needs one browser open at a time per test.
 */
public class DriverManager {
    private static WebDriver driver;

    private DriverManager(){

    }

    /**
     * Returns the current WebDriver instance, creating a new Chrome
     * session (via WebDriverManager, so no manual chromedriver setup
     * is needed) the first time it's called.
     *
     * Also disables Chrome's built-in "password found in a data
     * breach" warning, since SauceDemo's well-known public password
     * would otherwise trigger a popup that blocks the page underneath.
     *
     * @return the active WebDriver instance
     */
    public static WebDriver getDriver(){
        if(driver== null){
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_leak_detection", false);
            options.setExperimentalOption("prefs", prefs);
            driver=new ChromeDriver(options);
            driver.manage().window().maximize();
        }
        return driver;
    }
    /**
     * Closes the current browser session, if one is open, and clears
     * the stored reference so the next getDriver() call starts a new
     * session.
     */
    public static void quitDriver(){
        if(driver!=null){
            driver.quit();
            driver=null;
        }
    }

}
