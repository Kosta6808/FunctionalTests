// LoginPage.java
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class LoginPage {
    private final WebDriver driver;
    private final By emailID = By.cssSelector("#mod_login_username");
    private final By passwordField = By.cssSelector("#mod_login_password");
    private final By signInButton = By.cssSelector("input.button");

    public LoginPage(WebDriver driver)
    {
        this.driver = driver;
    }
    public void login(String email, String password)
    {
        driver.findElement(emailID).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(signInButton).click();
    }
}
//#login-form>fieldset>div>span>