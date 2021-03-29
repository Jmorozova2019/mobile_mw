package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;

import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;


public class SearchTests extends CoreTestCase
{
    @Test
    public void testSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("bject-oriented programming language");
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        String search_line = "Linkin Park Diskography";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_line);

        int amount_of_search_result = searchPageObject.getAmountOfFoundArticles();

        assertTrue(
            "We found two few results!",
            amount_of_search_result > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {
        String search_line = "Lkjhkjkjhghkjgkjhgjkgghj";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_line);
        searchPageObject.assertThereIsNotResultOfSearch();
    }

    @Test
    public void testCancelSearch_Ex3_after_refactoring() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        int amount_of_search_result = searchPageObject.getAmountOfFoundArticles();

        assertTrue("We found two few results!", amount_of_search_result > 0);

        searchPageObject.clickCancelSearch();
        searchPageObject.assertThereIsNotResultOfSearch();
    }

    @Test
    public void testSearchByTitleAndDescription_Ex4_after_refactoring() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");

        int amount_of_search_result = searchPageObject.getAmountOfFoundArticles();
        assertTrue("We found two few results!", amount_of_search_result > 2);

        searchPageObject.waitForElementByTitleAndDescription("Java",
                "Island of Indonesia");

        searchPageObject.waitForElementByTitleAndDescription("JavaScript",
                "Programming language");

        searchPageObject.waitForElementByTitleAndDescription("Java (programming language)",
                "Object-oriented programming language");
    }
}
