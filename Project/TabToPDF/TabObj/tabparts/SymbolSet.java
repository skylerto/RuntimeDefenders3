package tabparts;

/**
 * A SymbolSet is used as an attribute for the TabStaff class.
 * It represents the tab elements pull(p), hammer(h) and slide(s) that connect
 * two numbers together in the TabStaff. The SymbolSet holds the measure index in the staff,
 * the string index in the measure, and the character index range in the string for the symbol and the
 * two numbers it connects. Each int array must be of size 4.
 * 
 * @author Ron
 *
 */
public class SymbolSet {
	
	/* CONSTANTS */
	
	public final int MAX_SIZE = 4;
	
	/* ATTRIBUTES */
	
	private int[] symbol_index;
	private int[] num1_index;
	private int[] num2_index;
	
	/**
	 * Creates a SymbolSet using the given indexes.
	 * 
	 * For example a staff has these two measures. Measure 5 and 6 respectively:
	 * 
	 * |----------|----------|
	 * |----3p----|---15-----|
	 * |----------|----------|
	 * |----------|----------|
	 * |----------|----------|
	 * |----------|----------|
	 * 
	 * The pull connects 3 and 15 with an arc.
	 * 
	 * The symbol p has the symbol_index array equal to {5, 1, 6, 7} because
	 * the symbol is in measure 5, string 1 and character 6-7.
	 * 
	 * The number 3 has the num1_index array equal to {5, 1, 5, 6} because
	 * it's in the same measure as the symbol but at character 5-6.
	 * 
	 * The number 7 has the num2_index array equal to {6, 1, 4, 6} because
	 * it's in measure 6, string 1 and character 4 to 6.
	 * 
	 * Example constructor call: new SymbolSet(new int[]{5, 1, 6, 7}, new int[]{5, 1, 5, 6}, new int[]{6, 1, 4, 6});
	 * 
	 * @param symbol_index	Indexes of the symbol {measure index, string index, char startindex, char endindex}
	 * @param num1_index	Indexes of the first number {measure index, string index, char startindex, char endindech
	 * @param num2_index	Indexes of the second number {measure index, string index, char startindex, char endindex}
	 */
	public SymbolSet(int[] symbol_index, int[] num1_index, int[] num2_index) {
		this.symbol_index = new int[MAX_SIZE];
		this.num1_index = new int[MAX_SIZE];
		this.num2_index = new int[MAX_SIZE];
		
		for (int i = 0; i < this.MAX_SIZE; i ++) {
			this.symbol_index[i] = symbol_index[i];
			this.num1_index[i] = num1_index[i];
			this.num2_index[i] = num2_index[i];
		}
	}
	
	/**
	 * Gets the symbol indexes.
	 * @return
	 */
	public int[] getSymbolIndex() {
		int[] a = new int[MAX_SIZE];
		for (int i = 0; i < MAX_SIZE; i++)
			a[i] = this.symbol_index[i];
		return a;
	}
	
	/**
	 * Gets the indexes of the first number.
	 * @return
	 */
	public int[] getNum1Index() {
		int[] a = new int[MAX_SIZE];
		for (int i = 0; i < MAX_SIZE; i++)
			a[i] = this.num1_index[i];
		return a;
	}
	
	/**
	 * Gets the indexes of the second number.
	 * @return
	 */
	public int[] getNum2Index() {
		int[] a = new int[MAX_SIZE];
		for (int i = 0; i < MAX_SIZE; i++)
			a[i] = this.num1_index[i];
		return a;
	}
	
	public int compareNums(TabStaff staff) {
		String sub = "";
		int num1 = -1;
		int num2 = -1;
		try {
			sub = staff.getStringText(this.num1_index[0], this.num1_index[1]).substring(this.num1_index[2], this.num1_index[3]);
			num1 = Integer.parseInt(sub);
		} catch (NumberFormatException e) {
			System.err.println("Cannot convert: " + sub + " to a number");
		}
		System.out.println(num1);
		return 0;
	}
}
