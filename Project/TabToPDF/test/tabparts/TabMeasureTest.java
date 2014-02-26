//Khurram and Bilal

package tabparts;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.Before;
import org.junit.Test;
import tabparts.TabMeasure;
import tabparts.TabString;

public class TabMeasureTest {

    TabString temp;    // needed
    TabString temp2;  // needed
    TabString temp7;
    TabString tab;   // needed
    TabString tab1; // needed
    TabMeasure tabm;
    TabMeasure tabm1;
    TabMeasure tabm2;
    TabMeasure tabm3;
    TabMeasure tabm4;
    TabMeasure tabm5;
    TabMeasure tabm6;
    TabMeasure tabm7;
    TabMeasure tabm8;
    TabMeasure tabm9;
    TabMeasure tabm10;
    TabMeasure tabm11;
    TabMeasure tabm12;
    TabMeasure tabm13;
    TabMeasure tabm14;
    TabMeasure tabm15;
    TabString temp9;
    TabString temp10;
    TabString temp11;
    

    @Before
    public void setUp() throws Exception {

        tab = new TabString();
        tabm = new TabMeasure();
        tabm1 = new TabMeasure(5);
        tabm2 = new TabMeasure(tabm);
        tabm3 = new TabMeasure(3);
        tabm3.setString(tab, 4);
        tab1 = tabm3.getString(3);
        tabm4 = new TabMeasure();
        tabm4.copyMeasure(tabm3);
        tabm5 = new TabMeasure();
        tabm5.setLength(9);
        tabm6 = new TabMeasure();
        tabm6.size = 4;
        tabm7 = new TabMeasure();
        tabm7.size = 0;
        tabm8 = new TabMeasure();
        tabm9 = new TabMeasure();
        tabm10 = new TabMeasure();
        // Inserting into a tabmeasure for testing purposes
        
        char a;
        a = 'c';

        TabString temp3;
        temp3 = new TabString();

        for (int i = 0; i < 6; i++) {
            temp3.addChar(a);
            tabm9.setString(temp3, i);

        }

        // Inserting again for testing purposes

        char b;
        b = 'a';

        TabString temp4;
        temp4 = new TabString();
        temp4.addChar(b);

        for (int i = 0; i < 5; i++) {

            tabm10.setString(temp4, i);

        }

        char c = 'c';
        char d = 'd';

        TabString temp5;
        temp5 = new TabString();
        temp5.addChar(c);
        temp5.addChar(d);

        tabm11 = new TabMeasure(tabm10);
        tabm11.setString(temp5, 5);
        
        tabm12 = new TabMeasure(tabm10);
        TabString temp6;
        char bar = '|';
        temp6 = new TabString();
        temp6.addChar(bar);
        temp6.addChar(bar);
        temp6.addChar(c);
        temp6.addChar(bar);
        tabm12.setString(temp6, 5);
        tabm12.fixStartBar();
        tabm13 = new TabMeasure(tabm10);
        
       
        temp7 = new TabString();
        temp7.addChar(bar);
        temp7.addChar(c);
        temp7.addChar('d');
        temp7.addChar(bar);
        temp7.addChar(bar);
        tabm13.setString(temp7, 5);
        tabm13.fixEndBar();


        char s = '2';
        temp9 = new TabString();
        tabm14 = new TabMeasure();
        temp9.addChar('1');
        temp9.addChar('-');
        temp9.addChar('2');
        for (int i = 0; i < 6; i++) {
           // temp9.addChar(s);
            tabm14.setString(temp9, i);

        }
        
        temp10 = new TabString();
        temp10.addChar('|');
        temp10.addChar('1');
        temp10.addChar('|');
        
        tabm15 = new TabMeasure();
        temp11 = new TabString();
        temp11.addChar('1');
        temp11.addChar('1');
        temp11.addChar('1');
        
        for (int i = 0; i < 6; i++) {
          
             tabm15.setString(temp11, i);

         }
        
        
        
}
    

    @Test
    public void TestTabMeasureDefaultConstructor() {
        assertEquals(6, tabm.size()); // checks the size of TABMEASURE
        assertEquals(0, tabm.length()); // checks the length of TABMEASURE
        for (int i = 0; i < 6; i++) {
            temp = new TabString(tabm.getString(i)); // creates a temp Tabstring
                                                        // which is extracted
                                                        // from TABMEASURE at i
            assertEquals(75, temp.MAX_SIZE); // checks the MAXSIZE of TabString
                                                // temp
            assertEquals(0, temp.size()); // checks the size of TabString temp
            for (int j = 0; j < 75; j++) {
                assertEquals('\0', temp.getChar(j)); // checks the contents of
                                                        // TabString temp

            }

        }
    }

    @Test
    public void TestTabMeasureLengthConstructor() {

        assertEquals(5, tabm1.length()); // checks the length of TABMEASURE

    }

    @Test
    public void TestTabMeasureCopyConstructor() { // same thing as first test,
                                                    // but instead
        // of comparing with actual values, we
        // are comparing with
        // another tabmeasure object values.

        assertEquals(tabm.size(), tabm2.size());
        assertEquals(tabm.length(), tabm2.length());

        for (int i = 0; i < 6; i++) {
            temp = new TabString(tabm.getString(i));
            temp2 = new TabString(tabm2.getString(i));

            assertEquals(temp.MAX_SIZE, temp2.MAX_SIZE);
            assertEquals(temp.size(), temp2.size());
            for (int j = 0; j < 75; j++) {
                assertEquals(temp.getChar(j), temp2.getChar(j));

            }

        }

    }

    @Test
    public void TestTabMeasureSetString() {

        temp = new TabString(tabm3.getString(4));
        assertEquals(tab.MAX_SIZE, temp.MAX_SIZE);
        assertEquals(tab.size(), temp.size());
        for (int j = 0; j < 75; j++) {
            assertEquals(tab.getChar(j), temp.getChar(j));

        }

    }

    @Test(expected = IllegalArgumentException.class)
    public void testTabMeasureSetStringException() {
        tabm3.setString(tab, 9);
    }

    @Test
    public void TestTabMeasureGetString() {

        assertEquals(tab.MAX_SIZE, tab1.MAX_SIZE);
        for (int i = 0; i < tab1.MAX_SIZE; i++) {
            assertEquals(tab.getChar(i), tab1.getChar(i));
        }
        assertEquals(tab.size(), tab1.size());

    }
    
    @Test
    public void TestGetStringText() {
    	
    	/*tabm9 contents
    	 * 			 cc
    	 * 			 ccc
    	 * 			 cccc	
    	 * Calling on the method on index 0 should return a single c
    	 */
    	String expect_3 = "c";
    	
    	assertEquals(expect_3,tabm9.getStringText(0));
    	
    }

    @Test
    public void TestTabMeasureCopyMeasure() {
        assertEquals(tabm3.size(), tabm4.size());
        assertEquals(tabm3.length(), tabm4.length());

        for (int i = 0; i < 6; i++) {
            temp = new TabString(tabm3.getString(i));
            temp2 = new TabString(tabm4.getString(i));

            assertEquals(temp.MAX_SIZE, temp2.MAX_SIZE);
            assertEquals(temp.size(), temp2.size());
            for (int j = 0; j < 75; j++) {
                assertEquals(temp.getChar(j), temp2.getChar(j));

            }

        }

    }
    
    @Test
    public void TestFixStartBar(){
    	/*
    	 * tabm12 contains
    	 * |a
    	 * |a
    	 * |a
    	 * |a
    	 * |a
    	 * ||c|
    	 * after calling the method it should return
    	 * ||a
    	 * ||a
    	 * ||a
    	 * ||a
    	 * ||a
    	 * ||c|
    	 */
    	String expect_4 = "||a\n||a\n||a\n||a\n||a\n||c|";
    	tabm12.fixStartBar();
    	assertEquals(expect_4,tabm12.toString());
    }
    
    @Test
    public void TestFixEndBar(){
    	/*
    	 * tabm13 contains
    	 * a|
    	 * a|
    	 * a|
    	 * a|
    	 * a|
    	 * |cd||
    	 * after calling the method it should return
    	 * a||
    	 * a||
    	 * a||
    	 * a||
    	 * a||
    	 * |cd||
    	 */
    	String expect_5 = "a||\na||\na||\na||\na||\n|cd||";
    	tabm13.fixEndBar();
    	assertEquals(expect_5,tabm13.toString());
    	
    }

    @Test
    public void TestTabMeasureSize() {
        assertEquals(6, tabm.size());
    }

    @Test
    public void TestTabMeasurelength() {
        assertEquals(3, tabm3.length());
    }

    @Test
    public void TestTabMeasureSetlength() {
        assertEquals(9, tabm5.length());
    }

    @Test
    public void TestTabMeasureIsEmptyTrue() {
        assertTrue(tabm7.isEmpty());
    }

    @Test
    public void TestTabMeasureIsEmptyFalse() {
    	assertFalse(tabm10.isEmpty());
       
    }
    
    @Test
    public void TestTabMeasureToStringNonEmpty(){ // making a non empty tab String and inserting
                                                    //it into tab measure at all indexes
        
        char a;
        a = 'b';

        TabString temp3;
        temp3 = new TabString();
        temp3.addChar(a);

        for (int i = 0; i < 6; i++) {

            tabm8.setString(temp3, i);

        }

        // Now Testing it
        String notEmpty = "b\nb\nb\nb\nb\nb";

        assertEquals(notEmpty, tabm8.toString());
        
    }
    
    @Test
    public void TestTabMeasureToStringIsEmpty() { // Testing for empty tab
                                                    // measure

        String empty = "[empty measure]";
        assertEquals(empty, tabm.toString());

    }
    
    @Test
    public void testEqualizeStrings() throws Exception { 
        //setting up tab14
        tabm14.fixMeasure();
        tabm14.setString(temp10, 5);
       
        
        /*
         * Tab10 contains
         * |1-2|
         * |1-2|
         * |1-2|
         * |1-2|
         * |1-2|
         * |1|
         * 
         * after calling the method it should return
         * 
         * |1-2|
         * |1-2|
         * |1-2|
         * |1-2|
         * |1-2|
         * |1--|
         * 
         */
        
       String expect_5 = "|1-2|\n|1-2|\n|1-2|\n|1-2|\n|1-2|\n|1--|";
        tabm14.equalizeStrings();
        assertEquals(expect_5,tabm14.toString());
        
     
       
    }
    
    @Test
    public void testFixStrings() throws Exception { // check if the output has a bar at both ends
                                    // inputting a a a a a a and check if it returns
                                    // |a| |a| |a| |a| |a| |a|
        
        String expect_0 = "|a|\n|a|\n|a|\n|a|\n|a|\n|-|";
    
        tabm10.fixStrings();
        assertEquals(expect_0, tabm10.toString());
        tabm.fixStrings();
    }
    
    @Test
    public void TestIsBlank() throws Exception  {
    	//setting up tabm
    	tabm.fixMeasure();
    	/*
    	 * tabm contains 
    	 * |-|
    	 * |-|
    	 * |-|
    	 * |-|
    	 * |-|
    	 * |-|
    	 * 
    	 * when calling the method should return true for blank
    	 */
    	
    	assertTrue(tabm.isBlank());
    }
    
    @Test
    public void TestGetRepeat(){
    	//tabm is a default empty measure so it should return 0
    	int expect_7 = 0;
    	assertEquals(expect_7,tabm.getRepeat());
    	
    }
    
    @Test
    public void TestSetRepeat(){
    	//setting repeat to 4 and check if it sets it
    	int expect_8 = 4;
    	tabm.setRepeat(4);
    	assertEquals(expect_8,tabm.getRepeat());
    	
    }
    
    @Test
    public void testAddCommentandIsComment(){
    	/*
    	 * tabm is a empty measure, 
    	 * after adding hello to it, it should print hello
    	 */
    	String expect_8 = "hello";
    	tabm.addComment(expect_8);
    	assertEquals(expect_8, tabm.toString());
    	assertTrue(tabm.isComment());
    }
    
    @Test
    public void testGetComment(){
    	String expect_8 = "new";
    	tabm.addComment(expect_8);
    	/*
    	 * tabm is a comment and contains "new"
    	 * after calling the method it should return new
    	 */
    	assertEquals(expect_8, tabm.getComment());
    	
    	
    }
    
    
    @Test
    public void testFixMeasure() throws Exception { 
    	/*
    	 * tabm14 contains 
				1-2
				1-2
				1-2
				1-2
				1-2
				1-2
			after calling the method should return
			
				|1-2|
				|1-2|
				|1-2|
				|1-2|
				|1-2|
				|1-2|
    	 */
    	
                                 
        String expect_1 = "|1-2|\n|1-2|\n|1-2|\n|1-2|\n|1-2|\n|1-2|";
       tabm14.fixMeasure();
        assertEquals(expect_1, tabm14.toString());
        
    }
    
    @Test(expected = LargeNumberException.class)
    public void testFixMeasureException() throws LargeNumberException{
    	/*
    	 * tam15 contains
    	 * 111
    	 * 111
    	 * 111
    	 * 111
    	 * 111
    	 * 111
    	 * 
    	 * it should throw large number exception because there are 3 consecutive 1's
    	 */
    	tabm15.fixMeasure();
    	System.out.println(tabm15.toString());
    }
    
    @Test(expected = LargeNumberException.class)
    public void testFixStringsException() throws LargeNumberException{
    	/*
    	 * tam15 contains
    	 * 111
    	 * 111
    	 * 111
    	 * 111
    	 * 111
    	 * 111
    	 * 
    	 * it should throw large number exception because there are 3 consecutive 1's
    	 */
    	tabm15.fixStrings();
    	System.out.println(tabm15.toString());
    }
    

}