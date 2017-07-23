/**
 * 
 */
package mevenk.worldclock.bean;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import mevenk.worldclock.WorldClock;
import mevenk.worldclock.exception.DefaultRunningClockDisplayPanelAlreadyExistsException;

/**
 * @author Venkatesh
 *
 */
@SuppressWarnings("serial")
public class RunningClockDisplayPanel extends JPanel {

	private static final int CENTER = SwingConstants.CENTER;

	private static final int PANEL_WIDTH = 245;
	private static final int PANEL_HEIGHT = 50;
	private static final Dimension PANEL_DIMENSION = new Dimension(PANEL_WIDTH, PANEL_HEIGHT);

	private static final int REMOVE_PANEL_BUTTON_WIDTH = 25;
	private static final int REMOVE_PANEL_BUTTON_HEIGHT = 25;
	private static final Dimension REMOVE_PANEL_BUTTON_DIMENSION = new Dimension(REMOVE_PANEL_BUTTON_WIDTH,
			REMOVE_PANEL_BUTTON_HEIGHT);

	private static TimeZoneBean defaultRunningClockTimeZoneBean;

	private TimeZoneBean timeZoneBean;
	private JLabel labelRunningClock;
	private JButton buttonRemoveCurrentRunningClockDisplayPanell;

	private RunningClock runningClock;

	private GridBagConstraints gridBagConstraints = new GridBagConstraints(0, 0, 1, 2, 1, 1, GridBagConstraints.CENTER,
			GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1);

	private RunningClockDisplayPanel() {
		super();
		this.timeZoneBean = new TimeZoneBean(TimeZone.getDefault());
		buildRequiredElements();
		removeAll();
		labelRunningClock.setBorder(null);
		setBorder(null);
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 1;
		add(labelRunningClock, gridBagConstraints);
		defaultRunningClockTimeZoneBean = timeZoneBean;
	}

	public RunningClockDisplayPanel(TimeZone timeZone) {
		super();
		this.timeZoneBean = new TimeZoneBean(timeZone);
		buildRequiredElements();
	}

	public static RunningClockDisplayPanel defaultRunningClockDisplayPanel()
			throws DefaultRunningClockDisplayPanelAlreadyExistsException {

		if (defaultRunningClockTimeZoneBean != null) {
			throw new DefaultRunningClockDisplayPanelAlreadyExistsException(defaultRunningClockTimeZoneBean);
		}
		return new RunningClockDisplayPanel();
	}

	/**
	 * @return the timeZoneBean
	 */
	public TimeZoneBean getTimeZoneBean() {
		return timeZoneBean;
	}

	private void buildRequiredElements() {
		buildLabelRunningClock();
		buildPanel();
	}

	private void buildLabelRunningClock() {
		labelRunningClock = new JLabel();
		labelRunningClock.setHorizontalAlignment(CENTER);
		labelRunningClock.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
	}

	private void buildPanel() {
		setSize(PANEL_DIMENSION);
		setPreferredSize(PANEL_DIMENSION);
		setMaximumSize(PANEL_DIMENSION);
		setBorder(new TitledBorder(new EtchedBorder(), timeZoneBean.timeZoneTitle()));
		setLayout(new GridBagLayout());
		add(labelRunningClock, gridBagConstraints);
		buildButtonRemoveTime(this);
		gridBagConstraints.gridx = 1;
		add(buttonRemoveCurrentRunningClockDisplayPanell, gridBagConstraints);
		setToolTipText(timeZoneBean.getDetailedTimeZone());
		startRunningClock();
	}

	private void buildButtonRemoveTime(final RunningClockDisplayPanel runningClockDisplayPanel) {
		buttonRemoveCurrentRunningClockDisplayPanell = new JButton("X");
		buttonRemoveCurrentRunningClockDisplayPanell.setPreferredSize(REMOVE_PANEL_BUTTON_DIMENSION);
		buttonRemoveCurrentRunningClockDisplayPanell.setMaximumSize(REMOVE_PANEL_BUTTON_DIMENSION);
		buttonRemoveCurrentRunningClockDisplayPanell.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				WorldClock.removeRunningClockDisplayPanel(runningClockDisplayPanel);
			}
		});
	}

	private void startRunningClock() {
		runningClock = new RunningClock(timeZoneBean.getLongDisplayName(), labelRunningClock,
				timeZoneBean.getTimeZone(), timeZoneBean.timeZoneType);
		runningClock.execute();
	}

	public void stopRunningClock() {
		runningClock.cancel(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RunningClockDisplayPanel [timeZoneBean=" + timeZoneBean + ", runningClock=" + runningClock + "]";
	}

}
