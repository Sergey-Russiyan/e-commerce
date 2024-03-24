package framework.util;


import framework.listeners.LogListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class has all Logging related utilities.
 *
 * @author Serhii R.
 */
public class LoggerUtil {

	/** The logger. */
	private static final Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	/**
	 * Log.
	 *
	 * @param message the message
	 */
	public static void log(String message) {
		logger.info(message);
	}

	/**
	 * Gets the logger.
	 *
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}
}