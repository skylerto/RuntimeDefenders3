package tabmain;

import tabparts.*;

public class LogMain {
	public static void main(String[] args) {
		AutofixLog log = new AutofixLog();
		log.write("huh");
		log.writeNL("new line!");
		log.close();
	}
}
