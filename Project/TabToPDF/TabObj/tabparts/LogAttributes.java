package tabparts;

/**
 * The LogAttributes object is used as an attribute of the TabString object to store information needed
 * to write to the auto-fix log. It stores information such as the line number of the TabString in the
 * text file, the original representation of the TabString and if the string has gone through auto-fixing.
 * 
 * @author Ron
 *
 */
public class LogAttributes {
	
	/* ATTRIBUTES */
	
	private int linenum;		// The line number of the TabString in the text file
	private String original;	// A text representation of the TabString when it is first read in from the file
	private boolean fixed;		// A flag that is true if the original string has been changed
	
	/**
	 * Creates LogAttributes with line number set to -1 and original and fixed strings to null.
	 */
	public LogAttributes() {
		this.setLineNum(-1);
		this.original = "";
		this.fixed = false;
	}
	
	/**
	 * Creates LogAttributes with the given line number and strings.
	 * 
	 * @param linenum The line number in the text file
	 * @param original The original text of the TabString
	 * @param fixed The fixed text of the TabString
	 */
	public LogAttributes(int linenum, String original, boolean fixed) {
		this.setLineNum(linenum);
		this.setOriginal(original);
		this.fixed = fixed;
	}
	
	
	public void copyLogAtt(LogAttributes log) {
		this.setLineNum(log.getLineNum());
		this.setOriginal(log.getOriginal());
		this.fixed = log.fixed;
	}
	/**
	 * Gets the number line in the text file.
	 * 
	 * @return the number line
	 */
	public int getLineNum() {
		return this.linenum;
	}
	
	/**
	 * Sets the number line in the text file.
	 * 
	 * @param num the number line in the text file
	 */
	public void setLineNum(int num) {
		this.linenum = num;
	}
	
	/**
	 * Gives the original representation of the string.
	 * 
	 * @return the original string
	 */
	public String getOriginal() {
		return this.original;
	}
	
	/**
	 * Sets the original string to the line given.
	 * 
	 * @param line the original representation to be set
	 */
	public void setOriginal(String line) {
		this.original = line;
	}
	
	/**
	 * Compares the original to given string, assuming to be fixed string.
	 * If they are equal then there was no change made to the original.
	 * 
	 * @param line the fixed string to compare to the original string
	 * @return true if the given string is different from the original string, false if they are the same
	 */
	public boolean checkFixed(String line) {
		if (!this.original.equals(line))
			this.fixed = true;
		else this.fixed = false;
		return this.fixed;
	}
	
	/**
	 * Returns whether the original has been changed or not.
	 * 
	 * @return true if the original has been changed and false otherwise
	 */
	public boolean isFixed() {
		return this.fixed;
	}
}
