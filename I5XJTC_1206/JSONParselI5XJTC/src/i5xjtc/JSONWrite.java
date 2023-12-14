package i5xjtc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONWrite {

	public static void main(String[] args) {
		try {
            FileReader fR = new FileReader("kurzusfelvetelI5XJTC.json");
            BufferedReader bR = new BufferedReader(fR);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            Object object = gson.fromJson(bR, Object.class);

            FileWriter fW = new FileWriter("kurzusfelvetelI5XJTC_1.json");
            BufferedWriter bW = new BufferedWriter(fW);

            gson.toJson(object, bW);
            bW.flush();

            fR.close();
            bR.close();
            fW.close();
            bW.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

	}

}
