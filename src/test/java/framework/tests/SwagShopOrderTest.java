package framework.tests;

import framework.pages.swagLabShop.constants.SwagLabUrls;
import framework.pages.swagLabShop.*;
import framework.pages.swagLabShop.checkoutFlow.CheckoutCompletePage;
import framework.pages.swagLabShop.checkoutFlow.CheckoutStepOnePage;
import framework.pages.swagLabShop.checkoutFlow.CheckoutStepTwoPage;
import framework.util.DataManipulationUtils;
import framework.util.JsonUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SwagShopOrderTest extends BaseTest {

    JsonUtil jsonUtil = new JsonUtil();
    DataManipulationUtils util = new DataManipulationUtils();

    //parse all available products from json file with test data and put into list of hashmaps
    List < HashMap < String, String >> allData = jsonUtil.getListOfProductsFromJson();

    // from all product - grab only what we need for testing.
    // this is configuration point for all tests related to adding/removing products
    // update this list if you want other product to be added/tested
    private static List < String > productsToAdd = Arrays.asList("Backpack", "Fleece Jacket", "Onesie");
    private static final int indexOfProductToRemove = 2;

    //for now its hardcoded 'magic number' (anti-pattern)

    private static final double expectedTaxAmount = 6.40;
    public static final String expectedCard = "SauceCard #31337";
    public static final String expectedShippingCompany = "Free Pony Express Delivery!";
    // TODO {link to relevant JIRA task} | move tax amount to 'SwagLabsProducts.json' (for taxes) or separate business logic class to avoid hardcode
    //end of config

    //removing last one product, according to test case requirement
    private static List < String > productsToRemove = Arrays.asList(productsToAdd.get(indexOfProductToRemove));

    //collect all product that we want to test into list of hashmaps - we will use it in our tests
    List < HashMap < String, String >> productsToTest = jsonUtil.searchByNames(allData, productsToAdd);
    List < HashMap < String, String >> productsToRemoveTest = jsonUtil.searchByNames(productsToTest, productsToRemove);

    //calculate expected total price for test products
    double expectedPriceBeforeRemoval = jsonUtil.calculateTotalPrice(productsToTest);
    double priceOfProductToRemove = util.parseAndRoundPriceString(productsToTest.get(indexOfProductToRemove).get("priceUsd"));
    double expectedPriceAfterRemoval = expectedPriceBeforeRemoval - priceOfProductToRemove;
    private double expectedGrandTotal = new DataManipulationUtils().roundToTwoDecimalPlaces(expectedPriceAfterRemoval + expectedTaxAmount);


    @Test(testName = "User could login into Swag Shop",
            description = "Go to login page, grab credentials from UI, log-in")
    public void userCouldLoginTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.goTo();

        Assert.assertEquals(driver.getCurrentUrl(), SwagLabUrls.LOGIN_PAGE.getUrl(), "Unexpected login page url");

        loginPage.tryLoginUsingCredentialsFromPage();
        InventoryPage inventoryPage = new InventoryPage(driver);

        Assert.assertTrue(inventoryPage.isTitleDisplayed(), "No expected title");
        Assert.assertEquals(driver.getCurrentUrl(), inventoryPage.getExpectedUrl(), "Unexpected page after login");
    }
    // TODO {link to relevant JIRA task} | reset application state via UI (not possible via API) as separate feature test. shopping cart may may contains products

    @Test(testName = "User could see default UI elements at 'Products' page",
            description = "Verify shopping cart, ... TODO",
            dependsOnMethods = "userCouldLoginTest")
    public void userSeeDefaultInventoryPageUiTest() {
        InventoryPage inventoryPage = new InventoryPage(driver);

        Assert.assertTrue(inventoryPage.isDefaultUiDisplayed(), "Shopping cart should be empty");
    }

    @Test(testName = "User could add few products to the shopping cart",
            description = "Add 3 products",
            dependsOnMethods = "userSeeDefaultInventoryPageUiTest",
            dataProvider = "getProductsList")
    public void userCouldAddProductsTest(HashMap < String, String > testData) {
        String id = testData.get("id");
        String item = testData.get("name");
        InventoryPage inventoryPage = new InventoryPage(driver);
        int cartCountBefore = inventoryPage.getProductsCounterFromCart();

        Assert.assertTrue(inventoryPage.isNotAdded(id), "Already added to cart, product: " + item);

        inventoryPage.addProduct(id);

        Assert.assertTrue(inventoryPage.isAlreadyAdded(id), "No 'Remove' button after click on product-card: " + item);

        int cartCountAfter = inventoryPage.getProductsCounterFromCart();

        Assert.assertEquals(cartCountAfter, cartCountBefore + 1, "'Shopping Cart' counter should increase by 1 after adding: " + item);
    }

    //TODO {link to relevant JIRA task} | additional asserts for product card: title, image, description, price etc.
    @DataProvider
    public Object[][] getProductsList() {
        return new Object[][] {
                {
                        productsToTest.get(0)
                }, {
                productsToTest.get(1)
        }, {
                productsToTest.get(2)
        }
        };
    }

    //TODO add test for adding product to shopping cart via its own (product) page {link to relevant JIRA task}

    @Test(testName = "User redirected to relevant page after click on 'shopping cart' icon",
            description = "Click on 'cart', verify user redirected & UI",
            dependsOnMethods = "userCouldAddProductsTest")
    public void userRedirectedAfterClickShoppingCart() {
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.clickShoppingCart();
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);

        Assert.assertTrue(shoppingCartPage.isTitleDisplayed(), "No expected title");
        Assert.assertEquals(driver.getCurrentUrl(), shoppingCartPage.getExpectedUrl(), "Unexpected cart page url, redirect after click on 'cart icon'");
    }

    @Test(testName = "User could cancel product",
            description = "At 'Shopping Cart' page - click 'Remove' button on some product",
            dependsOnMethods = "userRedirectedAfterClickShoppingCart")
    public void userCouldRemoveProduct() {
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        HashMap < String, String > removedProduct = productsToRemoveTest.get(0);
        String id = removedProduct.get("id");

        Assert.assertEquals(shoppingCartPage.getProductsCounterFromCart(), productsToTest.size(), "Wrong hopping cart counter");
        Assert.assertEquals(shoppingCartPage.getTotalPrice(), expectedPriceBeforeRemoval, "Wrong total price of products");

        shoppingCartPage.removeProduct(id);

        Assert.assertEquals(shoppingCartPage.getTotalPrice(), expectedPriceAfterRemoval, "Wrong total price of products after user removed 1 product");
        Assert.assertEquals(shoppingCartPage.getProductsCounterFromCart(), productsToTest.size() - productsToRemoveTest.size(), "Wrong hopping cart counter after user removed 1 product");
    }

    @Test(testName = "User could initiate checkout process",
            description = "At 'Shopping Cart' page - click 'Checkout'",
            dependsOnMethods = "userCouldRemoveProduct")
    public void userCouldInitiateCheckout() {
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);

        shoppingCartPage.clickCheckoutButton();
        CheckoutStepOnePage stepOnePage = new CheckoutStepOnePage(driver);

        Assert.assertTrue(stepOnePage.isTitleDisplayed(), "No Expected page title at 'Checkout' page");
        Assert.assertEquals(driver.getCurrentUrl(), stepOnePage.getExpectedUrl(), "Unexpected url, after click on 'checkout'");
    }

    @Test(testName = "User set valid credentials and continue checkout",
            description = "Set: username, lastname, zip; click 'Continue', verify new page opened",
            dependsOnMethods = "userCouldInitiateCheckout")
    public void userCouldSetValidCredentialsAndContinueCheckout() {
        CheckoutStepOnePage stepOnePage = new CheckoutStepOnePage(driver);
        // we may test validation (first name, last name etc.) in other testcases, now its out of scope
        stepOnePage.setRandomUserDataAndContinue();

        CheckoutStepTwoPage stepTwoPagePage = new CheckoutStepTwoPage(driver);

        Assert.assertTrue(stepTwoPagePage.isTitleDisplayed(), "No Expected page title at 'Checkout' page");
        Assert.assertEquals(driver.getCurrentUrl(), stepTwoPagePage.getExpectedUrl(), "Unexpected url, after click on continue @ checkout step 1");
    }

    @Test(testName = "User could see order information",
            description = "Verify order details: Payment, Shipping, Price, Tax",
            dependsOnMethods = "userCouldSetValidCredentialsAndContinueCheckout")
    public void userCouldSeeOrderInformation() {
        CheckoutStepTwoPage stepTwoPagePage = new CheckoutStepTwoPage(driver);
        String msg = " at the 'checkout step 2' page";

        Assert.assertTrue(stepTwoPagePage.isOrderLabelsDisplayed(), "No info (payment, shipping, total price) about order" + msg);
        Assert.assertEquals(stepTwoPagePage.getTotalPriceFromCards(), expectedPriceAfterRemoval, "Wrong total price of products cards" + msg);
        Assert.assertEquals(stepTwoPagePage.getPaymentValue(), expectedCard, "Wrong payment card info" + msg);
        Assert.assertEquals(stepTwoPagePage.getShippingValue(), expectedShippingCompany, "Wrong shipping company info" + msg);

        Assert.assertEquals(stepTwoPagePage.getPriceFromItemTotal(), expectedPriceAfterRemoval, "Wrong item(s) price in summary" + msg);
        Assert.assertEquals(stepTwoPagePage.getValueFromTax(), expectedTaxAmount, "Wrong tax value in summary" + msg);
        Assert.assertEquals(stepTwoPagePage.getPriceGrandTotal(), expectedGrandTotal, "Wrong grand total price in summary" + msg);
    }

    @Test(testName = "User could finalize checkout",
            description = "Verify order info. Click 'Finish', verify new page opened",
            dependsOnMethods = "userCouldSeeOrderInformation")
    public void userCouldFinalizeCheckout() {
        CheckoutStepTwoPage stepTwoPagePage = new CheckoutStepTwoPage(driver);
        stepTwoPagePage.clickFinish();

        CheckoutCompletePage checkoutCompletePage = new CheckoutCompletePage(driver);

        Assert.assertTrue(checkoutCompletePage.isTitleDisplayed(), "No Expected page title at 'Checkout complete' page");
        Assert.assertEquals(driver.getCurrentUrl(), checkoutCompletePage.getExpectedUrl(), "Unexpected url, after click on continue @ checkout step 1");
    }
    @Test(testName = "User use app further after finalized checkout",
            description = "Click 'Back Home', verify 'inventory' page opened",
            dependsOnMethods = "userCouldFinalizeCheckout")
    public void userCouldGoOnAfterCheckoutCompleted() {
        CheckoutCompletePage checkoutCompletePage = new CheckoutCompletePage(driver);
        checkoutCompletePage.clickBackHome();

        InventoryPage inventoryPage = new InventoryPage(driver);

        Assert.assertTrue(inventoryPage.isTitleDisplayed(), "No expected title");
        Assert.assertEquals(driver.getCurrentUrl(), inventoryPage.getExpectedUrl(), "Unexpected page after user clicked 'Back Home'");
    }

}