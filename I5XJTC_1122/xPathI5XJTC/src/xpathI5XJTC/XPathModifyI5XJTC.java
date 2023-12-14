package xpathI5XJTC;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import org.w3c.dom.NodeList;

public class XPathModifyI5XJTC {

	public static void main(String[] args) {
		try {
			File file = new File("studentI5XJTC.xml");
            DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
			DocumentBuilder dB = dBF.newDocumentBuilder();
			Document doc = dB.parse(file);
			doc.getDocumentElement().normalize();
            NodeList classList = doc.getElementsByTagName("class");
            for (int i=0; i<classList.getLength(); i++) {
                Element student = (Element) classList.item(i);
                //Element vezeteknev = (Element) student.getElementsByTagName("vezeteknev").item(0);
                //Element keresztnev = (Element) student.getElementsByTagName("keresztnev").item(0);
                //Element becenev = (Element) student.getElementsByTagName("becenev").item(0);
                //Element kor = (Element) student.getElementsByTagName("kor").item(0);
                //Element osztondij = (Element) student.getElementsByTagName("osztondij").item(0);
                Element szak = (Element) student.getElementsByTagName("szak").item(0);
                String id = student.getAttribute("id");

                if (szak == null) {
                    szak = doc.createElement("szak");
                    szak.appendChild(doc.createTextNode("GEIK"));
                    student.appendChild(szak);
                }

                if (id.equals("01")) {
                	student.getElementsByTagName("vezeteknev").item(0).setTextContent("Katona1");
                }

                if (id.equals("03")) {
                    student.setAttribute("id", "04");
                }
            }

            TransformerFactory tF = TransformerFactory.newInstance();
            Transformer transformer = tF.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult sR = new StreamResult(System.out);
            transformer.transform(source, sR);

            File file2 = new File("studentI5XJTC_1.xml");
            OutputStream oS = new FileOutputStream(file2);
            StreamResult sR2 = new StreamResult(oS);
            transformer.transform(source, sR2);
            oS.close();

        } catch (ParserConfigurationException | IOException | TransformerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}

