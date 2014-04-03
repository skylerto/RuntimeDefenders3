package version13;

import java.util.*;import java.util.regex.Matcher;
import java.util.regex.Pattern;

import version13.MusicSymbols.Symbols;
/**
 * this object  is detecting to detect all music symbol from strings
 * by using string regex. The approach is desgined into similar way
 * on how human might draw music note given the form asgiving the same  input.
 * Example:
 * suppose we have:
 * 			|----------3-----10-----s----||
 * the class will this string and detect symbol and update it.
 * So it sees that there is a bar in the beginning, using string regex:
 * 		   :#:
 * 		   :|:----------3-----10-----s----||
 *         :#:
 * and assign one bar and a value of 1 ,store it in MusicSymbol list. 
 * the string updated and  now is :
 *            
 * next we see a dashes, by using string regex:
 * 		   :###########:
 *         :-----------:3-----10-----s----||
 *         :###########:
 * we store dash enum and how many dashed in above , there is 11 dashes.
 * store it in MusicSymbol list.
 * The string has been updated:
 * 			3-----10-----s----||
 * we continue to apply the same thing for the rest of string and store them 
 * in MusicSymbol list. when we visit the list in draw class we know we going
 * to draw  one bar, 11 dashes , etc.This result in faster operation, rather drawing
 * char by char in previous version and easier handling cases.
 * @author saad
 *
 */
public class MusicNoteProcess {
	
	/* this is used to assign list of string from file.*/
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
	/* this is used to store music symbols in music symbol*/
	private List<MusicSymbols> list = new ArrayList<MusicSymbols>();
	
	/**
	 * initialize string processing by feeding a list of string.
	 * @param list
	 */
	public MusicNoteProcess ( List<String> list) {
		this.list_string = list;
		string_process();	
	}
	
	/**
	 * intialize matchers for this string.
	 * @param temp
	 */
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
	/**
	 * this method process the string 
	 */
	
	public void string_process() {
		int current_line_number = 0;
			for (int i = 0 ; i < list_string.size() ; i++) {			
				 String temp= list_string.get(i);
				 initialize_matcher(temp);
				 int left_half_diamond_found = 0;
				 while (temp.length() > 0 ) {	
					 if (m_OneBar.find()) {
						 temp = m_OneBar.group(2); // store the rest of the string
					 	 initialize_matcher(temp);
						 if( i == list_string.size()-1) { // the last string in music note
							 if (temp.length() > 0)
								 /* beginning  of the last string has one bar*/
								 list.add(new MusicSymbols(Symbols.OneBar_begin_lastline));
							 else
								 /* last character  of the last string has one bar*/
								 list.add(new MusicSymbols(Symbols.OneBar_end_lastline));

						 } else {
							 /* beginning  of the  string, other than last string, has one bar*/
							 if (temp.length() > 0)
								 list.add(new MusicSymbols(Symbols.OneBar_begin));
							 else
								 /* end  of the  string, other than last string, has one bar*/
								 list.add(new MusicSymbols(Symbols.OneBar_end));					 
						 }
						
					 }  else if (m_two_bar.find()) {
						 temp = m_two_bar.group(2); // store the rest of the string
						 initialize_matcher(temp);
						if( i == list_string.size()-1) {
							 if (temp.length() > 0)
								 /* beginning  of last string that  has two bar*/
								 list.add(new MusicSymbols(Symbols.Two_Bar_begin_lastline));
							 else
								 /* the ending of  last string, has two bar*/
								 list.add(new MusicSymbols(Symbols.Two_Bar_end_lastline));

						 } else {
							 if (temp.length() > 0)
								 /* beginning  of the  string, other than last string, has two bar*/
								 list.add(new MusicSymbols(Symbols.Two_Bar_begin));
							 else
								 /* end  of the  string, other than last string, has two bar*/
								 list.add(new MusicSymbols(Symbols.Two_Bar_end));					 
						 }						
					 } else if (m_three_bar.find()) {
						 temp = m_three_bar.group(2); // store the rest of the string
						 list.add(new MusicSymbols(Symbols.Three_Bar_End));
						 initialize_matcher(temp);
					 } else if (m_dash.find()) {
						 temp = m_dash.group(2); // store the rest of the string
						 list.add(new MusicSymbols(Symbols.Dash,m_dash.group(1).length()));
						 initialize_matcher(temp);
					 }  else if (m_one_digit.find()) {
						 temp = m_one_digit.group(2); // store the rest of the string
						 /* convert the string to digit and store it along with its one digit enum*/
						 list.add(new MusicSymbols(Symbols.One_digit,Integer.parseInt(m_one_digit.group(1))));
						 initialize_matcher(temp);
					 } else if (m_two_digit.find()) {
						 /* convert the string to two digit and store it along with its two digit enum*/
						 temp = m_two_digit.group(2); // group 2 get the rest of string and 1
						 list.add(new MusicSymbols(Symbols.Two_digit,Integer.parseInt(m_two_digit.group(1))));
						 initialize_matcher(temp);	 
					 } else if (m_space.find()) {
						 temp = m_space.group(2);// store the rest of the string
						 list.add(new MusicSymbols(Symbols.Space));
						 initialize_matcher(temp);
					 } else if (m_slide.find()) {
						 temp = m_slide.group(2); // store the rest of the string
						 list.add(new MusicSymbols(Symbols.Slide));
						 initialize_matcher(temp);
					 } else if (m_hammer.find()) {
						 temp = m_hammer.group(2); // store the rest of the string
						 list.add(new MusicSymbols(Symbols.Hammer));
						 initialize_matcher(temp);
					 } else if (m_pull.find()) {
						 temp = m_pull.group(2); // store the rest of the string
						 list.add(new MusicSymbols(Symbols.Pull));
						 initialize_matcher(temp);
					 } else if (m_star.find()) {
						 temp = m_star.group(2); // store the rest of the string 		 
						 initialize_matcher(temp);
						 if (temp.length() > 3)
							 /*	 the star at the beginning of the string ( music note)*/
							 list.add(new MusicSymbols(Symbols.Star_Begin));
						 else
							 /*	the star at the end of the string ( music note)*/
							 list.add(new MusicSymbols(Symbols.Star_End));	
					 } else if (m_left_half_diamond.find()) {
						 temp = m_left_half_diamond.group(2); // store the rest of the string 
						 list.add(new MusicSymbols(Symbols.Left_Half_Diamond));
						 left_half_diamond_found = 1;
						 initialize_matcher(temp);
					 }  else if (m_right_half_diamond.find()) {
						 temp = m_right_half_diamond.group(2); // store the rest of the string
						 if (left_half_diamond_found == 1) //  store the right diamond only if there was left diamond 
							 list.add(new MusicSymbols(Symbols.Right_half_Diamond));
						 initialize_matcher(temp);
						 left_half_diamond_found = 0;
					 } 
								 
				 }
				 /* add symbol to the end music line with its current line number*/
				 list.add(new MusicSymbols(Symbols.End_music_line,++current_line_number));
				 			 
			}
			 /*add symbol to the end music notes with total number of lines for this music note*/
			list.add(new MusicSymbols(Symbols.End_music_note,0));		
	}
	/**
	 * return list of music symbol after music note has been processed.
	 * @return
	 */
	public List<MusicSymbols> getSymbolsList() {
		return new ArrayList<MusicSymbols>(list);
	}
	/* return string that has all of the music symbol 
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    public String toString(){	
	 String s ="";
		for (MusicSymbols ms: list ) 
			s+="["+ ms.getchartype()+"," + ms.getValue()+ "]"+ "+";
		return s;
		
	}
	
		
}

