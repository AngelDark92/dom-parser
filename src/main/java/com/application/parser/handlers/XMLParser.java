package com.application.parser.handlers;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

/**
 * 
 * XML - XLST to HTML snippet edited from https://mkyong.com/java/java-dom-parser-xml-and-xslt-examples/
 *
 */
public class XMLParser{


    public static void htmlBuilder (String xmlFilename, String outputFilename, String xsltFilename) {  
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try (InputStream is = new FileInputStream(xmlFilename)) {

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(is);

            // call transform() to convert xml to html via a xslt file
            try (FileOutputStream output = new FileOutputStream(outputFilename)) {
                transform(doc, output, xsltFilename);
            }

        } catch (IOException | ParserConfigurationException |
            SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }
  

    private static void transform(Document doc, OutputStream output, String xsltFilename)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        // add XSLT in Transformer
        Transformer transformer = transformerFactory.newTransformer(
                new StreamSource(new File(xsltFilename)));

        transformer.transform(new DOMSource(doc), new StreamResult(output));

    }

}