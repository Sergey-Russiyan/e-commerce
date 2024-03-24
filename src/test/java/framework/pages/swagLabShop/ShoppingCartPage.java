package framework.pages.swagLabShop;

import framework.pages.BasePage;
import framework.pages.swagLabShop.constants.SwagLabUrls;
import framework.pages.swagLabShop.fragments.ProductCardFragment;
import framework.pages.swagLabShop.fragments.ShoppingCartFragment;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The Class represents Swag Shop Shopping Cart page.
 *
 * @author Serhii R.
 */
public class ShoppingCartPage extends BasePage {

    @FindBy(id = "checkout")
    private WebElement checkOutButton;
    private ShoppingCartFragment shoppingCartFragment;
    private ProductCardFragment productCardFragment;
    @FindBy(xpath = "//*[@class='title'][.='Your Cart']")
    private WebElement cartTitle;
    /**
     * Instantiates a new base page.
     *
     * @param driver the driver
     */
    public ShoppingCartPage(WebDriver driver) {
        super(driver);
        this.shoppingCartFragment = new ShoppingCartFragment(driver);
        this.productCardFragment = new ProductCardFragment(driver);
    }
    public boolean isTitleDisplayed() {
        return cartTitle.isDisplayed();
    }

    public void clickShoppingCart() {
        shoppingCartFragment.click();
    }
    public void removeProduct(String id) {
        productCardFragment.removeProductToCardById(id);
    }
    public int getProductsCounterFromCart() {
        return shoppingCartFragment.getShoppingCartProductsQuantity();
    }
    public double getTotalPrice() {
        return productCardFragment.getProductsCardsTotalPrice();
    }
    public void clickCheckoutButton() {
        checkOutButton.click();
    }
    public String getExpectedUrl() {
        return SwagLabUrls.CART_PAGE.getUrl();
    }

}
