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


public class MyListsTest extends CoreTestCase {

    private static final String name_of_folder = "Learning programming";
    private static final String
        login = "ZhannaMorozova",
        password = "";

    @Test
    public void testSaveFirstArticleToMyList() throws InterruptedException {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        String article_title = articlePageObject.getArticleTitle();
        articlePageObject.addArticleToMySaved();

        if (Platform.getInstance().isMW()) {
            AutorizationPageObject auth = new AutorizationPageObject(driver);
            auth.clickAuthButton();
            auth.enterLoginData(login, password);
            auth.submitForm();

            articlePageObject.waitForTitleElement();

            assertEquals("We are not on the same page after login", article_title, articlePageObject.getArticleTitle());

            articlePageObject.addArticleToMySaved();
        }

        articlePageObject.closeArticle();
        articlePageObject.closeSearchArticleList();//выйти из списка статей по кнопке Назад

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();
        //navigationUI.clickMyList();
        navigationUI.clickSavedButton();

        MyListPageObject myListPageObject = MyListPageObjectFactory.get(driver);

        //Блок Saved - в списке есть статья
        if (Platform.getInstance().isAndroid()) {
            //myListPageObject.openFolderByName(name_of_folder);
            myListPageObject.clickSavedBlock();//возможно, в iOS так же
        }

        myListPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    public void testSaveTwoArticleAndDeleteOne_Ex5_Ex11() throws InterruptedException {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        String first_article_title = articlePageObject.getArticleTitle();
        //String name_of_folder = "Learning programming";
        //articlePageObject.addArticleToMyList(name_of_folder);
        articlePageObject.addArticleToMySaved();
        articlePageObject.closeArticle();
        //articlePageObject.closeSearchArticleList();//выйти из списка статей по кнопке Назад

        //searchPageObject.initSearchInput();
        //searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("High-level programming language");

        articlePageObject.waitForTitleElement();
        String second_article_title = articlePageObject.getArticleTitle();
        //articlePageObject.addSecondArticleToMyList(name_of_folder);

        articlePageObject.addArticleToMySaved();
        articlePageObject.closeArticle();
        articlePageObject.closeSearchArticleList();//выйти из списка статей по кнопке Назад

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        //navigationUI.clickMyList();
        navigationUI.clickSavedButton();

        MyListPageObject myListPageObject = MyListPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            //myListPageObject.openFolderByName(name_of_folder);
            myListPageObject.clickSavedBlock();
        }

        myListPageObject.swipeByArticleToDelete(first_article_title);
        myListPageObject.waitForArticleToAppearByTitle(second_article_title);
        myListPageObject.openArticleFromMyList(second_article_title);

        articlePageObject.waitForTitleElement();
        articlePageObject.isArticleTitleEqualsExpected(second_article_title);
    }
}
