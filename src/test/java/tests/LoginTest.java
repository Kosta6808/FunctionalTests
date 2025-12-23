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
        assertEquals(loginPage.login("102945", "jtuxbzwh").getTitle(),
                "https://iytnet.com/");
    }
}