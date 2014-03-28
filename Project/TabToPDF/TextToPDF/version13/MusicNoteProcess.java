package version13;

import java.util.*;import java.util.regex.Matcher;
import java.util.regex.Pattern;

import version13.MusicSymbols.Symbols;

public class MusicNoteProcess {
	
	
	private List<String> list_string;
	
	private Pattern OneBar = Pattern.compile("^(\\|)(?!\\|){1,2}(.*)");
	private Pattern two_bar = Pattern.compile("^(\\|\\|)(?!\\|)+(.*)");
	private Pattern three_bar = Pattern.compile("^(\\|\\|\\|)(.*)");
	private Pattern dash = Pattern.compile("^(-+)(.*)");
	private Pattern one_digit = Pattern.compile("^(\\d)(?!\\d)(.*)");
	private Pattern two_digit = Pattern.compile("^(\\d{2})(.*)");
	private Pattern space = Pattern.compile("^( )(.*)");
	private Pattern slide = Pattern.compile("^(s)(.*)");
	private Pattern hammer = Pattern.compile("^(h)(.*)");
	private Pattern pull = Pattern.compile("^(p)(.*)");
	private Pattern star = Pattern.compile("^(\\*)(.*)");
	private Pattern left_half_diamond = Pattern.compile("^(\\<)(.*)");
	private Pattern right_half_diamond = Pattern.compile("^(\\>)(.*)");

	private Matcher m_OneBar;
	private Matcher m_two_bar;
	private Matcher m_three_bar;
	private Matcher m_dash;
	private Matcher m_one_digit;
	private Matcher m_two_digit;
	private Matcher m_space;
	private Matcher m_slide;
	private Matcher m_hammer;
	private Matcher m_pull;
	private Matcher m_star;
	private Matcher m_left_half_diamond;
	private Matcher m_right_half_diamond;

	private List<MusicSymbols> list = new ArrayList<MusicSymbols>();
	
	public MusicNoteProcess ( List<String> list) {
		this.list_string = list;
		string_process();
		
	}
	
	private void initialize_matcher(String temp) {
		 m_OneBar= OneBar.matcher(temp);
		 m_two_bar= two_bar.matcher(temp);
		 m_three_bar = three_bar.matcher(temp);
		 m_dash = dash.matcher(temp);
		 m_one_digit = one_digit.matcher(temp);
		 m_two_digit = two_digit.matcher(temp);
		 m_space = space.matcher(temp);
		 m_slide = slide.matcher(temp);
		 m_hammer = hammer.matcher(temp);
		 m_pull = pull.matcher(temp);
		 m_star = star.matcher(temp);
		 m_left_half_diamond = left_half_diamond.matcher(temp);
		 m_right_half_diamond = right_half_diamond.matcher(temp);
	}
	
	public void string_process() {
		int current_line_number = 0;
			for (int i = 0 ; i < list_string.size() ; i++) {			
				 String temp= list_string.get(i);
				 initialize_matcher(temp);
				 int left_half_diamond_found = 0;
				 while (temp.length() > 0 ) {	
					 if (m_OneBar.find()) {
						 temp = m_OneBar.group(2); // group 2   get the rest of string and 1 
					 	 initialize_matcher(temp);
						 if( i == list_string.size()-1) {
							 if (temp.length() > 0)
								 list.add(new MusicSymbols(Symbols.OneBar_begin_lastline));
							 else
								 list.add(new MusicSymbols(Symbols.OneBar_end_lastline));

						 } else {
							 if (temp.length() > 0)
								 list.add(new MusicSymbols(Symbols.OneBar_begin));
							 else
								 list.add(new MusicSymbols(Symbols.OneBar_end));					 
						 }
						
					 }  else if (m_two_bar.find()) {
						 temp = m_two_bar.group(2); // group 2 get the rest of string and 1
						 initialize_matcher(temp);
						if( i == list_string.size()-1) {
							 if (temp.length() > 0)
								 list.add(new MusicSymbols(Symbols.Two_Bar_begin_lastline));
							 else
								 list.add(new MusicSymbols(Symbols.Two_Bar_end_lastline));

						 } else {
							 if (temp.length() > 0)
								 list.add(new MusicSymbols(Symbols.Two_Bar_begin));
							 else
								 list.add(new MusicSymbols(Symbols.Two_Bar_end));					 
						 }						
					 } else if (m_three_bar.find()) {
						 temp = m_three_bar.group(2); // group 2 get the rest of string and 1
						 list.add(new MusicSymbols(Symbols.Three_Bar_End));
						 initialize_matcher(temp);
					 } else if (m_dash.find()) {
						 temp = m_dash.group(2); // group 2 get the rest of string and 1
						 list.add(new MusicSymbols(Symbols.Dash,m_dash.group(1).length()));
						 initialize_matcher(temp);
					 }  else if (m_one_digit.find()) {
						 temp = m_one_digit.group(2); // group 2 get the rest of string and 1 
						 list.add(new MusicSymbols(Symbols.One_digit,Integer.parseInt(m_one_digit.group(1))));
						 initialize_matcher(temp);
					 } else if (m_two_digit.find()) {
						 temp = m_two_digit.group(2); // group 2 get the rest of string and 1
						 list.add(new MusicSymbols(Symbols.Two_digit,Integer.parseInt(m_two_digit.group(1))));
						 initialize_matcher(temp);	 
					 } else if (m_space.find()) {
						 temp = m_space.group(2); // group 2 get the rest of string and 1
						 list.add(new MusicSymbols(Symbols.Space));
						 initialize_matcher(temp);
					 } else if (m_slide.find()) {
						 temp = m_slide.group(2); // group 2 get the rest of string and 1 
						 list.add(new MusicSymbols(Symbols.Slide));
						 initialize_matcher(temp);
					 } else if (m_hammer.find()) {
						 temp = m_hammer.group(2); // group 2 get the rest of string and 1 
						 list.add(new MusicSymbols(Symbols.Hammer));
						 initialize_matcher(temp);
					 } else if (m_pull.find()) {
						 temp = m_pull.group(2); // group 2 get the rest of string and 1 
						 list.add(new MusicSymbols(Symbols.Pull));
						 initialize_matcher(temp);
					 } else if (m_star.find()) {
						 temp = m_star.group(2); // group 2 get the rest of string and 1 		 
						 initialize_matcher(temp);
						 if (temp.length() > 3) 
							 list.add(new MusicSymbols(Symbols.Star_Begin));
						 else
							 list.add(new MusicSymbols(Symbols.Star_End));	
					 } else if (m_left_half_diamond.find()) {
						 temp = m_left_half_diamond.group(2); // group 2 get the rest of string and 1 
						 list.add(new MusicSymbols(Symbols.Left_Half_Diamond));
						 left_half_diamond_found = 1;
						 initialize_matcher(temp);
					 }  else if (m_right_half_diamond.find()) {
						 temp = m_right_half_diamond.group(2); // group 2 get the rest of string and 1 
						 if (left_half_diamond_found == 1)
							 list.add(new MusicSymbols(Symbols.Right_half_Diamond));
						 initialize_matcher(temp);
						 left_half_diamond_found = 0;
					 } 
								 
				 }

				 list.add(new MusicSymbols(Symbols.End_music_line,++current_line_number));
				 			 
			}
			list.add(new MusicSymbols(Symbols.End_music_note,0));		
	}
	
	public List<MusicSymbols> getSymbolsList() {
		return new ArrayList<MusicSymbols>(list);
	}
public String toString(){
	String s ="";
		for (MusicSymbols ms: list ) {
			s+="["+ ms.getchartype()+"," + ms.getValue()+ "]"+ "+";
		}
		return s;
		
	}
	
		
}

