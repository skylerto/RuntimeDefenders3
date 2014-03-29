package version13;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tabparts.TabString;

import com.itextpdf.text.*;

/**
 * Contains and handles all the document values that are changeable by
 * the user from the GUI. An instance of this object is created for every
 * TextToPDF object. This class is able to extract the title, subtitle and
 * spacing values from the input file. If no values are found then default
 * values are used.
 * 
 * @author Ron
 *
 */
public class PDFProperties {
	
	/* CONSTANTS */
	
	public static final String DEFAULT_TITLE = "My Guitar Tab";
	public static final String DEFAULT_SUBTITLE = "";
	public static final float DEFAULT_SPACING = 5.0f;
	public static final int DEFAULT_ELEMENTSIZE = 8;
	public static final Rectangle DEFAULT_PAGESIZE = PageSize.LETTER;
	public static final int DEFAULT_TITLE_FONTSIZE = 16;
	public static final int DEFAULT_SUBTITLE_FONTSIZE = 14;
	public static final float DEFAULT_LEFTMARGIN = 36f;
	public static final float DEFAULT_RIGHTMARGIN = 36f;
	public static final float DEFAULT_MEASURESPACE = 80f;
	
	public static final float MAX_SPACING = 90f;
	public static final float MIN_SPACING = 1f;
	
	public static final int VALUE_POSITION = 3;		// The position in the patterns where information is extracted from
	public static final Pattern TITLE_PATTERN = Pattern.compile("(%\\s*TITLE\\s*=)(\\s*)([\\w+\\s*]+)(\\s*%)", Pattern.CASE_INSENSITIVE);		// Extracts the title after the equal sign of "Title="
	public static final Pattern SUBTITLE_PATTERN = Pattern.compile("(%\\s*SUBTITLE\\s*=)(\\s*)([\\w+\\s*]+)(\\s*%)", Pattern.CASE_INSENSITIVE);	// Extracts the subtitle after the equal sign of "Subtitle="
	public static final Pattern SPACING_PATTERN = Pattern.compile("(%\\s*SPACING\\s*=)(\\s*)(\\d*(\\.)?\\d+)(?![0-9\\.])(\\s*%)", Pattern.CASE_INSENSITIVE);	// Extracts the spacing after the equal sign of "Spacing="
	
	/* ATTRIBUTES */
	
	private String title;
	private String subtitle;
	private float spacing;
	private int elementsize;
	private Rectangle pagesize;
	private int title_fontsize;
	private int subtitle_fontsize;
	private float leftmargin;
	private float rightmargin;
	private float measurespace;
	
	/**
	 * Creates properties with default values.
	 */
	public PDFProperties (){
		this.title = DEFAULT_TITLE;
		this.subtitle = DEFAULT_SUBTITLE;
		this.spacing = DEFAULT_SPACING;
		this.elementsize = DEFAULT_ELEMENTSIZE;
		this.pagesize = DEFAULT_PAGESIZE;
		this.title_fontsize = DEFAULT_TITLE_FONTSIZE;
		this.subtitle_fontsize = DEFAULT_SUBTITLE_FONTSIZE;
		this.leftmargin = DEFAULT_LEFTMARGIN;
		this.rightmargin = DEFAULT_RIGHTMARGIN;
		this.measurespace = DEFAULT_MEASURESPACE;
	}
	
	/**
	 * Creates properties with the given values.
	 * 
	 * @param title	The title of the PDF document.
	 * @param subtitle The subtitle of the PDF document.
	 * @param spacing	The spacing between numbers in the staff.
	 * @param elementsize	The size of the numbers and symbols in the staff.
	 * @param pagesize	The size of the PDF document.
	 */
	public PDFProperties (String title, String subtitle, float spacing) {
		this();
		this.title = title;
		this.subtitle = subtitle;
		this.spacing = spacing;
	}
	
	/**
	 * Extracts default values from the input file. The values in the file
	 * can be in any order as long as they are the first 3 non-empty lines.
	 * 
	 * Example if the input has the first three lines:
	 * 
	 * Title = Music Sheet
	 * Subtitle = By Ron
	 * Spacing = 4.5
	 * 
	 * It sets title to "Music Sheet", subtitle to "By Ron" and spacing to 4.5.
	 * 
	 * @param file
	 */
	public void extractProperties(File file) {
		File input = file;
		BufferedReader stream;
		try {
			String line;
			Matcher m_title, m_subtitle, m_spacing;
			StringBuffer extractable = new StringBuffer();
			stream = new BufferedReader(new FileReader(input));
			
			/* Read all comment lines from the input file and concatenate them */
			while ((line = stream.readLine()) != null) {
				line = line.replaceAll("\\s+$", "");
				TabString temp = new TabString(line);
				if (!line.isEmpty()) {
					if (temp.checkError() == TabString.ERROR_COMMENT)
						extractable.append("%" + line + "%");
					else
						break;
				}
				
			}
			/* Return if nothing was found */
			if (extractable.length() == 0) {
				stream.close();
				return;
			}
			
			m_title = TITLE_PATTERN.matcher(extractable.toString());
			m_subtitle = SUBTITLE_PATTERN.matcher(extractable.toString());
			m_spacing = SPACING_PATTERN.matcher(extractable.toString());
			
			/* Set the title to the extracted title */
			if (m_title.find())
				this.setTitle(m_title.group(VALUE_POSITION));
			
			/* Set the subtitle to the extracted subtitle */
			if (m_subtitle.find())
				this.setSubtitle(m_subtitle.group(VALUE_POSITION));
			
			/* Set the spacing to the extracted spacing */
			if (m_spacing.find())
				this.setSpacing(Float.parseFloat(m_spacing.group(VALUE_POSITION)));
				
			stream.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Set the PDF title.
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Set the PDF subtitle.
	 * 
	 * @param subtitle
	 */
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	
	/**
	 * Set the staff spacing. Keeps the spacing within bounds.
	 * 
	 * @param spacing
	 */
	public void setSpacing(float spacing) {
		if (spacing > MAX_SPACING) 
			this.spacing = MAX_SPACING;
		else if (spacing < MIN_SPACING)
			this.spacing = MIN_SPACING;
		else
			this.spacing = spacing;
	}
	
	/**
	 * Set the element size.
	 * 
	 * @param elementsize
	 */
	public void setElementSize(int elementsize) {
		this.elementsize = elementsize;
	}
	
	/**
	 * Set the page size.
	 * 
	 * @param size
	 */
	public void setPageSize(Rectangle pagesize) {
		this.pagesize = pagesize;
	}
	
	/**
	 * Set the size of the title.
	 * @param size
	 */
	public void setTitleFontSize(int size) {
		this.title_fontsize = size;
	}
	
	/**
	 * Set the size of the subtitle.
	 * @param size
	 */
	public void setSubtitleFontSize(int size) {
		this.subtitle_fontsize = size;
	}
	
	/**
	 * Sets the left margin.
	 * @param leftmargin
	 */
	public void setLeftMargin(float leftmargin) {
		this.leftmargin = leftmargin;
	}
	
	/**
	 * Sets the right margin.
	 * @param rightmargin
	 */
	public void setRightMargin(float rightmargin) {
		this.rightmargin = rightmargin;
	}
	
	/**
	 * Sets the space between measures.
	 * @param topmargin
	 */
	public void setMeasureSpace(float measurespace) {
		this.measurespace = measurespace;
	}
	
	/**
	 * Get the title.
	 * 
	 * @return
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Get the subtitle.
	 * @return
	 */
	public String getSubtitle() {
		return this.subtitle;
	}
	
	/**
	 * Get the spacing.
	 * @return
	 */
	public float getSpacing() {
		return this.spacing;
	}
	
	/**
	 * Get the element size.
	 * @return
	 */
	public int getElementSize() {
		return this.elementsize;
	}
	
	/**
	 * Get the page size.
	 * @return
	 */
	public Rectangle getPageSize() {
		return this.pagesize;
	}
	
	/**
	 * Get the size of the title.
	 * @return
	 */
	public int getTitleFontSize() {
		return this.title_fontsize;
	}
	
	/**
	 * Get the size of the subtitle.
	 * @return
	 */
	public int getSubtitleFontSize() {
		return this.subtitle_fontsize;
	}
	
	/**
	 * Get left margin.
	 * @return
	 */
	public float getLeftMargin() {
		return this.leftmargin;
	}
	
	/**
	 * Get right margin.
	 * @return
	 */
	public float getRightMargin() {
		return this.rightmargin;
	}
	
	/**
	 * Get measure space.
	 * @return
	 */
	public float getMeasureSpace() {
		return this.measurespace;
	}
	
	/**
	 * Returns string representation:
	 * 
	 * [(title), (subtitle), (spacing), (elementsize), (pagewidth,pageheight), (titlesize), (subtitlesize)]
	 */
	@Override
	public String toString() {
		return ("[" + this.getTitle() + ", " + this.getSubtitle() + ", " + 
				this.getSpacing() + ", " + this.getElementSize() + 
				", (" + this.getPageSize().getWidth() + "," + this.getPageSize().getHeight() + "), " +
				this.getTitleFontSize() + ", " + this.getSubtitleFontSize() + "]");
	}
}
