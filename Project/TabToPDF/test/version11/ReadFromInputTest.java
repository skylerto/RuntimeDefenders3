package version11;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ReadFromInputTest {

	ReadFromInput r1;
	@Before
	public void setUp() throws Exception{
	
	r1 = new ReadFromInput();	
	}
	
	@Test
	public void testSetTitleAndGetTitle() {
		/*
		 * Setting the title to khurram saleem
		 * Checking if it returns the setted title
		 */
		String title = "khurram saleem";
		r1.setTITLE(title);
		assertEquals(title, r1.getTITLE());
		
	}
	
	@Test
	public void testSetSubtitleAndGetSubtitle() {
		/*
		 * Setting the subtitle to sheraz
		 * Checking if it returns the setted subtitle
		 */
		String subtitle = "sheraz";
		r1.setSUBTITLE(subtitle);
		assertEquals(subtitle, r1.getSUBTITLE());
		
	}
	
	@Test
	public void testSetLineSpacingAndGetLineSpacing() { 
		
		/*
		 * Setting line spacing to 5 using setLineSpacing
		 * checking if it return 5 when calling getLineSpacing
		 */
		
		float space = 5;
		r1.setLineSpacing(space);
		assertEquals(space, r1.getSACING(),1);
	}
	
	@Test
	public void testTitleisEmpty() {
		/*
		 * Setting the title to NULL
		 * Checking if it returns the true for TitleIsEmpty
		 */
		String title = null;
		r1.setTITLE(title);
		assertTrue(r1.titleIsEmpty());
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetList(){
		/*
		 * The dynamic array is empty therefore it should 
		 * throw the exception
		 */
		r1.getList();
	}
	

}
