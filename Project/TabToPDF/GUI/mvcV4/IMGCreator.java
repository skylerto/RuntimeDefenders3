package mvcV4;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.ghost4j.document.DocumentException;
import org.ghost4j.document.PDFDocument;
import org.ghost4j.renderer.RendererException;
import org.ghost4j.renderer.SimpleRenderer;

/**
 * Sample image creator - converts the first page of a pdf into png NOTE: Code
 * only for testing basic jpedal functions
 * 
 * CHANGE LOG:
 * 
 * v0.1: - Turned main method into a static method. - Now communicates with
 * view, to update the image.
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
		try
		{
			// load PDF document
			PDFDocument document = new PDFDocument();
			document.load(new File(INPUT_PDFFILE));
			// create renderer
			SimpleRenderer renderer = new SimpleRenderer();
			// set resolution (in DPI)
			renderer.setResolution(300);
			// render
			List<Image> images = renderer.render(document);
			// write images to files to disk as PNG
			try
			{
				ImageIO.write((RenderedImage) images.get(0), "png", outputfile);
			} catch (IOException e)
			{
				System.out.println("ERROR: " + e.getMessage());
			}

		} catch (Exception e)
		{
			System.out.println("ERROR: " + e.getMessage());
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