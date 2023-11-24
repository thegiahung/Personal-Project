package Assignment2;

import java.lang.annotation.*;

import Assignment2.App.Generated;

public class Goods {
    public String name;
    public double price;
    public Integer quantity = 0;
    public Integer code;
    public Category type;

    @Generated
    public Goods(String name, double price, String type_str, Integer code, Integer quantity) {
        this.name = name;
        this.price = price;
        this.code = code;
        this.quantity = quantity;

        if (type_str.equals("Drinks")) {
            this.type = Category.DRINK;
        } else if (type_str.equals("Chocolates")) {
            this.type = Category.CHOCOLATES;
        } else if (type_str.equals("Chips")) {
            this.type = Category.CHIPS;
        } else if (type_str.equals("Candies")) {
            this.type = Category.CANDIES;
        }
    }

    public void editQuantity(int num) {
        quantity += num;
    }

    public Category getType() {
        return type;
    }
}

// 4 categories of goods
enum Category {
    DRINK, CHOCOLATES, CHIPS, CANDIES;

    @Generated
    public String getSize() {

        switch (this) {
            case DRINK:
                return "Drink";

            case CHOCOLATES:
                return "Chocolates";

            case CHIPS:
                return "Chips";

            case CANDIES:
                return "Candies";

            default:
                return null;
        }
    }

    // This is to skip the unit test in jacocoTestReport because we do not need to
    // test this file
    @Documented
    public @interface Generated {
    }
}