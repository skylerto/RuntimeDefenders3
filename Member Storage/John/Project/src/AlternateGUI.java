import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import com.itextpdf.text.DocumentException;

/**
 * Alternate GUI with bits and pieces from master project.
 */
public class AlternateGUI extends JFrame
{
	private static String logString = "";
	final static int maxGap = 20;

	private static JFrame frame;
	private static JPanel containerPanel;
	private static JPanel mainPanel;
	private static JPanel welcomePanel;
	private static JTextArea log;
	private static JTabbedPane mainPane;
	private static JButton convertButton;

	private static JButton selectButton;
	private static JLabel filePath;
	public static File fileToRead;
	private static Font titleFont = new Font("SANS_SERIF", Font.BOLD, 16);
	private static Font labelFont = new Font("SANS_SERIF", Font.PLAIN, 14);

	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				createAndShowGUI();
			}
		});
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI()
	{
		// Create the window.

		frame = new JFrame("Tab2PDF Converter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setLayout(new CardLayout);
		frame.setSize(520, 400);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		// initializing panels
		containerPanel = new JPanel();
		welcomePanel = new JPanel();
		addWelcome(welcomePanel);

		mainPanel = new JPanel();
		addMain(mainPanel);
		mainPanel.setVisible(false);
		mainPanel.setMinimumSize(new Dimension(500, 345));
		mainPanel.setPreferredSize(new Dimension(500, 345));

		containerPanel.setOpaque(true);
		mainPanel.setOpaque(true);
		welcomePanel.setOpaque(true);
		
		Color color = new Color(0xDEE6E9);
		containerPanel.setBackground(color);
		mainPanel.setBackground(color);
		welcomePanel.setBackground(color);
		// add panels
		containerPanel.add(mainPanel);
		containerPanel.add(welcomePanel);
		frame.setJMenuBar(createMenuBar());
		frame.add(containerPanel);

		// frame.pack();
		frame.setVisible(true);
	}

	public static JMenuBar createMenuBar()
	{
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

		// Print function for "File" tab section.
		menuItem = new JMenuItem("Print");
		menuItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				logString += "Opening Printer Interface...\n";
				updateLog();
				PrinterInterface printWindow = new PrinterInterface();
				printPDF test = new printPDF("outputfiles/musicPDF.pdf");
				printWindow.Scroller2(test);
			}

		});
		menu.add(menuItem);

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
		menuItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

				// OPEN USER MANUAL AND UPDATE LOG
				logString += "Opening User Manual...\n";
				updateLog();

				ReadAndDisplayUserManual.read();

				if (ReadAndDisplayUserManual.worked())
				{
					logString += "User manual was opened.\n";
					updateLog();
				} else
				{
					logString += "Eek! User manual failed to open.\n";
					updateLog();
				}

			}

		});
		menu.add(menuItem);

		return menuBar;
	}

	/**
	 * Creates the welcome screen.
	 * 
	 * @param panel
	 */
	public static void addWelcome(Container panel)
	{
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;

		// cheating with html Jlabels
		JLabel welcomeLabel = new JLabel(
				"Welcome to RuntimeDefender's Tab to PDF Converter!");
		JLabel stepOneLabel = new JLabel(
				"<html><b>Step 1:</b> Select musical tab in the form of a</html>");
		JLabel sampleTabLabel = new JLabel("<html><U>" + "text file."
				+ "<U></html>");
		JLabel stepTwoLabel = new JLabel(
				"<html><b>Step 2:</b> Configure the settings.");
		JLabel stepThreeLabel = new JLabel(
				"<html><b>Step 3:</b> Convert the selected text file into a");
		JLabel samplePDFLabel = new JLabel("<html><U>" + "PDF file."
				+ "<U></html>");
		JButton continueButton = new JButton("<html>Continue<html>");

		// configuring components
		welcomeLabel.setFont(titleFont);
		stepOneLabel.setFont(labelFont);
		sampleTabLabel.setFont(labelFont);
		stepTwoLabel.setFont(labelFont);
		stepThreeLabel.setFont(labelFont);
		samplePDFLabel.setFont(labelFont);
		continueButton.setFont(titleFont);
		Color color = new Color(0x51626C);
		// 0A92D0
		//51626C
		continueButton.setBackground(color);
		continueButton.setForeground(Color.WHITE);
		continueButton.setFocusPainted(false);
		continueButton.setPreferredSize(new Dimension(250, 35));

		// configuring gridbaglayout
		c.insets = new Insets(10, 0, 45, 0);
		c.gridx = 0;
		c.gridy = 0;
		panel.add(welcomeLabel, c);
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(0, 0, 0, 0);
		c.gridx = 0;
		c.gridy = 1;
		panel.add(stepOneLabel, c);
		;
		c.insets = new Insets(0, 50, 10, 0);
		c.gridx = 0;
		c.gridy = 2;
		panel.add(sampleTabLabel, c);
		c.insets = new Insets(0, 0, 10, 0);
		c.gridx = 0;
		c.gridy = 3;
		panel.add(stepTwoLabel, c);
		c.insets = new Insets(0, 0, 0, 0);
		c.gridx = 0;
		c.gridy = 4;
		panel.add(stepThreeLabel, c);
		c.insets = new Insets(0, 50, 10, 0);
		c.gridx = 0;
		c.gridy = 5;
		panel.add(samplePDFLabel, c);
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(50, 0, 0, 0);
		c.gridx = 0;
		c.gridy = 6;
		panel.add(continueButton, c);

		// MouseListener for sample tab
		sampleTabLabel
				.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		sampleTabLabel.setForeground(Color.BLUE);
		sampleTabLabel.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				try
				{
					URL url = AlternateGUI.class.getClassLoader().getResource(
							"sampletab.txt");
					File textFile = new File(url.getPath());
					Desktop.getDesktop().open(textFile);
				} catch (IOException ex)
				{

				}
			}
		});

		// MouseListener for sample pdf
		samplePDFLabel
				.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		samplePDFLabel.setForeground(Color.BLUE);
		samplePDFLabel.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				try
				{
					URL url = AlternateGUI.class.getClassLoader().getResource(
							"samplePDF.pdf");
					File pdfFile = new File(url.getPath());
					Desktop.getDesktop().open(pdfFile);
				} catch (IOException ex)
				{

				}

			}
		});

		// MouseListener for continuing
		continueButton.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				welcomePanel.setVisible(false);
				mainPanel.setVisible(true);
			}
		});
	}

	/**
	 * Creates the main menu.
	 * 
	 * @param panel
	 */
	public static void addMain(Container panel)
	{
		mainPane = new JTabbedPane();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		ImageIcon iconFolder = createImageIcon("res/folder-alt.png");
		ImageIcon iconSettings = createImageIcon("res/settings-alt.png");
		ImageIcon iconConvert = createImageIcon("res/convert-alt2.png");
		ImageIcon iconPrint = createImageIcon("res/print-alt2.png");
		ImageIcon iconLog = createImageIcon("res/log-alt.png");

		// Choose file panel
		JComponent selectPanel = makeSelectPanel();
		mainPane.addTab("", iconFolder, selectPanel);
		mainPane.setMnemonicAt(0, KeyEvent.VK_1);

		// Change settings panel
		JComponent settingsPanel = makeSettingsPanel();
		mainPane.addTab("", iconSettings, settingsPanel);
		mainPane.setMnemonicAt(1, KeyEvent.VK_2);

		// Convert panel
		JComponent convertPanel = makeConvertPanel();
		mainPane.addTab("", iconConvert, convertPanel);
		mainPane.setMnemonicAt(2, KeyEvent.VK_3);

		// Print Panel
		JComponent printPanel = makePrintPanel();
		mainPane.addTab("", iconPrint, printPanel);
		mainPane.setMnemonicAt(3, KeyEvent.VK_4);

		// Log Panel
		JComponent logPanel = makeLogPanel();
		mainPane.addTab("", iconLog, logPanel);
		mainPane.setMnemonicAt(4, KeyEvent.VK_4);

		mainPane.setTabPlacement(JTabbedPane.LEFT);
		panel.add(mainPane);
	}

	/**
	 * Returns an ImageIcon, or null if the path was invalid.
	 * 
	 * @param path
	 */
	protected static ImageIcon createImageIcon(String path)
	{
		java.net.URL imageURL = GUI2.class.getResource(path);
		if (imageURL != null)
		{
			return new ImageIcon(imageURL);
		} else
		{
			System.err.println("Could not locate file: " + path);
			return null;
		}
	}

	protected static JComponent makeSelectPanel()
	{
		JPanel panel = new JPanel(false);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// creating step 1 instructions label
		JLabel instructions = new JLabel(
				"<html>Step One: Select a file<br><br></html>");

		// creating button to select a file
		selectButton = new JButton("Select File to Convert");
		selectButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				// OPEN JFILESELECTOR
				logString += "Selecting a file...\n";
				updateLog();
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Select PDF to convert");
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					fileToRead = chooser.getSelectedFile();
					String filename = removeFileExtension(chooser
							.getSelectedFile().toString());
					logString += "File " + "\"" + filename + "\""
							+ " selected.\n";
					updateLog();
					filePath.setText(fileToRead.getPath());
				} else
				{
					logString += "Oops! Something went wrong when selecting file...\n";
					updateLog();
				}
			}
		});

		JLabel fileLabel = new JLabel("Selected File Path:");
		// !!!filePath label doesn't wrap!!!
		filePath = new JLabel();

		panel.add(instructions);
		panel.add(selectButton);
		panel.add(fileLabel);
		panel.add(filePath);
		return panel;
	}

	/**
	 * Updates the log box with current activity.
	 */
	private static void updateLog()
	{
		log.append(logString);
		logString = "";
	}

	/**
	 * Removes the extension of the selected file.
	 * 
	 * @param filenameWithExtention
	 * @return
	 */
	private static String removeFileExtension(String filenameWithExtention)
	{

		String fileSeparator = System.getProperty("file.separator");
		String filename;

		int lastSeparator = filenameWithExtention.lastIndexOf(fileSeparator);

		// Check to see if it's in the root directory. (Base case, probably will
		// never happen)
		if (lastSeparator == -1)
		{
			filename = filenameWithExtention;
		} else
		{
			// Create substring from index of last file seperator to the end of
			// the String.
			filename = filenameWithExtention.substring(lastSeparator + 1);
		}
		return filename;
	}

	protected static JComponent makeSettingsPanel()
	{
		JPanel panel = new JPanel(false);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JLabel instructions = new JLabel(
				"<html>Step Two: Configure the Settings"
						+ "<br><br>Change the PDF options according to your preferences.<br></html>");

		panel.add(instructions);

		return panel;
	}

	protected static JComponent makeConvertPanel()
	{
		JPanel panel = new JPanel(false);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JLabel instructions = new JLabel(
				"<html>Step Two: Convert the Tab"
						+ "<br><br>Select a location to output the converted tab and convert.<br></html>");

		convertButton = new JButton("Convert Selected Files");
		convertButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				// DO CONVERT
				logString += "Attempting to convert file...\n";

				if (fileToRead != null)
				{
					TextToPDF result = new TextToPDF();
					try
					{
						result.createPDF(TextToPDF.PDF_FILENAME);
					} catch (DocumentException e)
					{
						e.printStackTrace();
					} catch (IOException e)
					{
						e.printStackTrace();
					}
					System.out.println("Successfully converted "
							+ TextToPDF.INPUT_FILENAME + " to "
							+ TextToPDF.PDF_FILENAME + "!");

					updateLog();
				} else
				{
					logString += "No file to convert...\n";
					updateLog();
				}
			}
		});

		panel.add(instructions);
		panel.add(convertButton);

		return panel;
	}

	protected static JComponent makePrintPanel()
	{
		JPanel panel = new JPanel(false);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JLabel instructions = new JLabel("Print the converted tab.");

		panel.add(instructions);

		return panel;
	}

	protected static JComponent makeLogPanel()
	{
		JPanel panel = new JPanel(false);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JLabel logLabel = new JLabel("Log");

		log = new JTextArea();
		log.setEditable(false);
		log.setLineWrap(true);
		// log.setBackground(TRANSPARENT);

		JScrollPane scrollPane = new JScrollPane(log);
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		panel.add(logLabel);
		panel.add(scrollPane);

		return panel;
	}

}