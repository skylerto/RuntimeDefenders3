package testers;

import version11.HPStack;
import version11.InvalidSymbolPosition;
import version11.SymbolPoint;

public class TestHPStack {
	public static void main (String[] args) throws InvalidSymbolPosition {
		HPStack stack = new HPStack();
		
		stack.push(new SymbolPointv11('p', 2, 1));
		stack.push(new SymbolPointv11(1, 2, 1));
		stack.push(new SymbolPointv11(10, 2, 1));
		stack.push(new SymbolPointv11('h', 2, 1));
		stack.push(new SymbolPointv11('3', 2, 1));
		stack.push(new SymbolPointv11('p', 2, 1));
		stack.push(new SymbolPointv11(11, 2, 1));
		stack.push(new SymbolPointv11(12, 2, 1));
		stack.push(new SymbolPointv11('p', 2, 1));
		stack.push(new SymbolPointv11(3, 2, 1));
		stack.push(new SymbolPointv11(4, 2, 1));
		stack.push(new SymbolPointv11(5, 2, 1));
		stack.push(new SymbolPointv11(1, 2, 1));
		stack.push(new SymbolPointv11('p', 2, 1));
		//stack.push(new SymbolPoint('h', 2, 1)); // Uncommenting this should throw an error because p and h can't appear consecutively
		
		System.out.println(stack.toString());
	}
}
