package version13;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import tabparts.TabStaff;

public class SplitStackTest {

	SplitStack ss1;
	@Before
	public void setUp() throws Exception {
		ss1 = new SplitStack(); 
	}

	@Test
	public void test_defaultConst() {
		assertEquals(100,ss1.stack.length);
		assertEquals(0,ss1.size);
	}
	
	@Test
	public void test_push() throws ConversionException {
		TabStaff ts = new TabStaff(); // tabstaff ts
		ts.scanFile(new File("inputfiles/case1.txt"));
		
		TabStaff expt = new TabStaff(); // tabstaff 
		expt.scanFile(new File("inputfiles/case1.txt"));
		
		SplitMarker sm2 = new SplitMarker(ts,10); //marker sm2
		ss1.push(sm2);
		
		assertEquals(expt.toString(),ss1.stack[0].staff.toString()); //checks if its marker into splitstack
		assertEquals(10,ss1.stack[0].maxchars); //checks if marker is stacked
		assertEquals(1,ss1.size); //checks if the size is incremented
		
	}
	
	@Test
	public void test_pop() throws ConversionException {
		TabStaff ts = new TabStaff(); // tabstaff ts
		ts.scanFile(new File("inputfiles/case1.txt"));
		SplitMarker sm2 = new SplitMarker(ts,10); //marker sm2
		ss1.push(sm2);
		SplitMarker out = ss1.pop();
		assertEquals(sm2.staff.toString(),out.staff.toString()); //checks if popped the marker out splitstack
		assertEquals(sm2.maxchars,out.maxchars); //checks if marker is popped
		assertEquals(0,ss1.size); //checks if the size is decremented
		
	}
	
	@Test
	public void test_peak() throws ConversionException{
		
		TabStaff ts = new TabStaff(); // tabstaff ts
		ts.scanFile(new File("inputfiles/case1.txt"));
        SplitMarker sm2 = new SplitMarker(ts,10); //marker sm2
		ss1.push(sm2);
		SplitMarker out = ss1.peak();
		assertEquals(sm2.staff.toString(),out.staff.toString()); 
		assertEquals(sm2.maxchars,out.maxchars);
		
	}
	
	@Test(expected = ConversionException.class) 
	public void test_popException() throws ConversionException {
	    ss1.pop();
	}
	
	@Test(expected = ConversionException.class) 
	public void test_pushException() throws ConversionException {
		TabStaff temp = new TabStaff();
		SplitMarker sm = new SplitMarker(temp,10);
		
		ss1.size = 101;
	    ss1.push(sm);
	}
	
	

}
