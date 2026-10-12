package com.example.bdd.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ExamplePage {
    private final Page page;

    //Locators
    private final Locator getStarted;
    private final Locator pageHeader;


    public ExamplePage(Page page) {
        this.page = page;
        this.getStarted = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Get Started"));
        this.pageHeader = page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("Installation"));
    }

    public void navigate() {
        page.navigate("https://playwright.dev");
    }

    public void clickOnGetStarted() {
        getStarted.click();
    }

    public Boolean isHeaderVisible() {
        return pageHeader.isVisible();
    }
}
