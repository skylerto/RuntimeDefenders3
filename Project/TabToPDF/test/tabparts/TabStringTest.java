// KHurram and Bilal

package tabparts;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tabparts.TabString;

public class TabStringTest {

	TabString tab;
	TabString tab1;
	TabString tab2;
	TabString tab3;
	TabString tab4;
	TabString tab5;
	TabString tab6;
	TabString tab7;
	TabString tab8;
	TabString tab9;
	TabString tab10;
	TabString tab11;

	@Before
	public void setUp() throws Exception {
		tab = new TabString();
		tab1 = new TabString(tab);
		tab2 = new TabString();
		for (int i = 0; i < 75; i++) {
			tab2.addChar('a');
		}
		tab3 = new TabString();
		for (int i = 0; i < 70; i++) {
			tab3.addChar('b');
		}
		tab4 = new TabString();
		tab4.copyString(tab3);
		tab5 = new TabString(tab3);
		tab5.fixStart();
		tab5.fixEnd();
		tab6 = new TabString();
		for (int i = 0; i < 60; i++) {
			tab6.addChar('c');
		}
		tab6.fixBoth();
		tab7 = new TabString();
		tab7.addDash(5, 0);
	    tab8 = new TabString();
		tab8.addChar('a');
		tab8.addChar('x');
		tab8.addChar('y');
		tab8.fixBoth();
		tab8.addDash(5, 0);
		tab9 = new TabString();
		tab9.addChar('a');
		tab9.addChar('x');
		tab9.addChar('y');
		tab9.fixBoth();
		tab9.addDash(5, 1);
		tab10 = new TabString();
		tab10.addChar('a');
		tab10.addChar('b');
		tab10.addChar('c');
		tab11 = new TabString();
		tab11.addChar(' ');
		tab11.addChar('a');
		tab11.addChar('b');
		tab11.addChar('c');
		tab11.addChar(' ');
		tab11.delTrailSpaces();
		
		

	}

	@Test
	public void testTabString() {
		assertEquals(75, tab.MAX_SIZE);
		for (int i = 0; i < tab.MAX_SIZE; i++) {
			assertEquals('\0', tab.getChar(i));
		}
		assertEquals(0, tab.size());

	}

	@Test
	public void testTabStringOther() {
		assertEquals(tab.MAX_SIZE, tab1.MAX_SIZE);
		for (int i = 0; i < tab1.MAX_SIZE; i++) {
			assertEquals(tab.getChar(i), tab1.getChar(i));
		}
		assertEquals(tab.size(), tab1.size());

	}

	@Test
	public void testAddChar() {

		assertEquals(75, tab2.size()); // to test if size increments
		for (int i = 0; i < tab.MAX_SIZE; i++) {
			assertEquals('a', tab2.getChar(i));
		}

	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testAddCharException() { // testAddChar for the exception
		tab2.addChar('b');

	}

	@Test
	public void testgetChar() {

		assertEquals('a', tab2.getChar(2));
	}

	@Test
	public void testcopyString() {

		assertEquals(tab3.size(), tab4.size());
		for (int i = 0; i < tab4.size(); i++) {
			assertEquals(tab3.getChar(i), tab4.getChar(i));
		}
	}

	@Test
	public void TestFixStart() {

		assertEquals('|', tab5.getChar(0));

	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testfixStartException() { // testAddChar for the exception
		tab2.fixStart();

	}

	@Test
	public void TestFixEnd() {

		assertEquals('|', tab5.getChar(tab5.size() - 1));

	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testfixEndException() { // testAddChar for the exception
		tab2.fixEnd();

	}

	@Test
	public void TestFixBoth() {

		assertEquals('|', tab6.getChar(0));
		assertEquals('|', tab6.getChar(tab6.size() - 1));

	}

	@Test
	public void TestaddDashType0() {

		int j = 0;

		for (int i = 0; i < tab8.size() - 1; i++) {
			if (tab8.getChar(i) == '-') {
				j++;
			}
		}
		assertEquals(6, j);

		for (int i = 1; i <= 6; i++) {
			assertEquals('-', tab8.getChar(i));
		}

	}

	@Test
	public void TestaddDashType1() {

		int k = 0;

		for (int i = 0; i < tab9.size() - 1; i++) {
			if (tab9.getChar(i) == '-') {
				k++;
			}
		}
		assertEquals(6, k);

		for (int i = 5; i <= 9; i++) {
			assertEquals('-', tab9.getChar(i));
		}

	}
	
	@Test
	public void TestaddDashBase() {
		
		assertTrue(tab7.isEmpty()); //empty
		assertFalse(tab3.addDash(6,0)); // dashes more than MAX_SIZE
		assertFalse(tab3.addDash(2,0)); // if (type == 0 && this.getChar(0) != '|') return false;
		assertFalse(tab3.addDash(1,1)); // if (type == 1 && this.getChar(this.size()- 1) != '|') return false;
	}
	
	@Test
	public void TestisEmpty() {
		assertTrue(tab7.isEmpty());
	}
	
	@Test
	public void TestisFull() {
		assertTrue(tab2.isFull());
	}
	
	@Test
	public void Testsize() {
		assertEquals(0,tab7.size());
	}
	
	@Test
	public void TesttoString() {
		String s = "abc";
		String t = "[empty string]";
		assertEquals(s,tab10.toString());
		assertEquals(t,tab7.toString());
	}
	
	@Test
	public void TestdelTrailSpaces() {
		String s = "abc";
		assertEquals(s,tab11.toString());
		
		
	}
}
	
	
	
	
	
	

	
	