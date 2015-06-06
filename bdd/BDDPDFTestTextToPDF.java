package org.apache.pdfbox.bdd;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

import junit.framework.TestCase;

import org.apache.pdfbox.ExtractText;
import org.apache.pdfbox.TextToPDF;
import org.junit.Test;

/**
 * Test suite for ExtractText. 
 */
public class BDDPDFTestTextToPDF extends TestCase 
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
	private String expectPDFWithText = "src/test/resources/org/apache/pdfbox/bdd/Week9Text.pdf";
	private String givenText = "src/test/resources/org/apache/pdfbox/bdd/Week9.txt";
	private String expectText = "src/test/resources/org/apache/pdfbox/bdd/Week9Text.txt";
	private String givenOracleText = "src/test/resources/org/apache/pdfbox/bdd/Week9_oracle.txt";
	
	
    public void testMakePDFFileFromTextAndExtractText() throws Exception 
    {
    	ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
        PrintStream stdout = System.out;
        System.setOut(new PrintStream(outBytes));
        try 
        {
        	// When extract the pdf text from the file
        	TextToPDF.main(new String[] {expectPDFWithText,givenText});
        	ExtractText.main(new String[] { expectPDFWithText });
        	
        	
        	// Then the file is readable
        	String output = new Scanner(new File(expectText)).useDelimiter("\\A").next().replace(" ","");
        	assertNotNull(output);
        	// Then compare with the oracle result
        	String oracle = new Scanner(new File(givenOracleText)).useDelimiter("\\A").next().replace(" ","");
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