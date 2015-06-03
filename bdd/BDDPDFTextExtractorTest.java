package org.apache.pdfbox.bdd;



import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;
import org.apache.pdfbox.ExtractText;
import org.junit.Test;

/**
 * Test suite for ExtractText. 
 */
public class BDDPDFTextExtractorTest 
{
    
    /*
     * The ExtractText class is used tod extract all the text 
     * in the pdf file and convert it and output them into a text file
     * 
     * Scenario.
     *  
     * Given a pdf file with text with 4 pages, and a string of answer given by oracle
     * 
     * When extract these words into a text file
     *  
     * Then read the text file and compare the result string with the answer
     */
	
	// Given the pdf file with text
	private String givenPDFWithText = "src/test/resources/org/apache/pdfbox/bdd/Week9.pdf";
	private String expectedText = "src/test/resources/org/apache/pdfbox/bdd/Week9.txt";
	private String givenOracleText = "src/test/resources/org/apache/pdfbox/bdd/Week9_oracle.txt";
	
	@Test
    public void loadPDFFileWithTextAndExtractText() throws Exception 
    {
		
    	ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
        PrintStream stdout = System.out;
        System.setOut(new PrintStream(outBytes));
        try 
        {
        	// When extract the pdf text from the file
        	ExtractText.main(new String[] {givenPDFWithText});
        	// Then the file is readable
        	String output = new Scanner(new File("src/test/resources/org/apache/pdfbox/bdd/Week9.txt")).useDelimiter("\\A").next();
        	assertNotNull(output);
        	// Then compare with the oracle result
        	String oracle = new Scanner(new File("src/test/resources/org/apache/pdfbox/bdd/Week9_oracle.txt")).useDelimiter("\\A").next();
    		// Compare two string , should have the same value
    		assertEquals(output,oracle);
        	
        } 
        finally 
        {
            // Restore stdout
            System.setOut(stdout);
        }
   
        
    }

}