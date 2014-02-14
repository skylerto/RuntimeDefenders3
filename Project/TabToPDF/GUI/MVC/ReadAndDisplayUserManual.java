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
 * @author SKYLER LAYNE
 * @version 1.0
 * 
 */

public class ReadAndDisplayUserManual {

	// Detecting formatting and display to the user what's happened.
	// Flag dialog when something went terrible! Else just log and show.
	// Pull double digits together and center.

	protected String message = "";
	protected static boolean worked;

	public static void read() {

		worked = false;

		/*
		 * Read in the user manual text and display in a dialog option pane.
		 */

		String message = "";
		File userman = new File("res/userman");
		try {
			Scanner sc = new Scanner(userman);
			while (sc.hasNext()) {
				message += sc.nextLine() + "\n";
			}

			worked = true;

			sc.close();

			JOptionPane.showMessageDialog(new JFrame(), message);

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	protected static boolean worked() {
		return worked;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
