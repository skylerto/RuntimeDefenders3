package version11;

/**
 * An object used for the HPStack class. It is a character with an (x,y) coordinate
 * that stores the position of symbols in the tab staff for writing to the PDF
 * output file. Valid symbols are typically only 1 letter unless it's a double digit.
 * 
 * @author Ron
 *
 */
public class SymbolPoint {
	
	/* ATTRIBUTES */
	
	private String symbol;
	private int x;
	private int y;
	private boolean doubledigit;
	
	/**
	 * Creates a SymbolPoint object that takes a character and a coordinate in the
	 * PDF file.
	 * 
	 * @param c	the character
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 */
	public SymbolPoint(char c, int x, int y) {
		this.setSymbol(c);
		this.setX(x);
		this.setY(y);
		this.doubledigit = false;
	}
	
	/**
	 * Creates a SymbolPoint object that can be a double digit integer.
	 * 
	 * @param i the integer
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 */
	public SymbolPoint(int i, int x, int y) {
		this.setSymbol(i);
		this.setX(x);
		this.setY(y);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param other the other object to copy from
	 */
	public SymbolPoint(SymbolPoint other) {
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
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Sets the y-coordinate.
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
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
	public int getX() {
		return x;
	}
	
	/**
	 * Gets the y-coordinate.
	 * @return
	 */
	public int getY() {
		return y;
	}
}
