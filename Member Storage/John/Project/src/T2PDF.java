import java.io.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.itextpdf.text.*;
import com.itextpdf.text.Font.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.*;

public class T2PDF
{
	public static String INPUT_FILENAME = "inputfiles/try2.txt";
	public static String OUTPUT_FILENAME = "outputfiles/musicPDF.pdf";

	public static void main(String[] args)
	{
		T2PDF outputFile = new T2PDF();
		try
		{
			outputFile.createPDF(OUTPUT_FILENAME);
		} catch (DocumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{

		}
	}

	public void createPDF(String pdfname) throws DocumentException, IOException
	{
		PrintStream output = new PrintStream(System.out);

		Document group1 = new Document(PageSize.LETTER);
		File RESULT = new File(pdfname);
		PdfWriter document = PdfWriter.getInstance(group1,
				new FileOutputStream(RESULT));

		group1.open();
		document.open();
		PdfContentByte cb = document.getDirectContent();
		BaseFont bf1 = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1250,
				BaseFont.EMBEDDED);
		BaseFont bf_title = BaseFont.createFont(BaseFont.HELVETICA,
				BaseFont.CP1250, BaseFont.EMBEDDED);
		Font title_font = new Font(bf_title, 26);
		BaseFont bf_subtitle = BaseFont.createFont(BaseFont.HELVETICA,
				BaseFont.CP1250, BaseFont.EMBEDDED);
		Font subtitle_font = new Font(bf_subtitle, 14);
		FileReader file = new FileReader(INPUT_FILENAME);
		BufferedReader inputStream = null;
	}
}