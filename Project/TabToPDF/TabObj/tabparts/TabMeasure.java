package tabparts;

/*
 * (UPDATE) The equalizeString() method now calls the trimString() method for each of its blank
 * strings that are greater than the max length of the string that isn't blank.
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
	private int size;				// The number of strings added
	private int length;				// The length of the longest string
	
	/**
	 * Creates a TabMeasure with 6 empty TabStrings.
	 */
	public TabMeasure() {
		this.strings = new TabString[MAX_STRINGS];
		this.size = MAX_STRINGS;
		this.setLength(0);
		for (int i = 0; i < MAX_STRINGS; i++)
			this.strings[i] = new TabString();
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
		return new TabString(this.strings[index]);
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
	}
	
	/**
	 * Fixes all errors in strings and makes them all equal length
	 * by adding dashes to the ends.
	 */
	public void fixMeasure() {
		this.fixStrings();
		this.equalizeStrings();
	}
	
	/**
	 * Fixes errors in the TabString array.
	 */
	public void fixStrings() {
		for (int i = 0; i < this.size(); i++) {
			this.strings[i].fixErrors();
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
		int max = this.length();
		/* Finds the max string length that isn't blank */
		for (int i = 0; i < this.size(); i++)
			if (this.strings[i].size() > max && !this.strings[i].isBlank())
				max = this.strings[i].size();
		/* Set the measure length = to max found */
		this.setLength(max);
		/* Add dashes to short strings and trim blank strings */
		for (int i = 0; i < this.size(); i++) {
			if (this.strings[i].isBlank() && this.strings[i].size() > max) {
				this.strings[i].trimString(this.strings[i].size() - max);
			}
			else
				this.strings[i].addDash(this.length() - this.strings[i].size() - 1, 1);
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
	 * Checks whether the TabString array is full or not.
	 * 
	 * @return	true if the array is full
	 * @return	false if the array is not full
	 */
	public boolean isFull() {
		return this.size == MAX_STRINGS;
	}
	
	/**
	 * Checks whether the TabString array is empty or not.
	 * 
	 * @return	true if the array is empty
	 * @return	false if the array is not empty
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	/**
	 * Gives a string representation of the TabMeasure by
	 * displaying the string representation of all its TabStrings.
	 * 
	 * @return the string representation of the TabMeasure
	 */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
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
