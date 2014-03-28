package version12;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import mvcV2.Model;
import tabparts.TabStaff;
import version12.ReadFromInput;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class TextToPDFv12 {

	float titleFontSize;
	float subTitleFontSize;

	/* fist stable version */

	float LINE_SPACE = 5.0f;
	final float TITLE_SIZE = 26.0f;
	int FONT_SIZE; // SAME AS MEASUREFONTSIZE
	final float SUBTITLE_SIZE = 12.0f;
	final String CONTAINS_TITLE = "TITLE";
	final String CONTAINS_SUBTITLE = "SUBTITLE";
	final String CONTAINS_SPACING = "SPACING";
	public static String INPUT_FILENAME = "inputfiles/try3.txt";
	public static String PDF_FILENAME = "outputfiles/musicPDF.pdf";
	private int same_line_state = 0;
	/* new */
	private Document doc;
	private PdfWriter writer;
	private PdfContentByte cb;
	public ReadFromInput file;
	private DrawClassv12 draw;
	private TabStaff staff;
	private MusicNoteProcess sp;

	/* */
	public String titleString;
	public String subtitleString;
	public String tempTitle;
	public String tempSubtitle;

	/**
	 * 
	 * @param outputpath
	 *            - Path (excluding file name) to where the output file should
	 *            go.
	 * @param inputpath
	 *            - Path (including file name) to where the desired text file
	 *            is.
	 * @throws DocumentException
	 * @throws IOException
	 */
	public TextToPDFv12(String outputpath, String inputpath)
			throws DocumentException, IOException {

		titleFontSize = Model.getTitleFontSize();
		subTitleFontSize = Model.getSubTitleFontSize();
		FONT_SIZE = Model.getMeasureFontSize();

		System.out.println(outputpath);
		file = new ReadFromInput(inputpath);

		if (Model.getTitle().isEmpty()) {
			titleString = file.getTITLE();
		} else {
			titleString = Model.getTitle();
		}
		if (Model.getSubtitle().isEmpty()) {
			subtitleString = file.getSUBTITLE();
		} else {
			subtitleString = Model.getSubtitle();
		}
		Model.setTitle(titleString);
		Model.setSubtitle(subtitleString);

		staff = new TabStaff();
		try {
			staff.scanFile(new File(inputpath));
		} catch (Exception e) {
			System.exit(0);

		}
		System.out.println(staff.toString());

		String filename;

		// ALGORITHM TO SET OUTPUT NAME OF PDF.
		if (!(file.titleIsEmpty())) {
			filename = file.getTITLE() + ".pdf";
		} else {
			filename = Model.getFilename() + ".pdf";
		}

		outputpath = outputpath + filename;

		draw = new DrawClassv12();
		doc = new Document(PageSize.LETTER);
		writer = PdfWriter.getInstance(doc, new FileOutputStream(new File(
				outputpath)));
	}

	public void WriteToPDF() throws DocumentException, IOException {

		try {
			doc.open();
			writer.open();
			cb = writer.getDirectContent();

			BaseFont bf_title = BaseFont.createFont(BaseFont.HELVETICA,
					BaseFont.CP1250, BaseFont.EMBEDDED);
			Font title_font = new Font(bf_title, titleFontSize);
			Paragraph title = new Paragraph(titleString, title_font);
			title.setAlignment(Element.TITLE);
			doc.add(title);

			BaseFont bf_subtitle = BaseFont.createFont(BaseFont.HELVETICA,
					BaseFont.CP1250, BaseFont.EMBEDDED);
			Font subtitle_font = new Font(bf_subtitle, subTitleFontSize);
			Paragraph subtitle = new Paragraph(subtitleString, subtitle_font);
			subtitle.setAlignment(Element.ALIGN_CENTER);
			doc.add(subtitle);
			if (file.getSACING() == 0)
				file.setLineSpacing(5.0f);

			LINE_SPACE = file.getSACING();
			System.out.println(LINE_SPACE);

			List<List<String>> dynamic_array = staff.getList();
			// List<List<String>> dynamic_array = file.getList();
			float currX = 36.0f;
			float currY = 680.0f;

			for (int i = 0; i < dynamic_array.size(); i++) {
				boolean enable_repeat = false;
				sp = new MusicNoteProcess(dynamic_array.get(i));
				if (staff.getMeasureRepeat(i) > 1) {
					enable_repeat = true;
					System.out.println(staff.getMeasureRepeat(i) );
				}
				if (draw.getMusicNotelength(sp.getSymbolsList(), LINE_SPACE) < ((writer.getPageSize().getWidth() - 36f) - currX)) {
					if (i == 0) {
						same_line_state = 0;
						draw.DrawMarginMusicLines(sp.getSymbolsList(), 0,currY, 36f, FONT_SIZE, cb);
					} else
						same_line_state = 1;

					draw.DrawMusicNote(sp.getSymbolsList(), currX, currY,
							LINE_SPACE, FONT_SIZE, same_line_state, cb);
					currX = currX+ draw.getMusicNotelength(sp.getSymbolsList(),LINE_SPACE);
					if(enable_repeat)
						draw.InsertText("Repeat "+staff.getMeasureRepeat(i)+" times", currX-60f, currY+FONT_SIZE*.8f, FONT_SIZE, cb);

				} else {
					draw.DrawMarginMusicLines(new MusicNoteProcess(dynamic_array.get(i - 1)).getSymbolsList(), currX,currY, writer.getPageSize().getWidth(), FONT_SIZE,cb);
					if (currY > 120) {
						
						currX = 36.0f;
						currY = currY - 80;
						same_line_state = 0;
						draw.DrawMarginMusicLines(sp.getSymbolsList(), 0,currY, 36f, FONT_SIZE, cb); // for begining
						draw.DrawMusicNote(sp.getSymbolsList(), currX, currY,LINE_SPACE, FONT_SIZE, same_line_state, cb);
						currX = currX+ draw.getMusicNotelength(sp.getSymbolsList(),LINE_SPACE);
						if(enable_repeat)
							draw.InsertText("Repeat "+staff.getMeasureRepeat(i)+" times", currX-55f, currY+FONT_SIZE*.8f, FONT_SIZE, cb);
					} else if (currY <= 120 && i < dynamic_array.size() - 1) {
						//draw.printSymbolList();
						draw.drawSymbols(FONT_SIZE, LINE_SPACE, cb);
						draw.FlushSymbol();
						doc.newPage();
						same_line_state = 0;
						currX = 36.0f;
						currY = 750.0f;
						draw.DrawMarginMusicLines(sp.getSymbolsList(), 0,currY, 36f, FONT_SIZE, cb); // for begining
						draw.DrawMusicNote(sp.getSymbolsList(), currX, currY,LINE_SPACE, FONT_SIZE, same_line_state, cb);
						currX = currX+ draw.getMusicNotelength(sp.getSymbolsList(),LINE_SPACE);
						if(enable_repeat)
							draw.InsertText("Repeat "+staff.getMeasureRepeat(i)+" times", currX-55f, currY+FONT_SIZE*.8f, FONT_SIZE, cb);
					} else if (currY <= 120 && i == dynamic_array.size() - 1) {
						draw.drawSymbols(FONT_SIZE, LINE_SPACE, cb);
						draw.FlushSymbol();
						doc.newPage();
						same_line_state = 0;
						currX = 36.0f;
						currY = 750.0f;
						draw.DrawMarginMusicLines(sp.getSymbolsList(), 0,currY, 36f, FONT_SIZE, cb); // for begining
						draw.DrawMusicNote(sp.getSymbolsList(), currX, currY,LINE_SPACE, FONT_SIZE, same_line_state, cb);
						currX = currX+ draw.getMusicNotelength(sp.getSymbolsList(),LINE_SPACE);
						if(enable_repeat)
							draw.InsertText("Repeat "+staff.getMeasureRepeat(i)+" times", currX-55f, currY+FONT_SIZE*.8f, FONT_SIZE, cb);

					}

				}

			}
			draw.DrawMarginMusicLines(new MusicNoteProcess(dynamic_array.get(dynamic_array.size() - 1)).getSymbolsList(), currX, currY, writer.getPageSize().getWidth(), FONT_SIZE, cb);
			currX = currX+ draw.getMusicNotelength(sp.getSymbolsList(),LINE_SPACE);
			if (staff.getMeasureRepeat(staff.size()-1) > 1) 	
			    draw.InsertText("Repeat "+staff.getMeasureRepeat(staff.size()-1)+" times", currX-55f, currY+FONT_SIZE*.8f, FONT_SIZE, cb);
			draw.drawSymbols(FONT_SIZE, LINE_SPACE, cb);
			draw.FlushSymbol();
			doc.close();
			writer.close();

		} catch (Exception e) {

		}

	}

	/**
	 * Sets the INPUTFILENAME.
	 * 
	 * @author Skyler
	 * @param s
	 *            - INPUTFILE name.
	 */
	public static void setInputFileName(String s) {
		INPUT_FILENAME = s;
	}

	public void setLineSpacing(int spacing) {
		this.LINE_SPACE = spacing;
	}

	public float getLineSpacing() {
		return this.LINE_SPACE;
	}

	/**
	 * Sets the INPUTFILENAME.
	 * 
	 * @author Skyler
	 * @param s
	 *            - INPUTFILE name.
	 */
	public static String getInputFileName() {
		return INPUT_FILENAME;
	}

	public static void main(String[] args) {

		try {
			TextToPDFv12 saad = new TextToPDFv12(PDF_FILENAME, INPUT_FILENAME);
			saad.WriteToPDF();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}