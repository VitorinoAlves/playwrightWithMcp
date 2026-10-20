# Project Overview: playwrightWithMcp

This project is an automated testing and browser automation framework built in **Java** utilizing **Microsoft Playwright** and **Cucumber BDD** with **JUnit 5 Platform Suite**.

---

## 🛠 Tech Stack & Dependencies

- **Language**: Java 20 (compatible with Java 20+)
- **Build & Dependency Management**: Apache Maven (`pom.xml`)
- **Browser Automation**: [Microsoft Playwright for Java](https://playwright.dev/java/) (`1.63.0`)
- **BDD Framework**: [Cucumber Java](https://cucumber.io/) (`7.23.0`) with `cucumber-picocontainer` for Dependency Injection
- **Test Runner & Assertions**: JUnit 5 (`5.11.4`), JUnit Platform Suite (`1.11.4`), and Playwright Assertions

---

## 📁 Project Architecture & Key Directories

```
playwrightWithMcp/
├── .github/
│   └── workflows/
│       └── bdd-tests.yml         # GitHub Actions CI workflow for BDD tests
├── pom.xml                               # Maven project configuration & dependencies
├── example.png                           # Sample generated screenshot / artifacts
└── src/
    ├── main/
    │   └── java/org/example/
    │       └── Main.java                 # Standalone Playwright demo execution
    └── test/
        ├── java/com/example/bdd/
        │   ├── api/                      # API client layer (clients & request/response DTOs)
        │   │   ├── client/
        │   │   │   └── UserApiClient.java
        │   │   └── model/
        │   │       ├── ApiResponse.java
        │   │       └── CreateAccountRequest.java
        │   ├── hooks/
        │   │   └── PlaywrightHooks.java  # Lifecycle hooks (@Before, @After) managing Playwright, Browser, Context, Page
        │   ├── pages/
        │   │   └── ExamplePage.java      # Page Object encapsulating locators & user actions
        │   ├── runner/
        │   │   └── TestRunner.java       # JUnit Platform Suite runner for Cucumber
        │   └── steps/
        │       └── ExampleSteps.java     # Step definitions with PicoContainer constructor injection
        └── resources/
            ├── junit-platform.properties # Cucumber runtime settings (glue packages, reports, feature path)
            └── features/
                └── example.feature       # Gherkin scenario definitions
```

---

## 📄 Page Object Model (POM) Format

Page Object classes encapsulate the UI structure, locators, and interactions for specific web pages or components, decoupling step definitions from direct DOM manipulation.

### Conventions & Location
- **Package**: `com.example.bdd.pages` (under `src/test/java/com/example/bdd/pages/`)
- **Naming**: `<Name>Page.java` (e.g., `ExamplePage.java`, `LoginPage.java`)
- **Structure**:
  1. **Page Reference**: Holds `private final Page page;` passed via constructor.
  2. **Locators**: Stored as `private final Locator` fields initialized inside the constructor using user-facing / accessibility queries (`page.getByRole()`, `page.getByLabel()`, etc.).
  3. **Action Methods**: Public methods performing user actions (e.g., `navigate()`, `clickOnGetStarted()`).
  4. **State / Verification Queries**: Public methods returning state checks or exposing locators for assertions (e.g., `isHeaderVisible()`).

### Standard Page Object Template

```java
package com.example.bdd.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ExamplePage {
    private final Page page;

    // Locators
    private final Locator getStarted;
    private final Locator pageHeader;

    public ExamplePage(Page page) {
        this.page = page;
        this.getStarted = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Get Started"));
        this.pageHeader = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Installation"));
    }

    public void navigate() {
        page.navigate("https://playwright.dev");
    }

    public void clickOnGetStarted() {
        getStarted.click();
    }

    public Locator getPageHeader() {
        return pageHeader;
    }

    public void verifyRedirectedToIntroPage() {
        assertThat(page).hasURL(java.util.regex.Pattern.compile(".*/docs/intro.*"));
        assertThat(pageHeader).isVisible();
    }
}
```

### Usage in Step Definitions

Step definitions obtain the active `Page` instance from `PlaywrightHooks` (injected via PicoContainer) and delegate verification to the Page Object or Playwright assertions:

```java
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
        examplePage.verifyRedirectedToIntroPage();
    }
}
```

---

## 🤖 GitHub Actions Workflow

The CI/CD pipeline is defined in `.github/workflows/bdd-tests.yml` and automates headless BDD test execution on pull requests and branch pushes.

### Workflow Configuration Overview

- **Workflow File**: `.github/workflows/bdd-tests.yml`
- **Triggers**:
  - `push`: on branches `main`, `develop`
  - `pull_request`: on branch `main`
- **Runner Environment**: `ubuntu-latest`
- **Java Setup**: JDK 20 (`temurin` distribution)
- **Dependency Caching**: Maven `.m2/repository` cached against hash of `pom.xml`
- **Browser Installation**: Automatically runs Playwright CLI to install required browsers and system dependencies:
  ```bash
  mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install --with-deps"
  ```
- **Test Execution**: Runs test suite via `mvn clean test`
- **Artifacts**: Uploads Cucumber HTML report (`target/cucumber-report.html`) on completion (`if: always()`) named `cucumber-report`.

---

## 🚀 Common Commands & Workflows

Run the following commands from the project root:

### Run All Tests
```powershell
mvn clean test
```

### Run Test Runner Directly
```powershell
mvn test -Dtest=TestRunner
```

### Run Tests Filtered by Cucumber Tag
```powershell
mvn test -Dcucumber.filter.tags="@smoke"
```

### Run Standalone Main Class
```powershell
mvn compile exec:java -Dexec.mainClass="org.example.Main"
```

### View Test Reports
Reports are generated after test runs in the `target/` directory:
- HTML Report: `target/cucumber-report.html`
- JSON Report: `target/cucumber-report.json`

---

## 📋 Guidelines for Agent & Development Best Practices

### 1. Locator Strategy
- **Prioritize Accessibility / User-facing Locators**:
  - Prefer `page.getByRole(...)`, `page.getByText(...)`, `page.getByLabel(...)`, `page.getByPlaceholder(...)`.
  - Avoid brittle XPath or deep CSS selectors unless no accessible alternative exists.

### 2. Assertions
- Use **Playwright Assertions** (`com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat`) for web elements:
  - Supports auto-retrying until condition is met or timeout expires.
  - Example: `assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Installation"))).isVisible();`

### 3. Step Definitions & State Management
- Inject `PlaywrightHooks` into step definition classes via **PicoContainer constructor injection** to share the active `Page` instance across step classes within a scenario.
- Instantiate Page Objects inside step methods passing `hooks.getPage()`.
- Avoid static shared state across scenarios to maintain test isolation.

### 4. Browser Lifecycle & Execution Mode
- `PlaywrightHooks.java` manages browser startup and teardown per scenario.
- Default browser is Chromium. Headless mode can be toggled in `PlaywrightHooks.java` (`new BrowserType.LaunchOptions().setHeadless(false)`).

### 5. Code & Test Validation
- When adding or modifying features, step definitions, page objects, or hooks, verify by running `mvn clean test-compile` or `mvn test`.

---

## 🔗 Target Web Applications & Testing References
- Playwright Documentation: https://playwright.dev
- Automation Exercise: https://automationexercise.com/