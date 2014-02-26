package MVC;

/* 
 * (READ UPDATE) Changed the path for the image file on line 238.
 * 
 * Executes "TextToPDF_v1_08_00.java" when the execute button
 * is pressed and creates the pdf file (line 187)
 * 
 * 
 * 
 * 
 * -Ron
 * 
 * 
 * CHANGE LOG:
 * 
 * v0.1: 
 * 			-	Broke up the GUI view, placed some in the model.
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionListener;

import print.printPDF;
import swing.PrinterInterface;

public class GUIView {

	final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	final static boolean RIGHT_TO_LEFT = false;

	static File fileToRead = new File("TextToPDF.INPUT_FILENAME");
	private final static String PREVIEW_LABEL = "Preview Image: ";
	private final static String SELECTION_LABEL = "Select preview: ";
	private final static String EDITING_LABEL = "Edit PDF: ";
	protected final static String NO_PREVIEW = "No files to view";
	static String[] fontsArray = { "ROMAN_BASELINE", "SANS_SERIF", "SERIF" };
	static String[] fontSizesArray = { "8", "10", "12" };
	static String[] spacingArray = { "1", "2", "3", "4", "5" };

	// static ArrayList<String> selectionFiles = new ArrayList<String>();
	// static ArrayList<String> selectionFiles = new ArrayList<String>();

	static JScrollPane imgScrollPane;
	static JList selectionList = new JList(new DefaultListModel());
	static java.awt.Color TRANSPARENT = new java.awt.Color(0, 0, 0, 0);
	static JTextArea log;
	static JTextPane topBox;
	static JButton selectButton = new JButton("Select Files to Convert");
	static JButton edit = new JButton("Edit Text file");
	static JPanel finalPanel;
	static JPanel topPanel;
	static JButton convertButton = new JButton("Convert Selected Files");
	static Font buttonFont = new Font("SANS_SERIF", Font.BOLD, 25);
	static JPanel listPanel;
	static JFrame frame;
	private static Font labelFont = new Font("SANS_SERIF", Font.BOLD, 12);
	static JMenuItem menuItem2 = new JMenuItem("User Manual");
	static JMenuItem menuItem3 = new JMenuItem("User Log");

	public static JMenuBar createMenuBar() {
		JMenuBar menuBar;
		JMenu menu;
		// Create the menu bar.
		menuBar = new JMenuBar();

		// Build the first menu.
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("The first menu");
		menuBar.add(menu);

		// Print function for "File" tab section.
		JMenuItem menuItem = new JMenuItem("Print");
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// logString += "Opening Printer Interface...\n";
				// updateLog();
				PrinterInterface printWindow = new PrinterInterface();
				printPDF test = new printPDF("outputfiles/musicPDF.pdf");
				printWindow.Scroller2(test);
			}

		});
		menu.add(menuItem);

		/*
		 * // Build second menu in the menu bar. menu = new JMenu("Edit");
		 * menu.setMnemonic(KeyEvent.VK_N);
		 * menu.getAccessibleContext().setAccessibleDescription(
		 * "Contain Elements pertaining to Edit"); menuBar.add(menu);
		 */

		// Build third menu in the menu bar.
		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription(
				"Contain elements to help the user");
		menuBar.add(menu);

		menu.add(menuItem2);
		menu.add(menuItem3);

		return menuBar;
	}

	/**
	 * Method that creates the buttons.
	 * 
	 * Select button now updates log to show just the file name, not the entire
	 * path.
	 * 
	 * @param panel
	 */
	public static void addButtons(Container panel) {

		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;

		// selectButton = new JButton("Select Files to Convert");
		// selectButton.addActionListener(addSelectButtonListener());
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(5, 0, 0, 5);
		c.ipady = 40;
		c.ipadx = 40;
		panel.add(selectButton, c);

		selectButton.setFont(buttonFont);

		// convertButton = new JButton("Convert Selected Files");

		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(5, 0, 0, 0);
		c.ipady = 40;
		c.ipadx = 40;

		panel.add(convertButton, c);

		convertButton.setFont(buttonFont);

		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(5, 0, 0, 0);
		c.ipady = 40;
		c.ipadx = 40;

		panel.add(edit, c);

		edit.setFont(buttonFont);

	}

	/**
	 * Populates the PDF editing pane.
	 * 
	 * @return
	 */
	/*
	 * public static void addEditingPane(Container editPanel) {
	 * 
	 * editPanel.setPreferredSize(new Dimension(600, 50));
	 * editPanel.setMinimumSize(new Dimension(600, 50));
	 * editPanel.setBackground(TRANSPARENT); editPanel.setLayout(new
	 * FlowLayout());
	 * 
	 * JPanel LabelsAndComboBoxes = new JPanel();
	 * LabelsAndComboBoxes.setLayout(new GridBagLayout()); GridBagConstraints c
	 * = new GridBagConstraints(); JLabel fontLabel = new JLabel("Font:");
	 * JLabel fontSizeLabel = new JLabel("Font size:"); JLabel spacingLabel =
	 * new JLabel("Spacing:");
	 * 
	 * JComboBox fontType = new JComboBox(fontsArray); JComboBox fontSize = new
	 * JComboBox(fontSizesArray); JComboBox spacing = new
	 * JComboBox(spacingArray);
	 * 
	 * c.gridx = 0; c.gridy = 0; LabelsAndComboBoxes.add(fontLabel); c.gridx =
	 * 1; c.gridy = 1; LabelsAndComboBoxes.add(fontType); c.gridx = 1; c.gridy =
	 * 1; LabelsAndComboBoxes.add(fontSizeLabel); c.gridx = 1; c.gridy = 1;
	 * LabelsAndComboBoxes.add(fontSize); c.gridx = 2; c.gridy = 2;
	 * LabelsAndComboBoxes.add(spacingLabel); c.gridx = 2; c.gridy = 2;
	 * LabelsAndComboBoxes.add(spacing);
	 * 
	 * editPanel.add(LabelsAndComboBoxes); }
	 */

	/**
	 * Method that creates the top panel.
	 * 
	 * Adds the 'new' top box layout.
	 * 
	 * @param panel
	 */
	@SuppressWarnings("unchecked")
	public static void addTopBox(Container panel) {

		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		// GUIController.populateJList(selectionList);
		// Display, and set listener for selection pane.
		/*
		 * JScrollPane pane = new JScrollPane(selectionList);
		 * pane.setPreferredSize(new Dimension(100, 250));
		 * selectionList.setLayout(new FlowLayout());
		 * selectionList.setVisibleRowCount(10);
		 * selectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 */

		// Create JLabels for everything.
		JLabel previewLabel = new JLabel(PREVIEW_LABEL);
		previewLabel.setFont(labelFont);
		JLabel selectionLabel = new JLabel(SELECTION_LABEL);
		selectionLabel.setFont(labelFont);
		JLabel editingLabel = new JLabel(EDITING_LABEL);

		// Displays the preview image in a TextPane.
		topBox = new JTextPane();
		topBox.setEditable(false);
		topBox.setBackground(TRANSPARENT);
		imgScrollPane = new JScrollPane(topBox);
		imgScrollPane.setBackground(TRANSPARENT);
		imgScrollPane.setPreferredSize(new Dimension(550, 250));
		imgScrollPane.setMinimumSize(new Dimension(550, 250));

		listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());
		// listPanel.add(selectionLabel, BorderLayout.NORTH);
		// listPanel.add(pane, BorderLayout.SOUTH);

		JPanel previewPanel = new JPanel();
		previewPanel.setLayout(new BorderLayout());
		previewPanel.add(previewLabel, BorderLayout.NORTH);
		previewPanel.add(imgScrollPane, BorderLayout.SOUTH);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(listPanel);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 1;
		c.gridy = 0;
		panel.add(previewPanel);

		GUIController.updateTopBox();
	}

	/**
	 * Method that creates the log box.
	 * 
	 * @param panel
	 */
	/*
	 * public static void addLogBox(Container panel) {
	 * 
	 * panel.setLayout(new GridBagLayout()); GridBagConstraints c = new
	 * GridBagConstraints();
	 * 
	 * JLabel logLabel = new JLabel("Log:"); logLabel.setFont(labelFont);
	 * c.gridx = 0; c.gridy = 1; panel.add(logLabel, c);
	 * 
	 * log = new JTextArea(10, 30); log.setEditable(false);
	 * log.setLineWrap(true); log.setBackground(TRANSPARENT);
	 * 
	 * JScrollPane scrollPane = new JScrollPane(log); scrollPane
	 * .setVerticalScrollBarPolicy
	 * (ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	 * scrollPane.setBackground(TRANSPARENT); c.fill =
	 * GridBagConstraints.VERTICAL; c.gridx = 1; c.gridy = 2;
	 * panel.add(scrollPane, c);
	 * 
	 * }
	 */

	private static void setLookAndFeel() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

			// WebLookAndFeel.class.getCanonicalName ()
			// "com.seaglasslookandfeel.SeaGlassLookAndFeel"
			// "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"
		} catch (Exception exc) {

		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {

		setLookAndFeel();

		// Create and set up the window.
		frame = new JFrame("Pretty ASCII PDF");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		// Set up the content pane.
		finalPanel = new JPanel(new BorderLayout());

		// Setup sub panels
		JPanel editingPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JPanel logPanel = new JPanel();
		JPanel logAndButton = new JPanel();
		logAndButton.setLayout(new FlowLayout());
		topPanel = new JPanel();
		buttonPanel.setLocation(0, frame.getHeight());

		addButtons(buttonPanel);
		// addLogBox(logPanel);
		addTopBox(topPanel);
		// addEditingPane(editingPanel);

		logAndButton.add(buttonPanel);
		logAndButton.add(logPanel);

		finalPanel.add(topPanel, BorderLayout.PAGE_START);
		finalPanel.add(editingPanel, BorderLayout.LINE_END);
		finalPanel.add(logAndButton, BorderLayout.PAGE_END);
		frame.setJMenuBar(createMenuBar());
		frame.add(finalPanel);
		// Display the window.
		frame.pack();
		frame.setVisible(true);
		new ReadAndDisplayUserManual();

	}

	public static void main(String args[]) {

		// createAndShowGUI();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});

	}

	void addListSelectionListener(ListSelectionListener ListSelectionListener) {

		selectionList.addListSelectionListener(ListSelectionListener);
	}

	void addSelectButtonListener(ActionListener listenForSelectButton) {

		selectButton.addActionListener(listenForSelectButton);

	}

	void addConvertButtonListener(ActionListener listenForSelectButton) {

		convertButton.addActionListener(listenForSelectButton);

	}

	void addEditButtonListener(ActionListener listenForSelectButton) {

		edit.addActionListener(listenForSelectButton);

	}

	void addMenuItemListener(ActionListener listenForSelectButton) {

		menuItem2.addActionListener(listenForSelectButton);

	}

	void addMenuItem3Listener(ActionListener listenForSelectButton) {

		menuItem3.addActionListener(listenForSelectButton);

	}

}
