package mvcV4;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import version13.CannotReadFileException;
import version13.ConversionException;
import version13.EmptyFileException;
import version13.NoFileExistsException;
import version13.NoMusicException;
import version13.TextToPDF;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;

public class ModelTest {
     
	Model alpha;
	Model bravo;
	@Before
	public void setUp() throws Exception {
		
		alpha = new Model();
		
	}

	@Test
	public void test_modelDefault() {
		assertEquals("",alpha.getTitle());
		assertEquals("",alpha.getSubTitle());
		assertEquals("",alpha.getPreviewImage());
		assertEquals(0,alpha.getSubTitleFontSize());
		assertEquals(0,alpha.getTitleFontSize());
		assertEquals(0,alpha.getStaffSize());
		assertEquals(0,(int)alpha.getSpacing());
		Rectangle r = new Rectangle(0f, 0f);
		assertEquals(r,alpha.getPageSize());
		assertEquals(0,(int)alpha.getLeftMargin());
		assertEquals(0,(int)alpha.getRightMargin());
		assertEquals(0,(int)alpha.getMeasureSpace());
		assertEquals("",alpha.getFilenameWithExtension());
		assertEquals("",alpha.getFilename());
		assertEquals(alpha.outputpath,alpha.getOutputFilename());
	}

	@Test
	public void test_2ndConstructor(){
		
		//Setting the following values to model
		alpha = new Model("a","b","c","d","e",1,2,3,4);
		String expect_title = "a";
		String expect_subtitle = "b";
		String expect_PreviewImage = "c";
		String expect_filename = "d";
		String expect_filename_extension = "e";
		int expect_titleFontSize = 1;
		int expect_subtitleFontSize = 2;
		int expect_staffSize = 3;
		float expect_spacing = 4;
		
		assertEquals(expect_title, alpha.getTitle());
		assertEquals(expect_subtitle, alpha.getSubTitle());
		assertEquals(expect_PreviewImage, alpha.getPreviewImage());
		assertEquals(expect_filename, alpha.getFilename());
		assertEquals(expect_filename_extension, alpha.getFilenameWithExtension());
		assertEquals(expect_titleFontSize, alpha.getTitleFontSize());
		assertEquals(expect_subtitleFontSize, alpha.getSubTitleFontSize());
		assertEquals(expect_staffSize, alpha.getStaffSize());
		assertEquals((int)expect_spacing, (int)alpha.getSpacing());
			
	}
	
	@Test
	public void test_SetMethods(){
		
		//Setting the following values to model
		alpha = new Model();
		alpha.setTitle("a");
		alpha.setSubTitle("b");
		alpha.setPreviewImage("c");
		alpha.setFilename("d");
		alpha.setFilenameWithExtention("e");
		alpha.setTitleFontSize(1);
		alpha.setSubTitleFontSize(2);
		alpha.setStaffSize(3);
		alpha.setSpacing(4);
		
		assertEquals("a", alpha.getTitle());
		assertEquals("b", alpha.getSubTitle());
		assertEquals("c", alpha.getPreviewImage());
		assertEquals("d", alpha.getFilename());
		assertEquals("e", alpha.getFilenameWithExtension());
		assertEquals(1, alpha.getTitleFontSize());
		assertEquals(2, alpha.getSubTitleFontSize());
		assertEquals(3, alpha.getStaffSize());
		assertEquals((int)4, (int)alpha.getSpacing());
		
		alpha.setOutputFilename("hello.txt");
		assertEquals("hello.txt",alpha.outputpath);	
		Rectangle r1 = new Rectangle(PageSize.LETTER); // LEDGER, LEGAL
		alpha.setPageSize(r1);
		assertEquals(r1,alpha.getPageSize());
		Rectangle r2 = new Rectangle(PageSize.LEDGER);
		alpha.setPageSize(r2);
		assertEquals(r2,alpha.getPageSize());
		Rectangle r3 = new Rectangle(PageSize.LEGAL);
		alpha.setPageSize(r3);
		assertEquals(r3,alpha.getPageSize());
		
		alpha.setLeftMargin(1.5f);
		assertEquals((int)1.5f,(int)alpha.getLeftMargin());
		
		alpha.setRightMargin(3.5f);
		assertEquals((int)3.5f,(int)alpha.getRightMargin());
		
		alpha.setMeasureSpace(5.5f);
		assertEquals((int)5.5f,(int)alpha.getMeasureSpace());
	}
	
	@Test
	public void test_initialize_converter() throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException{
		//this tests for getconverterproperties also
		
		bravo = new Model("Moonlight Sonata","Daylight Sonata","","fixed","inputfiles/fixed.txt",5,4,2,5.0f);
		bravo.initializeConverter();
		TextToPDF expt = new TextToPDF(bravo.getOutputFilename(),"inputfiles/fixed.txt");
		assertEquals(expt.getOutputPath(),bravo.converter.getOutputPath());
		assertEquals(expt.getInputPath(),bravo.converter.getInputPath());
		
	
		assertEquals(expt.getProperties().getTitle(),bravo.getTitle());
		assertEquals(expt.getProperties().getSubtitle(),bravo.getSubTitle());
		assertEquals(expt.getProperties().getTitleFontSize(),bravo.getTitleFontSize());
        assertEquals(expt.getProperties().getSubtitleFontSize(),bravo.getSubTitleFontSize());
        assertEquals((int)expt.getProperties().getSpacing(),(int)bravo.getSpacing());
        assertEquals(expt.getProperties().getPageSize(),bravo.getPageSize());
        assertEquals((int)expt.getProperties().getRightMargin(),(int)bravo.getRightMargin());
        assertEquals((int)expt.getProperties().getLeftMargin(),(int)bravo.getLeftMargin());
        assertEquals((int)expt.getProperties().getMeasureSpace(),(int)bravo.getMeasureSpace());
        
        
	}
	
	@Test
	public void test_run_converter() throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, ConversionException, FileNotFoundException{
		bravo = new Model("Moonlight Sonata","Daylight Sonata","","fixed","inputfiles/fixed.txt",5,4,2,5.0f);
		bravo.initializeConverter();
		
		PrintStream ps = new PrintStream("outputfiles/out2.txt");
		PrintStream orig = System.out;
		System.setOut(ps);
		bravo.runConverter();
		System.setOut(orig);
		ps.close();
		Scanner in = new Scanner(new FileReader("outputfiles/out2.txt"));
		String passed_in = in.nextLine();
		String result = "Successfully converted the file inputfiles/fixed.txt to PDF: ./musicPDF.pdf";
		File f = new File("outputfiles/autofixlog.txt");
		
		if (f != null && result.equals(passed_in)){
			assertTrue(true);
		}
		
		else{
			fail();
		}
		
	}
	
	@Test
	public void test_convertPagesizeToString() throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException{
		
		String expt = "";
		String out = "";
		out = alpha.convertPageSizeToString(new Rectangle(PageSize.LETTER));
		expt = View.LETTER;
		assertTrue(expt.equals(out));
		out = alpha.convertPageSizeToString(new Rectangle(PageSize.LEDGER));
		expt = View.LEDGER;
		assertTrue(expt.equals(out));
		out = alpha.convertPageSizeToString(new Rectangle(PageSize.LEGAL));
		expt = View.LEGAL;
		assertTrue(expt.equals(out));
		out = alpha.convertPageSizeToString(new Rectangle(PageSize.A0));
		expt = View.LETTER;
		assertTrue(expt.equals(out));
	}
	
	@Test
	public void test_convertPagesizeToRectangle() throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException{
		
		Rectangle out = new Rectangle(0,0);
		Rectangle expt = new Rectangle(0,0);
		out = alpha.convertPageSizeToRectangle(View.LETTER);
		expt = PageSize.LETTER;
		assertTrue(expt.equals(out));
		out = alpha.convertPageSizeToRectangle(View.LEDGER);
		expt = PageSize.LEDGER;
		assertTrue(expt.equals(out));
		out = alpha.convertPageSizeToRectangle(View.LEGAL);
		expt = PageSize.LEGAL;
		assertTrue(expt.equals(out));
	}


	

}
