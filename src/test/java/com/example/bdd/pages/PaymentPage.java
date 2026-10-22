package com.example.bdd.pages;

import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PaymentPage {
    private final Page page;

    private final Locator nameOnCardInput;
    private final Locator cardNumberInput;
    private final Locator cvcInput;
    private final Locator expirationMonthInput;
    private final Locator expirationYearInput;
    private final Locator payAndConfirmOrderButton;
    private final Locator orderPlacedHeading;
    private final Locator downloadInvoiceButton;
    private Download lastDownloadedFile;

    public PaymentPage(Page page) {
        this.page = page;
        this.nameOnCardInput = page.locator("input[name='name_on_card']");
        this.cardNumberInput = page.locator("input[name='card_number']");
        this.cvcInput = page.locator("input[name='cvc']");
        this.expirationMonthInput = page.locator("input[name='expiry_month']");
        this.expirationYearInput = page.locator("input[name='expiry_year']");
        this.payAndConfirmOrderButton = page.locator("button#submit");
        this.orderPlacedHeading = page.locator("[data-qa='order-placed']");
        this.downloadInvoiceButton = page.locator("a[href*='/download_invoice/']");
    }

    public void submitPayment(String nameOnCard, String cardNumber, String cvc, String expMonth, String expYear) {
        nameOnCardInput.fill(nameOnCard);
        cardNumberInput.fill(cardNumber);
        cvcInput.fill(cvc);
        expirationMonthInput.fill(expMonth);
        expirationYearInput.fill(expYear);
        payAndConfirmOrderButton.click();
    }

    public void verifyOrderConfirmation(String message) {
        Locator confirmationText = page.locator("p, h2")
                .filter(new Locator.FilterOptions().setHasText(message))
                .first();
        assertThat(confirmationText).isVisible();
    }

    public void downloadInvoice() {
        downloadInvoiceButton.scrollIntoViewIfNeeded();
        lastDownloadedFile = page.waitForDownload(() -> {
            downloadInvoiceButton.click();
        });
    }

    public boolean isInvoiceDownloaded() {
        return lastDownloadedFile != null && lastDownloadedFile.suggestedFilename() != null;
    }
}
