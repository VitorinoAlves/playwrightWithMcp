package com.example.bdd.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ProductDetailPage {
    private final Page page;

    private final Locator quantityInput;
    private final Locator addToCartButton;
    private final Locator cartModal;

    public ProductDetailPage(Page page) {
        this.page = page;
        this.quantityInput = page.locator("#quantity");
        this.addToCartButton = page.locator("button.cart");
        this.cartModal = page.locator("#cartModal");
    }

    public void setQuantity(int quantity) {
        assertThat(quantityInput).isVisible();
        quantityInput.fill(String.valueOf(quantity));
    }

    public void addToCart() {
        addToCartButton.scrollIntoViewIfNeeded();
        addToCartButton.click();
        try {
            cartModal.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(4000));
        } catch (Exception e) {
            addToCartButton.click();
            cartModal.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE));
        }
        Locator closeModalBtn = cartModal.locator("button.close-modal");
        if (closeModalBtn.isVisible()) {
            closeModalBtn.click();
        }
    }
}
