package MVC;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class GUIController {
	
	public GUIController(){
		
		// Insert Model compenents in constructor and make changes when needed.
		GUIModel model = new GUIModel();
	}
	
	static BufferedImage image;

	public static void updateTopBox() {
		
		int neededWidth = GUIView.imgScrollPane.getVerticalScrollBar()
				.getPreferredSize().width;
		int neededHeight = GUIView.imgScrollPane.getHorizontalScrollBar()
				.getPreferredSize().height;
		
		ImageIcon preview = new ImageIcon(image);
		image.getScaledInstance(neededWidth, neededWidth, 0);
		GUIView.topBox.insertIcon(preview);
		SimpleAttributeSet attribs = new SimpleAttributeSet();
		StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
		populateJList(GUIView.selectionList);

		GUIView.selectionList.repaint();
		GUIView.topBox.repaint();
		GUIView.listPanel.repaint();
		GUIView.topPanel.revalidate();
		GUIView.topPanel.repaint();
		GUIView.frame.repaint();
		
	}
	
	/**
	 * Adds/updates the JList.
	 */
	public static void populateJList(JList list) {
		list.setListData(GUIView.selectionFiles.toArray());
	}
	

}
