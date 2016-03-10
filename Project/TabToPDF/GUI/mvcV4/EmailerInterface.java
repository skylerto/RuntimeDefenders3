package mvcV4;

import javax.print.PrintException;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class EmailerInterface extends JFrame{
	private  JTextField userEmail = new JTextField(20);
	private  JPasswordField userPassword = new JPasswordField(20);
	private  JTextField emailToSendTo = new JTextField(20);
	private  JTextField emailSubject = new JTextField(20);
	private  JTextField browsePath = new JTextField(20);
	private  JTextArea message = new JTextArea(15, 20);
	private  JPanel panel = new JPanel(new GridBagLayout());
	private  JFrame frame = new JFrame("Send PDF - Login");
	private  JFrame contactsFrame = new JFrame("Send PDF - Login");
	private  JFrame frameBrowse = new JFrame("Browse File");
	private  JPanel panelBrowse = new JPanel(new GridBagLayout());
	private  JLabel validating = new JLabel("Validating...");
	private  JCheckBox useDefaultEmail;
	private  boolean isBoxChecked = false;
	private  JLabel invalid1 = new JLabel("Invalid"), invalid2 = new JLabel("Invalid");
	private  GridBagConstraints c = new GridBagConstraints();
	private  getSentMessages getMessages;
	private  ArrayList<String> emailsToSendTo = new ArrayList<String>();
	private  ArrayList<String> emailsToSendToOld = new ArrayList<String>();
	private  String username, password;
	private  boolean check;
	
	private File inputfile = new File("."); /* current directory , but will be updated
	to current directory  after user choose the file*/

	
	public EmailerInterface(){
		
		frame.setSize(500, 250);
		frame.setResizable(false);
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		c.insets = new Insets(10,0,10,10);
		JLabel label0 = new JLabel("Please sign into your gmail account.");
		c.gridx = 1;
		c.gridy = 0;
		panel.add(label0, c);
		
		JLabel label1 = new JLabel("Email Address:");
		c.gridx = 0;
		c.gridy = 1;
		panel.add(label1, c);
		
		c.insets = new Insets(10,0,10,0);
		c.gridx = 1;
		c.gridy = 1;
		panel.add(userEmail, c);
		
		invalid1.setForeground(Color.red);
		invalid1.setVisible(false);
		c.gridx = 2;
		c.gridy = 1;
		panel.add(invalid1, c);
		
		JLabel label2 = new JLabel("Email Password:");
		c.insets = new Insets(10,0,10,10);
		c.gridx = 0;
		c.gridy = 2;
		panel.add(label2, c);
		
		c.insets = new Insets(10,0,10,0);
		c.gridx = 1;
		c.gridy = 2;
		panel.add(userPassword, c);
		
		invalid2.setForeground(Color.red);
		invalid2.setVisible(false);
		c.gridx = 2;
		c.gridy = 2;
		panel.add(invalid2, c);
		
		JButton prev = new JButton("Cancel");
		prev.addActionListener(new cancelButton());
		c.insets = new Insets(50,100,0,0);
		c.gridx = 1;
		c.gridy = 3;
		panel.add(prev, c);
		
		JButton next = new JButton("Login");
		next.addActionListener(new createScreen2());
		c.insets = new Insets(50,0,0,0);
		c.gridx = 2;
		c.gridy = 3;
		panel.add(next, c);
		
		useDefaultEmail = new JCheckBox("Don't have gmail?");
		useDefaultEmail.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e){
				invalid1.setVisible(false);
				invalid2.setVisible(false);
				if(isBoxChecked == false){
					userEmail.setText("cse2311@gmail.com");
					userPassword.setText("cse2311pw");
					userEmail.setEnabled(false);
					userPassword.setEnabled(false);
					isBoxChecked = true;
				}
				else{
					userEmail.setText("");
					userPassword.setText("");
					userEmail.setEnabled(true);
					userPassword.setEnabled(true);
					isBoxChecked = false;
				}
			}
				
		});
		c.insets = new Insets(0,0,0,0);
		c.gridx = 0;
		c.gridy = 3;
		panel.add(useDefaultEmail,c);
		
		
		
		frame.setVisible(true);
	}
	
	public class cancelButton implements ActionListener{
		public void actionPerformed(ActionEvent e){
			frame.dispose();
		}
	}
	
	public class createScreen2 implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			invalid1.setVisible(false);
			invalid2.setVisible(false);
			
			if(!userEmail.getText().contains("@")){
				invalid1.setVisible(true);
			}
			if(userPassword.getText().equals("")){
				invalid2.setVisible(true);
			}
			else{
				getMessages = new getSentMessages(userEmail.getText(), userPassword.getText());
				Thread messages = new Thread(getMessages);
				messages.start();
				while(getMessages.getIsRunning()){
					System.out.print("");
				}
				if(getMessages.wrongPWorEmail){
					invalid1.setVisible(true);
					invalid2.setVisible(true);
					return;
				}
				username = userEmail.getText();
				password = userPassword.getText();
				frame.setSize(500,550);
				panel.removeAll();
				
				c.insets = new Insets(10,0,10,10);
				JLabel labelA = new JLabel("Email to send to: ");
				c.gridx = 0;
				c.gridy = 0;
				panel.add(labelA, c);
				
				c.insets = new Insets(10,0,10,0);
				c.gridx = 1;
				c.gridy = 0;
				panel.add(emailToSendTo, c);
				
				JButton getContacts = new JButton("Contacts");
				getContacts.addActionListener(new openContacts());
				c.insets = new Insets(10,10,10,0);
				c.gridx = 2;
				c.gridy = 0;
				panel.add(getContacts, c);
				
				c.insets = new Insets(10,45,10,10);
				JLabel labelB = new JLabel("Subject: ");
				c.gridx = 0;
				c.gridy = 1;
				panel.add(labelB, c);
				
				c.insets = new Insets(10,0,10,0);
				c.gridx = 1;
				c.gridy = 1;
				panel.add(emailSubject, c);
				
				c.insets = new Insets(10,45,220,10);
				JLabel labelC = new JLabel("Message: ");
				c.gridx = 0;
				c.gridy = 2;
				panel.add(labelC, c);
				
				c.insets = new Insets(10,0,10,0);
				c.gridx = 1;
				c.gridy = 2;
				message.setMaximumSize(new Dimension(15, 20));
				message.setPreferredSize(new Dimension(15,20));
				message.setLineWrap(true);
				message.setWrapStyleWord(true);
				panel.add(message, c);
				
				c.insets = new Insets(10,66,10,10);
				JLabel labelD = new JLabel("File: ");
				c.gridx = 0;
				c.gridy = 3;
				panel.add(labelD, c);
				
				c.insets = new Insets(10,10,10,10);
				c.gridx = 1;
				c.gridy = 3;
				panel.add(browsePath, c);
				
				JButton browse = new JButton("Browse");
				browse.addActionListener(new createScreen3());
				c.insets = new Insets(10,10,10,0);
				c.gridx = 2;
				c.gridy = 3;
				panel.add(browse, c);
				
				JButton prev2 = new JButton("Cancel");
				prev2.addActionListener(new cancelButton());
				c.insets = new Insets(40,147,0,0);
				c.gridx = 1;
				c.gridy = 4;
				panel.add(prev2, c);
				
				JButton next2 = new JButton("Send");
				c.insets = new Insets(40,0,0,0);
				c.gridx = 2;
				c.gridy = 4;
				next2.addActionListener(new sendButton());
				panel.add(next2, c);
				
				frame.revalidate();
				frame.getContentPane().repaint();
				frame.setVisible(true);
			}
			
		}
		
		public class createScreen3 implements ActionListener{
			
			public void actionPerformed(ActionEvent e){
				
				JFileChooser chooseFile = new JFileChooser();
				// saad code
				// set the directory to current directory.
				chooseFile.setCurrentDirectory(new File(Model.globaloutpath));
				// only see pdf document format
				FileTypeFilter pdf_filter = new FileTypeFilter("Portable Document Format *.pdf", new String[]{ ".pdf" });
				chooseFile.addChoosableFileFilter(pdf_filter);
				chooseFile.setFileFilter(pdf_filter);
				chooseFile.setDialogTitle("Select your PDF file to E-mail");
				
				c.gridx = 0;
				c.gridy = 1;
				panelBrowse.add(chooseFile, c);
				int checker = chooseFile.showOpenDialog(null);
				if(checker == JFileChooser.APPROVE_OPTION){
					// after choosing the document , the direcotry will be stored in input file;
					browsePath.setText(chooseFile.getSelectedFile().toString());
					frameBrowse.setVisible(false);
					frameBrowse.dispose();
				}
				
			}
		}
		
		public class saveButton implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(emailToSendTo.getText().equals("") || check){
					emailToSendTo.setText(emailsToSendTo.toString().substring(1, emailsToSendTo.toString().length() - 1));
				}
				else if (!emailsToSendTo.toString().substring(1, emailsToSendTo.toString().length() - 1).equals("")){
					emailToSendTo.setText(emailToSendTo.getText() + ", " + emailsToSendTo.toString().substring(1, emailsToSendTo.toString().length() - 1));
				}
				contactsFrame.dispose();
			
			}
		}
		public class sendButton implements ActionListener{
			public void actionPerformed(ActionEvent e){
				String[] arrayOfEmails = emailToSendTo.getText().split(", ");
				Emailer sendEmail = new Emailer(username, password);
				File f = new File(browsePath.getText());
				for(String x: arrayOfEmails){
					sendEmail.sendEmail(browsePath.getText(), f.getName(), x, emailSubject.getText(), message.getText());
				}
				frame.dispose();
			}
		}
		
		public class cancelButton2 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				contactsFrame.dispose();
			}
		}
		
		public class openContacts implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(emailsToSendTo.toString().substring(1, emailsToSendTo.toString().length() - 1).equals(emailToSendTo.getText())){
					check = true;
				}
				else{
					check = false;
				}
				JPanel contactsPanel = new JPanel(new GridBagLayout());
				contactsFrame.setSize(500, 500);
				contactsFrame.getContentPane().add(contactsPanel, BorderLayout.NORTH);
				ArrayList<JCheckBox> contactCheckBoxes = new ArrayList<JCheckBox>();
				ArrayList<String> usersAddressBook = getMessages.getAddressBook();
				emailsToSendToOld = emailsToSendTo;
				for(int x = 0; x < usersAddressBook.size(); x++){
					c.gridx = 0;
					c.gridy = x;
					c.insets = new Insets(10,10,10,10);
					contactsPanel.add(new JLabel(usersAddressBook.get(x)), c);
					contactCheckBoxes.add(new JCheckBox());
					contactCheckBoxes.get(x).setName(usersAddressBook.get(x));
					final JCheckBox contactBoxNamer = new JCheckBox();
					String contactToAdd = usersAddressBook.get(x);
					if(contactToAdd.contains("<")){
						contactToAdd = contactToAdd.substring(contactToAdd.indexOf("<") + 1,contactToAdd.indexOf(">"));
					}
					contactBoxNamer.setName(contactToAdd);
					contactCheckBoxes.get(x).addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e){
							if(!emailsToSendTo.contains(contactBoxNamer.getName())){
								emailsToSendTo.add(contactBoxNamer.getName());
							}
							else{
								emailsToSendTo.remove(contactBoxNamer.getName());
							}
						}
							
					});
					c.gridx = 1;
					contactsPanel.add(contactCheckBoxes.get(x), c);
					
					
				}
		
				JButton cancelButton = new JButton("Cancel");
				c.gridx = 0;
				c.gridy = usersAddressBook.size()+1;
				cancelButton.addActionListener(new cancelButton2());
				contactsPanel.add(cancelButton, c);
				
				JButton saveButton = new JButton("Save");
				c.gridx = 1;
				c.gridy = usersAddressBook.size()+1;
				saveButton.addActionListener(new saveButton());
				contactsPanel.add(saveButton, c);
				
				final JScrollPane scroll = new JScrollPane(contactsPanel);
		        contactsFrame.setLayout(new BorderLayout());
		        contactsFrame.add(scroll, BorderLayout.CENTER);
				contactsFrame.setVisible(true);
				
			}
		}
		
	}
}



