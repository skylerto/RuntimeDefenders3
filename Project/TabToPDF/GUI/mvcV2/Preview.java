package mvcV2;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

	protected static JButton edit = new JButton("Edit");
	private static ImageIcon image = new ImageIcon(
			"C:/Users/Skyler/git/RuntimeDefenders3/Project/TabToPDF/outputfiles/musicIMG0.png");
	protected static JLabel imageLabel;
	protected static JFrame frame = new JFrame();

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

	public static void resetImage() {
		frame.remove(imageLabel);
		// image = new ImageIcon(Model.getImgOutput());
		// imageLabel.setIcon(image);
		imageLabel = new JLabel(new ImageIcon(Model.getImgOutput()));
		frame.add(imageLabel);
		frame.revalidate();
	}

	public static void CreateAndShowGUI() {
		setLookAndFeel();
		frame = new JFrame("Preview PDF");
		frame.setLayout(new FlowLayout());
		imageLabel = new JLabel(image);
		frame.add(imageLabel);
		frame.add(edit);
		edit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new EditingView();
				frame.revalidate();
			}

		});
		frame.pack();
		frame.setVisible(true);

	}

	/*
	 * protected static JButton editButton = new JButton();
	 * 
	 * 
	 * 
	 * editButton.setIcon(new ImageIcon("res/editButton.jpg")); c.gridx = 1;
	 * c.gridy = 1; c.insets = new Insets(5, 5, 0, 5); panel.add(editButton, c);
	 * 
	 * void addEditButtonListener(ActionListener listenForSelectButton) {
	 * 
	 * editButton.addActionListener(listenForSelectButton);
	 * 
	 * }
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Preview();

	}

}
