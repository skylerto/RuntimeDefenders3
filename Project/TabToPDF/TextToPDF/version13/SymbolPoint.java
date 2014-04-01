package version13;

/**
 * This class will be used to help draw arcs of special symbols like pulls,
 * hammer, slides by storing the integers and it coordinate values , line number.
 * It also will be used to paint integers and diamond after music note is drawn
 * to avoid lines passing through them when line_spacing is small
 * 
 * @author Ron
 * @author saad
 *
 */
public class SymbolPoint {
	
	/* ATTRIBUTES */
	
	private String symbol;
	private float x;
	private float y;
	private int line_number;
	
	/**
	 * Creates a SymbolPoint object that takes a character and a coordinate in the
	 * PDF file.
	 * 
	 * @param str	the character or number
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 */
	public SymbolPoint(String str, float x, float y , int line_number) {
		this.setSymbol(str);
		this.setX(x);
		this.setY(y);
		this.setLine_number(line_number);
	}
	
	/**
	 * Sets the symbol to the character.
	 * @param c
	 */
	public void setSymbol(char c) {
		this.symbol = String.valueOf(c);
	}
	
	/**
	 * Sets the symbol to the Symbol.
	 * 
	 * @param Symbol
	 */
	public void setSymbol(String Symbol) {
		this.symbol = Symbol;
		
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
		System.out.printf("(char is %c, x is %f,y is %f,current line is %d)\n",getSymbol(),getX(),getY(),getLineNumber());
	}
}