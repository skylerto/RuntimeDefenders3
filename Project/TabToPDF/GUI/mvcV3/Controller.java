package mvcV3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import version12.TextToPDFv12;

import com.itextpdf.text.DocumentException;

import creator.IMGCreator;

public class Controller {

	static Model model;
	View view;
	static TextToPDFv12 test;

	public Controller(View view) {
		this.view = view;

		this.view.addConvertListener(new ConvertButtonListener());
		this.view.addSelectButtonListener(new SelectButtonListener());
		this.view.addSaveButtonListener(new SaveButtonListener());
		this.view.spacingListener(new SpacingListener());
		this.view.titleListener(new TitleListener());
		this.view.subtitleListener(new SubtitleListener());
	}

	protected static void setWriter(TextToPDFv12 test2) {
		test = test2;
	}

	protected static TextToPDFv12 getWriter() {
		return test;
	}

	protected static void setModel(Model model2) {
		model = model2;
	}

	public static Model getModel() {
		return model;
	}
}

class TitleListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		Model model = Controller.getModel();
		String newTitle = View.title.getText();
		model.setFilename(newTitle);

		try {

			String input = model.getFilenameWithExtension();
			String output = "outputfiles/";
			TextToPDFv12 test = new TextToPDFv12(output, input);
			test.titleString = newTitle;
			test.WriteToPDF();
			IMGCreator.createPreview(model);

			// CHECK IF CONVERTION WAS DONE PROPTERLY.
			String image = model.getPreviewImage();
			View.repaintPreview(image);

		} catch (DocumentException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}

class SubtitleListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		Model model = Controller.getModel();
		String newTitle = View.subtitle.getText();
		model.setFilename(newTitle);

		try {

			String input = model.getFilenameWithExtension();
			String output = "outputfiles/";
			TextToPDFv12 test = new TextToPDFv12(output, input);
			test.subtitleString = newTitle;
			test.WriteToPDF();
			IMGCreator.createPreview(model);

			// CHECK IF CONVERTION WAS DONE PROPTERLY.
			String image = model.getPreviewImage();
			View.repaintPreview(image);

		} catch (DocumentException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}

class SelectButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		Model model = new Model();
		Controller.setModel(model);

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select File");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

			String filenameWithExtension = chooser.getSelectedFile().toString();
			model.setFilenameWithExtention(filenameWithExtension);
			model.setFilename(Utils.removeFileExtension(filenameWithExtension));

			View.input.setText(filenameWithExtension);
			View.input.setEnabled(true);
			View.convertButton.setEnabled(true);
		}
		// Save the path and filename.
		// Update destination path.

		View.repaintPreview("");
	}
}

/**
 * Saves the file in the desired location with the desired name.
 * 
 * @author Skyler
 * 
 */
class SaveButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		Model model = Controller.getModel();
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select PDF Destination");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setAcceptAllFileFilterUsed(false);

		String input = model.getFilenameWithExtension();
		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			String outputFilename = chooser.getSelectedFile().toString();
			TextToPDFv12 test;
			try {
				test = new TextToPDFv12(outputFilename, input);
				test.WriteToPDF();

			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}

/**
 * Update the stuffs from the GUI to the conversion code and convert, then
 * redisplay the image.
 * 
 * @author Skyler
 * 
 */
class ApplyButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}

class ConvertButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(" ghfjgf");

		Model model = Controller.getModel();

		String input = model.getFilenameWithExtension();
		String output = "outputfiles/";
		try {
			TextToPDFv12 test = new TextToPDFv12(output, input);
			Controller.setWriter(test);
			test.WriteToPDF();
			model.setSpacing(test.file.getSACING());
			model.setTitle(test.file.getTITLE());
			model.setSubTitle(test.file.getSUBTITLE());
			IMGCreator.createPreview(model);

			// CHECK IF CONVERTION WAS DONE PROPTERLY.

			String image2 = IMGCreator.getLastConverted();
			View.repaintPreview(image2);
			// IF IT WAS, ENABLE ALL BUTTONS and populate the fields.

			// GET CONVERTED FIELD VALUES.
			// ENABLE FIELDS
			View.title.setEnabled(true);
			View.subtitle.setEnabled(true);
			View.staffSpacing.setEnabled(true);
			View.measureFontSize.setEnabled(true);

			// SET FIELD VALUES
			View.subtitle.setText(model.getSubTitle());
			View.title.setText(model.getTitle());
			View.staffSpacing.setValue((int) model.getSpacing());
			View.measureFontSize.setValue(model.getMeasureFontSize());
			View.saveButton.setEnabled(true);

			// ELSE display the error message and don't enable buttons.

		} catch (DocumentException | IOException b) {
			// TODO Auto-generated catch block
			b.printStackTrace();
		}
	}

}

class SpacingListener implements ChangeListener {

	Model model = Controller.getModel();

	// TextToPDFv12 test = Controller.getWriter();

	public void stateChanged(ChangeEvent e) {

		JSlider source = (JSlider) e.getSource();

		if (!source.getValueIsAdjusting()) {
			// TODO Auto-generated method stub
			Model model = Controller.getModel();
			String input = model.getFilenameWithExtension();
			String output = "outputfiles/";
			try {

				TextToPDFv12 test = new TextToPDFv12(output, input);
				test.file.setLineSpacing((View.staffSpacing.getValue()));
				// test.setLineSpacing(View.staffSpacing.getValue());
				test.WriteToPDF();
				IMGCreator.createPreview(model);

				// CHECK IF CONVERTION WAS DONE PROPTERLY.
				model.setSpacing(View.staffSpacing.getValue());
				View.staffSpacing.setValue((int) model.getSpacing());
				System.out.println("kjaewfkjsadfkjadsfkj");
				String image = IMGCreator.getLastConverted();

				View.repaintPreview(image);

			} catch (DocumentException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}
}

/**
 * Displays more of the settings for the properties of the pdf.
 * 
 * @author Skyler
 * 
 */
class SettingsButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
