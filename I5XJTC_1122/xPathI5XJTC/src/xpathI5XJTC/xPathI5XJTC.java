package xpathI5XJTC;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class xPathI5XJTC {

	public static void main(String[] args) {
		try {		
			DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
			DocumentBuilder dB = dBF.newDocumentBuilder();
			Document doc = dB.parse("studentI5XJTC.xml");
			doc.getDocumentElement().normalize();
			XPath xPath = XPathFactory.newInstance().newXPath();
			
		
			//1.
			String lekerdezes = "class / student";

            //2.
			//String lekerdezes = "//student[@id='02']";

            //3.
			//String lekerdezes = "//student";

            //4.
			//String lekerdezes = "class/student[2]";
            
            //5.
			//String lekerdezes = "class/student[last()]";

            //6.
			//String lekerdezes = "class/student[last()-1]";

            //7.
			//String lekerdezes = "class/student[position()<3]";

            //8.
			//String lekerdezes = "/class/*";

            //9.
			//String lekerdezes = "//student[@*]";

            //10.
			//String lekerdezes = "//*";

            //11.
			//String lekerdezes = "/class/student[age>20]";

            //12.
			//String lekerdezes = "//student/firstname | //student/lastname";
			
			
			NodeList Lista = (NodeList) xPath.compile(lekerdezes).evaluate(doc, XPathConstants.NODESET);
			
			
			for (int i = 0; i < Lista.getLength(); i++) {
				Node node = Lista.item(i);
				System.out.println("\nAktuális elem: " + node.getNodeName());
				
				if(node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("student")) {
					
					Element student = (Element) node;
					
					System.out.println("Student ID: " + student.getAttribute("id"));
					System.out.println("Keresztnév: " + student.getElementsByTagName("keresztnev").item(0).getTextContent());
					System.out.println("Vezetéknév: " + student.getElementsByTagName("vezeteknev").item(0).getTextContent());
					System.out.println("Becenév: " + student.getElementsByTagName("becenev").item(0).getTextContent());
					System.out.println("Kor: " + student.getElementsByTagName("kor").item(0).getTextContent());
					System.out.println("Osztondij: " + student.getElementsByTagName("osztondij").item(0).getTextContent());
				}
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch(SAXException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} catch(XPathExpressionException e) {
			e.printStackTrace();
		}
		
	}
}


