package version13;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import tabparts.TabStaff;

public class SplitMarkerTest {
	
	SplitMarker sm;

	@Before
	public void setUp() throws Exception {
		TabStaff ts = new TabStaff();
		ts.scanFile(new File("inputfiles/case1.txt"));
	    sm = new SplitMarker(ts,10);
	}

	@Test
	public void test() throws Exception {
		TabStaff expt = new TabStaff();
		expt.scanFile(new File("inputfiles/case1.txt"));
		assertEquals(expt.toString(),sm.staff.toString());
		assertEquals(10,sm.maxchars);
	}

}
