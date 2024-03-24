package framework.pages.swagLabShop.checkoutFlow;

import framework.pages.BasePage;
import framework.pages.swagLabShop.constants.SwagLabUrls;
import framework.pages.swagLabShop.fragments.ProductCardFragment;
import framework.pages.swagLabShop.fragments.ShoppingCartFragment;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The Class represents Swag Shop Checkout step two page.
 *
 * @author Serhii R.
 */
public class CheckoutStepTwoPage extends BasePage {

    @FindBy(xpath = "//*[@class='title'][.='Checkout: Overview']")
    private WebElement pageTitle;

    @FindBy(id = "finish")
    private WebElement finishButton;

    private ShoppingCartFragment shoppingCartFragment;
    private ProductCardFragment productCardFragment;
    /**
     * Instantiates a new base page.
     *
     * @param driver the driver
     */
    public CheckoutStepTwoPage(WebDriver driver) {
        super(driver);
        this.shoppingCartFragment = new ShoppingCartFragment(driver);
        this.productCardFragment = new ProductCardFragment(driver);
    }

    public boolean isTitleDisplayed() {
        return pageTitle.isDisplayed();
    }

    public void clickFinish() {
        finishButton.click();
    }

    public String getExpectedUrl() {
        return SwagLabUrls.CHECKOUT_STEP_TWO_PAGE.getUrl();
    }


}
