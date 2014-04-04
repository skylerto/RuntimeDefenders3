package version13;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import version13.MusicNoteProcess;

public class MusicNoteProcessTest {

	MusicNoteProcess m1;
	MusicNoteProcess m2;
	MusicNoteProcess m3;
	
@Before
	public void setUp() throws Exception {
		List<String> in1 = new ArrayList<String>(); // for m1
		in1.add("||--h-h-2--3--2-s-|");
		in1.add("|*------2----------0----------2-------2-------0-------||");
		m1 = new MusicNoteProcess(in1);
		
	
		
		
	}

	@Test
	public void test_defaultconstructor_case1(){
	
		//Checking if it recognises all the symbols correctly with their quantity
		
		List<String> in2 = new ArrayList<String>(); // for m2
		in2.add("||*h-----2s-----2p-----<2>-----2-------||");
		m2 = new MusicNoteProcess(in2);
		
		String expected = "[Two_Bar_begin_lastline,1]+[Star_Begin,1]+[Hammer,1]+[Dash,5]+[One_digit,2]+[Slide,1]+[Dash,5]+[One_digit,2]+[Pull,1]+[Dash,5]+[Left_Half_Diamond,1]+[One_digit,2]+[Right_half_Diamond,1]+[Dash,5]+[One_digit,2]+[Dash,7]+[Two_Bar_end_lastline,1]+[End_music_line,1]+[End_music_note,0]+";
		assertEquals(expected, m2.toString());
	}
	
	@Test
	public void test_defaultconstructor_case2(){
	
		//Checking if it recognises all the symbols correctly with their quantity
		
		List<String> in2 = new ArrayList<String>(); // for m2
		in2.add("||*h-----2s-----2p-----<2>--- --10-------*|||");
		m2 = new MusicNoteProcess(in2);
		
		
		String expected = "[Two_Bar_begin_lastline,1]+[Star_Begin,1]+[Hammer,1]+[Dash,5]+[One_digit,2]+[Slide,1]+[Dash,5]+[One_digit,2]+[Pull,1]+[Dash,5]+[Left_Half_Diamond,1]+[One_digit,2]+[Right_half_Diamond,1]+[Dash,3]+[Space,1]+[Dash,2]+[Two_digit,10]+[Dash,7]+[Star_End,1]+[Three_Bar_End,1]+[End_music_line,1]+[End_music_note,0]+"
;
		assertEquals(expected, m2.toString());
	}

	
	@Test
	public void test_defaultconstructor_case3(){
	
		//Checking if it recognises all the symbols correctly with their quantity
		
		List<String> in2 = new ArrayList<String>(); // for m2
		in2.add("|--2----3----|");
		m2 = new MusicNoteProcess(in2);
		
		String expected = "[OneBar_begin_lastline,1]+[Dash,2]+[One_digit,2]+[Dash,4]+[One_digit,3]+[Dash,4]+[OneBar_end_lastline,1]+[End_music_line,1]+[End_music_note,0]+";
		assertEquals(expected, m2.toString());
	}
	
	@Test
	public void test_defaultconstructor_case4(){
	
		//Checking if it recognises all the symbols correctly with their quantity
		
		List<String> in2 = new ArrayList<String>(); // for m1
		in2.add("||--h-h-2--3--2-s-|");
		in2.add("||*------2----------0----------2-------2-------0-------|");
		m3 = new MusicNoteProcess(in2);
		
		
		String expected = "[Two_Bar_begin,1]+[Dash,2]+[Hammer,1]+[Dash,1]+[Hammer,1]+[Dash,1]+[One_digit,2]+[Dash,2]+[One_digit,3]+[Dash,2]+[One_digit,2]+[Dash,1]+[Slide,1]+[Dash,1]+[OneBar_end,1]+[End_music_line,1]+[Two_Bar_begin_lastline,1]+[Star_Begin,1]+[Dash,6]+[One_digit,2]+[Dash,10]+[One_digit,0]+[Dash,10]+[One_digit,2]+[Dash,7]+[One_digit,2]+[Dash,7]+[One_digit,0]+[Dash,7]+[OneBar_end_lastline,1]+[End_music_line,2]+[End_music_note,0]+"
;
		assertEquals(expected, m3.toString());
	}

	
	
	

}