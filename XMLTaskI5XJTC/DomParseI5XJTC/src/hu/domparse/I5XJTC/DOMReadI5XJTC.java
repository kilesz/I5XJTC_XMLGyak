package hu.domparse.I5XJTC;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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


public class DOMReadI5XJTC {

	public static void main(String[] args) {
		try {
			//fajl beolvas/Dom megalkotas
			File file = new File("XMLI5XJTC.xml");
            DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
            DocumentBuilder dB = dBF.newDocumentBuilder();
            Document doc = dB.parse(file);
            doc.getDocumentElement().normalize();
            //fajl kivalasztasa irasra
            File filekimenet = new File("src/XMLfilekimenet.xml");
            PrintWriter pW = new PrintWriter(new FileWriter(filekimenet, true));
            
            Element root = doc.getDocumentElement();
            String rootName = root.getTagName();
            StringJoiner rA = new StringJoiner(" ");
            NamedNodeMap rAM = root.getAttributes();
            
            for (int i = 0; i < rAM.getLength(); i++) {
                Node tulajdonsag = rAM.item(i);
                rA.add(tulajdonsag.getNodeName() + "=\"" + tulajdonsag.getNodeValue() + "\"");
            }
            //kiiratas consolra es fajlba
            System.out.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n");
            pW.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            System.out.println("<" + rootName + rA.toString() + ">");
            pW.print("<" + rootName + rA.toString() + ">");
            
            NodeList vendegList = doc.getElementsByTagName("Vendeg");
            NodeList latogatasList = doc.getElementsByTagName("Latogatas");
            NodeList ugralovarList = doc.getElementsByTagName("Ugralovar");
            NodeList beosztasList = doc.getElementsByTagName("Beosztas");
            NodeList alkalmazottakList = doc.getElementsByTagName("Alkalmazottak");
            NodeList maganvallalkozasList = doc.getElementsByTagName("Maganvallalkozas");
            NodeList berloList = doc.getElementsByTagName("Berlo");
            
            pW.println("");
            pW.println("");
            System.out.println("");
            listaKiir(vendegList, pW);
            System.out.println("");
            pW.println("");
            listaKiir(latogatasList, pW);
            System.out.println("");
            pW.println("");
            listaKiir(ugralovarList, pW);
            System.out.println("");
            pW.println("");
            listaKiir(beosztasList, pW);
            System.out.println("");
            pW.println("");
            listaKiir(alkalmazottakList, pW);
            System.out.println("");
            pW.println("");
            listaKiir(maganvallalkozasList, pW);
            System.out.println("");
            pW.println("");
            listaKiir(berloList, pW);
            
            System.out.println("</" + rootName + ">");
            pW.append("</" + rootName + ">");
            pW.close();
            
            
		} catch (ParserConfigurationException | SAXException | IOException e) {
	         e.printStackTrace();
	    }
	}
    private static void listaKiir(NodeList lista, PrintWriter wP) {
        for (int i = 0; i < lista.getLength(); i++) {
            Node node = lista.item(i);
            belsoElemekKiir(node, 0, wP);
            System.out.println("");
            wP.println("");
        }
    }
    private static void belsoElemekKiir(Node node, int kozdb, PrintWriter wP) {
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

            wP.print(kozAdas(kozdb));
            wP.print("<" + elemNev + " " + tulajdonsagok.toString() + ">");

            NodeList gyerekElem = element.getChildNodes();
            if (gyerekElem.getLength() == 1 && gyerekElem.item(0).getNodeType() == Node.TEXT_NODE) {
                System.out.print(gyerekElem.item(0).getNodeValue());
                wP.print(gyerekElem.item(0).getNodeValue());
            } else {
                System.out.println();
                wP.println();
                for (int i = 0; i < gyerekElem.getLength(); i++) {
                    belsoElemekKiir(gyerekElem.item(i), kozdb + 1, wP);
                }
                System.out.print(kozAdas(kozdb));
                wP.print(kozAdas(kozdb));
            }
            System.out.println("</" + elemNev + ">");
            wP.println("</" + elemNev + ">");
        }
    }
    //kozhossz kimerese
    private static String kozAdas(int kozdb) {
        StringBuilder sB = new StringBuilder();
        for (int i = 0; i < (kozdb+1); i++) {
            sB.append("   ");
        }
        return sB.toString();
    }
}