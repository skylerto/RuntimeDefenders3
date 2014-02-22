import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
 
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Sides;
 
public class printPDF {
	String directory;
	PrintService[] ps;
	ArrayList<String> listOfPrinters;
	String setPrinter;
	PrintService myService;
	public printPDF(String directory){
		this.directory = directory;
		DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
	    PrintRequestAttributeSet patts = new HashPrintRequestAttributeSet();
	    patts.add(Sides.DUPLEX);
	    this.ps = PrintServiceLookup.lookupPrintServices(flavor, patts);
	    if (ps.length == 0) {
	        throw new IllegalStateException("No Printer found");
	    }
	    this.myService = null;
		this.listOfPrinters = new ArrayList<String>();
		    for (PrintService printService : this.ps) {
		    	this.listOfPrinters.add(printService.getName());
		    }
		  
	}
	public ArrayList<String> getPrinters(){
		    return this.listOfPrinters;
	}
	public void setPrinter(String printerName){
		this.setPrinter = printerName;
	}
	public void printFile() throws PrintException, IOException{
		for (PrintService printService : this.ps) {
	        if (printService.getName().equals(this.setPrinter)) {
	            this.myService = printService;
	            break;
	        }
	    }

	    if (this.myService == null) {
	        throw new IllegalStateException("Printer not found");
	    }

	    FileInputStream fis = new FileInputStream(this.directory);
	    Doc pdfDoc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
	    DocPrintJob printJob = myService.createPrintJob();
	    printJob.print(pdfDoc, new HashPrintRequestAttributeSet());
	    fis.close();     
	}
	
}