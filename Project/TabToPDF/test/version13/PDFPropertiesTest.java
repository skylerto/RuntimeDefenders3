package version13;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.itextpdf.text.Rectangle;

public class PDFPropertiesTest {
	
	PDFProperties alpha;
	PDFProperties bravo;
	PDFProperties charlie;

	@Before
	public void setUp() throws Exception {
	
		alpha = new PDFProperties();
		bravo = new PDFProperties("This is Bravo","subtitle",3.5f);
		
	
	}

	@Test
	public void test_defaultConst() {
		String expt_title = "My Guitar Tab";
		String expt_sub= "";
		assertEquals(expt_title,alpha.getTitle());
		assertEquals(expt_sub,alpha.getSubtitle());
		assertEquals((int)5.0,(int)alpha.getSpacing());
		assertEquals(8,alpha.getElementSize());
		assertEquals(alpha.DEFAULT_PAGESIZE,alpha.getPageSize());
		assertEquals(16,alpha.getTitleFontSize());
		assertEquals(14,alpha.getSubtitleFontSize());
		assertEquals((int)alpha.DEFAULT_LEFTMARGIN,(int)alpha.getLeftMargin());
		assertEquals((int)alpha.DEFAULT_RIGHTMARGIN,(int)alpha.getRightMargin());
		assertEquals((int)alpha.DEFAULT_MEASURESPACE,(int)alpha.getMeasureSpace());
		
	}
	
	@Test
	public void test_CustomConst() {
		String expt_title = "This is Bravo";
		String expt_sub= "subtitle";
		assertEquals(expt_title,bravo.getTitle());
		assertEquals(expt_sub,bravo.getSubtitle());
		assertEquals((int)3.5f,(int)bravo.getSpacing());
		assertEquals(8,bravo.getElementSize());
		assertEquals(bravo.DEFAULT_PAGESIZE,bravo.getPageSize());
		assertEquals(16,bravo.getTitleFontSize());
		assertEquals(14,bravo.getSubtitleFontSize());
		assertEquals((int)bravo.DEFAULT_LEFTMARGIN,(int)bravo.getLeftMargin());
		assertEquals((int)bravo.DEFAULT_RIGHTMARGIN,(int)bravo.getRightMargin());
		assertEquals((int)bravo.DEFAULT_MEASURESPACE,(int)bravo.getMeasureSpace());
		
	}
	
	@Test
	public void test_extractProperties() {
		/*Extracts the propertes form case one then checks if corresponding thinds were assinge*/
		String expt_title = "Fixing Bars and Spacing";
		String expt_sub = "Test Case 1";
		double expt_space = 5.0f;
		alpha.extractProperties(new File("inputfiles/case1.txt"));
		assertEquals(expt_title,alpha.getTitle());
		assertEquals(expt_sub,alpha.getSubtitle());
		assertEquals((int)5.0,(int)alpha.getSpacing());
	}
	
	@Test
	public void test_setTitle() {
		String title = "WOW";
		bravo.setTitle(title);
		assertEquals(title,bravo.getTitle());
	}
	
	@Test
	public void test_setSUBTitle() {
		String sub_title = "SUB";
		bravo.setSubtitle(sub_title);
		assertEquals(sub_title,bravo.getSubtitle());
	}
	
	@Test
	public void test_setSpacing() {
		alpha.setSpacing(7.2f);
		assertEquals((int)7.2f,(int)alpha.getSpacing());
		alpha.setSpacing(100.0f);
		assertEquals((int)25.0f,(int)alpha.getSpacing());
		alpha.setSpacing(0.5f);
		assertEquals((int)1.0f,(int)alpha.getSpacing());
	}
	@Test
	public void test_setElement_size() {
		alpha.setElementSize(7);
		assertEquals(7,alpha.getElementSize());
	}
	
	@Test
	public void test_setPage_size() {
		Rectangle a = new Rectangle(3.0f,4.0f);
		alpha.setPageSize(a);
		assertEquals(a,alpha.getPageSize());
	}
	
	@Test
	public void test_settitlesize() {
		alpha.setTitleFontSize(7);
		assertEquals(7,alpha.getTitleFontSize());
		
	}
	
	@Test
	public void test_setSubtitlesize() {
		alpha.setSubtitleFontSize(4);
		assertEquals(4,alpha.getSubtitleFontSize());
		
	}
	
	@Test
	public void test_setleftMargin() {
		alpha.setLeftMargin(3.6f);
		assertEquals((int)3.6f,(int)alpha.getLeftMargin());
		
	}
	
	@Test
	public void test_setrightMargin() {
		alpha.setRightMargin(4.6f);
		assertEquals((int)4.6f,(int)alpha.getRightMargin());
		
	}
	
	@Test
	public void test_setMeasureSpace() {
		alpha.setMeasureSpace(7.0f);
		assertEquals((int)7.0f,(int)alpha.getMeasureSpace());
		
	}
	

	@Test
	public void test_getTitle() {
		String expt_title = "This is Bravo";
		assertEquals(expt_title,bravo.getTitle());
		}
	
	@Test
	public void test_getSubTitle() {
		String expt_sub= "subtitle";
		assertEquals(expt_sub,bravo.getSubtitle());
		}
	
	@Test
	public void test_getSpacing() {
		double expt_space= 3.5f;
		assertEquals((int)expt_space,(int)bravo.getSpacing());
		}
	
	@Test
	public void test_getElemensize() {
		int expt_esize= 8;
		assertEquals((int)expt_esize,(int)bravo.getElementSize());
		}
	
	@Test
	public void test_getPagesize() {
		assertEquals(bravo.DEFAULT_PAGESIZE,alpha.getPageSize());
		}
	
	@Test
	public void test_getTitlesize() {
		int exptd = 16;
		assertEquals(exptd,bravo.getTitleFontSize());
		}
	
	@Test
	public void test_getSubTitlesize() {
		int exptd = 14;
		assertEquals(exptd,bravo.getSubtitleFontSize());
		}
	
	@Test
	public void test_getLeftMargin() {
		assertEquals((int)bravo.DEFAULT_LEFTMARGIN,(int)bravo.getLeftMargin());
		}
	
	@Test
	public void test_getRightMargin() {
		assertEquals((int)bravo.DEFAULT_RIGHTMARGIN,(int)bravo.getRightMargin());
		}
	
	@Test
	public void test_getMeasurSpace() {
		assertEquals((int)bravo.DEFAULT_MEASURESPACE,(int)bravo.getMeasureSpace());
		}
	
	@Test
	public void test_toString() {
		String expt = "[This is Bravo, subtitle, 3.5, 8, (612.0,792.0), 16, 14]";
		assertTrue(expt.equals(bravo.toString()));
		}
}
