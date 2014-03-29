package mvcV4;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

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
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


import print.printPDF;
import tabparts.AutofixLog;
import MVC.PrinterInterface;

public class View
{

	/* CONSTANTS */
	
	public static final int SCROLL_WIDTH = 625;
	public static final int SCROLL_HEIGHT = 550;
	
	public static final int PAGEPROP_WIDTH = 325;
	public static final int PAGEPROP_HEIGHT = 325;
	
	public static final int BUTTON_WIDTH = 270;
	public static final int BUTTON_HEIGHT = 200;
	
	public static final int AUTOCORR_WIDTH = 270;
	public static final int AUTOCORR_HEIGHT = 75;
	
	public static final int LEFTPANEL_WIDTH = 400;
	public static final int LEFTPANEL_HEIGHT = 500;
	
	public static final int RIGHTPANEL_WIDTH = 650;
	public static final int RIGHTPANEL_HEIGHT = 500;
	
	
	/* ATTRIBUTES */
	
	protected static JFrame frame;

	// Panels to add to frame.
	protected static JPanel leftSide = new JPanel();
	protected static JPanel rightSide = new JPanel();
	protected static JPanel autoCorrectionPanel = new JPanel();

	protected static JScrollPane previewPane;

	// ImageIcons
	protected static ImageIcon SelectButtonIcon = CreateImageIcon("/gui_images/SelectButtonDefault.png");
	protected static ImageIcon SelectButtonPressedIcon = CreateImageIcon("/gui_images/SelectButtonPressed.png");
	protected static ImageIcon SelectButtonDisabledIcon = CreateImageIcon("/gui_images/SelectButtonDisabled.png");
	protected static ImageIcon ConvertButtonIcon = CreateImageIcon("/gui_images/ConvertButtonDefault.png");
	protected static ImageIcon ConvertButtonPressedIcon = CreateImageIcon("/gui_images/ConvertButtonPressed.png");
	protected static ImageIcon ConvertButtonDisabledIcon = CreateImageIcon("/gui_images/ConvertButtonDisabled.png");
	protected static ImageIcon SaveButtonIcon = CreateImageIcon("/gui_images/SaveButtonDefault.png");
	protected static ImageIcon SaveButtonPressedIcon = CreateImageIcon("/gui_images/SaveButtonPressed.png");
	protected static ImageIcon SaveButtonDisabledIcon = CreateImageIcon("/gui_images/SaveButtonDisabled.png");
	protected static ImageIcon CorrectionButtonIcon = CreateImageIcon("/gui_images/CorrectionButtonDefault.png");
	protected static ImageIcon CorrectionButtonPressedIcon = CreateImageIcon("/gui_images/CorrectionButtonPressed.png");
	protected static ImageIcon CorrectionButtonDisabledIcon = CreateImageIcon("/gui_images/CorrectionButtonDisabled.png");

	// Buttons
	protected static JButton selectButton = CreateButton(SelectButtonIcon,
			SelectButtonPressedIcon, SelectButtonDisabledIcon);
	protected static JButton convertButton = CreateButton(ConvertButtonIcon,
			ConvertButtonPressedIcon, ConvertButtonDisabledIcon);
	protected static JButton saveButton = CreateButton(SaveButtonIcon,
			SaveButtonPressedIcon, SaveButtonDisabledIcon);
	protected static JButton correctionButton = CreateButton(
			CorrectionButtonIcon, CorrectionButtonPressedIcon,
			CorrectionButtonDisabledIcon);

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
	protected static JSlider elementSize;
	private static final int elementSizeMin = 1;
	private static final int elementSizeMax = 41;
	private static int measureSizeCurrent = 1;

	// SPACING SLIDER INFO
	protected static JSlider staffSpacing;
	private static final int staffSpacingMin = 1;
	private static final int staffSpacingMax = 21;
	private static int staffSpacingCurrent = 1;

	// Font
	private static Font labelFont = new Font("SANS_SERIF", Font.BOLD, 12);

	static Dimension scroll = new Dimension(SCROLL_WIDTH, SCROLL_HEIGHT);

	protected static String previewImage = "C:/Users/Skyler/git/RuntimeDefenders3/Project/TabToPDF/outputfiles/musicIMG0.png";

	protected static ImageIcon ic;
	protected static JLabel iconLabel;
	protected static JTextPane topBox;
	protected static JScrollPane imgScrollPane;
	protected static JLabel correctionLabel;

	public View()
	{
		CreateAndShowGUI();
	}

	protected static void repaintPreview(String image)
	{

		ic = new ImageIcon(image);
		ic.getImage().flush();
		iconLabel.setIcon(ic);
	}

	// NOTE! Might want to combine this with repaintPreview into updateView
	protected static void updateCorrection(String filename)
	{
		if (AutofixLog.isEmpty())
		{
			correctionLabel.setVisible(false);
			correctionButton.setVisible(false);
		} else
		{
			correctionLabel.setText("Errors were found in " + filename);
			correctionLabel.setVisible(true);
			correctionButton.setVisible(true);
		}
	}

	public static JMenuBar createMenuBar()
	{
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
		menu2.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
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

	public static JPanel titles()
	{

		JPanel panel = new JPanel();
		GridBagConstraints c = new GridBagConstraints();
		panel.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.HORIZONTAL;

		return panel;
	}

	private static JPanel pageProperties()
	{
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setPreferredSize(new Dimension(PAGEPROP_WIDTH, PAGEPROP_HEIGHT));

		// Set border
		Border blackline = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
		TitledBorder titled = BorderFactory.createTitledBorder(blackline,
				"PDF Properties:");
		titled.setTitleJustification(TitledBorder.LEFT);
		panel.setBorder(titled);

		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		// Song Title.
		JLabel songLabel = new JLabel("Title: ");
		songLabel.setFont(labelFont);

		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.anchor = c.BASELINE_TRAILING;
		// c.insets = new Insets(5, 5, 5, 5);

		panel.add(songLabel, c);

		title = new JTextField(20);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		// c.insets = new Insets(5, 5, 5, 5);
		panel.add(title, c);

		// Subtitle.
		JLabel subLabel = new JLabel("Subtitle: ");
		subLabel.setFont(labelFont);
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.anchor = c.BASELINE_TRAILING;
		panel.add(subLabel, c);

		subtitle = new JTextField(20);
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
		staffSpacing.setMajorTickSpacing(2);
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
		elementSize = new JSlider(JSlider.HORIZONTAL, elementSizeMin,
				elementSizeMax, measureSizeCurrent);
		elementSize.setMajorTickSpacing(5);
		elementSize.setMinorTickSpacing(1);
		elementSize.setPaintTicks(true);
		elementSize.setPaintLabels(true);
		c.gridx = 0;
		c.gridy = 8;
		c.gridwidth = 2;
		panel.add(measureFontLabel, c);

		c.gridx = 0;
		c.gridy = 9;
		c.gridwidth = 2;
		panel.add(elementSize, c);

		title.setEnabled(false);
		subtitle.setEnabled(false);
		staffSpacing.setEnabled(false);
		elementSize.setEnabled(false);
		// panel.setEnabled(false);

		return panel;
	}

	protected static JPanel buttonPanel()
	{
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		GridBagConstraints c = new GridBagConstraints();
		panel.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 5, 5, 5);
		panel.add(selectButton, c);
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

	protected static JPanel autoCorrections()
	{
		JPanel autoCorrectionPanel = new JPanel();
		autoCorrectionPanel.setOpaque(false);
		autoCorrectionPanel.setPreferredSize(new Dimension(AUTOCORR_WIDTH, AUTOCORR_HEIGHT));
		autoCorrectionPanel.setMaximumSize(new Dimension(AUTOCORR_WIDTH, AUTOCORR_HEIGHT));
		autoCorrectionPanel.setMinimumSize(new Dimension(AUTOCORR_WIDTH, AUTOCORR_HEIGHT));
		autoCorrectionPanel.setVisible(true);
		autoCorrectionPanel.setEnabled(false);

		// Setting up GridbagLayout
		GridBagConstraints c = new GridBagConstraints();
		autoCorrectionPanel.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.NONE;

		// Adding label and button
		correctionLabel = new JLabel("");
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 10, 5, 5);
		autoCorrectionPanel.add(correctionLabel, c);

		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(5, 10, 5, 5);
		autoCorrectionPanel.add(correctionButton, c);

		// hiding button and label
		correctionLabel.setVisible(false);
		correctionButton.setVisible(false);

		return autoCorrectionPanel;
	}

	protected static void populateLeftPanel()
	{
		GridBagConstraints c = new GridBagConstraints();
		leftSide.setLayout(new GridBagLayout());
		// c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		rightSide.setPreferredSize(new Dimension(LEFTPANEL_WIDTH, LEFTPANEL_HEIGHT));

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 10, 5, 5);
		leftSide.add(buttonPanel(), c);
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(5, 10, 5, 5);
		leftSide.add(pageProperties(), c);
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(5, 10, 5, 5);
		leftSide.add(autoCorrections(), c);
	}

	protected static void buildPreviewScrollPane()
	{
		previewPane = new JScrollPane();
		previewPane.setPreferredSize(scroll);
		previewPane.setMinimumSize(scroll);
		iconLabel = new JLabel();
		previewPane.setViewportView(iconLabel);
	}

	/**
	 * Adds all the components to the right panel.
	 * 
	 * 
	 */
	protected static void populateRightPanel()
	{
		GridBagConstraints c = new GridBagConstraints();
		rightSide.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.HORIZONTAL;
		rightSide.setPreferredSize(new Dimension(RIGHTPANEL_WIDTH, RIGHTPANEL_HEIGHT));

		// Initiates the input label and adds to the right side panel.
		input = new JTextField(1);
		JLabel previewLabel = new JLabel();
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(5, 5, 5, 10);
		rightSide.add(input, c);
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(1, 1, 1, 10);
		rightSide.add(previewLabel, c); // Label naming the display pane.

		buildPreviewScrollPane();
		// Set border
		Border blackline = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
		TitledBorder titled = BorderFactory.createTitledBorder(blackline,
				"PDF Preview:");
		titled.setTitleJustification(TitledBorder.LEFT);
		previewPane.setBorder(titled);
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(1, 1, 1, 10);

		rightSide.add(previewPane, c); // Display a scrollPane of the
		// image.
	}

	/**
	 * Sets the look and feel of the Java application.
	 */
	private static void setLookAndFeel()
	{
		try
		{
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception exc)
		{

		}
	}

	/**
	 * Creates and shows the GUI
	 */
	public static void CreateAndShowGUI()
	{
		setLookAndFeel();
		frame = new JFrame("Convert Tab to PDF");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
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

	/** Returns a JButton with the given default, pressed, and disabled icons */
	public static JButton CreateButton(ImageIcon defaultIcon,
			ImageIcon pressedIcon, ImageIcon disabledIcon)
	{
		JButton button = new JButton(defaultIcon);
		button.setPressedIcon(pressedIcon);
		button.setDisabledIcon(disabledIcon);
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setContentAreaFilled(false);
		return button;
	}

	/** Returns an ImageIcon if the path is valid, otherwise null. */
	protected static ImageIcon CreateImageIcon(String path)
	{
		java.net.URL imgURL = View.class.getResource(path);
		if (imgURL != null)
		{
			return new ImageIcon(imgURL);
		} else
		{
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				CreateAndShowGUI();
			}
		});
	}

	void addSaveButtonListener(ActionListener listenForSelectButton)
	{

		saveButton.addActionListener(listenForSelectButton);

	}

	void addSelectButtonListener(ActionListener listenForSelectButton)
	{

		selectButton.addActionListener(listenForSelectButton);

	}

	void addConvertButtonListener(ActionListener listenForSelectButton)
	{

		convertButton.addActionListener(listenForSelectButton);

	}

	void addCorrectionButtonListener(ActionListener listenForSelectButton)
	{

		correctionButton.addActionListener(listenForSelectButton);

	}

	void spacingListener(ChangeListener spacingListener)
	{

		staffSpacing.addChangeListener(spacingListener);

	}
	
	void elementSizeListener(ChangeListener elementSizeListener)
	{

		elementSize.addChangeListener(elementSizeListener);

	}

	void titleListener(ActionListener titleListener)
	{
		title.addActionListener(titleListener);
	}

	void titleFocusListener(FocusListener titleFocusListener)
	{
		title.addFocusListener(titleFocusListener);
	}
		
	void subtitleListener(ActionListener subtitleListener)
	{
		subtitle.addActionListener(subtitleListener);
	}
	
	void subtitleFocusListener(FocusListener subtitleFocusListener)
	{
		subtitle.addFocusListener(subtitleFocusListener);
	}

}