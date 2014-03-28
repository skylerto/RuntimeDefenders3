package version12;

/**
 * An object used for the HPStack class. It is a character with an (x,y) coordinate
 * that stores the position of symbols in the tab staff for writing to the PDF
 * output file. Valid symbols are typically only 1 letter unless it's a double digit.
 * 
 * @author Ron
 *
 */
public class SymbolPointv12 {
	
	/* ATTRIBUTES */
	
	private String symbol;
	private float x;
	private float y;
	private int line_number;
	private boolean doubledigit;
	
	/**
	 * Creates a SymbolPoint object that takes a character and a coordinate in the
	 * PDF file.
	 * 
	 * @param c	the character
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 */
	public SymbolPointv12(char c, float x, float y , int line_number) {
		this.setSymbol(c);
		this.setX(x);
		this.setY(y);
		this.setLine_number(line_number);
		this.doubledigit = false;
	}
	
	/**
	 * Creates a SymbolPoint object that can be a double digit integer.
	 * 
	 * @param i the integer
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 */
	public SymbolPointv12(int i, float x, float y,int line_number ) {
		this.setSymbol(i);
		this.setX(x);
		this.setY(y);
		this.setLine_number(line_number);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param other the other object to copy from
	 */
	public SymbolPointv12(SymbolPointv12 other) {
		this.symbol = other.getSymbol();
		this.setX(other.getX());
		this.setY(other.getY());
		this.doubledigit = other.doubledigit;
	}
	
	/**
	 * Checks whether the symbol is a double digit or not.
	 * @return
	 */
	public boolean isDoubleDigit() {
		return this.doubledigit;
	}
	
	/**
	 * Sets the symbol to the character.
	 * @param c
	 */
	public void setSymbol(char c) {
		this.symbol = String.valueOf(c);
	}
	
	/**
	 * Sets the symbol to the integer.
	 * If the symbol is a double digit then a flag is set.
	 * @param i
	 */
	public void setSymbol(int i) {
		this.symbol = String.valueOf(i);
		if (i > 9) this.doubledigit = true;
		else this.doubledigit = false;
	}
	
	/**
	 * Sets the x-coordinate.
	 * @param x2
	 */
	public void setX(float x2) {
		this.x = x2;
	}
	
	/**
	 * Sets the y-coordinate.
	 * @param y2
	 */
	public void setY(float y2) {
		this.y = y2;
	}
	/**
	 * Sets the line number.
	 * @param l2
	 */
	public void setLine_number(int l2) {
		line_number = l2;
	}
	
	
	/**
	 * Returns the symbol.
	 * @return
	 */
	public String getSymbol() {
		return this.symbol;
	}
	
	/**
	 * Gets the first character of the symbol string.
	 * @return
	 */
	public char getChar() {
		return this.symbol.charAt(0);
	}
	
	/**
	 * Gets the x-coordinate.
	 * @return
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * Gets the y-coordinate.
	 * @return
	 */
	public float getY() {
		return y;
	}
	/**
	 * Gets the y-coordinate.
	 * @return
	 */
	public int getLineNumber() {
		return line_number;
	}
	
	public void Print() {
		System.out.printf("(char is %c, x is %f,y is %f,current line is %d)\n",getChar(),getX(),getY(),getLineNumber());
	}
}