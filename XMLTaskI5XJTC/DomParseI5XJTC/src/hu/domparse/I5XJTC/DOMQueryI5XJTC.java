package hu.domparse.I5XJTC;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.w3c.dom.NodeList;


public class DOMQueryI5XJTC {

	public static void main(String[] args) {
		try {
			//Fajl beolvasas/Dom megalkotas
			File file = new File("XMLI5XJTC.xml");
			DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
			DocumentBuilder dB = dBF.newDocumentBuilder();
			Document doc = dB.parse(file);
			
			//1.lekerdezes/ a 634544TA igazolvannyal rendelkezo berlo varosa+utcaja
			String bigazolvany = "634544TA";
			NodeList berloList = doc.getElementsByTagName("Berlo");
            for (int i = 0; i < berloList.getLength(); i++) {
                Element berlo = (Element) berloList.item(i);
                String berloid = berlo.getAttribute("Bigazolvany");
                if (berloid.equals(bigazolvany)) {   
                   Element cim = (Element) berlo.getElementsByTagName("cim").item(0);
                   String varos = cim.getElementsByTagName("Varos").item(0).getTextContent().trim();
                   String utca = cim.getElementsByTagName("Utca").item(0).getTextContent().trim();
                   System.out.println("A "+bigazolvany+" igazolvanyu berlo itt lakik:"+varos+","+utca);
                   break;
                }
            }
            
            //2.lekerdezes/ a 643-as idvel rendelkezo alkalmazott adatai
			String aid = "643";
			System.out.println();
            NodeList alkalmazottakList = doc.getElementsByTagName("Alkalmazottak");
            for (int i = 0; i < alkalmazottakList.getLength(); i++) {
                Element alkalmazottak = (Element) alkalmazottakList.item(i);
                String alkalmazottakId = alkalmazottak.getAttribute("Aid");
                if (alkalmazottakId.equals(aid)) {
                    String kor = alkalmazottak.getElementsByTagName("Kor").item(0).getTextContent();
                    String fizetes = alkalmazottak.getElementsByTagName("Fizetes").item(0).getTextContent();
                    String nev = alkalmazottak.getElementsByTagName("Nev").item(0).getTextContent();
                    System.out.println("Az "+aid+" id-vel rendelkező alkalmazott kora:"+kor+",   és neve:"+nev);
                    System.out.println("Az alkalmazott ennyi fizetést kap:"+fizetes);
                    break;
                }
            }
            //3.lekerdezes/ A 7389-es idvel rendelkezo ugralovar ara novelese 10k-val
            String uid = "7389";
            System.out.println();
            NodeList ugralovarList = doc.getElementsByTagName("Ugralovar");
            NodeList maganvallalkozasList = doc.getElementsByTagName("Maganvallalkozas");
            for (int i = 0; i < ugralovarList.getLength(); i++) {
                Element ugralovar = (Element) ugralovarList.item(i);
                String ugralovarId = ugralovar.getAttribute("Uid");
                if (ugralovarId.equals(uid)) {
                    for (int l = 0;l <maganvallalkozasList.getLength(); l++) {
                    	Element maganvallalkozas = (Element) maganvallalkozasList.item(l);
                    	String maganvallalkozasFk = maganvallalkozas.getAttribute("Szolgaltatas");
                    	if (ugralovarId.equals(maganvallalkozasFk)) {
                    		String dij = maganvallalkozas.getElementsByTagName("Dij").item(0).getTextContent();
                    		System.out.println("Az ugralovar dija a valtoztatas elott:"+dij+"Ft");
                    		maganvallalkozas.getElementsByTagName("Dij").item(0).setTextContent("50000");
                    		dij = maganvallalkozas.getElementsByTagName("Dij").item(0).getTextContent();
                    		System.out.println("Az ugralovar dija a valtoztatas utan:"+dij+"Ft");
                    		break;
                    	}
                    }
                    break;
                }
            }
            //4.lekerdezes/ Azok a cegek neveinek kiiratasa ahol 40000Ft alatt bereltek
            System.out.println();
            for (int i = 0; i < maganvallalkozasList.getLength(); i++) {
            	Element maganvallalkozas = (Element) maganvallalkozasList.item(i);
            	int dij = Integer.parseInt(maganvallalkozas.getElementsByTagName("Dij").item(0).getTextContent().trim());
            	if(dij < 40000) {
            		String nev = maganvallalkozas.getElementsByTagName("Nev").item(0).getTextContent();
            		System.out.println("Azok a cegek nevei ahol 40000ft alatt bereltek:"+nev);
            	}
            }
            
            //5.lekerdezes/ irassa ki az osszes vendeg nevet és adatait persze csak azokrol akikrol van adat akirol van adat
            NodeList vendegList = doc.getElementsByTagName("Vendeg");
            System.out.println();
            for (int i = 0; i < vendegList.getLength(); i++) {
            	Element vendeg = (Element) vendegList.item(i);
            	String nev = vendeg.getElementsByTagName("Nev").item(0).getTextContent();
            	String kor = vendeg.getElementsByTagName("Kor").item(0).getTextContent();
                Element cim = (Element) vendeg.getElementsByTagName("cim").item(0);
                String varos = cim.getElementsByTagName("Varos").item(0).getTextContent();
                String utca = cim.getElementsByTagName("Utca").item(0).getTextContent();
                System.out.println("Az "+(i+1)+". Vendég adatai:név:"+nev+", kor:"+kor+", lakhelye:"+varos+","+utca);
            }
            
			
		} catch(ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

}

