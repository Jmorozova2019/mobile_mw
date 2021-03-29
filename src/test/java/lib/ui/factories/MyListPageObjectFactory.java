package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.MyListPageObject;
import lib.ui.android.AndroidArticlePageObject;
import lib.ui.android.AndroidMyListPageObject;
import lib.ui.ios.iOSArticlePageObject;
import lib.ui.ios.iOSMyListPageObject;
import lib.ui.mobile_web.MWMyListPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MyListPageObjectFactory {
    public static MyListPageObject get(RemoteWebDriver driver)
    {
        if(Platform.getInstance().isAndroid()){
            return new AndroidMyListPageObject(driver);
        } else if (Platform.getInstance().isIOS()){
            return new iOSMyListPageObject(driver);
        } else {
            return new MWMyListPageObject(driver);
        }
    }
}
