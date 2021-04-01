package lib.ui.mobile_web;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWNavigatorUI  extends NavigationUI {
    static {
        MY_LIST_LINK = "css:a[data-event-name='menu.unStar']";
        OPEN_MAIN_MENU = "id:mw-mf-main-menu-button";
    }

    public MWNavigatorUI(RemoteWebDriver driver) {
        super(driver);
    }
}
