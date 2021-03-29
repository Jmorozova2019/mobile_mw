package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WelcomePageObject extends MainPageObject{

    private static final String
    STEP_LEARN_MORE_LINK = "id:Learn more about Wikipedia",
    STEP_NEW_WAYS_TO_EXPLORE_TEXT = "id:New way to explore",
    STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK = "id:Add or edit preferred languages",
    STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK = "id:Learn more about data collected",
    NEXT_LINK = "id:Next",
    GET_STARTED_BUTTON = "id:Get started",
    SKIP_IOS = "id:Skip",
    SKIP_ANDROID = "id:org.wikipedia:id/fragment_onboarding_skip_button";

    public WelcomePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void waitForLearnMoreLink()
    {
        waitForElementPresent(STEP_LEARN_MORE_LINK,
            "Cannot find 'Learn more about Wikipedia' link",10);
    }

    public void waitForNewWayToExploreText()
    {
        waitForElementPresent(STEP_NEW_WAYS_TO_EXPLORE_TEXT,
            "Cannot find 'New way to explore' link",10);
    }

    public void waitForAddOrEditPrefferedLangText()
    {
        waitForElementPresent(STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK,
            "Cannot find 'Add or edit preffered languages' link",10);
    }

    public void waitForLearnMoreAboutDataCollectedText()
    {
        waitForElementPresent(STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK,
            "Cannot find 'Learn more about data collected' link",10);
    }

    public void clickNextButton()
    {
        clear(NEXT_LINK,"Cannot find and click 'Next' link",10);
    }

    public void clickGetStartedButton()
    {
        clear(GET_STARTED_BUTTON,"Cannot find and click 'Get started' link",10);
    }

    //Почему-то у меня Андроид тоже стартует со страницы приветствия
    public void clickSkip()
    {
        if (Platform.getInstance().isIOS()) {
            click(SKIP_IOS, "Cannot find and click skip button", 5);
        } else {
            click(SKIP_ANDROID, "Cannot find and click skip button", 5);
        }
    }
}
