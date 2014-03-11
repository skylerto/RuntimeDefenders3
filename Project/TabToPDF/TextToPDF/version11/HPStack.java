package version11;

/**
 * Represents a stack of SymbolPoint objects. The stack will only push
 * valid hammer or pull patterns such as: #h# and #p#. The class is used
 * to determine the start and end points of the hammer and pull curves
 * in the PDF.
 * 
 * @author Ron
 *
 */

public class HPStack {
	
	/* CONSTANTS */
	
	public static final int MAX_SIZE = 200;
	public static final char HAMMER = 'h';
	public static final char PULL = 'p';
	
	/* ATTRIBUTES */
	
	private SymbolPoint[] stack;
	private int size;
	
	/**
	 * Creates a new empty stack.
	 */
	public HPStack() {
		this.stack = new SymbolPoint[200];
		this.size = 0;
	}
	
	/**
	 * Pushes SymbolPoints onto the stack only if it maintains valid pull and hammer patterns
	 * within the stack.
	 * 
	 * For example:
	 * 
	 * | |  |  |
	 * |p|  |h |
	 * |3|  |11|
	 * |_|  |__|
	 * 
	 * Pushing a number on any of the above stacks is valid, but pushing a pull or a hammer is invalid.
	 * Another example:
	 * 
	 * | |  |11|
	 * |4|  |10|
	 * |p|  |h |
	 * |2|  |6 |
	 * |_|  |__|
	 * 
	 * Pushing a number onto the first stack above is valid. Pushing a number onto the second stack will replace
	 * the 11 on top of the stack because the 11 is no longer the last seen number that isn't connected to a
	 * hammer/pull.
	 * 
	 * @param point the point to be pushed
	 * @return true if something was pushed, false otherwise
	 * @throws InvalidSymbolPosition thrown when a pull/hammer is being pushed onto another pull/hammer
	 */
	public boolean push(SymbolPoint point) throws InvalidSymbolPosition {
		/* Throw exception if full */
		if (this.isFull()) throw new StackOverflowError("Pushing to full HPStack.");
		
		/* If the symbol of the point isn't a number, hammer or a pull then return false */
		if (!(this.isNumber(point.getSymbol()) || point.getChar() == HAMMER || point.getChar() == PULL)) return false; 
		
		/* If the stack is empty and the point is a non-number char then return false */
		if (this.isEmpty() && !this.isNumber(point.getSymbol())) return false;
		
		/* If the stack is empty then push the point as long as it's a number */
		if ((this.isEmpty() && this.isNumber(point.getSymbol())) ||
			
				/* If the the top of the stack contains a number then push the point as long as it's a hammer/pull */
				(this.isNumber(this.peak().getSymbol()) && (point.getChar() == HAMMER || point.getChar() == PULL) ||
				
				/* Or if the the top of the stack contains a hammer/pull then push the point as long as it's a number */
				(this.peak().getChar() == HAMMER || this.peak().getChar() == PULL) && this.isNumber(point.getSymbol()))) {
			
			this.stack[this.size] = point;
			this.size++;
			return true;
			
		/* If the top of the stack contains a number and the point contains a number then push if you can */
		} else if (this.isNumber(this.peak().getSymbol()) && this.isNumber(point.getSymbol())) {
			/* Replace the number at the top of the stack if it has no connection to a pull/hammer */
			if (!this.canPushNumber())
				this.pop();

			this.stack[this.size] = point;
			this.size++;
			return true;
		/* If the top of the stack contains a hammer/pull and the point is a hammer/pull then throw an exception */
		} else if ((this.peak().getChar() == HAMMER || this.peak().getChar() == PULL) && (point.getChar() == HAMMER || point.getChar() == PULL)) {
			throw new InvalidSymbolPosition("Number missing for a hammer/pull symbol.");
		}
		
		/* Return false since nothing was pushed */
		return false;
	}
	
	/**
	 * Removes the element from the top of the stack.
	 * 
	 * @return the element from the top of the stack
	 * @return null if the stack is empty
	 */
	public SymbolPoint pop() {
		if (this.isEmpty()) return null;
		SymbolPoint temp = new SymbolPoint(this.stack[size-1]);
		this.stack[size-1] = null;
		this.size--;
		return temp;
	}
	
	/**
	 * Checks to see if the symbol of a SymbolPoint object is a double digit number.
	 * 
	 * @param s the symbol to check
	 * @return true if a double digit, false otherwise
	 */
	public boolean isNumber(String s) {
		boolean parsable = true;
		try {
			int i = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			parsable = false;
		}
		return parsable;
	}
	
	/**
	 * Checks to see if the top of the stack contains a number that is
	 * linked to a pull or hammer already in the stack.
	 * 
	 * @return true if the top of the stack has a connection to a pull/hammer, false otherwise
	 */
	public boolean canPushNumber() {
		if (this.isEmpty()) return true;
		if (this.isFull()) return false;
		int flag = 0;
		for (int i = 0; i < this.size; i++) {
			if (flag == 0 && this.isNumber(this.stack[i].getSymbol()))
				flag = 1;
			else if (flag == 0 && !this.isNumber(this.stack[i].getSymbol()))
				flag = 2;
			else if (flag == 1 && !this.isNumber(this.stack[i].getSymbol()))
				flag = 2;
			else if (flag == 2 && this.isNumber(this.stack[i].getSymbol()))
				flag = 0;
		}
		if (flag == 0 || flag == 2) return true;
		else return false;
	}
	
	/**
	 * Looks at the top of the stack.
	 * 
	 * @return a copy of the element at the top of the stack
	 */
	public SymbolPoint peak() {
		if(this.isEmpty()) return null;
		else return new SymbolPoint(this.stack[size - 1]);
	}
	
	/**
	 * Checks to see if the stack is full.
	 * @return
	 */
	public boolean isFull() {
		return this.size == MAX_SIZE;
	}
	
	/**
	 * Checks to see if the stack is empty.
	 * @return
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	/**
	 * Gets the size of the stack.
	 * @return
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * String representation of the stack.
	 */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer("[");
		for (int i = 0; i < this.size(); i++) {
			buf.append(this.stack[i].getSymbol() + ",");
		}
		buf.append("]");
		return buf.toString();
	}
	
}
