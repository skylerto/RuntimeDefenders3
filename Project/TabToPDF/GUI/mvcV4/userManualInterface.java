package mvcV4;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

public class userManualInterface extends JFrame{
	
	JButton button1;
	String outputString = "";
	
	// A Tree contains nodes that can contain other nodes
	static boolean is_open = false;
	JTree theTree;
	
	// If a node holds other nodes it is called a parent node
	// The nodes inside of a parent node are children nodes
	// Nodes on the same level are called siblings
	
	DefaultMutableTreeNode gettingStarted, print, email, emails;
	
	DefaultMutableTreeNode fileSystem = new DefaultMutableTreeNode("User Manual");
	private  JFrame frame = new JFrame("User Manual");
	private  JLabel browsePicture;
	private  JLabel convertPicture;
	private  JLabel editPicture;
	private  JLabel savePicture;
	private  JLabel gettingStartedStep1_0;
	private  JLabel gettingStartedStep2_0;
	private  JLabel gettingStartedStep3_0;
	private  JLabel gettingStartedStep4_0;
	private  JLabel printStep1_0;
	private  JLabel printStep2_0;
	private  JLabel emailStep1_0;
	private  JLabel emailStep2_0;
	private  JLabel emailStep3_0;
	private  JLabel emailStep4_0;
	private  JPanel informationPanel;
	private  JScrollPane scrollBox2;
	private  GridBagConstraints c = new GridBagConstraints();
	public userManualInterface(){
		frame.setSize(700,500);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		this.Close();
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
		
		JPanel thePanel = new JPanel(new GridBagLayout());
		informationPanel = new JPanel(new GridBagLayout());
		frame.getContentPane().add(thePanel, BorderLayout.NORTH);
		// Create the JTree by passing it the top tree node
		
		theTree = new JTree(fileSystem);
		
		// Makes sure only one item can be selected at a time
		// By default you can make multiple selections
		
		theTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		// Only show 8 rows of the tree at a time
		
		theTree.setVisibleRowCount(8);
		
		// Add the items to the tree documents, work, games
		// We first add the documents parent node
		
		gettingStarted = addAFile("Getting Started", fileSystem);
		
		// Now we add children nodes to the documents parent node
		
		addAFile("Step 1 - Selecting a File", gettingStarted);
		addAFile("Step 2 - Editing File", gettingStarted);
		addAFile("Step 3 - Saving File", gettingStarted);
		
		
		// Create the work node and its children
		
		print = addAFile("Printer", fileSystem);
		addAFile("How to Print: Step 1", print);
		addAFile("How to Print: Step 2", print);
		
		// Create the games node and its children
		
		email = addAFile("Email", fileSystem);
		addAFile("Step 1 - Click Email", email);
		addAFile("Step 2 - Login", email);
		addAFile("Step 3 - Fill in Fields", email);
		addAFile("Step 4 (optional) - Add Emails from Address Book", email);
		
		// Put the tree in a scroll component
		
		theTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener(){
			public void valueChanged(javax.swing.event.TreeSelectionEvent evt){
				gettingStartedClicked(evt);
			}
		}
		);
		
		theTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener(){
			public void valueChanged(javax.swing.event.TreeSelectionEvent evt){
				howToPrintClicked(evt);
			}
		}
		);
		
		theTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener(){
			public void valueChanged(javax.swing.event.TreeSelectionEvent evt){
				howToEmailClicked(evt);
			}
		}
		);
		
		JScrollPane scrollBox = new JScrollPane(theTree);
		// Set the size for the JScrollPane so that everything fits
		
		Dimension d = scrollBox.getPreferredSize();
		d.width = 200;
		d.height = 350;
		scrollBox.setPreferredSize(d);
		
		c.insets = new Insets(0,0,0,0);
		c.gridx = 0;
		c.gridy = 0;
		thePanel.add(scrollBox, c);
		
		ImageIcon Icon = CreateImageIcon("/userManualImages/UM.jpg");
		/*File imageFile = new File("res/userManualImages/UM.jpg");
		
		BufferedImage image;
		BufferedImage resizedImage = null;
		try {
			image = ImageIO.read(imageFile);
			resizedImage = resize(image, 260,240);
			Icon = new ImageIcon(resizedImage);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		browsePicture = new JLabel();
		browsePicture.setIcon(Icon);
		
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0,0,60,0);
		informationPanel.add(browsePicture, c);
		
		scrollBox2 = new JScrollPane(informationPanel);
		scrollBox2.getVerticalScrollBar().setUnitIncrement(16);
		// Set the size for the JScrollPane so that everything fits
		
		Dimension d2 = scrollBox2.getPreferredSize();
		d2.width = 400;
		d2.height = 350;
		scrollBox2.setPreferredSize(d2);
		c.insets = new Insets(0,0,0,0);
		c.gridx = 0;
		c.gridy = 0;
		
		container.add(thePanel);
		container.add(scrollBox2);
		frame.add(container);
		frame.pack();
		frame.setVisible(true);
	}
	
	private void gettingStartedClicked(TreeSelectionEvent evt) {
		String node = evt.getNewLeadSelectionPath().getLastPathComponent().toString();
	    if( node.equals("Getting Started") || node.equals("Step 1 - Selecting a File") || node.equals("Step 2 - Editing File") || node.equals("Step 3 - Saving File")) {
	    	int scrollAmount = 0;
	    	
	    	if(node.equals("Step 2 - Editing File")){
	    		scrollAmount = 310;
	    	}
	    	else if(node.equals("Step 3 - Saving File")){
	    		scrollAmount = 970;
	    	}
	    	informationPanel.removeAll();
	    	scrollBox2.getViewport().setViewPosition(new Point(0, scrollAmount));
	    	ImageIcon Icon = CreateImageIcon("/userManualImages/gettingStarted1.jpg");
	       /* ImageIcon Icon = new ImageIcon("res/userManualImages/gettingStarted1.jpg");
			File imageFile = new File("res/userManualImages/gettingStarted1.jpg");
			BufferedImage image;
			BufferedImage resizedImage = null;
			try {
				image = ImageIO.read(imageFile);
				resizedImage = resize(image, 250,250);
				Icon = new ImageIcon(resizedImage);
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			browsePicture.setIcon(Icon);
			c.gridx = 1;
			c.gridy = 1;
			c.insets = new Insets(0,0,60,0);
			informationPanel.add(browsePicture, c);
			
			gettingStartedStep1_0 = new JLabel("<html>Step 1:<br>Select a file by clicking the \"Select Input File\" button<br>located at the top left corner of the program.<html>");
			c.insets = new Insets(10,10,10,10);
			c.gridx = 1;
			c.gridy = 0;
			informationPanel.add(gettingStartedStep1_0, c);
			
			gettingStartedStep2_0 = new JLabel("<html>Step 2:<br>Edit certain fields such as the title or spacing<br>by clicking the edit button or moving the slider.<html>");
			c.gridx = 1;
			c.gridy = 2;
			c.insets = new Insets(-60,10,10,50);
			informationPanel.add(gettingStartedStep2_0, c);
			
			//Adding in the convert picture.
			ImageIcon Icon2 = CreateImageIcon("/userManualImages/gettingStarted2.jpg");
			/*ImageIcon Icon2 = new ImageIcon("res/userManualImages/gettingStarted2.jpg");
			File imageFile2 = new File("res/userManualImages/gettingStarted2.jpg");
			BufferedImage image2;
			BufferedImage resizedImage2 = null;
			try {
				image2 = ImageIO.read(imageFile2);
				resizedImage2 = resize(image2, 250,300);
				Icon2 = new ImageIcon(resizedImage2);
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			convertPicture = new JLabel();
			convertPicture.setIcon(Icon2);
			c.gridx = 1;
			c.gridy = 3;
			c.insets = new Insets(0,0,0,0);
			informationPanel.add(convertPicture, c);
			
			gettingStartedStep4_0 = new JLabel("<html>-The title field will change the title of the PDF.<br>-The subtitle field will change the subtitle of<br> the PDF.<br>-The staff space slider will change the<br>spacing of the staffs.<br>-The staff size slider will change the size of the<br>staffs.<br>The measure space slider will change the<br>spacing between each row of staffs.<br>-The title font size slider will change the font<br>size of the PDF title.<br>-The subtitle font size slider will change the<br>font size of the PDF subtitle.<br>-The left/right margin sliders create a left/right<br>margin.<br>-The page size option changes the type of<br>page size for the PDF.<html>");
			c.gridx = 1;
			c.gridy = 4;
			c.insets = new Insets(10,10,10,10);
			informationPanel.add(gettingStartedStep4_0, c);
			
			
			gettingStartedStep3_0 = new JLabel("<html>Step 3:<br>Save the file by clicking the \"Save As\" button.<html>");
			c.gridx = 1;
			c.gridy = 5;
			c.insets = new Insets(10,10,10,50);
			informationPanel.add(gettingStartedStep3_0, c);
			
			//Adding in the edit picture.
			ImageIcon Icon3 = CreateImageIcon("/userManualImages/gettingStarted3.jpg");
			/*ImageIcon Icon3 = new ImageIcon("res/userManualImages/gettingStarted3.jpg");
			File imageFile3 = new File("res/userManualImages/gettingStarted3.jpg");
			BufferedImage image3;
			BufferedImage resizedImage3 = null;
			try {
				image3 = ImageIO.read(imageFile3);
				resizedImage3 = resize(image3, 250,250);
				Icon3 = new ImageIcon(resizedImage3);
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			editPicture = new JLabel();
			editPicture.setIcon(Icon3);
			c.gridx = 1;
			c.gridy = 6;
			c.insets = new Insets(0,0,100,0);
			informationPanel.add(editPicture, c);
			
			scrollBox2.revalidate();
			frame.revalidate();
			frame.getContentPane().repaint();
	    }
		
	}
	
	private void howToPrintClicked(TreeSelectionEvent evt) {
		String node = evt.getNewLeadSelectionPath().getLastPathComponent().toString();
	    if( node.equals("Printer") || node.equals("How to Print: Step 1") || node.equals("How to Print: Step 2")) {
	    	int scrollAmount = 0;
	    	if(node.equals("How to Print: Step 2")){
	    		scrollAmount = 970;
	    	}
	    	informationPanel.removeAll();
	    	scrollBox2.getViewport().setViewPosition(new Point(0, scrollAmount));
	    	ImageIcon Icon = CreateImageIcon("/userManualImages/print1UM.jpg");
	       /* ImageIcon Icon = new ImageIcon("res/userManualImages/print1UM.jpg");
			File imageFile = new File("res/userManualImages/print1UM.jpg");
			BufferedImage image;
			BufferedImage resizedImage = null;
			try {
				image = ImageIO.read(imageFile);
				resizedImage = resize(image, 250,250);
				Icon = new ImageIcon(resizedImage);
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			browsePicture.setIcon(Icon);
			c.gridx = 1;
			c.gridy = 1;
			c.insets = new Insets(0,0,0,0);
			informationPanel.add(browsePicture, c);
			
			printStep1_0 = new JLabel("<html>Step 1:<br>Select the \"print\" tab located on the top left corner<br> of the menu.<html>");
			c.insets = new Insets(10,10,10,35);
			c.gridx = 1;
			c.gridy = 0;
			informationPanel.add(printStep1_0, c);
			
			printStep2_0 = new JLabel("<html>Step 2:<br>A window will appear, and the only thing left to do<br>is to select the printer you would like to print to.<html>");
			c.gridx = 1;
			c.gridy = 2;
			c.insets = new Insets(30,10,10,50);
			informationPanel.add(printStep2_0, c);
			
			//Adding in the convert picture.
			ImageIcon Icon2 = CreateImageIcon("/userManualImages/print2UM.jpg");
			/*ImageIcon Icon2 = new ImageIcon("res/userManualImages/print2UM.jpg");
			File imageFile2 = new File("res/userManualImages/print2UM.jpg");
			BufferedImage image2;
			BufferedImage resizedImage2 = null;
			try {
				image2 = ImageIO.read(imageFile2);
				resizedImage2 = resize(image2, 250,250);
				Icon2 = new ImageIcon(resizedImage2);
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			JLabel convertPicture = new JLabel();
			convertPicture.setIcon(Icon2);
			c.gridx = 1;
			c.gridy = 3;
			c.insets = new Insets(0,0,0,0);
			informationPanel.add(convertPicture, c);
			
			scrollBox2.revalidate();
			frame.revalidate();
			frame.getContentPane().repaint();
	    }
		
	}
	
	private void howToEmailClicked(TreeSelectionEvent evt) {
		String node = evt.getNewLeadSelectionPath().getLastPathComponent().toString();
	    if( node.equals("Email") || node.equals("Step 1 - Click Email") || node.equals("Step 2 - Login") || node.equals("Step 3 - Fill in Fields") || node.equals("Step 4 (optional) - Add Emails from Address Book")) {
	    	int scrollAmount = 0;
	    	if(node.equals("Step 2 - Login")){
	    		scrollAmount = 340;
	    	}
	    	else if(node.equals("Step 3 - Fill in Fields")){
	    		scrollAmount = 620;
	    	}
	    	else if(node.equals("Step 4 (optional) - Add Emails from Address Book")){
	    		scrollAmount = 1070;
	    	}
	    	informationPanel.removeAll();
	    	scrollBox2.getViewport().setViewPosition(new Point(0, scrollAmount));
	    	ImageIcon Icon = CreateImageIcon("/userManualImages/email0UM.jpg");
	       /* ImageIcon Icon = new ImageIcon("res/userManualImages/email0UM.jpg");
			File imageFile = new File("res/userManualImages/email0UM.jpg");
			BufferedImage image;
			BufferedImage resizedImage = null;
			try {
				image = ImageIO.read(imageFile);
				resizedImage = resize(image, 250,250);
				Icon = new ImageIcon(resizedImage);
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			browsePicture.setIcon(Icon);
			c.gridx = 1;
			c.gridy = 1;
			c.insets = new Insets(0,0,0,0);
			informationPanel.add(browsePicture, c);
			
			emailStep1_0 = new JLabel("<html>Step 1:<br>Select the \"email\" tab located on the top left corner<br> of the menu.<html>");
			c.insets = new Insets(10,10,10,35);
			c.gridx = 1;
			c.gridy = 0;
			informationPanel.add(emailStep1_0, c);
			
			emailStep2_0 = new JLabel("<html>Step 2:<br>Login using your gmail account or if you don't have one<br>then click the \"Don't have gmail?\" box.<html>");
			c.gridx = 1;
			c.gridy = 2;
			c.insets = new Insets(30, 40,10,50);
			informationPanel.add(emailStep2_0, c);
			
			//Adding in the convert picture.
			ImageIcon Icon2 = CreateImageIcon("/userManualImages/email1UM.jpg");
			/*ImageIcon Icon2 = new ImageIcon("res/userManualImages/email1UM.jpg");
			File imageFile2 = new File("res/userManualImages/email1UM.jpg");
			BufferedImage image2;
			BufferedImage resizedImage2 = null;
			try {
				image2 = ImageIO.read(imageFile2);
				resizedImage2 = resize(image2, 350,190);
				Icon2 = new ImageIcon(resizedImage2);
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			JLabel convertPicture = new JLabel();
			convertPicture.setIcon(Icon2);
			c.gridx = 1;
			c.gridy = 3;
			c.insets = new Insets(0,0,0,0);
			informationPanel.add(convertPicture, c);
			
			emailStep3_0 = new JLabel("<html>Step 3:<br>Fill in the fields as specified by the program.<html>");
			c.gridx = 1;
			c.gridy = 4;
			c.insets = new Insets(30,-15,10,50);
			informationPanel.add(emailStep3_0, c);
			
			//Adding in the convert picture.
			ImageIcon Icon3 = CreateImageIcon("/userManualImages/email2UM.jpg");
			/*ImageIcon Icon3 = new ImageIcon("res/userManualImages/email2UM.jpg");
			File imageFile3 = new File("res/userManualImages/email2UM.jpg");
			BufferedImage image3;
			BufferedImage resizedImage3 = null;
			try {
				image3 = ImageIO.read(imageFile3);
				resizedImage3 = resize(image3, 350,300);
				Icon3 = new ImageIcon(resizedImage3);
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			JLabel convertPicture2 = new JLabel();
			convertPicture2.setIcon(Icon3);
			c.gridx = 1;
			c.gridy = 5;
			c.insets = new Insets(0,0,0,0);
			informationPanel.add(convertPicture2, c);
			
			emailStep4_0 = new JLabel("<html>Step 4:<br>Click \"Contacts\" if you wish to add contacts from your<br>address book. Click save when done.<html>");
			c.gridx = 1;
			c.gridy = 6;
			c.insets = new Insets(30,30,10,50);
			informationPanel.add(emailStep4_0, c);
			
			//Adding in the convert picture.
			ImageIcon Icon4 = CreateImageIcon("/userManualImages/email3UM.jpg");
			/*ImageIcon Icon4 = new ImageIcon("res/userManualImages/email3UM.jpg");
			File imageFile4 = new File("res/userManualImages/email3UM.jpg");
			BufferedImage image4;
			BufferedImage resizedImage4 = null;
			try {
				image4 = ImageIO.read(imageFile4);
				resizedImage4 = resize(image4, 250,250);
				Icon4 = new ImageIcon(resizedImage4);
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			JLabel convertPicture3 = new JLabel();
			convertPicture3.setIcon(Icon4);
			c.gridx = 1;
			c.gridy = 7;
			c.insets = new Insets(0,0,0,0);
			informationPanel.add(convertPicture3, c);
			
			scrollBox2.revalidate();
			frame.revalidate();
			frame.getContentPane().repaint();
	    }
		
	}
	
	public static BufferedImage resize(BufferedImage image, int width, int height) {
	    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
	    Graphics2D g2d = (Graphics2D) bi.createGraphics();
	    g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
	    g2d.drawImage(image, 0, 0, width, height, null);
	    g2d.dispose();
	    return bi;
	}
	private DefaultMutableTreeNode addAFile(String fileName, DefaultMutableTreeNode parentFolder){
		
		// Creates a new node for the tree
		DefaultMutableTreeNode newFile = new DefaultMutableTreeNode(fileName);
		
		// Add attaches a name to the node
		
		parentFolder.add(newFile);
		
		// return the new node
		
		return newFile;
		
	}
	
	public void Close() {
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	is_open = false;
		    }
		});
	}
	
	static ImageIcon CreateImageIcon(String path)
	{
		java.net.URL imgURL = View.class.getResource(path);
		if (imgURL != null)
		{
			return new ImageIcon(imgURL);
		} else
		{
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
	
	
}

