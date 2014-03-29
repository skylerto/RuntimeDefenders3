/* 
 * (UPDATE) Change the import name for SwingGUI
 * 
 * -Ron
 */

package mvcV4;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;
import org.jpedal.fonts.FontMappings;

import version11.TextToPDFv11;

/**
 * Sample image creator - converts the first page of a pdf into png NOTE: Code
 * only for testing basic jpedal functions
 * 
 * CHANGE LOG:
 * 
 * v0.1: - Turned main method into a static method. - Now communicates with
 * view, to update the image.
 */
public class IMGCreator {

	static int counter = 0;

	private static String lastConverted;
	Model model;

	public static void createPreview(Model model) {
		/* CONSTANTS */

		String INPUT_PDFFILE = model.getOutputFilename();
		// "outputfiles/"+ model.getFilename().substring(0,
		// model.getFilename().indexOf('.') - 1)

		File outputfile = new File(model.getOutputFilename().substring(0,
				model.getOutputFilename().indexOf('.'))
				+ ".png"); // Location of
		// Image
		// file
		//System.out.println(INPUT_PDFFILE + " " + outputfile);

		/** instance of PdfDecoder to convert PDF into image */
		PdfDecoder decode_pdf = new PdfDecoder(true);

		/** set mappings for non-embedded fonts to use */
		FontMappings.setFontReplacements();

		/** open the PDF file - can also be a URL or a byte array */
		try {
			decode_pdf.openPdfFile(INPUT_PDFFILE); // Location of PDF file

			decode_pdf.setExtractionMode(0, 1f);
			BufferedImage img = decode_pdf.getPageAsImage(1); // Page to convert
			
			/* Rescale image */
			int w = img.getWidth();
			int h = img.getHeight();
			
			BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			AffineTransform at = new AffineTransform();
			at.scale(1, 1);
			AffineTransformOp scaleOp = 
			   new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			after = scaleOp.filter(img, after);

			
			try {
				ImageIO.write(after, "png", outputfile); // Saving the image to
														// png
			} catch (IOException exception) {
			}

			/** close the PDF file */
			decode_pdf.closePdfFile();

		} catch (PdfException e) {
			e.printStackTrace();
		}

		model.setPreviewImage(outputfile.getAbsolutePath());
		setLastConverted(outputfile.toString());
	}

	public static void setLastConverted(String name) {
		lastConverted = name;
	}

	public static String getLastConverted() {
		return lastConverted;
	}
}
