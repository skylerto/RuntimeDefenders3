package mvcV3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import print.printPDF;
import MVC.PrinterInterface;

import com.alee.extended.layout.VerticalFlowLayout;

public class View {

	protected static JFrame frame;

	// Panels to add to frame.
	protected static JPanel leftSide = new JPanel();
	protected static JPanel rightSide = new JPanel();

	protected static JScrollPane previewPane;

	// Buttons
	protected static JButton browseButton = new JButton("Browse");
	protected static JButton convertButton = new JButton("Convert");
	protected static JButton saveButton = new JButton("Save As...");

	// Menu bar items
	protected static JMenuItem log = new JMenuItem("Log");
	protected static JMenuItem autoCorrection = new JMenuItem(
			"Auto-Corrections");
	protected static JMenuItem emailTab;
	protected static JMenuItem helpTab;

	// Text fields for input and destination folders.
	protected static JTextField input;
	protected static JTextField destination;

	// TITLES
	protected static JTextField title;
	protected static JTextField subtitle;

	// SLIDER INFO
	// MEASURE SLIDER INFO
	protected static JSlider measureFontSize;
	private static final int measureFontMin = 0;
	private static final int measureFontMax = 40;
	private static int measureSized;

	// SPACING SLIDER INFO
	protected static JSlider staffSpacing;
	private static final int staffSpacingMin = 0;
	private static final int staffSpacingMax = 40;
	private static int staffSpacingCurrent;

	// Font
	private static Font labelFont = new Font("SANS_SERIF", Font.BOLD, 12);

	protected static Dimension frameSize = new Dimension(1000, 800);

	protected static String previewImage = "";

	public View() {
		CreateAndShowGUI();
	}

	public static JMenuBar createMenuBar() {
		JMenuBar menuBar;
		JMenu menu;
		menuBar = new JMenuBar();
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEFT);
		menuBar.setLayout(layout);

		// Build the first menu.
		menu = new JMenu("View");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("The first menu");
		menuBar.add(menu);

		// Print function for "File" tab section.
		JMenuItem menu2 = new JMenuItem("Print");
		menu2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// logString += "Opening Printer Interface...\n";
				// updateLog();
				PrinterInterface printWindow = new PrinterInterface();
				printPDF test = new printPDF("outputfiles/musicPDF.pdf");
				printWindow.Scroller2(test);
			}

		});

		menuBar.add(menu2);

		// Build third menu in the menu bar.
		menu = new JMenu("Email");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription(
				"Contain elements to help the user");
		menuBar.add(menu);

		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription(
				"Contain elements to help the user");
		menuBar.add(menu);

		menu.add(log);
		menu.add(autoCorrection);

		return menuBar;
	}

	public static JPanel titles() {

		JPanel panel = new JPanel();
		GridBagConstraints c = new GridBagConstraints();
		panel.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.HORIZONTAL;

		return panel;
	}

	private static JPanel pageProperties() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		// Song Title.
		JLabel songLabel = new JLabel("Title: ");
		songLabel.setFont(labelFont);
		title = new JTextField(20);
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = c.BASELINE_TRAILING;
		c.insets = new Insets(5, 5, 5, 5);

		panel.add(songLabel, c);
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(5, 5, 5, 5);

		panel.add(title, c);

		// Subtitle.
		JLabel subLabel = new JLabel("Subtitle: ");
		subLabel.setFont(labelFont);
		subtitle = new JTextField(20);
		c.gridx = 0;
		c.gridy = 3;
		c.anchor = c.BASELINE_TRAILING;
		panel.add(subLabel, c);
		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(5, 5, 5, 5);

		panel.add(subtitle, c);

		// Staff spacing.
		JLabel pageProperties = new JLabel("Edit Document");
		pageProperties.setFont(new Font("SANS_SERIF", Font.BOLD, 12));
		c.gridx = 0;
		c.gridy = 5;
		panel.add(pageProperties, c);
		JLabel spacingLabel = new JLabel("Staff Spacing: ");
		spacingLabel.setFont(labelFont);
		staffSpacing = new JSlider(JSlider.HORIZONTAL, staffSpacingMin,
				staffSpacingMax, staffSpacingCurrent);
		staffSpacing.setMajorTickSpacing(10);
		staffSpacing.setMinorTickSpacing(1);
		staffSpacing.setPaintTicks(true);
		staffSpacing.setPaintLabels(true);

		c.gridx = 0;
		c.gridy = 6;
		panel.add(spacingLabel, c);

		c.gridx = 0;
		c.gridy = 7;
		c.insets = new Insets(5, 5, 5, 5);

		panel.add(staffSpacing, c);

		// Measure Font size.
		JLabel measureFontLabel = new JLabel("Note Font Size: ");
		measureFontLabel.setFont(labelFont);
		measureFontSize = new JSlider(JSlider.HORIZONTAL, measureFontMin,
				measureFontMax, measureSized);
		measureFontSize.setMajorTickSpacing(10);
		measureFontSize.setMinorTickSpacing(1);
		measureFontSize.setPaintTicks(true);
		measureFontSize.setPaintLabels(true);

		c.gridx = 0;
		c.gridy = 8;
		panel.add(measureFontLabel, c);

		c.gridx = 0;
		c.gridy = 9;
		c.insets = new Insets(5, 5, 5, 5);

		panel.add(measureFontSize, c);

		return panel;
	}

	protected static JPanel buttonPanel() {
		JPanel panel = new JPanel();
		panel.add(browseButton);
		panel.add(convertButton);
		panel.add(saveButton);
		return panel;
	}

	protected static JPanel autoCorrections() {
		JPanel panel = new JPanel();
		return panel;
	}

	protected static void populateLeftPanel() {
		leftSide.setLayout(new VerticalFlowLayout());
		leftSide.add(buttonPanel());
		leftSide.add(pageProperties());
		leftSide.add(autoCorrections());

	}

	protected static JScrollPane buildPreviewScrollPane() {
		previewPane = new JScrollPane();
		Dimension scroll = new Dimension((int) (frameSize.getWidth() / 2), 750);
		previewPane.setMaximumSize(scroll);
		previewPane.setPreferredSize(scroll);
		previewPane.setMinimumSize(scroll);
		ImageIcon ic = new ImageIcon(previewImage);
		previewPane.add(new JLabel(ic));
		return previewPane;
	}

	/**
	 * Adds all the components to the right panel.
	 * 
	 * 
	 */
	protected static void populateRightPanel() {
		rightSide.setLayout(new VerticalFlowLayout());

		// Initiates the input label and adds to the right side panel.
		input = new JTextField(40);
		input.setEnabled(false);
		rightSide.add(input);
		JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
		rightSide.add(separator);
		JLabel previewLabel = new JLabel();
		rightSide.add(previewLabel); // Label naming the display pane.
		rightSide.add(buildPreviewScrollPane()); // Display a scrollPane of the
													// image.
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
		frame = new JFrame("Convert Tab to PDF");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		frame.setJMenuBar(createMenuBar());

		frame.setMaximumSize(frameSize);
		frame.setPreferredSize(frameSize);
		frame.setMinimumSize(frameSize);

		populateLeftPanel(); // Adds all the elements to the left panel.
		populateRightPanel(); // Adds all the elements to the right panel.

		// Add panels.
		frame.add(leftSide, BorderLayout.WEST);
		frame.add(rightSide, BorderLayout.EAST);

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

	void addInputButtonListener(ActionListener listenForSelectButton) {

		browseButton.addActionListener(listenForSelectButton);

	}

	void addLogListener(ActionListener listenForSelectButton) {

		log.addActionListener(listenForSelectButton);

	}

	void addConvertListener(ActionListener listenForSelectButton) {

		convertButton.addActionListener(listenForSelectButton);

	}

	void addAutoCorrectListener(ActionListener listenForSelectButton) {

		autoCorrection.addActionListener(listenForSelectButton);

	}

}
