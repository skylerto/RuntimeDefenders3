package MVC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

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

	public GUIController(GUIModel passedModel, GUIView passedView) {

		this.model = passedModel;
		this.view = passedView;

		this.view.addSelectButtonListener(new selectButtonListener());

		// Insert Model compenents in constructor and make changes when needed.

	}

	public static void updateTopBox() {

		GUIModel.updateTopBoxLogic();

	}

	/**
	 * Adds/updates the JList.
	 */
	public static void populateJList(JList list) {
		list.setListData(GUIView.selectionFiles.toArray());
	}

}

class selectButtonListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// OPEN JFILESELECTOR

		GUIModel.logString += "Selecting a file...\n";
		GUIModel.updateLog();
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select PDF to convert");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

			GUIView.fileToRead = chooser.getSelectedFile();
			String filename = GUIUtils.removeFileExtension(chooser
					.getSelectedFile().toString());
			GUIModel.logString += "File " + "\"" + filename + "\""
					+ " selected.\n";
			GUIModel.updateLog();
			IMGCreator.createPreview();
			GUIView.selectionFiles.add(GUIUtils
					.removeFileExtension(GUIView.selectionImages
							.get(GUIView.selectionImages.size() - 1)));
			GUIController.updateTopBox();

		} else {
			GUIModel.logString += "Oops! Something went wrong when selecting file...\n";
			GUIModel.updateLog();
		}
	}
}
