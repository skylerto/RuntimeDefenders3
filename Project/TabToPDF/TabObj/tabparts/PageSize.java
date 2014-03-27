package tabparts;

/**
 * Represents the width and height of a PDF page in pixels.
 * There are 3 sizes: Letter, Legal and Tabloid
 * 
 * @author Ron
 *
 */
public class PageSize {
	
	/* CONSTANTS */
	
	private final float DPI = 72f;
	private final float MM_PER_INCH = 25.4f;
	private final float LETTER_WIDTH = (215.9f * DPI)/MM_PER_INCH;
	private final float LETTER_HEIGHT = (279.4f * DPI)/MM_PER_INCH;
	private final float LEGAL_WIDTH = (215.9f * DPI)/MM_PER_INCH;
	private final float LEGAL_HEIGHT = (355.6f * DPI)/MM_PER_INCH;
	private final float TABLOID_WIDTH = (432f * DPI)/MM_PER_INCH;
	private final float TABLOID_HEIGHT = (279f * DPI)/MM_PER_INCH;
	
	public enum Size {
		LETTER, LEGAL, TABLOID
	}
	
	/* ATTRIBUTES */
	
	private Size size;
	private float width;
	private float height;
	
	/**
	 * Creates a page size.
	 * 
	 * @param size The page size to set.
	 */
	public PageSize(Size size) {
		this.size = size;
		this.setSize(size);
	}
	
	/**
	 * Sets the page size.
	 * 
	 * @param size The page size to set.
	 */
	public void setSize(Size size) {
		if (size == Size.LETTER) {
			this.width = LETTER_WIDTH;
			this.height = LETTER_HEIGHT;
		} else if (size == Size.LEGAL) {
			this.width = LEGAL_WIDTH;
			this.height = LEGAL_HEIGHT;
		}else if (size == Size.TABLOID) {
			this.width = TABLOID_WIDTH;
			this.height = TABLOID_HEIGHT;
		}
	}
	
	/**
	 * Returns a number based on the page size.
	 * 
	 * @return 0 if the page size is Letter, 1 if the page size is Legal and 2 if Tabloid.
	 */
	public int getPageSize() {
		if (this.size == Size.LETTER)
			return 0;
		else if (this.size == Size.LEGAL)
			return 1;
		else 
			return 2;
	}
	
	/**
	 * Gets the page width.
	 * 
	 * @return
	 */
	public float getWidth() {
		return this.width;
	}
	
	/**
	 * Gets the page height.
	 * 
	 * @return
	 */
	public float getHeight() {
		return this.height;
	}
}
