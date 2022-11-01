package com.application.parser.handlers;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.application.parser.interf.Controller;

/**
 * 
 * @author Angelo Need to update this file with threads or waits, files of
 *         bigger dimensions could give errors
 */

public class FileHandler {

	/**
	 * Process file and send it to the XMLParser which converts it to html.
	 */
	public static void processFiles(List<File> files) {
		//TODO use streams instead of for loops
		for (File file : files) {
			// check if xslt file with same name as xml is present
			if (new File(file.getAbsolutePath().replaceAll("\\.xml$", ".xslt")).exists()) {
				extractor(file, file.getAbsolutePath().replaceAll("\\.xml$", ".xslt"));
			}
			// check if href references an existing file through the use of SAXParser and calls the extractor
			else {

				try {
					SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
					saxParser.parse(file.getAbsolutePath(), new DefaultHandler() {
						// override SAXParser method
						@Override
						public void processingInstruction(String target, String data) throws SAXException {
							
							if (target.equals("xml-stylesheet")) {
								Pattern pattern = Pattern.compile("href=\"(.+)\"");
								Matcher matcher = pattern.matcher(data);
							
								if (matcher.find()) {
									File xslt = new File(matcher.group(1));
									
									// if pattern was matched (href exists in the xml file) check if the file it points to exists
									if (xslt.exists()) {
										extractor(file, xslt.getAbsolutePath());
									} 
									else {
										//check if referenced file exists in the same folder as main file or subpaths
										xslt = new File(file.getParent()+'\\'+xslt.getName());
										if (xslt.exists()) {
											extractor(file, xslt.getAbsolutePath());}
										else {
											Controller.publicTxtArea.appendText("\n" +file.getName()+" referenced xslt could not be found.");
										}
									}
								}
							}
						}
					});

				} catch (Exception e) {

					Controller.publicTxtArea.appendText("\nXslt file for " + file.getName() + " not found.");
				}
			}
			// checks if html file was created and updates the application's log
			if (new File(file.getAbsolutePath().replaceAll("\\.xml$", ".html")).exists()) {
				Controller.publicTxtArea.appendText("\n" + file.getName() + " was successfully processed.\n");
			} else {
				Controller.publicTxtArea.appendText("\n" + file.getName() + " could not be processed.\n");
			}
		}
	}

	// extractor method in XMLParser class
	private static void extractor(File file, String string) {
		XMLParser.htmlBuilder(file.getAbsolutePath(), file.getAbsolutePath().replaceAll("\\.xml$", ".html"), string);
	}

}
