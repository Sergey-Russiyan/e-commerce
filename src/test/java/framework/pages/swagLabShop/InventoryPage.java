package framework.pages.swagLabShop;


import framework.pages.BasePage;
import framework.pages.swagLabShop.constants.SwagLabUrls;
import framework.pages.swagLabShop.fragments.ProductCardFragment;
import framework.pages.swagLabShop.fragments.ShoppingCartFragment;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The Class represents Swag Shop 'Inventory' (Products list) page.
 *
 * @author Serhii R.
 */
public class InventoryPage extends BasePage {

    @FindBy(xpath = "//*[@class='title'][.='Products']")
    private WebElement pageTitle;
    private ShoppingCartFragment shoppingCartFragment;
    private ProductCardFragment productCardFragment;
    /**
     * Instantiates a new base page.
     *
     * @param driver the driver
     */
    public InventoryPage(WebDriver driver) {
        super(driver);
        this.shoppingCartFragment = new ShoppingCartFragment(driver);
        this.productCardFragment = new ProductCardFragment(driver);
    }
    public boolean isAlreadyAdded(String id) {
        return productCardFragment.isRemoveButtonDisplayed(id);
    }
    public boolean isNotAdded(String id) {
        return productCardFragment.isProductAddToCartButtonDisplayed(id);
    }
    public boolean isDefaultUiDisplayed() {
        //TODO {link to relevant JIRA task} | add more asserts for default state UI elements check
        ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment(driver);
        return shoppingCartFragment.isShoppingCartEmpty();
    }
    public void addProduct(String id) {
        productCardFragment.addProductToCardById(id);
    }
    public void clickShoppingCart() {
        shoppingCartFragment.click();
    }
    public int getProductsCounterFromCart() {
        return shoppingCartFragment.getShoppingCartProductsQuantity();
    }

    public String getExpectedUrl() {
        return SwagLabUrls.PRODUCTS_PAGE.getUrl();
    }
    public boolean isTitleDisplayed() {
        return pageTitle.isDisplayed();
    }

}