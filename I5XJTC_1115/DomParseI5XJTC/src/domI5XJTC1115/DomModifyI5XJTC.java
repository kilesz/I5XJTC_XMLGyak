package domI5XJTC1115;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;

public class DomModifyI5XJTC {

	public static void main(String[] args) {
		try {
            File file = new File("orarendI5XJTC.xml");
            DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
            DocumentBuilder dB = dBF.newDocumentBuilder();
            Document doc = dB.parse(file);
            doc.getDocumentElement().normalize();
            NodeList oraList = doc.getElementsByTagName("ora");
            for (int i = 0; i < oraList.getLength(); i++) {
                Element ora = (Element) oraList.item(i);
                Element oktato = (Element) ora.getElementsByTagName("oktato").item(0);
                String id = ora.getAttribute("id");
                String tipus = ora.getAttribute("tipus");

                if (oktato == null) {
                    oktato = doc.createElement("oraado");
                    oktato.appendChild(doc.createTextNode("Hazy Attila"));
                    ora.appendChild(oktato);
                }

                if (tipus.equals("gyakorlat")) {
                    ora.setAttribute("tipus", "eloadas");
                }
                
                if (id.equals("sz2hf")) {
                	ora.getElementsByTagName("helyszin").item(0).setTextContent("inf/102");
                }
            }

            TransformerFactory tF = TransformerFactory.newInstance();
            Transformer transformer = tF.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult sR = new StreamResult(System.out);
            transformer.transform(source, sR);

            File file2 = new File("orarendModifyI5XJTC.xml");
            OutputStream oPS = new FileOutputStream(file2);
            StreamResult sR2 = new StreamResult(oPS);
            transformer.transform(source, sR2);
            oPS.close();

        } catch (ParserConfigurationException | IOException | TransformerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
