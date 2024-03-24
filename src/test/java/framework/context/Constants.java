package framework.context;

/**
 * The Class is for Constants.
 *
 * @author Serhii R.
 */
public class Constants {

	/** The Constant WORKING_DIRECTORY. */
	public static final String WORKING_DIRECTORY = System.getProperty("user.dir");

	/** Reporting REPORT_DIRECTORY. */
	public final static String REPORT_DIRECTORY = WORKING_DIRECTORY + "/ExtentReports/AutomationResult.html";

	/** Reporting PROJECT_NAME. */
	public final static String PROJECT_NAME = "Swag Labs E-Shop";

	/** Reporting EXTENT_CONFIG_PATH. */
	public final static String EXTENT_CONFIG_PATH = WORKING_DIRECTORY + "/src/test/resources/config/extent-config.xml";

	/** The Constant PROPERTY_FILE_PATH. */
	public final static String PROPERTY_FILE_PATH = WORKING_DIRECTORY + "/src/test/resources/config/test.properties";

}
