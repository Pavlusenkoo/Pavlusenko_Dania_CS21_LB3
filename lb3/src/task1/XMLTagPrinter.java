package task1;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLTagPrinter extends DefaultHandler {
    private static Set<String> tags = new HashSet<>();

    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            XMLTagPrinter handler = new XMLTagPrinter();
            saxParser.parse("Popular_Baby_Names_NY.xml", handler);
            System.out.println("Теги, присутні у документі:");
            for (String tag : tags) {
                System.out.println(tag);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        tags.add(qName);
    }
}