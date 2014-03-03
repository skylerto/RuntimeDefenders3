package tabparts;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class TabStaffTest {

	TabString temp;
	TabMeasure tempm;
	TabStaff temps;
	TabString tab1;
	TabMeasure tabm1;
	TabStaff tabs1;

	@Before
	public void setUp() throws Exception {

		tab1 = new TabString();
		tabm1 = new TabMeasure();
		tabs1 = new TabStaff();

		temps = new TabStaff();

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
				for (int k = 0; k < 75; k++) {
					assertEquals('\0', temp.getChar(k));
				}
			}
		}

	}

	@Test
	public void testScanFileEmptyFile() throws IOException {
		String result1 = "";
		String result2 = "";

		File f = new File(
				"/cse/home/cse32017/git/RuntimeDefenders3/Project/TabToPDF/inputfiles/case0.txt");
		File temp = new File("temp");
		temp.createNewFile();

		BufferedReader reader1 = new BufferedReader(new FileReader(f));
		BufferedReader reader2 = new BufferedReader(new FileReader(temp));
		String line1 = null;
		String line2 = null;

		while ((line1 = reader1.readLine()) != null) {
			result1 += line1;
			result1 += "\n";
		}

		while ((line2 = reader2.readLine()) != null) {
			result2 += line2;
			result2 += "\n";
		}

		assertEquals(result1, result2);
	}

	@Test
	public void testScanFile_case1() throws Exception {

		File f = new File(
				"inputfiles/case1.txt");
		tabs1.scanFile(f);
		String h = "[[|----------------|, |--------------3-|, |---------------2|, |--1-------------|, |----------------|, |-------2--------|], [||---------------1------0-0-----||, ||----2---2---------------------||, ||------------2--2------2-------||, ||------------------------------||, ||----4----------------------4--||, ||------------------------------||], [|----------1------0-0-|, |---2---------------0-|, |---------------------|, |---------------------|, |------3--------------|, |---------------4-----|], [|---8----8|, |---1-----|, |------0--|, |---------|, |---2-----|, |---------|], [||-----------------------2--------1|, ||---------------------------------|, ||-------------1-------------------|, ||---------------------------------|, ||--------1---------------2--------|, ||----------------0----------------|], [|---------0------------0------||, |1----------------------------||, |-----------------------------||, |----------2------------------||, |-----------------------------||, |-----------------------------||], [|----------------|, |--------------3-|, |---------------2|, |--1-------------|, |----------------|, |-------2--------|], [|----------------|, |--------------3-|, |---------------2|, |--1-------------|, |----------------|, |-------2--------|], [||----------------||, ||--------------3-||, ||---------------2||, ||--1-------------||, ||----------------||, ||-------2--------||]]";
		String j = tabs1.getList().toString();
		//System.out.println(tabs1.getList().toString());
		assertTrue(h.equals(j));

	}

}