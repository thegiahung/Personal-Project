package Assignment2;

import java.io.*;
import java.lang.annotation.Documented;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

// Shaun
public class CashRegister {

    @Generated
    private static String[] sortMapKey() {

        String values[] = { "100", "50", "20", "10", "5", "2", "1", "0.5", "0.2", "0.1" };
        return values;
    }

    @Generated
    private static Map<String, Integer> getChangeMap(Map<String, Integer> jsonToMap, double change) {
        Map<String, Integer> resultMap = new HashMap<>();
        String[] strings = sortMapKey();
        for (String key : strings) {
            resultMap.put(key, 0);
            Double keyValue = Double.parseDouble(key.replace("\"", ""));

            while (keyValue <= change && jsonToMap.get(key) > 0) {
                change = change - keyValue;
                BigDecimal two = new BigDecimal(change);
                change = two.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                resultMap.put(key, resultMap.get(key) + 1);
                jsonToMap.put(key, jsonToMap.get(key) - 1);
            }
        }
        String jsonInfo = jsonToMap + "";
        jsonInfo = jsonInfo.replace("=", ":");
        File file = new File("src/database/cash_availability.json");
        String path = file.getAbsolutePath();

        JsonReader.saveJsonInfo(jsonInfo, path);
        if (change == 0) {
            resultMap.put("0.001", 0);
        } else {
            resultMap.put("0.001", 1);
        }
        return resultMap;
    }

    // Return all the changes for customers
    // If the cash transaction successful, it will return the changes(finalMap)
    // If the cash in the vending machine can't complete cash return, it will return
    // null
    @Generated
    public static Map<String, Integer> spendThroughCash(Double totalPrice, List<Double> cashList) {

        // changeCash(double value, int num)
        File file = new File("src/database/cash_availability.json");
        String cashPath = file.getAbsolutePath();
        String jsonInfo = JsonReader.getJsonInfo(cashPath);
        Map<String, Integer> jsonToMap = JsonReader.getJsonToMap(jsonInfo);
        Double cash = 0.0;
        for (Double d : cashList) {
            cash += d;
            String valueToKey = "" + d;
            if (d >= 1) {
                valueToKey = d.intValue() + "";
            }
            if (jsonToMap.keySet().contains(valueToKey)) {
                Cashier.modifyCash(Double.parseDouble(valueToKey), jsonToMap.get(valueToKey) + 1);
                jsonToMap = JsonReader.getJsonToMap(JsonReader.getJsonInfo(cashPath));
            } else {
                return null;
            }
        }
        double changes = cash - totalPrice;
        if (changes < 0) {
            // Transaction failed, cash availability unchanged
            JsonReader.saveJsonInfo(jsonInfo, cashPath);
            return null;
        }
        Map<String, Integer> resultMap = getChangeMap(jsonToMap, changes);
        Map<String, Integer> finalMap = new HashMap<>();
        String[] strings = sortMapKey();
        for (int i = 0; i < strings.length; i++) {
            if (resultMap.get(strings[i]) != 0) {
                finalMap.put(strings[i], resultMap.get(strings[i]));
            }
        }
        if (resultMap.get("0.001") == 0) {
            return finalMap;
        } else {
            // Transaction failed, cash availability unchanged
            JsonReader.saveJsonInfo(jsonInfo, cashPath);
        }
        return null;
    }

    @Documented
    public @interface Generated {
    }
}
