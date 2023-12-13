package domI5XJTC1108;

import java.io.File;
import java.io.IOException;
import java.util.StringJoiner;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class DomReadI5XJTC {

	public static void main(String[] args) {
		try {
			File file = new File("kurzusfelvetelI5XJTC.xml");
            DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
            DocumentBuilder dB = dBF.newDocumentBuilder();
            Document doc = dB.parse(file);
            doc.getDocumentElement().normalize();
            
            Element root = doc.getDocumentElement();
            String rootName = root.getTagName();
            StringJoiner rA = new StringJoiner(" ");
            NamedNodeMap rAM = root.getAttributes();
            
            for (int i = 0; i < rAM.getLength(); i++) {
                Node tulajdonsag = rAM.item(i);
                rA.add(tulajdonsag.getNodeName() + "=\"" + tulajdonsag.getNodeValue() + "\"");
            }
            System.out.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n");
            System.out.println("<" + rootName + rA.toString() + ">");
            
            NodeList kurzusokList = doc.getElementsByTagName("kurzusok");
            NodeList hallgatokList = doc.getElementsByTagName("hallgatok");
            
            System.out.println("");
            listaKiir(hallgatokList);
            System.out.println("");
            listaKiir(kurzusokList);
            System.out.println("");
            
            System.out.println("</" + rootName + ">");         
            
		} catch (ParserConfigurationException | SAXException | IOException e) {
	         e.printStackTrace();
	    }
	}
    private static void listaKiir(NodeList lista) {
        for (int i = 0; i < lista.getLength(); i++) {
            Node node = lista.item(i);
            belsoElemekKiir(node, 0);
            System.out.println("");
        }
    }
    private static void belsoElemekKiir(Node node, int kozdb) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String elemNev = element.getTagName();
            StringJoiner tulajdonsagok = new StringJoiner(" ");
            NamedNodeMap tulajdonsagokM = element.getAttributes();

            for (int i = 0; i < tulajdonsagokM.getLength(); i++) {
                Node tulajdonsag = tulajdonsagokM.item(i);
                tulajdonsagok.add(tulajdonsag.getNodeName() + "=\"" + tulajdonsag.getNodeValue() + "\"");
            }

            System.out.print(kozAdas(kozdb));
            System.out.print("<" + elemNev + " " + tulajdonsagok.toString() + ">");

            NodeList gyerekElem = element.getChildNodes();
            if (gyerekElem.getLength() == 1 && gyerekElem.item(0).getNodeType() == Node.TEXT_NODE) {
                System.out.print(gyerekElem.item(0).getNodeValue());
            } else {
                System.out.println();
                for (int i = 0; i < gyerekElem.getLength(); i++) {
                    belsoElemekKiir(gyerekElem.item(i), kozdb + 1);
                }
                System.out.print(kozAdas(kozdb));
            }
            System.out.println("</" + elemNev + ">");
        }
    }
    private static String kozAdas(int kozdb) {
        StringBuilder sB = new StringBuilder();
        for (int i = 0; i < (kozdb+1); i++) {
            sB.append("   ");
        }
        return sB.toString();
   }


}

