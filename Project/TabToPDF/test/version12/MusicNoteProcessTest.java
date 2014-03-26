package version12;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class MusicNoteProcessTest {

	MusicNoteProcess m1;
	
@Before
	public void setUp() throws Exception {
		List<String> in = new ArrayList<String>();
		in.add("|--h-h-2--3--2-s-|");
		in.add("||*------2----------0----------2-------2-------0-------||");
		m1 = new MusicNoteProcess(in);
		
        

		
		//System.out.println(m1.toString());
	}

	@Test
	public void test_toString() {
		String expt = "[OneBar_begin,1]+[Dash,2]+[Hammer,1]+[Dash,1]+[Hammer,1]+[Dash,1]+[One_digit,2]+[Dash,2]+[One_digit,3]+[Dash,2]+[One_digit,2]+[Dash,1]+[Slide,1]+[Dash,1]+[OneBar_end,1]+[End_music_line,1]+[Two_Bar_begin_lastline,1]+[Star_Begin,1]+[Dash,6]+[One_digit,2]+[Dash,10]+[One_digit,0]+[Dash,10]+[One_digit,2]+[Dash,7]+[One_digit,2]+[Dash,7]+[One_digit,0]+[Dash,7]+[Two_Bar_end_lastline,1]+[End_music_line,2]+[End_music_note,0]+";
		assertEquals(expt,m1.toString());
	}

}
