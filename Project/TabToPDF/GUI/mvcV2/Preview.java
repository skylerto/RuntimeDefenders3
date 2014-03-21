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
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.UIManager;

import version11.TextToPDFv11;

import com.itextpdf.text.DocumentException;

import creator.IMGCreator;

/**
 * Simple class to Display the preview on a JPanel.
 * 
 * @author Skyler
 * @version 1.0
 * 
 *          **Later implementation add the Label to a scrollPane**
 */
public class Preview {

	protected static JButton edit = new JButton("Edit");
	private static ImageIcon image = new ImageIcon(
			"C:/Users/Skyler/git/RuntimeDefenders3/Project/TabToPDF/outputfiles/musicIMG0.png");
	protected static JLabel imageLabel;
	protected static JFrame frame = new JFrame();
	protected static JTextField title;
	protected static JTextField subtitle;

	protected static JComboBox pageSize;

	// protected static JTextField titleFontSize;
	// protected static JTextField subTitleFontSize;
	// protected static JTextField measureFontSize;
	// protected static JTextField staffSpacing;

	// BUTTONS
	protected static JButton inputButton = new JButton("Browse");
	protected static JButton convertButton = new JButton("Convert To PDF");

	// Text fields for input and destination folders.
	protected static JTextField input;

	// TITLE SLIDER INFO
	protected static JSlider titleFontSize;
	private static final int titleFontMin = 0;
	private static final int titleFontMax = 40;
	private static final int titleSized = Model.getTitleFontSize();

	// SUBTITLE SLIDER INFO
	protected static JSlider subTitleFontSize;
	private static final int subTitleFontMin = 0;
	private static final int subTitleFontMax = 40;
	private static final int subTitleSized = Model.getSubTitleFontSize();

	// MEASURE SLIDER INFO
	protected static JSlider measureFontSize;
	private static final int measureFontMin = 0;
	private static final int measureFontMax = 40;
	private static final int measureSized = Model.getMeasureFontSize();

	// SPACING SLIDER INFO
	protected static JSlider staffSpacing;
	private static final int staffSpacingMin = 0;
	private static final int staffSpacingMax = 40;
	private static final int staffSpacingCurrent = Model.getSubTitleFontSize();

	protected static JButton apply = new JButton("Apply");
	protected static JButton cancel = new JButton("Cancel");

	private static Font labelFont = new Font("SANS_SERIF", Font.BOLD, 12);
	protected static String[] pageSizes = { "A4", "A5", "Long page name" };
	private static java.awt.Color TRANSPARENT = new java.awt.Color(0, 0, 0, 0);

	// MENUBAR ITEMS
	static JMenuItem saveMenu = new JMenuItem("Save");
	static JMenuItem saveAsMenu = new JMenuItem("Save As...");

	public Preview() {
		CreateAndShowGUI();
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

	public static JMenuBar createMenuBar() {
		JMenuBar menuBar;
		JMenu menu;
		menuBar = new JMenuBar();
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEFT);
		menuBar.setLayout(layout);

		// Build the first menu.
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("File menu bar");
		menuBar.add(menu);
		menu.add(saveMenu);
		menu.add(saveAsMenu);

		menu = new JMenu("View");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("The first menu");
		menuBar.add(menu);

		return menuBar;
	}

	public static JPanel titles() {

		JPanel panel = new JPanel();
		GridBagConstraints c = new GridBagConstraints();
		panel.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.HORIZONTAL;

		// Document Name.
		JLabel docLabel = new JLabel("Document Title: ");

		// Song Title.
		JLabel songLabel = new JLabel("Song Title: ");
		songLabel.setFont(labelFont);
		title = new JTextField(20);
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = c.BASELINE_TRAILING;
		c.insets = new Insets(5, 5, 5, 5);

		panel.add(songLabel, c);
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(5, 5, 5, 5);

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
		c.insets = new Insets(5, 5, 5, 5);

		panel.add(subtitle, c);

		title.setText(Model.getTitle());
		subtitle.setText(Model.getSubtitle());

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
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		// Staff spacing.
		JLabel pageProperties = new JLabel("Edit Document");
		pageProperties.setFont(new Font("SANS_SERIF", Font.BOLD, 12));
		c.gridx = 0;
		c.gridy = 0;
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
		c.gridy = 1;
		panel.add(spacingLabel, c);

		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(5, 5, 5, 5);

		panel.add(staffSpacing, c);

		// Title Font size.
		JLabel titleFontLabel = new JLabel("Title Font Size: ");
		titleFontLabel.setFont(labelFont);
		titleFontSize = new JSlider(JSlider.HORIZONTAL, titleFontMin,
				titleFontMax, titleSized);
		titleFontSize.setMajorTickSpacing(10);
		titleFontSize.setMinorTickSpacing(1);
		titleFontSize.setPaintTicks(true);
		titleFontSize.setPaintLabels(true);

		c.gridx = 0;
		c.gridy = 3;
		panel.add(titleFontLabel, c);

		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(5, 5, 5, 5);

		panel.add(titleFontSize, c);

		// Subtitle Font size.
		JLabel subTitleFontLabel = new JLabel("Subtitle Font Size: ");
		subTitleFontLabel.setFont(labelFont);
		subTitleFontSize = new JSlider(JSlider.HORIZONTAL, subTitleFontMin,
				subTitleFontMax, subTitleSized);

		subTitleFontSize.setMajorTickSpacing(10);
		subTitleFontSize.setMinorTickSpacing(1);
		subTitleFontSize.setPaintTicks(true);
		subTitleFontSize.setPaintLabels(true);

		c.gridx = 0;
		c.gridy = 5;
		panel.add(subTitleFontLabel, c);

		c.gridx = 0;
		c.gridy = 6;
		c.insets = new Insets(5, 5, 5, 5);

		panel.add(subTitleFontSize, c);

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
		c.gridy = 7;
		panel.add(measureFontLabel, c);

		c.gridx = 0;
		c.gridy = 8;
		c.insets = new Insets(5, 5, 5, 5);

		panel.add(measureFontSize, c);

		staffSpacing.setValue(Model.getStaffSpacing());
		titleFontSize.setValue(Model.getTitleFontSize());
		subTitleFontSize.setValue(Model.getSubTitleFontSize());
		measureFontSize.setValue(Model.getMeasureFontSize());

		return panel;
	}

	private static JPanel buttons() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		// Buttons (Apply & Cancel).
		panel.add(apply);
		apply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Model.setTitle(EditingView.title.getText());
				Model.setSubtitle(EditingView.subtitle.getText());
				Model.setMeasureFontSize(""
						+ EditingView.measureFontSize.getValue());
				Model.setTitleFontSize(""
						+ EditingView.titleFontSize.getValue());
				Model.setSubTitleFontSize(""
						+ EditingView.subTitleFontSize.getValue());
				String input = Model.getFilenameWithExtention();
				String output = "outputfiles/";
				try {
					TextToPDFv11 test = new TextToPDFv11(output, input);
					test.WriteToPDF();
					IMGCreator.createPreview();

					// test.createPDF(output);
				} catch (DocumentException | IOException b) {
					// TODO Auto-generated catch block
					b.printStackTrace();
				}
				Preview.resetImage();
			}

		});
		panel.add(cancel);
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}

		});
		return panel;
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

	public static JPanel fileSelection() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(addSelectionPanel(), BorderLayout.PAGE_START);
		panel.add(new JPanel(), BorderLayout.WEST);
		panel.add(convertButton(), BorderLayout.CENTER);
		return panel;
	}

	public static JPanel addEditingTools() {

		JPanel panel = new JPanel();

		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 0;
		c.gridy = 0;
		panel.add(titles(), c); // Exec method to add the
								// titles.

		c.gridx = 0;
		c.gridy = 1;
		panel.add(pageLayout(), c);

		c.gridx = 0;
		c.gridy = 2;
		panel.add(pageProperties(), c); // Exec
										// method
										// to
										// add
										// page
										// properties.

		c.gridx = 0;
		c.gridy = 3;
		panel.add(buttons(), c); // Exec method to add
									// button panel.

		return panel;

	}

	public static void resetImage() {
		frame.remove(imageLabel);
		// image = new ImageIcon(Model.getImgOutput());
		// imageLabel.setIcon(image);
		imageLabel = new JLabel(new ImageIcon(Model.getImgOutput()));
		frame.add(imageLabel);
		frame.revalidate();
		frame.repaint();
	}

	public static void CreateAndShowGUI() {

		setLookAndFeel();

		// Setup Frame
		frame = new JFrame("Preview PDF");
		frame.setLayout(new BorderLayout());
		frame.setJMenuBar(createMenuBar());

		// Setup Panel.
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		imageLabel = new JLabel(image);

		JPanel editingPane = addEditingTools();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 5, 5, 5);
		panel.add(fileSelection(), c);

		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(5, 5, 5, 5);
		panel.add(editingPane, c);
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 5;
		c.insets = new Insets(5, 5, 5, 5);
		panel.add(imageLabel, c);

		// Setup Scroll Pane
		JScrollPane scroll = new JScrollPane(panel);
		scroll.setBackground(TRANSPARENT);
		scroll.setPreferredSize(new Dimension(800, 400));
		scroll.setMinimumSize(new Dimension(800, 400));
		scroll.setMaximumSize(new Dimension(800, 800));
		frame.add(scroll, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);

	}

	/*
	 * protected static JButton editButton = new JButton();
	 * 
	 * 
	 * 
	 * editButton.setIcon(new ImageIcon("res/editButton.jpg")); c.gridx = 1;
	 * c.gridy = 1; c.insets = new Insets(5, 5, 0, 5); panel.add(editButton, c);
	 * 
	 * void addEditButtonListener(ActionListener listenForSelectButton) {
	 * 
	 * editButton.addActionListener(listenForSelectButton);
	 * 
	 * }
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Preview();

	}

}
