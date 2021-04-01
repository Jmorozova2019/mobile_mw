package lib.ui.mobile_web;

import lib.ui.MainPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AutorizationPageObject extends MainPageObject {

    private static final String
        MAIN_MENU_BUTTON = "id:mw-mf-main-menu-button",
        LOGIN_BUTTON = "css:a[data-event-name='menu.login']",
        LOGIN_INPUT = "id:wpName1",
        PASSWORD_INPUT = "id:wpPassword1",
        SUBMIT_BUTTON = "id:wpLoginAttempt";

    public AutorizationPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void openMainMenu(){
        click(MAIN_MENU_BUTTON, "Cannot main menu button", 5);//здесь уже есть waitForElementPresent
    }

    public void clickAuthButton() throws InterruptedException {
        Thread.sleep(2000);
        click(LOGIN_BUTTON, "Cannot find and click log in button", 5);//здесь уже есть waitForElementPresent
    }

    public void enterLoginData(String login, String password) throws InterruptedException {
        Thread.sleep(2000);
        sendKeys(LOGIN_INPUT, login, "Cannot find and put a login to the login input", 5);
        sendKeys(PASSWORD_INPUT, password, "Cannot find and put a password to the password input", 5);
    }

    public void submitForm() {
        click(SUBMIT_BUTTON, "Cannot find and click submit button", 5);
    }
}
