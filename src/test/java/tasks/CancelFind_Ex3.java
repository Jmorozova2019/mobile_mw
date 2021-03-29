package tasks;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import lib.ui.MainPageObject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

/**
 * Ex3 Тест: Отмена поиска
 */
public class CancelFind_Ex3 extends MainPageObject {

    private AppiumDriver driver;

    public CancelFind_Ex3(AppiumDriver driver) {
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
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void seach_cancel_search_Ex3() {
        WebElement element_to_init_search = driver.findElementByXPath("//*[contains(@text, 'Search Wikipedia')]");
        element_to_init_search.click();

        WebElement element_to_enter_search_line = waitForElementPresent("xpath://*[contains(@text, 'Search…')]",
                "Cannot find search input");

        element_to_enter_search_line.sendKeys("Java");
        String result_search_xpath = "xpath://*[contains(@resource-id, 'page_list_item_container')]";
        waitForElementPresent(result_search_xpath,
                "Cannot find any topic searching by 'Java'", 15);
        //можно обойтись предыдущей проверкой - там проверяется что найдена по крайней мере одна статья
        Assert.assertTrue(driver.findElementsByXPath(result_search_xpath).size() > 0);

        WebElement element_close_btn = driver.findElementById("org.wikipedia:id/search_close_btn");
        element_close_btn.click();

        waitForElementPresent("xpath://*[contains(@text, 'Search…')]", "Cannot find search input");

        String clear_error_message = "After clearing the search field, the results of the previous search remain";
        Assert.assertTrue(clear_error_message,
                driver.findElementsByXPath(result_search_xpath).size() == 0);
    }
}

