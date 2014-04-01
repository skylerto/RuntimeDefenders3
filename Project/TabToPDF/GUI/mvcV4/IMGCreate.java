package mvcV4;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.imageio.ImageIO;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

public class IMGCreate {
	private static String lastConverted;
	Model model;

	public static void createPreview(Model model) {
		try {
			String INPUT_PDFFILE = model.getOutputFilename();

			String output = model.getOutputFilename().substring(0,
					model.getOutputFilename().indexOf('.'));
			File sourceFile = new File(INPUT_PDFFILE);
			File destinationFile = new File(output);

			RandomAccessFile raf = new RandomAccessFile(sourceFile, "r");
			FileChannel channel = raf.getChannel();
			ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0,
					channel.size());
			PDFFile pdf = new PDFFile(buf);

			PDFPage page = pdf.getPage(0);

			// image dimensions
			int width = (int) model.getPageSize().getWidth() * 2;
			int height = (int) model.getPageSize().getHeight() * 2;

			// create the image
			Rectangle rect = new Rectangle(0, 0, (int) page.getBBox()
					.getWidth(), (int) page.getBBox().getHeight());
			BufferedImage bufferedImage = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);

			Image image = page.getImage(width, height, rect, null, true, true);
			Graphics2D bufImageGraphics = bufferedImage.createGraphics();
			bufImageGraphics.drawImage(image, 0, 0, null);

			File outputfile = new File(model.getOutputFilename().substring(0,
					model.getOutputFilename().indexOf('.'))
					+ ".jpg");

			ImageIO.write(bufferedImage, "jpg", outputfile);

			model.setPreviewImage(outputfile.getAbsolutePath());
			setLastConverted(outputfile.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setLastConverted(String name) {
		lastConverted = name;
	}

	public static String getLastConverted() {
		return lastConverted;
	}
}