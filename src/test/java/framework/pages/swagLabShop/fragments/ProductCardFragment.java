package framework.pages.swagLabShop.fragments;

import framework.pages.BasePage;
import framework.util.DataManipulationUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * The Class represents Swag Product Card fragment which could be present:
 * 1. At products list (aka 'Inventory') page
 * 2. At Cart page
 * 3. At Product separate page
 *
 * @author Serhii R.
 */
public class ProductCardFragment extends BasePage {
    protected WebDriver driver;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> genericProductsPriceTag;

    public static final String PRODUCT_CARD_WRAPPER = "//a[contains(@id, 'title_link')]";
    private By productWithName(String productName) {
        return By.xpath(String.format(PRODUCT_CARD_WRAPPER + "//*[contains(text(), '%s')]", productName));
    }
    private By itemPriceById(String productId) {
        return By.xpath(String.format("//*[contains(@id, 'item_%s_title')]/following-sibling::*[contains(@class, 'price')]//*[text()]", productId));
    }
    private By buttonByProductId(String productId, String buttonName) {
        return By.xpath(String.format("//*[contains(@id, 'item_%s_title')]/ancestor::*[contains(@class,'_item')]//button[contains(@name, '%s')]", productId, buttonName));
    }
    private By quantityByProductId(String productId) {
        return By.xpath(String.format("//*[contains(@id, 'item_%s_title')]/ancestor::*[contains(@class,'_item')]//*[@class='cart_quantity']", productId));
    }
    public By addToCartButtonByProductId(String productId) {
        return buttonByProductId(productId, "add-to-cart");
    }
    public By removeFromCartButtonByProductId(String productId) {
        return buttonByProductId(productId, "remove");
    }

    public By cartPageProductCounter(String productId) {
        return quantityByProductId(productId);
    }
    public ProductCardFragment addProductToCardById(String productId) {
        driver.findElement(addToCartButtonByProductId(productId)).click();
        return this;
    }
    public ProductCardFragment removeProductToCardById(String productId) {
        driver.findElement(removeFromCartButtonByProductId(productId)).click();
        return this;
    }

    public boolean isProductAddToCartButtonDisplayed(String productId) {
        return driver.findElement(addToCartButtonByProductId(productId)).isEnabled();
    }
    public boolean isRemoveButtonDisplayed(String productId) {
        return driver.findElement(removeFromCartButtonByProductId(productId)).isEnabled();
    }

    public double getProductsCardsTotalPrice() {
        double totalPrice = 0.0;

        for (WebElement priceElement : genericProductsPriceTag) {
            String priceString = priceElement.getText();
            double priceValue = new DataManipulationUtils().parseAndRoundPriceString(priceString);
            totalPrice += priceValue;
        }
        return totalPrice;
    }


    /**
     * Instantiates a new base page.
     *
     * @param driver the driver
     */
    public ProductCardFragment(WebDriver driver) {
        super(driver);
        this.driver = driver;

    }
}
