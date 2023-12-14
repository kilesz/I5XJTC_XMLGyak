package i5xjtc;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;

public class JSONRead {

	public static void main(String[] args) {
		try {
            String jfile = "kurzusfelvetelI5XJTC.json";
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(new FileReader(jfile));

            if (obj instanceof JSONObject) {
                kiirasJsonObjektum((JSONObject) obj, 0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void kiirasJsonObjektum(JSONObject jsonObjectum, int koz) {
        Iterator<Map.Entry> iterator = jsonObjectum.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry mP = iterator.next();
            Object kulcs = mP.getKey();
            Object ertek = mP.getValue();

            kiirKoz(koz);
            System.out.print(kulcs + ": ");

            if (ertek instanceof JSONObject) {
                System.out.println();
                kiirasJsonObjektum((JSONObject) ertek, koz + 1);
            } else if (ertek instanceof JSONArray) {
                System.out.println();
                kiirJsonSor((JSONArray) ertek, koz + 1, "  " + "kurzus");
            } else {
                kiirKoz(koz);
                System.out.println(ertek);
            }
        }
    }

    private static void kiirJsonSor(JSONArray jsonSor, int koz, String prefix) {
        for (Object elem : jsonSor) {
            if (elem instanceof JSONObject) {
                System.out.println(prefix + ": ");
                kiirasJsonObjektum((JSONObject) elem, koz + 1);
            } else if (elem instanceof JSONArray) {
                System.out.println(prefix + ": ");
                kiirJsonSor((JSONArray) elem, koz + 1, prefix);
            } else {
                kiirKoz(koz);
                System.out.println(prefix + ": " + elem);
            }
        }
    }

    private static void kiirKoz(int koz) {
        for (int i = 0; i < koz; i++) {
            System.out.print("  ");
        }
    }

}


