package version13;

import java.io.*;
import java.util.List;
import tabparts.LargeNumberException;
import tabparts.TabStaff;
import tabparts.TabString;
import version13.MusicNoteProcess;
import version13.DrawClass;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

/* UPDATE: Added a split stack attribute */

/**
 * Takes an input text file resembling a guitar tab and converts it into a PDF file in the 
 * designated location. 
 *
 */

public class TextToPDF {
	
	/* CONSTANTS */
	
	public static final String DEFAULT_INPUTPATH = "inputfiles/try.txt";
	public static final String DEFAULT_OUTPUTPATH = "outputfiles/musicPDF.pdf";
	private static final float TOPTITLE_MARGIN = 135;
	private static final float TOP_MARGIN = 42;
	private static final float BOT_MARGIN = 80;
	
	private static final String NOFILE_MSG = "The file doesn't exist! Please choose another file.";
	private static final String CANNOTREAD_MSG = "The file cannot be read! Please choose another file.";
	private static final String CANNOTCONVERT_MSG = "The file cannot be converted! Please close the PDF if you are trying to reconvert it.";
	private static final String EMPTY_MSG = "The file is empty! Please choose another file.";
	private static final String NOMUSIC_MSG = "The file has no tabulature! Please choose another file.";
	
	/* ATTRIBUTES */
	
	private String inputpath;
	private String outputpath;
	private PDFProperties properties;
	private TabStaff staff;
	private SplitStack splitstack;	// Used for merging back split measures

	
	/**
	 * Creates a new conversion object with the given input and output paths.
	 * Extracts title, subtitle and spacing from the input file.
	 * 
	 * @param outputpath
	 * @param inputpath
	 * @throws NoFileExistsException
	 * @throws CannotReadFileException
	 * @throws EmptyFileException
	 * @throws NoMusicException
	 * @throws LargeNumberException
	 */
	public TextToPDF (String outputpath, String inputpath) throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException {
		this.inputpath = inputpath;
		this.outputpath = outputpath;
		this.properties = new PDFProperties();
		this.staff = new TabStaff();
		this.splitstack = new SplitStack();
		
		/* Check input for errors */
		this.checkInputErrors();
		
		/* Extract properties from the input file */
		this.properties.extractProperties(new File(this.inputpath));
		
		/* Store the tab in a staff and fix errors */
		this.staff.scanFile(new File(this.inputpath));
		
		/* Checks if staff has music */
		if (this.staff.size() == 0) {
			throw new NoMusicException(NOMUSIC_MSG);
		}
	}
	
	/**
	 * Draws the tab staff into a PDF document.
	 * 
	 * @throws ConversionException
	 */
	public void WriteToPDF() throws ConversionException {

		PdfWriter writer = null;
		PdfContentByte cb;
		MusicNoteProcess sp = null;
		Document doc = new Document(this.getPageSize());
		DrawClass draw = new DrawClass();
		int same_line_state = 0;
		
		this.restoreStaff(); // Restores split measures
		this.splitStaff();	// Splits the measures in the staff if they are too long to fit onto the page
		
		List<List<String>> dynamic_array = staff.getList();
		
		try {
			writer = PdfWriter.getInstance(doc, new FileOutputStream(new File(this.getOutputPath())));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			throw new ConversionException(CANNOTCONVERT_MSG);
		}
		
		doc.open();
		writer.open();
		cb = writer.getDirectContent();
		
		try {
			doc.open();
			writer.open();
			cb = writer.getDirectContent();
			
			float currX = this.getLeftMargin();
			float currY = this.getPageSize().getHeight() - TOPTITLE_MARGIN;

			/* Set the title of the PDF document */
			BaseFont bf_title = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
			Font title_font = new Font(bf_title, this.getTitleFontSize());
			Paragraph title = new Paragraph(this.getTitle(), title_font);
			title.setAlignment(Element.TITLE);
			doc.add(title);

			/* Set the subtitle of the PDF document */
			BaseFont bf_subtitle = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
			Font subtitle_font = new Font(bf_subtitle, this.getSubtitleFontSize());
			Paragraph subtitle = new Paragraph(this.getSubtitle(), subtitle_font);
			subtitle.setAlignment(Element.ALIGN_CENTER);
			doc.add(subtitle);

			/* Loop through the staff and write all measures to the PDF */
			for (int i = 0; i < dynamic_array.size(); i++) {
				
				boolean enable_repeat = false;
				sp = new MusicNoteProcess(dynamic_array.get(i));
				
				if (staff.getMeasureRepeat(i) > 1) {
					enable_repeat = true;
				}
				if (draw.getMusicNotelength(sp.getSymbolsList(), this.getSpacing()) < ((writer.getPageSize().getWidth() - this.getRightMargin()) - currX)) {
					if (i == 0) {
						same_line_state = 0;
						draw.DrawMarginMusicLines(sp.getSymbolsList(), 0, currY, this.getLeftMargin(), this.getStaffSize(), cb);
					} else
						same_line_state = 1;

					draw.DrawMusicNote(sp.getSymbolsList(), currX, currY, this.getSpacing(), this.getStaffSize(), same_line_state, cb);
					currX = currX + draw.getMusicNotelength(sp.getSymbolsList(), this.getSpacing());
					if(enable_repeat)
						draw.InsertText("Repeat "+staff.getMeasureRepeat(i)+" times", currX-60f, currY+this.getStaffSize()*.8f, this.getStaffSize(), cb);
				} else {
					draw.DrawMarginMusicLines(new MusicNoteProcess(dynamic_array.get(i - 1)).getSymbolsList(), currX,
							currY, writer.getPageSize().getWidth(), this.getStaffSize(), cb);
					if (currY - this.getMeasureSpace() > BOT_MARGIN ) {
						currX = this.getLeftMargin();
						currY = currY - this.getMeasureSpace();
						same_line_state = 0;
						draw.DrawMarginMusicLines(sp.getSymbolsList(), 0, currY, this.getLeftMargin(), this.getStaffSize(), cb); // for begining
						draw.DrawMusicNote(sp.getSymbolsList(), currX, currY, this.getSpacing(), this.getStaffSize(), same_line_state, cb);
						currX = currX + draw.getMusicNotelength(sp.getSymbolsList(), this.getSpacing());
						if(enable_repeat)
							draw.InsertText("Repeat "+staff.getMeasureRepeat(i)+" times", currX-55f, currY+this.getStaffSize()*.8f, this.getStaffSize(), cb);
					} else if (currY - this.getMeasureSpace() <= BOT_MARGIN && i < dynamic_array.size() - 1) {
						draw.drawSymbols(this.getStaffSize(), this.getSpacing(), cb);
						draw.FlushSymbol();
						doc.newPage();
						same_line_state = 0;
						currX = this.getLeftMargin();
						currY = this.getPageSize().getHeight() - TOP_MARGIN;
						draw.DrawMarginMusicLines(sp.getSymbolsList(), 0, currY, this.getLeftMargin(), this.getStaffSize(), cb); // for begining
						draw.DrawMusicNote(sp.getSymbolsList(), currX, currY, this.getSpacing(), this.getStaffSize(), same_line_state, cb);
						currX = currX + draw.getMusicNotelength(sp.getSymbolsList(), this.getSpacing());
						if(enable_repeat)
							draw.InsertText("Repeat "+staff.getMeasureRepeat(i)+" times", currX-55f, currY+this.getStaffSize()*.8f, this.getStaffSize(), cb);
					} else if (currY - this.getMeasureSpace() <= BOT_MARGIN && i == dynamic_array.size() - 1) {
						draw.drawSymbols(this.getStaffSize(), this.getSpacing(), cb);
						draw.FlushSymbol();
						doc.newPage();
						same_line_state = 0;
						currX = this.getLeftMargin();
						currY = this.getPageSize().getHeight() - TOP_MARGIN;
						draw.DrawMarginMusicLines(sp.getSymbolsList(), 0, currY, this.getLeftMargin(), this.getStaffSize(), cb); // for begining
						draw.DrawMusicNote(sp.getSymbolsList(), currX, currY, this.getSpacing(), this.getStaffSize(), same_line_state, cb);
						currX = currX + draw.getMusicNotelength(sp.getSymbolsList(), this.getSpacing());
						if(enable_repeat)
							draw.InsertText("Repeat "+staff.getMeasureRepeat(i)+" times", currX-55f, currY+this.getStaffSize()*.8f, this.getStaffSize(), cb);
					}
				}
				draw = new DrawClass();
			}
			
			draw.DrawMarginMusicLines(new MusicNoteProcess(dynamic_array.get(dynamic_array.size() - 1))
							.getSymbolsList(), currX, currY, writer.getPageSize().getWidth(), this.getStaffSize(), cb);
			currX = currX+ draw.getMusicNotelength(sp.getSymbolsList(),this.getStaffSize());
			draw.drawSymbols(this.getStaffSize(), this.getSpacing(), cb);
			draw.FlushSymbol();
			doc.close();
			writer.close();
			
			System.out.println("Successfully converted the file " + this.getInputPath() + " to PDF: " + this.getOutputPath());
			//System.out.println(this.properties.toString());

		} catch (IOException e) {
			throw new ConversionException(CANNOTCONVERT_MSG);
		} catch (DocumentException e) {
			throw new ConversionException(CANNOTCONVERT_MSG);
		}

	}

	/**
	 * Checks the input file for errors.
	 * 
	 * @throws NoFileExistsException
	 * @throws CannotReadFileException
	 * @throws EmptyFileException
	 * @throws NoMusicException
	 */
	public void checkInputErrors() throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException {
		File test = new File(inputpath);
		BufferedReader stream;
		
		/* Checks input file existence */
		if (!test.exists())
			throw new NoFileExistsException(NOFILE_MSG);
		
		/* Checks if input file is readable and is a file */
		else if (!test.canRead() || !test.isFile())
			throw new CannotReadFileException(CANNOTREAD_MSG);
		
		/* Checks if the file is empty */
		try {
			stream = new BufferedReader(new FileReader(test));
			if (stream.readLine() == null) {
				stream.close();
				throw new EmptyFileException(EMPTY_MSG);
			}
			stream.close();
		} catch (FileNotFoundException e) {
			throw new NoFileExistsException(NOFILE_MSG);
		} catch (IOException e) {
			throw new CannotReadFileException(CANNOTREAD_MSG);
		}
		
		/* Checks if the file has tabulature that can be converted */
		try {
			boolean hasmusic = false;
			stream = new BufferedReader(new FileReader(test));
			String line;
			while ((line = stream.readLine()) != null) {
				if (TabString.VALID_STRING.matcher(line).find()) {
					hasmusic = true;
					break;
				}
			}
			stream.close();
			if (!hasmusic) {
				throw new NoMusicException(NOMUSIC_MSG);
			}
		} catch (FileNotFoundException e) {
			throw new NoFileExistsException(NOFILE_MSG);
		} catch (IOException e) {
			throw new CannotReadFileException(CANNOTREAD_MSG);
		}
	}

	/**
	 * Splits each measure in the staff that is too long to fit in the page width.
	 * Remembers the current state of the staff before splitting so it can revert the change.
	 * @throws ConversionException 
	 */
	public void splitStaff() throws ConversionException {
		float innerwidth = this.getPageSize().getWidth() - this.getLeftMargin() - this.getRightMargin();
		int maxchars = (int) (innerwidth/this.getSpacing());
		SplitMarker mark = new SplitMarker(this.staff, maxchars);	// Remember current staff
		//System.out.println("marking " + maxchars);
		if (this.staff.splitLongMeasures(maxchars)) {

			this.splitstack.push(mark);

		}
		
	}
	
	/**
	 * Restores a previous state of the staff if the spacing decreases past a point
	 * when it was previously split.
	 * @throws ConversionException
	 */
	public boolean restoreStaff() throws ConversionException {
		float innerwidth = this.getPageSize().getWidth() - this.getLeftMargin() - this.getRightMargin();
		int maxchars = (int) (innerwidth/this.getSpacing());
		boolean restored = false;
		if (this.splitstack.size != 0) {
			int size = this.splitstack.size;
			for (int i = 0; i < size; i++) {
				if (this.splitstack.peak().maxchars < maxchars) {
					SplitMarker a = this.splitstack.pop();
					
					this.staff.copyStaff(a.staff);
					restored = true;
				} else break;
			}
		}
		return restored;
	}
	
	/**
	 * Changes the title and recreates the PDF document.
	 * @param title
	 * @throws ConversionException
	 */
	public void updateTitle(String title) throws ConversionException {
		this.properties.setTitle(title);
		this.WriteToPDF();
	}
	
	/**
	 * Changes the subtitle and recreates the PDF document.
	 * @param subtitle
	 * @throws ConversionException
	 */
	public void updateSubtitle(String subtitle) throws ConversionException {
		this.properties.setSubtitle(subtitle);
		this.WriteToPDF();
	}
	
	/**
	 * Changes the spacing and and recreates the PDF document.
	 * @param spacing
	 * @throws ConversionException
	 */
	public void updateSpacing(float spacing) throws ConversionException {
		this.properties.setSpacing(spacing);
		this.WriteToPDF();
	}
	
	/**
	 * Changes the element size and recreates the PDF document.
	 * @param elementsize
	 * @throws ConversionException
	 */
	public void updateElementSize(int elementsize) throws ConversionException {
		this.properties.setElementSize(elementsize);
		this.WriteToPDF();
	}
	
	/**
	 * Changes the page size and recreates the PDF document.
	 * @param pagesize
	 * @throws ConversionException
	 */
	public void updatePageSize(Rectangle pagesize) throws ConversionException {
		this.properties.setPageSize(pagesize);
		this.WriteToPDF();
	}
	
	/**
	 * Changes the title font size and recreates the PDF document.
	 * @param titlesize
	 * @throws ConversionException
	 */
	public void updateTitleSize(int titlesize) throws ConversionException {
		this.properties.setTitleFontSize(titlesize);
		this.WriteToPDF();
	}
	
	/**
	 * Changes the subtitle font size and recreates the PDF document.
	 * @param subtitlesize
	 * @throws ConversionException
	 */
	public void updateSubtitleSize(int subtitlesize) throws ConversionException {
		this.properties.setSubtitleFontSize(subtitlesize);
		this.WriteToPDF();
	}
	
	/**
	 * Changes the left margin and recreates the PDF document.
	 * @param leftmargin
	 * @throws ConversionException
	 */
	public void updateLeftMargin(float leftmargin) throws ConversionException {
		this.properties.setLeftMargin(leftmargin);
		this.WriteToPDF();
	}
	
	/**
	 * Changes right margin and recreates the PDF document.
	 * @param rightmargin
	 * @throws ConversionException
	 */
	public void updateRightMargin(float rightmargin) throws ConversionException {
		this.properties.setRightMargin(rightmargin);
		this.WriteToPDF();
	}
	
	/**
	 * Changes measure space and recreates the PDF document.
	 * @param measurespace
	 * @throws ConversionException
	 */
	public void updateMeasureSpace(float measurespace) throws ConversionException {
		this.properties.setMeasureSpace(measurespace);
		this.WriteToPDF();
	}
	
	/**
	 * Sets the output path.
	 * @param path
	 */
	public void setOutputPath(String path) {
		this.outputpath = path;
	}
	
	/**
	 * Get the properties.
	 * @return
	 */
	public PDFProperties getProperties() {
		return this.properties;
	}
	
	/**
	 * Get the title.
	 * @return
	 */
	public String getTitle() {
		return this.properties.getTitle();
	}
	
	/**
	 * Get the subtitle.
	 * @return
	 */
	public String getSubtitle() {
		return this.properties.getSubtitle();
	}
	
	/**
	 * Get the spacing.
	 * @return
	 */
	public float getSpacing() {
		return this.properties.getSpacing();
	}
	
	/**
	 * Get the element size.
	 * @return
	 */
	public int getStaffSize() {
		return this.properties.getElementSize();
	}
	
	/**
	 * Get the page size as a rectangle object.
	 * @return
	 */
	public Rectangle getPageSize() {
		return this.properties.getPageSize();
	}
	
	/**
	 * Gets the title font size.
	 * @return
	 */
	public int getTitleFontSize() {
		return this.properties.getTitleFontSize();
	}
	
	/**
	 * Gets the subtitle font size.
	 * @return
	 */
	public int getSubtitleFontSize() {
		return this.properties.getSubtitleFontSize();
	}
	
	/**
	 * Gets the input path.
	 * @return
	 */
	public String getInputPath() {
		return this.inputpath;
	}
	
	/**
	 * Gets the left margin.
	 * @return
	 */
	public float getLeftMargin() {
		return this.properties.getLeftMargin();
	}
	
	/**
	 * Gets the right margin.
	 * @return
	 */
	public float getRightMargin() {
		return this.properties.getRightMargin();
	}
	
	/**
	 * Gets the measure space.
	 * @return
	 */
	public float getMeasureSpace() {
		return this.properties.getMeasureSpace();
	}
	
	/**
	 * Gets the output path.
	 * @return
	 */
	public String getOutputPath() {
		return this.outputpath;
	}
	
	/* 
	 * 
	 * FOR TESTING 
	 * 
	 * 
	 * */
	public static void main(String[] args) throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException, ConversionException {

		
		TextToPDF conversion = new TextToPDF
				("outputfiles/musicPDF.pdf", "inputfiles/try.txt");
		//conversion.updateLeftMargin(100f);
		conversion.WriteToPDF();
		System.out.println(conversion.getProperties().toString());
		//System.out.println(conversion.staff.toString());
	}
}
