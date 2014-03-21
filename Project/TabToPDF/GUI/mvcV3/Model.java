package mvcV3;

public class Model {

	protected String title; // Holds the value of the title.
	protected String subTitle; // Holds the value of the subTitle
	protected String previewImage; // Holds the pathname of previewImage

	protected int titleFontSize;
	protected int subTitleFontSize;
	protected int measureFontSize;

	protected int spacing;

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
			int titleFontSize, int subTitleFontSize, int measureFontSize,
			int spacing) {
		this.title = title;
		this.subTitle = subTitle;
		this.previewImage = previewImage;
		this.titleFontSize = titleFontSize;
		this.subTitleFontSize = subTitleFontSize;
		this.measureFontSize = measureFontSize;
		this.spacing = spacing;
	}

	protected String getTitle() {
		return title;
	}

	protected void setTitle(String title) {
		this.title = title;
	}

	protected String getSubTitle() {
		return subTitle;
	}

	protected void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	protected String getPreviewImage() {
		return previewImage;
	}

	protected void setPreviewImage(String previewImage) {
		this.previewImage = previewImage;
	}

	protected int getTitleFontSize() {
		return titleFontSize;
	}

	protected void setTitleFontSize(int titleFontSize) {
		this.titleFontSize = titleFontSize;
	}

	protected int getSubTitleFontSize() {
		return subTitleFontSize;
	}

	protected void setSubTitleFontSize(int subTitleFontSize) {
		this.subTitleFontSize = subTitleFontSize;
	}

	protected int getMeasureFontSize() {
		return measureFontSize;
	}

	protected void setMeasureFontSize(int measureFontSize) {
		this.measureFontSize = measureFontSize;
	}

	protected int getSpacing() {
		return spacing;
	}

	protected void setSpacing(int spacing) {
		this.spacing = spacing;
	}

}
