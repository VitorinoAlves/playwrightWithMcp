package com.example.bdd.steps;

import com.example.bdd.hooks.PlaywrightHooks;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.cucumber.java.en.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ExampleSteps {
    private final PlaywrightHooks hooks;
    private Page page;

    public ExampleSteps(PlaywrightHooks hooks) {
        this.hooks = hooks;
    }

    @Given("the user opens the playwright home page")
    public void openHomePage() {
        page = hooks.getPage();
        page.navigate("https://playwright.dev");
    }

    @When("the user click on the Docs option")
    public void clickOnOption() {
        page = hooks.getPage();
        Locator getStarted = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Get Started"));
        getStarted.click();
    }

    @Then("the user is redirected to the intro page")
    public void verifyTheUrlPage() {
        assertThat(page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("Installation"))).isVisible();
    }
}
