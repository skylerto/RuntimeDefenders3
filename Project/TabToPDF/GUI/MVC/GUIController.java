package MVC;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class GUIController {
	
	static GUIModel model;
	
	public GUIController(){
		File file = new File("nopreview.gif");
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
			this.model = new GUIModel(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Insert Model compenents in constructor and make changes when needed.

	}

	public static void updateTopBox() {
		
		GUIModel.updateTopBoxLogic();
		

		
	}
	
	/**
	 * Adds/updates the JList.
	 */
	public static void populateJList(JList list) {
		list.setListData(GUIView.selectionFiles.toArray());
	}
	

}
