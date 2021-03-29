package tasks;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import lib.ui.MainPageObject;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class AssertTitle_Ex6 extends MainPageObject
{
    private AppiumDriver driver;

    public AssertTitle_Ex6(AppiumDriver driver) {
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

    @Test
    public void testAssertElementPresent()
    {
        click("xpath://*[contains(@text,'Search Wikipedia')]",
                "Cannot find 'Search Wikipedia' input");

        sendKeys("xpath://*[contains(@text,'Search…')]","Java",
            "Cannot find Search input",5);

       click("xpath://*[@text='Object-oriented programming language']",
            "Cannot find article 'Object-oriented programming language'"
        );

       //считаем что статья открыта, если есть кнопка Найти (лупа)
       waitForElementPresent("xpath://*[@resource-id='org.wikipedia:id/menu_page_search']",
            "Cannot open article");

       assertElementsPresentNowByXpath("xpath://*[contains(@resource-id, 'view_page_title_text')]",
            "In the article not found title");
    }
}
