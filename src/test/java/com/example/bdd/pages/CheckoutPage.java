package com.example.bdd.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CheckoutPage {
    private final Page page;

    private final Locator addressDelivery;
    private final Locator commentTextarea;
    private final Locator placeOrderButton;

    public CheckoutPage(Page page) {
        this.page = page;
        this.addressDelivery = page.locator("#address_delivery");
        this.commentTextarea = page.locator("textarea[name='message']");
        this.placeOrderButton = page.locator("a[href='/payment']");
    }

    public void verifyCheckoutPageVisible() {
        assertThat(addressDelivery).isVisible();
    }

    public void enterCommentAndPlaceOrder(String comment) {
        if (comment != null && !comment.isEmpty()) {
            commentTextarea.scrollIntoViewIfNeeded();
            commentTextarea.fill(comment);
        }
        placeOrderButton.scrollIntoViewIfNeeded();
        placeOrderButton.click();
    }
}
