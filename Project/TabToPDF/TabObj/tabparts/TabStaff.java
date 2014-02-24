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
		this.staff = new ArrayList<TabMeasure>();
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
						if (maxmeasure > 0 && REPEAT_START.matcher(this.staff.get(maxmeasure-1).getString(0).toString()).find()) {
							boolean delete = true;
							for (int d = 1; d < TabMeasure.MAX_STRINGS; d++) {
								if (!this.staff.get(maxmeasure-1).getString(d).isEmpty()) delete = false;
							}
							if (delete) {
								this.staff.get(maxmeasure-1).getString(0).copyString(new TabString());
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
						if (!this.staff.get(currentmeasure).isComment()) {
							commentfound = true;
							this.staff.get(currentmeasure).addComment(line);
							//System.out.println("Storing first comment " + line + " in measure " + currentmeasure);
						/* Add any consecutive comment lines */
						} else if (this.staff.get(currentmeasure).isComment()) {
							this.staff.get(currentmeasure).addComment(line);
							//System.out.println("Storing consec comment " + line + " in measure " + currentmeasure);
						} 
					
					/* The line is a valid or partial valid string */
					} else {
						/* Special case if there's no line break between measures */
						if (stringnum >= TabMeasure.MAX_STRINGS) {
							if (maxmeasure > 0 && REPEAT_START.matcher(this.staff.get(maxmeasure-1).getString(0).toString()).find()) {
								boolean delete = true;
								for (int d = 1; d < TabMeasure.MAX_STRINGS; d++) {
									if (!this.staff.get(maxmeasure-1).getString(d).isEmpty()) delete = false;
								}
								if (delete) {
									this.staff.get(maxmeasure-1).getString(0).copyString(new TabString());
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
							this.staff.get(currentmeasure).setString(s, stringnum);
							//System.out.println("Storing " + s.toString() + " in measure " + currentmeasure);
							if (p == line.length() - 2 && 
									TabString.VALID_DB_END.matcher(this.staff.get(currentmeasure).getString(stringnum).toString()).find()) {
								currentmeasure++;
								break;
							} else if (p == line.length() - 3 && 
									TabString.VALID_TB_END.matcher(this.staff.get(currentmeasure).getString(stringnum).toString()).find()) {
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
	
	public void findOneRepeats() {
		for (int a = 0; a < this.staff.size(); a++) {
			if (this.staff.get(a).isComment()) break;
			if (this.staff.get(a).getRepeat() == 0 && TabString.VALID_DB_END.matcher(this.staff.get(a).getString(0).toString()).find() && !TabString.VALID_TB_END.matcher(this.staff.get(a).getString(0).toString()).find())
				this.staff.get(a).setRepeat(1);
		}
	}
	
	public void findRepeats() throws Exception {
		
		for (int a = 0; a < this.staff.size(); a++) {	
			if (this.staff.get(a).isComment()) break;
			if (a == this.staff.size() - 1 && REPEAT_START.matcher(this.staff.get(a).getString(0).toString()).find()) {
				boolean delete = true;
				for (int d = 1; d < TabMeasure.MAX_STRINGS; d++) {
					if (!this.staff.get(a).getString(d).isEmpty()) delete = false;
				}
				if (delete) {
					this.staff.remove(this.size() - 1);
				}
			} else {
				boolean single = true;
				if (REPEAT_END.matcher(this.staff.get(a).getString(0).toString()).find()) {
					for (int i = 1; i < TabMeasure.MAX_STRINGS; i++) {
						if (TabString.VALID_DB_END.matcher(this.staff.get(a).getString(i).toString()).find()) {
							single = false;
							char t = this.staff.get(a).getString(0).getChar(this.staff.get(a).getString(i).size() - 1);
							if (t >= '1' && t <= '9') {
								this.staff.get(a).setRepeat(Character.getNumericValue(t));
								this.staff.get(a).getString(0).replaceChar('|', this.staff.get(a).getString(0).size()-1);
							} else 
								throw new Exception("Error: repeat number is invalid");
							if (a < 30 - 1 && this.staff.get(a+1).getString(0).getChar(0) == '|' && this.staff.get(a+1).getString(0).getChar(1) == t) {
								for (int v = 1; v < TabMeasure.MAX_STRINGS; v++) {
									if (TabString.VALID_DB_START.matcher(this.staff.get(a+1).getString(v).toString()).find()) {
										this.staff.get(a+1).getString(0).replaceChar('|', 1);
									}
								}
							}
							break;
						}				
					}
					if (single)
						this.staff.get(a).getString(0).replaceChar(TabString.NULL_CHAR, this.staff.get(a).getString(0).size()-1);
				}
			}
		}	
	}
	
	public void addMeasure(TabMeasure measure) {
		
	}
	
	public void fixStaff() throws Exception {
		this.findRepeats();
		for (int i = 0; i < this.size(); i++) {
			this.staff.get(i).fixMeasure();
		}
		this.findOneRepeats();
	}
	
	public int size() {
		return this.staff.size();
	}
	
	public void debugStaff() throws Exception {
		this.findRepeats();
		int i;
		for (i = 0; i < 35; i++) {
			if (staff.get(i).isComment()) {
				System.out.println("......................................");
				System.out.println("Measure comment " + i + ": ");
				System.out.println(staff.get(i).toString());
				System.out.println("......................................\n");
			} else {
				System.out.println("......................................");
				System.out.println("Measure " + i + "(" + staff.get(i).getRepeat() + "): ");
				System.out.println(staff.get(i).toString());
				System.out.println("Measure " + i + " fixed errors" + ":");
				staff.get(i).fixStrings();
				System.out.println(staff.get(i).toString());
				System.out.println("Measure " + i + " double bar" + ":");
				staff.get(i).fixStartBar();
				staff.get(i).fixEndBar();
				findOneRepeats();
				System.out.println(staff.get(i).toString());
				System.out.println("Measure " + i + "(" + staff.get(i).getRepeat() +") equalized" + ":");
				staff.get(i).equalizeStrings();
				System.out.println(staff.get(i).toString());
				System.out.println("......................................\n");
			}
		}
	}
	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		int i;
		for (i = 0; i < this.size(); i++) {
			if (staff.get(i).isComment()) {
				buf.append(staff.get(i).getComment() + "\n");
			} else {
				buf.append(staff.get(i).toString() + "\n");
			}
		}
		return buf.toString();
	}
}
