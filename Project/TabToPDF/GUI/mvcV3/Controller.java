package mvcV3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFileChooser;

import version11.TextToPDFv11;

import com.itextpdf.text.DocumentException;

import creator.IMGCreator;

public class Controller {

	static Model model;
	View view;

	public Controller(Model model, View view) {
		Controller.model = model;
		this.view = view;

		this.view.addConvertListener(new ConvertButtonListener());
		this.view.addBrowseButtonListener(new BrowseButtonListener());
		this.view.addApplyButtonListener(new ApplyButtonListener());
		this.view.addSettingsButtonListener(new SettingsButtonListener());
		this.view.addSaveButtonListener(new SaveButtonListener());
	}

	protected static void setModel(Model model2) {
		model = model2;
	}

	protected static Model getModel() {
		return model;
	}
}

class BrowseButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		Model model = new Model();
		Controller.setModel(model);

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select PDF Destination");
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
		// TODO Auto-generated method stub

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
		Model model = Controller.getModel();

		String input = model.getFilenameWithExtension();
		String output = "outputfiles/";
		try {
			TextToPDFv11 test = new TextToPDFv11(output, input);
			test.WriteToPDF();
			IMGCreator.createPreview();

			// CHECK IF CONVERTION WAS DONE PROPTERLY.

			// IF IT WAS, ENABLE ALL BUTTONS and populate the fields.

			// ELSE display the error message and don't enable buttons.

		} catch (DocumentException | IOException b) {
			// TODO Auto-generated catch block
			b.printStackTrace();
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
