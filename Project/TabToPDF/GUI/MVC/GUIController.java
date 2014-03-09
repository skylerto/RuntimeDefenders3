package MVC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import version11.ReadFromInput;
import version11.TextToPDFv11;

import com.itextpdf.text.DocumentException;

import creator.IMGCreator;

/**
 * Communicates with the model and view.
 * 
 * 
 * @author cse23170
 * 
 *         CHANGE LOG:
 * 
 *         v0.1: - Allowed the view to communicate with the model.
 * 
 */
public class GUIController {

	private GUIModel model;
	private GUIView view;

	/**
	 * Constructor.
	 * 
	 * @param passedModel
	 * @param passedView
	 */
	public GUIController(GUIModel passedModel, GUIView passedView) {

		this.model = passedModel;
		this.view = passedView;

		this.view.addSelectButtonListener(new selectButtonListener());
		this.view.addConvertButtonListener(new convertButtonListener());
		this.view.addMenuItemListener(new menuItemListener());
		this.view.addMenuItem3Listener(new menuItem3Listener());
		this.view.addEditButtonListener(new editButtonListener());
		// this.view.addListSelectionListener(new theListSelectionListener());

		// Insert Model compenents in constructor and make changes when needed.

	}

	/**
	 * Call the updateTopBoxLogic method from the model.
	 */
	public static void updateTopBox() {

		GUIModel.updateTopBoxLogic();

	}

	/**
	 * Adds/updates the JList. Should be done on every button click.
	 * 
	 */
	/*
	 * public static void populateJList(JList list) {
	 * 
	 * list.setListData(GUIModel.selectionFiles.toArray());
	 * 
	 * }
	 */

}

/**
 * Action listener for select button.
 * 
 * @author Skyler
 * 
 */
class selectButtonListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent arg0) {

		GUIModel.logString += "Selecting a file...\n";
		GUIModel.updateLog();

		// OPEN JFILESELECTOR
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select PDF to convert");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

			// GUIView.fileToRead = chooser.getSelectedFile();

			String filenameWithExtension = chooser.getSelectedFile().toString();

			String filename = GUIUtils
					.removeFileExtension(filenameWithExtension);
			GUIModel.setfilenameWithExtension(filenameWithExtension);
			GUIModel.setfilename(GUIUtils.removeFileQualifier(filename));
			GUIModel.logString += "File " + "\"" + filename + "\""
					+ " selected.\n";
			GUIModel.updateLog();

			// output input

			GUIModel.setfilenameWithExtension(filenameWithExtension);
			String input = GUIModel.getfilenameWithExtension();
			String output = "outputfiles/";

			// TextToPDF test = new TextToPDF();
			// TextToPDF.setInputFileName(input);

			try {
				TextToPDFv11 test = new TextToPDFv11(output, input);
				test.WriteToPDF();
				// test.createPDF(output);
			} catch (DocumentException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			IMGCreator.createPreview();

			if (GUIModel.selectionFiles.size() >= 0) {

				GUIModel.selectionFiles.add(IMGCreator.OUTPUT_IMGFILE); // GUIUtils.removeFileExtension(
				GUIController.updateTopBox();

			}

		} else {
			GUIModel.logString += "Oops! Something went wrong when selecting file...\n";
			GUIModel.updateLog();
		}
		/*
		 * // Populate the JList. JList list = GUIView.selectionList;
		 * GUIController.populateJList(list);
		 */
	}
}

class convertButtonListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// DO CONVERT
		GUIModel.logString += "Attempting to convert file...\n";

		if (!(GUIModel.filenameWithExtensionIsEmpty())) {

			// CHOOSE A DESTINATION
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("Select PDF Destination");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);
			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

				// GUIView.fileToRead = chooser.getCurrentDirectory();
				String fileDestinationWithExtension = chooser.getSelectedFile()
						.toString() + System.getProperty("file.separator");

				GUIModel.logString += "Directory " + "\""
						+ fileDestinationWithExtension + "\""
						+ " selected to output to.\n";
				GUIModel.updateLog();

				// outputpath and input
				String input = GUIModel.getfilenameWithExtension();
				String outputpath = fileDestinationWithExtension;

				// TextToPDF test = new TextToPDF();
				// TextToPDF.setInputFileName(input);

				try {
					TextToPDFv11 test = new TextToPDFv11(outputpath, input);
					test.WriteToPDF();
					/*
					 * // Set the inputfile name and run the create pdf.
					 * TextToPDF test = new TextToPDF();
					 * TextToPDF.setInputFileName(input);
					 * test.createPDF(output);
					 */

				} catch (DocumentException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Successfully converted "
						+ TextToPDFv11.INPUT_FILENAME + " to "
						+ TextToPDFv11.PDF_FILENAME + "!");

				GUIModel.updateLog();

			}
		} else {
			new NoFileFound("Please select a file before trying to convert!");
		}
	}
}

class menuItemListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {

		// OPEN USER MANUAL AND UPDATE LOG
		GUIModel.logString += "Opening User Manual...\n";
		GUIModel.updateLog();

		boolean userManWorked = ReadAndDisplayUserManual.read();
		if (userManWorked) {
			GUIModel.logString += "User manual was opened.\n";
			GUIModel.updateLog();
		} else {
			GUIModel.logString += "Eek! User manual failed to open.\n";
			GUIModel.updateLog();
		}

	}
}

class menuItem3Listener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {

		// OPEN USER MANUAL AND UPDATE LOG
		GUIModel.logString += "Opening User log...\n";
		GUIModel.updateLog();

		try {
			new ShowLog();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

class editButtonListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {

		GUIModel.logString += "User editing text file...\n";
		GUIModel.updateLog();

		if (GUIModel.getfilenameWithExtension() == null) {
			// IF THERE HASN'T BEEN A FILE SELECTED YET PROMPT USER TO
			// SELECT
			// FILE.
			JOptionPane
					.showMessageDialog(
							new JFrame(),
							"Please select a file first!"
									+ "\n\n"
									+ "To do so, please press the \"Select File to Convert\" button."
									+ "\n\n"
									+ "For more information please go to Help > User Manual.");

		} else {
			GUIUtils.openTextEditor(new File(GUIModel
					.getfilenameWithExtension()));

		}
	}
}

/**
 * 
 * @author Skyler
 * 
 */
/*
 * class selectionListListener implements ActionListener {
 * 
 * @Override public void actionPerformed(ActionEvent e) {
 * 
 * // OPEN USER MANUAL AND UPDATE LOG GUIModel.logString +=
 * "Opening User Manual...\n"; GUIModel.updateLog();
 * 
 * boolean userManWorked = ReadAndDisplayUserManual.read();
 * 
 * if (userManWorked) { // GUIModel.logString += "User manual was opened.\n";
 * GUIModel.updateLog(); } else { GUIModel.logString +=
 * "Eek! User manual failed to open.\n"; GUIModel.updateLog(); }
 * 
 * } }
 */

/**
 * What specifically happens when a List item is selected.
 * 
 * @author Skyler
 * 
 */

/*
 * class theListSelectionListener implements ListSelectionListener {
 * 
 * @Override public void valueChanged(ListSelectionEvent selectedPreview) { if
 * (!selectedPreview.getValueIsAdjusting()) { JList list = (JList)
 * selectedPreview.getSource(); int index = list.getSelectedIndex(); if (index
 * == -1) { System.out.println("Nothing selected"); index = 0; } else { index++;
 * } list.setSelectedIndex(index); GUIModel.setPreviewImage(new
 * File(GUIModel.selectionFiles .get(index))); // GUIController.updateTopBox();
 * 
 * }
 * 
 * } }
 */