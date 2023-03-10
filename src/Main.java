import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        System.out.println("Hello world!");

        //skapa ett JSON objekt
        JSONObject jsonOb = new JSONObject();

        //spara värden i JSON object
        jsonOb.put("name", "Sebastian");
        jsonOb.put("age", 22);

        //skriva ut värden
        //System.out.println("mitt namn är: " + jsonOb.get("name"));
        //System.out.println("mitt ålder är: " + jsonOb.get("age"));
        convert(jsonOb);

        Object o = new JSONParser().parse(new FileReader("data.json"));

        JSONObject jsonData = (JSONObject) o;

        JSONObject person = (JSONObject) jsonData.get("p1");

        String name = (String) person.get("name");
        System.out.println("P1 Name :" + name);
        System.out.println("----------------------------------------------------");
        fetchJsonFromFile();
        fetchJSONFromApi();
    }

    static void fetchJsonFromFile() throws IOException, ParseException {
        String filePath = "data.json";

        //hämta data från JSON fil
        JSONObject fetchData = (JSONObject) new JSONParser().parse(new FileReader(filePath));

        //konvertera data till ett JSONObject
        JSONObject p1 = (JSONObject) fetchData.get("p1");
        JSONObject p2 = (JSONObject) fetchData.get("p2");

        //hämta och srkia ut data
        String nameP1 = p1.get("name").toString(), nameP2 = p2.get("name").toString();
        int ageP1 = Integer.parseInt(p1.get("age").toString()), ageP2 = Integer.parseInt(p2.get("age").toString());

        System.out.println("Mitt namn är " + nameP1 + " jag är " + ageP1 + " år");
        System.out.println("Mitt namn är " + nameP2 + " jag är " + ageP2 + " år");
    }
    public static void convert(JSONObject ob) {
        System.out.println("name: " + ob.get("name") + "\n" + "age: " + ob.get("age"));
    }

    static void fetchJSONFromApi() throws IOException, ParseException {
        //spara url till api
        URL url = new URL("https://api.wheretheiss.at/v1/satellites/?id=25544");

        //sätta upp HTTP request
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        if(conn.getResponseCode() == 200) {
            System.out.println("Koppling lyckades ");

            //skapa StrBuilder och scan object
            StringBuilder strData = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());

            //
            while (scanner.hasNext()) {
                strData.append(scanner.nextLine());
            }
            //stäng kopplingen när den har läst klart
            scanner.close();

            //skapa JSONObject av fetched data
            JSONObject dataObject = (JSONObject) new JSONParser().parse(String.valueOf(strData));

            System.out.println(dataObject.values());
            //loop through both key and value
            for(Object key: dataObject.keySet()) {
                Object value = dataObject.get(key);
                System.out.println(key + " = " + value);

            }
//            System.out.println(dataObject.get("latitude"));
//            System.out.println(dataObject.get("name"));
//            System.out.println(dataObject.get("timestamp"));
        } else {
            System.out.println("koppling misslyckades");
        }
    }
}
