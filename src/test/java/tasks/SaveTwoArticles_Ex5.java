package tasks;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import lib.ui.MainPageObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;


public class SaveTwoArticles_Ex5 extends MainPageObject
{
    private AppiumDriver driver;

    public SaveTwoArticles_Ex5(AppiumDriver driver) {
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
    public void testSaveTwoArticleAndDeleteOne() throws InterruptedException {
        click("xpath://*[contains(@text,'Search Wikipedia')]",
            "Cannot find 'Search Wikipedia' input");

        sendKeys("xpath://*[contains(@text,'Search…')]", "Java", "Cannot find Search input", 5);

        click("xpath://*[@text='Object-oriented programming language']",
            "Cannot find article 'Object-oriented programming language'");

        waitForElementPresent("id:org.wikipedia:id/view_page_title_text",  "Cannot find article title", 15);

        click("xpath://android.widget.ImageView[@content-desc='More options']",
            "Cannot find button to open article options");

        //чтобы не открывалось окно смены языка слип тоже успешно решает эту проблему
        waitForElementNotPresent("//*[@resource-id='org.wikipedia:id/search_lang_button']",
            "Find button change language");

        click("xpath://*[@text='Add to reading list']",
            "Cannot find option to add article to reading list");

        click("id:org.wikipedia:id/onboarding_button", "Cannot find 'GOT IT' tip overlay");

        clear("id:org.wikipedia:id/text_input", "Cannot find input to set name of articles folder");

        String name_of_folder = "Learning programming";
        sendKeysWithoutPaste("id:org.wikipedia:id/text_input",
                "Cannot put text into articles folder input", name_of_folder);

        click("xpath://*[@text='OK']", "Cannot press OK button");//add first article
//------------------------------------
        click("xpath://android.widget.ImageButton[@content-desc='Navigate up']","Cannot close article, cannot find X link");
//----
        click("xpath://*[contains(@text,'Search Wikipedia')]","Cannot find 'Search Wikipedia' input");

        sendKeys("xpath://*[contains(@text,'Search…')]","Java","Cannot find Search input", 5);

        click("xpath://*[@text='Programming language']","Cannot find article 'Programming language'");

        waitForElementPresent("id:org.wikipedia:id/view_page_title_text", "Cannot find article title", 15);

        click("xpath://android.widget.ImageView[@content-desc='More options']","Cannot find button to open article options");

        //чтобы не открывалось окно смены языка
        waitForElementNotPresent("xpath://*[@resource-id='org.wikipedia:id/search_lang_button']",
            "Find button change language");

        click("xpath://*[@text='Add to reading list']","Cannot find option to add article to reading list");

        click("xpath://*[@resource-id='org.wikipedia:id/item_title' and @text='" + name_of_folder +"']",
            "Cannot folder " + name_of_folder + " to list folders");
//------------
        click("xpath://android.widget.ImageButton[@content-desc='Navigate up']",
            "Cannot close article, cannot find X link");

        click("xpath://android.widget.FrameLayout[@content-desc='My lists']/android.widget.ImageView",
            "Cannot find navigation button to My list");

        click("xpath://*[@text='" + name_of_folder + "']/../preceding-sibling::android.widget.LinearLayout",
            "Cannot find created folder");

        swipeElementToLeft("xpath://*[@text='Java (programming language)']", "Cannot find saved article");

        waitForElementNotPresent("xpath://*[@text='Java (programming language)']","Cannot delete saved article");

        click("xpath://*[@text='JavaScript']", "Cannot find saved article");

        assertElementHasText("xpath://*[@resource-id='org.wikipedia:id/view_page_title_text']",
            "JavaScript","Cannot find Javascript article");
    }
}
