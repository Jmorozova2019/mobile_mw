package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;

import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import lib.ui.mobile_web.AutorizationPageObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.sql.Time;
import java.util.concurrent.TimeoutException;

import static java.lang.Thread.sleep;


public class MyListsTest extends CoreTestCase {

    //private static final String name_of_folder = "Learning programming";
    private static final String
            login = "ZhannaMorozova",
            password = "WikiJem2021#";

   @Test
   @Features(value = {@Feature(value="Search"), @Feature(value="Article"), @Feature(value="SavedList")})
   @DisplayName("Save first article to my list")
   @Description("Find article by search text Java, add to saved list and remove atricle from saved list")
   @Step("Starting test testSaveFirstArticleToMyList")
   @Severity(value = SeverityLevel.NORMAL)
   public void testSaveFirstArticleToMyList() throws InterruptedException, IOException {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        String article_title = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
            articlePageObject.addArticleToMySaved();
            articlePageObject.closeArticle();
            articlePageObject.closeSearchArticleList();//выйти из списка статей по кнопке Назад

            NavigationUI navigationUI = NavigationUIFactory.get(driver);
            //navigationUI.clickMyList();
            navigationUI.clickSavedButton();

            MyListPageObject myListPageObject = MyListPageObjectFactory.get(driver);
            myListPageObject.swipeByArticleToDelete(article_title);

            if (Platform.getInstance().isAndroid()) {
                //myListPageObject.openFolderByName(name_of_folder);
                myListPageObject.clickSavedBlock();//возможно, в iOS так же
            }
        } else if (Platform.getInstance().isMW()) {
            AutorizationPageObject auth = new AutorizationPageObject(driver);
            auth.openMainMenu();
            auth.clickAuthButton();
            auth.enterLoginData(login, password);
            auth.submitForm();

            articlePageObject.waitForTitleElement();
            articlePageObject.takeScreenshot("ArticlePage");
            Assert.assertEquals("We are not on the same page after login", article_title, articlePageObject.getArticleTitle());

            articlePageObject.addArticleToMySaved();

            //проверить, что статья сохранена
            NavigationUI navigationUI = NavigationUIFactory.get(driver);
            navigationUI.openNavigation();
            navigationUI.clickMyList();//открыть watchlist

            //убедиться, что есть статья с таким загловком
            MyListPageObject myListPageObject = MyListPageObjectFactory.get(driver);
            myListPageObject.waitForArticleToAppearByTitle(article_title);

            //удалить статью и убедиться, что в списке нет статьи с таким заголовком
            myListPageObject.removeArticleFromWatchlistByTitle(article_title);
        }
    }

    @Test
    @Features(value = {@Feature(value="Search"), @Feature(value="Article"), @Feature(value="SavedList")})
    @DisplayName("Save two article to my list and remove first article")
    @Description("Find two article by search text Java, add to saved list and remove first atricle from saved list")
    @Step("Starting test testSaveTwoArticleAndDeleteOne")
    @Severity(value = SeverityLevel.NORMAL)
    public void testSaveTwoArticleAndDeleteOne_Ex17() throws InterruptedException, IOException {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        String first_article_title = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
            articlePageObject.addArticleToMySaved();
            articlePageObject.closeArticle();
            searchPageObject.clickByArticleWithSubstring("High-level programming language");

            articlePageObject.waitForTitleElement();
            String second_article_title = articlePageObject.getArticleTitle();

            articlePageObject.addArticleToMySaved();
            articlePageObject.closeArticle();
            articlePageObject.closeSearchArticleList();//выйти из списка статей по кнопке Назад

            NavigationUI navigationUI = NavigationUIFactory.get(driver);
            navigationUI.clickSavedButton();

            MyListPageObject myListPageObject = MyListPageObjectFactory.get(driver);
            if (Platform.getInstance().isAndroid()) {
                myListPageObject.clickSavedBlock();
            }

            myListPageObject.swipeByArticleToDelete(first_article_title);
            myListPageObject.waitForArticleToAppearByTitle(second_article_title);
            myListPageObject.openArticleFromMyList(second_article_title);

            articlePageObject.waitForTitleElement();
            articlePageObject.isArticleTitleEqualsExpected(second_article_title);

        } else if (Platform.getInstance().isMW()) {
            AutorizationPageObject auth = new AutorizationPageObject(driver);
            auth.openMainMenu();
            auth.clickAuthButton();
            auth.enterLoginData(login, password);
            auth.submitForm();

            articlePageObject.waitForTitleElement();
            Assert.assertEquals("We are not on the same page after login", first_article_title, articlePageObject.getArticleTitle());
            articlePageObject.addArticleToMySaved();

            //найти и добавить вторую статью
            searchPageObject.initSearchInput();
            searchPageObject.typeSearchLine("Java");
            searchPageObject.clickByArticleWithSubstring("igh-level programming language");
            articlePageObject.addArticleToMySaved();

            articlePageObject.waitForTitleElement();
            String second_article_title = articlePageObject.getArticleTitle();

            //открыть watchlist
            NavigationUI navigationUI = NavigationUIFactory.get(driver);
            navigationUI.openNavigation();
            navigationUI.clickMyList();

            //убедиться, что есть статьи с такими загловками
            MyListPageObject myListPageObject = MyListPageObjectFactory.get(driver);
            myListPageObject.waitForArticleToAppearByTitle(first_article_title);
            myListPageObject.waitForArticleToAppearByTitle(second_article_title);

            //удалить статью и убедиться, что в списке нет статьи с таким заголовком
            myListPageObject.removeArticleFromWatchlistByTitle(first_article_title);
            myListPageObject.waitForArticleToAppearByTitle(second_article_title);
            myListPageObject.openArticleFromMyList(second_article_title);

            //проверить, что сохранилась именно нужная статья
            articlePageObject.waitForTitleElement();
            Assert.assertEquals("We are not on the same page after login",
                    second_article_title, articlePageObject.getArticleTitle());
        }
    }
}
