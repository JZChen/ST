/*
 * Copyright 2013 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.pdfbox.bdd;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.Assert;

/**
 *
 * @author jgreen
 */
public class TestBuildNewDocumentFromMultipleSources extends TestCase {

    private static final String FILENAME = "src/test/resources/input/rendering/source.pdf";

    public void testCreateDocument() throws IOException, COSVisitorException {
        PDDocument master = new PDDocument();

        for (int i=0; i<5; i++) {
            List<PDDocument> list = getList();
            if (i==0) {
                List<PDDocument> listOnFirstIteration = getList();
                for (PDDocument doc : listOnFirstIteration) {
                    for (PDPage page : (List<PDPage>) doc.getDocumentCatalog().getAllPages()) {
                        master.importPage(page);
                    }
                }
            }
            for (PDDocument doc : list) {
                for (PDPage page : (List<PDPage>) doc.getDocumentCatalog().getAllPages()) {
                    master.importPage(page);
                }
            }
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        master.save(output);
        Assert.assertTrue(output.size() > 1024);
    }

    private static List<PDDocument> getList() throws IOException {
        List<PDDocument> list = new ArrayList<PDDocument>();
        for (int i=0; i<5; i++) {
            list.add(read(FILENAME));
        }
        return list;
    }

    private static PDDocument read(String filename) throws FileNotFoundException, IOException {
        return PDDocument.load(new File(filename));
    }

}
