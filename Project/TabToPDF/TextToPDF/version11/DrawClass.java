package version11;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;

 	
public class DrawClass {
	static float p_detect_pos = 0f;
	
	
	public DrawClass() 
	{
		
	}
	
	static float getPx1y1() {
		return p_detect_pos;
	}
	  private  void InsertText(String text, float x, float y , int Fontsize, PdfContentByte cb) throws DocumentException, IOException {
     	 
     	 BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
          cb.saveState();
          cb.beginText();
          cb.setTextMatrix(x, y);
          cb.setFontAndSize(bf, Fontsize);
          cb.showText(text);
          cb.endText();
          cb.restoreState();
             
             
             
              }
      private  void DrawLine(float x , float y , float toX, float toY,float thinkess,PdfContentByte cb ) {
     	
     	 cb.setLineWidth(thinkess); // Make a bit thicker than 1.0 default , 0.5f
          cb.setGrayStroke(0.0f);// 0 = black, 1 = white
          cb.moveTo(x,y);
          cb.lineTo(toX,toY);
          cb.stroke();
      
              
      }
      private  void DrawWhiteLine(float x , float y , float toX, float toY,float thinkess,PdfContentByte cb ) {
      	
     	 cb.setLineWidth(thinkess); // Make a bit thicker than 1.0 default , 0.5f
          cb.setGrayStroke(1.0f);// 1 = black, 0 = white
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
     	 cb.moveTo(x-line_space+(font_size/1.8f), tempy);
     	 cb.curveTo((x-line_space)+(font_size/1.8f)+2.5f, tempy+(line_space*0.26f),  (x+line_space)-2.2f, tempy+(line_space*0.26f),  x +line_space, tempy);
          cb.stroke();
          cb.restoreState();
          cb.saveState();
          cb.beginText();
          cb.setTextMatrix(x+(line_space*0.155f), tempy+(line_space*0.26f));
          cb.setFontAndSize(bf, font_size/2);
          cb.showText("h");
          cb.endText();
          cb.restoreState();
     	
      }
      private void Pause(float x , float y,float p_detect,int curr_pos, int font_size, float line_space, PdfContentByte cb) throws DocumentException, IOException {
     	 //System.out.printf("num_pos is %f\n",p_detect);
     	 //System.out.printf("curr_pos is %d\n",curr_pos);
     	 /*if (font_size/line_space <= 1.14 ) 
     		 xctrl = (x -line_space)-(font_size/1.8f); 
     	 else 
     		 xctrl= (x -line_space);*/
     		 
     	// int  diff = (curr_pos-num_pos)-1; 
     	 //float tempx = (x- diff* (line_space))+((font_size/1.8f)/2.0f);
     	 //this.DrawLine( (x -line_space)-(font_size/1.8f), y, (x -line_space)-(font_size/1.8f), y+6.0f, 0.5f, cb);
     	 //this.DrawLine(tempx, y, tempx, y+5f, 0.5f, cb);
     	 //this.DrawLine(x, y, x+3f, y+3f, 0.5f, cb);
     	 float tempy = y +(font_size*0.5f);
     	 BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
     	 cb.saveState();
     	 cb.moveTo(p_detect, tempy);
     	 cb.curveTo(p_detect+1.3f, tempy+(line_space*0.4f),  (x +line_space), tempy+(line_space*0.4f),  (x +(line_space*1.0f))+((font_size/1.8f)/2.0f), tempy);
          cb.stroke();
          cb.restoreState();
          cb.saveState();
          cb.beginText();
          cb.setTextMatrix(x-(line_space*0.3f), tempy+(line_space*0.5f));
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
       void DrawEndingLines (List<String> list, float x, float y,float tox,int FontSize,  PdfContentByte cb) {
     	 int size  = list.size();
     	 while (size > 0) {
     		 DrawLine(x,y,tox,y,0.5f,cb);
     		 y-=FontSize*0.9f;
     		 size--;
     		 
     	 }
     	 
      }
     
      
       List<List<String>>  StringAnchor(List<List<String>> list) {
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
       
        float getMusicNotelength(List<String> inner , float line_space , int FontSize)
       {
    	   float total= 0.0f;
      	
      	 
      		for (int i = 0 ; i < inner.get(0).length()-3 ; i ++) {
      			if (inner.get(0).charAt(i) == '-')
      				total+= line_space;
      			else if (inner.get(0).charAt(i) != '|'&& inner.get(0).charAt(i) != ' ')
      				total+= line_space;
      			else if (inner.get(0).charAt(i) == '|' &&  inner.get(0).charAt(i+1) == '|'  &&  ((inner.get(0).charAt(i+2) != '|' ) || inner.get(0).charAt(i+2) == '$' )) {
      				//System.out.printf("passed through 2 ||\n");
      				total+= line_space;
      			}
      			
      				
      			else if (inner.get(0).charAt(i) == '|' &&  inner.get(0).charAt(i+1) == '|'  && inner.get(0).charAt(i+2) == '|' &&   inner.get(0).charAt(i+3) == '$' ) {
      				//System.out.printf("passed through 3 ||\n");
      				total+= line_space*1.0f;
      			}
      				
      			
      			else;
      		 
      	 }
      	 return total;
       }
       
       void DrawMusicNote (List<String> inner , float x , float y , float line_space ,float p_detect, int FontSize ,int same_line,PdfContentByte cb  ) throws DocumentException, IOException
      {
    	   
     	 float tempx = x;
     	 for (int s= 0  ; s < inner.size(); s++) {
     		 for (int i = 0 ; i < inner.get(s).length()-3 ; i++) {
     			 if (i <=2 && inner.get(s).charAt(i) == '|' &&  inner.get(s).charAt(i+1) == '|' && same_line == 1 && s < inner.size() -1 )
     			 {
     				 DrawLine(x, y, x, y-(FontSize*0.9f),2.8f, cb);
     				 DrawLine(x+(line_space/2.0f),y,x+(line_space/2.0f),y-(FontSize*0.9f),0.5f,cb); // draw thin bar
     				 DrawLine(x,y,x+line_space,y,0.5f,cb); // draw horizital for filling gap
     				 i = i+1;
     				 x+=line_space;
     				 
     				 
     			 }
     			 else if( inner.get(s).charAt(i) == '|' && s < inner.size() -1  &&  inner.get(s).charAt(i+1) != '|') {
     				 
     				 DrawLine(x,y,x,y-(FontSize*0.9f),0.5f,cb);
     				 
     			 }
     		
     			 else if ( inner.get(s).charAt(i) == '|' &&  inner.get(s).charAt(i+1) == '|' && s < inner.size() -1 && i < 2) {
     				 DrawLine(x-0.5f, y, x-0.5f, y-(FontSize*0.9f),2.8f, cb); //shifted by 0.5f
     				 DrawLine(x+(line_space/2.0f),y,x+(line_space/2.0f),y-(FontSize*0.9f),0.5f,cb); // draw thin bar
     				 DrawLine(x,y,x+line_space,y,0.5f,cb); // draw horizital for filling gap
     				 i = i+1;
     				 x+=line_space;
     			 }
     			 else if ( inner.get(s).charAt(i) == '|' &&  inner.get(s).charAt(i+1) == '|' && s < inner.size() -1 && inner.get(s).charAt(i+2) != '|'  && i > 3) {
     				 DrawLine(x+line_space, y, x+line_space, y-(FontSize*0.9f),2.8f, cb);
     				DrawLine((x+line_space)-(line_space/2.0f),y,(x+line_space)-(line_space/2.0f),y-(FontSize*0.9f),0.5f,cb); // draw thin bar before thick bar
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
     				 DrawLine(x,y,x,y-(FontSize*0.9f),0.5f,cb);
     				 DrawLine(x,y,x+line_space,y,0.5f,cb);
     				 DrawLine(x+line_space,y,x+line_space,y-(FontSize*0.9f),0.5f,cb);
     				 DrawLine(x+line_space,y,x+(line_space*2.0f),y,0.5f,cb);
     				 DrawLine(x+(line_space*2.0f),y,x+(line_space*2.0f),y-(FontSize*0.9f),0.5f,cb);
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
     				DrawWhiteLine(x-(line_space*0.2f), y, x, y, 0.5f, cb);
     				 InsertText( inner.get(s).charAt(i)+"",x-(line_space*0.3f),y-(1.0f+(FontSize/4.0f)),FontSize,cb);
     				 InsertText( inner.get(s).charAt(i+1)+"",x-(line_space*0.3f)+(FontSize/2.5f),y-(1.0f+(FontSize/4.0f)),FontSize,cb);
     				 DrawLine(x-(line_space*0.3f)+((FontSize/2.5f+(FontSize/1.8f))),y,x+(line_space*2.0f),y,0.5f,cb);
     				 x+=(line_space*2.0f);
     				 //num_pos = i+1;
     				 i = i+1;
     			 }
     			 else if ( inner.get(s).charAt(i) >= '0' &&  inner.get(s).charAt(i) <='9' &&  inner.get(s).charAt(i+1)== '|'){
     				 InsertText( inner.get(s).charAt(i)+"",x,y-(1.0f+(FontSize/4.0f)),FontSize,cb);
     				 DrawLine(x+(FontSize/2.3f),y,x+(line_space),y,0.5f,cb);
     				 x+=line_space;
     				 //num_pos = i;
     			 }
     			 else if ( inner.get(s).charAt(i) >= '0' &&  inner.get(s).charAt(i) <='9' &&  inner.get(s).charAt(i+1)== ' '){
     				 InsertText( inner.get(s).charAt(i)+"",x,y-(1.0f+(FontSize/4.0f)),FontSize,cb);
     				 DrawLine(x+(FontSize/1.8f),y,x+(line_space),y,0.5f,cb);
     				 x+=line_space;
     				 //num_pos = i;
     				 i = i+1;
     			 }
     			 else if ( inner.get(s).charAt(i) == 'h') {
     				 OpenNote(x,y, FontSize,line_space,cb);
     				 DrawLine(x,y,x+line_space,y,0.5f,cb);
     				 x+=line_space;
     			 }
     			 else if ( inner.get(s).charAt(i) == 'p') {
     				 Pause(x,y,p_detect,i,FontSize,line_space,cb);
     				 DrawLine(x,y,x+line_space,y,0.5f,cb);
     				 x+=line_space;
     			 }
     			 
     				 
     			 else if ( inner.get(s).charAt(i) != ' ' && inner.get(s).charAt(i) != '|' ) {
     				 InsertText( inner.get(s).charAt(i)+"",x,y-(1.0f+(FontSize/4.0f)),FontSize,cb);// write character taking account how many points it takes
     				 DrawLine(x+(FontSize/1.8f),y,x+line_space,y,0.5f,cb);
     				p_detect_pos= x+(FontSize/1.8f);
     				 x+=line_space;
     				 //num_pos = i;
     			 }
     				 
     				 
     				 
     		 }
     		x = tempx;
     		y = y - (FontSize*0.9f); //7.5 for 8 , 10 for 12 
     		 
     	 }
     	
     	 
     	 
      }

}
 
