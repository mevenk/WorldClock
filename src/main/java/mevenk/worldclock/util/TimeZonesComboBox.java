package mevenk.worldclock.util;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import mevenk.worldclock.bean.TimeZoneBean;

/**
 * A class for filtered combo box.
 */

@SuppressWarnings({ "serial", "rawtypes" })
public class TimeZonesComboBox extends JComboBox {

	private static final List<String> TIME_ZONES = new ArrayList<String>();

	static {
		TIME_ZONES.add("");
		TIME_ZONES.addAll(Arrays.asList(TimeZoneBean.DETAILED_TIME_ZONES));
	}

	@SuppressWarnings("unchecked")
	public TimeZonesComboBox() {
		super(TIME_ZONES.toArray());
		setEditable(true);
		setMaximumSize(new Dimension(200, 300));

		final JTextField textfield = (JTextField) this.getEditor().getEditorComponent();

		/**
		 * Listen for key presses.
		 */
		textfield.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ke) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						/**
						 * On key press filter the list. If there is "text" entered in text field of
						 * this combo use that "text" for filtering.
						 */
						comboFilter(textfield.getText());
					}
				});
			}
		});

	}

	/**
	 * Build a list of entries that match the given filter.
	 */
	@SuppressWarnings("unchecked")
	public void comboFilter(String enteredText) {
		List<String> entriesFiltered = new ArrayList<String>();

		for (String entry : TIME_ZONES) {
			if (entry.toLowerCase().contains(enteredText.toLowerCase())) {
				entriesFiltered.add(entry);
			}
		}

		if (entriesFiltered.size() > 0) {
			this.setModel(new DefaultComboBoxModel<>(entriesFiltered.toArray()));
			this.setSelectedItem(enteredText);
			this.showPopup();
		} else {
			this.hidePopup();
		}
	}

	public TimeZone selectedTimeZone() {

		String selectedItem = (String) getSelectedItem();

		if (selectedItem == "") {
			return null;
		} else {
			return TimeZone.getTimeZone(selectedItem.split(" ", 3)[1]);
		}

	}
}