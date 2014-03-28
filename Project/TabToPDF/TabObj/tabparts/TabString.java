package tabparts;
import java.util.regex.Pattern;

/* (UPDATED) Valid strings must have at least 1 dash between the bars.

Valid Hammers and Pulls now have the same pattern as valid Slides.
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
 * 
 * Each string will have a LogAttributes object that stores information
 * needed to write to the auto-fix log file.
 */
public class TabString {
	
	/* CONSTANTS */
	
	public static final Character NULL_CHAR = '\0';
	public static final int MAX_SIZE = 3000;			// Max chars the array can hold
	
	public static final Character[] VALID_SYMBOLS = {'|', '-', 's', 'h', 'p', '*', '<', '>', ' '};	// Valid symbols that make up a valid TabString
	public static final Pattern VALID_SLIDE = Pattern.compile("([0-9][s])|([s][0-9])");				// Valid: #s# or s# or #s
	public static final Pattern VALID_HAMMER = Pattern.compile("([0-9][h])|([h][0-9])");			// Valid: #h# or h# or #h
	public static final Pattern VALID_SPACE = Pattern.compile("([^\\s^<][\\s][^\\s^>])");			// Valid: #[space]#
	public static final Pattern VALID_PULL = Pattern.compile("([0-9][p])|([p][0-9])");				// Valid: #p# or #p or p#
	public static final Pattern VALID_STAR = Pattern.compile("([|][|][\\*])|([\\*][|][|])");		// Valid: ||* or *||
	public static final Pattern VALID_HARMONIC = Pattern.compile("[<][1-9][>]");					// Valid: <#>
	public static final Pattern VALID_HARMONIC2 = Pattern.compile("[<][1-9][1-9][>]");				// Valid: <##>
	public static final Pattern INVALID_NUMBER = Pattern.compile("[0-9]{3,}");						// Invalid: ### or more consecutive numbers
	
	public static final Pattern VALID_STRING = Pattern.compile("^\\s*[|]{1,2}.*-.*[|]{1,2}\\s*$");// A string enclosed in '|'
	public static final Pattern VALID_START = Pattern.compile("^\\s*[|].*[-].*\\s*\\S+");	// A string missing the end '|'
	public static final Pattern VALID_END = Pattern.compile("\\S+\\s*.*[-].*[|]\\s*$");	// A string missing the start '|'
	public static final Pattern EMPTY_STRING = Pattern.compile("^\\s*$");			// An empty string of none more spaces
	public static final Pattern BLANK_STRING = Pattern.compile("^\\s*[|]{1,2}[\\s-]*[|]{1,2}\\s*$");	// A string of dashes only
	public static final Pattern VALID_DB_START = Pattern.compile("^\\s*[|]{2}.*[-].*\\s*\\S+\\s*");	// A string missing the end '||'
	public static final Pattern VALID_DB_END = Pattern.compile("\\s*\\S+\\s*.*[-].*[|]{2}\\s*$");	// A string missing the start '||'
	public static final Pattern VALID_STAR_END = Pattern.compile("\\s*\\S+\\s*.*[-].*[*][|]{2}\\s*$");	// A string with '*||' in the end
	public static final Pattern VALID_TB_END = Pattern.compile("\\s*\\S+\\s*.*[-].*[|]{3}\\s*$");	// A string with '|||' at the end
	
	public static final int NO_ERROR = 0;			// Return when no error is found
	public static final int ERROR_START = 1;		// Error return when the start '|' is missing
	public static final int ERROR_END = 2;			// Error return when the end '|' is missing
	public static final int ERROR_EMPTY = 3;		// Error return when the line is empty
	public static final int ERROR_COMMENT = 4;		// Error return when the line is a comment
	public static final int ERROR_DB_START = 5;		// Error return when the start '||' is missing
	public static final int ERROR_DB_END = 6;		// Error return when the end '||' is missing
	public static final int SPECIAL_TRIPLE = 7;		// Return when the string only contains '|||'
	
	public static final Pattern HARD_VALID_STRING = Pattern.compile("^[|]{1,2}.*[-].*[|]{1,2}$");	// A string closed in at least one set of '|' with no trailing spaces
	public static final Pattern VALID_TRIPLE_STRING = Pattern.compile("^\\s*[|]{3}\\s*$");		// A string containing only '|||'
	public static final Pattern VALID_BARS = Pattern.compile("^\\s*[|]{2,}\\s*$");			// A string that contains at least 2 bars and only bars
	public static final Pattern EMPTY_BARS = Pattern.compile("^\\s*[|]{1,2}.*[|]{1,2}\\s*$");	// Anything enclosed in bars with no dashes
	
	public static final String FULL_MSG = "[cannot add char to full string]";
	public static final String EMPTY_MSG = "[empty string]";
	public static final String VALID_MSG = "[valid string]";
	public static final String VALID_SPACEFIX_MSG = "[valid string, deleted excess spaces]";
	public static final String STARTFIX_MSG = "[fixed start of string]";
	public static final String STARTFIX_SPACEFIX_MSG = "[fixed start of string, deleted excess spaces]";
	public static final String ENDFIX_MSG = "[fixed end of string]";
	public static final String ENDFIX_SPACEFIX_MSG = "[fixed end of string, deleted excess spaces]";
	public static final String COMMENT_MSG = "[invalid string, assumed to be a comment]";
	public static final String BOTHFIX_MSG = "[fixed both ends of string]";
	public static final String BOTHFIX_SPACEFIX_MSG = "[fixed both ends of string, deleted excess spaces]";
	public static final String SPECIAL_MSG = "[special triple fix]";
	
	/* ATTRIBUTES */
	
	private Character[] chars;	// The characters of the string
	private int size;			// Number of chars added
	private LogAttributes log;	// Information used to write to the auto-fix log file
	
	/**
	 * Creates an empty string with no characters.
	 */
	public TabString() {
		this.chars = new Character[MAX_SIZE];
		this.size = 0;
		for (int i = 0; i < MAX_SIZE; i++)
			this.chars[i] = NULL_CHAR;
		this.log = new LogAttributes();
	}
	
	/**
	 * Creates an empty TabString with the given log attributes.
	 * 
	 * @param log The log attributes to copy from
	 */
	public TabString(LogAttributes log) {
		this();
		this.log.copyLogAtt(log);
	}
	
	/**
	 * Creates a TabString using the given string.
	 * 
	 * @param line	The string that will represent the TabString
	 */
	public TabString(String line) {
		this();
		for (int i = 0; i < line.length(); i++) {
			this.addChar(line.charAt(i));
		}
		this.size = line.length();
		this.log = new LogAttributes();
		this.log.setOriginal(line);
	}
	
	/**
	 * Creates a string that is equal to the given string.
	 * 
	 * @param string	Another TabString to copy from.
	 */
	public TabString(TabString string) {
		this.chars = new Character[MAX_SIZE];
		this.log = new LogAttributes();
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
	 * @param repeat If true then it is the first string in a measure and will try to detect the repetition number
	 * @return	The index number of the line the scanning stops at.
	 * @return	-1 if the line is all spaces
	 */
	public int scanLine(String line, int start, boolean first, int linenum) {
		boolean singlefound = false;
		boolean doublefound = false;
		boolean doubleendfound = false;
		boolean tripleendfound = false;
		boolean missingfirst = false;
		boolean repfound = false;
		
		int i = 0;
		
		/* Traverse the line and detect the last index of a valid string (i) */
		for (i = start; i < line.length(); i++) {
			/* Find first '|' */
			if (line.charAt(i) == '|'&& !singlefound) {
				if (missingfirst) {
					if (i < line.length()-1 && line.charAt(i+1) == '|') {
						doubleendfound = true;
						i++;
						break;
					} else
						break;
				}
				singlefound = true;
			/* Another '|' is found */
			} else if (line.charAt(i) == '|' && singlefound) {
				/* The start '||' is found */
				if (i > start && line.charAt(i-1) == '|' && !doublefound)
					doublefound = true;
				
				/* Find the end '|' */
				else {
					/* Stop if triple bars are found at the end */
					if (i < line.length()-2 && line.charAt(i+1) == '|' && line.charAt(i+2) == '|') {
						tripleendfound = true;
						i = i + 2;
						break;
					/* Stop if double bars are found at the end */
					} else if (i < line.length()-1 && line.charAt(i+1) == '|') {
						doubleendfound = true;
						i++;
						break;
					/* Stop if repetition number is found */
					} else if (first && i < line.length()-1 && line.charAt(i+1) >= '1' && line.charAt(i+1) <= '9') {
						repfound = true;
						i++;
						break;
					/* Stop if a bar is found at the end */
					} else
						break;
				}
					
			/* First char found is not a '|' */
			} else if (line.charAt(i) != '|' && line.charAt(i) != ' ' && !singlefound) {
				missingfirst = true;
			}
		}
		/* If the end is reached but no '|' is found then set i back to the last element */
		if (i == line.length()) i--;
		
		/* Add the recognizable string from start to i */
		for (int j = start; j <= i; j++)
			this.addChar(line.charAt(j));
		
		/* Saves the original representation of the string before fixes and the line number in the text file */
		this.getLogAtt().setLineNum(linenum);
		this.getLogAtt().setOriginal(this.toString());
		
		/* Minus i if a double/triple end bar or repetition was found */
		if (tripleendfound) i = i - 2;
		else if (doubleendfound || repfound) i--;
		
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
	 * Adds dashes to the end of the string assuming that the string
	 * is valid. For example, a call of addDash(5) will add 5 dashes
	 * to the end: ||----3--2--|| will become ||----3--2-------||
	 * 
	 * @param num	The number of dashes to add.
	 * @return		true if dashes are added, false if not
	 */
	public boolean addDash(int num) {
		TabString s;
		if (this.isEmpty()) return false;
		if ((num + this.size() - 1) > MAX_SIZE) return false;
		if (!HARD_VALID_STRING.matcher(this.toString()).find()) return false;
		
		s = new TabString(this.getLogAtt());

		/* Do if string is wrapped in '||' */
		if(VALID_STAR_END.matcher(this.toString()).find()) {
			/* Add the original chars of the string except the '*||' */
			for (int i = 0; i < this.size() - 3; i++)
				s.addChar(this.getChar(i));
			/* Add num dashes */
			for (int i = 0; i < num; i++)
				s.addChar('-');
			s.addChar('*');
			s.addChar('|');
			s.addChar('|');
			/* Copy the temp string to this string */
			this.copyString(s);
			return true;

		/* Do if string is wrapped in '||' */
		}	else if(VALID_DB_END.matcher(this.toString()).find()) {
			/* Add the original chars of the string except the '||' */
			for (int i = 0; i < this.size() - 2; i++)
				s.addChar(this.getChar(i));
			/* Add num dashes */
			for (int i = 0; i < num; i++)
				s.addChar('-');
			s.addChar('|');
			s.addChar('|');
			/* Copy the temp string to this string */
			this.copyString(s);
			return true;

		/* Do if string is wrapped in '|' */
		} else {
			/* Add the original chars of the string except the '|' */
			for (int i = 0; i < this.size() - 1; i++)
				s.addChar(this.getChar(i));
			/* Add num dashes */
			for (int i = 0; i < num; i++)
				s.addChar('-');
			s.addChar('|');
			/* Copy the temp string to this string */
			this.copyString(s);
			return true;
		}
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
		int start = 0;
		int end = 0;
		if (this.isEmpty()) return false;
		if (!this.isBlank()) return false;
		if (!VALID_STRING.matcher(this.toString()).find()) return false;
		if (VALID_BARS.matcher(this.toString()).find()) return false;
		if (this.size() < 3) return false;
		/* Count the '|'s at the start of the string */
		for (int i = 0; i < this.size(); i++) {
			if (this.getChar(i) != '|') break;
			else
				start++;
		}
		/* Count the '|'s at the end of the string */
		for (int i = this.size() - 1; i >= 0; i--) {
			if (this.getChar(end) != '|') break;
			else
				end++;
		}
		/* Delete everything between bars if num is greater than the number of characters */
		if ((this.size()-(start + end)) <= num) {
			s = new TabString(this.getLogAtt());
			for (int i = 0; i < start; i++)
				s.addChar('|');
			for (int i = 0; i < end; i++)
				s.addChar('|');
			this.copyString(s);
		/* Delete the number of chars given */
		} else {
			s = new TabString(this.getLogAtt());
			if (VALID_DB_END.matcher(this.toString()).find()) {
				for (int i = 0; i < this.size() - num - 2; i++)
					s.addChar(this.getChar(i));
				s.addChar('|');
				s.addChar('|');
			} else {
				for (int i = 0; i < this.size() - num - 1; i++)
					s.addChar(this.getChar(i));
				s.addChar('|');
			}
			
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
	 * @throws	LargeNumberException when 3 or more consecutive digits are found.
	 */
	public String fixErrors() {
		/* Find what type of error needs to be fixed */
		int error = this.checkError();
		boolean b = false;
		/* If the string is valid then delete outer spaces if needed */
		if (error == NO_ERROR) {
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
			
		/* If the start '||' is missing, then add it */
		} else if (error == ERROR_DB_START) {
			b = this.delTrailSpaces();
			this.fixStart();
			if (b) return STARTFIX_SPACEFIX_MSG;
			else return STARTFIX_MSG;
			
		/* If the end '||' is missing, then add it */
		} else if (error == ERROR_DB_END) {
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
		} else if (error == SPECIAL_TRIPLE) {
			this.delTrailSpaces();
			this.addDash(1);
			return SPECIAL_MSG;
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
	 * Replaces a char in the given index.
	 * 
	 * @param c	The char that is replacing
	 * @param index	The position in the string to replace
	 */
	public void replaceChar(char c, int index) {
		if (c == NULL_CHAR && index == this.size - 1) {
			this.chars[index] = c;
			this.size--;
		}
		this.chars[index] = c;
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
		this.log.copyLogAtt(string.getLogAtt());
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
	 * @return	5 if the start '||' is missing
	 * @return	6 if the end '||' is missing	
	 * @return	7 if the string only contains '|||'
	 * 
	 */
	public int checkError() {
		if (VALID_TRIPLE_STRING.matcher(this.toString()).find()) return SPECIAL_TRIPLE;
		if (VALID_STRING.matcher(this.toString()).find()) return NO_ERROR;
		if (this.isEmpty() || EMPTY_STRING.matcher(this.toString()).find()) return ERROR_EMPTY;
		if (this.size() == 1) return ERROR_COMMENT;
		if (VALID_DB_START.matcher(this.toString()).find()) return ERROR_DB_END;
		if (VALID_DB_END.matcher(this.toString()).find()) return ERROR_DB_START;
		if (VALID_START.matcher(this.toString()).find()) return ERROR_END;
		if (VALID_END.matcher(this.toString()).find()) return ERROR_START;
		return ERROR_COMMENT;
	}
	
	/**
	 * Assuming that the string is part of a measure that isn't a comment,
	 * it will throw an exception if there are 3 or more consecutive numbers
	 * in the string.
	 * 
	 * @throws LargeNumberException 3 or more consecutive numbers found in the string
	 */
	
	public void checkNumberException() throws LargeNumberException {
		if (INVALID_NUMBER.matcher(this.toString()).find()) throw new LargeNumberException("Cannot have more than 2 consecutive digits in the string: " + this.toString());
	}
	
	/**
	 * This method should only run for each string after the entire measure is fixed.
	 * Replaces characters that are not valid symbols with dashes. Replaces invalid
	 * spaces with dashes. Valid symbols that are not used properly are replaced with
	 * dashes.
	 * 
	 * Example, the string:
	 * 
	 * ||* - <2<3>-><5 3>bs>a5s5-<6>--dp  h*-1h1-1p-1 2 *||
	 * 
	 * becomes:
	 * 
	 * ||*----2<3>---5 3-----5s5-<6>---------1h1-1p-1 2-*||
	 * 
	 */
	public void fixSymbols() {
		/* Replace invalid symbols with dashes */
		for (int i = 0; i < this.size(); i++) {
			if (!this.isSymbol(this.getChar(i)))
				this.replaceChar('-', i);
		}
		/* Replace invalid spaces with dashes */
		for (int i = 0; i < this.size(); i++) {
			if (this.getChar(i) == ' ' && !VALID_SPACE.matcher(this.getSubstring(i-1, i+2)).find())
				this.replaceChar('-', i);
		}
		/* Replace invalid stars with dashes */
		for (int i = 0; i < this.size(); i++) {
			if (this.getChar(i) == '*' && !VALID_STAR.matcher(this.getSubstring(i-2, i+3)).find())
				this.replaceChar('-', i);
		}
		/* Replace invalid slide with dashes */
		for (int i = 0; i < this.size(); i++) {
			if (this.getChar(i) == 's' && !VALID_SLIDE.matcher(this.getSubstring(i-1, i+2)).find())
				this.replaceChar('-', i);
		}
		/* Replace invalid hammer with dashes */
		for (int i = 0; i < this.size(); i++) {
			if (this.getChar(i) == 'h' && !VALID_HAMMER.matcher(this.getSubstring(i-1, i+2)).find())
				this.replaceChar('-', i);
		}
		/* Replace invalid pull with dashes */
		for (int i = 0; i < this.size(); i++) {
			if (this.getChar(i) == 'p' && !VALID_PULL.matcher(this.getSubstring(i-1, i+2)).find())
				this.replaceChar('-', i);
		}
		/* Replace invalid '<' harmonics with dashes */
		for (int i = 0; i < this.size(); i++) {
			if (this.getChar(i) == '<' && !VALID_HARMONIC2.matcher(this.getSubstring(i, i+4)).find())
				if (this.getChar(i) == '<' && !VALID_HARMONIC.matcher(this.getSubstring(i, i+3)).find())
					this.replaceChar('-', i);
					
		}
		/* Replace invalid '>' harmonics with dashes */
		for (int i = 0; i < this.size(); i++) {
			if (this.getChar(i) == '>' && !VALID_HARMONIC2.matcher(this.getSubstring(i-3, i+1)).find())
				if (this.getChar(i) == '>' && !VALID_HARMONIC.matcher(this.getSubstring(i-2, i+1)).find())
					this.replaceChar('-', i);
		}
	}
	
	/**
	 * Checks to see if the given character is a valid symbol in the string.
	 * Valid symbols are spaces and: | - * s p h
	 * @param c	The character to compare
	 * @return	true if the character is a valid symbol, false otherwise
	 */
	public boolean isSymbol(Character c) {
		for (int i = 0; i < VALID_SYMBOLS.length; i++) {
			if (c == VALID_SYMBOLS[i] || c >= '0' && c <= '9')
				return true;
		}
		return false;
	}
	
	/**
	 * Returns a substring of the TabString given 2 indexes.
	 * 
	 * @param index1	The starting index
	 * @param index2	The ending index
	 * @return	The substring of the TabString
	 */
	public String getSubstring(int index1, int index2) {
		if (this.isEmpty()) return "";
		if (index1 < 0) index1 = 0;
		if (index2 < 0) index2 = 0;
		if (index1 > this.size()) index1 = this.size();
		if (index2 > this.size()) index2 = this.size();
		return this.toString().substring(index1, index2);
	}
	
	/**
	 * Gets the log attributes.
	 * 
	 * @return
	 */
	public LogAttributes getLogAtt() {
		return this.log;
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
