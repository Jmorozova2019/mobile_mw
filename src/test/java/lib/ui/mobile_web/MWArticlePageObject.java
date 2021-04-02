package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {
    static {
        TITLE = "xpath://*[@class='page-heading']/h1";
        FOOTER_ELEMENT = "css:footer";
        SAVE_BUTTON = "id:ca-watch";
        NOTIFICATION_ADDED_TO_LIST = "id:mw-notification-area";
        OPTION_REMOVE_FROM_MY_LIST_BUTTON = "xpath://a[@id='ca-watch' and (contains(@title,'Remove') or @title='Unwatch')]";
        OPTIONS_ADD_TO_MY_LIST_BUTTON     = "xpath://a[@id='ca-watch' and (contains(@title, 'Add') or @title='Watch')]";
    }

    public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
