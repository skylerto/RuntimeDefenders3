package mvcV3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

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
	}

	protected static void setModel(Model model2) {
		model = model2;
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

class ApplyButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}

class ConvertButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}

class SettingsButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
