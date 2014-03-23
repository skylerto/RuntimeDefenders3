package mvcV3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

public class Utils {

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

}
