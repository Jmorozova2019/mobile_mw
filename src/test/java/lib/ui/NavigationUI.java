package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;


abstract public class NavigationUI extends MainPageObject{

    protected static String
            MY_LIST_LINK,
            SAVED_BUTTON,
            OPEN_MAIN_MENU;

    public NavigationUI(RemoteWebDriver driver)
    {
        super(driver);
    }

    public void openNavigation() {
        if (Platform.getInstance().isMW()) {
            click(OPEN_MAIN_MENU, "Cannot find and click open navigation button", 5);
        }
        System.out.println("Method openNavigation() does nothing for platform " + lib.Platform.getInstance().getPlatformVar());
    }

    public void clickMyList() {
        if (Platform.getInstance().isMW()) {
            tryClickElementWithFewAttempt(MY_LIST_LINK, "Cannot find Watchlist button inside main menu", 5);
        } else {
            click(MY_LIST_LINK,"Cannot find navigation button to My list",5);
        }
    }

    public void clickSavedButton()
    {
        click(SAVED_BUTTON, "Cannot find saved button");
    }
}
