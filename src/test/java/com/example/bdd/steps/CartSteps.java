package com.example.bdd.steps;

import com.example.bdd.api.client.UserApiClient;
import com.example.bdd.api.model.CreateAccountRequest;
import com.example.bdd.hooks.PlaywrightHooks;
import com.example.bdd.pages.*;
import com.microsoft.playwright.Page;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

public class CartSteps {
    private final PlaywrightHooks hooks;
    private HomePage homePage;
    private CartPage cartPage;
    private ProductDetailPage productDetailPage;
    private CheckoutPage checkoutPage;
    private PaymentPage paymentPage;
    private LoginSignupPage loginSignupPage;
    private UserApiClient userApiClient;

    public CartSteps(PlaywrightHooks hooks) {
        this.hooks = hooks;
    }

    private void initPages() {
        Page page = hooks.getPage();
        if (homePage == null) homePage = new HomePage(page);
        if (cartPage == null) cartPage = new CartPage(page);
        if (productDetailPage == null) productDetailPage = new ProductDetailPage(page);
        if (checkoutPage == null) checkoutPage = new CheckoutPage(page);
        if (paymentPage == null) paymentPage = new PaymentPage(page);
        if (loginSignupPage == null) loginSignupPage = new LoginSignupPage(page);
        if (userApiClient == null) userApiClient = new UserApiClient(page);
    }

    @Given("the user is an unauthenticated guest")
    public void userIsAnUnauthenticatedGuest() {
        initPages();
    }

    @Given("the user is logged in with valid credentials")
    public void userIsLoggedInWithValidCredentials() {
        initPages();
        String email = "cart_user_" + System.currentTimeMillis() + "@example.com";
        String password = "ValidPassword123!";
        CreateAccountRequest request = CreateAccountRequest.builder()
                .name("Valid User")
                .email(email)
                .password(password)
                .title("Mr")
                .birthDate("10")
                .birthMonth("05")
                .birthYear("1990")
                .firstName("Valid")
                .lastName("User")
                .company("Quality Corp")
                .address1("123 Test Avenue")
                .address2("Suite 100")
                .country("Canada")
                .zipcode("73301")
                .state("Texas")
                .city("Austin")
                .mobileNumber("5125551234")
                .build();
        userApiClient.createAccount(request);
        homePage.clickSignupLogin();
        loginSignupPage.enterLoginCredentials(email, password);
        loginSignupPage.clickLogin();
        homePage.verifyLoggedInAs("Valid User");
    }

    @Given("the user views the product details for {string}")
    public void userViewsProductDetails(String productName) {
        initPages();
        homePage.viewProductDetails(productName);
    }

    @When("the user sets the product quantity to {int}")
    public void userSetsProductQuantity(int quantity) {
        initPages();
        productDetailPage.setQuantity(quantity);
    }

    @When("adds the product to the cart")
    public void userAddsTheProductToTheCart() {
        initPages();
        productDetailPage.addToCart();
    }

    @Given("the user has added {string} to the cart")
    @When("the user adds {string} to the cart")
    public void userAddsProductToCart(String productName) {
        initPages();
        homePage.addProductToCart(productName);
    }

    @When("the user adds the following products to the cart:")
    public void userAddsMultipleProductsToCart(DataTable dataTable) {
        initPages();
        List<Map<String, String>> products = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : products) {
            String product = row.get("product");
            homePage.addProductToCart(product);
        }
    }

    @When("the user navigates to the shopping cart")
    @When("navigates to the shopping cart")
    public void navigateToShoppingCart() {
        initPages();
        cartPage.navigateToCart();
    }

    @When("proceeds to checkout")
    public void proceedsToCheckout() {
        initPages();
        cartPage.clickProceedToCheckout();
    }

    @Then("the user is prompted to register or login before checkout")
    public void verifyUserPromptedToRegisterOrLogin() {
        initPages();
        cartPage.verifyCheckoutModalPrompt();
    }

    @When("the user removes {string} from the cart")
    public void userRemovesProductFromCart(String productName) {
        initPages();
        cartPage.removeProduct(productName);
    }

    @Then("{string} is no longer visible in the cart")
    public void verifyProductNotVisibleInCart(String productName) {
        initPages();
        cartPage.verifyProductNotPresent(productName);
    }

    @Then("the cart displays {string}")
    public void verifyCartDisplaysMessage(String message) {
        initPages();
        cartPage.verifyCartDisplaysMessage(message);
    }

    @Then("the cart displays {string} with a price of {string} and quantity of {int}")
    public void verifyProductInCartWithPriceAndQuantity(String productName, String price, int quantity) {
        initPages();
        cartPage.verifyProductInCart(productName, price, quantity);
    }

    @Then("the cart displays {string} with quantity {int} and total price {string}")
    public void verifyProductInCartWithQuantityAndTotal(String productName, int quantity, String totalPrice) {
        initPages();
        cartPage.verifyProductInCart(productName, quantity, totalPrice);
    }

    @Then("the cart displays the following items:")
    public void verifyMultipleItemsInCart(DataTable dataTable) {
        initPages();
        List<Map<String, String>> items = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : items) {
            String product = row.get("product");
            String price = row.get("price");
            int quantity = Integer.parseInt(row.get("quantity"));
            String total = row.get("total");
            cartPage.verifyProductInCart(product, price, quantity, total);
        }
    }

    @When("places the order with comment {string}")
    public void placesOrderWithComment(String comment) {
        initPages();
        checkoutPage.enterCommentAndPlaceOrder(comment);
    }

    @When("places the order")
    public void placesOrder() {
        initPages();
        checkoutPage.enterCommentAndPlaceOrder(null);
    }

    @When("submits the payment details:")
    public void submitsPaymentDetails(DataTable dataTable) {
        initPages();
        Map<String, String> data = dataTable.asMaps(String.class, String.class).get(0);
        paymentPage.submitPayment(
                data.get("name-on-card"),
                data.get("card-number"),
                data.get("cvc"),
                data.get("expiration-month"),
                data.get("expiration-year")
        );
    }

    @Then("the order confirmation message {string} is displayed")
    public void verifyOrderConfirmationMessage(String message) {
        initPages();
        paymentPage.verifyOrderConfirmation(message);
    }

    @When("downloads the order invoice")
    public void downloadsOrderInvoice() {
        initPages();
        paymentPage.downloadInvoice();
    }

    @Then("the invoice file is downloaded successfully")
    public void verifyInvoiceDownloaded() {
        initPages();
        assertTrue(paymentPage.isInvoiceDownloaded(), "Expected invoice to be downloaded successfully");
    }
}
