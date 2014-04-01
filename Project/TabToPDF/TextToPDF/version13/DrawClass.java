package version13;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import version13.MusicSymbols.Symbols;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;

/**
 *  the draw class will draw in PDF MusicSymbol object from a list that 
 *  contain  MusicSymbol objects from MusicNoteProcess .
 *  . Not all music symbols will drawn in PDF 
 *  immediately . Symbols slide , hammer, pull will be stored in list
 *  list to later drawn in TextToPDF. 
 * @author saad
 *
 */
 	
public class DrawClass {
	
	/* ATTRIBUTES */
	
	/*check to see if that music note has only 1 bar  before second music note   
	 * that begin with two bar on the same line
	 */
	static int onebar_before = 0; // 
	/* list to use to store integers, pulls, hammer, and slides.
	 * its static because it have to collect all numbers from
	 * music note before new page */
	static List<SymbolPoint> symbolp_list =  new ArrayList<SymbolPoint>(); 
	/* list to paint (draw) numbers after it was music note is drawn*/
	private List<SymbolPoint> num_list =  new ArrayList<SymbolPoint>(); 
	/**
	 * 
	 * @param text
	 * @param x
	 * @param y
	 * @param Fontsize
	 * @param cb
	 * @throws DocumentException
	 * @throws IOException
	 */
	void InsertText(String text, float x, float y , int Fontsize, PdfContentByte cb) throws DocumentException, IOException {
     	/* create font for the desired text*/
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        cb.saveState();
        cb.beginText();
        cb.setTextMatrix(x, y); // x,y location in document where to insert text
        
        cb.setFontAndSize(bf, Fontsize); // set font type and size
        cb.showText(text); // display text
        cb.endText();
        cb.restoreState();    
      }
	/**
	 * 
	 * @param x
	 * @param y
	 * @param toX
	 * @param toY
	 * @param thinkess
	 * @param color
	 * @param cb
	 */
    private  void drawLine(float x , float y , float toX, float toY,float thinkess,float color, PdfContentByte cb ) {
  
    	cb.saveState();
     	cb.setLineWidth(thinkess); // Make Line  thicker , 1f thickest
        cb.setGrayStroke(color);// 0 = black color, 1 = white color
        cb.moveTo(x,y); // go to x,y coordinate in document
        cb.lineTo(toX,toY); // draw from x,y to tox,toy
        cb.stroke();
        cb.restoreState();
      }
   /**
    * this will draw slide depending on orientation.If orientation is
    * 1 it will draw slide up. Otherwise, if orientation is -1 it will
    * draw slide down at x,y coordinate.
    * @param x
    * @param y
    * @param orientation 1 for slide up , -1 for slide down
    * @param font_size
    * @param line_space
    * @param cb
    */
   private void slide(float x , float y, int orientation, int font_size, float line_space, PdfContentByte cb) {
       cb.setLineWidth(0.5f); // Make Line  thicker , 1f thickest
       cb.setGrayStroke(0f);// 0 = black color, 1 = white color
       cb.moveTo(x,y);// go to x,y coordinate in document
       /* draw from x,y to upper half or lower half depending on orientation of slide */
       cb.lineTo(x+orientation*((line_space/2.0f)-0.3f),y+(font_size/3.4f));
       cb.stroke();
       cb.setLineWidth(0.5f); // Make a bit thicker than 1.0 default
       cb.setGrayStroke(0f);// 1 = black, 0 = white
       cb.moveTo(x,y);
       /* draw from x,y to upper half or lower half depending on orientation of slide */
       cb.lineTo(x-orientation*((line_space/2.0f)-0.3f),y-(font_size/3.4f));
       cb.stroke();
      }
  /**
   * this will  between two digits whether both digits are on the same
   * music note or on separate music notes.And whether separate music notes are 
   * same line or on different lines.
   * @param x
   * @param y
   * @param tox
   * @param toy
   * @param font_size
   * @param line_space
   * @param cb
   * @throws DocumentException
   * @throws IOException
   */
  private void hammer(float x , float y, float tox, float toy, int font_size, float line_space, PdfContentByte cb) throws DocumentException, IOException {
   	/* create font for the desired text*/
     BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
     cb.saveState();
     cb.setLineWidth(0.5f);
     cb.setGrayStroke(0);
      if (y == toy) {
          cb.saveState();
          cb.moveTo(x+(font_size/1.8f),y +(font_size*0.5f));	 
          cb.curveTo(x+(font_size/1.8f)+2.5f, y +(font_size*0.5f)+(line_space*0.26f),  tox-2.2f, y +(font_size*0.5f)+(line_space*0.26f),  tox, y +(font_size*0.5f));
          cb.stroke();
          cb.restoreState();
          cb.saveState();
          cb.beginText();
          cb.setTextMatrix(x+(font_size/1.8f)+(tox-(x+(font_size/1.8f)))/2f-1f, y +(font_size*0.5f)+(line_space*0.26f));
          cb.setFontAndSize(bf, font_size/2);
          cb.showText("h");
          cb.endText();
          cb.restoreState();
       } else {
           cb.saveState();	 
           cb.moveTo(x+(font_size/1.8f),y+font_size*0.4f);	 
           cb.curveTo(x+(font_size/1.8f)+1.0f, y+font_size*0.45f+(line_space*0.26f), (x+(font_size/1.8f)+(line_space/2f))-1.5f, y+font_size*0.48f+(line_space*0.26f),  x+(font_size/1.8f)+(line_space/2f), y+font_size*0.6f);             
           cb.stroke();
           cb.restoreState();
           cb.saveState();
           cb.beginText();
           cb.setTextMatrix(x+(font_size/1.8f)+(line_space/2f)-1f, y+font_size*0.6f+(line_space*0.26f));
           cb.setFontAndSize(bf, font_size/2);
           cb.showText("h");
           cb.endText();
           cb.restoreState();
           cb.saveState();
           cb.moveTo(tox,toy+font_size*0.4f);	 
           cb.curveTo( tox-(line_space/2f)+2.2f, toy+font_size*0.5f+(line_space*0.26f),tox-2.2f , toy+font_size*0.5f+(line_space*0.26f),  tox-(line_space/2f),toy+font_size*0.7f );
           cb.stroke();
           cb.restoreState();
           cb.saveState();
           cb.beginText();
           cb.setTextMatrix(tox-(line_space/2f)-1f, toy+font_size*0.6f+(line_space*0.26f));
           cb.setFontAndSize(bf, font_size/2);
           cb.showText("h");
           cb.endText();
           cb.restoreState();
          }  	
       cb.restoreState();   	
       }
  /**
   * this will draw hammer between two digits whether both digits are on the same
   * music note or on separate music notes.And whether separate music notes are 
   * same line or on different lines.  
   * @param x
   * @param y
   * @param tox
   * @param toy
   * @param font_size
   * @param line_space
   * @param cb
   * @throws DocumentException
   * @throws IOException
   */
  private void pull(float x , float y,float tox,float toy, int font_size, float line_space, PdfContentByte cb) throws DocumentException, IOException {
       	 
     BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
     cb.saveState();
     cb.setLineWidth(0.5f);// Make Line  thicker , 1f thicker , 0 no thickness
     cb.setGrayStroke(0); // 0 = black color, 1 = white color
     if (y == toy) { // if both music notes are one same line ( y coordinate).	 
        cb.saveState();
        cb.moveTo(x+(font_size/1.8f), y+(font_size*0.5f));	 // move after the digit
        /* draw curve after digit before p and continue until the first digit after p */
        cb.curveTo(x+(font_size/1.8f)/2f+1.3f,  y+(line_space*0.4f)+(font_size*0.5f),  tox,  toy+(line_space*0.4f)+(font_size*0.5f),  tox+(font_size/1.8f)/2f, toy+(font_size*0.5f));
        cb.stroke();
        cb.restoreState();
        cb.saveState();
        cb.beginText();
        /* x,y location in document where to insert text */
        cb.setTextMatrix(x+(tox-x)/2f+1.2f, toy+(font_size*0.5f)+(line_space*0.5f));
        cb.setFontAndSize(bf, font_size/2);
        cb.showText("p");
        cb.endText();
        cb.restoreState();
      } else { // the music notes are not in the same line 
         cb.saveState();	 
         cb.moveTo(x+(font_size/1.8f)/2f,y+font_size*0.4f);	 // move after the digit of the first music note
          /* draw half curve after first digit */
          cb.curveTo(x+(font_size/1.8f)/2f+1.0f, y+font_size*0.45f+(line_space*0.26f), (x+(font_size/1.8f)+(line_space/2f))-1.5f, y+font_size*0.48f+(line_space*0.26f),  x+(font_size/1.8f)+(line_space/2f), y+font_size*0.6f);             
          cb.stroke();
       	  cb.restoreState();
       	  cb.saveState();
          cb.beginText();
          /* x,y location in document where to insert text */
          cb.setTextMatrix(x+(font_size/1.8f)+(line_space/2f)-1f, y+font_size*0.6f+(line_space*0.26f));
          cb.setFontAndSize(bf, font_size/2);
          cb.showText("p");
          cb.endText();
          cb.restoreState();
          cb.saveState();
       	  /* move before  the digit of the second music note */
       	  cb.moveTo(tox+(font_size/1.8f)/2f,toy+font_size*0.4f);	 
          cb.curveTo( tox+(font_size/1.8f)/2f-1f, toy+font_size*0.45f+(line_space*0.26f),tox+(font_size/1.8f)-(line_space/2f)-1.5f , toy+font_size*0.48f+(line_space*0.26f),  tox-(line_space/2f)+0.5f,toy+font_size*0.7f );
          cb.stroke();
          cb.restoreState();
          cb.saveState();
          cb.beginText();
          /* x,y location in document where to insert text */
          cb.setTextMatrix(tox-(line_space/2f)-1f, toy+font_size*0.6f+(line_space*0.26f));
          cb.setFontAndSize(bf, font_size/2);
          cb.showText("p");
          cb.endText();
           cb.restoreState();
       	 }
     	  
          cb.restoreState();	
  }
      
  /**
   * this will draw circle that is filled with black color with specified radius
   * from x,y coordinate    
   * @param x
   * @param y
   * @param r
   * @param cb
   */
  private void DrawCircle(float x, float y, float r ,  PdfContentByte cb) {
    	  
     cb.saveState();
     cb.setColorStroke(BaseColor.BLACK); // set the line color of circle
     cb.setColorFill(BaseColor.BLACK); // set the area of circle to be filled
     cb.circle(x, y, r); // draw circle from x,y with r radius
     cb.fillStroke(); // fill area of circle
     cb.restoreState();
  }
  
  /**
   * the method will draw diamond from x,y at certain height with certain diagonal.    
   * @param x
   * @param y
   * @param height
   * @param diagonal
   * @param cb
   */
  private void DrawDiamond(float x, float y,float height,float diagonal ,PdfContentByte cb) {
  
     cb.saveState();
     cb.setLineWidth(0.5f); // Make Line  thicker , 1f thicker , 0 no thickness
     cb.setGrayStroke(0);	// 0 = black color, 1 = white color
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
  /**
   * the method  will draw lines extending from music note to x,y .
   * Usually the x,y are the left or right margins.
   * @param list
   * @param x
   * @param y
   * @param tox
   * @param FontSize
   * @param cb
   */
  void DrawMarginMusicLines (List<MusicSymbols> list, float x, float y,float tox,int FontSize,  PdfContentByte cb) {   
     int lines  = list.get(list.size()-2).getValue(); // get the number of lines the music have
     while (lines > 0) { 
     	drawLine(x,y,tox,y,0.5f,0, cb); // draw line
     	y-=FontSize*0.9f; // move to new y-coordinate to draw next line
     	lines--;	 
     	  }
     	 
      }
   /**
    * the method will return the size(length) of music note provided
    * line spacing.Varible line spacing will give different size  
    * @param list
    * @param line_space
    * @return
    */
  float getMusicNotelength(List<MusicSymbols> list, float line_space) {
     int i = 0;
     float total = 0;
     while (list.get(i).getchartype() != Symbols.End_music_line) { // stop when it reaches end of music note
    		   switch(list.get(i).getchartype()) {
    		   
    		   case Dash: case Slide: case Pull: case Hammer: case Left_Half_Diamond: case Right_half_Diamond: case Star_End: case Star_Begin:	 
    			   total+=(line_space*list.get(i).getValue());
    			   break;
    		   case OneBar_begin: case OneBar_end: case OneBar_begin_lastline: case OneBar_end_lastline:
    			   total+=(5*0.5f);
    			   break;
    		   case Two_Bar_begin :case Two_Bar_begin_lastline: 
    			   total+=(10);
    			   break;
    		   case Two_Bar_end: case Two_Bar_end_lastline: 
    			   total+=(10);
    			   break;	  	   
    		   case Three_Bar_End :
    			   total+=(line_space*2.5f);
    			   break;
    		   case One_digit: 
    			   total+=(line_space);
    			   break;
    		   case Two_digit:
    			   total+=(line_space*2f);
    			   break;
    		   case Space:
    			   total+=(line_space*0.1f);
    			   break;
    		   case End_music_line : case End_music_note:
    			   break;
    		   default:
    			   break;
    		   
    		   }
    		  
    		 i++;  
    	   }
        	   return total;  	   
       }
 /**
  * the method will draw numbers and diamond after music note is drawn 
  * to avoid horizontal lines passing through them when line spacing
  * is small
  * @param line_space
  * @param FontSize
  * @param cb
  * @throws DocumentException
  * @throws IOException
  */
   private void paintNumbers (float line_space, int FontSize, PdfContentByte cb ) throws DocumentException, IOException  {
	  for (int i = 0 ; i < num_list.size() ; i++) {
		  if (num_list.get(i).getSymbol()== "<>")	  
			  DrawDiamond(num_list.get(i).getX()+(line_space*0.2f), num_list.get(i).getY(), (FontSize/1.3f)-(FontSize*0.5f), FontSize*0.5f, cb);
		  else {
			  if (Integer.parseInt(num_list.get(i).getSymbol())<10) { 
				  drawLine(num_list.get(i).getX(),num_list.get(i).getY()+(1.0f+(FontSize/4.0f)),num_list.get(i).getX()+(FontSize/1.8f),num_list.get(i).getY()+(1.0f+(FontSize/4.0f)),0.5f,1f, cb);
				  InsertText(num_list.get(i).getSymbol(), num_list.get(i).getX(), num_list.get(i).getY(), FontSize, cb);
			  } else {
				 
				  drawLine(num_list.get(i).getX(),num_list.get(i).getY()+(1.0f+(FontSize/4.0f)),num_list.get(i).getX()+FontSize/2.1f+FontSize/1.8f,num_list.get(i).getY()+(1.0f+(FontSize/4.0f)),0.5f,1f, cb);
				  InsertText(num_list.get(i).getSymbol().charAt(0)+"", num_list.get(i).getX(), num_list.get(i).getY(), FontSize, cb);
				  InsertText(num_list.get(i).getSymbol().charAt(1)+"", num_list.get(i).getX()+(FontSize/2.1f), num_list.get(i).getY(), FontSize, cb);
				  
			  }
			  
		  }
		  
			  
	  }
  }
  /**
   * this method will draw every music character except slides,pull , hammer.
   * however there cases will be visited to draw lines and incrementing x coordinate
   * @param list
   * @param x
   * @param y
   * @param line_space
   * @param FontSize
   * @param same_line
   * @param cb
   * @throws DocumentException
   * @throws IOException
   */
  void DrawMusicNote (List<MusicSymbols> list , float x , float y , float line_space , int FontSize ,int same_line,PdfContentByte cb  ) throws DocumentException, IOException {
	float tempx = x; 
    int count_music_lines = 0; // give the current music line
    int is_second_beginstar = 0; // tell whether the beginning of the star is first one or second one
    int is_second_endnstar = 0; // tell whether the beginning of the star is first one or second one
    int star_end_enable = 0;
        	for (int i = 0 ; i < list.size() ; i++) {
        		switch (list.get(i).getchartype()) {
        		
        		case OneBar_begin:
        			drawLine(x,y,x,y-(FontSize*0.9f),0.5f,0, cb); // draw vertical line that has length of  dependent on fontsize
        			drawLine(x,y,x+(5*0.5f),y,0.5f,0, cb);// draw horizontal  line that has length line dependsnce on line space 
        			x+=(5*0.5f); // move and update my x-coordinate
        			break;
        		case OneBar_end:
        			/*draw line between last music symbol detected and vertical line at the end*/
        			drawLine(x,y,x+(5*0.5f),y,0.5f,0, cb);
        			x+=(5*0.5f);// move and update my x-coordinate
        			drawLine(x,y,x,y-(FontSize*0.9f),0.5f,0, cb);// draw vertical line that has length of  dependent on fontsize
        			break;
        		case OneBar_begin_lastline: case OneBar_end_lastline :
        			/* don't draw vertical line for the last lines of the music note.
        			 * but draw line between last music symbol detected and vertical line at the end */
        			drawLine(x,y,x+(5*0.5f),y,0.5f,0, cb);
        			x+=(5*0.5f);// move and update my x-coordinate
        			break;
        		case Two_Bar_begin :
        			switch (same_line) { // if there was music note before this music note on same line
        			case 0 : // false
        				 /* draw thick vertical line length of  dependance on fontsize*/
        				 drawLine(x-0.5f, y, x-0.5f, y-(FontSize*0.9f),2.8f, 0, cb); 
        				 /*draw line between last music symbol detected and vertical line at the end*/
        				 drawLine(x,y,x+(10),y,0.5f,0, cb);
         				 x+=(10); // move and update my x-coordinate    			
         				 break;
        			case 1: // true 
        				switch (onebar_before) { // if the previous note had one bar
        				case 0 : //false, so its two bar
        					 /*this will not draw thick bar for this note because 
       				         a thick bar was already drawn for the previous note.*/
        					 drawLine(x,y,x+(10),y,0.5f,0, cb); // draw horizital for filling gap
             				 x+=(10);// move and update my x-coordinate
             				 break;
        				case 1 : 	
        					 /*this will a draw thick bar for this note because 
      				         a one vertical  bar was already drawn for the previous note.*/
        					 /* draw thick vertical line length of  dependance on fontsize*/
        					 drawLine(x-0.5f, y, x-0.5f, y-(FontSize*0.9f),2.8f, 0, cb); 
        					 /*draw line between last music symbol detected and vertical line at the end*/
             				 drawLine(x,y,x+(10),y,0.5f,0, cb);
             				 x+=(10);// move and update my x-coordinate
             				
             				 break;
        				default:break; 
        				}		
         				 break;
         			default:break; 		 			
        			}
        			break;
        			
        		case Two_Bar_end:
        			if (star_end_enable == 1) {		
        			    DrawCircle(x+2,y,(FontSize/1.3f)-(FontSize*0.6f),cb);
      				    if (is_second_endnstar == 0) {
      				    	/* draw vertical line upward from the star till first line */
        				    drawLine(x+5,y,x+5,y+((FontSize*0.9f)*(count_music_lines)),0.5f,0, cb);
        				    is_second_endnstar = 1;
        			    } else {
        			    	/* draw vertical line upward from the second star to first star*/
        				    drawLine(x+5,y,x+5,y+(FontSize*0.9f),0.5f,0, cb);
        				    /* draw vertical line upward from the second star till last line */
        				    drawLine(x+5,y,x+5,y-((FontSize*0.9f)*(list.get(list.size()-2).getValue()-count_music_lines-1)),0.5f,0, cb);
        				    is_second_endnstar = 0;		
        			     }
        			}
        			star_end_enable = 0;
        			drawLine(x,y,x+(10),y,0.5f,0, cb); // draw horizontal  line that has length line dependsnce on line space 
        			  /*this will a draw thick bar for this note because 
				      a one vertical  bar was already drawn for the previous note.*/
        			  drawLine(x+(5*2f)-0.5f, y, x+(5*2f)-0.5f, y-(FontSize*0.9f),2.8f, 0, cb);
    				x+=(10);  // move and update my x-coordinate
        			break;	
        		case Two_Bar_begin_lastline : // a thick bar will not be drawn for the last line
        			drawLine(x,y,x+(10),y,0.5f,0, cb);// draw horizontal  line that has length line dependance on line space 
    				x+=(5*2f);// move and update  x-coordinate
    				break;
        		case Two_Bar_end_lastline:// a thick bar will not be drawn for the last line
        			drawLine(x,y,x+(10),y,0.5f,0, cb);// draw horizontal  line that has length line dependance on line space 
    				x+=(5*2f);// move and update  x-coordinate		
    				break;
        		case Three_Bar_End :
        			if(list.get(i+1).getValue() < list.get(list.size()-2).getValue()) { 
        				drawLine(x+(line_space*0.5f),y,x+(line_space*0.5f),y-((FontSize*0.9f)),0.5f,0, cb);
        				drawLine(x+(line_space*1.5f),y,x+(line_space*1.5f),y-((FontSize*0.9f)),0.5f,0, cb);
        				drawLine(x+(line_space*2.5f),y,x+(line_space*2.5f),y-((FontSize*0.9f)),0.5f,0, cb);
        			}
        			/* draw horizontal line to connect the three bars*/
        			drawLine(x,y,x+(line_space*2.5f),y,0.5f,0, cb);
    				x+=(line_space*2.5f);// move and update  x-coordinate		
    				break;
        		case Dash: case Left_Half_Diamond:
        			/*draw horizontal  line for left diamond*/
        			drawLine(x,y,x+(line_space*list.get(i).getValue()),y,0.5f,0, cb);
     				x+=(line_space*list.get(i).getValue());// move and update  x-coordinate
     				break;
        		case One_digit:
        			/* draw horizontal line for two digits*/
        			drawLine(x, y, x+line_space, y, 0.5f,0, cb);
        			/* add integer and its x,y coordinate so that special symbol can use to draw arcs*/ 
    				symbolp_list.add(new SymbolPoint(list.get(i).getValue()+"",x,y,count_music_lines+1));
    				/* add integer and its coordinate to list to be drawn after music note is drawn 
    				to avoid line passing through it*/
    				num_list.add(new SymbolPoint(list.get(i).getValue()+"",x,y-(1.0f+(FontSize/4.0f)),0));
    				x+=line_space; // move and update  x-coordinate
    				break;
        		case Two_digit:
        			/* draw horizontal line for two digits*/
        			drawLine(x, y, x+(line_space*2.0f), y, 0.5f,0, cb);
        			/* add integer and its x,y coordinate so that special symbol can use to draw arcs*/ 
    				symbolp_list.add(new SymbolPoint(list.get(i).getValue()+"",x,y,count_music_lines+1));
    				/* add integer and its coordinate to list to be drawn after music note is drawn 
    				to avoid line passing through it*/
    				num_list.add(new SymbolPoint(list.get(i).getValue()+"",x-(line_space*0.3f),y-(1.0f+(FontSize/4.0f)),0));
        			x+=(line_space*2.0f); // move and update  x-coordinate
        			break;
        		case Slide:
        			/* insert slide in the array*/
        			symbolp_list.add(new SymbolPoint("s",x,y,count_music_lines+1));
        			/*draw horizontal  line for slide*/
       				drawLine(x,y,x+line_space,y,0.5f,0, cb);
       				x+=line_space;// move and update  x-coordinate
       				break;
        		case Hammer:
        			/*draw horizontal  line for hammer*/
    				drawLine(x,y,x+line_space,y,0.5f,0, cb);
    				/* insert slide in the array*/
    				symbolp_list.add(new SymbolPoint("h",x,y,count_music_lines+1));
    				x+=line_space; // move and update  x-coordinate
    				break;
        		case Space:
        			/*draw small horizontal  line for space*/
        			drawLine(x,y,x+(line_space*0.1f),y,0.5f,0, cb);
        			x+=(line_space*0.1f); // move and update  x-coordinate
        			break;
        		case Pull:
        			/* insert slide in the pull*/
        			symbolp_list.add(new SymbolPoint("p",x,y,count_music_lines+1));
        			/*draw horizontal  line for pull*/
    				drawLine(x,y,x+line_space,y,0.5f,0, cb);
    				x+=line_space;// move and update  x-coordinate
    				break;
        		case Star_Begin:
        			if (is_second_beginstar == 0) {
        				/* draw vertical line upward from the star till first line */
        				drawLine(x-(6),y,x-(6),y+((FontSize*0.9f)*(count_music_lines)),0.5f,0, cb);
        				is_second_beginstar = 1;
        			} else {
        				/* draw vertical line upward from the second star to first star*/
        				drawLine(x-(6),y,x-(6),y+(FontSize*0.9f),0.5f,0, cb);
        				/* draw vertical line downward from the second star till last line */
        				drawLine(x-(6),y,x-(6),y-((FontSize*0.9f)*(list.get(list.size()-2).getValue()-count_music_lines-1)),0.5f,0, cb);
        				is_second_beginstar = 0;
        				
        			}		
        			DrawCircle((x-3)+(5*0.1f),y,(FontSize/1.3f)-(FontSize*0.6f),cb);
        			drawLine(x,y,x+line_space,y,0.5f,0, cb);
      				x+=line_space;	;// move and update  x-coordinate
      				
      				break;
        		case Star_End:
        			star_end_enable = 1;
        			drawLine(x,y,x+line_space,y,0.5f,0, cb);
      				x+=line_space;
      				break;
        		case Right_half_Diamond:
        			/* draw horizontal line for right half diamond*/
        			drawLine(x,y,x+line_space,y,0.5f,0, cb);
        			/* add diamond and its coordinate to list to be drawn after music note is drawn 
        			 * to avoid line passing through it*/
    				num_list.add(new SymbolPoint("<>",x,y,0));
        			//DrawDiamond(x+(line_space*0.2f), y, (FontSize/1.3f)-(FontSize*0.5f), FontSize*0.5f, cb);
    				x+=line_space;// move and update  x-coordinate
    				break;
        		case End_music_line:	
        			x = tempx; // reset the x-coordinate
        			y = y - (FontSize*0.9f); // move y-coordinate to the next line
        			count_music_lines++; 
    				break;
        		case End_music_note:
        			/* when music finished , tell the next music note whether 
        			 * its on the same line or not for one bar case*/
        			if(list.get(i-2).getchartype() == Symbols.OneBar_end_lastline)
        				onebar_before = 1;
        			else 
        				onebar_before = 0;
    			default:break;    		
        		}
        	}
     /* paint the numbers after music note is drawn to avoid lines
     * passing through numbers such as 0 when line spacing is
     * small.     		
     */
     paintNumbers(line_space,FontSize, cb);
  }
  /**
   * this method will draw hammer,slide , and pull by grabbing 
   * the previous number and next of the desired symbol./
   * @param FontSize
   * @param line_space
   * @param cb
   * @throws DocumentException
   * @throws IOException
   */
           
  public void drawSymbols(int FontSize,float  line_space,PdfContentByte cb) throws DocumentException, IOException {
	  	
      SymbolPoint prev_nu = null;
      SymbolPoint next_nu = null;
      for (int i =0 ; i < symbolp_list.size(); i++) {  
          if (symbolp_list.get(i).getSymbol() == "h") {
        	  prev_nu = getNumberPrevious(symbolp_list.get(i),i);
        	  next_nu= getNumberNext(symbolp_list.get(i),i);
        	  if (prev_nu == null || next_nu == null) { 
                  symbolp_list.remove(i);
        		  continue;
        	  }
        	  /* draw hammer by get getting x,y coordinate of previous number to the x,y of the second number*/
        	  hammer( prev_nu.getX(),prev_nu.getY(), next_nu.getX(),next_nu.getY(),FontSize,line_space,cb);		
           } else if (symbolp_list.get(i).getSymbol()== "p") {
        	  prev_nu = getNumberPrevious(symbolp_list.get(i),i);
        	  next_nu= getNumberNext(symbolp_list.get(i),i);
        	  if (prev_nu == null || next_nu == null){
        		   symbolp_list.remove(i);
        		   continue;
        	   }
        	  /* draw pull by get getting x,y coordinate of previous number to the x,y of the second number*/		
        	  pull( prev_nu.getX(),prev_nu.getY(), next_nu.getX(),next_nu.getY(),FontSize,line_space,cb);	
           } else if (symbolp_list.get(i).getSymbol() == "s") {
        	   prev_nu = getNumberPrevious(symbolp_list.get(i),i);
        	   next_nu= getNumberNext(symbolp_list.get(i),i);
        	   if (prev_nu == null || next_nu == null) {
        		   symbolp_list.remove(i);
        		   continue;
        	   }			
        	   if (Integer.parseInt(prev_nu.getSymbol()) > Integer.parseInt(next_nu.getSymbol()))
        		   /* draw slide down*/
        		   slide(symbolp_list.get(i).getX()+line_space/2f,symbolp_list.get(i).getY(),-1,(FontSize-2),(line_space-1), cb);			
        	  else 
        		  /* draw slide up*/
        		   slide(symbolp_list.get(i).getX()+line_space/2f,symbolp_list.get(i).getY(),1,(FontSize-2),(line_space-1), cb); 			
        	}
        }
  }
 
        
  /**
   * this method will print the symbol point list      
   */
  void printSymbolList() { 
      for(SymbolPoint ss : symbolp_list) 
    	  ss.Print();        	
  }
  /**
   * the method will return the previous number before
   * h, p, s from same , different music note on the same
   * line  
   * @param sp the music symbol
   * @param index the music symbol location
   * @return
   */
  private SymbolPoint getNumberPrevious(SymbolPoint sp , int index) {  
      
	  for (int i =index ; i >= 0; i--)   
    	  if (sp.getLineNumber() ==symbolp_list.get(i).getLineNumber() && symbolp_list.get(i).getSymbol() != "h" &&  symbolp_list.get(i).getSymbol() != "p" &&symbolp_list.get(i).getSymbol() != "s" )
    		   return symbolp_list.get(i);	 		
      return null;
  }
  /**
   * the method will return the next number before
   * h, p, s from same , different music note on the same
   * @param sp
   * @param index
   * @return
   */
  private SymbolPoint getNumberNext(SymbolPoint sp , int index) {	
 	  
	  for (int i =index ; i < symbolp_list.size(); i++) 
 		   if(sp.getLineNumber() ==symbolp_list.get(i).getLineNumber() && symbolp_list.get(i).getSymbol() != "h" &&  symbolp_list.get(i).getSymbol() != "p" &&symbolp_list.get(i).getSymbol() != "s")
 				return symbolp_list.get(i);	
 	  return null;
  }
  /**
   * this method will clear the symbol point list
   */
     
  void FlushSymbol() {
    	  symbolp_list.clear();
  }
      

}