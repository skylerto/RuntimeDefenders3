package version11;

import java.io.*;
import java.util.List;

import MVC.GUIModel;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class TextToPDFv11 {

	/* fist stable version */

	float LINE_SPACE = 5.0f;
	final float TITLE_SIZE = 26.0f;
	final int FONT_SIZE = 8;
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

	public TextToPDFv11(String outputpath, String inputpath)
			throws DocumentException, IOException {

		file = new ReadFromInput(inputpath);
		outputpath = "outputfiles/" + file.getTITLE() + ".pdf";
		GUIModel.setOutputFilename(outputpath);
		draw = new DrawClass();
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
			Font title_font = new Font(bf_title, 26);
			Paragraph title = new Paragraph(file.getTITLE(), title_font);
			title.setAlignment(Element.TITLE);
			doc.add(title);

			BaseFont bf_subtitle = BaseFont.createFont(BaseFont.HELVETICA,
					BaseFont.CP1250, BaseFont.EMBEDDED);
			Font subtitle_font = new Font(bf_subtitle, 14);
			Paragraph subtitle = new Paragraph(file.getSUBTITLE(),
					subtitle_font);
			subtitle.setAlignment(Element.ALIGN_CENTER);
			doc.add(subtitle);
			if (file.getSACING() == 0)
				file.setLineSpacing(5.0f);

			LINE_SPACE = file.getSACING();
			System.out.println(LINE_SPACE);

			List<List<String>> dynamic_array = draw
					.StringAnchor(file.getList());
			System.out.printf("size is %d\n", dynamic_array.size());
			float currX = 36.0f;
			float currY = 680.0f;

			for (int i = 0; i < dynamic_array.size(); i++) {

				if (draw.getMusicNotelength(dynamic_array.get(i), LINE_SPACE,
						FONT_SIZE) < (writer.getPageSize().getWidth() - currX)) {

					if (currY <= 120.0f) {

						draw.DrawMusicNote(dynamic_array.get(i), currX, currY,
								LINE_SPACE, FONT_SIZE, same_line_state, cb);

						draw.DrawEndingLines(
								dynamic_array.get(i),
								currX
										+ draw.getMusicNotelength(
												dynamic_array.get(i),
												LINE_SPACE, FONT_SIZE), currY,
								writer.getPageSize().getWidth(), FONT_SIZE, cb);
						doc.newPage();
						same_line_state = 0;
						currX = 36.0f;
						currY = 750.0f;
					} else {

						if (i == 0) {

							same_line_state = 0;
						} else
							same_line_state = 1;

						draw.DrawMusicNote(dynamic_array.get(i), currX, currY,
								LINE_SPACE, FONT_SIZE, same_line_state, cb);
						currX = currX
								+ draw.getMusicNotelength(dynamic_array.get(i),
										LINE_SPACE, FONT_SIZE);
						draw.DrawEndingLines(dynamic_array.get(i), 0, currY,
								36f, FONT_SIZE, cb);

					}

				} else {
					draw.DrawEndingLines(dynamic_array.get(i), currX, currY,
							writer.getPageSize().getWidth(), FONT_SIZE, cb);
					currX = 36.0f;
					currY = currY - 80;
					same_line_state = 0;
					draw.DrawMusicNote(dynamic_array.get(i), currX, currY,
							LINE_SPACE, FONT_SIZE, same_line_state, cb);

					currX = currX
							+ draw.getMusicNotelength(dynamic_array.get(i),
									LINE_SPACE, FONT_SIZE);
					draw.DrawEndingLines(dynamic_array.get(i), 0, currY, 36f,
							FONT_SIZE, cb); // for begining
				}
				if (currY <= 120.0f) {
					if (i < dynamic_array.size() - 1) {
						if (draw.getMusicNotelength(dynamic_array.get(i + 1),
								LINE_SPACE, FONT_SIZE) < (writer.getPageSize()
								.getWidth() - currX)) {
							draw.DrawMusicNote(dynamic_array.get(i + 1), currX,
									currY, LINE_SPACE, FONT_SIZE,
									same_line_state, cb);
							draw.DrawEndingLines(
									dynamic_array.get(i + 1),
									currX
											+ draw.getMusicNotelength(
													dynamic_array.get(i),
													LINE_SPACE, FONT_SIZE),
									currY, writer.getPageSize().getWidth(),
									FONT_SIZE, cb);
							i += 1;
						}

					}

					doc.newPage();
					same_line_state = 0;
					currX = 36.0f;
					currY = 750.0f;
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
