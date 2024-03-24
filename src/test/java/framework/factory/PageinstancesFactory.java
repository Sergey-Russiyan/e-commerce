package framework.factory;

import java.lang.reflect.InvocationTargetException;

import framework.context.WebDriverContext;
import framework.pages.BasePage;
import org.openqa.selenium.WebDriver;

/**
 * A factory for creating Pageinstances objects.
 * currently deprecated
 * @author Serhii R.
 */
@Deprecated
public class PageinstancesFactory {

	/**
	 * Gets the single instance of PageinstancesFactory.
	 *
	 * @param <T> the generic type
	 * @param type the type
	 * @return single instance of PageinstancesFactory
	 */
	public static <T extends BasePage> T getInstance(Class<T> type) {
		try {
			return type.getConstructor(WebDriver.class).newInstance(WebDriverContext.getDriver());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
}
