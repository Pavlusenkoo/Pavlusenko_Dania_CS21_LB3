package task5;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class ReadBabyNamesXML {
    public static void main(String[] args) {
        String fileName = "top_baby_names.xml";
        readXMLFile(fileName);
    }

    private static void readXMLFile(String fileName) {
        try {
            File inputFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            System.out.println("Baby names in the XML file:");
            NodeList nodeList = doc.getElementsByTagName("name");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String name = element.getElementsByTagName("nm").item(0).getTextContent();
                    String gender = element.getElementsByTagName("gndr").item(0).getTextContent();
                    int count = Integer.parseInt(element.getElementsByTagName("cnt").item(0).getTextContent());
                    int rank = Integer.parseInt(element.getElementsByTagName("rnk").item(0).getTextContent());

                    System.out.println("Name: " + name);
                    System.out.println("Gender: " + gender);
                    System.out.println("Count: " + count);
                    System.out.println("Rank: " + rank);
                    System.out.println();
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
