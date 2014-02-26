package MVC;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * Class to display a dialog box when user should be prompted for incorrect or
 * malformatted input.
 * 
 * @author Skyler
 * @version 1.0 - Constructor that instantiates dialog.
 * 
 */
public class IncorrectFormattingAlert {

	private JFrame alertFrame = new JFrame();

	// This should be the name of the file to be changed.
	File fileToOpen = new File(GUIModel.getfilenameWithExtension());

	public IncorrectFormattingAlert(String whatWentWrong) {

		Object[] options = { "Show log", "Fix error" };
		int result = JOptionPane.showOptionDialog(new JFrame(),
				"There was in error in the document we couldn't handle for you! \n"
						+ whatWentWrong + "\n" + "What would you like to do?"
						+ " (Please reopen the application).",
				"Something went wrong!", 0, 0, null, options, options[1]);

		checkClicked(result);

		/*
		 * switch (result) { case 0:
		 * 
		 * break;
		 * 
		 * case 1:
		 * 
		 * break; }
		 */

	}

	public void checkClicked(int result) {

		if (result == 0) {
			// Try catch checks if the log file has been created or not.
			try {
				new ShowLog();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			if (fileToOpen != null) {
				GUIUtils.openTextEditor(fileToOpen);

			} else
				throw new NullPointerException("Missing file!");
		}

	}
}
