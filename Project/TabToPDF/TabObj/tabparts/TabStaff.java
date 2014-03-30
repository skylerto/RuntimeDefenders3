package tabparts;


import java.io.*;
import java.util.*;
import java.util.regex.Pattern;


/* (UPDATE) findRepeats() doesn't throw an exception anymore
 * scanFile() will only throw a LargeNumberException
 * Added new method getMeasureRepeat()
 * Added 2 methods: removeIncompleteSymbols() and splitLongMeasures()
 */

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
	public static final Pattern REPEAT_END = Pattern.compile("^\\s*\\S+\\s*.*-.*[|][1-9]\\s*$");	// The string that has a repeat number at the end
	public static final Pattern NO_DASH = Pattern.compile("^[|][^-]+[|]$");
	
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
	 * @throws LargeNumberException 
	 */
	public void scanFile(File file) throws LargeNumberException {
		File input = file;
		BufferedReader stream;
		String line;			// A line read from the input file
		int linenum = 0;			// The number line in the input file currently being read
			
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
				linenum++;
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
					temp.scanLine(line, 0, false, linenum);
					
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
						/* If there's no line break after 6 strings have been read or there's no line break after a comment, go to the next measure */
						if (stringnum >= TabMeasure.MAX_STRINGS || commentfound == true) {
							
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
							/* If a block comment was found previously, then increment current measure */
							if (commentfound) {
								currentmeasure++;
								maxmeasure = currentmeasure;	// The max number of measures seen is equal to the current measure
								commentfound = false;
							}
							stringnum = 0;
							basemeasure = maxmeasure;
						}
						currentmeasure = basemeasure;
						
						/* Reads through a row of measures and stores strings, starting with the first string of each measure */
						/* Each string in a line will create a new measure */
						for (int p = 0; p < line.length(); currentmeasure++) {
							s = new TabString();
							p = s.scanLine(line, p, stringnum == 0, linenum);	// Stores the string in 's' and 'p' is the char index where it left off in the line
							System.out.println("scanned " + s.toString());
							if (!NO_DASH.matcher(s.toString()).find()) {
								System.out.println("adding " + s.toString());
								this.staff.get(currentmeasure).setString(s, stringnum);	// Stores the string in the staff's measure
	
								/* Break from the loop if a double barred end is detected */
								if (p == line.length() - 2 && this.getStringText(currentmeasure, stringnum).charAt(this.getStringText(currentmeasure, stringnum).length() - 1) == '|'
										&& this.getStringText(currentmeasure, stringnum).charAt(this.getStringText(currentmeasure, stringnum).length() - 2) == '|') {
									//TabString.VALID_DB_END.matcher(this.staff.get(currentmeasure).getString(stringnum).toString()).find()) {
									currentmeasure++;
									break;
	
									/* Break from the loop if a triple barred end is detected */
								} else if (p == line.length() - 3 && this.getStringText(currentmeasure, stringnum).charAt(this.getStringText(currentmeasure, stringnum).length() - 1) == '|'
										&& this.getStringText(currentmeasure, stringnum).charAt(this.getStringText(currentmeasure, stringnum).length() - 2) == '|'
										&& this.getStringText(currentmeasure, stringnum).charAt(this.getStringText(currentmeasure, stringnum).length() - 3) == '|') {
									//TabString.VALID_TB_END.matcher(this.staff.get(currentmeasure).getString(stringnum).toString()).find()) {
									currentmeasure++;
									break;
	
									/* Break from the loop if at the end of the line */
								} else if (p == line.length() - 1) { 
									currentmeasure++;
									break;
								}
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
			
			/* Writes the auto-fix changes to the log */
			this.fixedLogAtt();
			this.writeAutofixLog();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Returns a 2-dimensional array list of strings. The outer list is the collection
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
	 * Gets a hashmap<key, value> where the key is the index of the measure in the list
	 * and the value is the repeat number of that measure.
	 * 
	 * @return hashmap containing the index of each measure in the list as a key and the measure's repeat number as the value
	 */
	public HashMap<Integer, Integer> getRepeatMap() {
		HashMap<Integer, Integer> map = new HashMap<Integer,Integer>();
		for (int i = 0; i < this.size(); i++) {
			map.put(i, this.staff.get(i).getRepeat());
		}
		return map;
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
	 * Gets the TabMeasure at the index in the list.
	 * 
	 * @param index	The index of the TabMeasure
	 * @return	The TabMeasure from the given index
	 */
	public TabMeasure getMeasure(int index) {
		return this.staff.get(index);
	}
	
	/**
	 * Gets the repeat number for the measure.
	 * @param index
	 * @return
	 */
	public int getMeasureRepeat(int index) {
		return this.staff.get(index).getRepeat();
	}
	
	/**
	 * Adds a measure to the list.
	 */
	public void addMeasure() {
		this.staff.add(new TabMeasure());
	}
	
	/**
	 * Removes all empty TabMeasures from the list.for (int i = 0; i < this.size(); i++) {
				this.strings[i].fixErrors();
			}
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
	 * @throws LargeNumberException 
	 * 
	 * @throws Exception
	 */
	public void fixStaff() throws LargeNumberException {
		this.removeEmpty();
		this.findRepeats();
		this.fixEnd();
		for (int i = 0; i < this.size(); i++)
			this.staff.get(i).fixMeasure();
		this.findOneRepeats();
		this.deleteComments();
		this.removeIncompleteSymbols();
	}
	
	/**
	 * Removes the symbols p, h and s if they are dangling at the start or beginning of the staff.
	 * Example:
	 * 
	 * |-----h3----|
	 * 
	 * This is a string in the first measure of the staff. There is no number before h to connect
	 * 3 with so the h is replaced with a dash. 
	 * 
	 * |---3s------|
	 * 
	 * This string is in the last measure of the staff. There is no number after s to connect
	 * 3 with so the s is replaced with a dash.
	 * 
	 * All other h, p and s within the staff are assumed to be valid.
	 */
	public void removeIncompleteSymbols() {
		/* Start to End */
		for (int j = 0; j < TabMeasure.MAX_STRINGS; j++) {
			staff :
			for (int i = 0; i < staff.size(); i++) {
				String s = this.getMeasure(i).getStringText(j);
				for (int k = 0; k < s.length(); k++) {
					if (s.charAt(k) >= '0' && s.charAt(k) <= '9')
						break staff;
					else if (s.charAt(k) == 'p' || s.charAt(k) == 'h' || s.charAt(k) == 's') {
						this.getMeasure(i).getString(j).replaceChar('-', k);
						break staff;
					}
				}
			}
		}
		/* End to Start */
		for (int j = 0; j < TabMeasure.MAX_STRINGS; j++) {
			staff :
			for (int i = staff.size()- 1; i >= 0; i--) {
				String s = this.getMeasure(i).getStringText(j);
				for (int k = s.length() - 1; k >= 0; k--) {
					if (s.charAt(k) >= '0' && s.charAt(k) <= '9')
						break staff;
					else if (s.charAt(k) == 'p' || s.charAt(k) == 'h' || s.charAt(k) == 's') {
						this.getMeasure(i).getString(j).replaceChar('-', k);
						break staff;
					}
				}
			}
		}
		
	}
	
	
	
	/**
	 * Deletes all comment measures in the staff.
	 */
	public void deleteComments() {
		for (int i = this.size() - 1; i >= 0; i--) {
			if (this.staff.get(i).isComment()) {
				this.staff.remove(i);
			}
				
		}
	}
	
	/**
	 * * A special fix where the end measure contains a repeat number that needs to be deleted.
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
	public void findRepeats() {
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
							}
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
	 * Checks each string and turns on its fixed flag in its LogAttributes variable if the string was auto-fixed.
	 */
	public void fixedLogAtt() {
		if (this.size() == 0) return;
		for (int i = 0 ; i < this.size(); i++)
			if (!this.staff.get(i).isComment())
				for (int j = 0; j< TabMeasure.MAX_STRINGS; j++)
					if (this.staff.get(i).getString(j).getLogAtt().checkFixed(this.staff.get(i).getString(j).toString()));
	}
	
	/**
	 * Writes the the measures that were auto-fixed to the auto-fix log file. It displays
	 * the measure number, the lines in the text file the measure was found and it shows the
	 * before and after effects of the auto-fix.
	 * 
	 * Example output in the auto-fix log file:
	 * 
		*****************************************************************************************************
		*****************************************************************************************************
		
		Measure 2 from line 12 to 17 of the text file was auto-fixed:
		
		|---------7--------| -------------------> |---------7--------|
		|-----5s7---7------| -------------------> |-----5s7---7------|
		|---0s--------0----| -------------------> |---0s--------0----|
		|-------------ss-2-| [string 4 fixed]---> |----------------2-|
		|--sssssssss-------| [string 5 fixed]---> |------------------|
		|-0----------------| -------------------> |-0----------------|
	 *
	 */
	public void writeAutofixLog() {
		if (this.size() == 0) return;
		AutofixLog log = new AutofixLog();	// The log file to write to
		/* Do for each measure that isn't a comment */
		for (int i = 0 ; i < this.size(); i++) {
			if (!this.staff.get(i).isComment()) {
				/* If there exists a measure that has been fixed */
				inner :
				for (int j = 0; j< TabMeasure.MAX_STRINGS; j++) {
					if (this.staff.get(i).getString(j).getLogAtt().isFixed()) {
						int length = this.maxOriginalLength(i);
						log.writeNL("*****************************************************************************************************");
						log.writeNL("*****************************************************************************************************");
						log.writeNL("");
						log.writeNL("Measure " + (i+1) + " from line " + (this.getStartLine(i)+1) + " to " + (this.getEndLine(i)+1)
								+ " of the text file was auto-fixed:");
						log.writeNL("");
						/* Print out the original string followed by the fixed string */
						for (int k = 0; k < TabMeasure.MAX_STRINGS; k++) {
							String original = this.staff.get(i).getString(k).getLogAtt().getOriginal();
							String fixed = this.staff.get(i).getString(k).toString();
							String middle1 = "------------------->";
							String middle2 = "[string " + (k+1) + " fixed]--->";
							if (this.staff.get(i).getString(k).getLogAtt().isFixed())
								log.writeNL(String.format("%-" + length +"s %s %s", original, middle2, fixed));
							else
								log.writeNL(String.format("%-" + length +"s %s %s", original, middle1, fixed));
							
							
						}
						log.writeNL("");
						log.writeNL("");
						break inner;
					}
				}
			}
		}
		log.close();
	}
	
	/**
	 * Used for the writeAutofixLog() method to find the starting line of the measure in the
	 * text file. This is needed if the original measure had missing strings that were added later.
	 * 
	 * For example, in the original measure:
	 * 
	 * [empty string]
	 * [empty string]
	 * ||-----2-0------2-0------0------||
	 * ||-----3-0------3-0------0------||
	 * [empty string]
	 * [empty string]
	 * 
	 * The first empty string doesn't exist in the text file therefore it has no line number.
	 * This method would find the line number of string 3 since it exists in the text file.
	 * 
	 * @param measureindex the index of the measure in the staff list
	 * @return the string number of the starting line in the text file
	 */
	public int getStartLine(int measureindex) {
		int linenum = -1;
		for (int i = 0; i < TabMeasure.MAX_STRINGS; i++) {
			if (this.staff.get(measureindex).getString(i).getLogAtt().getLineNum() != -1) {
				linenum = this.staff.get(measureindex).getString(i).getLogAtt().getLineNum();
				return linenum;
			}
		}
		return linenum;
	}
	
	/**
	 * Used for the writeAutofixLog() method to find the ending line of the measure in the
	 * text file. This is needed if the original measure had missing strings that were added later.
	 * 
	 * For example, in the original measure:
	 * 
	 * [empty string]
	 * [empty string]
	 * ||-----1-0------1-0------0------||
	 * ||-----6-0------6-0------0------||
	 * [empty string]
	 * [empty string]
	 * 
	 * The 6th empty string doesn't exist in the text file therefore it has no line number.
	 * This method would find the line number of string 4 since it exists in the text file.
	 * 
	 * @param measureindex the index of the measure in the staff list
	 * @return the string number of the ending line in the text file
	 */
	public int getEndLine(int measureindex) {
		int linenum = -1;
		for (int i = TabMeasure.MAX_STRINGS - 1; i >= 0; i--) {
			if (this.staff.get(measureindex).getString(i).getLogAtt().getLineNum() != -1) {
				linenum = this.staff.get(measureindex).getString(i).getLogAtt().getLineNum();
				return linenum;
			}
		}
		return linenum;
	}
	
	/**
	 * Splits measures into two measures if they are longer than the given
	 * max length. If the second measure is also longer then it is split again
	 * and so on. Split measures maintain their original order in the staff.
	 * 
	 * Example, this staff is split with max length 10:
	 * 
	 * ||---------3----------10-----0-------0---------------7-||
	 * ||----0---------10--------------0-------0-------5s7----||
	 * ||*------2----------0----------2-------2-------0-------||
	 * ||*----------------------------------------------------||
	 * ||---2--------------------3----------------------------||
	 * ||-0-----------7-----------------------------0---------||
	 * 
	 * this measure is split into the following measures in the staff:
	 * 
	 * ||---------3--------|
	 * ||----0---------10--|
	 * ||*------2----------|
	 * ||*-----------------|
	 * ||---2--------------|
	 * ||-0-----------7----|
	 * 
	 * |--10-----0-------0-|
	 * |------------0------|
	 * |0----------2-------|
	 * |-------------------|
	 * |------3------------|
	 * |-------------------|
	 * 
	 * |--------------7-||
	 * |-0-------5s7----||
	 * |2-------0-------||
	 * |----------------||
	 * |----------------||
	 * |------0---------||
	 * 
	 * @param maxlength The max number of characters a measure can have in a string.
	 */
	public void splitLongMeasures(int maxlength) {
		List<TabMeasure> newstaff = new ArrayList<TabMeasure>();	// The new staff with the split measures
		
		maxlength = maxlength - 4;
		
		/* For each measure in the staff, split it if it's longer than maxlength */
		for (int i = 0; i < this.size(); i++) {
			int repeat = this.staff.get(i).getRepeat();	// Save the repeat
			TabMeasure shortened = this.staff.get(i);
			shortened.setRepeat(0);	// The split measures shouldn't repeat, only the last measure should
			TabMeasure split;
			
			
			/* While the split part is still too long, then continue to split it */
			while ((split = shortened.splitMeasure(maxlength)) != null) {
				newstaff.add(shortened);	// Add first half of the split
				shortened = split;			// Set second half of the split to check if it needs to be split
			}
			shortened.setRepeat(repeat);	// The last measure gets the original repeat	
			newstaff.add(shortened);
			
		}
		/* If splits were made then set the new staff list equal to the current staff list */
		if (!this.staff.equals(newstaff)) {
			this.staff.clear();
			for (int i = 0; i < newstaff.size(); i++)
				this.staff.add(newstaff.get(i));
		}
	}
	
	/**
	 * Used for the writeAutofixLog() method for finding the maximum length of the original
	 * measure so it can be formatted properly in the auto-fix log file.
	 * 
	 * @param measureindex The measure index in the staff list
	 * @return the max length of the original measure
	 */
	public int maxOriginalLength(int measureindex) {
		int max = 0;
		for (int i = 0; i < TabMeasure.MAX_STRINGS; i++) {
			if (this.staff.get(measureindex).getString(i).getLogAtt().getOriginal().length() > max)
				max = this.staff.get(measureindex).getString(i).getLogAtt().getOriginal().length();
		}
		return max;
	}
	
	/**
	 * Returns the size of the list.
	 * @return
	 */
	public int size() {
		return this.staff.size();
	}
	
	/**
	 * A string representation of the staff. Displays the measure number and the
	 * number of repeats for each measure. Also if the measure is comment or not.
	 * Outputs the total measures found at the end.
	 * Example output of a staff of size 3:
	 * 
	 * comment(0)
	 * Title = My Song
	 * By = Ron
	 * 
	 * measure(1) repeats(0)
	 * |---22-|
	 * |---22-|
	 * |---22-|
	 * |---22-|
	 * |---22-|
	 * |---22-|
	 * 
	 * measure(2) repeats(0)
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
			if (this.staff.get(i).isComment()) {
				buf.append("comment(" + i + ") iscomment=" + String.valueOf(this.staff.get(i).isComment())+ "\n");
				buf.append(this.staff.get(i).getComment() + "\n\n");
			} else {
				buf.append("measure(" + i + ") "+ "repeats(" + staff.get(i).getRepeat() + ")\n");
				buf.append(this.staff.get(i).toString() + "\n\n");
			}
		}
		buf.append("Total Measures=" + this.size());
		return buf.toString();
	}
}
