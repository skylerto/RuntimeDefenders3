package version13;
import java.io.IOException;import java.util.ArrayList;
import java.util.List;

import version13.MusicSymbols.Symbols;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;

 	
public class DrawClass {
	static float p_detect_pos = 0f;
	static int onebar_before = 0; // check to see if that music note has only 1 bar  before second music note  that begin with two bar on the same line
	static List<SymbolPoint> symbolp_list =  new ArrayList<SymbolPoint>();
	
	    void InsertText(String text, float x, float y , int Fontsize, PdfContentByte cb) throws DocumentException, IOException {
     	 
     	 BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
          cb.saveState();
          cb.beginText();
          cb.setTextMatrix(x, y);
          cb.setFontAndSize(bf, Fontsize);
          cb.showText(text);
          cb.endText();
          cb.restoreState();

          
      }
      private  void drawLine(float x , float y , float toX, float toY,float thinkess,float color, PdfContentByte cb ) {
     	
    	  cb.saveState();
     	  cb.setLineWidth(thinkess); // Make a bit thicker than 1.0 default , 0.5f
          cb.setGrayStroke(0.0f);// 0 = black, 1 = white
          cb.moveTo(x,y);
          cb.lineTo(toX,toY);
          cb.stroke();
          cb.restoreState();
      }
       
      private void slide(float x , float y, int orientation, int font_size, float line_space, PdfContentByte cb) {
  	    
     	  cb.setLineWidth(0.5f); // Make a bit thicker than 1.0 default
          cb.setGrayStroke(0f);// 1 = black, 0 = white
          cb.moveTo(x,y);
          cb.lineTo(x+orientation*((line_space/2.0f)-0.3f),y+(font_size/3.4f));
          cb.stroke();
          cb.setLineWidth(0.5f); // Make a bit thicker than 1.0 default
          cb.setGrayStroke(0f);// 1 = black, 0 = white
          cb.moveTo(x,y);
          cb.lineTo(x-orientation*((line_space/2.0f)-0.3f),y-(font_size/3.4f));
          cb.stroke();
      } 
    
      private void hammer(float x , float y, float tox, float toy, int font_size, float line_space, PdfContentByte cb) throws DocumentException, IOException {
    	     
      	 float tempy = y +(font_size*0.5f);
      	 BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
      	 cb.saveState();
      	 cb.setLineWidth(0.5f);
      	 cb.setGrayStroke(0);
      	 cb.moveTo(x+(font_size/1.8f),tempy);	 
      	 cb.curveTo(x+(font_size/1.8f)+2.5f, tempy+(line_space*0.26f),  tox-2.2f, tempy+(line_space*0.26f),  tox, tempy);
         cb.stroke();
         cb.restoreState();
         cb.saveState();
         cb.beginText();
         cb.setTextMatrix(x+(font_size/1.8f)+(tox-(x+(font_size/1.8f)))/2f-1f, tempy+(line_space*0.26f));
         cb.setFontAndSize(bf, font_size/2);
         cb.showText("h");
         cb.endText();
         cb.restoreState();
      	
       }
    
      private void pull(float x , float y,float tox,float toy, int font_size, float line_space, PdfContentByte cb) throws DocumentException, IOException {
     	 
     	  BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
     	  cb.saveState();
     	  cb.setLineWidth(0.5f);
      	  cb.setGrayStroke(0);
     	  cb.moveTo(x+(font_size/1.8f)/2f, y+(font_size*0.5f));
     	  cb.curveTo(x+(font_size/1.8f)/2f+1.3f, y+(line_space*0.4f)+(font_size*0.5f),  tox, toy+(line_space*0.4f)+(font_size*0.5f),  tox+(font_size/1.8f)/2f, toy+(font_size*0.5f));
          cb.stroke();
          cb.restoreState();
          cb.saveState();
          cb.beginText();
          cb.setTextMatrix(x+(tox-x)/2f+1.2f, toy+(font_size*0.5f)+(line_space*0.5f));
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
      
      private void DrawDiamond(float x, float y,float height,float diagonal ,PdfContentByte cb) {
    	 
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
       void DrawMarginMusicLines (List<MusicSymbols> list, float x, float y,float tox,int FontSize,  PdfContentByte cb) {
    	   
    	   
     	  int lines  = list.get(list.size()-2).getValue();
     	  while (lines > 0) {
     		  drawLine(x,y,tox,y,0.5f,0, cb);
     		  y-=FontSize*0.9f;
     		  lines--;	 
     	  }
     	 
      }
     
       float getMusicNotelength(List<MusicSymbols> list, float line_space) {
    	   int i = 0;
    	   float total = 0;
    	   while (list.get(i).getchartype() != Symbols.End_music_line) {
    		   switch(list.get(i).getchartype()) {
    		   
    		   case Dash: case Slide: case Pull: case Hammer: case Left_Half_Diamond: case Right_half_Diamond: case Star_Begin: case Star_End:	 
    			   total+=(line_space*list.get(i).getValue());
    			   break;
    		   case OneBar_begin: case OneBar_end: case OneBar_begin_lastline: case OneBar_end_lastline:
    			   total+=(line_space*0.5f);
    			   break;
    		   case Two_Bar_begin :case Two_Bar_begin_lastline: 
    			   total+=(line_space*1.0f);
    			   break;
    		   case Two_Bar_end: case Two_Bar_end_lastline:
    			   total+=(line_space*1.0f);
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
    			   total+=(line_space*0.03f);
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
       
      
        void DrawMusicNote (List<MusicSymbols> list , float x , float y , float line_space , int FontSize ,int same_line,PdfContentByte cb  ) throws DocumentException, IOException {
        	float tempx = x; 
        	int count_music_lines = 0; // give the current music line
        	int is_second_beginstar = 0; // tell whether the beginning of the star is first one or second one
        	int is_second_endnstar = 0; // tell whether the beginning of the star is first one or second one
        	
        	for (int i = 0 ; i < list.size() ; i++) {
        		switch (list.get(i).getchartype()) {
        		
        		case OneBar_begin:
        			drawLine(x,y,x,y-(FontSize*0.9f),0.5f,0, cb);
        			drawLine(x,y,x+(line_space*0.5f),y,0.5f,0, cb);
        			x+=(line_space*0.5f);
        			break;
        		case OneBar_end:
        			drawLine(x,y,x+(line_space*0.5f),y,0.5f,0, cb);
        			x+=(line_space*0.5f);
        			drawLine(x,y,x,y-(FontSize*0.9f),0.5f,0, cb);
        			break;
        		case OneBar_begin_lastline: case OneBar_end_lastline :
        			drawLine(x,y,x+(line_space*0.5f),y,0.5f,0, cb);
        			x+=(line_space*0.5f);
        			break;
        		case Two_Bar_begin :
        			switch (same_line) {
        			case 0 :
        				 drawLine(x-0.5f, y, x-0.5f, y-(FontSize*0.9f),2.8f, 0, cb); //shifted by 0.5f
         				 drawLine(x,y,x+(line_space*1.2f),y,0.5f,0, cb); // draw horizital for filling gap
         				 x+=(line_space*1.0f);
         				 break;
        			case 1:
        				switch (onebar_before) {
        				case 0 :
        					 drawLine(x,y,x+(line_space*1.0f),y,0.5f,0, cb); // draw horizital for filling gap
             				 x+=(line_space*1.0f);
             				 break;
        				case 1 :
        					 drawLine(x-0.5f, y, x-0.5f, y-(FontSize*0.9f),2.8f, 0, cb); //shifted by 0.5f
             				 drawLine(x,y,x+(line_space*1.2f),y,0.5f,0, cb); // draw horizital for filling gap
             				 x+=(line_space*1.0f);
             				 break;
        				default:break; 
        				}		
         				 break;
         			default:break; 		 			
        			}
        			break;
        			
        		case Two_Bar_end:
        			 drawLine(x,y,x+(line_space*1.0f),y,0.5f,0, cb);		
        			 drawLine(x+(line_space)-0.5f, y, x+(line_space)-0.5f, y-(FontSize*0.9f),2.8f, 0, cb); //shifted by 0.5f
    				 x+=(line_space*1.0f);  
        			 break;	
        		case Two_Bar_begin_lastline : 
        			drawLine(x,y,x+(line_space*1.0f),y,0.5f,0, cb);
    				x+=(line_space*1.0f);
    				break;
        		case Two_Bar_end_lastline:
        			drawLine(x,y,x+(line_space*1.0f),y,0.5f,0, cb);
    				x+=(line_space*1.0f);				
    				break;
        		case Three_Bar_End :
        			if(list.get(i+1).getValue() < list.get(list.size()-2).getValue()) {
        				drawLine(x+(line_space*0.5f),y,x+(line_space*0.5f),y-((FontSize*0.9f)),0.5f,0, cb);
        				drawLine(x+(line_space*1.5f),y,x+(line_space*1.5f),y-((FontSize*0.9f)),0.5f,0, cb);
        				drawLine(x+(line_space*2.5f),y,x+(line_space*2.5f),y-((FontSize*0.9f)),0.5f,0, cb);
        			}
        			drawLine(x,y,x+(line_space*2.5f),y,0.5f,0, cb);
    				x+=(line_space*2.5f);
    				break;
        		case Dash: case Left_Half_Diamond:
        			drawLine(x,y,x+(line_space*list.get(i).getValue()),y,0.5f,0, cb);
     				x+=(line_space*list.get(i).getValue());
     				break;
        		case One_digit:
        			InsertText(list.get(i).getValue()+"",x,y-(1.0f+(FontSize/4.0f)),FontSize,cb);
    				drawLine(x+(FontSize/1.8f),y,x+line_space,y,0.5f,0, cb);
    				symbolp_list.add(new SymbolPoint(list.get(i).getValue(),x,y,count_music_lines+1));
    				x+=line_space;
    				break;
        		case Two_digit:
        			drawLine(x-(line_space*0.2f), y, x, y, 0.5f,1, cb); // white line
    				InsertText(Integer.toString(list.get(i).getValue()).charAt(0)+"",x-(line_space*0.3f),y-(1.0f+(FontSize/4.0f)),FontSize,cb);
    				InsertText( Integer.toString(list.get(i).getValue()).charAt(1)+"",x-(line_space*0.3f)+(FontSize/2.5f),y-(1.0f+(FontSize/4.0f)),FontSize,cb);
    				drawLine(x-(line_space*0.3f)+((FontSize/2.5f+(FontSize/1.8f))),y,x+(line_space*2.0f),y,0.5f,0, cb);
        			x+=(line_space*2.0f);
        			break;
        		case Slide:
        			symbolp_list.add(new SymbolPoint('s',x,y,count_music_lines+1));
       				drawLine(x,y,x+line_space,y,0.5f,0, cb);
       				x+=line_space;
       				break;
        		case Hammer:
    				drawLine(x,y,x+line_space,y,0.5f,0, cb);
    				symbolp_list.add(new SymbolPoint('h',x,y,count_music_lines+1));
    				x+=line_space;
    				break;
        		case Space:
        			drawLine(x,y,x+(line_space*0.03f),y,0.5f,0, cb);
        			x+=(line_space*0.03f);
        			break;
        		case Pull:
        			symbolp_list.add(new SymbolPoint('p',x,y,count_music_lines+1));
    				drawLine(x,y,x+line_space,y,0.5f,0, cb);
    				x+=line_space;
    				break;
        		case Star_Begin:
        			if (is_second_beginstar == 0) {
        				drawLine(x-((line_space)/2.0f),y,x-((line_space)/2.0f),y+((FontSize*0.9f)*(count_music_lines)),0.5f,0, cb);
        				is_second_beginstar = 1;
        			} else {
        				drawLine(x-((line_space)/2.0f),y,x-((line_space)/2.0f),y+(FontSize*0.9f),0.5f,0, cb);
        				drawLine(x-((line_space)/2.0f),y,x-((line_space)/2.0f),y-((FontSize*0.9f)*(list.get(list.size()-2).getValue()-count_music_lines-1)),0.5f,0, cb);
        				is_second_beginstar = 0;
        				
        			}
        			DrawCircle(x+(line_space*0.1f),y,(FontSize/1.3f)-(FontSize*0.6f),cb);
      				drawLine(x,y,x+line_space,y,0.5f,0, cb);
      				x+=line_space;
      				break;
        		case Star_End:
        			DrawCircle(x+(line_space*0.75f),y,(FontSize/1.3f)-(FontSize*0.6f),cb);
      				drawLine(x,y,x+line_space,y,0.5f,0, cb);
      				x+=line_space;
      				if (is_second_endnstar == 0) {
        				drawLine(x+(line_space*.3f),y,x+(line_space*.3f),y+((FontSize*0.9f)*(count_music_lines)),0.5f,0, cb);
        				is_second_endnstar = 1;
        			} else {
        				drawLine(x+(line_space*.3f),y,x+(line_space*.3f),y+(FontSize*0.9f),0.5f,0, cb);
        				drawLine(x+(line_space*.3f),y,x+(line_space*.3f),y-((FontSize*0.9f)*(list.get(list.size()-2).getValue()-count_music_lines-1)),0.5f,0, cb);
        				is_second_endnstar = 0;		
        			}
      				break;
        		case Right_half_Diamond:
        			drawLine(x,y,x+line_space,y,0.5f,0, cb);
        			DrawDiamond(x+(line_space*0.2f), y, (FontSize/1.3f)-(FontSize*0.5f), FontSize*0.5f, cb);
    				x+=line_space;
    				break;
        		case End_music_line:	
        			x = tempx;
        			y = y - (FontSize*0.9f);
        			count_music_lines++;
    				break;
        		case End_music_note:
        			if(list.get(i-2).getchartype() == Symbols.OneBar_end_lastline)
        				onebar_before = 1;
        			else 
        				onebar_before = 0;
    				//symbolp_list.add(new SymbolPoint('%',0,0,-1));
    			default:break;    		
        		}
        	}
        	
        	    	
        }


                  
        public void drawSymbols(int FontSize,float  line_space,PdfContentByte cb) throws DocumentException, IOException {
        	
        	SymbolPoint temp = null;
        	SymbolPoint temp2 = null;
        	for (int i =0 ; i < symbolp_list.size(); i++) {
        		if (symbolp_list.get(i).getChar() == 'h') {	
        			temp = getNumberPrevious(symbolp_list.get(i),i);
        			temp2= getNumberNext(symbolp_list.get(i),i);
        			if (temp == null || temp2 == null) {

        				symbolp_list.remove(i);
        				continue;
        			}
        			hammer( temp.getX(),temp.getY(), temp2.getX(),temp2.getY(),FontSize,line_space,cb);		
        		} else if (symbolp_list.get(i).getChar() == 'p') {
        			temp = getNumberPrevious(symbolp_list.get(i),i);
        			temp2= getNumberNext(symbolp_list.get(i),i);
        			if (temp == null || temp2 == null) {
        				symbolp_list.remove(i);
        				continue;
        			}
        				
        			pull( temp.getX(),temp.getY(), temp2.getX(),temp2.getY(),FontSize,line_space,cb);	
        		}else if (symbolp_list.get(i).getChar() == 's') {
        			temp = getNumberPrevious(symbolp_list.get(i),i);
        			temp2= getNumberNext(symbolp_list.get(i),i);
        			if (temp == null || temp2 == null) {
        				symbolp_list.remove(i);
        				continue;
        			}
        					
        			if (temp.getChar() > temp2.getChar()) 
        				slide(symbolp_list.get(i).getX()+line_space/2f,symbolp_list.get(i).getY(),-1,(FontSize-2),(line_space-1), cb);			
        			else if (temp.getChar() < temp2.getChar())
        				slide(symbolp_list.get(i).getX()+line_space/2f,symbolp_list.get(i).getY(),1,(FontSize-2),(line_space-1), cb); 			
        		}
        	}
       	}
 
        
        
     public void printSymbolList() {
    		for(SymbolPoint ss : symbolp_list) 
    			ss.Print();        	
     }
     
     private SymbolPoint getNumberPrevious(SymbolPoint sp , int index) {
    		for (int i =index ; i >= 0; i--) {
    			if(sp.getLineNumber() ==symbolp_list.get(i).getLineNumber() && symbolp_list.get(i).getChar() != 'h' &&  symbolp_list.get(i).getChar() != 'p' &&symbolp_list.get(i).getChar() != 's' ) {
    				return symbolp_list.get(i);
    			}
    				
    			
    		}
    		return null;
     }
     private SymbolPoint getNumberNext(SymbolPoint sp , int index) {
    	
 		for (int i =index ; i < symbolp_list.size(); i++) {
 			if(sp.getLineNumber() ==symbolp_list.get(i).getLineNumber() && symbolp_list.get(i).getChar() != 'h'&&  symbolp_list.get(i).getChar() != 'p' &&symbolp_list.get(i).getChar() != 's' ) {
 				return symbolp_list.get(i);
 			}
 				
 			
 		}
 		return null;
  }
     
      void FlushSymbol() {
    	  symbolp_list.clear();
     }
      

}