package framework.pages.swagLabShop.constants;

public enum SwagLabUrls {
    PRODUCTS_PAGE("/inventory.html"),
    CART_PAGE("/cart.html"),
    LOGIN_PAGE("/"),
    CHECKOUT_STEP_ONE_PAGE("/checkout-step-one.html"),
    CHECKOUT_STEP_TWO_PAGE("/checkout-step-two.html"),
    CHECKOUT_COMPLETE_PAGE("/checkout-complete.html");

    private static final String BASE_URL = "https://www.saucedemo.com";
    private final String url;

    SwagLabUrls(String url) {
        this.url = url;
    }

    public String getUrl() {
        return BASE_URL + url;
    }
}
