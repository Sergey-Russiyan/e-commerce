package framework.pages.swagLabShop.fragments;

import framework.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The Class represents Swag Shopping Cart fragment which could be present:
 * 1. At products list (aka 'Inventory') page
 * 2. At Cart page
 *
 * @author Serhii R.
 */
public class ShoppingCartFragment extends BasePage {
    protected WebDriver driver;
    @FindBy(id="shopping_cart_container")
    public WebElement shoppingCart;

    public static final By shoppingCartBadge = By.className("shopping_cart_badge");

    /**
     * Instantiates a new base page.
     *
     * @param driver the driver
     */
    public ShoppingCartFragment(WebDriver driver) {
        super(driver);
        this.driver=driver;
    }
    public int getShoppingCartProductsQuantity() {
        if (isShoppingCartEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(driver.findElement(shoppingCartBadge).getText());
        }
    }
    public boolean isShoppingCartEmpty() {
        return isElementDisplayed(shoppingCartBadge, 2);
    }
    public void click(){
        shoppingCart.click();
    }
}
