package mvcV2;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

/**
 * Simple class to Display the preview on a JPanel.
 * 
 * @author Skyler
 * @version 1.0
 * 
 *          **Later implementation add the Label to a scrollPane**
 */
public class Preview {

	private static ImageIcon image = new ImageIcon(
			"C:/Users/Skyler/git/RuntimeDefenders3/Project/TabToPDF/outputfiles/musicIMG0.png");
	private static JLabel imageLabel;
	private static JFrame frame = new JFrame();

	public Preview() {
		CreateAndShowGUI();
	}

	/**
	 * Sets the look and feel of the Java application.
	 */
	private static void setLookAndFeel() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception exc) {

		}
	}

	public static void CreateAndShowGUI() {
		setLookAndFeel();
		frame = new JFrame("Preview PDF");
		JScrollPane pane = new JScrollPane();
		imageLabel = new JLabel(image);
		frame.add(imageLabel);
		frame.pack();
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Preview();

	}

}
