package tabparts;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tabparts.PageSize.Size;

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
	
	public static final int EXTRACTABLE_VALUES = 3;				// The 3 values extracted: title, subtitle, spacing
	public static final String DEFAULT_TITLE = "My Guitar Tab";
	public static final String DEFAULT_SUBTITLE = "Author";
	public static final float DEFAULT_SPACING = 5.0f;
	public static final float DEFAULT_ELEMENTSIZE = 12.0f;
	public static final Size DEFAULT_PAGESIZE = Size.LETTER;
	
	public static final int VALUE_POSITION = 3;		// The position in the patterns where information is extracted from
	public static final Pattern TITLE_PATTERN = Pattern.compile("(%\\s*TITLE\\s*=)(\\s*)([\\w+\\s*]+)(\\s*%)", Pattern.CASE_INSENSITIVE);		// Extracts the title after the equal sign of "Title="
	public static final Pattern SUBTITLE_PATTERN = Pattern.compile("(%\\s*SUBTITLE\\s*=)(\\s*)([\\w+\\s*]+)(\\s*%)", Pattern.CASE_INSENSITIVE);	// Extracts the subtitle after the equal sign of "Subtitle="
	public static final Pattern SPACING_PATTERN = Pattern.compile("(%\\s*SPACING\\s*=)(\\s*)(\\d*(\\.)?\\d+)(?![0-9\\.])(\\s*%)", Pattern.CASE_INSENSITIVE);	// Extracts the spacing after the equal sign of "Spacing="
	
	/* ATTRIBUTES */
	
	private String title;
	private String subtitle;
	private float spacing;
	private float elementsize;
	private PageSize pagesize;
	
	/**
	 * Creates properties with default values.
	 */
	public PDFProperties (){
		this.title = DEFAULT_TITLE;
		this.subtitle = DEFAULT_SUBTITLE;
		this.spacing = DEFAULT_SPACING;
		this.elementsize = DEFAULT_ELEMENTSIZE;
		this.pagesize = new PageSize(DEFAULT_PAGESIZE);
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
	public PDFProperties (String title, String subtitle, float spacing, float elementsize, PageSize.Size pagesize) {
		this.title = title;
		this.subtitle = subtitle;
		this.spacing = spacing;
		this.elementsize = elementsize;
		this.pagesize = new PageSize(pagesize);
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
			int i = 0;
			String line;
			Matcher m_title, m_subtitle, m_spacing;
			StringBuffer extractable = new StringBuffer();
			stream = new BufferedReader(new FileReader(input));
			
			/* Read the first 3 non-empty lines from the input file and concatenate them */
			while ((line = stream.readLine()) != null && i < EXTRACTABLE_VALUES) {
				line = line.replaceAll("\\s+$", "");
				if (!line.isEmpty()) {
					extractable.append("%" + line + "%");
					i++;
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
	 * Set the staff spacing.
	 * 
	 * @param spacing
	 */
	public void setSpacing(float spacing) {
		this.spacing = spacing;
	}
	
	/**
	 * Set the element size.
	 * 
	 * @param elementsize
	 */
	public void setElementSize(float elementsize) {
		this.elementsize = elementsize;
	}
	
	/**
	 * Set the page size.
	 * 
	 * @param size
	 */
	public void setPageSize(Size size) {
		this.pagesize.setSize(size);;
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
	public float getElementSize() {
		return this.elementsize;
	}
	
	/**
	 * Get the page size.
	 * @return
	 */
	public int getPageSize() {
		return this.pagesize.getPageSize();
	}
}
