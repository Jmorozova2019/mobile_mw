package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidNavigationUI extends NavigationUI {
    static {
        MY_LIST_LINK = "xpath://android.widget.FrameLayout[@content-desc='My lists']/android.widget.ImageView";
        SAVED_BUTTON = "xpath://android.widget.FrameLayout[@content-desc='Saved']/android.widget.ImageView";
    }

    public AndroidNavigationUI(RemoteWebDriver driver) {
        super(driver);
    }
}
