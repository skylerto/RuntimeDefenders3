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
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.itextpdf.text.Rectangle;

import print.printPDF;
import tabparts.AutofixLog;

public class View
{

	/* CONSTANTS */

	public static final int PREVIEW_SCROLL_WIDTH = 640;
	public static final int PREVIEW_SCROLL_HEIGHT = 550;

	public static final int PROPERTIES_SCROLL_WIDTH = 330;
	public static final int PROPERTIES_SCROLL_HEIGHT = 325;

	public static final int PAGEPROP_WIDTH = 300;
	public static final int PAGEPROP_HEIGHT = 450;

	public static final int CORRLOG_WIDTH = 760;
	public static final int CORRLOG_HEIGHT = 500;

	public static final int BUTTON_WIDTH = 270;
	public static final int BUTTON_HEIGHT = 200;

	public static final int AUTOCORR_WIDTH = 270;
	public static final int AUTOCORR_HEIGHT = 75;

	public static final int LEFTPANEL_WIDTH = 400;
	public static final int LEFTPANEL_HEIGHT = 500;

	public static final int RIGHTPANEL_WIDTH = 650;
	public static final int RIGHTPANEL_HEIGHT = 500;

	public static final String LETTER = "Letter";
	public static final String LEGAL = "Legal";
	public static final String LEDGER = "Ledger";

	/* ATTRIBUTES */

	protected static JFrame frame;

	// Panels to add to frame.
	protected static JPanel leftSide = new JPanel();
	protected static JPanel rightSide = new JPanel();
	protected static JPanel autoCorrectionPanel = new JPanel();

	protected static JScrollPane previewPane;
	protected static JScrollPane propertiesPane;

	// ImageIcons
	protected static ImageIcon SelectButtonIcon = CreateImageIcon("/gui_images/SelectButtonDefault.png");
	protected static ImageIcon SelectButtonPressedIcon = CreateImageIcon("/gui_images/SelectButtonPressed.png");
	protected static ImageIcon SelectButtonDisabledIcon = CreateImageIcon("/gui_images/SelectButtonDisabled.png");
	protected static ImageIcon SaveButtonIcon = CreateImageIcon("/gui_images/SaveButtonDefault.png");
	protected static ImageIcon SaveButtonPressedIcon = CreateImageIcon("/gui_images/SaveButtonPressed.png");
	protected static ImageIcon SaveButtonDisabledIcon = CreateImageIcon("/gui_images/SaveButtonDisabled.png");
	protected static ImageIcon CorrectionButtonIcon = CreateImageIcon("/gui_images/CorrectionButtonDefault.png");
	protected static ImageIcon CorrectionButtonPressedIcon = CreateImageIcon("/gui_images/CorrectionButtonPressed.png");
	protected static ImageIcon CorrectionButtonDisabledIcon = CreateImageIcon("/gui_images/CorrectionButtonDisabled.png");
	protected static ImageIcon ErrorIcon = CreateImageIcon("/gui_images/ErrorImage.png");

	// Buttons
	protected static JButton selectButton = CreateButton(SelectButtonIcon,
			SelectButtonPressedIcon, SelectButtonDisabledIcon);
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
	private static final int elementSizeMax = 21;
	private static int elementSizeCurrent = 1;

	// SPACING SLIDER INFO
	protected static JSlider staffSpacing;
	private static final int staffSpacingMin = 1;
	private static final int staffSpacingMax = 21;
	private static int staffSpacingCurrent = 1;

	// MEASURE SLIDER INFO
	protected static JSlider measureSpace;
	private static final int measureSizeMin = 50;
	private static final int measureSizeMax = 200;
	private static int measureSizeCurrent = 50;

	// TITLE FONT SIZE SLIDER INFO
	protected static JSlider titleFontSize;
	private static final int titleFontSizeMin = 1;
	private static final int titleFontSizeMax = 26;
	private static int titleFontSizeCurrent = 1;

	// SUBTITLE FONT SIZE SLIDER INFO
	protected static JSlider subtitleFontSize;
	private static final int subtitleFontSizeMin = 1;
	private static final int subtitleFontSizeMax = 26;
	private static int subtitleFontSizeCurrent = 1;

	// LEFT MARGIN SPACE SLIDER INFO
	protected static JSlider leftMarginSpace;
	private static final int leftMarginSpaceMin = 10;
	private static final int leftMarginSpaceMax = 210;
	private static int leftMarginSpaceCurrent = 10;

	// SUBTITLE FONT SIZE SLIDER INFO
	protected static JSlider rightMarginSpace;
	private static final int rightMarginSpaceMin = 10;
	private static final int rightMarginSpaceMax = 210;
	private static int rightMarginSpaceCurrent = 10;

	// JComboBox
	protected static JComboBox<String> pageList;

	// Auto Correction Panel
	protected static JLabel correctionLabel;
	protected static JDialog correctionLogDialog;
	protected static JTextArea correctionLogText;
	protected static JScrollPane correctionLogScroller;

	// Font
	private static Font labelFont = new Font("SANS_SERIF", Font.BOLD, 12);

	static Dimension previewScroll = new Dimension(PREVIEW_SCROLL_WIDTH,
			PREVIEW_SCROLL_HEIGHT);
	static Dimension propertiesScroll = new Dimension(PROPERTIES_SCROLL_WIDTH,
			PROPERTIES_SCROLL_HEIGHT);

	protected static String previewImage = "C:/Users/Skyler/git/RuntimeDefenders3/Project/TabToPDF/outputfiles/musicIMG0.png";

	protected static JLabel iconLabel;
	protected static JTextPane topBox;
	protected static JScrollPane imgScrollPane;

	public View()
	{
		CreateAndShowGUI();
	}

	protected static void repaintPreview(String image)
	{
		ImageIcon iconImage = new ImageIcon(image);
		iconImage.getImage().flush();
		iconLabel.setIcon(iconImage);
		iconLabel.setText("");
		previewPane.getVerticalScrollBar().setValue(0);
		previewPane.getHorizontalScrollBar().setValue(0);
	}

	protected static void showError(String message)
	{
		iconLabel.setText("<html>" + message + "<html>");
		iconLabel.setIcon(ErrorIcon);
		iconLabel.setPreferredSize(new Dimension(PROPERTIES_SCROLL_WIDTH,
				PROPERTIES_SCROLL_HEIGHT));
	}

	protected static void setComponentsEnabled(boolean value)
	{
		saveButton.setEnabled(value);
		title.setEnabled(value);
		subtitle.setEnabled(value);
		staffSpacing.setEnabled(value);
		elementSize.setEnabled(value);
		measureSpace.setEnabled(value);
		titleFontSize.setEnabled(value);
		subtitleFontSize.setEnabled(value);
		leftMarginSpace.setEnabled(value);
		rightMarginSpace.setEnabled(value);
		pageList.setEnabled(value);
	}

	protected static void resetView()
	{
		title.setText("");
		subtitle.setText("");
		pageList.setSelectedIndex(0);
		propertiesPane.getVerticalScrollBar().setValue(0);
	}

	protected static void repaintPreview(String image, Rectangle pageSize)
	{
		ImageIcon iconImage = new ImageIcon(image);
		iconImage.getImage().flush();
		iconLabel.setText("");
		iconLabel.setIcon(iconImage);
		iconLabel.setPreferredSize(new Dimension((int) pageSize.getWidth(),
				(int) pageSize.getHeight()));
		iconLabel.revalidate();
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

			// writes Autofix Log
			String correctionLogPath = AutofixLog.LOG_PATH;
			correctionLogText.setText(Utils.openAndReadFile(correctionLogPath));
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

	protected static void buildCorrectionLogDialog()
	{
		correctionLogDialog = new JDialog(frame, "Auto Correction Log");
		correctionLogDialog.setPreferredSize(new Dimension(CORRLOG_WIDTH,
				CORRLOG_HEIGHT));
		correctionLogText = new JTextArea();
		correctionLogScroller = new JScrollPane(correctionLogText);
		correctionLogText.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		correctionLogText.setEditable(false);
		correctionLogDialog.add(correctionLogScroller);
		correctionLogDialog.pack();
	}

	private static void setSliderTicks(JSlider slider, int majorTickSpace,
			int minorTickSpace)
	{
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(majorTickSpace);
		slider.setMinorTickSpacing(minorTickSpace);
	}

	private static JPanel pageProperties()
	{
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setPreferredSize(new Dimension(PAGEPROP_WIDTH, PAGEPROP_HEIGHT));

		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		// Song Title.
		JLabel songLabel = new JLabel("Title: ");
		songLabel.setFont(labelFont);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		c.insets = new Insets(10, 5, 5, 0);
		panel.add(songLabel, c);

		title = new JTextField(20);
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		c.insets = new Insets(10, 5, 5, 5);
		panel.add(title, c);

		// Subtitle.
		JLabel subLabel = new JLabel("Subtitle: ");
		subLabel.setFont(labelFont);
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		c.insets = new Insets(0, 5, 10, 0);
		panel.add(subLabel, c);

		subtitle = new JTextField(20);
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		c.insets = new Insets(0, 5, 10, 5);
		panel.add(subtitle, c);

		// Staff spacing.
		JLabel spacingLabel = new JLabel("Staff Space: ");
		spacingLabel.setFont(labelFont);
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0;
		c.anchor = GridBagConstraints.BASELINE_LEADING;
		c.insets = new Insets(2, 5, 5, 0);
		panel.add(spacingLabel, c);

		staffSpacing = new JSlider(JSlider.HORIZONTAL, staffSpacingMin,
				staffSpacingMax, staffSpacingCurrent);
		setSliderTicks(staffSpacing, 5, 1);

		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0, 8, 5, 8);
		panel.add(staffSpacing, c);

		// Measure Element size.
		JLabel measureElementLabel = new JLabel("Staff Size: ");
		measureElementLabel.setFont(labelFont);
		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 0;
		c.anchor = GridBagConstraints.BASELINE_LEADING;
		c.insets = new Insets(2, 5, 5, 0);
		panel.add(measureElementLabel, c);

		elementSize = new JSlider(JSlider.HORIZONTAL, elementSizeMin,
				elementSizeMax, elementSizeCurrent);
		setSliderTicks(elementSize, 5, 1);
		c.gridx = 1;
		c.gridy = 3;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0, 8, 5, 8);
		panel.add(elementSize, c);

		// Measure Spacing
		JLabel measureSpaceLabel = new JLabel("Measure Space: ");
		measureSpaceLabel.setFont(labelFont);
		c.gridx = 0;
		c.gridy = 4;
		c.weightx = 0;
		c.anchor = GridBagConstraints.BASELINE_LEADING;
		c.insets = new Insets(2, 5, 5, 0);
		panel.add(measureSpaceLabel, c);

		measureSpace = new JSlider(JSlider.HORIZONTAL, measureSizeMin,
				measureSizeMax, measureSizeCurrent);
		setSliderTicks(measureSpace, 50, 10);
		c.gridx = 1;
		c.gridy = 4;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0, 8, 5, 8);
		panel.add(measureSpace, c);

		// Title Font Size
		JLabel titleFontSizeLabel = new JLabel("Title Font Size: ");
		titleFontSizeLabel.setFont(labelFont);
		c.gridx = 0;
		c.gridy = 5;
		c.weightx = 0;
		c.anchor = GridBagConstraints.BASELINE_LEADING;
		c.insets = new Insets(2, 5, 5, 0);
		panel.add(titleFontSizeLabel, c);

		titleFontSize = new JSlider(JSlider.HORIZONTAL, titleFontSizeMin,
				titleFontSizeMax, titleFontSizeCurrent);
		setSliderTicks(titleFontSize, 5, 1);
		c.gridx = 1;
		c.gridy = 5;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0, 8, 5, 8);
		panel.add(titleFontSize, c);

		// Subtitle Font Size
		JLabel subtitleFontSizeLabel = new JLabel("Subtitle Font Size: ");
		subtitleFontSizeLabel.setFont(labelFont);
		c.gridx = 0;
		c.gridy = 6;
		c.weightx = 0;
		c.anchor = GridBagConstraints.BASELINE_LEADING;
		c.insets = new Insets(2, 5, 5, 0);
		panel.add(subtitleFontSizeLabel, c);

		subtitleFontSize = new JSlider(JSlider.HORIZONTAL, subtitleFontSizeMin,
				subtitleFontSizeMax, subtitleFontSizeCurrent);
		setSliderTicks(subtitleFontSize, 5, 1);
		c.gridx = 1;
		c.gridy = 6;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0, 8, 5, 8);
		panel.add(subtitleFontSize, c);

		// Left Margin Space
		JLabel leftMarginLabel = new JLabel("Left Margin: ");
		leftMarginLabel.setFont(labelFont);
		c.gridx = 0;
		c.gridy = 7;
		c.weightx = 0;
		c.anchor = GridBagConstraints.BASELINE_LEADING;
		c.insets = new Insets(2, 5, 5, 0);
		panel.add(leftMarginLabel, c);

		leftMarginSpace = new JSlider(JSlider.HORIZONTAL, leftMarginSpaceMin,
				leftMarginSpaceMax, leftMarginSpaceCurrent);
		setSliderTicks(leftMarginSpace, 50, 10);
		c.gridx = 1;
		c.gridy = 7;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0, 8, 5, 8);
		panel.add(leftMarginSpace, c);

		// Right Margin Space
		JLabel rightMarginLabel = new JLabel("Right Margin: ");
		rightMarginLabel.setFont(labelFont);
		c.gridx = 0;
		c.gridy = 8;
		c.weightx = 0;
		c.anchor = GridBagConstraints.BASELINE_LEADING;
		c.insets = new Insets(2, 5, 5, 0);
		panel.add(rightMarginLabel, c);

		rightMarginSpace = new JSlider(JSlider.HORIZONTAL, rightMarginSpaceMin,
				rightMarginSpaceMax, rightMarginSpaceCurrent);
		setSliderTicks(rightMarginSpace, 50, 10);
		c.gridx = 1;
		c.gridy = 8;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0, 8, 5, 8);
		panel.add(rightMarginSpace, c);

		JLabel pageSizeLabel = new JLabel("Page Size: ");
		pageSizeLabel.setFont(labelFont);
		c.gridx = 0;
		c.gridy = 9;
		c.weightx = 0;
		c.insets = new Insets(0, 5, 10, 0);
		panel.add(pageSizeLabel, c);

		String[] pageSizes =
		{ LETTER, LEGAL, LEDGER };
		pageList = new JComboBox<String>(pageSizes);
		pageList.setSelectedIndex(0);
		c.gridx = 1;
		c.gridy = 9;
		c.weightx = 1;
		c.insets = new Insets(0, 5, 10, 5);
		panel.add(pageList, c);

		setComponentsEnabled(false);
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
		panel.add(saveButton, c);

		saveButton.setEnabled(false);

		return panel;
	}

	protected static JPanel autoCorrections()
	{
		JPanel autoCorrectionPanel = new JPanel();
		autoCorrectionPanel.setOpaque(false);
		autoCorrectionPanel.setPreferredSize(new Dimension(AUTOCORR_WIDTH,
				AUTOCORR_HEIGHT));
		autoCorrectionPanel.setMaximumSize(new Dimension(AUTOCORR_WIDTH,
				AUTOCORR_HEIGHT));
		autoCorrectionPanel.setMinimumSize(new Dimension(AUTOCORR_WIDTH,
				AUTOCORR_HEIGHT));
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
		rightSide.setPreferredSize(new Dimension(LEFTPANEL_WIDTH,
				LEFTPANEL_HEIGHT));

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 10, 5, 5);
		leftSide.add(buttonPanel(), c);

		buildPropertiesScrollPane();
		Border blackline = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
		TitledBorder titled = BorderFactory.createTitledBorder(blackline,
				"PDF Properties:");
		titled.setTitleJustification(TitledBorder.LEFT);
		propertiesPane.setBorder(titled);
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(5, 10, 5, 5);
		leftSide.add(propertiesPane, c);

		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(5, 10, 5, 5);
		leftSide.add(autoCorrections(), c);
	}

	protected static void buildPreviewScrollPane()
	{
		previewPane = new JScrollPane();
		previewPane.setPreferredSize(previewScroll);
		previewPane.setMinimumSize(previewScroll);
		previewPane.getViewport().setOpaque(false);
		Border border = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		previewPane.setViewportBorder(border);
		previewPane.setBorder(border);
		iconLabel = new JLabel();
		previewPane.setViewportView(iconLabel);
	}

	protected static void buildPropertiesScrollPane()
	{
		propertiesPane = new JScrollPane(pageProperties());
		propertiesPane.setPreferredSize(propertiesScroll);
		propertiesPane.setMinimumSize(propertiesScroll);
		propertiesPane.getViewport().setOpaque(false);
		Border border = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		propertiesPane.setViewportBorder(border);
		propertiesPane.setBorder(border);
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
		rightSide.setPreferredSize(new Dimension(RIGHTPANEL_WIDTH,
				RIGHTPANEL_HEIGHT));

		// Initiates the input label and adds to the right side panel.
		input = new JTextField(1);
		input.setEditable(false);
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
		frame.setResizable(false);

		GridBagConstraints c = new GridBagConstraints();
		frame.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.setJMenuBar(createMenuBar());

		populateLeftPanel(); // Adds all the elements to the left panel.
		populateRightPanel(); // Adds all the elements to the right panel.
		buildCorrectionLogDialog();

		// Add panels.
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		frame.add(leftSide, c);
		c.gridx = 1;
		c.gridy = 0;
		frame.add(rightSide, c);
		frame.pack();
		frame.setLocationByPlatform(true);
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

	void addCorrectionButtonListener(ActionListener listenForSelectButton)
	{
		correctionButton.addActionListener(listenForSelectButton);
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

	void measureSpaceListener(ChangeListener measureSpaceListener)
	{
		measureSpace.addChangeListener(measureSpaceListener);
	}

	void titleFontSizeListener(ChangeListener titleFontSizeListener)
	{
		titleFontSize.addChangeListener(titleFontSizeListener);
	}

	void subtitleFontSizeListener(ChangeListener subtitleFontSizeListener)
	{
		subtitleFontSize.addChangeListener(subtitleFontSizeListener);
	}

	void leftMarginListener(ChangeListener leftMarginListener)
	{
		leftMarginSpace.addChangeListener(leftMarginListener);
	}

	void rightMarginListener(ChangeListener rightMarginListener)
	{
		rightMarginSpace.addChangeListener(rightMarginListener);
	}

	void spacingListener(ChangeListener spacingListener)
	{

		staffSpacing.addChangeListener(spacingListener);
	}

	void elementSizeListener(ChangeListener elementSizeListener)
	{
		elementSize.addChangeListener(elementSizeListener);
	}

	void pageSizeListener(ActionListener pageSizeListener)
	{
		pageList.addActionListener(pageSizeListener);
	}
}