package task3;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class NationalityExtractor {
    private static final Set<String> nationalities = new HashSet<>();

    public static void main(String[] args) {
        String xmlFilePath = "Popular_Baby_Names_NY.xml";
        parseXMLFile(xmlFilePath);
        System.out.println("Національні групи, представлені в документі:");
        for (String nationality : nationalities) {
            System.out.println(nationality);
        }
    }

    private static void parseXMLFile(String filePath) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new DefaultHandler() {
                boolean isEthnicityElement = false;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    if (qName.equalsIgnoreCase("ethcty")) {
                        isEthnicityElement = true;
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) {
                    if (qName.equalsIgnoreCase("ethcty")) {
                        isEthnicityElement = false;
                    }
                }

                @Override
                public void characters(char ch[], int start, int length) {
                    if (isEthnicityElement) {
                        String ethnicity = new String(ch, start, length);
                        nationalities.add(ethnicity);
                    }
                }
            };

            saxParser.parse(new File(filePath), handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
