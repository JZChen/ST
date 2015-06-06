package org.apache.pdfbox.bdd;



import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import junit.framework.TestCase;

import org.apache.pdfbox.Decrypt;
import org.apache.pdfbox.Encrypt;
import org.apache.pdfbox.ExtractText;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test suite for encrypting the pdf and decrypt back
 */
public class BDDTestPDFEncryptDecrypt extends TestCase
{
    
    /*
     * This test case will encrypt a pdf file 
     * and try to read the file which should fail
     *  
     * Scenario
     * Given a pdf file  
     * When the pdf encryption program encrypts the file 
     * Then file would be un-modifiable
     * 
     */
	
	private String givenPDFFile = "src/test/resources/org/apache/pdfbox/bdd/Week9.pdf";
	private String encryptedPDFFile = "src/test/resources/org/apache/pdfbox/bdd/Week9En.pdf";
	private String decryptedPDFFile = "src/test/resources/org/apache/pdfbox/bdd/Week9De.pdf";
	
	private PDDocument pdf1 = null , pdf2 = null;
	
	@Rule public ExpectedException ioexception = ExpectedException.none();
	
	
    public void testEncryptPDFFileAndUnModifiable() throws Exception 
    {
    	ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
        PrintStream stdout = System.out;
        System.setOut(new PrintStream(outBytes));
        try 
        {
        	// When the program encrypts the pdf file
        	Encrypt.main(new String[] {"-O","password","-canExtractContent","false", givenPDFFile,encryptedPDFFile});
        	// Then it would have a pdf file that you can't do modification to it
        	pdf1 = PDDocument.load(encryptedPDFFile);
        	// Check its existence 
        	assertNotNull(pdf1);
        	// Expect io exception here since file is not modifiable
        	ioexception.expect(IOException.class);
        	ExtractText.main(new String[] { encryptedPDFFile });
        	
        	
        }catch(Exception e){
        	e.printStackTrace();
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
	
	
	
    public void testDecryptPDFFileAndModifiable() throws Exception 
    {
    	ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
        PrintStream stdout = System.out;
        System.setOut(new PrintStream(outBytes));
        try 
        {
        	// When the program decrypts the pdf file
        	Decrypt.main(new String[] {"-password","password", encryptedPDFFile,decryptedPDFFile});
        	// Then it would have a pdf file that you can do modification to it
        	pdf2 = PDDocument.load(decryptedPDFFile);
        	// Check its existence 
        	assertNotNull(pdf2);
        	ExtractText.main(new String[] { decryptedPDFFile });
        	
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