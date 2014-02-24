package tabparts;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

// Still have to account for comments and strings with no empty lines inbetween them
// Still have to convert comments inside of measures to strings.. maybe..
// Questions:
// 1) Do you want jibberish chars inside of valid strings?

/**
 * A TabStaff contains a list of TabMeasures. TabStaff can read in tab strings
 * from a text file and stores them in the list. Can fix errors and detects
 * repetition numbers.
 * 
 *
 */

public class TabStaff {
	
	/* CONSTANTS */
	
	public static final int MAX_SIZE = 1000;
	public static final Pattern REPEAT_START = Pattern.compile("^\\s*[|][1-9]\\s*$");	// The pattern of a string that has the repeat number at the start
	public static final Pattern REPEAT_END = Pattern.compile("^\\s*\\S+\\s*[|][1-9]\\s*$");	// The string that has a repeat number at the end
	
	/* ATTRIBUTES */
	
	private List<TabMeasure> staff;		// Collection of TabMeasures
	
	/**
	 * Creates an empty TabStaff filled with a set number of empty
	 * TabMeasures.
	 */
	public TabStaff() {
		this.staff = new ArrayList<TabMeasure>();
		for (int i = 0; i < MAX_SIZE; i++)
			this.staff.add(new TabMeasure());
	}
	
	/**
	 * Reads from the given file and stores TabStrings into TabMeasures in the list.
	 * Any line that isn't recognized as a valid TabString is considered to be a comment
	 * and still added as a measure but in a separate comment attribute.
	 * 
	 * A full TabMeasure will read in 6 lines. If not all 6 lines are read in,
	 * the empty ones are filled with blank TabStrings. A block of comment
	 * is recognized either at the beginning of the file, after an empty line,
	 * or after 6 lines of a TabMeasure.
	 * 
	 * Any lines that are recognized as comments but within the 6 lines of a measure
	 * (not including the first string) are considered valid strings that need fixing.
	 * 
	 * When a row of measures is encountered, it reads the first string of each measure first
	 * before moving to the second string of each measure.
	 * 
	 * A row of 3 measures looks like:
	 * 
	 * |---2----1--|---6----2--|---0-0|
	 * |---2----1--|---6----2--|---0-0|
	 * |---2----1--|---6----2--|---0-0|
	 * |---2----1--|---6----2--|---0-0|
	 * |---2----1--|---6----2--|---0-0|
	 * 
	 * A block comment would be like:
	 * 
	 * Title: My Song
	 * Author: Ron
	 * Note: Blah blah blah blah
	 * blah blah
	 *
	 * @param file	The file to read the input data from.
	 */
	public void scanFile(File file) {
		File input = file;
		BufferedReader stream;
		String line;			// A line read from the input file

		try {
			stream = new BufferedReader(new FileReader(input));
			TabString s;		// A string to be stored in a measure
			TabString temp;		// Used to differentiate between comments and strings
			boolean commentfound = false;	// True while reading through a comment block
			boolean emptyfound = false;		// True when an empty line is found
			int basemeasure = 0;			// The first measure of a row of measures
			int currentmeasure = 0;			// Represents the total number of measures in the staff
			int maxmeasure = 0;				// The max measure seen in a row of measures
			int stringnum = 0;			// The string of the current measure
			
			/* If we not at the end of the file, then read a line and process it */
			while ((line = stream.readLine()) != null) {
				line = line.replaceAll("\\s+$", "");	// Removes trailing blank spaces
				
				/* If line is empty then process index variables */
				if (line.isEmpty()) {
					
					/* Runs for one empty line found, but consecutive empty lines are ignored */
					if (!emptyfound) {
						/* Special case that detects if the start of the measure contains a repeat number that needs to be deleted */
						if (maxmeasure > 0 && REPEAT_START.matcher(this.staff.get(maxmeasure-1).getString(0).toString()).find()) {
							boolean delete = true;
							/* Will delete if the other 5 lines are empty */
							for (int d = 1; d < TabMeasure.MAX_STRINGS; d++) {
								if (!this.staff.get(maxmeasure-1).getString(d).isEmpty()) delete = false;
							}
							if (delete) {
								this.staff.get(maxmeasure-1).getString(0).copyString(new TabString());
								maxmeasure = maxmeasure - 1;
							}
						}
						
						/* If a block comment was found previously, then increment current measure */
						if (commentfound) {
							currentmeasure++;
							maxmeasure = currentmeasure;	// The max number of measures seen is equal to the current measure
							commentfound = false;
						}
						basemeasure = maxmeasure;	// basemeasure, currentmeasure, and maxmeasure are all equal before reading the next strings */
						stringnum = 0;				// Reset the string back to 0
					}
					emptyfound = true;
					//System.out.println("empty found. base=" + basemeasure + " curr=" + currentmeasure + " max=" + maxmeasure + " cfound=" + String.valueOf(commentfound));
				
				/* The line is either a comment or a valid/partial valid tab string */
				} else {
					emptyfound = false;
					temp = new TabString();
					temp.scanLine(line, 0, false);
					
					/* If the line is a comment */
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
					
					/* If the line is a valid or partial valid string */
					} else {
						/* If there's no line break after 6 strings have been read, go to the next measure */
						if (stringnum >= TabMeasure.MAX_STRINGS) {
							
							/* Special case that detects if the start of the measure contains a repeat number that needs to be deleted */
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
						
						/* Reads through a row of measures and stores strings, starting with the first string of each measure */
						/* Each string in a line will create a new measure */
						for (int p = 0; p < line.length(); currentmeasure++) {
							s = new TabString();
							p = s.scanLine(line, p, stringnum == 0);	// Stores the string in 's' and 'p' is the char index where it left off in the line
							this.staff.get(currentmeasure).setString(s, stringnum);	// Stores the string in the staff's measure
							//System.out.println("Storing " + s.toString() + " in measure " + currentmeasure);
							
							/* Break from the loop if a double barred end is detected */
							if (p == line.length() - 2 && 
									TabString.VALID_DB_END.matcher(this.staff.get(currentmeasure).getString(stringnum).toString()).find()) {
								currentmeasure++;
								break;
								
							/* Break from the loop if a triple barred end is detected */
							} else if (p == line.length() - 3 && 
									TabString.VALID_TB_END.matcher(this.staff.get(currentmeasure).getString(stringnum).toString()).find()) {
								currentmeasure++;
								break;
								
							/* Break from the loop if at the end of the line */
							} else if (p == line.length() - 1) { 
								currentmeasure++;
								break;
							}		
						}
						/* Increases maxmeasure when more measures are detected in the row */
						if (currentmeasure > maxmeasure)
							maxmeasure = currentmeasure;
						stringnum++;
					}
				} 
			}
			stream.close();
			
			/* Fix errors in the staff and store repeat numbers */
			this.fixStaff();
			
			
			//this.debugStaff();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns a 2-dimentional array list of strings. The outer list is the collection
	 * of the staff's TabMeasures in text form. For each TabMeasure (inner list) is
	 * the text of each TabString.
	 * 
	 * @return	The list of strings.
	 */
	public List<List<String>> getList() {
		List<List<String>> list = new ArrayList<List<String>>();
		List<String> inner;
		if (this.size() == 0) return list;
		for (int i = 0 ; i < this.size(); i++) {
			inner = new ArrayList<String>();
			if (this.staff.get(i).isComment()) {
				inner.add(this.getComment(i));
			} else {
				for (int j = 0; j < TabMeasure.MAX_STRINGS; j++)
					inner.add(this.getStringText(i,j));
			}
			list.add(inner);
		}
		return list;
	}
	
	/**
	 * Gets the text of the TabMeasure's Tabstring with the given
	 * indexes.
	 * 
	 * @param measureindex	The index of the TabMeasure in the list
	 * @param stringindex	The index of the TabString in the TabMeasure
	 * @return	The text of the TabString
	 */
	public String getStringText(int measureindex, int stringindex) {
		return this.staff.get(measureindex).getStringText(stringindex);
	}
	
	/**
	 * Gets the text of the TabMeasure's comment given its index.
	 * 
	 * @param measureindex	The index of the TabMeasure in the list
	 * @return	The TabMeasure's comment
	 */
	public String getComment(int measureindex) {
		return this.staff.get(measureindex).getComment();
	}
	
	/**
	 * Adds a measure to the list.
	 */
	public void addMeasure() {
		this.staff.add(new TabMeasure());
	}
	
	/**
	 * Removes all empty TabMeasures from the list.
	 */
	public void removeEmpty() {
		if (this.size() > 0) {
			for (int i = this.size() - 1; i >= 0; i--) {
				if (this.staff.get(i).isEmpty())
					this.staff.remove(i);
			}
		}
	}

	/**
	 * Fixes errors for each TabMeasure. In addition, removes empty measures
	 * and stores and fixes repeating numbers.
	 * 
	 * @throws Exception
	 */
	public void fixStaff() throws Exception {
		this.removeEmpty();
		this.findRepeats();
		this.fixEnd();
		for (int i = 0; i < this.size(); i++) {
			this.staff.get(i).fixStrings();
			this.staff.get(i).fixStartBar();
			this.staff.get(i).fixEndBar();
			this.staff.get(i).equalizeStrings();
		}
		this.findOneRepeats();
	}
	
	/**
	 * A special fix where the end measure contains a repeat number that needs to be deleted.
	 */
	public void fixEnd() {
		if (!this.staff.get(this.size()-1).isComment()) {
			if (REPEAT_START.matcher(this.staff.get(this.size()-1).getString(0).toString()).find()) {
				boolean delete = true;
				for (int d = 1; d < TabMeasure.MAX_STRINGS; d++) {
					if (!this.staff.get(this.size()-1).getString(d).isEmpty()) delete = false;
				}
				if (delete) {
					this.staff.remove(this.size() - 1);
				}
			}
		}
	}
	
	/**
	 * Finds any measure that ends in double bars and sets its repeat number to 1.
	 */
	public void findOneRepeats() {
		for (int a = 0; a < this.staff.size(); a++) {
			if (!this.staff.get(a).isComment()) {
				if (this.staff.get(a).getRepeat() == 0 && TabString.VALID_DB_END.matcher(this.staff.get(a).getString(0).toString()).find() && !TabString.VALID_TB_END.matcher(this.staff.get(a).getString(0).toString()).find())
					this.staff.get(a).setRepeat(1);
			}
		}
	}
	
	/**
	 * Finds measures with repeat numbers greater than 1 and stores it in the measure's
	 * attributes. The repeat number is then replaced with a '|' or deleted to keep
	 * the measure valid.
	 * 
	 * @throws Exception	When the repeat number is a character not between 1-9
	 */
	public void findRepeats() throws Exception {
		for (int a = 0; a < this.size(); a++) {	
			if (!this.staff.get(a).isComment()) {
				boolean single = true;
				if (REPEAT_END.matcher(this.staff.get(a).getString(0).toString()).find()) {
					for (int i = 1; i < TabMeasure.MAX_STRINGS; i++) {
						if (TabString.VALID_DB_END.matcher(this.staff.get(a).getString(i).toString()).find()) {
							single = false;
							char t = this.staff.get(a).getString(0).getChar(this.staff.get(a).getString(0).size() - 1);
							if (t >= '1' && t <= '9') {
								this.staff.get(a).setRepeat(Character.getNumericValue(t));
								this.staff.get(a).getString(0).replaceChar('|', this.staff.get(a).getString(0).size()-1);
							} else 
								throw new Exception("Error: repeat number is invalid " + this.staff.get(a).getString(0).toString());
							if (a < this.size() - 1 && this.staff.get(a+1).getString(0).getChar(0) == '|' && this.staff.get(a+1).getString(0).getChar(1) == t) {
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

	/**
	 * Returns the size of the list.
	 * @return
	 */
	public int size() {
		return this.staff.size();
	}
	
	/**
	 * Used to test the staff.
	 * 
	 * @throws Exception
	 */
	public void debugStaff() throws Exception {
		int i;
		this.findRepeats();
		for (i = 0; i < this.size(); i++) {
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
				this.removeEmpty();
				this.fixEnd();
				System.out.println(staff.get(i).toString());
				System.out.println("Measure " + i + "(" + staff.get(i).getRepeat() +") equalized" + ":");
				staff.get(i).equalizeStrings();
				System.out.println(staff.get(i).toString());
				System.out.println("......................................\n");
			}
		}
		System.out.println(this.size());
	}
	
	/**
	 * A string representation of the staff.
	 * Example output of a staff of size 3:
	 * 
	 * Title = My Song
	 * By = Ron
	 * 
	 * |---22-|
	 * |---22-|
	 * |---22-|
	 * |---22-|
	 * |---22-|
	 * |---22-|
	 * 
	 * |1---3-|||
	 * |1---3-|||
	 * |1---3-|||
	 * |1---3-|||
	 * |1---3-|||
	 * |1---3-|||
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer();
		int i;
		for (i = 0; i < this.size(); i++) {
			if (staff.get(i).isComment()) {
				buf.append(staff.get(i).getComment() + "\n\n");
			} else {
				buf.append("repeats(" + staff.get(i).getRepeat() + ")\n");
				buf.append(staff.get(i).toString() + "\n\n");
			}
		}
		return buf.toString();
	}
}