package autocomplete;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

public class FilterComboBoxDemo {
	static FilterComboBox fcb;

	static List<String> asList = new ArrayList<String>();

	public static void makeUI() {

		String[] ids = TimeZone.getAvailableIDs();

		asList.add("");
		TimeZone timeZone = TimeZone.getDefault();

		for (String cid : ids) {
			timeZone = TimeZone.getTimeZone(cid);
			String timezoneSearchString = displayTimeZone(timeZone);
			// System.out.println(timezoneSearchString);
			asList.add(timezoneSearchString);
		}

		JFrame frame = new JFrame("Your frame");
		frame.setSize(200, 500);
		/**
		 * Put data to your filtered combobox.
		 */

		fcb = new FilterComboBox(asList);
		fcb.setSize(100, fcb.getHeight());

		fcb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedItem = fcb.getSelectedItem().toString();
				if (selectedItem == "" || !asList.contains(selectedItem)) {
					return;
				}
				System.out.println("Selected Item : " + selectedItem);
			}
		});
		/**
		 * Set up the frame.
		 */
		frame.getContentPane().add(fcb);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private static String displayTimeZone(TimeZone timeZone) {

		long hours = TimeUnit.MILLISECONDS.toHours(timeZone.getRawOffset());
		long minutes = TimeUnit.MILLISECONDS.toMinutes(timeZone.getRawOffset()) - TimeUnit.HOURS.toMinutes(hours);
		// avoid -4:-30 issue
		minutes = Math.abs(minutes);

		String result = "";

		Object[] params = { hours, minutes, timeZone.getID(),
				timeZone.getDisplayName(timeZone.useDaylightTime(), TimeZone.LONG, Locale.getDefault()) };

		if (hours > 0) {
			result = String.format("(GMT+%d:%02d) %s - %s", params);
		} else {
			result = String.format("(GMT%d:%02d) %s - %s", params);
		}

		return result;

	}

	public static void main(String[] args) throws Exception {
		makeUI();
	}
}