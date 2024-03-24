selenium-testng-swaglabs
---

---
A testing framework based on Page Object Model, Selenium, TestNG using Java.

This framework is based in **Page Object Model (POM).**

The framework uses:

1. Java
2. Selenium
3. TestNG
4. ExtentReport
5. Log4j

Steps to create test cases:
----
Let's say we want to automate Login into Swag Labs shop: https://www.saucedemo.com/.  

1.Create LoginPage in **pages.swagLabShop** package.  
  A page class typically should contain all the elements that are present on the page and corresponding action methods.
  
  ```
  public class LoginPage extends BasePage {
	
	@FindBy(name = "q")
	private WebElement searchinput;

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public void searchText(String key) {
		searchinput.sendKeys(key + Keys.ENTER);
	}

}
```
2.Create the test class which class the methods of GoogleSearchPage

```
 @Test(testName = "User could login into Swag Shop",
            description = "Go to login page, grab credentials from UI, log-in")
    public void userCouldLoginTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.goTo();

        Assert.assertEquals(driver.getCurrentUrl(), SwagLabUrls.LOGIN_PAGE.getUrl(), "Unexpected login page url");

        loginPage.tryLoginUsingCredentialsFromPage();
        InventoryPage inventoryPage = new InventoryPage(driver);

        Assert.assertTrue(inventoryPage.isTitleDisplayed(), "No expected title");
        Assert.assertEquals(driver.getCurrentUrl(), inventoryPage.getExpectedUrl(), "Unexpected page after login");
    }
```
3.Add the test class in testng.xml file under the folder `src/test/resources/suites/`

```
<suite name="Suite">
	<listeners></listeners>
	<test thread-count="5" name="Test" parallel="classes">
		<classes>
			<class name="framework.tests.SwagShopOrderTest" />
```
4.Execute the test cases by maven command `mvn clean test`

---

Reporting
---
The framework gives report in 2 ways,

1. Log - In file `logfile.log`.
2. A html report - Which is generated using extent reports, under the folder `ExtentReports`.

---

Key Points:
---

1. The class `WebDriverContext` is responsible for maintaining the same WebDriver instance throughout the test. So whenever you require a webdriver instance which has been using for current test (In current thread) always call `WebDriverContext.getDriver()`.
2. @Deprecated: Always use `PageinstancesFactory.getInstance(type)` to get the instance of particular Page Object. (Of course you can use `new` but it's better use a single approach across the framework.

---

>For any query or suggestions please do comment or mail to sergey.russiyan@gmail.com 
