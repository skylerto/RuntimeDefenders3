package version12;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import mvcV2.Model;
import tabparts.TabStaff;
import version12.ReadFromInput;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class TextToPDFv12Test {

	TextToPDFv12 t;
	TextToPDFv12 t1;
	@Before
	public void setUp() throws Exception{
	
	//String a = "";
	//String b = "";
	}
	
	@Test
	public void test_TextToPDFv12DefaultConstructor_CASE1() throws DocumentException, IOException {
		
		String a = "";
		String b = "inputfiles/fixed.txt";
		t = new TextToPDFv12(a,b);	
		
		assertEquals(Model.getTitleFontSize(),(int)t.titleFontSize); /*Compares default titlesize from Model class and compares it
		                                                               with the title assigned by the constructor*/
		
		assertEquals(Model.getSubTitleFontSize(),(int)t.subTitleFontSize);   /*Compares default Subtitlesize from Model class and compares it
                                                                               with the Subtitle assigned by the constructor*/
		
		assertEquals(Model.getMeasureFontSize(),t.FONT_SIZE);   /*Compares default Font size from Model class and compares it
                                                                  with the Font size assigned by the constructor*/
		assertEquals(Model.getTitle(),t.titleString); 
		
		assertEquals(Model.getSubtitle(),t.subtitleString);
		
		String expected_filename = "Moonlight Sonata.pdf";              /*goes and checks the title and compare the filename with 
	                                                              the expect3ed filename*/
	  assertEquals(expected_filename,Model.getOutputFilename());
	  
	  String expected_outputpath = a + Model.getOutputFilename();
	  
	  assertEquals(expected_outputpath, a + Model.getOutputFilename());
		
	}
	
	
/*	
	@Ignore
public void test_TextToPDFv12DefaultConstructor_CASE2() throws DocumentException, IOException {
		
		String a = "hello";
		String b = "inputfiles/fixed1.txt";
		t1 = new TextToPDFv12(a,b);
		
		assertEquals(Model.getTitleFontSize(),(int)t1.titleFontSize); 
assertEquals(Model.getSubTitleFontSize(),(int)t1.subTitleFontSize);   
assertEquals(Model.getMeasureFontSize(),t1.FONT_SIZE);  
assertEquals(Model.getTitle(),t1.titleString); 
assertEquals(Model.getSubtitle(),t1.subtitleString);
	}
	*/
	@Test
	public void test_WriteToPDFcase1() throws DocumentException, IOException {
		
		String a = "";
		String b = "inputfiles/fixed.txt";
		t = new TextToPDFv12(a,b);
		t.WriteToPDF();
        
		assertEquals(Model.getTitle(),t.titleString);
		assertEquals(Model.getSubtitle(),t.subtitleString);
		assertEquals(Model.getTitleFontSize(),(int)t.titleFontSize);
		assertEquals(Model.getSubTitleFontSize(),(int)t.subTitleFontSize);
		assertEquals(Model.getMeasureFontSize(),t.FONT_SIZE);
		
		
		
	}
	
	@Test
	public void test_WriteToPDFcase2() throws DocumentException, IOException {
		
		String a = "";
		String b = "inputfiles/case1.txt";
		t = new TextToPDFv12(a,b);
		t.WriteToPDF();
		
		assertEquals(Model.getTitle(),t.titleString);
		assertEquals(Model.getSubtitle(),t.subtitleString);
		assertEquals(Model.getTitleFontSize(),(int)t.titleFontSize);
		assertEquals(Model.getSubTitleFontSize(),(int)t.subTitleFontSize);
		assertEquals(Model.getMeasureFontSize(),t.FONT_SIZE);
		
	}
	
	@Test
	public void test_WriteToPDFcase3() throws DocumentException, IOException {
		
		String a = "";
		String b = "inputfiles/case4.txt";
		t = new TextToPDFv12(a,b);
		t.WriteToPDF();
		
		assertEquals(Model.getTitle(),t.titleString);
		assertEquals(Model.getSubtitle(),t.subtitleString);
		assertEquals(Model.getTitleFontSize(),(int)t.titleFontSize);
		assertEquals(Model.getSubTitleFontSize(),(int)t.subTitleFontSize);
		assertEquals(Model.getMeasureFontSize(),t.FONT_SIZE);
		
	}
	
	@Test
	public void test_WriteToPDFcase4() throws DocumentException, IOException {
		
		String a = "";
		String b = "inputfiles/case5.txt";
		t = new TextToPDFv12(a,b);
		t.WriteToPDF();
		
		assertEquals(Model.getTitle(),t.titleString);
		assertEquals(Model.getSubtitle(),t.subtitleString);
		assertEquals(Model.getTitleFontSize(),(int)t.titleFontSize);
		assertEquals(Model.getSubTitleFontSize(),(int)t.subTitleFontSize);
		assertEquals(Model.getMeasureFontSize(),t.FONT_SIZE);
		
	}
	

	
	@Test
	public void test_setInputFilename() throws DocumentException, IOException{
		String a = "";
		String b = "inputfiles/case1.txt";
		t = new TextToPDFv12(a,b);
		t.setInputFileName("hello");
		
		assertEquals("hello",t.INPUT_FILENAME);
		
		
	}
	
	@Test
	public void test_getInputFilename() throws DocumentException, IOException{
		String a = "";
		String b = "inputfiles/case1.txt";
		t = new TextToPDFv12(a,b);
		String litter = t.getInputFileName();
		
		assertEquals("hello",litter);
		
		
	}
	
	

}