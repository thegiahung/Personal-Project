package Assignment2;

import java.lang.annotation.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

// Tsai
public class Owner extends Admin {
    private FileUtil fu_goods;

    // Constructor for establish a new user
    public Owner(String username, String password, int authority) {
        super(username, password, authority);
        this.fu_goods = new FileUtil("src/database/items.csv", false);
    }

    // Constructor for read a existed user from database
    public Owner(String username, String salt, String hasvalue, int authority, String[] last5) {
        super(username, salt, hasvalue, authority, last5);
        this.fu_goods = new FileUtil("src/database/items.csv", false);
    }

    /*
     * Owner has methods
     * 1. Modify the amount of items
     * 2. Change the price of items
     * 3. Modify items' name
     * 4. Modify items' category
     * 5. Modify items' code
     * 6. Add new users
     * 7. Remove exist users
     * 8. Report 1:
     * 9. Report 2:
     * 
     * ...
     * A few methods same as class Cashier
     * ...
     */

    public boolean editAmount(Goods goods, Integer newAmount) {
        // Amount should be greater -1 and less than 16
        if (newAmount < 0 & newAmount > 15) {
            return false;
        }

        if (fu_goods.edit_amount(goods, newAmount)) {
            goods.quantity = newAmount;
            return true;
        } else {
            return false;
        }
    }

    public boolean editPrice(Goods goods, Double newPrice) {
        // Price should be greater than 0
        if (newPrice <= 0) {
            return false;
        }

        if (fu_goods.edit_price(goods, newPrice)) {
            goods.price = newPrice;
            return true;
        } else {
            return false;
        }
    }

    public boolean editName(Goods goods, String newn) {
        if (fu_goods.edit_name(goods, newn)) {
            goods.name = newn;
            return true;
        } else {
            return false;
        }
    }

    @Generated // This code is to ignore in the jacocoTestReport
    public boolean editCategory(Goods goods, String newc_str) {
        Category newc;
        if (newc_str.equals("Drink")) {
            newc = Category.DRINK;
        } else if (newc_str.equals("Chocolates")) {
            newc = Category.CHOCOLATES;
        } else if (newc_str.equals("Chips")) {
            newc = Category.CHIPS;
        } else if (newc_str.equals("Candies")) {
            newc = Category.CANDIES;
        } else {
            return false;
        }

        if (fu_goods.edit_category(goods, newc)) {
            goods.type = newc;
            return true;
        } else {
            return false;
        }
    }

    @Generated // This code is to ignore in the jacocoTestReport
    public boolean editItem(Goods goods, int newcode, List<Goods> ls) {
        // Code should be a 4-digit number
        if (newcode < 1000 & newcode > 9999) {
            return false;
        }

        // Code it the primary key, thus it should be unique
        for (int i = 0; i < ls.size(); i++) {
            if (ls.get(i).name.equals(Integer.toString(newcode))) {
                return false;
            }
        }

        if (fu_goods.edit_code(goods, newcode)) {
            goods.code = newcode;
            return true;
        } else {
            return false;
        }
    }

    @Generated
    // e.g. "1001,Mars,2.5,8,Chips"
    public boolean report1(List<Goods> goods) {
        String[] info = new String[goods.size()];

        for (int i = 0; i < goods.size(); i++) {
            // Code, Name, Quantity, Price, Category
            info[i] = Integer.toString(goods.get(i).code) + "," + goods.get(i).name + ","
                    + Double.toString(goods.get(i).price)
                    + "," + Integer.toString(goods.get(i).quantity) + "," + goods.get(i).getType().getSize();
        }
        return ReportWriter.arr_csv(info, "src/vmreports/sellerReport1.csv");
    }

    @Generated
    // e.g. "1001,Mineral Water,12", "1002, Mars, 1", etc.
    public boolean report2(List<Goods> goods, HashMap<Goods, Integer> sold) {
        String[] info = new String[goods.size()];

        for (int i = 0; i < goods.size(); i++) {
            info[i] = Integer.toString(goods.get(i).code) + "," + goods.get(i).name + ","
                    + Integer.toString(sold.get(goods.get(i)));
        }

        return ReportWriter.arr_csv(info, "src/vmreports/sellerReport2.csv");
    }

    /*
     * Go VendingMachine class to find the methods add new admina and remove admin
     */

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // modify / fill cash availability
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

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Generate Amdin report. This method will return a csv file in the reports/
    @Generated // This code is to ignore in the jacocoTestReport
    public boolean adminReport(List<Admin> ls) {
        // This array represents the lines in the final csv file
        String[] report = new String[ls.size()];

        // User name, role
        for (int i = 0; i < ls.size(); i++) {
            Admin tmp = ls.get(i);
            String role;
            if (tmp.getAuthority() == 0) {
                role = "Owner";
            } else if (tmp.getAuthority() == 1) {
                role = "Seller";
            } else if (tmp.getAuthority() == 2) {
                role = "Cashier";
            } else if (tmp.getAuthority() == 3) {
                role = "Customer";
            } else {
                return false;
            }
            // Fill array
            report[i] = tmp.getUsername() + "," + role;
        }

        return ReportWriter.arr_csv(report, "src/vmreports/ownerReport1.csv");
    }

    @Generated // This code is to ignore in the jacocoTestReport
    // Report 2
    // Format: Date&Time, reason
    public boolean cancelledTransctionReport(List<Transaction> ls) {
        int amount = 0;
        for (int i = 0; i < ls.size(); i++) {
            if (ls.get(i).getStatus() < 0) {
                amount += 1;
            }
        }
        String[] report = new String[amount];

        int j = 0;

        for (int i = 0; i < ls.size(); i++) {
            if (ls.get(i).getStatus() < 0) {
                // timeout = -1; user canncelled = -2; change not available = -3; other = -4
                String reason;

                if (ls.get(i).getStatus() == -1) {
                    reason = "Timeout";
                } else if (ls.get(i).getStatus() == -2) {
                    reason = "User canncelled";
                } else if (ls.get(i).getStatus() == -3) {
                    reason = "Change not available";
                } else {
                    reason = "Other";
                }
                report[j] = ls.get(i).getDate_time() + ", " + reason;
                i += 1;
            }
        }

        return ReportWriter.arr_csv(report, "src/vmreports/ownerReport2.csv");
    }

    @Documented
    public @interface Generated {
    }
}
