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
	TabString tab12;
	TabString tab13;
	TabString tab14;
	TabString tab15;
	TabString tab16;
	TabString tab17;
	TabString tab18;
	TabString tab19;
	TabString tab20;
	String s;
	

	@Before
	public void setUp() throws Exception {
		s = "1 2 4 4 5 a";
		tab20 = new TabString(s);
		
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
		tab7.addDash(5);
	    tab8 = new TabString();
		tab8.addChar('a');
		tab8.addChar('x');
		tab8.addChar('y');
		tab8.fixBoth();
		tab8.addDash(5);
		tab9 = new TabString();
		tab9.addChar('a');
		tab9.addChar('x');
		tab9.addChar('y');
		tab9.fixBoth();
		tab9.addDash(5);
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
		tab12 = new TabString();
		tab12.addChar('|');
		tab12.addChar('a');
		tab12.addChar(' ');
		tab12.addChar(' ');
		tab12.addChar('h');
		tab12.addChar('|');
		tab13 = new TabString();
		tab13.addChar('|');
		tab13.addChar('|');
		tab14 = new TabString();
		tab14.addChar('|');
		for (int i = 0; i < 10; i++) {
			tab14.addChar(' ');
		}
		tab14.addChar('|');
		tab15 = new TabString();
		tab15.addChar('c');
		tab16 = new TabString(tab14);
		tab17 = new TabString();
		tab17.addChar('|');
		for (int i = 0; i < 10; i++) {
			tab17.addChar(' ');
		}
		tab17.addChar('|');
		tab18 = new TabString();
		tab18.addChar('|');
		tab18.addChar('|');
		for (int i = 0; i < 10; i++) {
			tab18.addChar(' ');
		}
		tab18.addChar('|');
		tab18.addChar('|');
		tab19 = new TabString(tab2);
		tab19.replaceChar('b', 3);
		

		

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
	public void testTabString_String() {
		assertEquals(75, tab20.MAX_SIZE);
		for (int i = 0; i < s.length(); i++) {
			assertEquals(s.charAt(i), tab20.getChar(i));
		}
		assertEquals(s.length(), tab20.size());

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
	public void TestaddDashBase() {
		
		assertFalse(tab7.addDash(3)); //empty
		assertFalse(tab3.addDash(6)); // dashes more than MAX_SIZE
		assertFalse(tab3.addDash(2)); // if (type == 0 && this.getChar(0) != '|') return false;
		assertFalse(tab3.addDash(1)); // no BARS AT ALL xxxxxx
		assertTrue(tab17.addDash(9)); //strings like this | xxxxxx |
		assertTrue(tab18.addDash(9)); //strings like this || xxxxxx ||
	}
	
	@Test
	public void TestfixSymbols() {
		//1 2 3 4 5 a becomes 1 2 3 4 5--
		//System.out.println(tab20.toString());
		tab20.fixSymbols();
		//System.out.println(tab20.toString());
		for(int i = 0; i <tab20.size();i++){
			assertTrue(tab20.isSymbol(tab20.getChar(i)));
		}
	}
	
	@Test
	public void TestGetSubstring() {
	   String t = tab20.getSubstring(0, 5);
	   String e = s.substring(0,5);
	   assertTrue(e.equals(t));
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
	
	@Test
	public void TestisBlank() {
		
		assertFalse(tab8.isBlank());
		assertFalse(tab12.isBlank());
		assertTrue(tab13.isBlank());
		assertTrue(tab14.isBlank());
	}
	
	@Test
	public void TestisSymbol() {
		
		assertFalse(tab20.isSymbol('a'));
		assertTrue(tab20.isSymbol('3'));
		assertTrue(tab20.isSymbol('|'));
		assertTrue(tab20.isSymbol('-'));
		assertTrue(tab20.isSymbol('s'));
		assertTrue(tab20.isSymbol('h'));
		assertTrue(tab20.isSymbol('p'));
		assertTrue(tab20.isSymbol('*'));
		assertTrue(tab20.isSymbol('<'));
		assertTrue(tab20.isSymbol('>'));
		assertTrue(tab20.isSymbol(' '));
	}
	
	@Test
	public void TesttrimString() {
		
		assertFalse(tab.trimString(3));
		assertFalse(tab12.trimString(3));
		assertFalse(tab3.trimString(3));
		assertFalse(tab15.trimString(3));
		assertTrue(tab14.trimString(13));
		assertTrue(tab16.trimString(3));
	}
	
	
	
	@Test
	public void TestreplaceChar() {
		
		char b = 'b';
		assertEquals(b,tab19.getChar(3));
		
	}
	
	
	
	
}
	
	
	
	
	
	

	
	