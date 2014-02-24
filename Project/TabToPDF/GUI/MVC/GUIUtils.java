package MVC;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

/**
 * Utilities used for GUIModel.
 * 
 * @author cse23170
 * 
 * 
 *         TO IMPLEMENT: - Make and concatenate to a text file as a log.
 * 
 *         CHANGE LOG:
 * 
 *         v0.1: - Created and added removeFileExtension method.
 * 
 */

public class GUIUtils {

	/**
	 * Opens
	 * 
	 * Works for linux mac and windows.
	 * 
	 * @param file
	 *            you'd like to open in external text editor.
	 * @return - returns if the editor was able to open or not.
	 * @throws OSNotSupportedException
	 * 
	 */
	public static boolean openTextEditor(File file) {

		/*
		 * boolean worked = true;
		 * 
		 * // Opens a new text area with the required file.
		 * 
		 * JFrame editorFrame = new JFrame(logName); JTextArea editArea = new
		 * JTextArea(); editArea.setEditable(false);
		 * editArea.setText(GUIUtils.openAndReadFile(file.toString()));
		 * editorFrame.add(editArea); editorFrame.pack();
		 */
		// Code to open, but user has to select what he wants to open with.

		boolean worked = true;

		String os = System.getProperty("os.name").toLowerCase();

		// is windows or mac
		if (os.indexOf("win") >= 0 || os.indexOf("mac") >= 0) {
			Desktop dt = Desktop.getDesktop();
			try {
				dt.open(file);
				worked = true;
			} catch (IOException e) {
				e.printStackTrace();
			} // TODO Auto-generated catch block e.printStackTrace(); }

		} else if (os.indexOf("nux") >= 0) {
			// is linux ish.

			try {

				Runtime.getRuntime().exec(
						new String[] { "jedit", file.toString() });
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			try {
				throw new OSNotSupportedException("The operating system: " + os
						+ " is not supported.");
			} catch (OSNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return worked;
	}

	/**
	 * 
	 * Parses filename and trims the file extension.
	 * 
	 * @param filenameWithExtention
	 *            - File name with extension you wish to remove.
	 * @return
	 */
	static String removeFileExtension(String filenameWithExtention) {

		String fileSeparator = System.getProperty("file.separator");
		String filename;

		int lastSeparator = filenameWithExtention.lastIndexOf(fileSeparator);
		// Check to see if it's in the root directory. (Base case, probably will
		// never happen)
		if (lastSeparator == -1) {

			filename = filenameWithExtention;
		} else {

			// Create substring from index of last file separator to the end of
			// the String.
			filename = filenameWithExtention.substring(lastSeparator + 1);
		}

		return filename;
	}

	static String removeFileQualifier(String file) {
		return file.substring(0, file.indexOf('.'));
	}

	/**
	 * 
	 * Writes to a file. Should replace updateLog method.
	 * 
	 * @param filename
	 * @param toWrite
	 */
	static void writeToFile(File filename, String toWrite) {

		Writer writer = null;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(filename), "utf-8"));
			writer.write(toWrite);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Reads from a file, returning a string versions of the contents of the
	 * file.
	 * 
	 * @param fileName
	 * @return
	 */
	static String openAndReadFile(String fileName) {

		String message = "";
		File userman = new File(fileName);
		try {
			Scanner sc = new Scanner(userman);
			while (sc.hasNext()) {
				message += sc.nextLine() + "\n";
			}
			sc.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return message;
	}

	public static void main(String[] args) {

	}
}
