package tests;

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
import org.junit.Test;

import java.sql.Time;
import java.util.concurrent.TimeoutException;

import static java.lang.Thread.sleep;


public class MyListsTest extends CoreTestCase {

    //private static final String name_of_folder = "Learning programming";
    private static final String
            login = "ZhannaMorozova",
            password = "WikiJem2021#";

   @Test
    public void testSaveFirstArticleToMyList() throws InterruptedException {
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
            assertEquals("We are not on the same page after login", article_title, articlePageObject.getArticleTitle());

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
    public void testSaveTwoArticleAndDeleteOne_Ex17() throws InterruptedException {
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
            assertEquals("We are not on the same page after login", first_article_title, articlePageObject.getArticleTitle());
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
            assertEquals("We are not on the same page after login",
                    second_article_title, articlePageObject.getArticleTitle());
        }
    }
}
