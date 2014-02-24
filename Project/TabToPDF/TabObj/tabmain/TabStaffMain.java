package tabmain;
import java.io.*;
import java.util.*;

import tabparts.*;

public class TabStaffMain {
	public static void main(String[] args) throws Exception {
		TabStaff staff = new TabStaff();
		List<List<String>> list;
		
		staff.scanFile(new File("inputfiles/try4.txt"));

		list = staff.getList();

		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.get(i).size(); j++) {
				System.out.println(list.get(i).get(j));
			}
			System.out.println();
		}
	}
}