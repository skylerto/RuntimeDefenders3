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
		s1 = new SymbolPoint("*",3.8f,2.1f,4);
		s2 = new SymbolPoint("4",3.8f,2.1f,4);
		s3 = new SymbolPoint("42",3.8f,2.1f,4);
		
	}

	@Test
	public void test_Case1_Constructor() {
		assertEquals("*",s1.getSymbol());
		assertEquals((int)3.8f,(int)s1.getX());
		assertEquals((int)2.1f,(int)s1.getY());
		assertEquals((int)4,(int)s1.getLineNumber());
		
	}
	
	@Test
	public void test_case2_Constructor() {
		assertEquals("4",s2.getSymbol());
		assertEquals((int)3.8f,(int)s2.getX());
		assertEquals((int)2.1f,(int)s2.getY());
		assertEquals((int)4,(int)s2.getLineNumber());
		assertEquals("42",s3.getSymbol());
		assertEquals((int)3.8f,(int)s3.getX());
		assertEquals((int)2.1f,(int)s3.getY());
		assertEquals((int)4,(int)s3.getLineNumber());
		
	}
	

	@Test
	public void test_setSymbol() {
		s1.setSymbol('^'); //set symbol char version
		assertEquals("^",s1.getSymbol());
		s1.setSymbol("50");//set int version double digit
		assertEquals("50",s1.getSymbol()); 
	    s1.setSymbol('9');//set int version single digit
		assertEquals("9",s1.getSymbol()); 
	
		
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
	public void test_getSymbol() {
		assertEquals("42",s3.getSymbol());
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
