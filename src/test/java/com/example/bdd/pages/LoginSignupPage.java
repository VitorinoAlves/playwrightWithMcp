package com.example.bdd.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginSignupPage {
    private final Page page;

    private final Locator loginSectionHeader;
    private final Locator signupSectionHeader;
    private final Locator signupNameInput;
    private final Locator signupEmailInput;
    private final Locator signupButton;
    private final Locator loginEmailInput;
    private final Locator loginPasswordInput;
    private final Locator loginButton;

    public LoginSignupPage(Page page) {
        this.page = page;
        this.loginSectionHeader = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Login to your account"));
        this.signupSectionHeader = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("New User Signup!"));
        this.signupNameInput = page.locator("input[data-qa='signup-name']");
        this.signupEmailInput = page.locator("input[data-qa='signup-email']");
        this.signupButton = page.locator("button[data-qa='signup-button']");
        this.loginEmailInput = page.locator("input[data-qa='login-email']");
        this.loginPasswordInput = page.locator("input[data-qa='login-password']");
        this.loginButton = page.locator("button[data-qa='login-button']");
    }

    public void verifySectionVisible(String sectionName) {
        Locator sectionHeading = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName(sectionName));
        assertThat(sectionHeading).isVisible();
    }

    public void enterSignupDetails(String name, String email) {
        signupNameInput.fill(name);
        signupEmailInput.fill(email);
    }

    public void clickSignup() {
        signupButton.click();
    }

    public void enterLoginCredentials(String email, String password) {
        loginEmailInput.fill(email);
        loginPasswordInput.fill(password);
    }

    public void clickLogin() {
        loginButton.click();
    }

    public void verifyErrorMessage(String expectedMessage) {
        Locator errorLocator = page.getByText(expectedMessage);
        assertThat(errorLocator).isVisible();
    }

    public void verifyNavigatedToLoginPage() {
        assertThat(page).hasURL(Pattern.compile(".*/login.*"));
        assertThat(loginSectionHeader).isVisible();
    }
}
