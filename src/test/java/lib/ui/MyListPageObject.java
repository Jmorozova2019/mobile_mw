package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListPageObject extends MainPageObject{

    protected static String
        SAVED_BLOCK,
        FOLDER_BY_NAME_TPL,
        REMOVE_FROM_SAVED_BUTTON,
        ARTICLE_BY_TITLE_TPL;

    public MyListPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    private static String getFolderByName(String name_of_folder)
    {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleByTitle(String article_title)
    {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }

    private static String getRemoveButtonByTitle(String article_title)
    {
        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", article_title);
    }

    public void openFolderByName(String name_of_folder) throws InterruptedException {
        Thread.sleep(1000);
        String folder_name = getFolderByName(name_of_folder);
        click(folder_name, "Cannot find folder", 15);
    }

    public void clickSavedBlock()
    {
        click(SAVED_BLOCK, "Cannot find folder", 15);
    }

    public void swipeByArticleToDelete(String article_title) throws InterruptedException {
        waitForArticleToAppearByTitle(article_title);
        Thread.sleep(1000);

        String article_title_rpl = getSavedArticleByTitle(article_title);
        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            swipeElementToLeft(article_title_rpl, "Cannot find saved article");
        } else {
            String remove_locator = getRemoveButtonByTitle(article_title);
            click(remove_locator, "Cannot click button to remove article from saved", 10);
        }

        if (Platform.getInstance().isIOS()) {
            clickElementToTheRightUpperCorner(article_title_rpl, "Cannot find saved article");
        }

        if (Platform.getInstance().isMW()) {
            driver.navigate().refresh();
        }

        waitForArticleToDisappearByTitle(article_title);
    }


    public void removeArticleFromWatchlistByTitle(String article_title) throws InterruptedException {
        waitForArticleToAppearByTitle(article_title);
        String article_title_rpl = getRemoveButtonByTitle(article_title);

        if (Platform.getInstance().isMW()) {
            click(article_title_rpl,"Cannot click button to remove article from saved", 10);
            driver.navigate().refresh();
        }
        waitForArticleToDisappearByTitle(article_title);
    }


    public void waitForArticleToDisappearByTitle(String article_title)
    {
        String article_title_rpl = getSavedArticleByTitle(article_title);

        waitForElementNotPresent(article_title_rpl,
                "Saved article still present with title " + article_title,15);
    }

    public void waitForArticleToAppearByTitle(String article_title)
    {
        waitForElementPresent(getSavedArticleByTitle(article_title),
            "Cannot find saved article by title " + article_title, 15
        );
    }

    public void openArticleFromMyList(String article_title)
    {
        click(getSavedArticleByTitle(article_title),
            "Cannot find saved article by name " + article_title, 15);
    }
}
