package mvcV2;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.UIManager;

import version11.TextToPDFv11;

import com.itextpdf.text.DocumentException;

import creator.IMGCreator;

public class EditingView {

	private static JFrame frame;

	protected static JTextField title;
	protected static JTextField subtitle;

	protected static JComboBox pageSize;

	// protected static JTextField titleFontSize;
	// protected static JTextField subTitleFontSize;
	// protected static JTextField measureFontSize;
	// protected static JTextField staffSpacing;

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

		// Staff spacing.
		JLabel spacingLabel = new JLabel("Staff Spacing: ");
		spacingLabel.setFont(labelFont);
		staffSpacing = new JSlider();
		panel.add(spacingLabel);
		panel.add(staffSpacing);

		// Title Font size.
		JLabel titleFontLabel = new JLabel("Title Font Size: ");
		titleFontLabel.setFont(labelFont);
		titleFontSize = new JSlider(JSlider.HORIZONTAL, titleFontMin,
				titleFontMax, titleSized);
		titleFontSize.setMajorTickSpacing(10);
		titleFontSize.setMinorTickSpacing(1);
		titleFontSize.setPaintTicks(true);
		titleFontSize.setPaintLabels(true);
		panel.add(titleFontLabel);
		panel.add(titleFontSize);

		// Subtitle Font size.
		JLabel subTitleFontLabel = new JLabel("Subtitle Font Size: ");
		subTitleFontLabel.setFont(labelFont);
		subTitleFontSize = new JSlider(JSlider.HORIZONTAL, subTitleFontMin,
				subTitleFontMax, subTitleSized);

		subTitleFontSize.setMajorTickSpacing(10);
		subTitleFontSize.setMinorTickSpacing(1);
		subTitleFontSize.setPaintTicks(true);
		subTitleFontSize.setPaintLabels(true);
		panel.add(subTitleFontLabel);
		panel.add(subTitleFontSize);

		// Measure Font size.
		JLabel measureFontLabel = new JLabel("Note Font Size: ");
		measureFontLabel.setFont(labelFont);
		measureFontSize = new JSlider(JSlider.HORIZONTAL, measureFontMin,
				measureFontMax, measureSized);
		measureFontSize.setMajorTickSpacing(10);
		measureFontSize.setMinorTickSpacing(1);
		measureFontSize.setPaintTicks(true);
		measureFontSize.setPaintLabels(true);
		panel.add(measureFontLabel);
		panel.add(measureFontSize);

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
