package Assignment2;

import java.io.*;
import java.lang.annotation.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

// Tsai
public class Cashier extends Admin {
    // Constructor for establish a new user
    public Cashier(String username, String password, int authority) {
        super(username, password, authority);
    }

    // Constructor for read an existed user from database
    public Cashier(String username, String salt, String hashvalue, int authority, String[] last5) {
        super(username, salt, hashvalue, authority, last5);
    }

    // Modify / fill the cash availability
    // return -2 for wrong input, return -1 for successfully change
    @Generated
    public static int modifyCash(double value, int num) {
        File file = new File("src/database/cash_availability.json");
        String path = file.getAbsolutePath();
        String jsonInfo = JsonReader.getJsonInfo(path);
        Map<String, Integer> jsonToMap = JsonReader.getJsonToMap(jsonInfo);
        String valueToKey = "" + value;
        if (value >= 1) {
            valueToKey = (int) value + "";
        }
        if (jsonToMap.keySet().contains(valueToKey)) {
            jsonToMap.put(valueToKey, num);
        } else {
            return -2;
        }
        JsonReader.saveJsonInfo((jsonToMap + "").replace("=", ":"), path);
        return -1;
    }

    // Print the current changes for cashier
    @Generated
    public static Map<String, Integer> currentList() {
        File file = new File("src/database/cash_availability.json");
        String path = file.getAbsolutePath();
        String jsonInfo = JsonReader.getJsonInfo(path);
        Map<String, Integer> cashList = JsonReader.getJsonToMap(jsonInfo);
        return cashList;
    }

    // Return all the completed transactions through card or cash
    @Generated
    public static ArrayList<String> getPurchaseInfo() {
        File csv = new File("src/database/completed_Transaction.csv");
        csv.setReadable(true);
        csv.setWritable(true);
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            isr = new InputStreamReader(new FileInputStream(csv), "UTF-8");
            br = new BufferedReader(isr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String line = "";
        ArrayList<String> records = new ArrayList<>();
        try {
            while ((line = br.readLine()) != null) {
                records.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    @Documented
    public @interface Generated {
    }
}
