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

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFImageWriter;
import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;
import org.jpedal.fonts.FontMappings;

/**
 * Sample image creator - converts the first page of a pdf into png NOTE: Code
 * only for testing basic jpedal functions
 * 
 * CHANGE LOG:
 * 
 * v0.1: - Turned main method into a static method. - Now communicates with
 * view, to update the image.
 */
public class IMGMaker
{
	private static String lastConverted;
	Model model;

	public static void createPreview(Model model)
	{
		/* CONSTANTS */

		String INPUT_PDFFILE = model.getOutputFilename();

		File outputfile = new File(model.getOutputFilename().substring(0,
				model.getOutputFilename().indexOf('.'))
				+ "1.png");

		String password = "";
		String output = model.getOutputFilename().substring(0,
				model.getOutputFilename().indexOf('.'));
		String imageType = "png";
		int pageNumber = 1;

		PDDocument document = null;

		System.out.println(outputfile.getAbsolutePath());
		try
		{
			document = PDDocument.load(new File(INPUT_PDFFILE));
			// Make the call
			PDFImageWriter W = new PDFImageWriter();

			W.writeImage(document, imageType, password, pageNumber, pageNumber,
					output, BufferedImage.TYPE_INT_RGB, 200);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
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
