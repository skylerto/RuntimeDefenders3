package tabparts;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class AutofixLogTest {
	
	AutofixLog a1;
	String output;

	@Before
	public void setUp() throws Exception {
		a1 = new AutofixLog();
		a1.write("writing to log with write() method");
		//a1.writeNL("writing to log with writeNL() method");
		//String h = "hello\nworld";
		
		
		
	}

	@Test
	public void Test_defaultConstructor() {
	    String expected_path = "outputfiles\\autofixlog.txt";
	    String expected_name = "autofixlog.txt";
		assertEquals(expected_path,a1.log.getPath().toString());
		assertEquals(expected_name,a1.log.getName());
		
		
	}
	
	@Test
	public void Test_write() throws IOException {
		
		FileReader in = new FileReader(a1.log);
	    BufferedReader br = new BufferedReader(in);
        String line;
	    while ((line = br.readLine()) != null) {
	       output = line;
	    }
	    in.close();
	    
	    
	    assertEquals("writing to log with write() method",output);
	    
	    output = ""; //resetting
	    
	}
	
	//@Test
	//public void Test_writeNL() throws IOException {
		//fail();
	//}
	
	

}
