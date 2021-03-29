package tasks;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;

import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import org.openqa.selenium.ScreenOrientation;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CheckRotate extends CoreTestCase
{
    @Test
    public void testARotateToLanscape()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        rotateScreenLandscape();
    }

    @Test
    public void testBPortrateOrientation_Ex7()
    {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        //Assert.assertTrue(driver.getOrientation().equals(ScreenOrientation.PORTRAIT));
    }
}
