package mvcV2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFileChooser;

import version11.TextToPDFv11;

import com.itextpdf.text.DocumentException;

import creator.IMGCreator;

public class Controller {

	private Model model;
	private View view;

	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;

		// this.view.addDestinationButtonListener(new destinationListener());
		this.view.addInputButtonListener(new inputListener());
		this.view.addConvertListener(new convertButtonListener());
		this.view.addPreviewListener(new previewListener());
	}

}

class inputListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Select a file.

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select PDF Destination");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

			String filenameWithExtension = chooser.getSelectedFile().toString();
			Model.setFilenameWithExtention(filenameWithExtension);
			Model.setFilename(Utils.removeFileExtension(filenameWithExtension));

			View.input.setText(filenameWithExtension);
			View.input.setEnabled(true);
			View.convertButton.setEnabled(true);
		}
		// Save the path and filename.
		// Update destination path.

	}
}

class convertButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String input = Model.getFilenameWithExtention();
		String output = "outputfiles/";
		try {
			TextToPDFv11 test = new TextToPDFv11(output, input);
			test.WriteToPDF();
			Model.setConvertedObject(test);
			IMGCreator.createPreview();
			View.previewButton.setEnabled(true);
			// test.createPDF(output);
		} catch (DocumentException | IOException b) {
			// TODO Auto-generated catch block
			b.printStackTrace();
		}

	}

}

class previewListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		new Preview();

	}

}

class editListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		new EditingView();

	}

}

class menuItemListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
