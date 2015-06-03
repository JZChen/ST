package org.apache.pdfbox.bdd;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.pdfbox.PDFSplit;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.Test;


/**
 * Test suite for splitting PDF file. 
 */
public class BDDTestPDFSplit 
{
    
    /*
     * This test case will use a pdf file with 4 pages
     * and split it into 2 different pdf file evenly
     * each of them would have 2 pages 
     * 
     * Scenario
     * Given a pdf file with 4 pages 
     * When it gets split into two sub files
     * Then each of them would have 2 pages
     * 
     */
	
	private String givenPDFFile = "src/test/resources/org/apache/pdfbox/bdd/Week9.pdf";
	private String expectedPDFFile1 = "src/test/resources/org/apache/pdfbox/bdd/Week9-0.pdf";
	private String expectedPDFFile2 = "src/test/resources/org/apache/pdfbox/bdd/Week9-1.pdf";
	
	private PDDocument pdf1 = null , pdf2 = null;
	
	@Test
    public void split4PagePDFEvenlyWouldProduce2PDFFilesWith2Pages() throws Exception 
    {
    	ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
        PrintStream stdout = System.out;
        System.setOut(new PrintStream(outBytes));
        try 
        {
        	// When splits the 4 pages pdf file evenly
        	PDFSplit.main(new String[] {"-split","2", this.givenPDFFile });
        	// Then it would have 2 different pdf file, each has 2 pages
        	pdf1 = PDDocument.load(expectedPDFFile1);
        	pdf2 = PDDocument.load(expectedPDFFile2);
        	// Check its existence 
        	assertNotNull(pdf1);
        	assertNotNull(pdf2);
        	
        	// Check if each has 2 pages
        	assertEquals( pdf1.getNumberOfPages(), 2);
        	assertEquals( pdf2.getNumberOfPages(), 2);
        	
        } 
        finally 
        {
            // Restore stdout
            System.setOut(stdout);
            if( pdf1 != null )
            {
                pdf1.close();
            }
            
            if( pdf2 != null )
            {
                pdf2.close();
            }
        }
            	
    }
    
}