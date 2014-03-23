package mvcV3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import print.printPDF;
import MVC.PrinterInterface;

import com.alee.extended.layout.VerticalFlowLayout;

public class View {

	protected static JFrame frame;

	// Panels to add to frame.
	protected static JPanel leftSide = new JPanel();
	protected static JPanel rightSide = new JPanel();
	protected static JPanel autoCorrectionPanel = new JPanel();

	protected static JScrollPane previewPane;

	// Buttons
	protected static JButton browseButton = new JButton("Browse");
	protected static JButton convertButton = new JButton("Convert");
	protected static JButton saveButton = new JButton("Save As...");
	protected static JButton applyButton = new JButton("A");
	protected static JButton settingsButton = new JButton("B");

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

	protected static Dimension frameSize = new Dimension(950, 650);

	protected static String previewImage = "C:/Users/Skyler/git/RuntimeDefenders3/Project/TabToPDF/outputfiles/musicIMG0.png";

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
		panel.setPreferredSize(new Dimension(270, 300));

		// Set border
		Border blackline = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
		TitledBorder titled = BorderFactory.createTitledBorder(blackline,
				"PDF Properties:");
		titled.setTitleJustification(TitledBorder.LEFT);
		panel.setBorder(titled);

		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.NONE;
		panel.add(applyButton, c);

		c.gridx = 1;
		c.gridy = 0;
		panel.add(settingsButton, c);

		// Song Title.
		JLabel songLabel = new JLabel("Title: ");
		songLabel.setFont(labelFont);
		title = new JTextField(20);
		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.anchor = c.BASELINE_TRAILING;
		// c.insets = new Insets(5, 5, 5, 5);

		panel.add(songLabel, c);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		// c.insets = new Insets(5, 5, 5, 5);

		panel.add(title, c);

		// Subtitle.
		JLabel subLabel = new JLabel("Subtitle: ");
		subLabel.setFont(labelFont);
		subtitle = new JTextField(20);
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.anchor = c.BASELINE_TRAILING;
		panel.add(subLabel, c);
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		// c.insets = new Insets(5, 5, 5, 5);

		panel.add(subtitle, c);

		// Staff spacing.
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
		c.gridwidth = 2;
		panel.add(spacingLabel, c);

		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 2;
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
		c.gridwidth = 2;
		panel.add(measureFontLabel, c);

		c.gridx = 0;
		c.gridy = 9;
		c.gridwidth = 2;
		panel.add(measureFontSize, c);

		applyButton.setEnabled(false);
		settingsButton.setEnabled(false);
		title.setEditable(false);
		subtitle.setEditable(false);
		staffSpacing.setEnabled(false);
		measureFontSize.setEnabled(false);
		panel.setEnabled(false);

		return panel;
	}

	protected static JPanel buttonPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(270, 225));
		GridBagConstraints c = new GridBagConstraints();
		panel.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 5, 5, 5);
		panel.add(browseButton, c);
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(5, 5, 5, 5);
		panel.add(convertButton, c);
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(5, 5, 5, 5);
		panel.add(saveButton, c);

		convertButton.setEnabled(false);
		saveButton.setEnabled(false);

		return panel;
	}

	protected static JPanel autoCorrections() {
		JPanel autoCorrectionPanel = new JPanel();
		autoCorrectionPanel.setPreferredSize(new Dimension(270, 100));
		autoCorrectionPanel.setMaximumSize(new Dimension(270, 100));
		autoCorrectionPanel.setMinimumSize(new Dimension(270, 100));
		autoCorrectionPanel.setVisible(true);
		autoCorrectionPanel.setEnabled(false);
		return autoCorrectionPanel;
	}

	protected static void populateLeftPanel() {
		GridBagConstraints c = new GridBagConstraints();
		leftSide.setLayout(new GridBagLayout());
		// c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		rightSide.setPreferredSize(new Dimension(300, 650));

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 5, 5, 5);
		leftSide.add(buttonPanel(), c);
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(5, 5, 5, 5);
		leftSide.add(pageProperties(), c);
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(5, 5, 5, 5);
		leftSide.add(autoCorrections(), c);

	}

	protected static JPanel buildPreviewScrollPane() {
		JPanel panel = new JPanel();

		Dimension scroll = new Dimension(615, 575);

		ImageIcon ic = new ImageIcon("");

		JTextPane topBox = new JTextPane();
		topBox.setBorder(null);
		topBox.setEditable(false);
		topBox.setBackground(new java.awt.Color(0, 0, 0, 0));
		JScrollPane imgScrollPane = new JScrollPane(topBox);
		imgScrollPane.setBackground(new java.awt.Color(0, 0, 0, 0));
		imgScrollPane.setPreferredSize(scroll);
		imgScrollPane.setMinimumSize(scroll);
		topBox.insertIcon(ic);
		// JLabel jl = new JLabel(ic);
		// JTextPane ta = new JTextPane();
		// ta.insertIcon(ic);
		// / previewPane.add(jl);
		panel.add(imgScrollPane);
		return panel;
	}

	/**
	 * 
	 */
	/**
	 * Adds all the components to the right panel.
	 * 
	 * 
	 */
	protected static void populateRightPanel() {

		GridBagConstraints c = new GridBagConstraints();
		rightSide.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.HORIZONTAL;
		rightSide.setPreferredSize(new Dimension(650, 660));

		// Initiates the input label and adds to the right side panel.
		input = new JTextField(1);
		JLabel previewLabel = new JLabel();
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(5, 5, 5, 5);
		rightSide.add(input, c);
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(1, 1, 1, 1);
		rightSide.add(previewLabel, c); // Label naming the display pane.

		JPanel pane = buildPreviewScrollPane();
		// Set border
		Border blackline = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
		TitledBorder titled = BorderFactory.createTitledBorder(blackline,
				"PDF Preview:");
		titled.setTitleJustification(TitledBorder.LEFT);
		pane.setBorder(titled);
		c.gridx = 0;
		c.gridy = 3;
		rightSide.add(pane, c); // Display a scrollPane of the
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

		GridBagConstraints c = new GridBagConstraints();
		frame.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.setJMenuBar(createMenuBar());

		populateLeftPanel(); // Adds all the elements to the left panel.
		populateRightPanel(); // Adds all the elements to the right panel.

		// Add panels.
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		frame.add(leftSide, c);
		c.gridx = 1;
		c.gridy = 0;
		frame.add(rightSide, c);

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

	void addSaveButtonListener(ActionListener listenForSelectButton) {

		saveButton.addActionListener(listenForSelectButton);

	}

	void addBrowseButtonListener(ActionListener listenForSelectButton) {

		browseButton.addActionListener(listenForSelectButton);

	}

	void addSettingsButtonListener(ActionListener listenForSelectButton) {

		settingsButton.addActionListener(listenForSelectButton);

	}

	void addConvertListener(ActionListener listenForSelectButton) {

		convertButton.addActionListener(listenForSelectButton);

	}

	void addApplyButtonListener(ActionListener listenForSelectButton) {

		applyButton.addActionListener(listenForSelectButton);

	}

}
