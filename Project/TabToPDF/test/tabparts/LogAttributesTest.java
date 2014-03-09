package tabparts;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LogAttributesTest {

	LogAttributes alpha;
	LogAttributes bravo;
	LogAttributes charlie;

	// LogAttributes delta;
	// LogAttributes echo;
	// LogAttributes foxtrot;

	@Before
	public void setUp() throws Exception {
		alpha = new LogAttributes();
		bravo = new LogAttributes(5, "test cases", true);
		charlie = new LogAttributes();
		charlie.copyLogAtt(bravo);
	}

	@Test
	public void Test_DefaultConstructor() {
		String numb = "";
		assertEquals(-1, alpha.getLineNum());
		assertEquals(numb, alpha.getOriginal());
		assertFalse(alpha.isFixed());
	}

	@Test
	public void Test_CustomConstructor() {
		String numb = "test cases";
		assertEquals(5, bravo.getLineNum());
		assertEquals(numb, bravo.getOriginal());
		assertTrue(bravo.isFixed());

	}

	@Test
	public void Test_CopyLogAttributes() {
		assertEquals(bravo.getLineNum(), charlie.getLineNum());
		assertEquals(bravo.getOriginal(), charlie.getOriginal());
		assertEquals(bravo.isFixed(), charlie.isFixed());

	}

	@Test
	public void Test_GetLineNumber() {
		assertEquals(5, bravo.getLineNum());
	}

	@Test
	public void Test_SetLineNumber() {
		bravo.setLineNum(6);
		charlie.setLineNum(3);
		assertEquals(6, bravo.getLineNum());
		assertEquals(3, charlie.getLineNum());
	}

	@Test
	public void Test_GetOriginal() {
		assertEquals("test cases", bravo.getOriginal());
	}

	@Test
	public void Test_SetOriginal() {
		bravo.setOriginal("hello");
		charlie.setOriginal("bye");
		assertEquals("hello", bravo.getOriginal());
		assertEquals("bye", charlie.getOriginal());
	}

	@Test
	public void Test_IsFixed() {
		assertEquals(true, charlie.isFixed());
		assertEquals(true, bravo.isFixed());
		assertEquals(false, alpha.isFixed());

	}

	@Test
	public void Test_CheckFixed() {

		String a = "hi"; // assumed "a" was autofixed
		String b = "test cases"; // assumed no autofixes were done
		assertTrue(bravo.checkFixed(a));
		assertFalse(charlie.checkFixed(b));

	}

}
