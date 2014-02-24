package version11;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;


public class ReadFromInput {
	private List<List<String>> dynamic_array = new ArrayList<List<String>>();
    private List<String> inner;
    
    private static  String TITLE;
    private static  String SUBTITLE;
    private static  float LINE_SPACING;
    
    private int enable_add = 0;
     
   public ReadFromInput (String filepath) throws FileNotFoundException, IOException {
	   readFile(filepath);
	   
   }
   
   private void readFile(String file_path) throws FileNotFoundException, IOException {
	   
	   try (BufferedReader inputStream = new BufferedReader(new FileReader(file_path))) {
		   String line;
           inner = new ArrayList<String>();
        
           Pattern p_title = Pattern.compile("^(TITLE)(=)(.+)+");
           Pattern p_subtitle = Pattern.compile("^(SUBTITLE)(=)(.+)+");
           Pattern p_spacing = Pattern.compile("^(SPACING)(=)(\\d*(\\.)?\\d+)(?![0-9\\.])");
           Pattern p_music = Pattern.compile("^(\\|)(.+)+");
           while ( (line = inputStream.readLine()) != null)
           {
         
                   Matcher m_title = p_title.matcher(line);
                   Matcher m_subtitle = p_subtitle.matcher(line);
                   Matcher m_spacing = p_spacing.matcher(line);
                   Matcher m_music = p_music.matcher(line);

                
                       
                    if (m_title.find() && !line.isEmpty()) {
                
                       TITLE = m_title.group(3); 
                   }
                   
                   else if (m_subtitle.find() && !line.isEmpty()) {

                	   SUBTITLE = m_subtitle.group(3);
                       
                   }
                   else if (m_spacing.find() && !line.isEmpty()) {
                	   LINE_SPACING=  Float.parseFloat(m_spacing.group(3));
                   }    
                   else if (m_music.find() && !line.isEmpty()) {
                   
                   	if (!line.isEmpty()) {
               		
                   		line  = m_music.group(0);
                   	  
               			//line = line.replaceAll("\\p{Z}", "");
               			inner.add(line);
               			enable_add = 1;	
               		}
                   				        		
                   }
                   else if (line.isEmpty() && enable_add == 1 ) {
                	 
                   	dynamic_array.add(new ArrayList<String>(inner));
           			inner.clear();
           			enable_add =0;
                   }
                   else;
                   
                   
                   
           }  // end of while loop  
           dynamic_array.add(new ArrayList<String>(inner));
           inner.clear();
		   
	   } catch (IOException e) {
			e.printStackTrace();
		} 
	   
   }
   
   String getTITLE() {
	   return TITLE;   
   }
   
   String getSUBTITLE() {
	   return SUBTITLE;   
   }
   
   float getSACING() {
	   return LINE_SPACING;   
   }
   
   void setTITLE(String title) {
	   TITLE = title;
   }
   void setSUBTITLE(String subtitle) {
	   SUBTITLE = subtitle;
   }
   
   void setLineSpacing(float line_space) {
	   LINE_SPACING = line_space;
   }
  
   List<List<String>> getList() {
	   if(dynamic_array.isEmpty())
		   throw new IllegalArgumentException("No music note is found in the input file.");
	   return dynamic_array;
   }

}
