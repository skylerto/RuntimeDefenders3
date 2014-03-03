package print;
import java.io.IOException;

import javax.print.PrintException;


public class printTests {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws PrintException 
	 */
	public static void main(String[] args) throws PrintException, IOException {
		printPDF test = new printPDF("outputfiles/musicPDF.pdf");//Create instance of class and set directory of PDF file
		System.out.println(test.getPrinters());//Print out all available printers in the network.
		test.setPrinter(test.getPrinters().get(test.getPrinters().size()-1));//Set printer to one of the printers in the network
		test.printFile();//Actually print the file.
		System.out.println("done");
	}

}
