package tabparts;

import java.util.regex.Pattern;

// Make double bars have stars only in the 3rd and 4th strings
/*
 * (UPDATE) Added fixSymbols() method
 * fixSymbols() and fixMeasure() throw a LargeNumberException
 */

/**
 * Represents a group of TabString objects. A measure
 * contains 6 TabStrings.
 */
public class TabMeasure {
	
	/* CONSTANTS */
	
	public static final int MAX_STRINGS = 6;	// The max strings it can have
	
	/* ATTRIBUTES */
	
	private TabString[] strings;	// The group of strings
	public int size;				// The number of strings added
	private int length;				// The length of the longest string
	private int repeat;				// If > 0, used for the repeat text above the measure
	
	private StringBuffer comment;	// If the measure isn't valid then the contents are stored as a string buffer
	private boolean is_comment;		// True if the measure is not valid and a comment is stored
	
	/**
	 * Creates a TabMeasure with 6 empty TabStrings.
	 */
	public TabMeasure() {
		this.strings = new TabString[MAX_STRINGS];
		this.size = MAX_STRINGS;
		this.setLength(0);
		this.setRepeat(0);
		for (int i = 0; i < MAX_STRINGS; i++)
			this.strings[i] = new TabString();
		this.comment = new StringBuffer();
		this.is_comment = false;
	}
	
	/**
	 * Creates an empty TabMeasure with a given length, containing empty TabStrings.
	 * 
	 * @param length	The length of the measure.
	 */
	public TabMeasure(int length) {
		this();
		this.setLength(length);
	}
	
	/**
	 * Creates a TabMeasure that is equal to the given TabMeasure.
	 * 
	 * @param measure	The measure to copy from.
	 */
	public TabMeasure(TabMeasure measure) {
		this.strings = new TabString[MAX_STRINGS];
		for (int i = 0; i < MAX_STRINGS; i++)
			this.strings[i] = new TabString(measure.getString(i));
		this.size = measure.size();
		this.setLength(measure.length());
		this.setRepeat(measure.getRepeat());
		this.comment = measure.comment;
		this.is_comment = measure.isComment();
	}
	
	/**
	 * Adds a TabString to the TabString array.
	 * 
	 * @param string	The string to be added.
	 * @return
	 */
	public void setString(TabString string, int index) {
		if (index >= MAX_STRINGS) throw new IllegalArgumentException("error, index must be < " + MAX_STRINGS);
		this.strings[index].copyString(string);
	}
	
	/**
	 * Gets the TabString at the given index of the TabString array.
	 * 
	 * @param index		The index of the TabString.
	 * @return	the TabString at the given index.
	 */
	public TabString getString(int index) {
		return this.strings[index];
	}	
	
	/**
	 * Gets the string as a text from the index given.
	 * 
	 * @param index the index of the string
	 * @return a text representation of the string	for (int i = 0; i < MAX_STRINGS; i++)
			this.strings[i] = new TabString(measure.getString(i));
		this.size = measure.size();
		this.setLength(measure.length());
		this.setRepeat(measure.getRepeat());
		this.addComment(measure.getComment());
		this.is_comment = measure.isComment();
	 */
	public String getStringText(int index) {
		return this.getString(index).toString();
	}
	
	/**
	 * Copies the given TabMeasure.
	 * 
	 * @param measure 	The TabMeasure to copy from.
	 */
	public void copyMeasure(TabMeasure measure) {
		for (int i = 0; i < MAX_STRINGS; i++)
			this.strings[i].copyString(measure.strings[i]);
		this.size = measure.size;
		this.setLength(measure.length());
		this.setRepeat(measure.getRepeat());
		this.comment = measure.comment;
		this.is_comment = measure.isComment();
	}
	
	/**
	 * Fixes all errors in strings and makes them all equal length
	 * by adding dashes to the ends. Finds a repeat number and stores it
	 * in the measure then changes the number into a bar or deletes it.
	 */
	public void fixMeasure() throws LargeNumberException {
		this.fixStrings();
		this.fixStartBar();
		this.fixEndBar();
		this.equalizeStrings();
		this.fixSymbols();
	}
	

	
	/**
	 * Fixes errors in the TabString array.
	 */
	public void fixStrings() {
		if (this.isComment()) return;
		for (int i = 0; i < this.size(); i++) {
			this.strings[i].fixErrors();
		}
	}
	
	/** 
	 * Makes sure the number of bars at the start of each string is the same.
	 * If at least one has a double barred start, then the rest will have it.
	 * 
	 * For example, turns this:
	 * |------|
	 * ||------|
	 * |------|
	 * |------|
	 * |------|
	 * |------|
	 * 
	 * into:
	 * 
	 * ||------|
	 * ||------|
	 * ||------|
	 * ||------|
	 * ||------|
	 * ||------|
	 */
	public void fixStartBar() {
		if (this.isComment()) return;
		boolean db = false;
		
		/* Detect if there's a string with double bars at the start */
		for (int i = 0; i < this.size(); i++) {
			if (TabString.VALID_DB_START.matcher(this.getString(i).toString()).find()) {
				db = true;
				break;
			}                       
		}
		
		/* If double bars are detected, then make the rest of the strings have double bars */
		if (db) {
			for (int i = 0; i < this.size(); i++) {
				/* Add a bar is the string is equal to "|||" */
				if (TabString.VALID_TRIPLE_STRING.matcher(this.getString(i).toString()).find()) {
					this.strings[i].fixEnd();
				/* Add a bar is the string doesn't have a double bar end */
				} else if (!TabString.VALID_DB_START.matcher(this.getString(i).toString()).find()) {
					this.strings[i].fixStart();
				}
			}
		}
	}
	
	/** 
	 * Makes sure the number of bars at the end of each string is the same.
	 * If at least one has a double barred end, then the rest will have it.
	 * 
	 * For example, turns this:
	 * |------|
	 * |------||
	 * |------|
	 * |------|
	 * |------|
	 * |------|
	 * 
	 * into:
	 * 
	 * |------||
	 * |------||
	 * |------||
	 * |------||
	 * |------||
	 * |------||
	 */
	public void fixEndBar() {
		if (this.isComment()) return;
		boolean db = false;
		/* Detect if there's a string with double bars at the end */
		for (int i = 0; i < this.size(); i++) {
			if (TabString.VALID_DB_END.matcher(this.getString(i).toString()).find()) {
				db = true;
				break;
			}
		}
		
		/* If double bars are detected, then make the rest of the strings have double bars */
		if (db) {
			for (int i = 0; i < this.size(); i++) {
				/* Add a bar is the string is equal to "|||" */
				if (TabString.VALID_TRIPLE_STRING.matcher(this.getString(i).toString()).find()) {
					this.strings[i].fixEnd();
				/* Add a bar is the string doesn't have a double bar end */
				} else if (!TabString.VALID_DB_END.matcher(this.getString(i).toString()).find()) {
					this.strings[i].fixEnd();
				}
			}
		}
	}
	
	/**
	 * Makes all string lengths equal to the greatest string length and 
	 * adds dashes to the ends of the strings. Blank strings will be trimmed
	 * down to the size of the string with the greatest length that isn't blank.
	 * 
	 * Example this measure:
	 * 
	 * |----------------------|
	 * |1--2---3-|
	 * |---2--3--4---6--3|
	 * |---2--3--4---6--3|
	 * |1--2---3-|
	 * |1--2---3-|
	 * 
	 * becomes:
	 * 
	 * |-----------------|
	 * |1--2---3---------|
	 * |---2--3--4---6--3|
	 * |---2--3--4---6--3|
	 * |1--2---3---------|
	 * |1--2---3---------|
	 */
	public void equalizeStrings() {
		if (this.isComment()) return;
		int max = this.length();
		/* Finds the max string length if the measure is blank */
		if (this.isBlank()) {
			for (int i = 0; i < this.size(); i++)
				if (this.strings[i].size() > max)
					max = this.strings[i].size();
		/* Finds the max string length that isn't blank */
		} else {
			for (int i = 0; i < this.size(); i++)
				if (this.strings[i].size() > max && !this.strings[i].isBlank())
					max = this.strings[i].size();
		}
		/* Set the measure length = to max found */
		this.setLength(max);
		/* Add dashes to short strings and trim blank strings */
		for (int i = 0; i < this.size(); i++) {
			if (this.strings[i].isBlank() && this.strings[i].size() > max) {
				this.strings[i].trimString(this.strings[i].size() - max);
			}
			else
				this.strings[i].addDash(this.length() - this.strings[i].size());
		}
			
	}
	
	/** 
	 * Fixes the symbols for each string of the measure.
	 * 
	 * @throws	LargeNumberException when 3 or more consecutive digits are found in a string.
	 */
	public void fixSymbols() throws LargeNumberException {
		if (this.isComment()) return;
		for (int i = 0; i < this.size; i++) {
			this.strings[i].fixSymbols();
		}
			
	}
	
	/**
	 * Gets the number of TabStrings added. Should always return the value of MAX_STRINGS.
	 * 
	 * @return size
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Gets the length of the TabMeasure.
	 * 
	 * @return	length
	 */
	public int length() {
		return this.length;
	}
	
	/**
	 * Sets the length of the TabMeasure to the given integer.
	 * 
	 * @param length	The amount to set the length to.
	 */
	public void setLength(int length) {
		this.length = length;
	}
	
	/**
	 * Checks whether the TabString array is empty or not.
	 * An empty measure contains all empty strings and is not a comment.
	 * 
	 * @return	true if the array is empty
	 * @return	false if the array is not empty
	 */
	public boolean isEmpty() {
		boolean empty = true;
		for (int i = 0; i < this.size(); i++) {
			if (!this.strings[i].isEmpty()) {
				empty = false;
				break;
			}
		}
		return empty && !this.isComment();
	}
	
	/**
	 * Checks whether the measure contains all blank strings
	 * 
	 * @return true if blank, false otherwise
	 */
	public boolean isBlank() {
		for (int i = 0; i < MAX_STRINGS; i++) {
			if (!strings[i].isBlank()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Sets the repeats to the given amount.
	 * 
	 * @param repeat the number of repeats to set
	 */
	public void setRepeat(int repeat) {
		if (repeat > 9 || repeat < 0) throw new IllegalArgumentException("Error: number of repeats must be >=0 and <=9");
		this.repeat = repeat;
	}
	
	public int getRepeat() {
		return this.repeat;
	}
	
	/**
	 * Appends the given string to the comment.
	 * 
	 * @param line	the string to append
	 */
	public void addComment(String line) {
		this.is_comment = true;
		this.comment.append(line + "\n");
	}
	
	/**
	 * Returns the comment as a string.
	 * 
	 * @return	the comment as a string
	 */
	public String getComment() {
		StringBuffer b = new StringBuffer(this.comment.toString());
		b.deleteCharAt(b.length()-1);
		return b.toString();
	}
	
	/**
	 * Checks to see if the measure is invalid and therefore a comment.
	 * 
	 * @return	true if the measure is invalid and a comment
	 * @return	false if the measure is valid and not a comment
	 */
	public boolean isComment() {
		return this.is_comment;
	}
	
	/**
	 * Gives a string representation of the TabMeasure by
	 * displaying the string representation of all its TabStrings.
	 * If the measure is invalid, then it returns it as a comment.
	 * 
	 * @return the string representation of the TabMeasure
	 */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		if (this.isComment()) {
			return this.getComment();
		} else {
			if (this.isEmpty())
				return "[empty measure]";
			for (int i = 0; i < MAX_STRINGS; i++) {
				buf.append(strings[i].toString());
				if (i < MAX_STRINGS - 1)
					buf.append("\n");
			}	
			return buf.toString();
		}
	}
}
