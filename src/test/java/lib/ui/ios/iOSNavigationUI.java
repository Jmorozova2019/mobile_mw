package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSNavigationUI extends NavigationUI {

    static { MY_LIST_LINK = "id:Saved"; }

    public iOSNavigationUI(RemoteWebDriver driver) { super(driver); }
}
