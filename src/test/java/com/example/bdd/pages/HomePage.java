package com.example.bdd.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomePage {
    private final Page page;

    private final Locator homeLogo;
    private final Locator signupLoginLink;
    private final Locator logoutLink;
    private final Locator deleteAccountLink;
    private final Locator navBar;

    public HomePage(Page page) {
        this.page = page;
        this.homeLogo = page.locator("#header .logo img");
        this.signupLoginLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Signup / Login"));
        this.logoutLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Logout"));
        this.deleteAccountLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Delete Account"));
        this.navBar = page.locator(".navbar-nav");
    }

    public void navigate() {
        page.navigate("https://automationexercise.com/");
    }

    public void verifyHomePageVisible() {
        assertThat(page).hasTitle(Pattern.compile(".*Automation Exercise.*"));
        assertThat(homeLogo).isVisible();
    }

    public void clickSignupLogin() {
        signupLoginLink.click();
    }

    public void clickLogout() {
        logoutLink.click();
    }

    public void clickDeleteAccount() {
        deleteAccountLink.click();
    }

    public void verifyLoggedInAs(String username) {
        Locator loggedInUser = page.locator(".navbar-nav li")
                .filter(new Locator.FilterOptions().setHasText("Logged in as " + username));
        assertThat(loggedInUser).isVisible();
    }
}
