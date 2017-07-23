/**
 * 
 */
package mevenk.worldclock.bean;

import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * @author Venkatesh
 *
 */
public class TimeZoneBean {

	private static final String[] ALL_TIME_ZONES_IDS = TimeZone.getAvailableIDs();

	private static String[] detailedTimeZones = new String[ALL_TIME_ZONES_IDS.length];

	public static final String[] DETAILED_TIME_ZONES = detailedTimeZones();

	private TimeZone timeZone;
	private String timeZoneID;
	private String shortDisplayName;
	private String longDisplayName;
	private String detailedTimeZone;
	public TimeZoneType timeZoneType;

	public enum TimeZoneType {
		LOCAL, NON_LOCAL;
	}

	public TimeZoneBean(TimeZone timeZone) {
		this.timeZone = timeZone;
		buildRequiredElements();
	}

	private void buildRequiredElements() {
		this.timeZoneID = timeZone.getID();
		this.shortDisplayName = shortDisplayName(timeZone);
		this.longDisplayName = longDisplayName(timeZone);
		this.detailedTimeZone = detailedTimeZone(timeZone);
		if (timeZoneID.equalsIgnoreCase(TimeZone.getDefault().getID().toString())) {
			timeZoneType = TimeZoneType.LOCAL;
		} else {
			timeZoneType = TimeZoneType.NON_LOCAL;
		}
	}

	/**
	 * @return the timeZone
	 */
	public TimeZone getTimeZone() {
		return timeZone;
	}

	/**
	 * @return the timeZoneId
	 */
	public String getTimeZoneId() {
		return timeZoneID;
	}

	/**
	 * @return the shortDisplayName
	 */
	public String getShortDisplayName() {
		return shortDisplayName;
	}

	/**
	 * @return the longDisplayName
	 */
	public String getLongDisplayName() {
		return longDisplayName;
	}

	/**
	 * @return the detailedTimeZone
	 */
	public String getDetailedTimeZone() {
		return detailedTimeZone;
	}

	public String timeZoneTitle() {
		return getShortDisplayName() + " (" + getTimeZoneId() + ")";
	}

	private static final String[] detailedTimeZones() {

		TimeZone timeZone = TimeZone.getDefault();

		int currentTimeZoneCounter = -1;
		for (String currentTimeZoneId : ALL_TIME_ZONES_IDS) {
			timeZone = TimeZone.getTimeZone(currentTimeZoneId);
			detailedTimeZones[++currentTimeZoneCounter] = detailedTimeZone(timeZone);
		}

		return detailedTimeZones;
	}

	private static final String detailedTimeZone(TimeZone timeZone) {

		long hours = TimeUnit.MILLISECONDS.toHours(timeZone.getRawOffset());
		long minutes = TimeUnit.MILLISECONDS.toMinutes(timeZone.getRawOffset()) - TimeUnit.HOURS.toMinutes(hours);
		// avoid -4:-30 issue
		minutes = Math.abs(minutes);

		String detailedTimeZone = "";

		Object[] params = { hours, minutes, timeZone.getID(), longDisplayName(timeZone) };

		if (hours > 0) {
			detailedTimeZone = String.format("(GMT+%d:%02d) %s - %s", params);
		} else {
			detailedTimeZone = String.format("(GMT%d:%02d) %s - %s", params);
		}

		return detailedTimeZone;

	}

	private static String shortDisplayName(TimeZone timeZone) {
		return timeZone.getDisplayName(timeZone.useDaylightTime(), TimeZone.SHORT, Locale.getDefault());
	}

	private static String longDisplayName(TimeZone timeZone) {
		return timeZone.getDisplayName(timeZone.useDaylightTime(), TimeZone.LONG, Locale.getDefault());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TimeZoneBean [timeZoneID=" + timeZoneID + ", shortDisplayName=" + shortDisplayName
				+ ", longDisplayName=" + longDisplayName + ", detailedTimeZone=" + detailedTimeZone + ", timeZoneType="
				+ timeZoneType + "]";
	}

}
