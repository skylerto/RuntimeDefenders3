package mvcV4;

import java.io.File;

import javax.swing.filechooser.FileFilter;
/**
 * this class filter the file types in JFileChooser
 * @author saad
 *
 */

public class FileTypeFilter extends FileFilter {
	private String[] extensions;
	private String description;
	
	/**
	 * this create file description of files that wants to be selected
	 * @param description
	 * @param extentions
	 */
	public FileTypeFilter (String description, String[] extentions) {
		this.description = description;
		this.extensions = extentions;
		
	}
	
	/**
	 * tell whether file is valid or not
	 * @param file
	 * @return boolean
	 */
	public boolean accept(File file) {
		if(file.isDirectory())
				return true;
		String filepath = file.getAbsolutePath();
		for (int i = 0 ; i < extensions.length ; i ++) {
			if(filepath.endsWith(extensions[i]) && filepath.charAt(filepath.length()-extensions[i].length()) == '.')
				return true;
				
		}
		return false;
	}
	
	/**
	 * get description of file types
	 * @return String
	 */
	public String getDescription() {
		return (description == null ? extensions[0] : description);
	}
	
	

}
