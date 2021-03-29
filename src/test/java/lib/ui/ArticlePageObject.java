package lib.ui;

import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class ArticlePageObject extends MainPageObject {
    protected static String
        TITLE,
        FOOTER_ELEMENT,
        OPTIONS_BUTTON,
        SAVE_BUTTON,
        NOTIFICATION_ADDED_TO_LIST,
        REMOVE_FROM_SAVED,
        OPTION_REMOVE_FROM_MY_LIST_BUTTON,
        OPTIONS_ADD_TO_MY_LIST_BUTTON,
        ADD_TO_MY_LIST_OVERLAY,
        MY_LIST_NAME_INPUT,
        MY_LIST_OK_BUTTON,
        CLOSE_ARTICLE_BUTTON,
        CLOSE_SEARCH_ARTICLE_LIST,
        SEARCH_LANG_BUTTON;

    public ArticlePageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    public WebElement waitForTitleElement()
    {
        return waitForElementPresent(TITLE, "Cannot find article title in page", 15);
    }

    public String getArticleTitle()
    {
        WebElement titleElement = waitForTitleElement();
        if(Platform.getInstance().isAndroid()) {
            return titleElement.getAttribute("text");
        } else if (Platform.getInstance().isIOS()){
            return titleElement.getAttribute("name");
        } else {
            return titleElement.getText();
        }
    }

    public  void swipeToFooter()
    {
        if (Platform.getInstance().isAndroid()){
            swipeUpToFindElement(FOOTER_ELEMENT,"Cannot find end of article", 40);
        } else if (Platform.getInstance().isIOS()) {
            swipeUpTillElementAppear(FOOTER_ELEMENT, "Cannot find end of article", 40);
        } else {
            scrollWebPageTillElementNotVisible(FOOTER_ELEMENT, "Cannot find end of article", 40);
        }
    }

    public void addArticleToMyList(String name_of_folder)
    {
        click(OPTIONS_BUTTON, "Cannot find button to open article options");

        waitForElementNotPresent(SEARCH_LANG_BUTTON,"Find button change language");
        click(OPTIONS_ADD_TO_MY_LIST_BUTTON,"Cannot find option to add article to reading list");
        click(ADD_TO_MY_LIST_OVERLAY,"Cannot find 'GOT IT' tip overlay");
        clear(MY_LIST_NAME_INPUT, "Cannot find input to set name of articles folder");
        sendKeysWithoutPaste(MY_LIST_NAME_INPUT,"Cannot put text into articles folder input", name_of_folder);
        click(MY_LIST_OK_BUTTON,"Cannot press OK button");
    }
/*
    public void addSecondArticleToMyList(String name_of_folder)
    {
        click( OPTIONS_BUTTON,"Cannot find button to open article options");
        //чтобы не открывалось окно смены языка
        waitForElementNotPresent(SEARCH_LANG_BUTTON,"Find button change language");
        click(OPTIONS_ADD_TO_MY_LIST_BUTTON,"Cannot find option to add article to reading list");
        click("//*[@resource-id='org.wikipedia:id/item_title' and @text='" + name_of_folder +"']",
            "Cannot folder " + name_of_folder + " to list folders"
        );
    }*/

    public void addArticleToMySaved()
    {
        if (Platform.getInstance().isMW()) {
            removeArticleFromSavedIfAdded();
        }
        //click(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find option to add article to reading list", 5);
        click(SAVE_BUTTON, "Cannot find button to open article options");
        waitForElementPresent(NOTIFICATION_ADDED_TO_LIST,"Cannot find notification of saved article");
        waitForElementNotPresent(NOTIFICATION_ADDED_TO_LIST,"No close notification of saved article");
    }

    public void closeArticle()
    {
        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            click(CLOSE_ARTICLE_BUTTON,"Cannot close article, cannot find back button");
        } else {
            System.out.println("Method closeArticle() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void closeSearchArticleList()
    {
        click(CLOSE_SEARCH_ARTICLE_LIST,"Cannot close article list, cannot find back button");
    }

    public void isArticleTitleEqualsExpected(String expected_title)
    {
        String article_title_fact = getArticleTitle();
        Assert.assertTrue(article_title_fact.equals(expected_title));
    }

    public void assertElementsPresentNowByXpath(String error_message)
    {
        assertElementsPresentNowByXpath(TITLE, error_message);
    }

    public void removeArticleFromSavedIfAdded() {
        if (isElementPresent(OPTION_REMOVE_FROM_MY_LIST_BUTTON)) {
            click(OPTION_REMOVE_FROM_MY_LIST_BUTTON,
                "Cannot click button to remove an article from saved",
                1);
            waitForElementPresent(OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find button to add an article to saved list after removing it from this list befor",
                1);
        }
    }

}
