package task2;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;

public class Validator {

    public static final String filePathXSD = "xml-scheme.xsd";

    public static final String filePathXML = "Popular_Baby_Names_NY.xml";

    public static void main(String[] args) throws SAXException, ParserConfigurationException, IOException {

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File(filePathXSD));

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        dbf.setSchema(schema);
        dbf.setValidating(false);
        dbf.setNamespaceAware(true);
        dbf.setIgnoringElementContentWhitespace(true);

        File xmlFile = new File(filePathXML);

        DocumentBuilder builder = dbf.newDocumentBuilder();
        builder.setErrorHandler(new SimpleErrorHandler());
        builder.parse(xmlFile);

        System.out.println("Successful validation");

    }
}
