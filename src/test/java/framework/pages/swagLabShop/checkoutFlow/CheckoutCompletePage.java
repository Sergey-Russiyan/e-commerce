package framework.pages.swagLabShop.checkoutFlow;

import framework.pages.BasePage;
import framework.pages.swagLabShop.constants.SwagLabUrls;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The Class represents Swag Shop Checkout Complete status page.
 *
 * @author Serhii R.
 */
public class CheckoutCompletePage extends BasePage {

    @FindBy(xpath = "//*[@class='title'][.='Checkout: Complete!']")
    private WebElement pageTitle;

    /**
     * Instantiates a new base page.
     *
     * @param driver the driver
     */
    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    public String getExpectedUrl() {
        return SwagLabUrls.CHECKOUT_COMPLETE_PAGE.getUrl();
    }
    public boolean isTitleDisplayed() {
        return pageTitle.isDisplayed();
    }
}
