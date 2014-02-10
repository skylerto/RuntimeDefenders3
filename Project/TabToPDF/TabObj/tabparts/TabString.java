package tabparts;
import java.util.regex.Pattern;

/*
 * (UPDATED) Added a pattern attribute for finding a blanks string.
 * Added a isBlank() method which checks to see if there are only dashes/spaces
 * between the vertical lines of a string.
 * Added a trimString() method which deletes dashes and blanks at the end of the
 * string.
 * 
 * -Ron
 */

/**
 * Represents a guitar string on the tab staff (ie. |----1----3---|).
 * Stores the string in a character array and is able to check
 * to see if the string is valid. If it is invalid, it attempts
 * to correct the errors.
 * 
 * A valid string is any line with two '|' on either side of the
 * line. Any character can be between them. Only spaces are allowed
 * outside them. If a '|' is missing from one end it will add a '|'
 * to the other end. If the line is only length 1 or has no '|' it's
 * assumed to be a comment and there are no attempts to fix it.
 */
public class TabString {
	
	/* CONSTANTS */
	
	public final Character NULL_CHAR = '\0';
	public final int MAX_SIZE = 75;		// Max chars it can hold
	public final int ERROR_START = 1;	// Error return when the start '|' is missing
	public final int ERROR_END = 2;		// Error return when the end '|' is missing
	public final int ERROR_EMPTY = 3;	// Error return when the line is empty
	public final int ERROR_COMMENT = 4;	// Error return when the line is a comment
	
	public final Pattern VALID_STRING = Pattern.compile("\\s*[|].*[|]\\s*");// A string enclosed in '|'
	public final Pattern VALID_START = Pattern.compile("\\s*[|]\\s*\\S+");	// A string missing the end '|'
	public final Pattern VALID_END = Pattern.compile("\\S+\\s*[|]\\s*");	// A string missing the start '|'
	public final Pattern EMPTY_STRING = Pattern.compile("^\\s*$");			// An empty string of none more spaces
	public final Pattern BLANK_STRING = Pattern.compile("\\s*[|][\\s-]*[|]\\s*");	// A string of dashes only
	
	public final String FULL_MSG = "[cannot add char to full string]";
	public final String VALID_MSG = "[valid string]";
	public final String VALID_SPACEFIX_MSG = "[valid string, deleted excess spaces]";
	public final String STARTFIX_MSG = "[fixed start of string]";
	public final String STARTFIX_SPACEFIX_MSG = "[fixed start of string, deleted excess spaces]";
	public final String ENDFIX_MSG = "[fixed end of string]";
	public final String ENDFIX_SPACEFIX_MSG = "[fixed end of string, deleted excess spaces]";
	public final String EMPTY_MSG = "[empty string]";
	public final String COMMENT_MSG = "[invalid string, assumed to be a comment]";
	public final String BOTHFIX_MSG = "[fixed both ends of string]";
	public final String BOTHFIX_SPACEFIX_MSG = "[fixed both ends of string, deleted excess spaces]";
	
	/* ATTRIBUTES */
	
	private Character[] chars;	// The characters of the string
	private int size;			// Number of chars added
	
	/**
	 * Creates an empty string with no characters.
	 */
	public TabString() {
		this.chars = new Character[MAX_SIZE];
		this.size = 0;
		for (int i = 0; i < MAX_SIZE; i++)
			this.chars[i] = NULL_CHAR;
	}
	
	/**
	 * Creates a string that is equal to the given string.
	 * 
	 * @param string	Another TabString to copy from.
	 */
	public TabString(TabString string) {
		this.chars = new Character[MAX_SIZE];
		this.copyString(string);
	}
	
	/**
	 * Scans a string of characters for a TabString pattern. Sets the char
	 * array equal to a sequence wrapped in '|'. If letters/numbers are detected
	 * before a '|' then it is assumed the start '|' is missing and will add chars
	 * up until it finds the end '|'. If it finds the start '|' but not the end '|', it
	 * will continue scanning until the end of the line.
	 * 
	 * @param line	The given string to scan.
	 * @param start	The starting index in the given string to start scanning from.
	 * @return	The index number of the line the scanning stops at.
	 * @return	-1 if the line is all spaces
	 */
	public int scanLine(String line, int start) {
		boolean firstfound = false;
		boolean missingfirst = false;
		int i = 0;
		for (i = start; i < line.length(); i++) {
			if (line.charAt(i) == '|'&& !firstfound) {
				if (missingfirst) {
					break;
				}
				firstfound = true;
			} else if (line.charAt(i) == '|' && firstfound) {
				break;
			} else if (line.charAt(i) != '|' && line.charAt(i) != ' ' && !firstfound) {
				missingfirst = true;
			}
		}
		if (i == line.length()) i--;
		for (int j = start; j <= i; j++)
			this.addChar(line.charAt(j));
		return i;
	}
	
	/**
	 * Adds a char to the char array.
	 * 
	 * @param c		The char to be added.
	 */
	public void addChar(char c) {
		if (this.isFull()) throw new IndexOutOfBoundsException(FULL_MSG);
		this.chars[size++] = c;
	}
	
	/**
	 * Adds a char to the char array in the given index.
	 * 
	 * @param c			The char to be added.
	 * @param index		Where the char will be added in the char array.
	 */
	private void addCharAt(char c, int index) {
		if (this.isFull()) throw new IndexOutOfBoundsException(FULL_MSG);
		this.chars[index] = c;
		size++;
	}
	
	/**
	 * Adds dashes to either end of the string assuming that the string
	 * is valid. For example, a call of addDash(5, 0) will add 5 dashes
	 * to the start: |----3--2--| will become |---------3--2--|
	 * 
	 * @param num	The number of dashes to add.
	 * @param type	0 will add dashes to the start and 1 will add to the end.
	 * @return		true if dashes are added, false if not
	 */
	public boolean addDash(int num, int type) { // this adds an extra dash ie 5 given adds 6
		TabString s;
		if (this.isEmpty()) return false;
		if ((num + this.size() - 1) > MAX_SIZE) return false;
		if (type == 0 && this.getChar(0) != '|') return false;
		if (type == 1 && this.getChar(this.size()- 1) != '|') return false;
		/* Add dashes to the start */
		if (type == 0) {
			s = new TabString();
			s.addChar('|');
			/* Add num dashes */
			for (int i = 0; i <= num; i++)
				s.addChar('-');
			/* Add the original character from the string except the '|' */
			for (int i = 1; i < this.size() - 1; i++)
				s.addChar(this.getChar(i));
			s.addChar('|');
			/* Copy the temp string to this string */
			this.copyString(s);
			return true;
		/* Add dashes to the end */
		} else if (type == 1) {
			s = new TabString();
			s.addChar('|');
			/* Add the original chars of the string except the '|' */
			for (int i = 1; i < this.size() - 1; i++)
				s.addChar(this.getChar(i));
			/* Add num dashes */
			for (int i = 0; i <= num; i++)
				s.addChar('-');
			s.addChar('|');
			/* Copy the temp string to this string */
			this.copyString(s);
			return true;
		}
		return false;
	}
	
	/**
	 * Deletes dashes and blanks from the end of a valid string.
	 * Example this:
	 * 
	 * |--------- ----|
	 * 
	 * becomes this with num = 6:
	 * 
	 * |--------|
	 * 
	 * @param num The number of dashes to delete
	 * @return	true if something was deleted
	 * @return	false if nothing was deleted
	 */
	public boolean trimString(int num) {
		TabString s;
		if (this.isEmpty()) return false;
		if (!this.isBlank()) return false;
		if (this.getChar(0) != '|' && this.getChar(this.size()- 1) != '|') return false;
		if (this.size() < 3) return false;
		if ((this.size()-2) <= num) {
			s = new TabString();
			s.addChar('|');
			s.addChar('|');
			this.copyString(s);
		} else {
			s = new TabString();
			for (int i = 0; i < this.size() - num - 1; i++)
				s.addChar(this.getChar(i));
			s.addChar('|');
			this.copyString(s);
		}
		return true;
	}
	
	/**
	 * Checks for errors in the string and attempts to fix them.
	 * Removes leading or trailing spaces for valid or partial-valid
	 * strings. Strings that are comments or empty are left as is.
	 * Adds '|' to strings that are missing one at the start or end.
	 * 
	 * @param fixboth	If true, the method with add a '|' to both ends of
	 * 					a comment, assuming it's a string.
	 * @return	a message saying what was fixed.
	 */
	public String fixErrors() {
		/* Find what type of error needs to be fixed */
		int error = this.checkError();
		boolean b = false;
		/* If the string is valid then delete outer spaces if needed */
		if (error == 0) {
			b = this.delTrailSpaces();
			if (b) return VALID_SPACEFIX_MSG;
			else return VALID_MSG;
			
		/* If the start '|' is missing, then add it */
		} else if (error == ERROR_START) {
			b = this.delTrailSpaces();
			this.fixStart();
			if (b) return STARTFIX_SPACEFIX_MSG;
			else return STARTFIX_MSG;
			
		/* If the end '|' is missing, then add it */
		} else if (error == ERROR_END) {
			b = this.delTrailSpaces();
			this.fixEnd();
			if (b) return ENDFIX_SPACEFIX_MSG;
			else return ENDFIX_MSG;
			
		/* If the string is empty then make it blank string */
		} else if (error == ERROR_EMPTY) {
			this.addChar('-');
			return this.fixErrors();
			
		/* If the string has no '|' then add both */
		} else if (error == ERROR_COMMENT) {
			b = this.delTrailSpaces();
			if (this.size == 1 && this.getChar(0) == '|') {
				this.fixEnd();
				if (b) return ENDFIX_SPACEFIX_MSG;
				else return ENDFIX_MSG;
			} else {
				this.fixBoth();
				if (b) return BOTHFIX_SPACEFIX_MSG;
				else return BOTHFIX_MSG;
			}
		}
		
		/* It should never reach this return */
		return "[no error caught: " + error + "]";
	}
	
	/**
	 * Adds a '|' to the start of the char array.
	 */
	
	//made public for testing purposes
	public void fixStart() {
		if (this.isFull()) throw new IndexOutOfBoundsException(FULL_MSG);
		for (int i = this.size(); i > 0; i--)
			this.chars[i] = this.chars[i-1];
		this.addCharAt('|', 0);
	}
	
	/**
	 * Adds a '|' to the end of the char array.
	 */
	//made public for testing purposes
	public void fixEnd() {
		if (this.isFull()) throw new IndexOutOfBoundsException(FULL_MSG);
		this.addChar('|');
	}
	
	/**
	 * Adds a '|' to both ends of the char array.
	 */
	//made public for testing purposes
	public void fixBoth() {
		this.fixEnd();
		this.fixStart();
	}
	
	/**
	 * Deletes leading or trailing spaces in the char array.
	 * Spaces inside a string are left as is. 
	 * ie. A string ("   |----1--- -2|   ") would become ("|----1--- -2|").
	 * 
	 * @return	true if spaces were deleted
	 * @return	false if no spaces were detected
	 */
	// made public for testing purposes
	public boolean delTrailSpaces() {
		boolean fixed = false;
		int start = 0;
		int end = this.size() - 1;
		
		/* Finds the first non-space char going from left to right and stores the index in start */
		for (int i = 0; i < this.size(); i++) {
			if (this.getChar(i) != ' ') {
				start = i;
				break;
			}
		}
		/* Finds the first non-space char going from right to left and stores the index in end */
		for (int i = this.size() - 1; i >= 0; i--) {
			if (this.getChar(i) != ' ') {
				end = i;
				break;
			}
		}
		/* Delete spaces if spaces have been detected at the leading or trailing ends */
		if (start > 0 || end < this.size() - 1) {
			/* If there's only 1 char, then move it to index 0 and delete other other spaces */
			if (start == end) {
				this.chars[0] = this.getChar(start);
				for (int i = 1; i < this.size(); i++)
					this.chars[i] = NULL_CHAR;
			} else {
				/* Delete all white spaces that occur before the start index */
				if (start > 0) {
					for (int i = start; i < end + 1; i++) {
						this.chars[i-start] = this.chars[i];
						this.chars[i] = NULL_CHAR;
					}
				}
				/* Delete all white spaces that occur after the end index */
				if (end < this.size() - 1) {
					for (int i = end + 1; i < this.size(); i++)
						this.chars[i] = NULL_CHAR;
				}
				/* Get new size */
			}
			this.size = end - start + 1;
			fixed = true;
		}
		return fixed;
	}
	
	/**
	 * Returns the char form the given index in the char array.
	 * 
	 * @param index		The index of the char.
	 * @return	the char at the given index.
	 */
	public char getChar(int index) {
		return this.chars[index];
	}
	
	/**
	 * Copies the information from the given string.
	 * 
	 * @param string The string to copy from.
	 */
	public void copyString(TabString string) {
		for (int i = 0; i < MAX_SIZE; i++)
			this.chars[i] = string.getChar(i);
		this.size = string.size;
	}
	
	/**
	 * Returns the number of chars in the char array.
	 * 
	 * @return size
	 */
	
	public int size() {
		return this.size;
	}
	
	/**
	 * Checks whether the char array is full or not.
	 * 
	 * @return	true if the char array is full.
	 * @return	false if the char array is not full.
	 */
	
	public boolean isFull() {
		return this.size() == MAX_SIZE;
	}
	
	/**
	 * Checks whether the char array is empty or not.
	 * 
	 * @return	true if the char array is empty.
	 * @return	false if the char array is not empty.
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	/**
	 * Checks whether a string only contains dashes or spaces between its vertical lines.
	 * For example, the following would return true:
	 * 
	 * |--------|
	 * |---   ---  --|
	 * 
	 * The following return false:
	 * 
	 * |------
	 * |---2---3--2|
	 * 
	 * @return	true if there are only dashes or spaces in a valid string
	 * @return	false if there are characters other than dashes and spaces in the string
	 */
	public boolean isBlank() {
		if (BLANK_STRING.matcher(this.toString()).find()) return true;
		else return false;
	}
	
	/**
	 * Uses a string representation of the char array and checks for
	 * patterns to see if it's valid or not.
	 * 
	 * @return	0 if no errors are found.
	 * @return	1 if the start '|' is missing
	 * @return	2 if the end '|' is missing
	 * @return	3 if the string is empty or contains only spaces
	 * @return	4 if the string is of length 1 or does not have any '|'
	 */
	public int checkError() {
		if (VALID_STRING.matcher(this.toString()).find()) return 0;
		if (this.isEmpty() || EMPTY_STRING.matcher(this.toString()).find()) return ERROR_EMPTY;
		if (this.size() == 1) return ERROR_COMMENT;
		if (VALID_START.matcher(this.toString()).find()) return ERROR_END;
		if (VALID_END.matcher(this.toString()).find()) return ERROR_START;
		return ERROR_COMMENT;
	}
	
	/**
	 * Converts the char array into a string object.
	 * 
	 * @returns	the string representation of the char array
	 */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		if (this.isEmpty())
			return EMPTY_MSG;
		for (int i = 0; i < this.size(); i++)
			buf.append(this.chars[i]);
		return buf.toString();
	}
}
