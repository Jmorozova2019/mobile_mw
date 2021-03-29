package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidArticlePageObject extends ArticlePageObject {
    static {
        TITLE = "xpath://*[@resource-id='pcs-edit-section-title-description']/preceding-sibling::android.view.View";
        FOOTER_ELEMENT = "xpath://*[@text='View page in browser']";
        OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']";
        SAVE_BUTTON = "id:org.wikipedia:id/article_menu_bookmark";
        NOTIFICATION_ADDED_TO_LIST = "xpath://*[contains(@text,'Saved Java')]";
        REMOVE_FROM_SAVED = "xpath://*[@text=Remove from Saved]";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://*[@text='Add to reading list']";
        ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button";
        MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input";
        MY_LIST_OK_BUTTON = "xpath://*[@text='OK']";
        CLOSE_ARTICLE_BUTTON = "xpath://*[@content-desc='Navigate up']";
        CLOSE_SEARCH_ARTICLE_LIST = "xpath://*[@resource-id='org.wikipedia:id/search_toolbar']/android.widget.ImageButton";
        SEARCH_LANG_BUTTON = "xpath://*[@resource-id='org.wikipedia:id/search_lang_button']";
    }

    public AndroidArticlePageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

}
