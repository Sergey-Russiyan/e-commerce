package framework.tests;

import java.util.concurrent.TimeUnit;

import framework.context.WebDriverContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import framework.listeners.LogListener;
import framework.listeners.ReportListener;
import framework.util.LoggerUtil;
import framework.util.TestProperties;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Every test class should extend this class.
 *
 * @author Serhii R.
 */
@Listeners({ ReportListener.class, LogListener.class })
public class BaseTest {

	protected WebDriver driver;

	/**
	 * Global setup before test suite
	 */
	@BeforeSuite(alwaysRun = true)
	public void beforeSuiteActions() {
		LoggerUtil.log("************************** Test Execution Started ************************************");
		TestProperties.loadAllProperties();
	}

	/**
	 * Tear down actions after test suite
	 */
	@AfterSuite(alwaysRun = true)
	public void afterTestSuiteActions() {
		LoggerUtil.log("************************** Test Execution Finished ************************************");
	}

	/**
	 * Setup actions before each test class
	 */
	@BeforeClass
	protected void beforeClassActions() {
		//TODO {link to relevant JIRA task} | add ability to run other browsers: Firefox, Opera, Safari etc.
		WebDriverManager.chromedriver().setup();
		ChromeOptions ops = new ChromeOptions();
		ops.addArguments("disable-infobars");
		driver = new ChromeDriver(ops);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		WebDriverContext.setDriver(driver);
	}

	/**
	 * Tear down actions after each test class
	 */
	@AfterClass
	public void afterClassActions() {
		if (driver != null) {
			driver.close();
			driver.quit();
		}
	}
}
