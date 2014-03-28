package version13;

import java.io.*;
import java.util.List;
import java.util.regex.Pattern;

import tabparts.LargeNumberException;
import tabparts.TabStaff;
import version13.DrawClass;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

/**
 * Takes an input text file resembling a guitar tab and converts it into a PDF file in the 
 * designated location. 
 *
 */

public class TextToPDFv13 {
	
	/* CONSTANTS */
	
	private static final String DEFAULT_INPUTPATH = "inputfiles/try.txt";
	private static final String DEFAULT_OUTPUTPATH = "outputfiles/musicPDF.pdf";
	
	/* ATTRIBUTES */
	
	private String inputpath;
	private String outputpath;
	private PDFProperties properties;
	private TabStaff staff;

	/**
	 * Creates a new convert object with default paths and properties.
	 * 
	 * @throws NoFileExistsException
	 * @throws CannotReadFileException
	 * @throws EmptyFileException
	 * @throws NoMusicException
	 * @throws LargeNumberException
	 */
	public TextToPDFv13 () throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException {
		this.inputpath = DEFAULT_INPUTPATH;
		this.outputpath = DEFAULT_OUTPUTPATH;
		this.properties = new PDFProperties();
		this.staff = new TabStaff();
		
		/* Check input for errors */
		this.checkInputErrors();
		
		/* Extract properties from the input file */
		this.properties.extractProperties(new File(this.inputpath));
		
		/* Store the tab in a staff and fix errors */
		try {
			this.staff.scanFile(new File(this.inputpath));		
		} catch (LargeNumberException e) {
			throw new LargeNumberException ("Invalid tab in file: " + this.getInputPath() + "\n" + e.getMessage());
		}
	}
	
	/**
	 * Creates a new conversion object with the given input and output paths.
	 * 
	 * @param outputpath
	 * @param inputpath
	 * @throws NoFileExistsException
	 * @throws CannotReadFileException
	 * @throws EmptyFileException
	 * @throws NoMusicException
	 * @throws LargeNumberException
	 */
	public TextToPDFv13 (String outputpath, String inputpath) throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException {
		this.inputpath = inputpath;
		this.outputpath = outputpath;
		this.properties = new PDFProperties();
		this.staff = new TabStaff();
		
		/* Check input for errors */
		this.checkInputErrors();
		
		/* Extract properties from the input file */
		this.properties.extractProperties(new File(this.inputpath));
		
		/* Store the tab in a staff and fix errors */
		try {
			this.staff.scanFile(new File(this.inputpath));
		} catch (LargeNumberException e) {
			throw new LargeNumberException ("Invalid tab in file: " + this.getInputPath() + "\n" + e.getMessage());
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
		MusicNoteProcess sp;
		Document doc = new Document(this.getPageSize());
		DrawClass draw = new DrawClass();
		int same_line_state = 0;
		List<List<String>> dynamic_array = staff.getList();
		
		try {
			writer = PdfWriter.getInstance(doc, new FileOutputStream(new File(this.getOutputPath())));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			throw new ConversionException("Error detected creating PDF for file: " + this.getInputPath());
		}
		
		doc.open();
		writer.open();
		cb = writer.getDirectContent();
		
		try {
			doc.open();
			writer.open();
			cb = writer.getDirectContent();
			
			float currX = 36.0f;
			float currY = 680.0f;

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

			for (int i = 0; i < dynamic_array.size(); i++) {
				sp = new MusicNoteProcess(dynamic_array.get(i));
				if (draw.getMusicNotelength(sp.getSymbolsList(), this.getSpacing()) < ((writer.getPageSize().getWidth() - 36f) - currX)) {
					if (i == 0) {
						same_line_state = 0;
						draw.DrawMarginMusicLines(sp.getSymbolsList(), 0, currY, 36f, this.getElementSize(), cb);
					} else
						same_line_state = 1;

					draw.DrawMusicNote(sp.getSymbolsList(), currX, currY, this.getSpacing(), this.getElementSize(), same_line_state, cb);
					currX = currX + draw.getMusicNotelength(sp.getSymbolsList(), this.getSpacing());

				} else {
					draw.DrawMarginMusicLines(new MusicNoteProcess(dynamic_array.get(i - 1)).getSymbolsList(), currX,
							currY, writer.getPageSize().getWidth(), this.getElementSize(), cb);
					if (currY > 120) {
						currX = 36.0f;
						currY = currY - 80;
						same_line_state = 0;
						draw.DrawMarginMusicLines(sp.getSymbolsList(), 0, currY, 36f, this.getElementSize(), cb); // for begining
						draw.DrawMusicNote(sp.getSymbolsList(), currX, currY, this.getSpacing(), this.getElementSize(), same_line_state, cb);
						currX = currX + draw.getMusicNotelength(sp.getSymbolsList(), this.getSpacing());
					} else if (currY <= 120 && i < dynamic_array.size() - 1) {
						doc.newPage();
						same_line_state = 0;
						currX = 36.0f;
						currY = 750.0f;
						draw.DrawMarginMusicLines(sp.getSymbolsList(), 0, currY, 36f, this.getElementSize(), cb); // for begining
						draw.DrawMusicNote(sp.getSymbolsList(), currX, currY, this.getSpacing(), this.getElementSize(), same_line_state, cb);
						currX = currX + draw.getMusicNotelength(sp.getSymbolsList(), this.getSpacing());

					} else if (currY <= 120 && i == dynamic_array.size() - 1) {
						doc.newPage();
						same_line_state = 0;
						currX = 36.0f;
						currY = 750.0f;
						draw.DrawMarginMusicLines(sp.getSymbolsList(), 0, currY, 36f, this.getElementSize(), cb); // for begining
						draw.DrawMusicNote(sp.getSymbolsList(), currX, currY, this.getSpacing(), this.getElementSize(), same_line_state, cb);
						currX = currX + draw.getMusicNotelength(sp.getSymbolsList(), this.getSpacing());
					}
				}
			}
			draw.DrawMarginMusicLines(new MusicNoteProcess(dynamic_array.get(dynamic_array.size() - 1))
							.getSymbolsList(), currX, currY, writer.getPageSize().getWidth(), this.getElementSize(), cb);
			doc.close();
			writer.close();

		} catch (IOException e) {
			throw new ConversionException("Error detected creating the PDF file for: " + this.getInputPath());
		} catch (DocumentException e) {
			throw new ConversionException("Error detected creating the PDF file for: " + this.getInputPath());
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
		Pattern music = Pattern.compile("([|][^\\s]+)|([^\\s]+[|])");
		
		/* Checks input file existence */
		if (!test.exists())
			throw new NoFileExistsException("The file: " + this.inputpath + " does not exist!");
		
		/* Checks if input file is readable and is a file */
		else if (!test.canRead() || !test.isFile())
			throw new CannotReadFileException("The file: " + this.inputpath + " cannot be read!");
		
		/* Checks if the file is empty */
		try {
			stream = new BufferedReader(new FileReader(test));
			if (stream.readLine() == null) {
				stream.close();
				throw new EmptyFileException("The file: " + this.inputpath + " is empty!");
			}
			stream.close();
		} catch (FileNotFoundException e) {
			throw new NoFileExistsException("The file: " + this.inputpath + " does not exist!");
		} catch (IOException e) {
			throw new CannotReadFileException("The file: " + this.inputpath + " cannot be read!");
		}
		
		/* Checks if the file has tabulature that can be converted */
		try {
			boolean hasmusic = false;
			stream = new BufferedReader(new FileReader(test));
			String line;
			while ((line = stream.readLine()) != null) {
				if (music.matcher(line).find()) {
					hasmusic = true;
					break;
				}
			}
			stream.close();
			if (!hasmusic) {
				throw new NoMusicException("The file: " + this.inputpath + " has no detectable tabulature!");
			}
		} catch (FileNotFoundException e) {
			throw new NoFileExistsException("The file: " + this.inputpath + " does not exist!");
		} catch (IOException e) {
			throw new CannotReadFileException("The file: " + this.inputpath + " cannot be read!");
		}
	}

	public void updateTitle(String title) throws ConversionException {
		this.properties.setTitle(title);
		this.WriteToPDF();
	}
	
	public void updateSubtitle(String subtitle) throws ConversionException {
		this.properties.setSubtitle(subtitle);
		this.WriteToPDF();
	}
	
	public void updateSpacing(float spacing) throws ConversionException {
		this.properties.setSpacing(spacing);
		this.WriteToPDF();
	}
	
	public void updateElementSize(int elementsize) throws ConversionException {
		this.properties.setElementSize(elementsize);
		this.WriteToPDF();
	}
	
	public void updatePageSize(String pagesize) throws ConversionException {
		/*if (pagesize.equals(""));
		this.properties.setElementSize(pagesize);
		this.WriteToPDF();*/
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
	public int getElementSize() {
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
	 * Gets the output path.
	 * @return
	 */
	public String getOutputPath() {
		return this.outputpath;
	}
}
