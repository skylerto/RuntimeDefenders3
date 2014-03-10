package mvcV2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class EditingView {

	private static JFrame frame;

	protected static JTextField docTitle;
	protected static JTextField title;
	protected static JTextField subtitle;

	protected static JComboBox pageSize;
	protected static JTextField fontSize;
	protected static JTextField staffSpacing;

	protected static JButton apply = new JButton("Apply");
	protected static JButton cancel = new JButton("Cancel");

	private static Font labelFont = new Font("SANS_SERIF", Font.BOLD, 12);
	protected static String[] pageSizes = { "A4", "A5", "Long page name" };

	public EditingView() {
		CreateAndShowGUI();
	}

	public static JPanel titles() {

		JPanel panel = new JPanel();
		GridBagConstraints c = new GridBagConstraints();
		panel.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.HORIZONTAL;

		// Document Name.
		JLabel docLabel = new JLabel("Document Title: ");
		docLabel.setFont(labelFont);
		docTitle = new JTextField(20);
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = c.BASELINE_TRAILING;
		panel.add(docLabel, c);
		c.gridx = 1;
		c.gridy = 0;
		panel.add(docTitle, c);

		// Song Title.
		JLabel songLabel = new JLabel("Song Title: ");
		songLabel.setFont(labelFont);
		title = new JTextField(20);
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = c.BASELINE_TRAILING;
		panel.add(songLabel, c);
		c.gridx = 1;
		c.gridy = 1;
		panel.add(title, c);

		// Subtitle.
		JLabel subLabel = new JLabel("Subtitle: ");
		subLabel.setFont(labelFont);
		subtitle = new JTextField(20);
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = c.BASELINE_TRAILING;
		panel.add(subLabel, c);
		c.gridx = 1;
		c.gridy = 2;
		panel.add(subtitle, c);

		return panel;
	}

	private static JPanel pageLayout() {
		JPanel panel = new JPanel();
		// Combo box for pace size.
		JLabel pageLabel = new JLabel("Page Size: ");
		pageLabel.setFont(labelFont);
		pageSize = new JComboBox(pageSizes);
		panel.add(pageLabel);
		panel.add(pageSize);
		return panel;
	}

	private static JPanel pageProperties() {
		JPanel panel = new JPanel();

		// Staff spacing.
		JLabel spacingLabel = new JLabel("Staff Spacing: ");
		spacingLabel.setFont(labelFont);
		staffSpacing = new JTextField(5);
		panel.add(spacingLabel);
		panel.add(staffSpacing);

		// Font size.
		JLabel fontLabel = new JLabel("Font Size: ");
		fontLabel.setFont(labelFont);
		fontSize = new JTextField(5);
		panel.add(fontLabel);
		panel.add(fontSize);

		return panel;
	}

	private static JPanel buttons() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		// Buttons (Apply & Cancel).
		panel.add(apply);
		panel.add(cancel);
		return panel;
	}

	/**
	 * Sets the look and feel of the Java application.
	 */
	private static void setLookAndFeel() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception exc) {

		}
	}

	/**
	 * Creates and shows the GUI
	 */
	public static void CreateAndShowGUI() {
		setLookAndFeel();
		frame = new JFrame("Edit File Properties: ");
		frame.setResizable(false);
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 0;
		c.gridy = 0;
		frame.add(titles(), c); // Exec method to add the
								// titles.

		c.gridx = 0;
		c.gridy = 1;
		frame.add(pageLayout(), c);

		c.gridx = 0;
		c.gridy = 2;
		frame.add(pageProperties(), c); // Exec
										// method
										// to
										// add
										// page
										// properties.

		c.gridx = 0;
		c.gridy = 3;
		frame.add(buttons(), c); // Exec method to add
									// button panel.

		frame.pack();
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				CreateAndShowGUI();
			}
		});
	}

	void addApplyListener(ActionListener listenForSelectButton) {

		apply.addActionListener(listenForSelectButton);

	}

	void addCancelListener(ActionListener listenForSelectButton) {

		cancel.addActionListener(listenForSelectButton);

	}

}
