package mvcV2;

/**
 * Model of the GUI holding variables and information.
 * 
 * @author Skyler
 * 
 */
public class Model {

	private static String title;
	private static String subtitle;
	private static int fontSize; // NEED TO SET TO A DEAFULT FONT SIZE IF THEY
									// DONT PASS AN INT.
	private static int staffSpacing; // NEED TO SET TO A DEAFULT FONT SIZE IF
										// THEY DONT PASS AN INT.

	/**
	 * Default constructor
	 * 
	 */
	public Model() {

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
	public static int getFontSize() {
		return fontSize;
	}

	public static void setFontSize(String fontSize) {
		int size;

		try {
			size = Integer.parseInt(fontSize);
		} catch (NumberFormatException e) {
			size = 12;
			// e.printStackTrace();
		}

		Model.fontSize = size;
	}

	public static int getStaffSpacing() {
		return staffSpacing;
	}

	public static void setStaffSpacing(int staffSpacing) {
		Model.staffSpacing = staffSpacing;
	}

}
