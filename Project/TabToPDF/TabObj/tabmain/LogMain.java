package tabmain;

import java.io.File;

import tabparts.*;

public class LogMain {
	public static void main(String[] args) {
		/*AutofixLog log = new AutofixLog();
		log.write("huh");
		log.writeNL("new line!");
		log.close();*/
		
		TabStaff s = new TabStaff();
		try {
			s.scanFile(new File("inputfiles/tabtester.txt"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(s.toString());
		
		s.splitLongMeasures(100);
		
		System.out.println(s.toString());
	}
}
