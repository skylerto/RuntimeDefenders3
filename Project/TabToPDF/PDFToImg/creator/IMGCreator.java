/* 
 * (UPDATE) Change the import name for SwingGUI
 * 
 * -Ron
 */

package creator;

import swing.SwingGUI;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;
import org.jpedal.fonts.FontMappings;


/**
 * Sample image creator - converts the first page of a pdf into png
 * NOTE: Code only for testing basic jpedal functions
 * 
 * CHANGE LOG:
 * 
 * v0.1: 
 * 		-	Turned main method into a static method.
 * 		-	Now communicates with view, to update the image.
 */
public class IMGCreator
{
	/* CONSTANTS */
	
	public static String OUTPUT_IMGFILE = "outputfiles/musicIMG.png";
	public static String INPUT_PDFFILE = "outputfiles/musicPDF.pdf";
	
	
	
	
	public static void createPreview()
	{
		File outputfile = new File(OUTPUT_IMGFILE);						// Location of Image file

		/** instance of PdfDecoder to convert PDF into image */
		PdfDecoder decode_pdf = new PdfDecoder(true);

		/** set mappings for non-embedded fonts to use */
		FontMappings.setFontReplacements();

		/** open the PDF file - can also be a URL or a byte array */
		try
		{
			decode_pdf.openPdfFile(INPUT_PDFFILE);					// Location of PDF file

			decode_pdf.setExtractionMode(0, 1f);
			BufferedImage img = decode_pdf.getPageAsImage(1);			// Page to convert

			try
			{
				ImageIO.write(img, "png", outputfile);					// Saving the image to png
				SwingGUI.setPreviewImage(outputfile);
				SwingGUI.updateTopBox();	
			} catch (IOException exception) {}
			
			/** close the PDF file */
			decode_pdf.closePdfFile();
			
		} catch (PdfException e)
		{
			e.printStackTrace();
		}
		System.out.println("Successfully converted " + INPUT_PDFFILE + " to " + OUTPUT_IMGFILE);
	}
}