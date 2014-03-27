package tabmain;

import java.io.File;

import tabparts.*;

public class LogMain {
	public static void main(String[] args) {
		
		PDFProperties prop = new PDFProperties();
		
		System.out.println(prop.getTitle());
		System.out.println(prop.getSubtitle());
		System.out.println(prop.getSpacing());
		prop.extractProperties(new File("inputfiles/case01.txt"));
		System.out.println(prop.getTitle());
		System.out.println(prop.getSubtitle());
		System.out.println(prop.getSpacing());
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
		
		System.out.println(s.toString());
		
		s.splitLongMeasures(100);
		
		System.out.println(s.toString());*/
	}
}
