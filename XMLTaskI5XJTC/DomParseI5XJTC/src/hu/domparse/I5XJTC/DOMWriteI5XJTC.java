package hu.domparse.I5XJTC;

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

public class DOMWriteI5XJTC {

	public static void main(String[] args) {
		try {
			
			//Dom megalkotas
			DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
			DocumentBuilder dB = dBF.newDocumentBuilder();
			Document doc = dB.newDocument();
			
			//Gyoker elem megalkotas
			Element root = doc.createElement("I5XJTC_Beadando");
			root.setAttribute("xmlns:xs", "http://www.w3.org/2001/XMLSchema-instance");
			root.setAttribute("elementFormDefault", "qualified");
			root.setAttribute("attributeFormDefault", "unqualified");
			doc.appendChild(root);
			
			//adat feltoltes
			bevitelVendeg(doc,root,"382945TA","Adler Károly utca 12.","Miskolc","Király Roland","42");
			bevitelLatogatas(doc, root, "1592","382945TA","7389","8:00","13:10");
			bevitelUgralovar(doc, root, "7389","20 perc","600x600x500 cm","U74/small");
			bevitelBeosztas(doc, root, "14","7389","643","8:00","14:00");
			bevitelAlkalmazottak(doc, root, "643", "23", "2300Ft/óra","Aranyos József");
			bevitelMaganvallalkozas(doc, root, "1111","7389", "40000", "Napvirág Ugráló", "2021.05.01.");
			bevitelBerlo(doc, root, "634544TA", "1111", "csakavas@gmail.com","csakavas@freemail.hu","csakavas@citromail.hu","06207385332","06707352332","06207242322","Lakatos Armando","Miskolc","Ady Endre utca 23.");
			
			bevitelVendeg(doc,root,"261552PE","Akác utca 44.","Miskolc","Nemes Norbert","23");
			bevitelLatogatas(doc, root,"8787","261552PE","6524","10:23","14:00");
			bevitelUgralovar(doc, root,"6524","15 perc","400x450x250 cm","U44/medium");
			bevitelBeosztas(doc, root,"30","6524","232","12:00","20:00");
			bevitelAlkalmazottak(doc, root,"232","20","2250Ft/óra","Koldus Mátyás");
			bevitelMaganvallalkozas(doc, root,"2222","6524","30000","Napvirág Ugráló","2007.03.22.");
			bevitelBerlo(doc, root,"664755PE","2222","rocklau@gmail.com","rocklau@freemail.hu","rocklau@citromail.hu","06207482726",">06708277382","06401782754","Vidám Laura","Legyesbénye","Rákóczi utca 21.");
			
			bevitelVendeg(doc,root,"113324RF","Árpád utca 10.","Hatvan","Utond Zoli","33");
			bevitelLatogatas(doc, root,"5366","113324RF","2374","8:10","18:30");
			bevitelUgralovar(doc, root,"2374","35 perc","500x400x400 cm","U66/big");
			bevitelBeosztas(doc, root,"45","2374","977","8:00","14:00");
			bevitelAlkalmazottak(doc, root,"977","21","2000Ft/óra","Fátyol Enikő");
			bevitelMaganvallalkozas(doc, root,"4444","2374","35000","UgriBugri","2022.07.11.");
			bevitelBerlo(doc, root,"726354TA","4444","izomero44@gmail.com","izomero44@freemail.hu","izomero44@citromail.hu","06703728994","06202352723","06701167211","Karos Viktor","Budapest","Bajza utca 34.");
			
			
			TransformerFactory tF = TransformerFactory.newInstance();
			Transformer transformer = tF.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "2");
			
			//Console kiiratas/fajlba iras
			DOMSource source = new DOMSource(doc);
			File file = new File("src/XMLI5XJTC1.xml");
			StreamResult sR = new StreamResult(file);
			transformer.transform(source, sR);
			StreamResult sR2 = new StreamResult(System.out);
			transformer.transform(source, sR2);
			
		} catch (ParserConfigurationException | TransformerException e) {
	         e.printStackTrace();
	    }
	}
	
	private static void bevitelVendeg(Document doc, Element root, String vigazolvany, String utca, String varos, String nev, String kor) {
		Element vendeg = doc.createElement("Vendeg");
		vendeg.setAttribute("Vigazolvany",vigazolvany);
		
		Element cim = doc.createElement("cim");
		Element varosE = createElement(doc, "Varos",varos);
		Element utcaE = createElement(doc, "Utca",utca);
		Element nevE = createElement(doc, "Nev",nev);
		Element korE = createElement(doc, "Kor",kor);
		
		cim.appendChild(utcaE);
		cim.appendChild(varosE);
		vendeg.appendChild(cim);
		vendeg.appendChild(nevE);
		vendeg.appendChild(korE);
		
		root.appendChild(vendeg);
		
	}
	private static void bevitelLatogatas(Document doc, Element root, String lid, String vigazolvany_lid, String lid_uid, String erkezes, String tavozas) {
		Element latogatas = doc.createElement("Latogatas");
		latogatas.setAttribute("Lid",lid);
		latogatas.setAttribute("Vigazolvany_Lid",vigazolvany_lid);
		latogatas.setAttribute("Lid_Uid",lid_uid);
		
		Element erkezesE = createElement(doc, "Erkezes",erkezes);
		Element tavozasE = createElement(doc, "Tavozas",tavozas);
		
		latogatas.appendChild(erkezesE);
		latogatas.appendChild(tavozasE);
		
		root.appendChild(latogatas);
	}
	private static void bevitelUgralovar(Document doc, Element root, String uid, String elokeszitesi_ido, String helyigeny, String tipus) {
		Element ugralovar = doc.createElement("Ugralovar");
		ugralovar.setAttribute("Uid",uid);
		
		Element elokeszitesi_idoE = createElement(doc, "Elokeszitesi_ido",elokeszitesi_ido);
		Element helyigenyE = createElement(doc, "Helyigeny",helyigeny);
		Element tipusE = createElement(doc, "Tipus",tipus);
		
		ugralovar.appendChild(elokeszitesi_idoE);
		ugralovar.appendChild(helyigenyE);
		ugralovar.appendChild(tipusE);
		
		root.appendChild(ugralovar);
	}
	private static void bevitelBeosztas(Document doc, Element root, String bid, String uid_bid, String bid_aid, String mettol, String meddig) {
		Element beosztas = doc.createElement("Beosztas");
		beosztas.setAttribute("Bid",bid);
		beosztas.setAttribute("Uid_Bid",uid_bid);
		beosztas.setAttribute("Bid_Aid",bid_aid);
		
		Element mettolE = createElement(doc, "Mettol",mettol);
		Element meddigE = createElement(doc, "Meddig",meddig);
		
		beosztas.appendChild(mettolE);
		beosztas.appendChild(meddigE);
		
		root.appendChild(beosztas);
	}
	private static void bevitelAlkalmazottak(Document doc, Element root, String aid, String kor, String fizetes, String nev) {
		Element alkalmazottak = doc.createElement("Alkalmazottak");
		alkalmazottak.setAttribute("Aid",aid);
		
		Element korE = createElement(doc, "Kor",kor);
		Element fizetesE = createElement(doc, "Fizetes",fizetes);
		Element nevE = createElement(doc, "Nev", nev);
		
		alkalmazottak.appendChild(korE);
		alkalmazottak.appendChild(fizetesE);
		alkalmazottak.appendChild(nevE);
		
		root.appendChild(alkalmazottak);
	}
	private static void bevitelMaganvallalkozas(Document doc, Element root, String mid, String szolgaltatas, String dij, String nev, String idopontok) {
		Element maganvallalkozas = doc.createElement("Maganvallalkozas");
		maganvallalkozas.setAttribute("Mid",mid);
		maganvallalkozas.setAttribute("Szolgaltatas",szolgaltatas);
		
		Element dijE = createElement(doc, "Dij",dij);
		Element nevE = createElement(doc, "Nev",nev);
		Element idopontokE = createElement(doc, "Idopontok", idopontok);
		
		maganvallalkozas.appendChild(dijE);
		maganvallalkozas.appendChild(nevE);
		maganvallalkozas.appendChild(idopontokE);
		
		root.appendChild(maganvallalkozas);
	}
	private static void bevitelBerlo(Document doc, Element root, String bigazolvany, String berles, String utca, String varos, String nev, String email1, String email2, String email3, String telefonszam1, String telefonszam2, String telefonszam3) {
		Element berlo = doc.createElement("Berlo");
		berlo.setAttribute("Bigazolvany",bigazolvany);
		berlo.setAttribute("Berles",berles);
		
		Element email1E = createElement(doc, "Email",email1);
		Element email2E = createElement(doc, "Email",email2);
		Element email3E = createElement(doc, "Email",email3);
		Element telefonszam1E = createElement(doc, "Telefonszam",telefonszam1);
		Element telefonszam2E = createElement(doc, "Telefonszam",telefonszam2);
		Element telefonszam3E = createElement(doc, "Telefonszam",telefonszam3);
		Element nevE = createElement(doc, "Nev",nev);
		Element cim = doc.createElement("cim");
		Element varosE = createElement(doc, "Varos", varos);
		Element utcaE = createElement(doc, "Utca", utca);
		
		berlo.appendChild(email1E);
		berlo.appendChild(email2E);
		berlo.appendChild(email3E);
		berlo.appendChild(telefonszam1E);
		berlo.appendChild(telefonszam2E);
		berlo.appendChild(telefonszam3E);
		berlo.appendChild(nevE);
		cim.appendChild(varosE);
		cim.appendChild(utcaE);
		
		root.appendChild(berlo);
	}
	private static Element createElement(Document doc, String nev2, String value) {
	    Element element = doc.createElement(nev2);
	    element.appendChild(doc.createTextNode(value));
	    return element;
	 }

}
