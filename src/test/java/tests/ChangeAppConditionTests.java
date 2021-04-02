package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Features;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.time.Duration;

public class ChangeAppConditionTests extends CoreTestCase
{
    @Test
    @Severity(value = SeverityLevel.MINOR)
    @DisplayName("Check search results view after change screen orientation")
    public void testChangeScreenOrientationOnSearchResults() throws IOException {
        if (Platform.getInstance().isMW()) {
            return;
        }

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        String title_before_rotation = articlePageObject.getArticleTitle();

        rotateScreenLandscape();

        String title_after_rotation = articlePageObject.getArticleTitle();
        Assert.assertEquals(
            "Article title have been changed after screen rotation",
            title_before_rotation,
            title_after_rotation
        );

        rotateScreenPotrait();

        String title_after_secont_rotation = articlePageObject.getArticleTitle();
        Assert.assertEquals(
            "Article title have been changed after screen rotation",
            title_before_rotation,
            title_after_secont_rotation
        );
    }

    @Test
    @Severity(value = SeverityLevel.MINOR)
    @DisplayName("Check run application in background")
    public void testRunAppInBackground()
    {
        if (Platform.getInstance().isMW()) {
            return;
        }

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("bject-oriented programming language");
        backgroundApp(Duration.ofSeconds(2));
        searchPageObject.waitForSearchResult("bject-oriented programming language");
    }
}
