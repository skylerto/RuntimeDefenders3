package MVC;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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

	public IncorrectFormattingAlert(String whatWentWrong) {

		Object[] options = { "Show log", "Fix it error" };
		int result = JOptionPane.showOptionDialog(alertFrame,
				"There was in error in the document we couldn't handle for you! \n"
						+ whatWentWrong + "\n What would you like to do?",
				"Something went wrong!", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		switch (result) {
		case 0:

			// Try catch checks if the log file has been created or not.
			try {
				new ShowLog();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 1:
			File fileToOpen = GUIView.fileToRead;
			if (fileToOpen != null)
				openTextEditor(fileToOpen);
			else
				throw new NullPointerException("Missing file!");
			break;
		}

	}

	/**
	 * Opens
	 * 
	 * @param file
	 *            you'd like to open in external text editor.
	 * @return - returns if the editor was able to open or not.
	 * 
	 */
	public boolean openTextEditor(File file) {

		boolean worked = false;
		Desktop dt = Desktop.getDesktop();
		try {
			dt.open(file);
			worked = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return worked;
	}

	/**
	 * Created for testing
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new IncorrectFormattingAlert("Wrong!");
	}
}
