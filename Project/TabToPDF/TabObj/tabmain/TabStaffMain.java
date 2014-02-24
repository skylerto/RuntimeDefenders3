package tabmain;
import java.io.*;

import tabparts.*;

public class TabStaffMain {
	public static void main(String[] args) throws Exception {
		TabStaff staff = new TabStaff();
		
		staff.scanFile(new File("inputfiles/try4.txt"));
		
		staff.debugStaff();
	}
}