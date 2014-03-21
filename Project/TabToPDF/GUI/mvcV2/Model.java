package mvcV2;

import java.io.File;

import version11.TextToPDFv11;

/**
 * Model of the GUI holding variables and information.
 * 
 * @author Skyler
 * 
 */
public class Model {

	private static TextToPDFv11 convertedObject;
	private static File previewImage;
	private static String outputFilename;
	private static String filename;
	private static String filenameWithExtension;
	private static String title = "";
	private static String imgOutput;
	private static String subtitle = "";
	private static int subTitleFontSize = 14;
	private static int measureFontSize = 8;
	private static int titleFontSize = 26; // NEED TO SET TO A DEAFULT FONT SIZE
											// IF
											// THEY
											// DONT PASS AN INT.
	private static int staffSpacing; // NEED TO SET TO A DEAFULT FONT SIZE IF
										// THEY DONT PASS AN INT.

	private static String destinationFolder;
	private static String destinationName;
	private static String desitinationPathName;

	/**
	 * Default constructor
	 * 
	 */
	public Model() {

	}

	public static String getFilename() {
		return filename;
	}

	public static void setFilename(String name) {
		Model.filename = name;
	}

	public static String getFilenameWithExtention() {
		return filenameWithExtension;
	}

	public static void setFilenameWithExtention(String name) {
		Model.filenameWithExtension = name;
	}

	/**
	 * Gets the song title.
	 * 
	 * @return String representatio of the title.
	 */
	public static String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the song.
	 * 
	 * @param title
	 *            - Title of the song.
	 */
	public static void setTitle(String title) {
		Model.title = title;
	}

	/**
	 * Gets the songs subtitle.
	 * 
	 * @return the subtitle of the song.
	 */
	public static String getSubtitle() {
		return subtitle;
	}

	/**
	 * Sets the songs subtitle.
	 * 
	 * @param subtitle
	 *            subtitle of the song.
	 */
	public static void setSubtitle(String subtitle) {
		Model.subtitle = subtitle;
	}

	/**
	 * 
	 * @return size of the font.
	 */
	public static int getMeasureFontSize() {
		return measureFontSize;
	}

	public static void setMeasureFontSize(String fontSize) {
		int size;

		try {
			size = Integer.parseInt(fontSize);
		} catch (NumberFormatException e) {
			size = 12;
			// e.printStackTrace();
		}

		Model.measureFontSize = size;
	}

	/**
	 * 
	 * @return size of the font.
	 */
	public static int getSubTitleFontSize() {
		return subTitleFontSize;
	}

	public static void setSubTitleFontSize(String fontSize) {
		int size;

		try {
			size = Integer.parseInt(fontSize);
		} catch (NumberFormatException e) {
			size = 12;
			// e.printStackTrace();
		}

		Model.subTitleFontSize = size;
	}

	/**
	 * 
	 * @return size of the font.
	 */
	public static int getTitleFontSize() {
		return titleFontSize;
	}

	public static void setTitleFontSize(String fontSize) {
		int size;

		try {
			size = Integer.parseInt(fontSize);
		} catch (NumberFormatException e) {
			size = 12;
			// e.printStackTrace();
		}

		Model.titleFontSize = size;
	}

	public static int getStaffSpacing() {
		return staffSpacing;
	}

	public static void setStaffSpacing(int staffSpacing) {
		Model.staffSpacing = staffSpacing;
	}

	public static String getOutputFilename() {
		// TODO Auto-generated method stub
		return outputFilename;
	}

	public static void setOutputFilename(String outputpath) {
		Model.outputFilename = outputpath;
	}

	public static void setPreviewImage(File outputfile) {
		Model.previewImage = outputfile;
	}

	public static File getPreviewImage() {
		return previewImage;
	}

	public static void setConvertedObject(TextToPDFv11 obj) {
		Model.convertedObject = obj;
	}

	public static TextToPDFv11 getConvertedObject() {
		return convertedObject;
	}

	public static String getImgOutput() {
		return imgOutput;
	}

	public static void setImgOutput(String name) {
		Model.imgOutput = name;
	}

}
