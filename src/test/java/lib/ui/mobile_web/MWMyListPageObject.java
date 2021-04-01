package lib.ui.mobile_web;

import lib.ui.MyListPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWMyListPageObject extends MyListPageObject {
    static {
        ARTICLE_BY_TITLE_TPL = "xpath://h3[text()='{TITLE}']/..";
        REMOVE_FROM_SAVED_BUTTON =
                "xpath://li[contains(@class,'watchstar') and @title='{TITLE}']/a[contains(@href,'action=unwatch')]";
    }

    public MWMyListPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
