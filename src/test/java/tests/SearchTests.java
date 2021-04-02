package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Features;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.SearchPageObject;

import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;


public class SearchTests extends CoreTestCase
{
    @Test
    @Severity(value = SeverityLevel.CRITICAL)
    @Features(value = {@Feature(value="Search")})
    @DisplayName("Search article by name")
    public void testSearch() throws InterruptedException {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("bject-oriented programming language");
    }

    @Test
    @Severity(value = SeverityLevel.MINOR)
    @Features(value = {@Feature(value="Search")})
    @DisplayName("Cancel search")
    public void testCancelSearch() throws InterruptedException {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        if (!Platform.getInstance().isMW()) {
            searchPageObject.clickCancelSearch();
            searchPageObject.waitForCancelButtonToDisappear();
        }
    }

    @Test
    @Features(value = {@Feature(value="Search")})
    @Severity(value = SeverityLevel.CRITICAL)
    @DisplayName("Amount of not empty search")
    public void testAmountOfNotEmptySearch() throws InterruptedException {
        String search_line = "Linkin Park Diskography";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_line);

        int amount_of_search_result = searchPageObject.getAmountOfFoundArticles();

        Assert.assertTrue(
            "We found two few results!",
            amount_of_search_result > 0
        );
    }

    @Test
    @Features(value = {@Feature(value="Search")})
    @Severity(value = SeverityLevel.CRITICAL)
    @DisplayName("Amount of empty search")
    public void testAmountOfEmptySearch() throws InterruptedException {
        String search_line = "Lkjhkjkjhghkjgkjhgjkgghj";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_line);
        searchPageObject.assertThereIsNotResultOfSearch();
    }

    @Test
    @Features(value = {@Feature(value="Search")})
    @Severity(value = SeverityLevel.MINOR)
    public void testCancelSearch_Ex3() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        int amount_of_search_result = searchPageObject.getAmountOfFoundArticles();

        Assert.assertTrue("We found two few results!", amount_of_search_result > 0);
        if (!Platform.getInstance().isMW()) {
            searchPageObject.clickCancelSearch();
            searchPageObject.assertThereIsNotResultOfSearch();
        }
    }

    @Test
    @Features(value = {@Feature(value="Search")})
    @Severity(value = SeverityLevel.CRITICAL)
    @DisplayName("Search article by title and description")
    public void testSearchByTitleAndDescription_Ex4() {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");

        int amount_of_search_result = searchPageObject.getAmountOfFoundArticles();
        Assert.assertTrue("We found two few results!", amount_of_search_result > 2);

        searchPageObject.waitForElementByTitleAndDescription("Java",
                "Island of Indonesia");

        searchPageObject.waitForElementByTitleAndDescription("JavaScript",
                "Programming language");

        searchPageObject.waitForElementByTitleAndDescription("Java (programming language)",
                "Object-oriented programming language");
    }
}
