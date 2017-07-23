package mevenk.worldclock;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import mevenk.worldclock.bean.RunningClockDisplayPanel;
import mevenk.worldclock.exception.DefaultRunningClockDisplayPanelAlreadyExistsException;
import mevenk.worldclock.util.TimeZonesComboBox;
import mevenk.worldclock.util.WorldClockUtil;

public class WorldClock {

	private static final int ALIGNMENT_CENTER = SwingConstants.CENTER;

	private static final int MAX_TIME_ZONES = WorldClockUtil.MAX_TIME_ZONES;

	private static final JCheckBox CHECK_BOX_RUNNING_CLOCK_ALWAYS_ON_TOP = new JCheckBox();

	private static JFrame frameWorldClock;

	private static GridBagConstraints gridBagConstraintsFrameWorldClock;

	private JPanel panelControlPanel;
	private JButton buttonAddTime;

	private RunningClockDisplayPanel runningClockDisplayPanelLocalTimeZone;

	private static int timeZoneCounter;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					Thread currentThread = Thread.currentThread();
					String currentThreadName = currentThread.getName();
					currentThread.setName("World Clock - JAVA (" + currentThreadName + ") started @ " + new Date());

					new WorldClock();
					frameWorldClock.setVisible(true);

				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws DefaultRunningClockDisplayPanelAlreadyExistsException
	 */
	public WorldClock() throws DefaultRunningClockDisplayPanelAlreadyExistsException {
		runningClockDisplayPanelLocalTimeZone = RunningClockDisplayPanel.defaultRunningClockDisplayPanel();
		gridBagConstraintsFrameWorldClock = new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 1, 1);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frameWorldClock = new JFrame();
		frameWorldClock.setBounds(100, 100, 450, 300);
		frameWorldClock.setIconImage(Toolkit.getDefaultToolkit()
				.getImage(WorldClock.class.getResource("/mevenk/image/mevenkGitHubLogo.png")));
		frameWorldClock.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameWorldClock.setAlwaysOnTop(true);
		frameWorldClock.setType(Type.UTILITY);
		frameWorldClock.setTitle(runningClockDisplayPanelLocalTimeZone.getTimeZoneBean().timeZoneTitle());
		frameWorldClock.setResizable(false);
		frameWorldClock.setLayout(new GridBagLayout());
		frameWorldClock.setSize(250, 100);
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		frameWorldClock.setLocation(screenDimension.width - frameWorldClock.getSize().width - 50,
				screenDimension.height / 5 - frameWorldClock.getSize().height / 2);

		CHECK_BOX_RUNNING_CLOCK_ALWAYS_ON_TOP.setText("Always On Top");
		CHECK_BOX_RUNNING_CLOCK_ALWAYS_ON_TOP.setSelected(frameWorldClock.isAlwaysOnTop());
		CHECK_BOX_RUNNING_CLOCK_ALWAYS_ON_TOP.setHorizontalAlignment(ALIGNMENT_CENTER);
		CHECK_BOX_RUNNING_CLOCK_ALWAYS_ON_TOP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frameWorldClock.setAlwaysOnTop(CHECK_BOX_RUNNING_CLOCK_ALWAYS_ON_TOP.isSelected());

			}
		});

		buttonAddTime = new JButton("ADD");
		buttonAddTime.setHorizontalAlignment(ALIGNMENT_CENTER);
		buttonAddTime.setAlignmentX(JButton.RIGHT_ALIGNMENT);
		buttonAddTime.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (timeZoneCounter == MAX_TIME_ZONES) {
					JOptionPane.showMessageDialog(frameWorldClock, "Max Limit Reached !!", "Add New Time",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				TimeZonesComboBox timeZonesComboBox = new TimeZonesComboBox();

				JOptionPane.showMessageDialog(frameWorldClock, timeZonesComboBox, "Select TimeZone",
						JOptionPane.QUESTION_MESSAGE);

				TimeZone selectedTimeZone = timeZonesComboBox.selectedTimeZone();

				if (selectedTimeZone == null) {
					return;
				}

				final RunningClockDisplayPanel runningClockDisplayPanel = new RunningClockDisplayPanel(
						selectedTimeZone);

				frameWorldClock.setSize(frameWorldClock.getWidth(),
						frameWorldClock.getHeight() + runningClockDisplayPanel.getHeight());

				gridBagConstraintsFrameWorldClock.gridy = gridBagConstraintsFrameWorldClock.gridy + 1;
				frameWorldClock.getContentPane().add(runningClockDisplayPanel, gridBagConstraintsFrameWorldClock);

				timeZoneCounter++;
			}
		});

		panelControlPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.LINE_START;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;

		panelControlPanel.setPreferredSize(new Dimension(frameWorldClock.getWidth() - 5, 60));
		panelControlPanel.setMaximumSize(new Dimension(frameWorldClock.getWidth() - 5, 60));
		panelControlPanel.add(CHECK_BOX_RUNNING_CLOCK_ALWAYS_ON_TOP, gridBagConstraints);

		gridBagConstraints.fill = GridBagConstraints.LINE_END;
		gridBagConstraints.gridx = 1;
		panelControlPanel.add(buttonAddTime, gridBagConstraints);

		frameWorldClock.getContentPane().add(panelControlPanel, gridBagConstraintsFrameWorldClock);

		gridBagConstraintsFrameWorldClock.gridy = 1;
		frameWorldClock.getContentPane().add(runningClockDisplayPanelLocalTimeZone, gridBagConstraintsFrameWorldClock);

	}

	public static void removeRunningClockDisplayPanel(RunningClockDisplayPanel runningClockDisplayPanel) {
		frameWorldClock.remove(runningClockDisplayPanel);
		gridBagConstraintsFrameWorldClock.gridy = gridBagConstraintsFrameWorldClock.gridy - 1;
		frameWorldClock.setSize(frameWorldClock.getWidth(),
				frameWorldClock.getHeight() - runningClockDisplayPanel.getHeight());
		frameWorldClock.revalidate();
		frameWorldClock.repaint();
		timeZoneCounter--;
	}

}
