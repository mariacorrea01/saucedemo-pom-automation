# SauceDemo POM Automation Framework

Selenium + Java + TestNG automation framework for **[saucedemo.com](https://www.saucedemo.com/)**, built with the **Page Object Model (POM)** design pattern and **PageFactory**.

## 1. Project purpose

This project automates the three required end-to-end scenarios on SauceDemo, plus one extra negative-login scenario:

| # | Scenario | Test class |
|---|----------|------------|
| 1 | Complete purchase buy-flow (random product → cart → checkout → confirmation) | `PurchaseProductTest` |
| 2 | Add 3 products to the cart and remove all of them | `RemoveCartItemsTest` |
| 3 | Log out and verify redirection to the login page | `LogoutTest` |
| 4 | Invalid login shows the correct error message (extra scenario) | `LoginNegativeTest` |

## 2. Tech stack

- **Java 17**
- **Selenium WebDriver 4** (`PageFactory`, explicit waits, the `Actions` class)
- **TestNG** as the test runner and assertion library (`@BeforeMethod`/`@AfterMethod`, `@DataProvider`, `@Parameters`, groups, `ITestListener`)
- **WebDriverManager** — no need to manually download/manage chromedriver binaries
- **Maven** for dependency management and build lifecycle
- **Log4j2** for execution logging

## 3. Project structure

```
saucedemo-pom-automation/
├── docs/                              # Documentation (test plan, architecture notes)
├── src/
│   └── test/
│       ├── java/com/saucedemo/
│       │   ├── data/                  # Test data models (CustomerInfo)
│       │   ├── pages/                 # Page Objects (one class per screen)
│       │   │   ├── base/              # BasePage — shared waits + PageFactory init
│       │   │   └── components/        # Reusable components (HeaderComponent)
│       │   ├── tests/                 # Test classes (BaseTest + the 4 scenarios)
│       │   └── utils/                 # DriverManager
│       └── resources/                 # config, test data
├── testng.xml                         # Suite grouping all scenarios, parameters, and the listener
├── pom.xml
└── README.md
```

## 4. Page Object Model design decisions

- **`BasePage`** centralizes explicit waits (`WebDriverWait`) and `PageFactory.initElements(...)`, so every page object gets consistent, safe interactions (`click`, `type`, `getText`) without duplicating wait logic.
- **`HeaderComponent`** models the hamburger menu / logout link that appears identically on every authenticated page. Instead of copy-pasting those locators into every page, `InventoryPage` *composes* a `HeaderComponent` instance — favoring composition over duplication inside POM.
- Each page's public methods return the **next page object** the user lands on (e.g. `CartPage.proceedToCheckout()` returns `CheckoutStepOnePage`), so tests read like the actual user flow instead of a list of low-level Selenium calls.
- **`@FindBy` + `PageFactory`** is used throughout instead of `driver.findElement(...)` calls scattered in pages.
- Locators use a mix of `id`, `className`, and `css` selectors, chosen per element based on what's most stable in SauceDemo's DOM.
- `InventoryPage.addRandomProductToCart()` uses Selenium's **`Actions`** class to hover over the randomly chosen product before adding it to the cart.

## 5. Test design decisions

- **`BaseTest`** owns the `@BeforeMethod`/`@AfterMethod` hooks: every test starts from a clean browser session (no shared state between tests, making them atomic and independent) and every browser is always closed, even on failure.
- **`BaseTest` also implements TestNG's `ITestListener`**, logging every test's start/pass/fail outcome to the console automatically, without adding logging code inside each individual test. It's registered in `testng.xml` via `<listeners>`.
- **`RemoveCartItemsTest`** sources its list of products from a **`@DataProvider`**, instead of a hardcoded constant, so a new product combination could be tested by adding another data row, with zero changes to the test logic.
- **`LoginNegativeTest`** receives its username/password/expected-error-message via **`@Parameters`**, injected from `testng.xml`, so invalid-credential scenarios can be changed from the suite file without touching Java code.
- Tests are tagged with **TestNG groups** (`smoke` for the critical happy-path scenarios, `regression` for the more detailed ones), so subsets can be run selectively.
- Assertions focus on **user-visible outcomes** (page loaded, cart badge count, confirmation message) rather than implementation details.

## 6. How to run the tests

### Prerequisites
- JDK 17+
- Maven 3.6+
- Google Chrome installed locally

### Run from IntelliJ
Right-click `testng.xml` → **Run 'testng.xml'** to execute all scenarios (this is required for `LoginNegativeTest`, since its `@Parameters` values only exist in this file — running it directly via the method's green arrow will fail).

### Run from the terminal
```bash
mvn clean test
```

Execution logs (including the listener's `STARTING TEST` / `PASSED` / `FAILED` lines) are written to the console; TestNG's own HTML/XML report is generated under `target/surefire-reports/`.

## 7. Branching strategy

This repository follows a simple trunk-based flow for learning purposes:

- `main` — always green, deployable state of the framework.
- `develop` — integration branch where feature branches are merged before going to `main`.
- `feature/*` — one branch per page object / test scenario (e.g. `feature/base-page`, `feature/login-page`, `feature/cart-page`, `feature/purchase-test`, `feature/remove-cart-items-test`, `feature/logout-test`, `feature/testng-suite`, `feature/testng-groups`).

Commits are kept small and scoped to a single concern (one page object, one test, one config file) to keep the history easy to review.

## 8. Known bugs fixed during development

While building this framework, a few real bugs were found and fixed:

- A typo in `LoginPage.loginAs()` was writing the password into the username field instead of the password field.
- A race condition in `InventoryPage.getCartItemCount()`: it checked the cart badge instantly instead of waiting for it to render after adding a product.
- Chrome's built-in "password found in a data breach" warning (triggered by SauceDemo's well-known public password) was blocking the page; disabled via ChromeOptions preferences in `DriverManager`.
- An early version of the `Actions`-based hover used a fragile XPath rebuilt from the product's name string, which broke on names containing special characters (parentheses, periods). Fixed by hovering over the already-selected `WebElement` directly instead of re-locating it.