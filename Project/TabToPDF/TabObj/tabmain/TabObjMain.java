package tabmain;
import tabparts.*;

import java.io.*;
import java.util.*;

public class TabObjMain {

	public static void main(String[] args) {
		int i, j, k;
		TabMeasure measure[];
		TabString s;
		Random gen = new Random();
		
		
		File input = new File("inputfiles/try4.txt");
		BufferedReader stream;
		measure = new TabMeasure[30];
		String line;
		for (k = 0; k < 30; k++)
			measure[k] = new TabMeasure();
		try {
			stream = new BufferedReader(new FileReader(input));
			boolean emptyfound = false;
			int basemeasure = 0;
			int currentmeasure = 0;
			int maxmeasure = 0;
			int stringnum = 0;
			while ((line = stream.readLine()) != null) {
				line = line.replaceAll("\\s+$", "");
				if (line.isEmpty()) {
					if (!emptyfound) basemeasure = maxmeasure;
					emptyfound = true;
					stringnum = 0;
				} else {
					if (stringnum >= TabMeasure.MAX_STRINGS) {
						stringnum = 0;
						basemeasure = maxmeasure;
					}
					emptyfound = false;
					currentmeasure = basemeasure;
					inner:
					for (int p = 0; p < line.length(); currentmeasure++) {
						s = new TabString();
						p = s.scanLine(line, p);
						measure[currentmeasure].setString(s, stringnum);
						if (p == line.length() - 1) {
							currentmeasure++;
							break;
						}
					}
					if (currentmeasure > maxmeasure)
						maxmeasure = currentmeasure;
					stringnum++;
				}
			}
			stream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (i = 0; i < 6; i++) {
			System.out.println("......................................");
			System.out.println("Measure " + i + ":");
			System.out.println(measure[i].toString());
			System.out.println("Measure " + i + " fixed errors" + ":");
			measure[i].fixStrings();
			System.out.println(measure[i].toString());
			System.out.println("Measure " + i + " equalized" + ":");
			measure[i].equalizeStrings();
			System.out.println(measure[i].toString());
			System.out.println("......................................");
		}
		
		
		
		
		
		
		/*String input[] = {" |",
						  " |a ",
						  " bbb",
						  "      9----0----9-----5-10-|",
						  "   ---5-5----5-----2---- ",
						  " |--- ---3---    ---    "};
		
		
		measure = new TabMeasure();
		for (j = 0; j < input.length; j++) {
			string = new TabString();
			for (i = 0; i < input[j].length(); i++) {
				string.addChar(input[j].charAt(i));
			}
			measure.addString(string);
		}
		System.out.println(measure.toString());
		measure.fixMeasure();
		System.out.println();
		System.out.println(measure.toString());*/

		
		
		/*for (int p = 0; p < TabSheet.MAX_STAFFS; p++) {
			staff = new TabStaff();
			for (int k = 0; k < TabStaff.MAX_MEASURES; k++) {
				measure = new TabMeasure(6);
				for (int j = 0; j < TabMeasure.MAX_STRINGS; j++) {
					string = new TabString(gen.nextInt(5));
					for (int i = 0; i < 4; i++) {
						string.addNote(new TabSymbol((gen.nextInt(7)), (gen.nextInt(7)+1)));
					}
					measure.addString(string, true);
				}
				staff.addMeasure(measure);
			}
			sheet.addStaff(staff);
		}
		System.out.println(sheet.toString());*/
		
/*		String input = " adwa   dd ";
		string = new TabString();
		
		for (int i = 0; i < input.length(); i++) {
			string.addChar(input.charAt(i));
		}
		
		System.out.println(string.toString() + " size=" + string.size());
		System.out.println("error: " + string.checkError());
		
		System.out.println(string.fixErrors());
		System.out.println(string.toString() + " size=" + string.size());*/
	}

}
