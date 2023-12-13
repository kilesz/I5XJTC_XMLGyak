package hu.saxI5XJTC;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxI5XJTC {

	public static void main(String[] args) {
		try {
			File file = new File("I5XJTC_kurzusfelvetel.xml");
			SAXParserFactory sPF = SAXParserFactory.newInstance();
            SAXParser sP = sPF.newSAXParser();
            SaxHandler sH = new SaxHandler();
            sP.parse(file, sH);					
        } catch (ParserConfigurationException | SAXException | IOException e) {
        	e.printStackTrace();
        }
	}
}
class SaxHandler extends DefaultHandler {
	private int indent = 0;
	
	private String formatAttributes(Attributes attributes) {
        int attributeLength = attributes.getLength();
        if (attributeLength == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(", {");
        for (int i = 0; i < attributeLength; i++) {
            sb.append(attributes.getLocalName(i) + "=" + attributes.getValue(i));
            if (i < attributeLength - 1) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    private void indent() {
        for (int i = 0; i < indent; i++) {
            System.out.print("  ");
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        indent++;
        indent();
        System.out.print(qName + formatAttributes(attributes) + " start\n");
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        indent();
        indent--;
        System.out.print(qName + " end\n");
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String chars = new String(ch, start, length).trim();
        if (!chars.isEmpty()) {
            indent++;
            indent();
            indent--;
            System.out.print(chars + "\n");
        }
    }
}
