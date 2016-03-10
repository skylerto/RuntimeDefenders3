package mvcV4;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Log object, gets created every time the GUI gets initiallized.
 * 
 * @author Skyler
 * 
 */
public class Log {

	File filename;
	Writer writer;

	/**
	 * Default constructor, constructs a log with the filename as the date upon
	 * initialization.
	 */
	public Log() {
		this.filename = new File("outputfiles/" + System.currentTimeMillis());
		try {
			this.writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(filename), "utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes the passed message to file
	 * 
	 * @param message
	 */
	protected void writeToLog(String message) {

		try {
			writer.write(message);
			writer.close();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
