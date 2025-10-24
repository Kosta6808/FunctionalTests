// LoginTest.java
package tests;
import base.BaseTests;
import org.testng.annotations.Test;
import pages.LoginPage;
import static org.testng.Assert.*;

public class LoginTest extends BaseTests {
    @Test
    public void verifySuccessfulLogin(){
        LoginPage loginPage = new LoginPage(driver);
        assertEquals(loginPage.login("Kosta6808", "9411f34530fb4278991f71178976eca9").getTitle(),
                "http://localhost:8080/");
    }
}