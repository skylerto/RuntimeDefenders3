/* 
 * (UPDATE) Change the import name for SwingGUI
 * 
 * -Ron
 */

package mvcV4;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.imageio.ImageIO;

import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;
import org.jpedal.fonts.FontMappings;

public class IMGMaker {

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

		File pdfFile = new File("/path/to/pdf.pdf");
		RandomAccessFile raf = new RandomAccessFile(pdfFile, "r");
		FileChannel channel = raf.getChannel();
		ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
		PDFFile pdf = new PDFFile(buf);
		PDFPage page = pdf.getPage(0);

		// create the image
		Rectangle rect = new Rectangle(0, 0, (int) page.getBBox().getWidth(),
		                                 (int) page.getBBox().getHeight());
		BufferedImage bufferedImage = new BufferedImage(rect.width, rect.height,
		                                  BufferedImage.TYPE_INT_RGB);

		Image image = page.getImage(rect.width, rect.height,    // width & height
		                            rect,                       // clip rect
		                            null,                       // null for the ImageObserver
		                            true,                       // fill background with white
		                            true                        // block until drawing is done
		);
		Graphics2D bufImageGraphics = bufferedImage.createGraphics();
		bufImageGraphics.drawImage(image, 0, 0, null);
		ImageIO.write(bufferedImage, format, new File( "/path/to/image.jpg" ));
	}
	
	public static void setLastConverted(String name) {
		lastConverted = name;
	}

	public static String getLastConverted() {
		return lastConverted;
	}
}
