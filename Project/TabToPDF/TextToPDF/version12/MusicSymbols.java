package version12;

public class MusicSymbols {
	public enum Symbols {
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
	
	
	public MusicSymbols (Symbols ct , int num) {
		ch_type = ct;
		number = num;
	}
	public MusicSymbols (Symbols ct) {
		this(ct,1);
	}
	public int getValue() {
		return number;
	}
	
	public Symbols getchartype() {
		return ch_type;
	}

}
