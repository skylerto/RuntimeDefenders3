package tabmain;
import tabparts.*;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import com.itextpdf.text.pdf.spatial.Measure;

// Handle comments between measures with no empty lines

public class TabObjMain {

	public static final Pattern REPEAT_START = Pattern.compile("^\\s*[|][1-9]\\s*$");	// The pattern of a string that has the repeat number at the start
	public static final Pattern REPEAT_END = Pattern.compile("^\\s*\\S+\\s*[|][1-9]\\s*$");	// The string that has a repeat number at the end
	static TabMeasure measure[] = new TabMeasure[200];
	public static void main(String[] args) throws Exception {
		int i, j, k;
		
		Random gen = new Random();
		
		/*String st = "||-||";
		
		System.out.println(String.valueOf(TabString.TRUE_VALID_STRING.matcher(st).find()));
		System.out.println(String.valueOf(TabString.TRUE_VALID_DB_STRING.matcher(st).find()));
		
		s = new TabString();
		
		//s.addChar('a');
		
		//s.addChar(' ');
		s.addChar('|');
		s.addChar('|');
		//s.addChar(' ');
		s.addChar('a');
		s.addChar('|');
		//s.addChar('|');
		//s.addChar(' ');
		

		
		System.out.println(s.checkError());
		System.out.println(s.toString());
		System.out.println(s.fixErrors());
		System.out.println(s.toString());
		s.addDash(5);
		System.out.println(s.toString());*/
		
		File input = new File("inputfiles/try4.txt");
		BufferedReader stream;
		
		String line;
		
		for (k = 0; k < 200; k++)
			measure[k] = new TabMeasure();
		try {
			stream = new BufferedReader(new FileReader(input));
			TabString s;
			TabString temp;
			boolean commentfound = false;
			boolean emptyfound = false;
			int basemeasure = 0;
			int currentmeasure = 0;
			int maxmeasure = 0;
			int stringnum = 0;
			while ((line = stream.readLine()) != null) {
				line = line.replaceAll("\\s+$", "");
				/* If line is empty */
				if (line.isEmpty()) {
					if (!emptyfound) {
						if (maxmeasure > 0 && REPEAT_START.matcher(measure[maxmeasure-1].getString(0).toString()).find()) {
							boolean delete = true;
							for (int d = 1; d < TabMeasure.MAX_STRINGS; d++) {
								if (!measure[maxmeasure-1].getString(d).isEmpty()) delete = false;
							}
							if (delete) {
								measure[maxmeasure-1].getString(0).copyString(new TabString());
								maxmeasure = maxmeasure - 1;
							}
						}
						if (commentfound) {
							currentmeasure++;
							maxmeasure = currentmeasure;
							commentfound = false;
						}
						basemeasure = maxmeasure;
						stringnum = 0;
					}
					emptyfound = true;
					//System.out.println("empty found. base=" + basemeasure + " curr=" + currentmeasure + " max=" + maxmeasure + " cfound=" + String.valueOf(commentfound));
				/* If the line isn't empty */
				} else {
					emptyfound = false;
					temp = new TabString();
					temp.scanLine(line, 0, false);
					/* Check if line is a comment */
					if (temp.checkError() == TabString.ERROR_COMMENT) {
						/* If the first line of the measure is a comment then add it as a comment */
						if (!measure[currentmeasure].isComment()) {
							commentfound = true;
							measure[currentmeasure].addComment(line);
							//System.out.println("Storing first comment " + line + " in measure " + currentmeasure);
						/* Add any consecutive comment lines */
						} else if (measure[currentmeasure].isComment()) {
							measure[currentmeasure].addComment(line);
							//System.out.println("Storing consec comment " + line + " in measure " + currentmeasure);
						} 
					
					/* The line is a valid or partial valid string */
					} else {
						/* Special case if there's no line break between measures */
						if (stringnum >= TabMeasure.MAX_STRINGS) {
							if (maxmeasure > 0 && REPEAT_START.matcher(measure[maxmeasure-1].getString(0).toString()).find()) {
								boolean delete = true;
								for (int d = 1; d < TabMeasure.MAX_STRINGS; d++) {
									if (!measure[maxmeasure-1].getString(d).isEmpty()) delete = false;
								}
								if (delete) {
									measure[maxmeasure-1].getString(0).copyString(new TabString());
									maxmeasure = maxmeasure - 1;
								}
							}
							stringnum = 0;
							basemeasure = maxmeasure;
						}
						currentmeasure = basemeasure;
						
						for (int p = 0; p < line.length(); currentmeasure++) {
							s = new TabString();
							p = s.scanLine(line, p, stringnum == 0);
							measure[currentmeasure].setString(s, stringnum);
							//System.out.println("Storing " + s.toString() + " in measure " + currentmeasure);
							if (p == line.length() - 2 && 
									TabString.VALID_DB_END.matcher(measure[currentmeasure].getString(stringnum).toString()).find()) {
								currentmeasure++;
								break;
							} else if (p == line.length() - 3 && 
									TabString.VALID_TB_END.matcher(measure[currentmeasure].getString(stringnum).toString()).find()) {
								currentmeasure++;
								break;
							} else if (p == line.length() - 1) { 
								currentmeasure++;
								break;
							}		
						}
						if (currentmeasure > maxmeasure)
							maxmeasure = currentmeasure;
						stringnum++;
					}
				} 
			}
			stream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*findRepeats();
		for (i = 0; i < 35; i++) {
			System.out.println("Measure " + i + " (repeats=" + measure[i].getRepeat() + "):");
			measure[i].fixMeasure();
			System.out.println(measure[i].toString());
		}*/
		
		findRepeats();
		
		for (i = 0; i < 35; i++) {
			if (measure[i].isComment()) {
				System.out.println("......................................");
				System.out.println("Measure comment " + i + ": ");
				System.out.println(measure[i].toString());
				System.out.println("......................................\n");
			} else {
				System.out.println("......................................");
				System.out.println("Measure " + i + "(" + measure[i].getRepeat() + "): ");
				System.out.println(measure[i].toString());
				System.out.println("Measure " + i + " fixed errors" + ":");
				measure[i].fixStrings();
				System.out.println(measure[i].toString());
				System.out.println("Measure " + i + " double bar" + ":");
				measure[i].fixStartBar();
				measure[i].fixEndBar();
				findOneRepeats();
				System.out.println(measure[i].toString());
				System.out.println("Measure " + i + "(" + measure[i].getRepeat() +") equalized" + ":");
				measure[i].equalizeStrings();
				System.out.println(measure[i].toString());
				System.out.println("......................................\n");
			}
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

	static public void findOneRepeats() {
		for (int a = 0; a < 32; a++) {
			if (measure[a].isComment()) break;
			if (measure[a].getRepeat() == 0 && TabString.VALID_DB_END.matcher(measure[a].getString(0).toString()).find() && !TabString.VALID_TB_END.matcher(measure[a].getString(0).toString()).find())
				measure[a].setRepeat(1);
		}
	}
	static public void findRepeats() throws Exception {
		
		for (int a = 0; a < 32; a++) {	
			if (measure[a].isComment()) break;
			if (a == 32 - 1 && REPEAT_START.matcher(measure[a].getString(0).toString()).find()) {
				boolean delete = true;
				for (int d = 1; d < TabMeasure.MAX_STRINGS; d++) {
					if (!measure[a].getString(d).isEmpty()) delete = false;
				}
				if (delete) {
					measure[a].getString(0).copyString(new TabString());
					//size--;
				}
			} else {
				boolean single = true;
				if (REPEAT_END.matcher(measure[a].getString(0).toString()).find()) {
					for (int i = 1; i < TabMeasure.MAX_STRINGS; i++) {
						if (TabString.VALID_DB_END.matcher(measure[a].getString(i).toString()).find()) {
							single = false;
							char t = measure[a].getString(0).getChar(measure[a].getString(i).size() - 1);
							if (t >= '1' && t <= '9') {
								measure[a].setRepeat(Character.getNumericValue(t));
								measure[a].getString(0).replaceChar('|', measure[a].getString(0).size()-1);
							} else 
								throw new Exception("Error: repeat number is invalid");
							if (a < 30 - 1 && measure[a+1].getString(0).getChar(0) == '|' && measure[a+1].getString(0).getChar(1) == t) {
								for (int v = 1; v < TabMeasure.MAX_STRINGS; v++) {
									if (TabString.VALID_DB_START.matcher(measure[a+1].getString(v).toString()).find()) {
										measure[a+1].getString(0).replaceChar('|', 1);
									}
								}
							}
							break;
						}				
					}
					if (single)
						measure[a].getString(0).replaceChar(TabString.NULL_CHAR, measure[a].getString(0).size()-1);
				}
			}
		}	
	}
}
