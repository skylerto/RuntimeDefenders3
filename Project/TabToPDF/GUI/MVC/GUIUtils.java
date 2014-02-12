package MVC;

/**
 * Utilities used for GUIModel.
 * 
 * v0.1	-	Created and added removeFileExtension method.
 * 
 * @author cse23170
 *
 */

public class GUIUtils {
	
	/**
	 * 
	 * Parses filename and trims the file extension.
	 * 
	 * @param filenameWithExtention - File name with extension you wish to remove.
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

}
