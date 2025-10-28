// BaseTests.java
package base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.time.Duration;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import java.io.File;
import java.io.IOException;
import com.google.common.io.Files;


public class BaseTests {
    protected WebDriver driver;
    @BeforeMethod
    public void setUp(){
        System.setProperty("webdriver.chrome.driver",
                "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://iytnet.com/");
    }

    @AfterMethod
        public void teardown(ITestResult result){
            if(ITestResult.FAILURE == result.getStatus()) {
                var camera = (TakesScreenshot) driver;
                File screenshot = camera.getScreenshotAs(OutputType.FILE);
                try {
                    Files.move(screenshot,
                            new File("src/main/resources/screenshots/" +
                                    result.getName() + ".png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        driver.quit();
    }
}