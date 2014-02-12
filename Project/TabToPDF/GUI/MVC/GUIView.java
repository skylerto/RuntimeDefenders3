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
 * v0.2: 
 * 		-	added preview image update method. 
 * v0.3: 
 * 		-	Created a method to update the image.
 * 		-	Changed some variables to be static.
 * 
 * v0.4:
 * 		-	Changed display size from x to y.
 * 		-	
 * 		-	
 */

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
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
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import ttp.TextToPDF;

import com.itextpdf.text.DocumentException;

import creator.IMGCreator;

public class GUIView {

	final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	final static boolean RIGHT_TO_LEFT = false;

	private static File fileToRead;
	private final static String PREVIEW_LABEL = "Preview Image: ";
	private final static String SELECTION_LABEL = "Select preview: ";
	private final static String EDITING_LABEL = "Edit PDF: ";
	protected final static String NO_PREVIEW = "No files to view";
	private static String[] fontsArray = { "ROMAN_BASELINE", "SANS_SERIF",
			"SERIF" };
	private static String[] fontSizesArray = { "8", "10", "12" };
	private static String[] spacingArray = { "1", "2", "3", "4", "5" };
	protected static ArrayList<String> selectionFiles = new ArrayList<String>();
	protected static ArrayList<String> selectionImages = new ArrayList<String>();



	private static JScrollPane imgScrollPane;
	private static JList selectionList;
	private static java.awt.Color TRANSPARENT = new java.awt.Color(0, 0, 0, 0);
	private static JTextArea log;
	private static JTextPane topBox;
	private static JButton selectButton;
	private static JPanel finalPanel;
	private static JPanel topPanel;
	private static JButton convertButton;
	private static Font buttonFont = new Font("SANS_SERIF", Font.BOLD, 25);
	private static JPanel listPanel;
	private static JFrame frame;
	private static Font labelFont = new Font("SANS_SERIF", Font.BOLD, 12);

	public static JMenuBar createMenuBar() {
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;
		// Create the menu bar.
		menuBar = new JMenuBar();

		// Build the first menu.
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("The first menu");
		menuBar.add(menu);

		// Build second menu in the menu bar.
		menu = new JMenu("Edit");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription(
				"Contain Elements pertaining to Edit");
		menuBar.add(menu);

		// Build third menu in the menu bar.
		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription(
				"Contain elements to help the user");
		menuBar.add(menu);

		menuItem = new JMenuItem("User Manual");
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// OPEN USER MANUAL AND UPDATE LOG
				logString += "Opening User Manual...\n";
				updateLog();

				ReadAndDisplayUserManual.read();

				if (ReadAndDisplayUserManual.worked()) {
					logString += "User manual was opened.\n";
					updateLog();
				} else {
					logString += "Eek! User manual failed to open.\n";
					updateLog();
				}

			}

		});
		menu.add(menuItem);

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

		selectButton = new JButton("Select Files to Convert");
		selectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// OPEN JFILESELECTOR

				logString += "Selecting a file...\n";
				updateLog();
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Select PDF to convert");
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

					fileToRead = chooser.getSelectedFile();
					String filename = removeFileExtension(chooser
							.getSelectedFile().toString());
					logString += "File " + "\"" + filename + "\""
							+ " selected.\n";
					updateLog();
					IMGCreator.createPreview();
					selectionFiles.add(removeFileExtension(selectionImages.get(selectionImages.size() - 1)));
					updateTopBox();

				} else {
					//
					logString += "Oops! Something went wrong when selecting file...\n";
					updateLog();
				}
			}
		});
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(5, 0, 0, 0);
		c.ipady = 40;
		panel.add(selectButton, c);

		selectButton.setFont(buttonFont);

		convertButton = new JButton("Convert Selected Files");
		convertButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// DO CONVERT
				logString += "Attempting to convert file...\n";

				TextToPDF test = new TextToPDF();

				try {
					test.createPDF(TextToPDF.PDF_FILENAME);
				} catch (DocumentException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Successfully converted "
						+ TextToPDF.INPUT_FILENAME + " to "
						+ TextToPDF.PDF_FILENAME + "!");

				updateLog();

			}
		});
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(5, 0, 0, 0);
		c.ipady = 40;
		panel.add(convertButton, c);

		convertButton.setFont(buttonFont);

	}

	/**
	 * Populates the PDF editing pane.
	 * 
	 * @return
	 */
	public static void addEditingPane(Container editPanel) {

		editPanel.setPreferredSize(new Dimension(600, 50));
		editPanel.setMinimumSize(new Dimension(600, 50));
		editPanel.setBackground(TRANSPARENT);
		editPanel.setLayout(new FlowLayout());

		JPanel LabelsAndComboBoxes = new JPanel();
		LabelsAndComboBoxes.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JLabel fontLabel = new JLabel("Font:");
		JLabel fontSizeLabel = new JLabel("Font size:");
		JLabel spacingLabel = new JLabel("Spacing:");

		JComboBox fontType = new JComboBox(fontsArray);
		JComboBox fontSize = new JComboBox(fontSizesArray);
		JComboBox spacing = new JComboBox(spacingArray);

		c.gridx = 0;
		c.gridy = 0;
		LabelsAndComboBoxes.add(fontLabel);
		c.gridx = 1;
		c.gridy = 1;
		LabelsAndComboBoxes.add(fontType);
		c.gridx = 1;
		c.gridy = 1;
		LabelsAndComboBoxes.add(fontSizeLabel);
		c.gridx = 1;
		c.gridy = 1;
		LabelsAndComboBoxes.add(fontSize);
		c.gridx = 2;
		c.gridy = 2;
		LabelsAndComboBoxes.add(spacingLabel);
		c.gridx = 2;
		c.gridy = 2;
		LabelsAndComboBoxes.add(spacing);

		editPanel.add(LabelsAndComboBoxes);
	}

	/**
	 * Adds/updates the JList.
	 */
	public static void populateJList(JList list) {
		list.setListData(selectionFiles.toArray());
	}

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

		selectionList = new JList();
		populateJList(selectionList);
		// Display, and set listener for selection pane.
		if (selectionFiles.isEmpty()) {
			selectionFiles.add(NO_PREVIEW);
		}

		selectionList.setPreferredSize(new Dimension(100, 250));
		selectionList.setLayout(new FlowLayout());
		selectionList.setVisibleRowCount(10);

		// selectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selectionList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent selectedPreview) {
				// Creates and displays image
				GUIView.setPreviewImage(new File(selectionImages.get(
						selectionList.getSelectedIndex()).toString()));
				GUIView.updateTopBox();

			}
		});
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
		listPanel.add(selectionLabel, BorderLayout.NORTH);
		listPanel.add(selectionList, BorderLayout.SOUTH);

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

		updateTopBox();
	}

	/**
	 * Method that creates the log box.
	 * 
	 * @param panel
	 */
	public static void addLogBox(Container panel) {

		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JLabel logLabel = new JLabel("Log:");
		logLabel.setFont(labelFont);
		c.gridx = 0;
		c.gridy = 1;
		panel.add(logLabel, c);

		log = new JTextArea(10, 30);
		log.setEditable(false);
		log.setLineWrap(true);
		log.setBackground(TRANSPARENT);

		JScrollPane scrollPane = new JScrollPane(log);
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBackground(TRANSPARENT);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 1;
		c.gridy = 2;
		panel.add(scrollPane, c);

	}

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
		// frame.setSize(700, 500);
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
		addLogBox(logPanel);
		addTopBox(topPanel);
		addEditingPane(editingPanel);

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
	}

	public static void main(String args[]) {

		// createAndShowGUI();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});

	}

}
