package mvcV3;

public class Model {

	protected String title; // Holds the value of the title.
	protected String subTitle; // Holds the value of the subTitle
	protected String previewImage; // Holds the pathname of previewImage

	protected int titleFontSize;
	protected int subTitleFontSize;
	protected int measureFontSize;

	protected String filename;
	protected String filenameWithExtention;

	protected float spacing;
	protected String outputpath;

	/**
	 * Deafault constructor, initializes strings to be empty integers to be 0.
	 */
	public Model() {

		this.title = "";
		this.subTitle = "";
		this.previewImage = "";
		this.titleFontSize = 0;
		this.subTitleFontSize = 0;
		this.measureFontSize = 0;
		this.spacing = 0;
		this.filename = "";
		this.filenameWithExtention = "";

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
		this.title = title;
		this.filename = filename;
		this.filenameWithExtention = filenameWithExtension;
		this.subTitle = subTitle;
		this.previewImage = previewImage;
		this.titleFontSize = titleFontSize;
		this.subTitleFontSize = subTitleFontSize;
		this.measureFontSize = measureFontSize;
		this.spacing = spacing;
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

	public int getMeasureFontSize() {
		return measureFontSize;
	}

	public void setMeasureFontSize(int measureFontSize) {
		this.measureFontSize = measureFontSize;
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

}
