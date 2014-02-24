package tabmain;

import version11.*;
import java.io.*;
import java.util.*;

import tabparts.*;

public class TabStaffMain {
	public static void main(String[] args) throws Exception {
		
		TabStaff staff = new TabStaff();
		List<List<String>> list = new ArrayList<List<String>>();
		for (int i = 0 ; i < list.size(); i++){
			
		}
		
		
		staff.scanFile(new File("inputfiles/case1.txt"));
		
		System.out.println(staff.toString());
		System.out.println(staff.getRepeatMap().toString());
		
		
		//staff.deleteComments();
		
		//System.out.println(staff.toString());
		
		//System.out.println(staff.getComment(0) + String.valueOf(staff.getMeasure(0).isComment()));

		//list = staff.getList();
		
		

		/*for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.get(i).size(); j++) {
				System.out.println(list.get(i).get(j));
			}
			System.out.println();
		}*/
	}
}