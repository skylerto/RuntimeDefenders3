package mvcV2;

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
import javax.swing.JTextField;
import javax.swing.UIManager;

import print.printPDF;
import MVC.PrinterInterface;

public class View {

	private static JFrame frame;

	// Buttons
	protected static JButton inputButton = new JButton("Browse");
	protected static JButton convertButton = new JButton("Convert To PDF");
	protected static JButton previewButton = new JButton();

	// Menu bar items
	protected static JMenuItem log = new JMenuItem("Log");
	protected static JMenuItem autoCorrection = new JMenuItem(
			"Auto-Corrections");
	protected static JMenuItem emailTab;
	protected static JMenuItem helpTab;

	// Text fields for input and destination folders.
	protected static JTextField input;
	protected static JTextField destination;

	// Font
	private static Font labelFont = new Font("SANS_SERIF", Font.BOLD, 12);

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

	public static JPanel addSelectionPanel() {
		JPanel panel = new JPanel();
		GridBagConstraints c = new GridBagConstraints();
		panel.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.HORIZONTAL;

		JLabel inputLabel = new JLabel("Text File to Convert:");
		inputLabel.setFont(labelFont);
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = c.WEST;
		c.insets = new Insets(5, 5, 0, 5);
		panel.add(inputLabel, c);

		// Inline
		input = new JTextField(25);
		input.setEnabled(false);
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(5, 5, 0, 5);
		panel.add(input, c);

		c.gridx = 2;
		c.gridy = 1;
		c.insets = new Insets(5, 5, 0, 5);
		panel.add(inputButton, c);

		return panel;
	}

	public static JPanel convertButton() {
		JPanel panel = new JPanel();
		convertButton.setPreferredSize(new Dimension(150, 50));
		convertButton.setEnabled(false);
		panel.add(convertButton);

		return panel;
	}

	public static JPanel helpButton() {
		JPanel panel = new JPanel();
		previewButton.setIcon(new ImageIcon("res/previewImage.jpg"));
		previewButton.setEnabled(false);
		panel.add(previewButton);
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
		frame = new JFrame("Convert Tab to PDF");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		frame.setJMenuBar(createMenuBar());
		frame.add(addSelectionPanel(), BorderLayout.PAGE_START);
		frame.add(new JPanel(), BorderLayout.WEST);
		frame.add(convertButton(), BorderLayout.CENTER);
		frame.add(helpButton(), BorderLayout.EAST);

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

		inputButton.addActionListener(listenForSelectButton);

	}

	void addPreviewListener(ActionListener previewListener) {
		previewButton.addActionListener(previewListener);
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
