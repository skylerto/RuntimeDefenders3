package mvcV4;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;


public class getSentMessages implements Runnable{
	ArrayList<String> addressBook = new ArrayList<String>();
	boolean wrongPWorEmail = false, isRunning = false, isRunningFirstTime = true;
	Properties props;
	String email, password;
	public getSentMessages(String email, String password){
	    this.email = email;
	    this.password = password;
	    this.isRunning = true;
	}
	public void run(){
		try {
			props = new Properties();

		    props.setProperty("mail.store.protocol", "imaps");
	        Session session = Session.getInstance(props, null);
	        Store store = session.getStore();
	        store.connect("imap.gmail.com", email, password);
	        Folder[] folderList = store.getFolder("[Gmail]").list();
	       
	        Folder inbox = store.getFolder("[Gmail]/Sent Mail");
	        inbox.open(Folder.READ_ONLY);
	        for (int x = inbox.getMessageCount(); x >0; x--){
		        Message msg = inbox.getMessage(x);
		        Address[] in = msg.getRecipients(Message.RecipientType.TO);
		        for (Address address : in) {
		        	if(!addressBook.contains(address.toString())){
		        		addressBook.add(address.toString());
		        	}
		        }
	        }
	    } catch (Exception mex) {
	        this.wrongPWorEmail = true;
	    }
		this.isRunning = false;
	}
	
	public boolean isRunningFirstTime() {
		return isRunningFirstTime;
	}
	public void setRunningFirstTime(boolean isRunningFirstTime) {
		this.isRunningFirstTime = isRunningFirstTime;
	}
	public ArrayList<String> getAddressBook(){
		return this.addressBook;
	}
	public boolean getIsRunning(){
		return this.isRunning;
	}
}


