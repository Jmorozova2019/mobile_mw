package lib.ui;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.appium.java_client.AppiumDriver;

import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

import lib.Platform;


public class MainPageObject {

    protected RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver)
    {
        this.driver = driver;
    }

    //можно расширить link, className и пр.
    public static By getLocatorByString(String locator_with_type)
    {
        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"), 2);
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];

        if (by_type.equals("xpath")){
            return By.xpath(locator);
        } else if (by_type.equals("id")) {
            return By.id(locator);
        } else if (by_type.equals("css")) {
            return By.cssSelector(locator);
        } else {
            throw new IllegalArgumentException("Cannot get type of locator. Locator: " + locator_with_type);
        }
    }

    public int getAmountOfElements(String locator_with_type) {
        By by = getLocatorByString(locator_with_type);

        List elements = driver.findElements(by);
        return elements.size();
    }

    public boolean isElementPresent(String locator) {
        return  getAmountOfElements(locator) > 0;
    }

    public void tryClickElementWithFewAttempt(String locator, String error_message, int amount_of_attempts) {
        int current_attemps = 0;
        boolean need_more_attemps = true;

        while (need_more_attemps) {
            try{
                click(locator, error_message, 1);
                need_more_attemps = false;
            }catch (Exception e) {
                if (current_attemps > amount_of_attempts) {
                    click(locator, error_message, 1);
                }
            }
            ++current_attemps;
        }
    }

    public WebElement waitForElementPresent(String locator_with_type, String error_message, int timeoutInSecond)
    {
        By by = getLocatorByString(locator_with_type);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_message + "\n");

        return wait.until( ExpectedConditions.presenceOfElementLocated(by));
    }

    //*****************************waits
    public WebElement waitForElementPresent(String locator_with_type, String error_message)
    {
        return waitForElementPresent(locator_with_type, error_message, 5);
    }

    //retest
    public void waitForElementNotPresent(String locator_with_type, String error_message, int timeoutInSecond)
    {
        By by = getLocatorByString(locator_with_type);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_message + "\n");

        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public void waitForElementNotPresent(String locator_with_type, String error_message)
    {
        waitForElementNotPresent(locator_with_type, error_message, 5);
    }

    //******************************************************
    public WebElement click(String locator_with_type, String error_message, int timeoutInSecond)
    {
        WebElement element = waitForElementPresent(locator_with_type, error_message, timeoutInSecond);
        element.click();

        return element;
    }

    public WebElement click(String locator_with_type, String error_message)
    {
        return click(locator_with_type, error_message, 5);
    }

    public WebElement sendKeys(String locator_with_typer, String value, String error_message, int timeoutInSecond)
    {
        WebElement element = waitForElementPresent(locator_with_typer, error_message, timeoutInSecond);
        element.sendKeys(value);
        return element;
    }

    public WebElement sendKeys(String locator_with_type, String value, String error_message)
    {
        return sendKeys(locator_with_type, value, error_message,5);
    }

    public WebElement sendKeysWithoutPaste(String locator_with_type, String error_message, String value)
    {
        WebElement element = waitForElementPresent(locator_with_type, error_message);
        ((AndroidElement)element).setValue(value);
        /*TouchAction action = new TouchAction((MobileDriver) waitUtils.getDriver());
        action.longPress(element).waitAction(2000).perform();
        element.sendKeys(value);
        */
        return element;
    }

    public WebElement clear(String locator_with_type, String error_message, int timeoutInSecond)
    {
        WebElement element = waitForElementPresent(locator_with_type, error_message, timeoutInSecond);
        element.clear();

        return element;
    }

    public WebElement clear(String locator_with_type, String error_message)
    {
        return clear(locator_with_type, error_message, 5);
    }

    public String getAttribute(String locator_with_type, String attribute_name, String error_message, int timeoutInSecond)
    {
        WebElement element = waitForElementPresent(locator_with_type, error_message, timeoutInSecond);
        return element.getAttribute(attribute_name);
    }

    public String getAttribute(String locator_with_type, String attribute_name, String error_message)
    {
        return getAttribute(locator_with_type, attribute_name, error_message, 5);
    }

    //***************************************************************
    public void assertElementHasText(String locator_with_type, String expected_text, String error_message) {
        By by = getLocatorByString(locator_with_type);

        String error_find_message = "Cannot web element by " + by.toString();
        WebElement element = waitForElementPresent(locator_with_type, error_find_message);
        Assert.assertTrue(error_message, element.getText().equals(expected_text));
    }

    public void assertElementNotPresent(String locator_with_type, String error_message) {
        By by = getLocatorByString(locator_with_type);
        int amount_of_elements = getAmountOfElements(locator_with_type);
        if (amount_of_elements > 0) {
            String default_message = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    public void assertElementsPresentNowByXpath(String locatorString, String error_message)
    {
        Assert.assertTrue(error_message, driver.findElements(By.xpath(locatorString)).size() != 0);
    }

    public void assertElementsPresent(String locator) {
        By by = getLocatorByString(locator);
        int amount_of_elements = getAmountOfElements(locator);
        if (amount_of_elements == 0) {
            String default_message = "An element '" + by.toString() + "' to be present";
            throw new AssertionError(default_message + " " + locator);
        }
    }

    //**************************************************************
    public void swipeUp(int timeOfSwipe)
    {
        if(driver instanceof AppiumDriver) {
            TouchAction action = new TouchAction((AppiumDriver) driver);
            Dimension size = driver.manage().window().getSize();

            int x = size.width / 2;
            int start_y = (int) (size.height * 0.8);
            int end_y = (int) (size.height * 0.2);

            action
                .press(new PointOption().point(x, start_y))
                .waitAction(new WaitOptions().withDuration(Duration.ofSeconds(timeOfSwipe)))
                .moveTo(new PointOption().point(x, end_y))
                .release()
                .perform();
        } else {
            System.out.println("Method swipeUp() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void swipeUpQuick()
    {
        swipeUp(200);
    }


    public void swipeUpToFindElement(String locator_with_type, String error_message, int max_swipe)
    {
        int already_swiped = 0;
        By by = getLocatorByString(locator_with_type);
        while (driver.findElements(by).size() == 0)
        {
            if (already_swiped > max_swipe)
            {
                waitForElementPresent(locator_with_type,
                    "Cannot find element by swiing up. \n" + error_message);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    public void swipeUpTillElementAppear(String locator, String error_message, int max_swipes)
    {
        int already_swiped = 0;

        while (!isElementLocatedOnTheScreen(locator))
        {
            if(already_swiped > max_swipes)
            {
                Assert.assertTrue(error_message, isElementLocatedOnTheScreen(locator));
            }

            swipeUpQuick();
            ++already_swiped;
        }
    }

    public boolean isElementLocatedOnTheScreen(String locator)
    {
        int element_location_by_y = waitForElementPresent(locator,
                "Cannot find element by locator", 1).getLocation().getY();

        if(Platform.getInstance().isMW()) {
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            Object js_result = JSExecutor.executeScript("return window.pageYOffset");
            element_location_by_y -= Integer.parseInt(js_result.toString());
        }

        int screen_size_by_y = driver.manage().window().getSize().getHeight(); //высота всего экрана

        return element_location_by_y < screen_size_by_y;
    }

    public void clickElementToTheRightUpperCorner (String locator, String error_message)
    {
        if(driver instanceof AppiumDriver) {
            WebElement element = waitForElementPresent(locator + "/..", error_message);
            int right_x = element.getLocation().getX();
            int upper_y = element.getLocation().getY();
            int lower_y = upper_y + element.getSize().getHeight();
            int middle_y = (upper_y + lower_y) / 2;
            int width = element.getSize().getWidth();

            int point_to_click_x = (right_x + width) - 3;
            int point_to_click_y = middle_y;

            TouchAction action = new TouchAction((AppiumDriver) driver);
            action.tap(new PointOption().point(point_to_click_x, point_to_click_y)).perform();//не уверена,что это правильно из-за различия абс. позиций и смещения
        } else {
            System.out.println("Method clickElementToTheRightUpperCorner() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }

   }

    public void swipeElementToLeft(String locator_with_type, String error_message)
    {
        if(driver instanceof AppiumDriver) {
            WebElement element = waitForElementPresent(locator_with_type, error_message, 5);
            int left_x = element.getLocation().getX();
            int right_x = left_x + element.getSize().getWidth();
            int upper_y = element.getLocation().getY();
            int lower_y = upper_y + element.getSize().getHeight();
            int middle_y = (upper_y + lower_y) / 2;

            TouchAction action = new TouchAction((AppiumDriver) driver);
            action.press(new PointOption().point(right_x, middle_y));
            action.waitAction(new WaitOptions().withDuration(Duration.ofMillis(300)));
            if (Platform.getInstance().isAndroid()) {
                action.moveTo(new PointOption().point(left_x, middle_y));
            } else {
                int offset_x = (-1 * element.getSize().getWidth());
                action.moveTo(new PointOption().point(offset_x, 0));//не уверена,что это правильно из-за различия абс. позиций и смещения
            }

            action.release();
            action.perform();
        } else {
            System.out.println("Method swipeElementToLeft() does nothing for platform " + Platform.getInstance().getPlatformVar());

        }
    }

    public void scrollWebPageUp() {
        if (Platform.getInstance().isMW()) {
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            JSExecutor.executeScript("window.scrollBy(0,520)");
        } else {
            System.out.println("Method scrollWebPageUp() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void scrollWebPageTillElementNotVisible(String locator, String error_message, int max_swipe) {
        int already_swipe = 0;

        WebElement element = waitForElementPresent(locator, error_message);
        while (!isElementLocatedOnTheScreen(locator)) {
            scrollWebPageUp();
            ++already_swipe;
            if (already_swipe > max_swipe) {
                Assert.assertTrue(error_message, element.isDisplayed());
            }
        }
    }

    public String takeScreenshot(String name) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/" + name + "screenshot.png";
        try {
            FileUtils.copyFile(source, new File(path));
            System.out.println("The screenshot was taken: " + path);
         } catch(Exception e) {
            System.out.println("Cannot take screenshot. Error: " + e.getMessage());
        }
        return path;
    }

    @Attachment
    public static byte[] screenshot(String path) {
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            System.out.println("Cannot get bytes from screenshot. Error: " + e.getMessage());
        }
        return bytes;
    }
}