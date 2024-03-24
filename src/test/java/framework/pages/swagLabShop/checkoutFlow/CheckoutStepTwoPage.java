package framework.pages.swagLabShop.checkoutFlow;

import framework.pages.BasePage;
import framework.pages.swagLabShop.constants.SwagLabUrls;
import framework.pages.swagLabShop.fragments.ProductCardFragment;
import framework.pages.swagLabShop.fragments.ShoppingCartFragment;
import framework.util.DataManipulationUtils;
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

    //specific
    @FindBy(xpath = "//*[@class='summary_info_label'][.='Payment Information']")
    private WebElement paymentInfoLabel;
    @FindBy(xpath = "//*[@class='summary_info_label'][.='Shipping Information']")
    private WebElement shippingInfoLabel;
    @FindBy(xpath = "//*[@class='summary_info_label'][.='Price Total']")
    private WebElement priceTotalLabel;

    @FindBy(className = "summary_value_label")
    private WebElement paymentInfoValue;
    @FindBy(xpath = "(//*[@class='summary_value_label'])[2]")
    private WebElement shippingInfoValue;
    @FindBy(className = "summary_subtotal_label")
    private WebElement itemTotalPriceValue;
    @FindBy(className = "summary_tax_label")
    private WebElement itemTaxValue;
    @FindBy(css = "[class='summary_info_label summary_total_label']")
    private WebElement orderSummaryTotalPriceValue;

    @FindBy(id = "finish")
    private WebElement finishButton;
    @FindBy(id = "cancel")
    private WebElement cancelButton;

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

    public boolean isOrderLabelsDisplayed() {
        return areElementsDisplayed(paymentInfoLabel, shippingInfoLabel, priceTotalLabel, finishButton, cancelButton);
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
    public String getPaymentValue() {
        return paymentInfoValue.getText();
    }
    public String getShippingValue() {
        return shippingInfoValue.getText();
    }
    public double getTotalPriceFromCards() {
        return productCardFragment.getProductsCardsTotalPrice();
    }
    public double getPriceFromItemTotal() {
        return new DataManipulationUtils().parseAndRoundPriceString(itemTotalPriceValue.getText());
    }
    public double getValueFromTax() {
        return new DataManipulationUtils().parseAndRoundPriceString(itemTaxValue.getText());
    }
    public double getPriceGrandTotal() {
        return new DataManipulationUtils().parseAndRoundPriceString(orderSummaryTotalPriceValue.getText());
    }

}
