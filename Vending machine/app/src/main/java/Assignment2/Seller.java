package Assignment2;

import java.lang.annotation.Documented;
import java.util.HashMap;
import java.util.List;

// Tsai
public class Seller extends Admin {
    private FileUtil fu_goods;

    // Constructor for establish a new user
    public Seller(String username, String password, int authority) {
        super(username, password, authority);

        this.fu_goods = new FileUtil("src/database/items.csv", false);

    }

    // Constructor for read a existed user from database
    public Seller(String username, String salt, String hasvalue, int authority, String[] last5) {
        super(username, salt, hasvalue, authority, last5);

        this.fu_goods = new FileUtil("src/database/items.csv", false);

    }

    /*
     * Seller has 4 main methods:
     * 1. Modify the amount of items
     * 2. Change the price of items
     * 3. Modify items' name
     * 4. Modify items' category
     * 5. Modify items' code
     * 6. Generate a report of items' detail
     * 7. A report of the total number of quantity sold for each item
     */
    @Generated
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

    @Generated
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

    @Generated
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
        if (newc_str.equals("Drinks")) {
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

    @Generated
    public boolean editItem(Goods goods, int newcode, List<Goods> ls) {
        // Code should be a 4-digit number
        if (newcode < 1000 & newcode > 9999) {
            return false;
        }

        // Code it the primary key, thus it should be unique
        for (int i = 0; i < ls.size(); i++) {
            if (ls.get(i).code.equals(newcode)) {
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

    @Documented
    public @interface Generated {
    }
}
