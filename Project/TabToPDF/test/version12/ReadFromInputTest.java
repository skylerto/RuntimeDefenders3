package version12;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

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
	public void testReadFileSmallFile() throws FileNotFoundException, IOException{
		r1.readFile("inputfiles/fixed.txt");
		
		//This file called fixed contains
		/*
		 * TITLE=Moonlight Sonata
			SUBTITLE=Daylight Sonata
			SPACING=5.0

			|----------------|
			|--------------3-|
			|---------------2|
			|--1-------------|
			|----------------|
			|-------2--------|
			
			
		 */
		
		//We are going to read this file using readfile() method
		//and see if it works properly
		
		String expect_title = "Moonlight Sonata";
		String expect_subtitle = "Daylight Sonata";
		String expect_list = "[[|----------------|, |--------------3-|, |---------------2|, |--1-------------|, |----------------|, |-------2--------|]]";

		float expect_spacing = 5;
		
		assertEquals(expect_title, r1.getTITLE()); //check the title
		assertEquals(expect_subtitle, r1.getSUBTITLE()); // check the subtitle
		assertEquals(expect_spacing, r1.getSACING(),1); // check the spacing
		assertEquals(expect_list, r1.getList().toString());

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