package tabparts;

import java.io.*;

/**
 * Creates an auto-fix log file that stores the before and after effects all the strings
 * that were auto-fixed in the text file. The autofixlog.txt file is located in the
 * outputfiles/ folder. The log is empty if the input file converted without any
 * auto-fixing.
 * 
 * @author Ron
 *
 */
public class AutofixLog {
	
	/* CONSTANTS */
	
	public static final String LOG_NAME = "autofixlog.txt"; //also used for testing purposes
	public static final String LOG_PATH = "outputfiles/" + LOG_NAME;
	
	/* ATTRIBUTES */
	
	public File log; //made public for testing
	private PrintStream stream;
	
	/**
	 * Creates the new auto-fix file.
	 */
	public AutofixLog() {
		this.log = new File(LOG_PATH);
		try {
			//System.out.println("Opening " + LOG_NAME);
			this.stream = new PrintStream(this.log);
		} catch (FileNotFoundException e) {
			System.err.println("Error: The file " + LOG_NAME + " could not be found.");
		}
	}
	
	/**
	 * Writes to the log file without line breaks.
	 * 
	 * @param line The string to be written
	 */
	public void write(String line) {
		//System.out.println("Writing [" + line + "] to " + LOG_NAME);
		this.stream.print(line);
	}
	
	/**
	 * Writes to the log file  with a line break.
	 * 
	 * @param line The string to be written
	 */
	public void writeNL(String line) {
		//System.out.println("Writing [" + line + "] to " + LOG_NAME);
		this.stream.println(line);
	}
	
	/**
	 * Closes the log file.
	 */
	public void close() {
		//System.out.println("Closing " + LOG_NAME);
		this.stream.close();
	}
	
	/**
	 * Checks to see if the called autofixlog.txt in the input folder is
	 * empty or not.
	 * 
	 * @return true if empty, false otherwise
	 */
	public static boolean isEmpty() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(LOG_PATH));
			if (br.readLine() == null) {
			    return true;
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
