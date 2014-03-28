package tabmain;

import java.io.File;
import java.util.regex.Pattern;

import tabparts.*;
import version13.CannotReadFileException;
import version13.ConversionException;
import version13.EmptyFileException;
import version13.NoFileExistsException;
import version13.NoMusicException;
import version13.TextToPDFv13;

public class LogMain {
	public static void main(String[] args) {
		
		try {
			TextToPDFv13 convert = new TextToPDFv13("outputfiles/musicPDF.pdf", "inputfiles/try2.txt");
			convert.WriteToPDF();
			//System.out.println(convert.getProperties().toString());
		} catch (NoFileExistsException | CannotReadFileException
				| EmptyFileException | NoMusicException | LargeNumberException | ConversionException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}
		
		/*Pattern music = Pattern.compile("([|][^\\s]+)|([^\\s]+[|])");
		
		System.out.println(String.valueOf((music.matcher("--|--")).find()));*/
		
		/*PDFProperties prop = new PDFProperties();
		
		System.out.println(prop.getTitle());
		System.out.println(prop.getSubtitle());
		System.out.println(prop.getSpacing());
		prop.extractProperties(new File("inputfiles/case01.txt"));
		System.out.println(prop.getTitle());
		System.out.println(prop.getSubtitle());
		System.out.println(prop.getSpacing());*/
		/*AutofixLog log = new AutofixLog();
		log.write("huh");
		log.writeNL("new line!");
		log.close();*/
		
		/*TabStaff s = new TabStaff();
		try {
			s.scanFile(new File("inputfiles/tabtester.txt"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(s.toString());*/
		
	}
}
