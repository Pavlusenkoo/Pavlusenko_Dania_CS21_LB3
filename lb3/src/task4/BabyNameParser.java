package task4;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class BabyName {
    String name;
    String gender;
    int count;
    int rank;

    BabyName(String name, String gender, int count, int rank) {
        this.name = name;
        this.gender = gender;
        this.count = count;
        this.rank = rank;
    }
}

public class BabyNameParser {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the desired ethnicity: ");
        String ethnicity = scanner.nextLine();
        System.out.print("Enter the number of top names to retrieve: ");
        int topCount = scanner.nextInt();

        List<BabyName> topNames = parseXMLFile("Popular_Baby_Names_NY.xml", ethnicity, topCount);
        writeToXMLFile(topNames, "top_baby_names.xml");
    }

    private static List<BabyName> parseXMLFile(String fileName, String ethnicity, int topCount) {
        List<BabyName> topNames = new ArrayList<>();
        try {
            File inputFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("row");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String elementEthnicity = element.getElementsByTagName("ethcty").item(0).getTextContent();
                    if (elementEthnicity.equals(ethnicity)) {
                        String name = element.getElementsByTagName("nm").item(0).getTextContent();
                        String gender = element.getElementsByTagName("gndr").item(0).getTextContent();
                        int count = Integer.parseInt(element.getElementsByTagName("cnt").item(0).getTextContent());
                        int rank = Integer.parseInt(element.getElementsByTagName("rnk").item(0).getTextContent());
                        topNames.add(new BabyName(name, gender, count, rank));
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        topNames.sort((a, b) -> Integer.compare(a.rank, b.rank));
        return topNames.subList(0, Math.min(topCount, topNames.size()));
    }

    private static void writeToXMLFile(List<BabyName> topNames, String fileName) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("top_baby_names");
            doc.appendChild(rootElement);

            for (BabyName babyName : topNames) {
                Element nameElement = doc.createElement("name");
                rootElement.appendChild(nameElement);

                Element nameValueElement = doc.createElement("nm");
                nameValueElement.appendChild(doc.createTextNode(babyName.name));
                nameElement.appendChild(nameValueElement);

                Element genderElement = doc.createElement("gndr");
                genderElement.appendChild(doc.createTextNode(babyName.gender));
                nameElement.appendChild(genderElement);

                Element countElement = doc.createElement("cnt");
                countElement.appendChild(doc.createTextNode(String.valueOf(babyName.count)));
                nameElement.appendChild(countElement);

                Element rankElement = doc.createElement("rnk");
                rankElement.appendChild(doc.createTextNode(String.valueOf(babyName.rank)));
                nameElement.appendChild(rankElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }
}