package lib.ui.mobile_web;

import lib.ui.MainPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AutorizationPageObject extends MainPageObject {

    private static final String
        MAIN_MENU_BUTTON = "css:#mw-mf-main-menu-button",
        LOGIN_BUTTON = "css:[data-event-name='menu.login']",
        LOGIN_INPUT = "css:input[name='wpName1']",
        PASSWORD_INPUT = "css:input[name='wpPassword2']",
        SUBMIT_BUTTON = "css:button#wpLoginAttempt";

    public AutorizationPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void openMainMenu(){
        click(MAIN_MENU_BUTTON, "Cannot main menu button", 5);//здесь уже есть waitForElementPresent
    }

    public void clickAuthButton() {
        click(LOGIN_BUTTON, "Cannot find and click log in button", 5);//здесь уже есть waitForElementPresent
    }

    public void enterLoginData(String login, String password) {
        sendKeys(LOGIN_INPUT, login, "Cannot find and put a login to the login input", 5);
        sendKeys(PASSWORD_INPUT, password, "Cannot find and put a password to the password input", 5);
    }

    public void submitForm() {
        click(SUBMIT_BUTTON, "Cannot find and click submit button", 5);
    }
}
