package MVC;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Class to open the log file.
 * 
 * @author Skyler
 * @version 1.0 - implemented open() method to open logFile in res.
 * 
 */
public class ShowLog {

	public ShowLog() throws FileNotFoundException {

		boolean worked = open();
		if (!worked) {
			throw new FileNotFoundException();
		}
	}

	public boolean open() {
		boolean worked = false;

		String message = GUIUtils.openAndReadFile("res/logFile.txt");
		if (message != null) {
			JOptionPane.showMessageDialog(new JFrame(), message);
			worked = true;
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
