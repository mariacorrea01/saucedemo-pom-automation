package com.saucedemo.tests;

import com.saucedemo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
/**
 * Negative login scenario, added to put in practice TestNG's
 * {@code @Parameters} annotation: instead of hardcoding invalid
 * credentials inside this class, the actual username/password values
 * are injected from testng.xml. This lets the same test method be
 * reused for different invalid-credential scenarios just by changing
 * the suite file, without touching the Java code.
 *
 * Must be run via testng.xml (not the method's individual "Run"
 * button), since the @Parameters values only exist in that suite file.
 */
public class LoginNegativeTest extends BaseTest{
    /**
     * @param username              injected from testng.xml
     * @param password              injected from testng.xml
     * @param expectedErrorMessage  injected from testng.xml
     */
    @Parameters({"username", "password", "expectedErrorMessage"})
    @Test(description = "Invalid credentials keep the user on the login page with a matching error message",
            groups = {"regression"})
    public void shouldShowErrorForInvalidCredentials(String username, String password, String expectedErrorMessage) {
        loginPage.loginAs(username, password);
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(),
                "User should remain on the login page after an invalid login attempt");
        String actualErrorMessage = loginPage.getErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage,
                "Error message shown does not match the expected one");

    }
}
