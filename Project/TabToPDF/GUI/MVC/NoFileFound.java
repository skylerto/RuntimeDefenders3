package MVC;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Opens a dialog when the file cannot be found prompting the user
 * 
 * @author Skyler
 * @version 1.0
 * 
 */
public class NoFileFound {

	/**
	 * Default constructor.
	 */
	public NoFileFound() {
		JOptionPane.showMessageDialog(new JFrame(),
				"File was not found.\nPlease select a valid file." + "\n\n"
						+ "For more information go to Help > User Manual");

	}

	/**
	 * Constructs a dialog pop up with a given message, then prompting the user
	 * to reselect a valid file.
	 * 
	 * @param message
	 *            - Description of situation
	 */
	public NoFileFound(String message) {
		JOptionPane.showMessageDialog(new JFrame(), message + "\n\n"
				+ "Please select a valid file." + "\n\n"
				+ "For more information go to Help > User Manual");

	}

}
