package version11;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import mvcV2.Model;
import tabparts.TabStaff;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class TextToPDFv11 {

	float titleFontSize;
	float subTitleFontSize;

	/* fist stable version */

	float LINE_SPACE = 8.0f;
	final float TITLE_SIZE = 26.0f;
	int FONT_SIZE; // SAME AS MEASUREFONTSIZE
	final float SUBTITLE_SIZE = 12.0f;
	final String CONTAINS_TITLE = "TITLE";
	final String CONTAINS_SUBTITLE = "SUBTITLE";
	final String CONTAINS_SPACING = "SPACING";
	public static String INPUT_FILENAME = "inputfiles/try.txt";
	public static String PDF_FILENAME = "outputfiles/musicPDF.pdf";
	private int same_line_state = 0;
	/* new */
	private Document doc;
	private PdfWriter writer;
	private PdfContentByte cb;
	private ReadFromInput file;
	private DrawClass draw;
	private TabStaff staff;

	/* */
	static String titleString;
	static String subtitleString;
	static String tempTitle;
	static String tempSubtitle;

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
	public TextToPDFv11(String outputpath, String inputpath)
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
		Model.setOutputFilename(outputpath);
		draw = new DrawClass();
		doc = new Document(PageSize.LETTER);
		writer = PdfWriter.getInstance(doc, new FileOutputStream(new File(
				outputpath)));
	}

	public void WriteToPDF() throws DocumentException, IOException {

		titleString = Model.getTitle();
		subtitleString = Model.getSubtitle();
		titleFontSize = Model.getTitleFontSize();
		subTitleFontSize = Model.getSubTitleFontSize();
		FONT_SIZE = Model.getMeasureFontSize();

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

			List<List<String>> dynamic_array = draw.StringAnchor(staff
					.getList());
			// List<List<String>> dynamic_array =
			// draw.StringAnchor(file.getList());
			System.out.printf("size is %d\n", dynamic_array.size());
			float currX = 36.0f;
			float currY = 680.0f;

			for (int i = 0; i < dynamic_array.size(); i++) {

				if (draw.getMusicNotelength(dynamic_array.get(i), LINE_SPACE,
						FONT_SIZE) < ((writer.getPageSize().getWidth() - 36f) - currX)) {
					if (i == 0) {

						same_line_state = 0;
						draw.DrawEndingLines(dynamic_array.get(i), 0, currY,
								36f, FONT_SIZE, cb);
					} else
						same_line_state = 1;

					draw.DrawMusicNote(dynamic_array.get(i), currX, currY,
							LINE_SPACE, FONT_SIZE, same_line_state, cb);

					currX = currX
							+ draw.getMusicNotelength(dynamic_array.get(i),
									LINE_SPACE, FONT_SIZE);

				} else {

					draw.DrawEndingLines(dynamic_array.get(i - 1), currX,
							currY, writer.getPageSize().getWidth(), FONT_SIZE,
							cb);

					if (currY > 120) {
						currX = 36.0f;
						currY = currY - 80;
						same_line_state = 0;

						draw.DrawMusicNote(dynamic_array.get(i), currX, currY,
								LINE_SPACE, FONT_SIZE, same_line_state, cb);
						draw.DrawEndingLines(dynamic_array.get(i), 0, currY,
								36f, FONT_SIZE, cb); // for begining
						currX = currX
								+ draw.getMusicNotelength(dynamic_array.get(i),
										LINE_SPACE, FONT_SIZE);

					} else if (currY <= 120 && i < dynamic_array.size() - 1) {
						doc.newPage();
						same_line_state = 0;
						currX = 36.0f;
						currY = 750.0f;
						draw.DrawEndingLines(dynamic_array.get(i), 0, currY,
								36f, FONT_SIZE, cb); // for begining

					}

				}

			}
			draw.DrawEndingLines(dynamic_array.get(dynamic_array.size() - 1),
					currX, currY, writer.getPageSize().getWidth(), FONT_SIZE,
					cb);
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
			TextToPDFv11 saad = new TextToPDFv11(PDF_FILENAME, INPUT_FILENAME);
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
