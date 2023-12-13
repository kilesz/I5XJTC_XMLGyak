package domI5XJTC1115;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomQueryI5XJTC {

	public static void main(String[] args) {
		try {
			File file = new File("orarendI5XJTC.xml");
			File file2 = new File("orarendI5XJTCkimenet.xml");
			DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
			DocumentBuilder dB = dBF.newDocumentBuilder();
			Document doc = dB.parse(file);
			doc.getDocumentElement().normalize();
			DOMQuery query = new DOMQuery();		
			
			List<String> kurzusNevek = query.kurzusNevek(doc);
			
			System.out.println("a) Kurzusok: "+kurzusNevek+"\n");
		
			System.out.println("b) Az első elem:");
			String elso = query.elso(doc);
			System.out.println(elso+"\n");
			FileWriter fW = new FileWriter(file2);
			fW.write("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n");
			fW.write(elso);
			fW.close();
		
			System.out.println("c) Oktatók: "+query.oktatok(doc)+"\n");
		
			System.out.println("d) Kurzusok időpontjai: "+query.idoPontok(doc));
			
		} catch(ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
	private static class DOMQuery{
		public List<String> kurzusNevek(Document doc){
			List<String> kurzusNevek = new ArrayList<>();
			Element root = doc.getDocumentElement();
			NodeList kurzusok = root.getElementsByTagName("ora");
			for(int i=0; i<kurzusok.getLength(); i++) {
				Node kurzus = kurzusok.item(i);
				NodeList nevek = ((Element)kurzus).getElementsByTagName("targy");
				for(int j=0; j<nevek.getLength(); j++) {
					if(!kurzusNevek.contains(nevek.item(j).getTextContent())) {
						kurzusNevek.add(nevek.item(j).getTextContent());
					}
				}
			}
			return kurzusNevek;
		}

		public String elso(Document doc) {
			Element root = doc.getDocumentElement();
			Node elso = root.getElementsByTagName("ora").item(0);
			return kurzusIras(elso);
		}

		public List<String> oktatok(Document doc){
			List<String> oktatoNevek = new ArrayList<>();
			Element root = doc.getDocumentElement();
			NodeList kurzusok = root.getElementsByTagName("ora");
			for(int i=0; i<kurzusok.getLength(); i++) {
				Node kurzus = kurzusok.item(i);
				Node oktato = ((Element)kurzus).getElementsByTagName("oktato").item(0);
				if(!oktatoNevek.contains(oktato.getTextContent())) {
					oktatoNevek.add(oktato.getTextContent());
				}
			}
			return oktatoNevek;
		}

		public List<String> idoPontok(Document doc){
			List<String> idopontok = new ArrayList<>();
			Element root = doc.getDocumentElement();
			NodeList kurzusok = root.getElementsByTagName("ora");
			for(int i=0; i<kurzusok.getLength(); i++) 
			{

				Node kurzus = kurzusok.item(i);
				Node idopont = ((Element)kurzus).getElementsByTagName("idopont").item(0);
				Element nap = (Element)((Element)idopont).getElementsByTagName("nap").item(0);
				Element tol = (Element)((Element)idopont).getElementsByTagName("tol").item(0);
				Element ig = (Element) ((Element)idopont).getElementsByTagName("ig").item(0);
				idopontok.add(nap.getTextContent()+" "+tol.getTextContent()+"-"+ig.getTextContent());
			}
			return idopontok;
		}
	
		private String kurzusIras(Node _kurzus) {
			String kimenet = "";
			String kozElem= "   ";
			int koz = 0;
			Element kurzus = (Element)_kurzus;
			kimenet+=(kozElem.repeat(koz)+"<ora");
			NamedNodeMap tulajdonsagok = kurzus.getAttributes();
			kimenet+=kiirTulajdonsag(tulajdonsagok);
			
			Element targy = (Element) kurzus.getElementsByTagName("targy").item(0);
			koz++;
			kimenet+=(kozElem.repeat(koz)+"<targy>"+targy.getTextContent()+"</targy>\n");
			Element idopont = (Element) kurzus.getElementsByTagName("idopont").item(0);
			kimenet+=kiirIdo(idopont, koz, kozElem);
			Element helyszin = (Element) kurzus.getElementsByTagName("helyszin").item(0);
			kimenet+=(kozElem.repeat(koz)+"<helyszin>"+helyszin.getTextContent()+"</helyszin>\n");
			Element oktato = (Element) kurzus.getElementsByTagName("oktato").item(0);
			kimenet+=(kozElem.repeat(koz)+"<oktato>"+oktato.getTextContent()+"</oktato>\n");
			Element szak = (Element) kurzus.getElementsByTagName("szak").item(0);
			kimenet+=(kozElem.repeat(koz)+"<szak>"+szak.getTextContent()+"</szak>\n");
			koz--;
			kimenet+=(kozElem.repeat(koz)+"</ora>");
			return kimenet;
		}

		private static String kiirIdo(Element idopont, int koz, String kozElem) {


			String kimenet = "";

			kimenet+=(kozElem.repeat(koz)+"<idopont>\n");
			Element nap = (Element) idopont.getElementsByTagName("nap").item(0);
			Element tol = (Element) idopont.getElementsByTagName("tol").item(0);
			Element ig = (Element) idopont.getElementsByTagName("ig").item(0);

			koz++;
			kimenet+=(kozElem.repeat(koz)+"<nap>"+nap.getTextContent()+"</nap>\n");
			kimenet+=(kozElem.repeat(koz)+"<tol>"+tol.getTextContent()+"</tol>\n");
			kimenet+=(kozElem.repeat(koz)+"<ig>"+ig.getTextContent()+"</ig>\n");
			koz--;
			kimenet+=(kozElem.repeat(koz)+"</idopont>\n");
			return kimenet;
		}


		private static String kiirTulajdonsag(NamedNodeMap _tulajdonsagok) {
			String kimenet = "";
			if(_tulajdonsagok.getLength()==0) {
				kimenet+=(">\n");
			}
			else {
				kimenet+=(" ");
				for(int i=0; i<_tulajdonsagok.getLength(); i++) {
					kimenet+=(_tulajdonsagok.item(i).getNodeName()+"=\""+_tulajdonsagok.item(i).getNodeValue()+"\"");
					if(i!=_tulajdonsagok.getLength()-1) {
						kimenet+=(" ");
					}
				}
				kimenet+=(">\n");
			}
			return kimenet;
			
		}
	}
}


