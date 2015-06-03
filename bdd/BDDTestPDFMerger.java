package org.apache.pdfbox.bdd;



import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.pdfbox.PDFMerger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.Test;

/**
 * Test suite for Merging two PDF file. 
 */
public class BDDTestPDFMerger 
{
    
    /*
     *  Scenario 
     *  
     *  Given two pdf file with both 2 pages
     *  
     *  When apply PDFMerger class
     *  
     *  Then the final PDF should have 4 pages
     * 
     */

	// Given files
	private String givenFirst2Pages = "src/test/resources/org/apache/pdfbox/bdd/Week9-0.pdf";
	private String givenLast2Pages = "src/test/resources/org/apache/pdfbox/bdd/Week9-1.pdf";
	private String expectedPDF = "src/test/resources/org/apache/pdfbox/bdd/Week9Merge.pdf";
	private String oraclePDF = "src/test/resources/org/apache/pdfbox/bdd/Week9.pdf";
	// Expceted files
	PDDocument merged = null, oracle = null;
	@Test
    public void mergeTwoPDFFileShouldHave4Pages() throws Exception 
    {
    	ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
        PrintStream stdout = System.out;
        System.setOut(new PrintStream(outBytes));
        try 
        {
        	// When merge two different pdf files, each has 2 pages
        	PDFMerger.main(new String[]{givenFirst2Pages,givenLast2Pages,expectedPDF});
        	// Then the expectedPDF should have 4 same pages as the given Oracle pdf file
        	merged = PDDocument.load(expectedPDF);
        	oracle = PDDocument.load(oraclePDF);
        	assertEquals( merged.getNumberOfPages(), oracle.getNumberOfPages());
            
        } 
        finally 
        {
            // Restore stdout
            System.setOut(stdout);
            if( merged != null )
            {
            	merged.close();
            }
            
            if( oracle != null )
            {
            	oracle.close();
            }
        }

    }
}