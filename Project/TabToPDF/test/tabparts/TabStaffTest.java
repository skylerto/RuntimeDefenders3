package tabparts;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class TabStaffTest {

	TabString temp;
	TabMeasure tempm;
	TabStaff temps;
	TabString tab1;
	TabMeasure tabm1;
	TabStaff tabs1;
	TabStaff tabs8;

	@Before
	public void setUp() throws Exception {

		tab1 = new TabString();
		tabm1 = new TabMeasure();
		tabs1 = new TabStaff();

		temps = new TabStaff();
		
		tabs8 = new TabStaff();
		

	}
	
	
	@Test
	public void TestTabStaffConstructor() {

		/*
		 * Basically, compared default tabmeasure, tabstaff and tabstring, if
		 * they are equal test should pass First for loop tests if we have 1000
		 * tabmeasures in tabstaff Second for loop tests if we have 6 tabstrings
		 * in the tabmeasure Third for loop test if all 75 indexes of the
		 * tabstring are 0
		 */
		for (int i = 0; i < 1000; i++) {
			tempm = new TabMeasure(tabs1.getMeasure(i));
			for (int j = 0; j < 6; j++) {
				temp = new TabString(tempm.getString(j));
				for (int k = 0; k < 3000; k++) {
					assertEquals('\0', temp.getChar(k));
				}
			}
		}

	}
	
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testScanFile_case0() throws Exception {

		File f = new File(
				"inputfiles/case0.txt");
		tabs1.scanFile(f);
		
 }

	@Test
	public void testScanFile_case1() throws Exception {

		File f = new File(
				"inputfiles/case1.txt");
		tabs1.scanFile(f);
		String h = "[[|---------------------|, |-------------3-3-----|, |-----------------2---|, " +
				   "|---1---<2>-----------|, |---------------------|, |-----<1>---2---------|], " +
				   "[||---------------1------0-0-----||, ||----2---2---------------------||, " +
				   "||------------2--2------2-------||, ||------------------------------||, " +
				   "||----4----------------------4--||, ||------------------------------||], " +
				   "[|----------1------0-0-|, |---2---------------0-|, |---------------------|, " +
				   "|---------------------|, |------3--------------|, |---------------4-----|], " +
				   "[|---8----8|, |---1-----|, |------0--|, |---------|, |---2-----|, |---------|], " +
				   "[||-----------------------2--------1|, ||---------------------------------|, " +
				   "||-------------1-------------------|, ||---------------------------------|, " +
				   "||--------1---------------2--------|, ||----------------0----------------|], " +
				   "[|---------0------------0------||, |1----------------------------||," +
				   " |-----------------------------||, |----------2------------------||, " +
				   "|-----------------------------||, |-----------------------------||], " +
				   "[|----------------|, |--------------3-|, |---------------2|, |--1-------------|, " +
				   "|----------------|, |-------2--------|], [|----------------|, |--------------3-|, " +
				   "|---------------2|, |--1-------------|, |----------------|, |-------2--------|]," +
				   " [||----------------||, ||--------------3-||, ||---------------2||, " +
				   "||--1-------------||, ||----------------||, ||-------2--------||]]"
;
		String j = tabs1.getList().toString();
		
		assertTrue(h.equals(j));
 }

	@Test
	public void testScanFile_case2() throws Exception {

		File f = new File(
				"inputfiles/case2.txt");
		tabs1.scanFile(f);
		String h = "[[|------------------------------|, |-----1-----1-----1-----1------|, " +
				   "|---2-----2-----2-----2--------|, |-2-----2-----2-----2----------|, " +
				   "|-----0------------------------|, |------------------------------|], " +
				   "[|-------------------------|, |-----1-----1-----3-----3-|, |---2-----2-----3-----3---|, " +
				   "|-3-----3-----3-----3-----|, |-------------5-----------|, |-1-----------------------|], " +
				   "[|-------------------------|, |-----3-----1-----0-----0-|, |---1-----2-----2-----1---|," +
				   " |-2-----2-----2-----0-----|, |-------------------------|, |-0-----------0-----------|], " +
				   "[||---3--------2--------0-----0-0-----|, ||---------------------------------0-|," +
				   " ||*----2-0------2-0------2-0-----2---|, ||*---------0------------------------|, " +
				   "||-3-----------------2---------------|, ||-----------------------------------|], " +
				   "[|------------------------------||, |------------------------------||, " +
				   "|-----2-0------2-0------0------||, |----------0--------------2----||," +
				   " |------------------------------||, |------------------------------||], " +
				   "[|---3--------2--------0-----0-------|, |-3-----------------2---------------|, " +
				   "|-----------------------------------|, |-----------------------------------|, " +
				   "|-----------------------------------|, |-----------------------------------|]]"
;
		
		
		String j = tabs1.getList().toString();
		assertTrue(h.equals(j));
	}
	
	@Test
	public void testScanFile_case3() throws Exception {

		File f = new File(
				"inputfiles/case3.txt");
		tabs1.scanFile(f);
		String h = "[[||---12----12----12----12-|, ||------------------------|, " +
				"||*-----12----11----12----|, ||*-----------------------|, ||------------------------|, " +
				"||-0----------------------|], [|----12----12----12----12-|, |-------------------------|, " +
				"|-------12----11----12----|, |-------------------------|, |-------------------------|, " +
				"|-12----------------------|], [|---10---10---10---10---10---10-||, " +
				"|------7---------7---------7----||, |---------------------0--------*||," +
				" |-----------7------------------*||, |-------------------------------||, " +
				"|-7-----------------------------||], [|---------7-------|, |-----5s7---7-----|," +
				" |---0---------0---|, |---------------2-|, |-----------------|, |-0---------------|]," +
				" [|---------5-------|, |-----3s5---5-----|, |-------------0---|, |---2-----------2-|," +
				" |-2---------------|, |-----------------|], [|---------7-------|, |-----5s7---7-----|, " +
				"|---0---------0---|, |---------------2-|, |-----------------|, |-0---------------|], " +
				"[|---------5---------|, |-----3s5---5-------|, |-------------0---0-|, |---2-----------2---|, " +
				"|-2-----------------|, |-------------------|]]";
		String j = tabs1.getList().toString();
		assertTrue(h.equals(j));
		

	}
	
	@Test
	public void testScanFile_case4() throws Exception {

		File f = new File(
				"inputfiles/case4.txt");
		tabs1.scanFile(f);
		String h = "[[||2---3--------2--------0-----0-0-----||, ||---------------------------------0--||, " +
				"||*----2-0------2-0------2-0-----2---*||, ||*---------0------------------------*||, " +
				"||-3-----------------2----------------||, ||------------------------------------||], " +
				"[||---3--------2--------0-----0--||, ||------------------------------||," +
				" ||*----2-0------2-0------0-----*||, ||*---------0--------------2---*||, " +
				"||-3----------------------------||, ||-------------------0----------||]]"
;
		String j = tabs1.getList().toString();
		assertTrue(h.equals(j));
	}
	
	@Test
	public void testScanFile_Case5() throws Exception {

		File f = new File(
				"inputfiles/case5.txt");
		tabs1.scanFile(f);
		String h = "[[|---------3----------10-----0-------0---------------7--|, " +
				"|-----0---------10--------------0-------0-------5-7----|, " +
				"|-------2----------0----------2-------2-------0--------|, " +
				"|---------------------------------2--------------------|, " +
				"|---2---------------------3----------------------------|, " +
				"|-0-----------7-----------------------------0----------|]," +
				" [|---------7--------|, |-----5s7---7------|," +
				" |---0s--------0----|, |----------------2-|, |------------------|," +
				" |-0----------------|], [|----------5---------|, |------3s5---5s----0-|," +
				" |--------------0-----|, |----2-----------2---|, |-2------------------|," +
				" |--------------------|], [||---------3----------10-----0-------0---------------7-|," +
				" ||-----0---------10--------------0-------0-------5h7---|, " +
				"||*-----h2----------0h---------2h-----h2------h0-------|, " +
				"||*--------------------------------2-------------------|, " +
				"||---2---------------------3---------------------------|, " +
				"||-0---------5h7-----------------------------0---------|]," +
				" [|-----------5p1-------10-----0-------0-----------------7-|," +
				" |---10-------------1p-------------0------p0-------5-7----|, " +
				"|------0--------------0----------2-------2-------0-------|, " +
				"|--------12--------------------------2-------------------|, " +
				"|----------------------------3---------------------------|, " +
				"|-0--------------7-----------------------------0---------|], " +
				"[||------------------------------<12>----------||, " +
				"||-<12>------------------------------<12>-----||, " +
				"||*-----<5>-----------<7>--------------------*||, " +
				"||*---------------<7>--------3--4h-----------*||, " +
				"||--------------------------------------------||, " +
				"||--------------------------------------------||], " +
				"[|---------3-----------10---|, |-----0----------10--------|, " +
				"|-------2-----------0------|, |--------------------------|, " +
				"|---2----------------------|, |---2----------------------|]," +
				" [|---3--------2--------0-----2 0|, |---------------------------- -|," +
				" |-----2-0------2-0------0---- -|, |----------0--------------2-- -|," +
				" |-3-------------------------- -|, |-------------------0-------- -|]," +
				" [|----2<3>--<5>----5s5--<6>----------1h1-1p-12-|, " +
				"|---------------------------------------------|, " +
				"|---------------------------------------------|, " +
				"|---------------------------------------------|, " +
				"|---------------------------------------------|, " +
				"|---------------------------------------------|]]"
;
		
		String j = tabs1.getList().toString();
		
		assertTrue(h.equals(j));

	}

	
	@Test
	public void testAddMeasureAndGetMeasure() throws Exception {
		File f = new File(
				"inputfiles/case7.txt");
		tabs1.scanFile(f);
		tabs1.addMeasure();
		
		/*
		 * tab s1 contains only one measure
		 * |----------------|
		   |--------------3-|
		   |---------------2|
	       |--1-------------|
		   |----------------|
		   |-------2--------|
		   
		   
		   after inserting an empty measure which addMeasure does, it should return
		   [empty measure] when we call the toString method on index 1
		 */
		
		String expected = "[empty measure]"; 
		
		assertEquals(expected,tabs1.getMeasure(1).toString());
		
	}
	
	
	@Test
	public void testToString() throws Exception {

		File f = new File(
				"inputfiles/case7.txt");
		tabs1.scanFile(f);
		
		/*
		 * tab s1 contains 
		 * |----------------|
		   |--------------3-|
		   |---------------2|
	       |--1-------------|
		   |----------------|
		   |-------2--------|
		 after calling the toString method it should return
		 
			measure(0) repeats(0)
			|----------------|
			|--------------3-|
			|---------------2|
			|--1-------------|
			|----------------|
			|-------2--------|

			Total Measures=1
		*/
	
		String expected = "measure(0) repeats(0)\n|---------------------------------------------------------------------------------------------------------------|\n|---------------------------------------------------------------------------------------------------------------|\n|---------------------------------------------------------------------------------------------------------------|\n|---------------------------------------------------------------------------------------------------------------|\n|---------------------------------------------------------------------------------------------------------------|\n|---------------------------------------------------------------------------------------------------------------|\n\nTotal Measures=1";
		assertEquals(expected, tabs1.toString());
		
	
	}
	
	
	@Test
	public void testGetStartline() throws Exception {
		/*testing the expected line of the first measure which is 4 */
		
		
		File f = new File(
				"inputfiles/case7.txt");
		tabs1.scanFile(f);
		
		assertEquals(5,tabs1.getStartLine(0));
	}
	
	
	@Test
	public void testGetEndline() throws Exception {
		/*testing the expected line of the first measure which is 9 */
		
		
		File f = new File(
				"inputfiles/case7.txt");
		tabs1.scanFile(f);
		
		assertEquals(5,tabs1.getEndLine(0));
		
	}
	
	@Test
	public void testGetsplit() throws Exception {
		
		/*splits the tabstaff and an expected tabstaff is stored oon other .txt file and copares if the string rep
		 *-resentation is correct and equal for both expected and passed in
		 */
		
		File f = new File(
				"inputfiles/case9.txt");
	
		tabs1.scanFile(f);
		
		assertTrue(tabs1.splitLongMeasures(20));
		assertFalse(tabs8.splitLongMeasures(20));
		
	
	}
	
	
	
	


}