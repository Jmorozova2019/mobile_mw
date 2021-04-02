package lib;

import io.appium.java_client.AppiumDriver;

import io.qameta.allure.Step;
import junit.framework.TestCase;

import lib.ui.WelcomePageObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.RemoteWebDriver;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class CoreTestCase {

    protected RemoteWebDriver driver;

    @Before
    @Step("Run driver and session")
    public void setUp() throws Exception {
        driver = Platform.getInstance().getDriver();
        createAllurePropertyFile();
        rotateScreenPotrait();
        skipWelcomePageForIOSApp();
        openWikiWebPage();
    }

    @After
    @Step("Remove driver and session")
    public void tearDown() { driver.quit(); }

    //public void rotateScreenPotraitBeforTest() {rotateScreenPotrait();}
    @Step("rotateScreenPotrait")
    protected void rotateScreenPotrait()
    {
        if(driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.rotate(ScreenOrientation.PORTRAIT);
        } else {
            System.out.println("Method rotateScreenPortrait() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    @Step("rotateScreenLandscape")
    protected void rotateScreenLandscape()
    {
        if(driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.rotate(ScreenOrientation.LANDSCAPE);
        } else {
            System.out.println("Method rotateScreenLandscape() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Send mobile app to background (this method does nothing for mobile web)")
    protected void backgroundApp(Duration durationOfSecond)
    {
        if(driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.runAppInBackground(durationOfSecond);
        }else {
            System.out.println("Method runAppInBackground() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Open Wikipedia URL for mobile web (this method does nothing for Android and IOS)")
    protected void openWikiWebPage()
    {
        if (Platform.getInstance().isMW()) {
            driver.get("https://en.m.wikipedia.org");
        } else {
            System.out.println("Method openWikiWebPage() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Skip welcome page screen for IOS")
    private void skipWelcomePageForIOSApp()
    {
        if (Platform.getInstance().isIOS())
        {
            AppiumDriver driver = (AppiumDriver) this.driver;
            WelcomePageObject welcomePageObject = new WelcomePageObject(driver);
            welcomePageObject.clickSkip();
        }
    }

    private void createAllurePropertyFile() throws IOException {
        String path = System.getProperty("allure.results.directory");
        try {
            Properties props = new Properties();
            FileOutputStream fos = new FileOutputStream(path + "/environment.properties");
            props.setProperty("Environment", Platform.getInstance().getPlatformVar());
            props.store(fos, "See https://github.com/allure-framework/allure-app/wiki/Environment");
            fos.close();
        } catch (FileNotFoundException e) {
            System.err.println("IO problem when writing allure properties file");
            e.printStackTrace();
        }
    }
}
