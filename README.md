# 🎭 Playwright with Java & Cucumber BDD (Study & Training Project)

[![Java 20](https://img.shields.io/badge/Java-20%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Playwright](https://img.shields.io/badge/Playwright-1.63.0-2EAD33?style=for-the-badge&logo=playwright&logoColor=white)](https://playwright.dev/java/)
[![Cucumber](https://img.shields.io/badge/Cucumber-7.23.0-23D96C?style=for-the-badge&logo=cucumber&logoColor=white)](https://cucumber.io/)
[![JUnit 5](https://img.shields.io/badge/JUnit-5-25A162?style=for-the-badge&logo=junit5&logoColor=white)](https://junit.org/junit5/)

> 🎓 **Project Purpose**:  
> This repository is created specifically for **study, practice, and hands-on training** in modern test automation engineering. It demonstrates how to build an enterprise-ready, maintainable, and scalable testing framework by combining **Playwright for Java**, **Cucumber (BDD)**, and a layered architectural pattern (Page Object Model + API Client Layer).

---

## 📚 What You Will Learn From This Project

By exploring and running this project, you will learn and practice:

1. **Behavior-Driven Development (BDD) with Cucumber**:
   - Writing readable Gherkin features (`Scenario`, `Scenario Outline`, `Examples`, `DataTable`).
   - Tagging strategies (`@smoke`, `@regression`, `@login`, `@negative`) to run targeted test suites.
   - Managing shared test state across steps using **PicoContainer** dependency injection.

2. **Modern Web Automation with Microsoft Playwright**:
   - Using accessibility-first and user-centric locator strategies (`getByRole`, `getByText`, `getByLabel`).
   - Leveraging Playwright's automatic waiting and resilient web assertions (`assertThat`).
   - Intercepting and aborting network routes (e.g., blocking third-party ads and trackers).

3. **Hybrid E2E Testing (UI + API Integration)**:
   - Setting up test preconditions fast and reliably through backend REST APIs before executing browser UI flows.
   - Using Playwright's built-in `APIRequestContext` for direct HTTP communication (`POST`, `GET`, `DELETE`).

4. **Software Engineering Design Patterns**:
   - **Page Object Model (POM)**: Decoupling test steps from DOM selectors and UI mechanics.
   - **API Client Layer (Service Layer)**: Encapsulating endpoints, HTTP methods, and payload building behind clean, typed client classes and DTOs (Data Transfer Objects with Builder Pattern).
   - **Test Data Isolation**: Using dynamic identifiers (e.g., timestamp-based email generation) to prevent data collisions when running tests against shared demo environments.

---

## 🏛️ Architecture & Folder Structure

```
playwrightWithMcp/
├── .github/
│   └── workflows/
│       └── bdd-tests.yml              # CI/CD pipeline running headless tests on GitHub Actions
├── pom.xml                            # Maven project dependencies & build configuration
├── README.md                          # Project documentation and study guide
├── AGENT.md                           # AI agent and development guidelines
└── src/
    ├── main/
    │   └── java/org/example/
    │       └── Main.java              # Standalone Playwright script for initial experimentation
    └── test/
        ├── java/com/example/bdd/
        │   ├── api/                   # 🌐 API Client Layer
        │   │   ├── client/
        │   │   │   └── UserApiClient.java         # Encapsulates HTTP calls to Automation Exercise API
        │   │   └── model/
        │   │       ├── ApiResponse.java           # DTO representing API responses (status & message)
        │   │       └── CreateAccountRequest.java  # DTO with Builder pattern & FormData converter
        │   ├── hooks/                 # ⚙️ Test Lifecycle Hooks
        │   │   └── PlaywrightHooks.java           # Manages Playwright, Browser, Context & Route blocking
        │   ├── pages/                 # 📄 Page Object Model (POM) Layer
        │   │   ├── AccountInformationPage.java    # Registration form interactions & assertions
        │   │   ├── AccountStatusPage.java         # Account created/deleted confirmation screens
        │   │   ├── ExamplePage.java               # Playwright documentation demo page
        │   │   ├── HomePage.java                  # Header, navigation, and user session verification
        │   │   └── LoginSignupPage.java           # Login and signup form interactions
        │   ├── runner/                # 🏃 Test Runner Configuration
        │   │   └── TestRunner.java                # JUnit 5 Platform Suite configured for Cucumber
        │   └── steps/                 # 👣 Cucumber Step Definitions
        │       ├── ExampleSteps.java              # Steps for playwright.dev documentation flow
        │       └── LoginSteps.java                # Steps for authentication, registration & API setup
        └── resources/
            ├── junit-platform.properties          # Cucumber runtime configuration (glue, plugins)
            └── features/              # 🥒 Gherkin Feature Files
                ├── example.feature                # Example documentation navigation scenario
                └── login.feature                  # Login, registration, API creation, and logout scenarios
```

---

## 🧩 Architectural Highlights & Best Practices

### 1. Page Object Model (POM)
Every web page is represented by a dedicated class under `com.example.bdd.pages`. Locators are private, accessible, and query user-visible elements:

```java
public class HomePage {
    private final Page page;
    private final Locator signupLoginLink;

    public HomePage(Page page) {
        this.page = page;
        this.signupLoginLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Signup / Login"));
    }

    public void clickSignupLogin() {
        signupLoginLink.click();
    }
}
```

### 2. Dedicated API Client Layer
Rather than putting raw HTTP URLs or JSON manipulation inside step definitions, API interactions are encapsulated in `com.example.bdd.api`:

```java
// Fluent payload creation using Builder pattern
CreateAccountRequest request = CreateAccountRequest.builder()
        .name("Valid User")
        .email(dynamicEmail)
        .password(password)
        .title("Mr")
        .country("Canada")
        // ... other required fields
        .build();

// Executed cleanly via client
APIResponse response = userApiClient.createAccount(request);
ApiResponse apiResponse = userApiClient.parseApiResponse(response);

assertEquals(201, apiResponse.getResponseCode());
assertEquals("User created!", apiResponse.getMessage());
```

### 3. Network Ad-Blocking for Cleaner Test Runs
The target practice site ([Automation Exercise](https://automationexercise.com/)) loads dynamic advertisements that can interfere with tests. In [`PlaywrightHooks.java`](src/test/java/com/example/bdd/hooks/PlaywrightHooks.java), route abortion is configured before each scenario:

```java
context.route("**/*googleads*", Route::abort);
context.route("**/*doubleclick*", Route::abort);
context.route("**/*pagead*", Route::abort);
context.route("**/*adservice.google*", Route::abort);
```

### 4. Dependency Injection via PicoContainer
`PlaywrightHooks` is injected into Step classes (`LoginSteps`, `ExampleSteps`) via constructor injection:

```java
public class LoginSteps {
    private final PlaywrightHooks hooks;

    public LoginSteps(PlaywrightHooks hooks) {
        this.hooks = hooks;
    }
    // ...
}
```
This ensures all step definitions in the same scenario share the exact same `Page` instance without using problematic static variables.

---

## 🛠️ Prerequisites & Installation

### Requirements
- **Java Development Kit (JDK)**: Version 20 or higher (compatible with JDK 21 LTS)
- **Apache Maven**: Version 3.8+
- **Git**

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/playwrightWithMcp.git
cd playwrightWithMcp
```

### 2. Install Playwright Browsers & Dependencies
Playwright requires browser binaries (Chromium, Firefox, WebKit). Run the following Maven command once to download and install them:
```bash
mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install --with-deps"
```

---

## 🚀 How to Run Tests

### Run All Scenarios
Executes all feature files headlessly or with the configured browser mode:
```powershell
mvn clean test
```

### Run by Specific Tags
You can filter which scenarios run using Cucumber tag expressions:

- **Run only smoke scenarios**:
  ```powershell
  mvn test "-Dcucumber.filter.tags=@smoke"
  ```

- **Run login & smoke scenarios**:
  ```powershell
  mvn test "-Dcucumber.filter.tags=@login and @smoke"
  ```

- **Run negative scenarios**:
  ```powershell
  mvn test "-Dcucumber.filter.tags=@negative"
  ```

- **Run full regression suite**:
  ```powershell
  mvn test "-Dcucumber.filter.tags=@regression"
  ```

### Run the Standalone Demo Script
If you want to test Playwright without Cucumber:
```powershell
mvn compile exec:java -Dexec.mainClass="org.example.Main"
```

---

## 📊 Test Reports

After running the tests, detailed Cucumber reports are generated in the `target/` directory:

- **HTML Report**: `target/cucumber-report.html` (open in any web browser to see interactive execution results, step timings, and failure stack traces).
- **JSON Report**: `target/cucumber-report.json` (useful for CI/CD integrations and dashboard tools).

---

## 🎯 Target Practice Environments

- **Automation Exercise**: [https://automationexercise.com/](https://automationexercise.com/) (E-commerce UI & REST API practice sandbox)
- **Playwright Official Documentation**: [https://playwright.dev/](https://playwright.dev/)
