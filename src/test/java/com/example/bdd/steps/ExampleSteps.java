package com.example.bdd.steps;

import com.example.bdd.hooks.PlaywrightHooks;
import com.example.bdd.pages.ExamplePage;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

public class ExampleSteps {
    private final PlaywrightHooks hooks;
    private ExamplePage examplePage;

    public ExampleSteps(PlaywrightHooks hooks) {
        this.hooks = hooks;
    }

    @Given("the user opens the playwright home page")
    public void openHomePage() {
        examplePage = new ExamplePage(hooks.getPage());
        examplePage.navigate();
    }

    @When("the user click on the Docs option")
    public void clickOnOption() {
        examplePage.clickOnGetStarted();
    }

    @Then("the user is redirected to the intro page")
    public void verifyTheUrlPage() {
        assertTrue(examplePage.isHeaderVisible());
    }
}
