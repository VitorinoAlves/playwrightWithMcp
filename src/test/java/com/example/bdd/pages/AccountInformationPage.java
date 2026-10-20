package com.example.bdd.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.Map;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AccountInformationPage {
    private final Page page;

    private final Locator accountInfoHeader;
    private final Locator genderMrRadio;
    private final Locator genderMrsRadio;
    private final Locator passwordInput;
    private final Locator daysSelect;
    private final Locator monthsSelect;
    private final Locator yearsSelect;
    private final Locator newsletterCheckbox;
    private final Locator optinCheckbox;
    private final Locator firstNameInput;
    private final Locator lastNameInput;
    private final Locator companyInput;
    private final Locator address1Input;
    private final Locator address2Input;
    private final Locator countrySelect;
    private final Locator stateInput;
    private final Locator cityInput;
    private final Locator zipcodeInput;
    private final Locator mobileNumberInput;
    private final Locator createAccountButton;

    public AccountInformationPage(Page page) {
        this.page = page;
        this.accountInfoHeader = page.locator("h2").filter(
                new Locator.FilterOptions().setHasText(Pattern.compile("Enter Account Information", Pattern.CASE_INSENSITIVE))
        );
        this.genderMrRadio = page.locator("#id_gender1");
        this.genderMrsRadio = page.locator("#id_gender2");
        this.passwordInput = page.locator("input[data-qa='password']");
        this.daysSelect = page.locator("select[data-qa='days']");
        this.monthsSelect = page.locator("select[data-qa='months']");
        this.yearsSelect = page.locator("select[data-qa='years']");
        this.newsletterCheckbox = page.locator("#newsletter");
        this.optinCheckbox = page.locator("#optin");
        this.firstNameInput = page.locator("input[data-qa='first_name']");
        this.lastNameInput = page.locator("input[data-qa='last_name']");
        this.companyInput = page.locator("input[data-qa='company']");
        this.address1Input = page.locator("input[data-qa='address']");
        this.address2Input = page.locator("input[data-qa='address2']");
        this.countrySelect = page.locator("select[data-qa='country']");
        this.stateInput = page.locator("input[data-qa='state']");
        this.cityInput = page.locator("input[data-qa='city']");
        this.zipcodeInput = page.locator("input[data-qa='zipcode']");
        this.mobileNumberInput = page.locator("input[data-qa='mobile_number']");
        this.createAccountButton = page.locator("button[data-qa='create-account']");
    }

    public void verifyAccountInformationTitleVisible() {
        assertThat(accountInfoHeader).isVisible();
    }

    public void fillAccountInformation(Map<String, String> data) {
        if (data.containsKey("Title")) {
            String title = data.get("Title");
            if (title.equalsIgnoreCase("Mr.") || title.equalsIgnoreCase("Mr")) {
                genderMrRadio.check();
            } else if (title.equalsIgnoreCase("Mrs.") || title.equalsIgnoreCase("Mrs")) {
                genderMrsRadio.check();
            }
        }
        if (data.containsKey("Password")) {
            passwordInput.fill(data.get("Password"));
        }
        if (data.containsKey("Day")) {
            daysSelect.selectOption(data.get("Day"));
        }
        if (data.containsKey("Month")) {
            monthsSelect.selectOption(data.get("Month"));
        }
        if (data.containsKey("Year")) {
            yearsSelect.selectOption(data.get("Year"));
        }
    }

    public void selectCheckbox(String checkboxName) {
        if (checkboxName.toLowerCase().contains("newsletter")) {
            newsletterCheckbox.check();
        } else if (checkboxName.toLowerCase().contains("special offers") || checkboxName.toLowerCase().contains("optin")) {
            optinCheckbox.check();
        } else {
            page.getByLabel(checkboxName).check();
        }
    }

    public void fillAddressInformation(Map<String, String> data) {
        if (data.containsKey("First Name")) {
            firstNameInput.fill(data.get("First Name"));
        }
        if (data.containsKey("Last Name")) {
            lastNameInput.fill(data.get("Last Name"));
        }
        if (data.containsKey("Company")) {
            companyInput.fill(data.get("Company"));
        }
        if (data.containsKey("Address")) {
            address1Input.fill(data.get("Address"));
        }
        if (data.containsKey("Address2")) {
            address2Input.fill(data.get("Address2"));
        }
        if (data.containsKey("Country")) {
            countrySelect.selectOption(data.get("Country"));
        }
        if (data.containsKey("State")) {
            stateInput.fill(data.get("State"));
        }
        if (data.containsKey("City")) {
            cityInput.fill(data.get("City"));
        }
        if (data.containsKey("Zipcode")) {
            zipcodeInput.fill(data.get("Zipcode"));
        }
        if (data.containsKey("Mobile")) {
            mobileNumberInput.fill(data.get("Mobile"));
        }
    }

    public void clickCreateAccount() {
        createAccountButton.click();
    }
}
