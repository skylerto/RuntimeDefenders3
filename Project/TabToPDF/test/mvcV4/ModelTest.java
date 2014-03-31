package mvcV4;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tabparts.LargeNumberException;
import version13.CannotReadFileException;
import version13.ConversionException;
import version13.EmptyFileException;
import version13.NoFileExistsException;
import version13.NoMusicException;

import com.itextpdf.text.Rectangle;

public class ModelTest {
     
	Model alpha;
	Model bravo;
	@Before
	public void setUp() throws Exception {
		
		alpha = new Model();
		bravo = new Model();
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
			
	}
	

	

}
