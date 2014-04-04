package mvcV4;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RunTest {

	Run r;
	Controller c;
	View v;
	userManualInterface ui;
	EmailerInterface ei;
	PrinterInterface pi;
	Model m ;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		
		r = new Run();
		v = new View();
		c = new Controller(v);
		ui = new userManualInterface();
		ei = new EmailerInterface();
		pi = new PrinterInterface();
		m = new Model();
		c.setModel(m);
		assertEquals(m,c.getModel());
	}

}
