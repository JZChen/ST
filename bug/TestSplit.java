/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.pdfbox;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.pdfbox.pdmodel.PDDocument;

import junit.framework.TestCase;

/**
 * Test suite for ExtractText. 
 */
public class TestSplit extends TestCase
{
    
    /**
     * Run the text extraction test splitting a pdf 
     * 
     * @throws Exception if something went wrong
     */
    public void testSplitPDF() throws Exception 
    {
    	ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
        PrintStream stdout = System.out;
        System.setOut(new PrintStream(outBytes));
        try 
        {
        	PDFSplit.main(new String[] {"src/test/resources/org/apache/pdfbox/original.pdf", 
                    "-console", "-encoding UTF-8","-split 1"});
        } 
        finally 
        {
            // Restore stdout
            System.setOut(stdout);
        }
        
        PDDocument doc = null,doc2 = null, docCopy = null;
        try
        {
           doc = PDDocument.load( "src/test/resources/org/apache/pdfbox/original.pdf" );
           docCopy = PDDocument.load( "src/test/resources/org/apache/pdfbox/original.pdf" );
           doc2 = PDDocument.load( "src/test/resources/org/apache/pdfbox/original-0.pdf" );
           // This line won't fail
           assertEquals( doc.getDocument().getObjects().size(),docCopy.getDocument().getObjects().size());
           // This line will fail
           assertEquals( doc.getDocument().getObjects().size(),doc2.getDocument().getObjects().size());
        }
        finally
        {
            if( doc != null )
            {
                doc.close();
            }
            
            if( doc2 != null )
            {
                doc2.close();
            }
            
            if( docCopy != null )
            {
                docCopy.close();
            }
        }
            	
    }
}
