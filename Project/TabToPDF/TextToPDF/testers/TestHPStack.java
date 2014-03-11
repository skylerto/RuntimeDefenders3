package testers;

import version11.HPStack;
import version11.InvalidSymbolPosition;
import version11.SymbolPoint;

public class TestHPStack {
	public static void main (String[] args) throws InvalidSymbolPosition {
		HPStack stack = new HPStack();
		
		stack.push(new SymbolPoint('p', 2, 1));
		stack.push(new SymbolPoint(1, 2, 1));
		stack.push(new SymbolPoint(10, 2, 1));
		stack.push(new SymbolPoint('h', 2, 1));
		stack.push(new SymbolPoint('3', 2, 1));
		stack.push(new SymbolPoint('p', 2, 1));
		stack.push(new SymbolPoint(11, 2, 1));
		stack.push(new SymbolPoint(12, 2, 1));
		stack.push(new SymbolPoint('p', 2, 1));
		stack.push(new SymbolPoint(3, 2, 1));
		stack.push(new SymbolPoint(4, 2, 1));
		stack.push(new SymbolPoint(5, 2, 1));
		stack.push(new SymbolPoint(1, 2, 1));
		stack.push(new SymbolPoint('p', 2, 1));
		//stack.push(new SymbolPoint('h', 2, 1)); // Uncommenting this should throw an error because p and h can't appear consecutively
		
		System.out.println(stack.toString());
	}
}
