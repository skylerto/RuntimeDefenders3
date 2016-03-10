package version13;

public class SplitStack {
	
	/* CONSTANTS */
	
	public static final int MAX_SIZE = 100;
	
	/* ATTRIBUTES */
	
	protected SplitMarker[] stack;
	protected int size;
	
	public SplitStack() {
		this.stack = new SplitMarker[MAX_SIZE];
		this.size = 0;
	}
	
	public void push(SplitMarker marker) throws ConversionException {
		if (this.size > MAX_SIZE) throw new ConversionException("Conversion error: pushing to full SplitStack!");
		this.stack[size] = marker;
		this.size++;
	}
	
	public SplitMarker pop() throws ConversionException {
		if (this.size == 0) throw new ConversionException("Conversion error: popping from empty SplitStack!");
		SplitMarker r = this.stack[size-1];
		this.stack[size-1] = null;
		this.size--;
		return r;
	}
	
	public SplitMarker peak() {
		SplitMarker a = this.stack[size-1];
		return a;
	}
}
