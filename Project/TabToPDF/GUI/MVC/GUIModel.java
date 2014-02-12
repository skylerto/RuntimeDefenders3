package MVC;

import java.util.ArrayList;

public class GUIModel {
	
	//	Static variables.
	
	protected static ArrayList<String> selectionImages = new ArrayList<String>();
	
	
	/**
	 * Updates the log box with current activity.
	 */
	private static void updateLog() {
		log.append(logString);
		logString = "";
	}

	
	public static void addToSelectionImages(String image) {
		selectionImages.add(image);
	}

}
