package MVC;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class GUIModel {
	
	//	Static variables.
	
	protected static ArrayList<String> selectionImages = new ArrayList<String>();
	private static File imageFile;
	private static String logString = "";
	
	
	public static void updateTopBox() {
		topBox.setText("");

		try {

			if (imageFile == null) {
				imageFile = new File("res/nopreview.gif");
			} else if (selectionFiles.get(0) == NO_PREVIEW) {
				selectionImages.remove(0);
				selectionFiles.remove(0);
			}
			int neededWidth = imgScrollPane.getVerticalScrollBar()
					.getPreferredSize().width;
			int neededHeight = imgScrollPane.getHorizontalScrollBar()
					.getPreferredSize().height;
			BufferedImage image = ImageIO.read(imageFile);
			ImageIcon preview = new ImageIcon(image);
			image.getScaledInstance(neededWidth, neededWidth, 0);
			topBox.insertIcon(preview);
			SimpleAttributeSet attribs = new SimpleAttributeSet();
			StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
			populateJList(selectionList);

			selectionList.repaint();
			topBox.repaint();
			listPanel.repaint();
			topPanel.revalidate();
			topPanel.repaint();
			frame.repaint();

			logString += "Document preview has been updated.\n";
			updateLog();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param f
	 *            - File to replace "no preview available"
	 */
	public static void setPreviewImage(File f) {
		imageFile = f;
	}
	
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
