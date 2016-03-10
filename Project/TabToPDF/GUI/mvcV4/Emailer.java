package mvcV4;

import java.util.Properties;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Emailer {
	Session session;
	public Emailer(String usernameInput, String passwordInput){
		final String username;
		final String password;
		if(usernameInput.equals("") && passwordInput.equals("")){
			username = "cse2311@gmail.com";
		    password = "cse2311pw";
		}
		else{
			username = usernameInput;
			password = passwordInput;
		}
	    Properties props = new Properties();
	    props.put("mail.smtp.auth", true);
	    props.put("mail.smtp.starttls.enable", true);
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.port", "587");

	    session = Session.getInstance(props,
	            new javax.mail.Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(username, password);
	                }
	            });
	    
	}
	
	public boolean sendEmail(String fileDirectory, String newFileName, String emailToSendTo, String emailSubject, String emailBody){
		try {
			Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress("cse2311@gmail.com"));
	        message.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse(emailToSendTo));
	        message.setSubject(emailSubject);

	        MimeBodyPart messageBodyPart = new MimeBodyPart();

	        Multipart multipart = new MimeMultipart();

	        messageBodyPart = new MimeBodyPart();
	        String file = "outputfiles/musicPDF.pdf";
	        String fileName = "musicPDFSentFile.pdf";
	        DataSource source = new FileDataSource(fileDirectory);
	        messageBodyPart.setDataHandler(new DataHandler(source));
	        messageBodyPart.setFileName(newFileName);
	        //messageBodyPart.setText(emailBody);
	        multipart.addBodyPart(messageBodyPart);
	        message.setContent(multipart);
	        Transport.send(message);
	        return true;
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
		return false;
	}
	
}


