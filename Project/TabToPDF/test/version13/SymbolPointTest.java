package version13;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SymbolPointTest {
	
	SymbolPoint s1;
	SymbolPoint s2;
	SymbolPoint s3;
	SymbolPoint s4;

	@Before
	public void setUp() throws Exception {
		s1 = new SymbolPoint('*',3.8f,2.1f,4);
		s2 = new SymbolPoint(4,3.8f,2.1f,4);
		s3 = new SymbolPoint(42,3.8f,2.1f,4);
		s4 = new SymbolPoint(s1);
	}

	@Test
	public void test_CharConstructor() {
		assertEquals("*",s1.getSymbol());
		assertEquals((int)3.8f,(int)s1.getX());
		assertEquals((int)2.1f,(int)s1.getY());
		assertEquals((int)4,(int)s1.getLineNumber());
		assertFalse(s1.isDoubleDigit());
	}
	
	@Test
	public void test_IntConstructor() {
		assertEquals("4",s2.getSymbol());
		assertEquals((int)3.8f,(int)s2.getX());
		assertEquals((int)2.1f,(int)s2.getY());
		assertEquals((int)4,(int)s2.getLineNumber());
		assertFalse(s2.isDoubleDigit());
		assertEquals("42",s3.getSymbol());
		assertEquals((int)3.8f,(int)s3.getX());
		assertEquals((int)2.1f,(int)s3.getY());
		assertEquals((int)4,(int)s3.getLineNumber());
		assertTrue(s3.isDoubleDigit());
	}
	
	@Test
	public void test_CopyConstructor() {
		assertEquals(s1.getSymbol(),s4.getSymbol());
		assertEquals((int)s1.getX(),(int)s4.getX());
		assertEquals((int)s1.getY(),(int)s4.getY());
		assertEquals(s1.isDoubleDigit(),s4.isDoubleDigit());
		
	}
	
	@Test
	public void test_isDoubleDigit() {
		assertTrue(s3.isDoubleDigit());
		assertFalse(s4.isDoubleDigit());
	}
	
	@Test
	public void test_setSymbol() {
		s4.setSymbol('^'); //set symbol char version
		assertEquals("^",s4.getSymbol());
		s4.setSymbol(50);//set int version double digit
		assertEquals("50",s4.getSymbol()); 
		assertTrue(s4.isDoubleDigit()); //checking the flag
		s4.setSymbol(9);//set int version single digit
		assertEquals("9",s4.getSymbol()); 
		assertFalse(s4.isDoubleDigit()); //checking the flag
		
	}
	
	@Test
	public void test_setX() {
		s1.setX(7.1f);
		assertEquals((int)7.1f,(int)s1.getX());
		
	}
	
	@Test
	public void test_setY() {
		s1.setY(3.1f);
		assertEquals((int)3.1f,(int)s1.getY());
	}
	
	@Test
	public void test_setLineNumber() {
		s1.setLine_number(5);
		assertEquals(5,s1.getLineNumber());
	}
	
	@Test
	public void test_getChar() {
		assertEquals('4',s3.getChar());
	}
	
	@Test
	public void test_getX() {
		assertEquals((int)3.8f,(int)s2.getX());
	}
	
	@Test
	public void test_getY() {
		assertEquals((int)2.1f,(int)s2.getY());
	}
	
	@Test
	public void test_LineNumber() {
		assertEquals((int)4,(int)s2.getLineNumber());
	}
	
}
