package tabparts;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class TabStaff {
	
	/* CONSTANTS */
	
	public static final Pattern REPEAT_START = Pattern.compile("^\\s*[|][1-9]\\s*$");	// The pattern of a string that has the repeat number at the start
	public static final Pattern REPEAT_END = Pattern.compile("^\\s*\\S+\\s*[|][1-9]\\s*$");	// The string that has a repeat number at the end
	
	/* ATTRIBUTES */
	
	private List<TabMeasure> staff;
	
	public TabStaff() {
		staff = new ArrayList<TabMeasure>();
	}
	
	public void scanFile(File file) {
		File input = file;
		BufferedReader stream;
		String line;

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
						if (maxmeasure > 0 && REPEAT_START.matcher(staff.get(maxmeasure-1).getString(0).toString()).find()) {
							boolean delete = true;
							for (int d = 1; d < TabMeasure.MAX_STRINGS; d++) {
								if (!staff.get(maxmeasure-1).getString(d).isEmpty()) delete = false;
							}
							if (delete) {
								staff.get(maxmeasure-1).getString(0).copyString(new TabString());
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
						if (!staff.get(currentmeasure).isComment()) {
							commentfound = true;
							staff.get(currentmeasure).addComment(line);
							//System.out.println("Storing first comment " + line + " in measure " + currentmeasure);
						/* Add any consecutive comment lines */
						} else if (staff.get(currentmeasure).isComment()) {
							staff.get(currentmeasure).addComment(line);
							//System.out.println("Storing consec comment " + line + " in measure " + currentmeasure);
						} 
					
					/* The line is a valid or partial valid string */
					} else {
						/* Special case if there's no line break between measures */
						if (stringnum >= TabMeasure.MAX_STRINGS) {
							if (maxmeasure > 0 && REPEAT_START.matcher(staff.get(maxmeasure-1).getString(0).toString()).find()) {
								boolean delete = true;
								for (int d = 1; d < TabMeasure.MAX_STRINGS; d++) {
									if (!staff.get(maxmeasure-1).getString(d).isEmpty()) delete = false;
								}
								if (delete) {
									staff.get(maxmeasure-1).getString(0).copyString(new TabString());
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
							staff.get(currentmeasure).setString(s, stringnum);
							//System.out.println("Storing " + s.toString() + " in measure " + currentmeasure);
							if (p == line.length() - 2 && 
									TabString.VALID_DB_END.matcher(staff.get(currentmeasure).getString(stringnum).toString()).find()) {
								currentmeasure++;
								break;
							} else if (p == line.length() - 3 && 
									TabString.VALID_TB_END.matcher(staff.get(currentmeasure).getString(stringnum).toString()).find()) {
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
	}
	
	public void addMeasure(TabMeasure measure) {
		
	}
	
	public void findRepeats() {
		
	}
	
	public int size() {
		return staff.size();
	}
}
