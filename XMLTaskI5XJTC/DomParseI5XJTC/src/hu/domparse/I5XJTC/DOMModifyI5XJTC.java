package hu.domparse.I5XJTC;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class DOMModifyI5XJTC {

	public static void main(String[] args) {
		try {
			//fajl beolvas/Dom megalkotas
			File file = new File("XMLI5XJTC.xml");
			DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
			DocumentBuilder dB = dBF.newDocumentBuilder();
			Document doc = dB.parse(file);
			
			//fajl modositas
			modosit(doc);
			
			//fajl mentes	
			TransformerFactory tF = TransformerFactory.newInstance();
			Transformer transformer = tF.newTransformer();
			DOMSource source = new DOMSource(doc); 
			StreamResult sR = new StreamResult(new File("src/hu/domparse/I5XJTC/XMl_modositva.xml"));
			transformer.transform(source, sR);

			//Console kiiratas
			StreamResult sR2 = new StreamResult(System.out);
			transformer.transform(source, sR2);
		} catch(TransformerException | ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
	public static void modosit(Document doc) {
		//Lekerjuk a listahoz tartozo elemeket, majd megkeressuk és kivalasztjuk az elemet amit modositunk
		NodeList vendegList = doc.getElementsByTagName("Vendeg");
		Element vendeg = (Element) vendegList.item(1);
		vendeg.getElementsByTagName("Nev").item(0).setTextContent("Jóska Pista");
		
		NodeList latogatasList = doc.getElementsByTagName("Latogatas");
		Element latogatas = (Element) latogatasList.item(2);
		latogatas.getElementsByTagName("Erkezes").item(0).setTextContent("4:00");
		
		NodeList ugralovarList = doc.getElementsByTagName("Ugralovar");
		Element ugralovar = (Element) ugralovarList.item(0);
		ugralovar.getElementsByTagName("Helyigeny").item(0).setTextContent("200x400x300 cm");
		
		NodeList beosztasList = doc.getElementsByTagName("Beosztas");
		Element beosztas = (Element) beosztasList.item(0);
		beosztas.getElementsByTagName("Mettol").item(0).setTextContent("5:00");
		
		NodeList alkalmazottakList = doc.getElementsByTagName("Alkalmazottak");
		Element alkalmazottak = (Element) alkalmazottakList.item(0);
		alkalmazottak.getElementsByTagName("Kor").item(0).setTextContent("24");
		
		NodeList maganvallalkozasList = doc.getElementsByTagName("Maganvallalkozas");
		Element maganvallalkozas = (Element) maganvallalkozasList.item(1);
		maganvallalkozas.getElementsByTagName("Nev").item(0).setTextContent("HatalmasUgralo");
		
		NodeList berloList = doc.getElementsByTagName("Berlo");
		Element berlo = (Element) berloList.item(2);
		berlo.getElementsByTagName("Nev").item(0).setTextContent("Burok Tamás");
		
	}

}