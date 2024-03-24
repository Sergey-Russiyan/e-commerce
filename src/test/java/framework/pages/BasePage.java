package framework.pages;

import java.time.Duration;

import framework.util.LoggerUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * The Class BasePage every Page should extend this class.
 *
 * @author Serhii R.
 */
public class BasePage {

	/** The driver. */
	protected WebDriver driver;

	/** The waiter. */
	protected FluentWait<WebDriver> waiter;

	/**
	 * Instantiates a new base page.
	 *
	 * @param driver the driver
	 */
	public BasePage(WebDriver driver) {
		super();
		this.driver = driver;
		PageFactory.initElements(driver, this);
		waiter = new FluentWait<>(driver).ignoring(NoSuchElementException.class, WebDriverException.class)
				.withTimeout(Duration.ofSeconds(3)).pollingEvery(Duration.ofSeconds(1));
	}
	public boolean isElementDisplayed(By locator, int waitTimeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(waitTimeout));
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			return false;
		} catch (TimeoutException e) {
			LoggerUtil.log("At page:\n" + driver.getCurrentUrl() + "\nNot displayed element:\n" + locator);
			return true;
		}
	}

}
