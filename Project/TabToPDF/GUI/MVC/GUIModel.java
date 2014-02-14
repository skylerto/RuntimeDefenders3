package MVC;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * 
 * 
 * @author cse23170
 * 
 *         TO IMPLEMENT: - Update logbox ONLY WHEN NEEDED -
 *         
 *	CHANGE LOG:
 * 
 * v0.1: 
 * 			- Broke up some components from the view into the model.
 * 			-
 */
public class GUIModel {

	// Static variables.

	protected static ArrayList<String> selectionImages = new ArrayList<String>();
	private static File imageFile;
	static String logString = "";
	static BufferedImage image;

	/**
	 * GUI Model constructor, constructs a model of the GUI.
	 * 
	 * @param - First initialization should give bare minimum i.e. picture
	 *        should be no preview image. Image lists should be empty (Except
	 *        for preview image). Should offset other array by s1 to account for
	 *        this.
	 */
	public GUIModel() { //BufferedImage image
		//this.image = image;

	}

	public static void updateTopBoxLogic() {
		GUIView.topBox.setText("");
		File imageFile = new File("res/nopreview.gif");
/*
		if (imageFile == null) {
			imageFile = new File("res/nopreview.gif");
		} else if (GUIView.selectionFiles.get(0) == GUIView.NO_PREVIEW) {
			selectionImages.remove(0);
			GUIView.selectionFiles.remove(0);
		}
*/
		try {
			image = ImageIO.read(imageFile);
			setImage(image);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int neededWidth = GUIView.imgScrollPane.getVerticalScrollBar()
				.getPreferredSize().width;
		int neededHeight = GUIView.imgScrollPane.getHorizontalScrollBar()
				.getPreferredSize().height;

		ImageIcon preview = new ImageIcon(GUIModel.getImage());
		GUIModel.getImage().getScaledInstance(neededWidth, neededWidth, 0);
		GUIView.topBox.insertIcon(preview);
		SimpleAttributeSet attribs = new SimpleAttributeSet();
		StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
		GUIController.populateJList(GUIView.selectionList);

		GUIView.selectionList.repaint();
		GUIView.topBox.repaint();
		GUIView.listPanel.repaint();
		GUIView.topPanel.revalidate();
		GUIView.topPanel.repaint();
		GUIView.frame.repaint();

		logString += "Document preview has been updated.\n";
		updateLog();
	}

	/**
	 * 
	 */
	public static void setImage(BufferedImage image2) {
		image = image2;
	}

	public static BufferedImage getImage() {
		return GUIModel.image;
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
	static void updateLog() {
		GUIView.log.append(logString);
		logString = "";
	}

	public static void addToSelectionImages(String image) {
		selectionImages.add(image);
	}

}
