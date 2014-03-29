package mvcV4;

import tabparts.LargeNumberException;
import version13.*;
import com.itextpdf.text.*;

public class Model {
	
	/* CONSTANTS */
	
	public static final String DEFAULT_OUTPUTPATH = TextToPDF.DEFAULT_OUTPUTPATH;
	
	/* ATTRIBUTES */
	
	protected TextToPDF converter;
	
	protected String filename;
	protected String filenameWithExtention;
	protected String outputpath;
	protected String previewImage; // Holds the pathname of previewImage
	
	protected String title; // Holds the value of the title.
	protected String subTitle; // Holds the value of the subTitle
	protected float spacing;
	protected int elementSize;
	protected int titleFontSize;
	protected int subTitleFontSize;
	protected Rectangle pageSize;
	protected float leftMargin;
	protected float rightMargin;
	protected float measureSpace;	// The space between two rows of measures

	/**
	 * Deafault constructor, initializes strings to be empty integers to be 0.
	 */
	public Model() {

		this.title = "";
		this.subTitle = "";
		this.previewImage = "";
		this.titleFontSize = 0;
		this.subTitleFontSize = 0;
		this.elementSize = 0;
		this.spacing = 0;
		this.pageSize = new Rectangle(0f, 0f);
		this.leftMargin = 0;
		this.rightMargin = 0;
		this.measureSpace = 0;
		
		this.filename = "";
		this.filenameWithExtention = "";
		this.outputpath = DEFAULT_OUTPUTPATH;
	}

	/**
	 * Constructs a new model for the current state of the GUI. Everytime the
	 * conversion code is ran this needs to be initialized.
	 * 
	 * @param title
	 *            - title of the pdf.
	 * @param subTitle
	 *            - subtitle of the pdf.
	 * @param previewImage
	 *            - pathname to the preview image file.
	 * @param titleFontSize
	 *            - font size of the title.
	 * @param subTitleFontSize
	 *            - subtitle font size.
	 * @param measureFontSize
	 *            - measure font size.
	 * @param spacing
	 *            - spacing between elements.
	 */
	public Model(String title, String subTitle, String previewImage,
			String filename, String filenameWithExtension, int titleFontSize,
			int subTitleFontSize, int measureFontSize, float spacing) {
		this();
		this.title = title;
		this.filename = filename;
		this.filenameWithExtention = filenameWithExtension;
		this.subTitle = subTitle;
		this.previewImage = previewImage;
		this.titleFontSize = titleFontSize;
		this.subTitleFontSize = subTitleFontSize;
		this.elementSize = measureFontSize;
		this.spacing = spacing;
	}

	public void initializeConverter() throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException {
		this.converter = new TextToPDF(this.getOutputFilename(), this.getFilenameWithExtension());
		this.getConverterProperties();
	}
	
	public void runConverter() throws ConversionException {
		this.converter.WriteToPDF();
	}
	
	public void getConverterProperties() {

		this.setTitle(this.converter.getTitle());
		this.setSubTitle(this.converter.getSubtitle());
		this.setSpacing(this.converter.getSpacing());
		this.setElementSize(this.converter.getElementSize());
		this.setTitleFontSize(this.converter.getTitleFontSize());
		this.setSubTitleFontSize(this.converter.getSubtitleFontSize());
		this.setPageSize(this.converter.getPageSize());
		this.setLeftMargin(this.converter.getLeftMargin());
		this.setRightMargin(this.converter.getRightMargin());
		this.setMeasureSpace(this.converter.getMeasureSpace());
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilenameWithExtension() {
		return filenameWithExtention;
	}

	public void setFilenameWithExtention(String filenameWithExtention) {
		this.filenameWithExtention = filenameWithExtention;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getPreviewImage() {
		return previewImage;
	}

	public void setPreviewImage(String previewImage) {
		this.previewImage = previewImage;
	}

	public int getTitleFontSize() {
		return titleFontSize;
	}

	public void setTitleFontSize(int titleFontSize) {
		this.titleFontSize = titleFontSize;
	}

	public int getSubTitleFontSize() {
		return subTitleFontSize;
	}

	public void setSubTitleFontSize(int subTitleFontSize) {
		this.subTitleFontSize = subTitleFontSize;
	}

	public int getElementSize() {
		return elementSize;
	}

	public void setElementSize(int measureFontSize) {
		this.elementSize = measureFontSize;
	}

	public float getSpacing() {
		return spacing;
	}

	public void setSpacing(float spacing) {
		this.spacing = spacing;
	}

	public void setOutputFilename(String outputpath) {
		this.outputpath = outputpath;
	}

	public String getOutputFilename() {
		return this.outputpath;
	}
	
	public void setPageSize(Rectangle pagesize) {
		this.pageSize = pagesize;
	}
	
	public Rectangle getPageSize() {
		return this.pageSize;
	}
	
	public void setLeftMargin(float leftmargin) {
		this.leftMargin = leftmargin;
	}
	
	public float getLeftMargin() {
		return this.leftMargin;
	}
	
	public void setRightMargin(float rightmargin) {
		this.rightMargin = rightmargin;
	}
	
	public float getRightMargin() {
		return this.rightMargin;
	}
	
	public void setMeasureSpace(float measurespace) {
		this.measureSpace = measurespace;
	}
	
	public float getMeasureSpace() {
		return this.measureSpace;
	}

}
