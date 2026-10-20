package com.example.bdd.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AccountStatusPage {
    private final Page page;

    private final Locator accountCreatedHeader;
    private final Locator accountDeletedHeader;
    private final Locator continueButton;

    public AccountStatusPage(Page page) {
        this.page = page;
        this.accountCreatedHeader = page.locator("h2[data-qa='account-created']");
        this.accountDeletedHeader = page.locator("h2[data-qa='account-deleted']");
        this.continueButton = page.locator("a[data-qa='continue-button']");
    }

    public void verifyAccountCreatedVisible() {
        assertThat(accountCreatedHeader).isVisible();
    }

    public void verifyAccountDeletedVisible() {
        assertThat(accountDeletedHeader).isVisible();
    }

    public void verifyMessageVisible(String message) {
        if (message.equalsIgnoreCase("ACCOUNT CREATED!")) {
            verifyAccountCreatedVisible();
        } else if (message.equalsIgnoreCase("ACCOUNT DELETED!")) {
            verifyAccountDeletedVisible();
        } else {
            assertThat(page.getByText(Pattern.compile(Pattern.quote(message), Pattern.CASE_INSENSITIVE))).isVisible();
        }
    }

    public void clickContinue() {
        continueButton.click();
    }
}
