package framework.pages.swagLabShop.checkoutFlow;

import framework.pages.BasePage;
import framework.pages.swagLabShop.constants.SwagLabUrls;
import framework.pages.swagLabShop.fragments.ShoppingCartFragment;
import framework.util.DataManipulationUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The Class represents Swag Shop Checkout step one page.
 *
 * @author Serhii R.
 */
public class CheckoutStepOnePage extends BasePage {

    @FindBy(xpath = "//*[@class='title'][.='Checkout: Your Information']")
    private WebElement pageTitle;

    @FindBy(id = "first-name")
    private WebElement firstNameInput;

    @FindBy(id = "last-name")
    private WebElement lastNameInput;

    @FindBy(id = "postal-code")
    private WebElement zipInput;

    @FindBy(id="continue")
    private WebElement continueButton;

    private ShoppingCartFragment shoppingCartFragment;
    /**
     * Instantiates a new base page.
     *
     * @param driver the driver
     */
    public CheckoutStepOnePage(WebDriver driver) {
        super(driver);
        this.shoppingCartFragment = new ShoppingCartFragment(driver);
    }

    public boolean isTitleDisplayed() {
        return pageTitle.isDisplayed();
    }
    public void setRandomUserDataAndContinue() {
        setRandomFirstName();
        setRandomLastName();
        setRandomZipCode();
        clickContinue();
    }
    public CheckoutStepOnePage setRandomFirstName() {
        setFirstName(new DataManipulationUtils().generateRandomFirstName());
        return this;
    }
    public CheckoutStepOnePage setRandomLastName() {
        setLastName(new DataManipulationUtils().generateRandomLastName());
        return this;
    }
    public CheckoutStepOnePage setRandomZipCode() {
        setZipPostalCode(new DataManipulationUtils().generateRandomPostalCode());
        return this;
    }
    public CheckoutStepOnePage setFirstName(String firstname) {
        firstNameInput.clear();
        firstNameInput.sendKeys(firstname);
        return this;
    }
    public CheckoutStepOnePage setLastName(String lastname) {
        lastNameInput.clear();
        lastNameInput.sendKeys(lastname);
        return this;
    }
    public CheckoutStepOnePage setZipPostalCode(String zipCode) {
        zipInput.clear();
        zipInput.sendKeys(zipCode);
        return this;
    }

    public void clickContinue() {
        continueButton.click();
    }
    public String getExpectedUrl() {
        return SwagLabUrls.CHECKOUT_STEP_ONE_PAGE.getUrl();
    }

}
