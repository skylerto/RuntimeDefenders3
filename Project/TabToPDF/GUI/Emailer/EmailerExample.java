package Emailer;

import mvcV4.Emailer;

public class EmailerExample {
	public static void main(String[] args){
		Emailer sendMusicEmail = new Emailer();
		if(sendMusicEmail.sendEmail("outputfiles/musicPDF.pdf", "sentMusicPDF.pdf", "alexander.aolaritei@gmail.com", "This is the subject of the email message2"))
			System.out.println("Email was sent.");
		else
			System.out.println("An error occurred.");
	}
}
