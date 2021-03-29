package tasks;

import lib.ui.MainPageObject;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.List;

/**
 * Ex4 Тест: проеверка слов в поиске
 */
public class CheckResultSearch_Ex4 extends MainPageObject {

    private AppiumDriver driver;

    public CheckResultSearch_Ex4(AppiumDriver driver) {
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
    public void check_result_search_Ex4() {
        WebElement element_to_init_search = driver.findElementByXPath("//*[contains(@text, 'Search Wikipedia')]");
        element_to_init_search.click();

        WebElement element_to_enter_search_line = waitForElementPresent(
            "xpath://*[contains(@text, 'Search…')]", "Cannot find search input");

        String searchText = "Java";
        element_to_enter_search_line.sendKeys(searchText);
        String result_title_search_xpath = "xpath://*[contains(@resource-id, 'page_list_item_title')]";
        waitForElementPresent(result_title_search_xpath,
                "Cannot find any topic searching by 'Java'", 15);

        List<WebElement> resTitles = driver.findElementsByXPath(result_title_search_xpath);
        String error_text_in_search_result = "As a result, there is no search word";
        for(WebElement elementTitle: resTitles)
        {
            Assert.assertTrue(error_text_in_search_result, elementTitle.getText().contains(searchText));
        }
    }
}