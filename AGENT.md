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
├── pom.xml                               # Maven project configuration & dependencies
├── example.png                           # Sample generated screenshot / artifacts
└── src/
    ├── main/
    │   └── java/org/example/
    │       └── Main.java                 # Standalone Playwright demo execution
    └── test/
        ├── java/com/example/bdd/
        │   ├── hooks/
        │   │   └── PlaywrightHooks.java  # Lifecycle hooks (@Before, @After) managing Playwright, Browser, Context, Page
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
- Avoid static shared state across scenarios to maintain test isolation.

### 4. Browser Lifecycle & Execution Mode
- `PlaywrightHooks.java` manages browser startup and teardown per scenario.
- Default browser is Chromium. Headless mode can be toggled in `PlaywrightHooks.java` (`new BrowserType.LaunchOptions().setHeadless(false)`).

### 5. Code & Test Validation
- When adding or modifying features, step definitions, or hooks, verify by running `mvn clean test-compile` or `mvn test`.

https://automationexercise.com/