package MVC;

import java.io.File;
import java.io.FileNotFoundException;
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

			// Create substring from index of last file seperator to the end of
			// the String.
			filename = filenameWithExtention.substring(lastSeparator + 1);
		}

		return filename;
	}

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

}
