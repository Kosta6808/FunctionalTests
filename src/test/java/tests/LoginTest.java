// LoginTest.java
package tests;
import base.BaseTests;
import io.github.cdimascio.dotenv.Dotenv;
import org.testng.annotations.Test;
import pages.LoginPage;
import static org.testng.Assert.*;

public class LoginTest extends BaseTests {
    @Test
    public void verifySuccessfulLogin()
    {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();
        String user = dotenv.get("IY_USER");
        String pass = dotenv.get("IY_PASS");
        driver.manage().deleteAllCookies();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user, pass);
        String LP=driver.getTitle();
        assertEquals(LP, "Home");
    }
}
