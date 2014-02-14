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

	public static boolean read() {
		boolean worked = false;

		/*
		 * Read in the user manual text and display in a dialog option pane.
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
