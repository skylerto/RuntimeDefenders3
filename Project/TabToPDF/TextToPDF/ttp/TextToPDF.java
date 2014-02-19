package ttp;


import java.io.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.itextpdf.text.*;
import com.itextpdf.text.Font.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.*;
public class TextToPDF {
        
		/* What is new :
		 * the method of detecting was for title,subtitle,linespacing  but not 100 (see line 162) % .
		 * Apperantly its adding an empty List<string> to the dynamic_array. I tried to make method
		 * to remove emtpy array but did not work
		 * I fixed the method of detecting subtitle ( not detected b string regex ). In fact it was being mached along with title method
		 *  removal of  dependency of linecount ( to detect title, substring, linespace) making it only dependable on regex  . Plus
		 * I made sure that it only detects TITLE=string,  subTITLE=string only no numbers, subTITLE=string, only no numbers , spacing=float number no string
		 * I added extra if statement for currY to move to add new page when needed
		 */
		/* what needed to be fixed or add :
		 * removal of empty list from dynamic array
		 * when concatinating of list from dynamic array , if the size of dynamic array is even , it does 
		 * all of them. If the size is odd it does not add the last one. added manually , check line 186
		 * add feature to make variable line space 
		 */
	
        /* CONSTANTS */
        
        final float LEFT_MARGIN = 36.0f;
        final float RIGHT_MARGIN = 556.0f;
        final float LINE_SPACE = 7.0f;
        final float PARA_SPACE = 15.0f;
        final float TITLE_SIZE = 26.0f;
        final float SUBTITLE_SIZE = 12.0f;
        final String CONTAINS_TITLE = "TITLE";
        final String CONTAINS_SUBTITLE = "SUBTITLE";
        final String CONTAINS_SPACING = "SPACING";
        final String INPUT_FILENAME = "inputfiles/try2.txt";
        private static String PDF_FILENAME = "outputfiles/musicPDF.pdf";
       
        
        private String title;
        private String subtitle;
        private int spacing;
        
        private List<List<String>> dynamic_array = new ArrayList<List<String>>();
        private List<String> inner;
        private List<List<String>> outerconcat;
        private static final List<Character> special_char  = new ArrayList<Character>();
        PdfWriter document;
        private int same_line_state = 0;
        private int enable_add = 0;
        private float currX, currY ;
        
        
        
        
        
        public void createPDF(String pdfname)throws DocumentException , IOException
        {
                PrintStream output = new PrintStream(System.out);
               
                Document group1 = new Document(PageSize.LETTER);
                File RESULT = new File(pdfname);
                PdfWriter document = PdfWriter.getInstance(group1, new FileOutputStream(RESULT));
        
                group1.open();
                document.open();
                PdfContentByte cb = document.getDirectContent();
                BaseFont bf1 = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1250, BaseFont.EMBEDDED);
                 BaseFont bf_title = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
                Font title_font = new Font(bf_title,26);
                 BaseFont bf_subtitle = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
                Font subtitle_font = new Font(bf_subtitle,14);
                FileReader file = new FileReader (INPUT_FILENAME);
                BufferedReader inputStream = null;
        
        try
        {
        
                inputStream = new BufferedReader(file);  
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
                         	

                        	Paragraph title = new Paragraph(m_title.group(3),title_font);
                            title.setAlignment(Element.TITLE);
                            group1.add(title); 
                        }
                        
                        else if (m_subtitle.find() && !line.isEmpty()) {

                        	Paragraph subtitle = new Paragraph(m_subtitle.group(3), subtitle_font);
                            subtitle.setAlignment(Element.ALIGN_CENTER);
                            group1.add(subtitle);
                            
                        }
                        else if (m_spacing.find() && !line.isEmpty()) {
                        	//output.printf("%s\n", m_spacing.group(3));
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
               for (List<String> s :  dynamic_array)
                {
                	for (String e : s)
                		output.printf("%s\n", e);
                	output.printf("%s\n", "------");
                }
                //output.printf("++%s\n",dynamic_array.get(0).get(0));

                
        } finally{
                        
                        if (inputStream != null)
                        inputStream.close();
        		  }
        
   
        dynamic_array = StringAnchor(dynamic_array);
        currX = 36.0f;
        currY = 680.0f;
   
       for ( int i = 0; i < dynamic_array.size() ; i++) {
        	
        	if (getMusicNotelength(dynamic_array.get(i),5.0f,8) < ( document.getPageSize().getWidth() - currX )) {
        		
        		output.printf("i is   %d\n",i);
        		//output.printf("currx if before adding  %f\n",currX);
        		//output.printf("curry if before adding  %f\n",currY);

        		DrawMusicNote(dynamic_array.get(i),currX,currY,5.0f,8,same_line_state,cb);
        		same_line_state=1;
        		currX = currX + getMusicNotelength(dynamic_array.get(i), 5.0f,8);
        		//output.printf("currx if after adding  %f\n",currX);
        		//output.printf("curry if after adding  %f\n",currY);
        		output.printf("size  %f\n",getMusicNotelength(dynamic_array.get(i),5.0f,8));
        		//output.printf("%s\n",dynamic_array.get(i).get(1));


        		
        		
        		
        		
        	}
        	else  {
        		//output.printf("is   %d\n",i);
        		
        		//output.printf("currx else adding  %f\n",currX);
        		//output.printf("curry else adding  %f\n",currY);
        		currX = 36.0f;
        		currY = currY - 80;
        		same_line_state=0;
        		DrawMusicNote(dynamic_array.get(i),currX,currY,5.0f, 8,same_line_state,cb);
        		
        		currX = currX + getMusicNotelength(dynamic_array.get(i), 5.0f,8);
        		//output.printf("currx else after adding curxx  %f\n",currX);
        		output.printf("size else  %f\n",getMusicNotelength(dynamic_array.get(i),5.0f,8));
        		//output.printf("%s\n",dynamic_array.get(i).get(1));

        		
        		
        	}
        	if ( currY <= 120.0f)
        	{
        		//DrawMusicNote(dynamic_array.get(i),currX,currY,8.0f,cb);
        		//currX = currX + getMusicNotelength(dynamic_array.get(i), 8.0f);
        		group1.newPage();
        		same_line_state=0;
                currX = 36.0f;
                currY = 680.0f;
        	}
        	
        }
        	
      
	     /*for (List<String> s :  dynamic_array)
       {
       	for (String e : s)
       		System.out.printf("new %s\n", e);
       	System.out.printf("%s\n", "------");
       }*/
         //System.out.printf("%f\n",document.getVerticalPosition(true));

        
         //System.out.printf("%f\n",group1.leftMargin());

     System.out.printf("the page width is %f and the page height is %f", group1.getPageSize().getWidth(),group1.getPageSize().getHeight());
 
     //cb.moveTo(100, 100);
     //cb.lineTo(100, 100);
     
     //cb.curveTo(102.5f, 101.3f, 103.8f, 101.3f, 106, 100); // for h
     //cb.curveTo(102.5f, 102.5f, 112.2f, 102.5f, 116, 100);
     //cb.stroke();
     //DrawThickBar(100, 100, 100,100-7.5f, cb);
      //this.InsertText("0",45 , 100, 12, cb);
      //this.DrawLine(45+(8.0f/1.8f), 100,45+(8.0f/1.8f) ,109.23f ,0.5f, cb); // 49 -45 = 5 ,, 8/5 = 1.6 font / point or 1.8 font/point, for line 8= 2.0,2.2
     // this.InsertText("9",45 , 90, 12, cb);
     //for height its 1.3 font/point
     // this.DrawLine(45, 80, 45+13.3f, 80, cb);
      //this.DrawLine(45+13.3f, 90, 45+13.3f, 97, cb);// 12=7 
     this.DrawDiamond(100, 100, 2.0f, 5, cb);
        group1.close();
        document.close();

                
                    
                    
            
     
        }
        
        private String getTitle(String line) {
                String out = "";
                if (line.regionMatches(true, 0, CONTAINS_TITLE, 0, CONTAINS_TITLE.length())) {
                        title = line.substring(CONTAINS_TITLE.length()+1, line.length() - 1);
                        System.out.println(title);
                        out = title;
                }
                return out;
        }
        
        private String getSubtitle(String line) {
                String out = "";
                if (line.regionMatches(true, 0, CONTAINS_SUBTITLE, 0, CONTAINS_SUBTITLE.length())) {
                        subtitle = line.substring(CONTAINS_SUBTITLE.length()+1, line.length() - 1);
                        System.out.println(title);
                        out = subtitle;
                }
                return out;
        }
        
        

        
         private static void InsertText(String text, float x, float y , int Fontsize, PdfContentByte cb) throws DocumentException, IOException {
        	 
        	 BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
             cb.saveState();
             cb.beginText();
             cb.setTextMatrix(x, y);
             cb.setFontAndSize(bf, Fontsize);
             cb.showText(text);
             cb.endText();
             cb.restoreState();
                
                
                
                 }
         private static void DrawLine(float x , float y , float toX, float toY,float thinkess,PdfContentByte cb ) {
        	
        	 cb.setLineWidth(thinkess); // Make a bit thicker than 1.0 default , 0.5f
             cb.setGrayStroke(0.0f);// 1 = black, 0 = white
             cb.moveTo(x,y);
             cb.lineTo(toX,toY);
             cb.stroke();
         
                 
         }
          
         private void DrawCaesura(float x , float y, int font_size, float line_space, PdfContentByte cb) {
         
        	 cb.setLineWidth(0.5f); // Make a bit thicker than 1.0 default
             cb.setGrayStroke(0.20f);// 1 = black, 0 = white
             cb.moveTo(x,y);
             cb.lineTo(x+((line_space/2.0f)-0.3f),y+(font_size/3.4f));
             cb.stroke();
             cb.setLineWidth(0.5f); // Make a bit thicker than 1.0 default
             cb.setGrayStroke(0.20f);// 1 = black, 0 = white
             cb.moveTo(x,y);
             cb.lineTo(x-((line_space/2.0f)-0.3f),y-(font_size/3.4f));
             cb.stroke(); 
         }
         private void OpenNote(float x , float y, int font_size, float line_space, PdfContentByte cb) throws DocumentException, IOException {
         
        	 System.out.println("x is " + x);
        	 float tempx = x- (line_space/2.0f);
        	 float tempy = y +(font_size*0.5f);
        	 BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        	 cb.saveState();
        	 cb.moveTo(tempx, tempy);
        	 cb.curveTo(tempx+2.2f, tempy+1.1f,  (tempx +(line_space*2.0f))-3.5f, tempy+1.1f,  (x +(line_space+line_space/2.0f)), tempy);
             cb.stroke();
             cb.restoreState();
             cb.saveState();
             cb.beginText();
             cb.setTextMatrix(x+line_space/3.0f, tempy+font_size/4.0f);
             cb.setFontAndSize(bf, font_size/2);
             cb.showText("h");
             cb.endText();
             cb.restoreState();
        	
         }
         private void Pause(float x , float y,int num_pos,int curr_pos, int font_size, float line_space, PdfContentByte cb) throws DocumentException, IOException {
         
        	 int  diff = (curr_pos-num_pos)-1; 
        	 float tempx = x- diff* (line_space)-(font_size/3.0f);
        	 float tempy = y +(font_size*0.5f);
        	 BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        	 cb.saveState();
        	 cb.moveTo(tempx, tempy);
        	 cb.curveTo(tempx+2.5f, tempy+1.1f,   ((x +(line_space*2.0f))-(font_size/1.8f))-2.2f, tempy+1.1f,  (x +(line_space*2.0f))-(font_size/1.8f), tempy);
             cb.stroke();
             cb.restoreState();
             cb.saveState();
             cb.beginText();
             cb.setTextMatrix(x-1.8f, tempy+font_size/4.0f);
             cb.setFontAndSize(bf, font_size/2);
             cb.showText("p");
             cb.endText();
             cb.restoreState();
        	
         }
         
         private void DrawCircle(float x, float y, float r ,  PdfContentByte cb) {
        	 cb.saveState();
        	 cb.setColorStroke(BaseColor.BLACK);
        	 cb.setColorFill(BaseColor.BLACK);
        	 cb.circle(x, y, r);
        	 cb.fillStroke();
        	 cb.restoreState();
         }
         
         private void DrawDiamond(float x, float y,float height,float diagonal ,PdfContentByte cb)
         {
        	 cb.saveState();
        	 cb.setColorStroke(BaseColor.BLACK);
        	 cb.setColorFill(BaseColor.WHITE);
        	 cb.moveTo(x, y);
        	 cb.lineTo(x+(diagonal/2.0f), y+height);
        	 cb.lineTo(x+diagonal, y);
        	 cb.lineTo(x+(diagonal/2.0f), y-height);
        	 cb.lineTo(x, y);
        	 cb.closePathFillStroke();
        	 cb.restoreState();
        	 
        	 
         }
         
         
         // this method remove any empty list inside the outer list
        /* private static List<List<String>> RemoveEmtpyList (List<List<String>> list)
         {
        	 for (List<String> inside : list){
        		 if (inside.isEmpty()){
        			 list.remove(list.indexOf((List<String>)inside));
        		 }	 
        	 }
        		return list; 
         }*/
         
         private List<List<String>>  StringAnchor(List<List<String>> list) {
        	 List<String> inside_temp = new ArrayList<String>();
        	 List<List<String>> list_temp = new ArrayList<List<String>>();
        	 for (List<String> inside: list) {
        		 for (String s: inside)
        		 {
        			 inside_temp.add(s + "$$$");
        		 }
        		 list_temp.add(new ArrayList<String>(inside_temp));
        		 inside_temp.clear();
        	 }
        	 return list_temp;
         }
         
         
         private float getMusicNotelength(List<String> inner , float line_space , int FontSize)
         {
        	 float total= 0.0f;
        	
        	 
        		for (int i = 0 ; i < inner.get(0).length()-3 ; i ++) {
        			if (inner.get(0).charAt(i) == '-')
        				total+= line_space;
        			else if (inner.get(0).charAt(i) != '|'&& inner.get(0).charAt(i) != ' ')
        				total+= line_space;
        			else if (inner.get(0).charAt(i) == '|' &&  inner.get(0).charAt(i+1) == '|'  && (inner.get(0).charAt(i+2) != '|' ))
        				total+= line_space;
        			else if (inner.get(0).charAt(i) == '|' &&  inner.get(0).charAt(i+1) == '|'  && inner.get(0).charAt(i+2) == '|')
        				total+= line_space*2.0f;
        			
        			else;
        		 
        	 }
        	 return total;
         }
         int num_pos =0;
         private void DrawMusicNote (List<String> inner , float x , float y , float line_space , int FontSize ,int same_line,PdfContentByte cb  ) throws DocumentException, IOException
         {
        	 float tempx = x;
        	
        	 for (int s= 0  ; s < inner.size(); s++) {
        		 for (int i = 0 ; i < inner.get(s).length()-3 ; i++) {
        			 if (i <=2 && inner.get(s).charAt(i) == '|' &&  inner.get(s).charAt(i+1) == '|' && same_line == 1 && s < inner.size() -1 )
        			 {
        				 DrawLine(x+(line_space/2.0f),y,x+(line_space/2.0f),y-7.5f,0.5f,cb); // draw thin bar
        				 DrawLine(x,y,x+line_space,y,0.5f,cb); // draw horizital for filling gap
        				 
        				 i = i+1;
        				 x+=line_space;
        				 
        				 
        			 }
        			 else if( inner.get(s).charAt(i) == '|' && s < inner.size() -1  &&  inner.get(s).charAt(i+1) != '|') {
        				 
        				 DrawLine(x,y,x,y-7.5f,0.5f,cb);
        				 
        			 }
        		
        			 else if ( inner.get(s).charAt(i) == '|' &&  inner.get(s).charAt(i+1) == '|' && s < inner.size() -1 && i < 2) {
        				 DrawLine(x-0.5f, y, x-0.5f, y-7.5f,2.8f, cb); //shifted by 0.5f
        				 DrawLine(x+(line_space/2.0f),y,x+(line_space/2.0f),y-7.5f,0.5f,cb); // draw thin bar
        				 DrawLine(x,y,x+line_space,y,0.5f,cb); // draw horizital for filling gap
        				 i = i+1;
        				 x+=line_space;
        			 }
        			 else if ( inner.get(s).charAt(i) == '|' &&  inner.get(s).charAt(i+1) == '|' && s < inner.size() -1 && inner.get(s).charAt(i+2) != '|'  && i > 3) {
        				 DrawLine(x+line_space, y, x+line_space, y-7.5f,2.0f, cb);
        				DrawLine((x+line_space)-(line_space/2.0f),y,(x+line_space)-(line_space/2.0f),y-7.5f,0.5f,cb); // draw thin bar before thick bar
        				 DrawLine(x,y,x+line_space,y,0.5f,cb);
        				 i=i+1;
        				 x+=line_space;
        			 }
        			 else if ( inner.get(s).charAt(i) == '|' &&  inner.get(s).charAt(i+1) == '|' && s == inner.size() -1 && i < 3) {
        				 DrawLine(x,y,x+line_space,y,0.5f,cb); // draw horizital for filling gap
        				 i=i+1;
        				 x+=line_space;
        		
        			 }
        			 else if ( inner.get(s).charAt(i) == '|' &&  inner.get(s).charAt(i+1) == '|' && s == inner.size() -1 && inner.get(s).charAt(i+2) != '|' && i > 3) {
         				
        				 DrawLine(x,y,x+line_space,y,0.5f,cb);
        				 
        				 x+=line_space;
        			 }
        			 else if ( inner.get(s).charAt(i) == '|' &&  inner.get(s).charAt(i+1) == '|' && s < inner.size() -1 && inner.get(s).charAt(i+2) == '|'  && i > 3) {
        				 DrawLine(x,y,x,y-7.5f,0.5f,cb);
        				 DrawLine(x,y,x+line_space,y,0.5f,cb);
        				 DrawLine(x+line_space,y,x+line_space,y-7.5f,0.5f,cb);
        				 DrawLine(x+line_space,y,x+(line_space*2.0f),y,0.5f,cb);
        				 DrawLine(x+(line_space*2.0f),y,x+(line_space*2.0f),y-7.5f,0.5f,cb);
        				 i=i+2;
        				 x+=line_space*2.0f;
        			 }
        			 else if ( inner.get(s).charAt(i) == '|' &&  inner.get(s).charAt(i+1) == '|' && s == inner.size() -1 && inner.get(s).charAt(i+2) == '|'  && i > 3) {
        				 
        				 DrawLine(x,y,x+line_space,y,0.5f,cb);
        				 x+=line_space;
        			 }
        			 
        			 else if ( inner.get(s).charAt(i) == '-'&&  inner.get(s).charAt(i+1) != ' ') {
        				 DrawLine(x,y,x+line_space,y,0.5f,cb);
        				x+=line_space;
        				
        			 }
        			 else if (inner.get(s).charAt(i) == '>' ) {
        				 DrawLine(x,y,x+line_space,y,0.5f,cb);
        				 DrawDiamond(x+(line_space*0.1f), y, (FontSize/1.3f)-(FontSize*0.5f), line_space-(line_space*0.15f), cb);
        				 x+=line_space;
        			 }
        			 else if (inner.get(s).charAt(i) == '<' ) {
        				 DrawLine(x,y,x+line_space,y,0.5f,cb);
        				 x+=line_space;
        			 }
        			
        			 else if ( inner.get(s).charAt(i) == '-' &&  inner.get(s).charAt(i+1) == ' ') {
        				DrawLine(x,y,x+line_space,y,0.5f,cb);
        				x+=line_space;
        				i=i+1;
        				
        			 }
        			 else if ( inner.get(s).charAt(i) == '*' && inner.get(s).charAt(i+1) != '|') {
        				 DrawCircle(x+(line_space*0.1f),y,(FontSize/1.3f)-(FontSize*0.6f),cb);// write character taking account how many points it takes
        				DrawLine(x,y,x+line_space,y,0.5f,cb);
        				 x+=line_space;
        			 }
        			 else if ( inner.get(s).charAt(i) == '*' && inner.get(s).charAt(i+1) == '|') {
        				 DrawCircle(x+(line_space*0.9f),y,(FontSize/1.3f)-(FontSize*0.6f),cb);// write character taking account how many points it takes
        				DrawLine(x,y,x+line_space,y,0.5f,cb);
        				 x+=line_space;
        			 }
        			 else if ( inner.get(s).charAt(i) == 's' && ( inner.get(s).charAt(i+1)!= '|' ||  inner.get(s).charAt(i-1)!= '|')) {
        			 
        				
          				 DrawLine(x,y,x+line_space,y,0.5f,cb);
          				 DrawCaesura(x+line_space/2.0f,y,FontSize,line_space,cb);
         				x+=line_space;
        				
        			 }
        			 else if ( inner.get(s).charAt(i) == 's' && ( inner.get(s).charAt(i-1)== '|' )) {
        			 
        				
          				 DrawLine(x,y,x+line_space,y,0.5f,cb);
          				
          				 DrawCaesura(x+((line_space/2.0f)+0.3f),y,(FontSize-2),(line_space-1),cb);
          				 x+=line_space;
        				
        			 }
        			 else if ( inner.get(s).charAt(i) == 's' && ( inner.get(s).charAt(i+1)== '|' )) {
            			 
        				 DrawCaesura(x+((line_space/2.0f)+0.3f),y,(FontSize-2),(line_space-1),cb);
          				 DrawLine(x,y,x+line_space,y,0.5f,cb);
          				 x+=line_space;
        				
        			 }
        			 else if ( inner.get(s).charAt(i) >= '0' &&  inner.get(s).charAt(i) <='9' &&  inner.get(s).charAt(i+1) >= '0' &&  inner.get(s).charAt(i+1) <= '9'){
        				 InsertText( inner.get(s).charAt(i)+"",x,y-(1.0f+(FontSize/4.0f)),FontSize,cb);
        				 InsertText( inner.get(s).charAt(i+1)+"",x+(FontSize/2.5f),y-(1.0f+(FontSize/4.0f)),FontSize,cb);
        				 DrawLine(x+((FontSize/2.5f+(FontSize/1.8f))),y,x+(line_space*2.0f),y,0.5f,cb);
        				 x+=(line_space*2.0f);
        				 num_pos = i+1;
        				 i = i+1;
        			 }
        			 else if ( inner.get(s).charAt(i) >= '0' &&  inner.get(s).charAt(i) <='9' &&  inner.get(s).charAt(i+1)== '|'){
        				 InsertText( inner.get(s).charAt(i)+"",x,y-(1.0f+(FontSize/4.0f)),FontSize,cb);
        				 DrawLine(x+(FontSize/2.3f),y,x+(line_space),y,0.5f,cb);
        				 x+=line_space;
        				 num_pos = i;
        			 }
        			 else if ( inner.get(s).charAt(i) >= '0' &&  inner.get(s).charAt(i) <='9' &&  inner.get(s).charAt(i+1)== ' '){
        				 InsertText( inner.get(s).charAt(i)+"",x,y-(1.0f+(FontSize/4.0f)),FontSize,cb);
        				 DrawLine(x+(FontSize/1.8f),y,x+(line_space),y,0.5f,cb);
        				 x+=line_space;
        				 num_pos = i;
        				 i = i+1;
        			 }
        			 else if ( inner.get(s).charAt(i) == 'h') {
        				 OpenNote(x,y, FontSize,line_space,cb);
        				 DrawLine(x,y,x+line_space,y,0.5f,cb);
        				 x+=line_space;
        			 }
        			 else if ( inner.get(s).charAt(i) == 'p') {
        				 Pause(x,y,num_pos,i,FontSize,line_space,cb);
        				 DrawLine(x,y,x+line_space,y,0.5f,cb);
        				 x+=line_space;
        			 }
        			 
        				 
        			 else if ( inner.get(s).charAt(i) != ' ' && inner.get(s).charAt(i) != '|' ) {
        				 InsertText( inner.get(s).charAt(i)+"",x,y-(1.0f+(FontSize/4.0f)),FontSize,cb);// write character taking account how many points it takes
        				 DrawLine(x+(FontSize/1.8f),y,x+line_space,y,0.5f,cb);
        				 x+=line_space;
        				
        			 }
        				 
        				 
        				 
        		 }
        		x = tempx;
        		y = y - 7.3f; //7.5 for 8 , 10 for 12 
        		 
        	 }
        	
        	 
        	 
         }
         
    
        
        public static void main (String[] args)
        {
                
                
                TextToPDF saad = new TextToPDF();
                
                try {
                        saad.createPDF(PDF_FILENAME);
                } catch (DocumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                 catch (IOException e)
                 {
                        
                 }
        
        
        
                
        }

}
