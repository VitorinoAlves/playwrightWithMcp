package com.example.bdd.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CartPage {
    private final Page page;

    private final Locator cartTable;
    private final Locator proceedToCheckoutButton;
    private final Locator emptyCartMessage;

    public CartPage(Page page) {
        this.page = page;
        this.cartTable = page.locator("#cart_info_table");
        this.proceedToCheckoutButton = page.locator(".check_out");
        this.emptyCartMessage = page.locator("#empty_cart");
    }

    public void navigateToCart() {
        Locator modalCartLink = page.locator("#cartModal a[href='/view_cart']");
        if (modalCartLink.isVisible()) {
            modalCartLink.click();
        } else {
            page.locator(".navbar-nav a[href='/view_cart']").click();
        }
    }

    public Locator getProductRow(String productName) {
        return cartTable.locator("tbody tr")
                .filter(new Locator.FilterOptions().setHasText(productName))
                .first();
    }

    public void verifyProductInCart(String productName, String price, int quantity) {
        Locator row = getProductRow(productName);
        assertThat(row).isVisible();
        assertThat(row.locator(".cart_description h4 a")).hasText(productName);
        assertThat(row.locator(".cart_price p")).hasText(price);
        assertThat(row.locator(".cart_quantity button")).hasText(String.valueOf(quantity));
    }

    public void verifyProductInCart(String productName, int quantity, String totalPrice) {
        Locator row = getProductRow(productName);
        assertThat(row).isVisible();
        assertThat(row.locator(".cart_description h4 a")).hasText(productName);
        assertThat(row.locator(".cart_quantity button")).hasText(String.valueOf(quantity));
        assertThat(row.locator(".cart_total .cart_total_price")).hasText(totalPrice);
    }

    public void verifyProductInCart(String productName, String price, int quantity, String totalPrice) {
        Locator row = getProductRow(productName);
        assertThat(row).isVisible();
        assertThat(row.locator(".cart_description h4 a")).hasText(productName);
        assertThat(row.locator(".cart_price p")).hasText(price);
        assertThat(row.locator(".cart_quantity button")).hasText(String.valueOf(quantity));
        assertThat(row.locator(".cart_total .cart_total_price")).hasText(totalPrice);
    }

    public void removeProduct(String productName) {
        Locator row = getProductRow(productName);
        Locator deleteBtn = row.locator(".cart_quantity_delete");
        deleteBtn.scrollIntoViewIfNeeded();
        deleteBtn.click();
        try {
            row.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.DETACHED)
                    .setTimeout(4000));
        } catch (Exception e) {
            if (row.isVisible()) {
                deleteBtn.click();
            }
        }
    }

    public void verifyProductNotPresent(String productName) {
        Locator productLink = cartTable.locator("tbody tr .cart_description h4 a")
                .filter(new Locator.FilterOptions().setHasText(productName));
        assertThat(productLink).not().isVisible();
    }

    public void verifyEmptyCart() {
        assertThat(emptyCartMessage).isVisible();
    }

    public void verifyCartDisplaysMessage(String message) {
        assertThat(emptyCartMessage).containsText(message);
    }

    public void clickProceedToCheckout() {
        proceedToCheckoutButton.scrollIntoViewIfNeeded();
        proceedToCheckoutButton.click();
    }

    public void verifyCheckoutModalPrompt() {
        Locator checkoutModal = page.locator("#checkoutModal");
        try {
            checkoutModal.waitFor(new Locator.WaitForOptions().setTimeout(3000));
        } catch (Exception e) {
            proceedToCheckoutButton.click();
        }
        assertThat(checkoutModal).isVisible();
        assertThat(checkoutModal.locator("a[href='/login']")).isVisible();
    }
}
