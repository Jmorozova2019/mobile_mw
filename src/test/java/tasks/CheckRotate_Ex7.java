package tasks;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import lib.ui.MainPageObject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class CheckRotate_Ex7 extends MainPageObject
{
    private AppiumDriver driver;

    public CheckRotate_Ex7(AppiumDriver driver) {
        super(driver);
    }

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Nexus6");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app",
                "/Users/Zhanna/Desktop/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown()
    {
        driver.quit();
    }

    @Test
    public void rotaiteToPortrait()
    {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    @Test
    public void testCheckRotate_1()
    {
        click("xpath://*[contains(@text,'Search Wikipedia')]", "Cannot find 'Search Wikipedia' input");
        sendKeys("xpath://*[contains(@text,'Search…')]", "Java", "Cannot find Search input", 5);

        driver.rotate(ScreenOrientation.LANDSCAPE);
    }
}
