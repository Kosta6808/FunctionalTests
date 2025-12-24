// BaseTests.java

package base;

import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.time.Duration;
import org.testng.ITestResult;
import java.io.File;
import java.io.IOException;
import com.google.common.io.Files;
import io.github.cdimascio.dotenv.Dotenv;


public class BaseTests
{

    protected WebDriver driver;

    @BeforeMethod
    public void setUp()
    {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();
        String link = dotenv.get("IY_LINK");

        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
//        options.addArguments("--headless=new");  //Не срабатывает нажатие Login в LoginPage при включении headless
        driver = new ChromeDriver(options);
//        FirefoxOptions options = new FirefoxOptions();
//        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
//        driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
        driver.get(link);
    }

    @AfterMethod
    public void teardown(ITestResult result)
    {
        if(ITestResult.FAILURE == result.getStatus())
        {
            var camera = (TakesScreenshot) driver;
            File screenshot = camera.getScreenshotAs(OutputType.FILE);
            try
            {
                Files.move(screenshot,
                        new File("src/main/resources/screenshots/" +
                                result.getName() + ".png"));
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        driver.quit();
    }
}
