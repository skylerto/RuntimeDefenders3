package MVC;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Commit example by saad from linux
 * 
 * Commit aagain..
 * 
 * This class opens read the user manual and displays it in a dialog box!
 * 
 * Just like every other piece, this is subject to change.
 * 
 * Some possible ideas: - Change from a dialog to actually opening another Java
 * Swing applet. - Hard coding the user manual.
 * 
 * Implemented as static due to having to fetch the filename from another class!
 * 
 * @author SKYLER LAYNE
 * @version 1.0
 * 
 */

public class ReadAndDisplayUserManual {

	// Detecting formatting and display to the user what's happened.
	// Flag dialog when something went terrible! Else just log and show.
	// Pull double digits together and center.

	protected String message = "";

	/**
	 * The text to be displayed in the user manual.
	 */
	public static void buildUserMan() {
		String userman = "Instructions:"
				+ "\n\n"
				+ "Step 1:"
				+ "\n\n"
				+ "Select a file by pressing the \"Select Files to Convert button\""
				+ "\n\n"
				+ "Step 2:"
				+ "\n\n"
				+ " Confirm the layout of your PDF in the Preview PDF section (At the top of the application)."
				+ "\n\n"
				+ "Step 3:"
				+ "\n\n"
				+ " If you would like to change/fix anything press the EDIT button and select your default text editor (Notepad on windows, jedit on linux, SimpleText on mac)."
				+ "\n\n"
				+ "Step 4:"
				+ "\n\n"
				+ " When finished editing, save the file then press the Convert Selected Files button; to repreview the edited document reselect the file i.e. return to Step 1.";
		GUIUtils.writeToFile(new File("res/userman"), userman);
	}

	public static boolean read() {
		boolean worked = false;
		buildUserMan();
		/*
		 * Read in the user manual text and display in a dialog option pane. So
		 * long as the userman contains text.
		 */

		String message = GUIUtils.openAndReadFile("res/userman");
		if (message != null) {
			JOptionPane.showMessageDialog(new JFrame(), message);
		}

		return worked;
	}

	/**
	 * Created for testing.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
