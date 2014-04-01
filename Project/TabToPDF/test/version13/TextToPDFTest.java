package version13;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import tabparts.LargeNumberException;
import tabparts.TabStaff;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;


public class TextToPDFTest {
	
	TextToPDF tp1;

	@Before
	public void setUp() throws Exception {
		
	}
	
     
	@Test(expected = EmptyFileException.class)
	public void test_Constructor_case0() throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException {
		String a = "";
		String b = "inputfiles/case0.txt";
		tp1 = new TextToPDF(a,b);	
		PDFProperties properties = new PDFProperties();
		properties.extractProperties(new File(b));
		SplitStack stack = new SplitStack();
		TabStaff ts = new TabStaff();
		ts.scanFile(new File(b));
		
		assertEquals(b,tp1.getInputPath()); 
		assertEquals(a,tp1.getOutputPath()); 
        assertTrue(properties.equals(tp1.getProperties()));
		assertEquals(properties.getElementSize(),tp1.getStaffSize());

		
	}
	
	@Test (expected = NoMusicException.class)
	public void  test_Constructor_case01() throws ConversionException, NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException, DocumentException, IOException{
		String a = TextToPDF.DEFAULT_OUTPUTPATH;
		String b = "inputfiles/case01.txt";
		tp1 = new TextToPDF(a,b);
	}
	
	@Test(expected = LargeNumberException.class)
	public void test_Constructor_case13() throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException {
		String a = "";
		String b = "inputfiles/case13.txt";
		tp1 = new TextToPDF(a,b);	
		PDFProperties properties = new PDFProperties();
		properties.extractProperties(new File(b));
		SplitStack stack = new SplitStack();
		TabStaff ts = new TabStaff();
		ts.scanFile(new File(b));
		
		assertEquals(b,tp1.getInputPath()); 
		assertEquals(a,tp1.getOutputPath()); 
        assertTrue(properties.equals(tp1.getProperties()));
		assertEquals(properties.getElementSize(),tp1.getStaffSize());

		
	}

	@Test
	public void test_Constructor_case1() throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException {
		String a = "";
		String b = "inputfiles/fixed.txt";
		tp1 = new TextToPDF(a,b);	
		PDFProperties properties = new PDFProperties();
		properties.extractProperties(new File(b));
		SplitStack stack = new SplitStack();
		TabStaff ts = new TabStaff();
		ts.scanFile(new File(b));
		
		assertEquals(b,tp1.getInputPath()); 
		assertEquals(a,tp1.getOutputPath()); 
	     assertTrue(properties.equals(tp1.getProperties()));
		assertEquals(properties.getElementSize(),tp1.getStaffSize());

		
	}
	
	@Test
	public void test_Constructor_case2() throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException {
		String a = "";
		String b = "inputfiles/case5.txt";
		tp1 = new TextToPDF(a,b);	
		PDFProperties properties = new PDFProperties();
		properties.extractProperties(new File(b));
		SplitStack stack = new SplitStack();
		TabStaff ts = new TabStaff();
		ts.scanFile(new File(b));
		
		assertEquals(b,tp1.getInputPath()); 
		assertEquals(a,tp1.getOutputPath()); 
        assertTrue(properties.equals(tp1.getProperties()));
		assertEquals(properties.getElementSize(),tp1.getStaffSize());

		
	}
	
	@Test
	public void test_Constructor_case3() throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException {
		String a = "";
		String b = "inputfiles/case1.txt";
		tp1 = new TextToPDF(a,b);	
		PDFProperties properties = new PDFProperties();
		properties.extractProperties(new File(b));
		SplitStack stack = new SplitStack();
		TabStaff ts = new TabStaff();
		ts.scanFile(new File(b));
		
		assertEquals(b,tp1.getInputPath()); 
		assertEquals(a,tp1.getOutputPath()); 
        assertTrue(properties.equals(tp1.getProperties()));
		assertEquals(properties.getElementSize(),tp1.getStaffSize());

		
	}
	
	@Test (expected = CannotReadFileException.class)
	public void  test_WriteToPDF_exception() throws ConversionException, NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException, DocumentException, IOException{
		String a = TextToPDF.DEFAULT_OUTPUTPATH;
		String b = "inputfiles/";
		tp1 = new TextToPDF(a,b);
		tp1.WriteToPDF();
	}
	
    @Test
	public void  test_WriteToPDF_case1() throws ConversionException, NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException, DocumentException, IOException{
		String a = TextToPDF.DEFAULT_OUTPUTPATH;
		String b = "inputfiles/case1.txt";
		tp1 = new TextToPDF(a,b);
		
		PrintStream ps = new PrintStream("outputfiles/out2.txt");
		PrintStream orig = System.out;
		System.setOut(ps);
		tp1.WriteToPDF();;
		System.setOut(orig);
		ps.close();
		Scanner in = new Scanner(new FileReader("outputfiles/out2.txt"));
		String passed_in = in.nextLine();
		String result = "Successfully converted the file inputfiles/case1.txt to PDF: outputfiles/musicPDF.pdf";
		
		File f = new File("outputfiles/autofixlog.txt");
		
		if (f != null && result.equals(passed_in) == true){
			assertTrue(true);
		}
		
		else{
			fail();
		}
		
	}
	
	@Test
	public void  test_WriteToPDF_case2() throws ConversionException, NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException, DocumentException, IOException{
		String a = TextToPDF.DEFAULT_OUTPUTPATH;
		String b = "inputfiles/case2.txt";
		tp1 = new TextToPDF(a,b);
		
		PrintStream ps = new PrintStream("outputfiles/out2.txt");
		PrintStream orig = System.out;
		System.setOut(ps);
		tp1.WriteToPDF();;
		System.setOut(orig);
		ps.close();
		Scanner in = new Scanner(new FileReader("outputfiles/out2.txt"));
		String passed_in = in.nextLine();
		String result = "Successfully converted the file inputfiles/case2.txt to PDF: outputfiles/musicPDF.pdf";
		
		File f = new File("outputfiles/autofixlog.txt");
		
		if (f != null && result.equals(passed_in) == true){
			assertTrue(true);
		}
		
		else{
			fail();
		}
	}
	
	@Test
	public void  test_WriteToPDF_case3() throws ConversionException, NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException, DocumentException, IOException{
		String a = TextToPDF.DEFAULT_OUTPUTPATH;
		String b = "inputfiles/case3.txt";
		tp1 = new TextToPDF(a,b);
		
		PrintStream ps = new PrintStream("outputfiles/out2.txt");
		PrintStream orig = System.out;
		System.setOut(ps);
		tp1.WriteToPDF();;
		System.setOut(orig);
		ps.close();
		Scanner in = new Scanner(new FileReader("outputfiles/out2.txt"));
		String passed_in = in.nextLine();
		String result = "Successfully converted the file inputfiles/case3.txt to PDF: outputfiles/musicPDF.pdf";
		
		File f = new File("outputfiles/autofixlog.txt");
		
		if (f != null && result.equals(passed_in) == true){
			assertTrue(true);
		}
		
		else{
			fail();
		}
	}
	
	@Test
	public void  test_WriteToPDF_case4() throws ConversionException, NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException, DocumentException, IOException{
		String a = TextToPDF.DEFAULT_OUTPUTPATH;
		String b = "inputfiles/case4.txt";
		tp1 = new TextToPDF(a,b);
		
		PrintStream ps = new PrintStream("outputfiles/out2.txt");
		PrintStream orig = System.out;
		System.setOut(ps);
		tp1.WriteToPDF();;
		System.setOut(orig);
		ps.close();
		Scanner in = new Scanner(new FileReader("outputfiles/out2.txt"));
		String passed_in = in.nextLine();
		String result = "Successfully converted the file inputfiles/case4.txt to PDF: outputfiles/musicPDF.pdf";
		
		File f = new File("outputfiles/autofixlog.txt");
		
		if (f != null && result.equals(passed_in) == true){
			assertTrue(true);
		}
		
		else{
			fail();
		}
	}
	
	@Test
	public void  test_WriteToPDF_case5() throws ConversionException, NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException, DocumentException, IOException{
		String a = TextToPDF.DEFAULT_OUTPUTPATH;
		String b = "inputfiles/case5.txt";
		tp1 = new TextToPDF(a,b);
		
		PrintStream ps = new PrintStream("outputfiles/out2.txt");
		PrintStream orig = System.out;
		System.setOut(ps);
		tp1.WriteToPDF();
		System.setOut(orig);
		ps.close();
		Scanner in = new Scanner(new FileReader("outputfiles/out2.txt"));
		String passed_in = in.nextLine();
		String result = "Successfully converted the file inputfiles/case5.txt to PDF: outputfiles/musicPDF.pdf";
		
		File f = new File("outputfiles/autofixlog.txt");
		
		if (f != null && result.equals(passed_in) == true){
			assertTrue(true);
		}
		
		else{
			fail();
		}
	}
	
	@Test
	public void  test_WriteToPDF_case10() throws ConversionException, NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException, DocumentException, IOException{
		String a = TextToPDF.DEFAULT_OUTPUTPATH;
		String b = "inputfiles/case10.txt";
		tp1 = new TextToPDF(a,b);
		
		PrintStream ps = new PrintStream("outputfiles/out2.txt");
		PrintStream orig = System.out;
		System.setOut(ps);
		tp1.WriteToPDF();;
		System.setOut(orig);
		ps.close();
		Scanner in = new Scanner(new FileReader("outputfiles/out2.txt"));
		String passed_in = in.nextLine();
		String result = "Successfully converted the file inputfiles/case10.txt to PDF: outputfiles/musicPDF.pdf";
		
		File f = new File("outputfiles/autofixlog.txt");
		
		if (f != null && result.equals(passed_in) == true){
			assertTrue(true);
		}
		
		else{
			fail();
		}
	}
	
	@Test
	public void  test_WriteToPDF_case11() throws ConversionException, NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException, DocumentException, IOException{
		String a = TextToPDF.DEFAULT_OUTPUTPATH;
		String b = "inputfiles/case11.txt";
		tp1 = new TextToPDF(a,b);
	
		PrintStream ps = new PrintStream("outputfiles/out2.txt");
		PrintStream orig = System.out;
		System.setOut(ps);
		tp1.WriteToPDF();;
		System.setOut(orig);
		ps.close();
		Scanner in = new Scanner(new FileReader("outputfiles/out2.txt"));
		String passed_in = in.nextLine();
		String result = "Successfully converted the file inputfiles/case11.txt to PDF: outputfiles/musicPDF.pdf";
		
		File f = new File("outputfiles/autofixlog.txt");
		
		if (f != null && result.equals(passed_in) == true){
			assertTrue(true);
		}
		
		else{
			fail();
		}
	}
	
	@Test
	public void  test_WriteToPDF_case12() throws ConversionException, NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException, DocumentException, IOException{
		String a = TextToPDF.DEFAULT_OUTPUTPATH;
		String b = "inputfiles/case12.txt";
		tp1 = new TextToPDF(a,b);
		
		PrintStream ps = new PrintStream("outputfiles/out2.txt");
		PrintStream orig = System.out;
		System.setOut(ps);
		tp1.WriteToPDF();;
		System.setOut(orig);
		ps.close();
		Scanner in = new Scanner(new FileReader("outputfiles/out2.txt"));
		String passed_in = in.nextLine();
		String result = "Successfully converted the file inputfiles/case12.txt to PDF: outputfiles/musicPDF.pdf";
		
		File f = new File("outputfiles/autofixlog.txt");
		
		if (f != null && result.equals(passed_in) == true){
			assertTrue(true);
		}
		
		else{
			fail();
		}
	}
	
	@Test (expected = NoFileExistsException.class)
	public void checkerrorException1() throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException{
		String a = TextToPDF.DEFAULT_OUTPUTPATH;
		String b = "inputfiles/case100.txt";
		tp1 = new TextToPDF(a,b);
		
	}
	
	
	@Test
	public void test_updateTitle() throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException, ConversionException{
		String a = TextToPDF.DEFAULT_OUTPUTPATH;
		String b = "inputfiles/case1.txt";
		String c = "NEW TITLE";
		tp1 = new TextToPDF(a,b);
		tp1.updateTitle(c);
		assertEquals(c,tp1.getTitle());
		
	}

	@Test
	public void test_updateSubtitle() throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException, ConversionException{
		String a = TextToPDF.DEFAULT_OUTPUTPATH;
		String b = "inputfiles/case1.txt";
		String c = "NEW SUBTITLE";
		tp1 = new TextToPDF(a,b);
		tp1.updateSubtitle(c);
		assertEquals(c,tp1.getSubtitle());
		
	}

	@Test
	public void test_updateSpacing() throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException, ConversionException{
		String a = TextToPDF.DEFAULT_OUTPUTPATH;
		String b = "inputfiles/case1.txt";
		float f = 8.1f;
		tp1 = new TextToPDF(a,b);
		tp1.updateSpacing(f);
		assertEquals((int)f,(int)tp1.getSpacing());
		
	}
	
	@Test
	public void test_updateElementSize() throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException, ConversionException{
		String a = TextToPDF.DEFAULT_OUTPUTPATH;
		String b = "inputfiles/case1.txt";
		int i = 5;
		tp1 = new TextToPDF(a,b);
		tp1.updateElementSize(i);
		assertEquals((int)i,(int)tp1.getStaffSize());
		
	}
	
	@Test
	public void test_updatePageSize() throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException, ConversionException{
		String a = TextToPDF.DEFAULT_OUTPUTPATH;
		String b = "inputfiles/case1.txt";
		Rectangle r1 = new Rectangle(PageSize.LETTER); // LEDGER, LEGAL
		Rectangle r2 = new Rectangle(PageSize.LEDGER);
		Rectangle r3 = new Rectangle(PageSize.LEGAL);
		tp1 = new TextToPDF(a,b);
		tp1.updatePageSize(r1);
		assertEquals(r1,tp1.getPageSize());
		tp1.updatePageSize(r2);
		assertEquals(r2,tp1.getPageSize());
		tp1.updatePageSize(r3);
		assertEquals(r3,tp1.getPageSize());
		
		
	}
	
	@Test
	public void test_updateTitleSize() throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException, ConversionException{
		String a = TextToPDF.DEFAULT_OUTPUTPATH;
		String b = "inputfiles/case1.txt";
		int i = 4;
		tp1 = new TextToPDF(a,b);
		tp1.updateTitleSize(i);
		assertEquals(i,tp1.getTitleFontSize());
	}
	
	@Test
	public void test_updateSubtitleSize() throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException, ConversionException{
		String a = TextToPDF.DEFAULT_OUTPUTPATH;
		String b = "inputfiles/case1.txt";
		int i = 4;
		tp1 = new TextToPDF(a,b);
		tp1.updateSubtitleSize(i);
		assertEquals(i,tp1.getSubtitleFontSize());
	}
	
	@Test
	public void test_updateSLeftMargin() throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException, ConversionException{
		String a = TextToPDF.DEFAULT_OUTPUTPATH;
		String b = "inputfiles/case1.txt";
		float f = 3.1f;
		tp1 = new TextToPDF(a,b);
		tp1.updateLeftMargin(f);
		assertEquals((int)f,(int)tp1.getLeftMargin());
		
	}
	
	@Test
	public void test_updateRightMargin() throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException, ConversionException{
		String a = TextToPDF.DEFAULT_OUTPUTPATH;
		String b = "inputfiles/case1.txt";
		float f = 3.5f;
		tp1 = new TextToPDF(a,b);
		tp1.updateRightMargin(f);
		assertEquals((int)f,(int)tp1.getRightMargin());
		
	}
	
	@Test
	public void test_updateMeasure() throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException, ConversionException{
		String a = TextToPDF.DEFAULT_OUTPUTPATH;
		String b = "inputfiles/case1.txt";
		float f = 4.5f;
		tp1 = new TextToPDF(a,b);
		tp1.updateMeasureSpace(f);
		assertEquals((int)f,(int)tp1.getMeasureSpace());
		
	}
	
	@Test
	public void test_updateOutputPath() throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException, ConversionException{
		String a = TextToPDF.DEFAULT_OUTPUTPATH;
		String b = "inputfiles/case1.txt";
		float f = 4.5f;
		tp1 = new TextToPDF(a,b);
		tp1.setOutputPath("lol");
		assertEquals("lol",tp1.getOutputPath());
		
	}
	
	@Test
	 public void test_restore() throws NoFileExistsException, CannotReadFileException, EmptyFileException, NoMusicException, LargeNumberException, ConversionException{
		String a = TextToPDF.DEFAULT_OUTPUTPATH;
		String b = "inputfiles/case1.txt";
		tp1 = new TextToPDF(a,b);
		assertFalse(tp1.restoreStaff());
		
	}
	
	


	
	
	}


