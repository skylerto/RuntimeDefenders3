package version13;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import version13.MusicSymbols;
import version13.MusicSymbols.Symbols;

public class MusicSymbolsTest {

	MusicSymbols m1;
	MusicSymbols m2;
	MusicSymbols m3;
	MusicSymbols m4;

	@Before
	public void setUp() throws Exception {

		Symbols temp1 = Symbols.OneBar_begin;
		Symbols temp2 = Symbols.Left_Half_Diamond;
		Symbols temp3 = Symbols.Dash;
		Symbols temp4 = Symbols.Star_Begin;
		m1 = new MusicSymbols(temp1, 3);
		m2 = new MusicSymbols(temp2);
		m3 = new MusicSymbols(temp4, 9);
		m4 = new MusicSymbols(temp3);
	}

	@Test
	public void test_default() {
		Symbols expt = Symbols.OneBar_begin; // tests the attribute which
												// contains the enums and the
												// value int
		assertEquals(expt, m1.getchartype());
		assertEquals(3, m1.getValue());
	}

	@Test
	public void test_Symbols_constructor() {

		Symbols expt = Symbols.Left_Half_Diamond; // tests the attribute which
													// contains the enums and
													// the value int
		assertEquals(expt, m2.getchartype()); // int value is 1 as which is
												// default set by the second
												// constructor
		assertEquals(1, m2.getValue());

	}

	@Test
	public void test_getValue() {
		assertEquals(9, m3.getValue());

	}

	@Test
	public void test_getchartype() {
		Symbols expt = Symbols.Dash;
		assertEquals(expt, m4.getchartype());

	}

}

