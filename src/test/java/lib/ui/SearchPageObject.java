package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;


public abstract class SearchPageObject extends MainPageObject{

     protected static String
        SEARCH_INIT_ELEMENT,
        SEARCH_INPUT,
        SEARCH_CANCEL_BUTTON,
        SEARCH_RESULT_BY_SUBSTRING_TPL,
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL,
        SEARCH_RESULT_ELEMENT;

    public SearchPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    /* Template */
    private static String getResultSearchElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchElementByTitleAndByDescription(String title, String description)
    {
        String search_result_locator_after_replace_title =
            SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL.replace("{SUBSTRING_TITLE}", title);
        return search_result_locator_after_replace_title.replace("{SUBSTRING_DESCRIPTION}", description);
    }
    /* Template */

    //Search
    public void initSearchInput()
    {
        click(SEARCH_INIT_ELEMENT, "Cannot find and click search init element");
        clear(SEARCH_INIT_ELEMENT, "Cannot find and click search init element");
        waitForElementPresent(SEARCH_INPUT, "Cannot find search input after clicking search init element");
    }

    public void typeSearchLine(String search_line)
    {
        sendKeys(SEARCH_INPUT, search_line, "Cannot find and type into search input");
    }

    public void waitForSearchResult(String substring)
    {
        String string_result_xpath = getResultSearchElement(substring);
        waitForElementPresent(string_result_xpath, "Cannot find search result with substring " + substring);
    }

    public void clickByArticleWithSubstring(String substring)
    {
        String string_result_xpath = getResultSearchElement(substring);
        click(string_result_xpath, "Cannot find and click search result with substring " + substring);
    }
    //---End search

    //Cancel
    public void waitForCancelButtonToAppear()
    {
        waitForElementPresent(SEARCH_CANCEL_BUTTON,"Cannot find search cancel button");
    }

    public void waitForCancelButtonToDisappear()
    {
        waitForElementNotPresent(SEARCH_CANCEL_BUTTON,"Search cancel button is still present",20);
    }

    public void clickCancelSearch()
    {
        click(SEARCH_CANCEL_BUTTON,"Cannot find and click search cancel button");
    }
    //---End Cancel

    public int getAmountOfFoundArticles()
    {
        waitForElementPresent(SEARCH_RESULT_ELEMENT, "Cannot find anything by the request");
        return getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void assertThereIsNotResultOfSearch()
    {
        assertElementNotPresent(SEARCH_RESULT_ELEMENT,"We supported not to find any result");
    }

    public void waitForElementByTitleAndDescription(String title, String description)
    {
        String locator = getResultSearchElementByTitleAndByDescription(title, description);

        waitForElementPresent(locator,
            "Cannot find anything article by title " + title + "and description " + description );
    }
}
