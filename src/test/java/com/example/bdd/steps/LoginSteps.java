package com.example.bdd.steps;

import com.example.bdd.api.client.UserApiClient;
import com.example.bdd.api.model.ApiResponse;
import com.example.bdd.api.model.CreateAccountRequest;
import com.example.bdd.hooks.PlaywrightHooks;
import com.example.bdd.pages.AccountInformationPage;
import com.example.bdd.pages.AccountStatusPage;
import com.example.bdd.pages.HomePage;
import com.example.bdd.pages.LoginSignupPage;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSteps {
    private final PlaywrightHooks hooks;

    private HomePage homePage;
    private LoginSignupPage loginSignupPage;
    private AccountInformationPage accountInformationPage;
    private AccountStatusPage accountStatusPage;
    private UserApiClient userApiClient;
    private String dynamicEmail;
    private String originalEmail;

    public LoginSteps(PlaywrightHooks hooks) {
        this.hooks = hooks;
    }

    private void initPages() {
        Page page = hooks.getPage();
        if (homePage == null) homePage = new HomePage(page);
        if (loginSignupPage == null) loginSignupPage = new LoginSignupPage(page);
        if (accountInformationPage == null) accountInformationPage = new AccountInformationPage(page);
        if (accountStatusPage == null) accountStatusPage = new AccountStatusPage(page);
        if (userApiClient == null) userApiClient = new UserApiClient(page);
    }

    @Given("the user navigates to the Automation Exercise home page")
    public void navigateToHomePage() {
        initPages();
        homePage.navigate();
    }

    @Given("the home page is displayed successfully")
    public void verifyHomePageDisplayed() {
        initPages();
        homePage.verifyHomePageVisible();
    }

    @When("the user clicks on the {string} button")
    public void clickOnButton(String buttonName) {
        performButtonClick(buttonName);
    }

    @When("clicks the {string} button")
    public void clicksTheButton(String buttonName) {
        performButtonClick(buttonName);
    }

    @When("the user clicks the {string} button")
    public void userClicksTheButton(String buttonName) {
        performButtonClick(buttonName);
    }

    private void performButtonClick(String buttonName) {
        initPages();
        switch (buttonName) {
            case "Signup / Login":
                homePage.clickSignupLogin();
                break;
            case "Signup":
                loginSignupPage.clickSignup();
                break;
            case "Create Account":
                accountInformationPage.clickCreateAccount();
                break;
            case "Continue":
                accountStatusPage.clickContinue();
                break;
            case "Delete Account":
                homePage.clickDeleteAccount();
                break;
            case "Login":
                loginSignupPage.clickLogin();
                break;
            case "Logout":
                homePage.clickLogout();
                break;
            default:
                hooks.getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(buttonName)).click();
        }
    }

    @Then("the {string} section is visible")
    public void verifySectionVisible(String sectionName) {
        initPages();
        loginSignupPage.verifySectionVisible(sectionName);
    }

    @When("the user enters a new signup name {string} and email {string}")
    public void enterNewSignupDetails(String name, String email) {
        initPages();
        String targetEmail = email;
        if (!email.equalsIgnoreCase("existing_user@example.com")) {
            targetEmail = email.replace("@", "_" + System.currentTimeMillis() + "@");
        }
        loginSignupPage.enterSignupDetails(name, targetEmail);
    }

    @Then("the {string} title is visible")
    public void verifyTitleVisible(String title) {
        initPages();
        if (title.equalsIgnoreCase("ENTER ACCOUNT INFORMATION")) {
            accountInformationPage.verifyAccountInformationTitleVisible();
        } else {
            assertThat(hooks.getPage().getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName(title))).isVisible();
        }
    }

    @When("the user fills the account information:")
    public void fillAccountInformation(DataTable dataTable) {
        initPages();
        accountInformationPage.fillAccountInformation(dataTable.asMap(String.class, String.class));
    }

    @When("selects the checkbox {string}")
    public void selectCheckbox(String checkboxName) {
        initPages();
        accountInformationPage.selectCheckbox(checkboxName);
    }

    @When("fills the address information:")
    public void fillAddressInformation(DataTable dataTable) {
        initPages();
        accountInformationPage.fillAddressInformation(dataTable.asMap(String.class, String.class));
    }

    @Then("the {string} message is visible")
    public void verifyMessageVisible(String message) {
        initPages();
        accountStatusPage.verifyMessageVisible(message);
    }

    @Then("the user is logged in as {string}")
    public void verifyLoggedInAs(String username) {
        initPages();
        homePage.verifyLoggedInAs(username);
    }

    @When("the user enters the login email {string} and password {string}")
    public void enterLoginCredentials(String email, String password) {
        initPages();
        String targetEmail = (dynamicEmail != null && email.equals(originalEmail)) ? dynamicEmail : email;
        loginSignupPage.enterLoginCredentials(targetEmail, password);
    }

    @Then("an error message {string} is visible")
    public void verifyErrorMessage(String errorMessage) {
        initPages();
        loginSignupPage.verifyErrorMessage(errorMessage);
    }

    @Then("the user is navigated to the login page")
    public void verifyNavigatedToLoginPage() {
        initPages();
        loginSignupPage.verifyNavigatedToLoginPage();
    }

    @Given("the user with te email {string} and password {string} is created using the API")
    @Given("the user with the email {string} and password {string} is created using the API")
    public void createUserUsingApi(String email, String password) {
        initPages();
        this.originalEmail = email;
        this.dynamicEmail = email.replace("@", "_" + System.currentTimeMillis() + "@");

        String name = "Valid User";
        String firstname = "Valid";
        String lastname = "User";

        if (!email.equalsIgnoreCase("valid_user@example.com")) {
            String prefix = email.split("@")[0];
            String[] parts = prefix.split("[._-]");
            if (parts.length >= 2) {
                firstname = parts[0].substring(0, 1).toUpperCase() + parts[0].substring(1);
                lastname = parts[1].substring(0, 1).toUpperCase() + parts[1].substring(1);
                name = firstname + " " + lastname;
            } else if (!parts[0].isEmpty()) {
                name = parts[0].substring(0, 1).toUpperCase() + parts[0].substring(1);
                firstname = name;
            }
        }

        CreateAccountRequest request = CreateAccountRequest.builder()
                .name(name)
                .email(this.dynamicEmail)
                .password(password)
                .title("Mr")
                .birthDate("10")
                .birthMonth("05")
                .birthYear("1990")
                .firstName(firstname)
                .lastName(lastname)
                .company("Quality Corp")
                .address1("123 Test Avenue")
                .address2("Suite 100")
                .country("Canada")
                .zipcode("73301")
                .state("Texas")
                .city("Austin")
                .mobileNumber("5125551234")
                .build();

        APIResponse response = userApiClient.createAccount(request);
        assertTrue(response.ok(), "HTTP request to createAccount failed with status: " + response.status());

        ApiResponse apiResponse = userApiClient.parseApiResponse(response);
        assertEquals(201, apiResponse.getResponseCode(), "Expected responseCode 201 but got: " + apiResponse.getResponseCode());
        assertEquals("User created!", apiResponse.getMessage(), "Expected message 'User created!' but got: " + apiResponse.getMessage());
    }

    private void cleanupUserIfPresent(String email) {
        cleanupUserIfPresent(email, "StrongPass123!");
    }

    private void cleanupUserIfPresent(String email, String password) {
        try {
            initPages();
            userApiClient.deleteAccount(email, password != null ? password : "StrongPass123!");
        } catch (Exception ignored) {
            // Cleanup attempt is best-effort
        }
    }
}
