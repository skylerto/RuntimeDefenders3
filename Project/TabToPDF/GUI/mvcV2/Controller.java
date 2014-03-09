package mvcV2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import MVC.GUIModel;
import MVC.ReadAndDisplayUserManual;

public class Controller {

	private Model model;
	private View view;

	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;

		this.view.addDestinationButtonListener(new destinationListener());
		this.view.addEditButtonListener(new editListener());
		this.view.addInputButtonListener(new inputListener());
	}

}

class inputListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Select a file.
		
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select PDF Destination");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			
		}
		// Save the path and filename.
		// Update destination path.
	}

}

class destinationListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Select a folder
		
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select PDF Destination");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			
		}
	}

}

class editListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}

class menuItemListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
