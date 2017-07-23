package mevenk.worldclock.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.SwingWorker;

import mevenk.worldclock.bean.TimeZoneBean.TimeZoneType;

public class RunningClock extends SwingWorker<Void, Void> {

	private static final String LOCAL_CLOCK_SIMPLE_DATE_FORMAT_PATTERN = "E, dd MMM yyyy HH:mm:ss,SSS";
	private static final String WORLD_CLOCK_SIMPLE_DATE_FORMAT_PATTERN = "E, dd MMM yyyy HH:mm";

	private String runningClockName;
	private JLabel runningClockLabel;

	private SimpleDateFormat simpleDateFormatRunningClock;
	private TimeUnit timeUnit;

	public RunningClock(String runningClockName, JLabel runningClockLabel, TimeZone timeZone,
			TimeZoneType timeZoneType) {
		this.runningClockLabel = runningClockLabel;
		this.runningClockName = runningClockName;
		if (timeZoneType == TimeZoneType.LOCAL) {
			simpleDateFormatRunningClock = new SimpleDateFormat(LOCAL_CLOCK_SIMPLE_DATE_FORMAT_PATTERN);
			timeUnit = TimeUnit.MILLISECONDS;
		} else {
			simpleDateFormatRunningClock = new SimpleDateFormat(WORLD_CLOCK_SIMPLE_DATE_FORMAT_PATTERN);
			simpleDateFormatRunningClock.setTimeZone(timeZone);
			timeUnit = TimeUnit.MINUTES;
		}
	}

	@Override
	protected Void doInBackground() throws Exception {

		String currentThreadName = Thread.currentThread().getName();
		Thread.currentThread().setName("Running Clock - " + runningClockName + " (" + currentThreadName + ")");

		updateRunningClock();

		if (timeUnit == TimeUnit.MINUTES) {
			Thread.sleep(60000 - new Date().getTime() % 60000);
		}

		executeRunningClockTask();

		return null;

	}

	/**
	 * 
	 */
	private void executeRunningClockTask() {
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {

				updateRunningClock();

			}
		}, 0, 1, timeUnit);
	}

	private void updateRunningClock() {
		runningClockLabel.setText(simpleDateFormatRunningClock.format(new Date()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RunningClock [runningClockName=" + runningClockName + ", simpleDateFormatRunningClock="
				+ simpleDateFormatRunningClock.toPattern() + ", timeUnit=" + timeUnit + "]";
	}

}