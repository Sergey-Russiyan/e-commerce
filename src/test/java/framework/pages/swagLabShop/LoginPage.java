package framework.pages.swagLabShop;

import framework.pages.BasePage;
import framework.pages.swagLabShop.constants.SwagLabUrls;
import framework.util.LoggerUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The Class represents Swag Shop login page.
 *
 * @author Serhii R.
 */
public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(xpath = "//*[@id='login_credentials']")
    private WebElement availableUsername;

    @FindBy(xpath = "//*[@class='login_password']")
    private WebElement availablePassword;

    public LoginPage goTo() {
        driver.get(SwagLabUrls.LOGIN_PAGE.getUrl());
        return this;
    }
    public void tryLoginUsingCredentialsFromPage() {
        usernameInput.sendKeys(tryGetUserNameFromPage());
        passwordInput.sendKeys(tryGetPasswordFromPage());
        loginButton.click();
    }

    /**
     * Util method for extracting login credentials from login page
     *
     */
    private String tryGetTextFromElement(WebElement element) {
        try {
            String text = element.getText();
            if (text != null && !text.isEmpty()) {
                return text.split("\n")[1]; // skipping title
            } else {
                LoggerUtil.getLogger().error("Login page. Check availability of element: " + element);
            }
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            // Log error message for any exceptions
            LoggerUtil.getLogger().error("Login page. Credentials not empty. Check text order: " + e.getMessage());
        }
        return null; // Return null if an error occurs
    }

    //TODO {link to relevant JIRA task} | create fallback login method - if there are no login credentials in UI - then use creds from properties file
    private String tryGetUserNameFromPage() {
        return tryGetTextFromElement(availableUsername);
    }

    private String tryGetPasswordFromPage() {
        return tryGetTextFromElement(availablePassword);
    }
    public String getExpectedUrl() {
        return SwagLabUrls.LOGIN_PAGE.getUrl();
    }

    /**
     * Instantiates a new base page.
     *
     * @param driver the driver
     */
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
}
