package version13;

/**
 * the class is using enums class embedded inside
 *  in order to create music symbol
 * object such it it will contain the enum for desired music
 *  and the value of the that music symbol. this class is used in 
 *  MusicNoteprocess class to process a music notes in form of string. 
 * Example:
 * suppose we want to create  dash music symbol. we will pass
 * the enum (dash) and the number of dashes we seen as value(num).
 * @author saad
 *
 */
 class MusicSymbols {
     enum Symbols {	 
	   OneBar_begin,OneBar_end,
	   OneBar_begin_lastline,OneBar_end_lastline,
	   Two_Bar_begin,Two_Bar_end,
	   Two_Bar_begin_lastline,Two_Bar_end_lastline,
	   Three_Bar_End,Dash,
	   One_digit,Two_digit,
	   Slide,Pull,Hammer,
	   Left_Half_Diamond,Right_half_Diamond,
	   Star_Begin, Star_End,
	   End_music_line,End_music_note,Space
	}
	private Symbols ch_type;
	private int number;
	
	/**
	 * create object that has enum , and its value.
	 * @param ct
	 * @param num
	 */
	MusicSymbols (Symbols ct , int num) {
		ch_type = ct;
		number = num;
	}
	/**
	 * create object that has enum with its value 1.
	 * @param ct
	 */
	MusicSymbols (Symbols ct) {
		this(ct,1);
	}
	/**
	 * return the value of this enum.
	 * @return
	 */
	 int getValue() {
		return number;
	}
	/**
	 * return the enum of this MusicSymbol object.
	 * @return
	 */
	Symbols getchartype() {
		return ch_type;
	}

}
