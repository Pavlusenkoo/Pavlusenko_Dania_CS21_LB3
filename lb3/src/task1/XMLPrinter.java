package task1;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLPrinter extends DefaultHandler {
    private static final String ROW_TAG = "row";
    private boolean stopPrinting = false;
    private Map<String, Integer> tagCounters = new HashMap<>();

    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            XMLPrinter handler = new XMLPrinter();
            saxParser.parse("Popular_Baby_Names_NY.xml", handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (!stopPrinting) {
            if (!qName.equals(ROW_TAG)) {
                int count = tagCounters.getOrDefault(qName, 0);
                if (count >= 2) {
                    stopPrinting = true;
                    return;
                }
                tagCounters.put(qName, count + 1);
            }
            System.out.print("<" + qName);
            for (int i = 0; i < attributes.getLength(); i++) {
                System.out.print(" " + attributes.getQName(i) + "=\"" + attributes.getValue(i) + "\"");
            }
            System.out.print(">");
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (!stopPrinting) {
            System.out.println("</" + qName + ">");
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (!stopPrinting) {
            System.out.print(new String(ch, start, length));
        }
    }
}