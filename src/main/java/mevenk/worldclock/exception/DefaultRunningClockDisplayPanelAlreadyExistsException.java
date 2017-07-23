/**
 * 
 */
package mevenk.worldclock.exception;

import mevenk.worldclock.bean.TimeZoneBean;

/**
 * The Class DefaultRunningClockDisplayPanelAlreadyExistsException.
 */
@SuppressWarnings("serial")
public class DefaultRunningClockDisplayPanelAlreadyExistsException extends Exception {

	/**
	 * Instantiates a new default running clock display panel already exists
	 * exception.
	 *
	 * @param defaultRunningClockTimeZoneBean
	 *            the default running clock time zone bean
	 */
	public DefaultRunningClockDisplayPanelAlreadyExistsException(TimeZoneBean defaultRunningClockTimeZoneBean) {
		super(wrappedMessage(defaultRunningClockTimeZoneBean));
	}

	/**
	 * Instantiates a new default running clock display panel already exists
	 * exception.
	 *
	 * @param defaultRunningClockTimeZoneBean
	 *            the default running clock time zone bean
	 * @param message
	 *            the message
	 */
	public DefaultRunningClockDisplayPanelAlreadyExistsException(TimeZoneBean defaultRunningClockTimeZoneBean,
			String message) {
		super(wrappedMessage(defaultRunningClockTimeZoneBean) + message);
	}

	/**
	 * Instantiates a new default running clock display panel already exists
	 * exception.
	 *
	 * @param defaultRunningClockTimeZoneBean
	 *            the default running clock time zone bean
	 * @param cause
	 *            the cause
	 */
	public DefaultRunningClockDisplayPanelAlreadyExistsException(TimeZoneBean defaultRunningClockTimeZoneBean,
			Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new default running clock display panel already exists
	 * exception.
	 *
	 * @param defaultRunningClockTimeZoneBean
	 *            the default running clock time zone bean
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public DefaultRunningClockDisplayPanelAlreadyExistsException(TimeZoneBean defaultRunningClockTimeZoneBean,
			String message, Throwable cause) {
		super(wrappedMessage(defaultRunningClockTimeZoneBean) + message, cause);
	}

	/**
	 * Instantiates a new default running clock display panel already exists
	 * exception.
	 *
	 * @param defaultRunningClockTimeZoneBean
	 *            the default running clock time zone bean
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 * @param enableSuppression
	 *            the enable suppression
	 * @param writableStackTrace
	 *            the writable stack trace
	 */
	public DefaultRunningClockDisplayPanelAlreadyExistsException(TimeZoneBean defaultRunningClockTimeZoneBean,
			String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(wrappedMessage(defaultRunningClockTimeZoneBean) + message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Wrapped message.
	 *
	 * @param defaultRunningClockTimeZoneBean
	 *            the default running clock time zone bean
	 * @return the string
	 */
	private static String wrappedMessage(TimeZoneBean defaultRunningClockTimeZoneBean) {
		return "Default Running Clock Display Pane Already Created !! (" + defaultRunningClockTimeZoneBean + ")";
	}

}
