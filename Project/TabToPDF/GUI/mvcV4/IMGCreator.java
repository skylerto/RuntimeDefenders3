package mvcV4;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jpedal.PdfDecoder;
import org.jpedal.fonts.FontMappings;

/**
 * Creates a png image from the first page of the passed pdf document from
 * model.
 */
public class IMGCreator
{
	static int counter = 0;
	private static String lastConverted;
	Model model;

	public static void createPreview(Model model)
	{
		/* CONSTANTS */
		String INPUT_PDFFILE = model.getOutputFilename();
		// Location of image output
		File outputfile = new File(model.getOutputFilename().substring(0,
				model.getOutputFilename().indexOf('.'))
				+ ".png");

		// instance of PdfDecoder to convert PDF into image
		PdfDecoder decode_pdf = new PdfDecoder(true);

		// set mappings for non-embedded fonts to use
		FontMappings.setFontReplacements();

		try
		{
			decode_pdf.openPdfFile(INPUT_PDFFILE); // Location of PDF file

			decode_pdf.setExtractionMode(0, 1f);
			BufferedImage img = decode_pdf.getPageAsImage(1); // Page to convert

			/* Rescale image */
			int w = img.getWidth();
			int h = img.getHeight();
			BufferedImage after = new BufferedImage(w, h,
					BufferedImage.TYPE_INT_ARGB);
			AffineTransform at = new AffineTransform();
			at.scale(1, 1);
			AffineTransformOp scaleOp = new AffineTransformOp(at,
					AffineTransformOp.TYPE_BILINEAR);
			after = scaleOp.filter(img, after);

			// save to png
			ImageIO.write(after, "png", outputfile);

			// close the file
			decode_pdf.closePdfFile();

		} catch (org.jpedal.exception.PdfException | IOException e)
		{
			e.printStackTrace();
		}

		model.setPreviewImage(outputfile.getAbsolutePath());
		setLastConverted(outputfile.toString());
	}

	public static void setLastConverted(String name)
	{
		lastConverted = name;
	}

	public static String getLastConverted()
	{
		return lastConverted;
	}
}
