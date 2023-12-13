package domI5XJTC1108;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DomWriteI5XJTC {

	public static void main(String[] args) {
		try {
			
			DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
			DocumentBuilder dB = dBF.newDocumentBuilder();
			Document doc = dB.newDocument();
			
			Element root = doc.createElement("I5XJTC_kurzusfelvetel");
			root.setAttribute("tanev", "2023/2024 I.");
			root.setAttribute("nev", "ME");
			doc.appendChild(root);
			
			bevitelhallgatok(doc,root,"4","Katona Bence","2001","programtervezo informatikus");
			bevitelkurzusok(doc, root, "MIGEBANNY1","igen","angol nyelvvizsga elokeszito szint 1.","0","hetfo, 12:00-13:30","A/5 202","Pasztor Krisztina");

			
			TransformerFactory tF = TransformerFactory.newInstance();
			Transformer transformer = tF.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "2");
			
			DOMSource source = new DOMSource(doc);
			File file = new File("kurzusfelvetel1I5XJTC.xml");
			StreamResult sR = new StreamResult(file);
			transformer.transform(source, sR);
			StreamResult sR2 = new StreamResult(System.out);
			transformer.transform(source, sR2);
			
		} catch (ParserConfigurationException | TransformerException e) {
	         e.printStackTrace();
	    }
	}
	private static void bevitelhallgatok(Document doc, Element root, String _evf, String _hnev, String _szulev, String _szak ) {
		Element hallgatok = doc.createElement("hallgatok");
		hallgatok.setAttribute("evf",_evf);
		
		Element hnev = createElement(doc, "hnev",_hnev);
		Element szulev = createElement(doc, "szulev",_szulev);
		Element szak = createElement(doc, "szak",_szak);
		
		hallgatok.appendChild(hnev);
		hallgatok.appendChild(szulev);
		hallgatok.appendChild(szak);
		
		root.appendChild(hallgatok);
	}
	private static void bevitelkurzusok(Document doc, Element root, String _id, String _jovahagyas, String _kurzusnev, String _kredit, String _idopont, String _hely, String _oktato) {
		Element kurzusok = doc.createElement("kurzusok");
		
		Element kurzus = doc.createElement("kurzus");
		kurzus.setAttribute("id",_id);
		kurzus.setAttribute("jovahagyas",_jovahagyas);
		Element kurzusnev = createElement(doc, "kurzusnev", _kurzusnev);
		Element kredit = createElement(doc, "kredit", _kredit);
		Element idopont = createElement(doc, "idopont", _idopont);
		Element hely = createElement(doc, "hely", _hely);
		Element oktato = createElement(doc, "oktato", _oktato);
		
		kurzus.appendChild(kurzusnev);
		kurzus.appendChild(kredit);
		kurzus.appendChild(idopont);
		kurzus.appendChild(hely);
		kurzus.appendChild(oktato);
		kurzusok.appendChild(kurzus);
		
		root.appendChild(kurzusok);
	}
	private static Element createElement(Document doc, String nev2, String value) {
	    Element element = doc.createElement(nev2);
	    element.appendChild(doc.createTextNode(value));
	    return element;
	}

}
