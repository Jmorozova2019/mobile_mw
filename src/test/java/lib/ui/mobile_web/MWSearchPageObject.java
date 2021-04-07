package lib.ui.mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "id:searchIcon";
        SEARCH_INPUT = "css:form>input[type='search']";
        SEARCH_CANCEL_BUTTON = "css:div>button.cancel";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://div[contains(@class,'wikidata-description') and contains(text(),'{SUBSTRING}')]";
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL = "css:ul.page-list>li.page-summary";
        SEARCH_RESULT_ELEMENT = "xpath://div[contains(@class, 'results-list-container')]//li";
        SEARCH_RESULT_VIEW = "css:div.search-results-view .results";
        SEARCH_RESULT_ARTICLE_TITLE = "css:ul.page-list>li[title]";
        SEARCH_NO_RESULT_ELEMENT = "xpath://p[text()='No page with this title.']";
    }

    public MWSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
