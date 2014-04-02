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
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFImageWriter;

/**
 * Image Creator
 */
public class IMGCreator
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
					output, BufferedImage.TYPE_INT_RGB, 180);
			document.close();
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