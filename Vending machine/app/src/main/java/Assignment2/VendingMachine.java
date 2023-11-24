package Assignment2;

import java.io.*;
import java.lang.annotation.Documented;
//import java.security.DrbgParameters.Reseed;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class VendingMachine {
    // Cart is a hashmap stored the name of goods and their amount in stoke
    private HashMap<Goods, Integer> cart = new HashMap<Goods, Integer>();
    public List<Admin> users = new ArrayList<Admin>();
    public List<Goods> goods = new ArrayList<Goods>();

    private FileUtil fu_user = new FileUtil("src/database/admin.csv", true);
    private FileUtil fu_goods = new FileUtil("src/database/items.csv", false);

    public Admin currentuser;
    public HashMap<Goods, Integer> sold = new HashMap<Goods, Integer>();

    private Transaction currenTransction;
    public List<Transaction> transctionsList = new ArrayList<Transaction>();

    private String[] last5 = new String[5];

    // constructor
    public VendingMachine() {
        users = fu_user.loadUsers();
        // Set the hashmap for goods
        goods = fu_goods.loadGoods();
        // TO DO
        for (int i = 0; i < goods.size(); i++) {
            cart.put(goods.get(i), 0);
            sold.put(goods.get(i), 0);
        }
    }

    // Get goods instance by code
    public Goods getGoodsbyCode(int code) {
        for (int i = 0; i < goods.size(); i++) {
            if (goods.get(i).code == code) {
                return goods.get(i);
            }
        }
        return null;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /*
     * Return, 1: success, 0: username exists, -1: invalid authority number,
     * -2: you are not owner but you want to add new admin
     */
    @Generated
    public int Add_new(String username, String password, int authority) {

        // Username already existed
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                return -1;
            }
        }

        Admin newAdmin;
        /*
         * If authority is legal, initialize a specific instance and append it
         */
        if (authority == 0) {
            newAdmin = new Owner(username, password, authority);
            users.add(newAdmin);
        } else if (authority == 1) {
            newAdmin = new Seller(username, password, authority);
            users.add(newAdmin);
        } else if (authority == 2) {
            newAdmin = new Cashier(username, password, authority);
            users.add(newAdmin);
        } else if (authority == 3) {
            newAdmin = new Customer(username, password, authority);
            users.add(newAdmin);
        }

        // Authority is illegal
        else {
            return 0;
        }
        // Append this new admin into csv file
        fu_user.Add_new(newAdmin);
        return 1;
    }

    /*
     * Remove users from vendingmachine
     * Owner only! Owner is not allowed to remove other owners
     * 0: you are not an owner
     * 1: Success
     * -1: you are trying to remove another owner
     */
    @Generated
    public int remove(String username) {
        // Check authority of current user
        if (currentuser.getAuthority() != 0) {
            return 0;
        }

        // Find the user
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                // Case 1: this is not a owner's name
                if (users.get(i).getAuthority() != 0) {
                    // Remove it from RAM
                    users.remove(i);
                    fu_user.remove_user(username);
                    return 1;
                } else {
                    return -1;
                }
            }
        }

        return -2;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /*
     * Return, -1: wrong usage, -2: wrong password, -3: can not find this username
     * owner: 0; seller: 1; cashier: 2
     */
    @Generated
    public Admin Login(String username, String password) {
        Admin tmpUser;
        // Traverse users list to find the input username
        for (int i = 0; i < users.size(); i++) {
            tmpUser = users.get(i);
            if (tmpUser.getUsername().compareTo(username) == 0) {
                /*
                 * Generate a new hashvalue by input password, and salt
                 * Then compare it to instance tmpUser's hashvalue
                 */
                if (Encrypter.get_SHA_512_SecurePassword(password, tmpUser.getSalt())
                        .compareTo(tmpUser.getHasvalue()) == 0) {
                    // Set this admin as current user
                    currentuser = tmpUser;

                    return tmpUser;
                } else {
                    return null;
                }
            }
        }
        // Cannot get input usrname, return false
        return null;
    }

    @Generated
    public void logout() {
        this.currentuser = null;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Generated
    // Start a new transaction
    public void start() {
        currenTransction = new Transaction();
    }

    /*
     * Put any numbers of one good into/out of the cart
     * 0: Success; -1: this item is out of stoke
     * Tips:
     * amount > 0 : put something into cart
     * amount < 0 : put something out of cart
     */
    @Generated
    public int inOutCart(int code, int amount) {
        Goods goods = getGoodsbyCode(code);
        int amountInCart = cart.get(goods);
        // Still enough in stoke
        if (amountInCart + amount <= goods.quantity) {
            cart.put(goods, amountInCart + amount);
            return 0;
        } else {
            return -1;
        }
    }

    @Generated
    // This method will return the price of items in cart
    public double calculator() {
        double amount = 0;
        Goods item;
        for (int i = 0; i < goods.size(); i++) {
            item = goods.get(i);
            amount += cart.get(item) * item.price;
        }
        return amount;
    }

    // Return a List contain all the items in the cart (quantity > 0)
    @Generated
    public List<String> inCart() {
        List<String> goodsInCart = new ArrayList<String>();

        for (int i = 0; i < goods.size(); i++) {
            Goods item = goods.get(i);
            if (cart.get(item) > 0) {
                // Code, Name, Price, Quantity, Cost
                goodsInCart.add(
                        item.code + ", " + item.name + ", " + item.price + ", "
                                + cart.get(item) + ", " + Double.toString(cart.get(item) * item.price));
            }
        }

        return goodsInCart;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Payment method: Card
    // If card details match the record, it will return -1, if not return -2;
    @Generated
    public int CardCheckout(String name, String number) {
        int result = CardRegister.spendThroughCard(name, number);
        Double totalPrice = calculator();
        String sold_str = "";
        if (result == -1) {
            File transaction = new File("src/database/completed_Transaction.csv");
            String infoPath = transaction.getAbsolutePath();

            Set<Goods> goods = cart.keySet();
            ArrayList<String> items = new ArrayList<>();

            for (Goods item : goods) {
                if (cart.get(item) > 0) {
                    items.add(item.name);
                    sold_str = sold_str + "," + item.name;
                }
            }

            StringBuffer stringBuff = new StringBuffer();
            // stringBuff.append(dateFormat.format(date)+ ",");
            for (String msg : items) {
                stringBuff.append(msg).append("/");
            }
            stringBuff.append("," + totalPrice).append(",").append("0").append(",card");
            String info = stringBuff.toString();
            savePurchaseInfo(info, infoPath);
            // This String will record all the items sold in this transaction
            // String sold_str = "";

        }
        if (result == -2) {
            return -2;
        }

        // Update last 5 items
        List<String> newLs_ls = new ArrayList<String>();

        for (int i = 0; i < goods.size(); i++) {
            if (cart.get(goods.get(i)) > 0) {
                newLs_ls.add(Integer.toString(goods.get(i).code));

            }
        }

        String[] newLs = new String[newLs_ls.size()];
        newLs = newLs_ls.toArray(newLs);

        // Update the last-5-items
        if (!(currentuser == null)) {
            currentuser.last5 = Last5.update(currentuser.last5, newLs);
            fu_user.edit_last(currentuser, currentuser.last5);
        } else {
            last5 = Last5.update(last5, newLs);
        }

        // Finishe transaction
        currenTransction.finisheTs(sold_str, calculator(), calculator(), 0, true);
        transctionsList.add(currenTransction);
        currentuser = null;

        // Part 3: Edit amount
        for (int i = 0; i < goods.size(); i++) {
            if (cart.get(goods.get(i)) > 0) {
                // Edit quantity in sold map
                sold.put(goods.get(i), cart.get(goods.get(i)));
                // Edit quantity in database
                fu_goods.edit_amount(goods.get(i), goods.get(i).quantity - cart.get(goods.get(i)));
                // Edit quantity in RAM
                goods.get(i).editQuantity(-cart.get(goods.get(i)));
                // Add a new name into string
                sold_str = sold_str + goods.get(i).name + ", ";
            }
        }

        clearCart();

        // Finishe the transaction
        // sold_str = sold_str.substring(0, sold_str.length() - 3);
        // transctionsList.get(transctions.size() - 1).finisheTs(sold_str, calculator(),
        // calculator(), 0, false);

        return result;
    }

    @Generated
    // Payment method: Cash
    public Map<String, Integer> spendThroughCash(List<Double> cashList) {
        // Part 1: Pay
        Double totalPrice = calculator();
        Map<String, Integer> result = CashRegister.spendThroughCash(totalPrice, cashList);
        Double cash = calculator();
        String sold_str = "";
        Double insert = 0.0;
        for (Double d : cashList) {
            insert += d;
        }
        Double change = insert - cash;
        File transaction = new File("src/database/completed_Transaction.csv");
        String infoPath = transaction.getAbsolutePath();
        if (result != null) {
            Set<Goods> goods = cart.keySet();
            ArrayList<String> items = new ArrayList<>();
            for (Goods item : goods) {
                if (cart.get(item) > 0) {
                    items.add(item.name);
                    sold_str = sold_str + "," + item.name;
                }

            }
            StringBuffer stringBuff = new StringBuffer();
            for (String msg : items) {
                stringBuff.append(msg).append("/");
            }
            stringBuff.append("," + totalPrice).append(",").append(change).append(",cash");
            String info = stringBuff.toString();
            savePurchaseInfo(info, infoPath);
        }

        // Error, there is not enough change. Transaction's status: -3
        if (result == null) {
            transctionsList.get(transctionsList.size() - 1).cancellTs(-3);
            return null;
        }

        // Part 2: Update last 5 items
        List<String> newLs_ls = new ArrayList<String>();

        for (int i = 0; i < goods.size(); i++) {
            if (cart.get(goods.get(i)) > 0) {
                newLs_ls.add(Integer.toString(goods.get(i).code));
                sold_str = sold_str + goods.get(i).name;
            }
        }

        String[] newLs = new String[newLs_ls.size()];
        newLs = newLs_ls.toArray(newLs);

        // Update the last-5-items
        if (!(currentuser == null)) {
            currentuser.last5 = Last5.update(currentuser.last5, newLs);
            fu_user.edit_last(currentuser, currentuser.last5);
        } else {
            last5 = Last5.update(last5, newLs);
        }

        // Finishe transaction
        currenTransction.finisheTs(sold_str, insert, calculator(), change, false);
        transctionsList.add(currenTransction);
        currentuser = null;

        // Part 3: Edit amount
        for (int i = 0; i < goods.size(); i++) {
            if (cart.get(goods.get(i)) > 0) {
                // Edit quantity in database
                fu_goods.edit_amount(goods.get(i), goods.get(i).quantity - cart.get(goods.get(i)));
                // Edit quantity in RAM
                goods.get(i).editQuantity(-cart.get(goods.get(i)));
                sold_str = sold_str + goods.get(i).name + ", ";
            }
        }

        clearCart();

        // Finishe the transaction
        // sold_str = sold_str.substring(0, sold_str.length() - 3);
        // transctionsList.get(transctions.size() - 1).finisheTs(sold_str, cash,
        // calculator(), cash - calculator(), true);

        return result;
    }

    // Clear everything in the cart
    public void clearCart() {
        for (int i = 0; i < goods.size(); i++) {
            Goods item = goods.get(i);

            cart.put(item, 0);
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // List all the goods in vendingmachine: code, Name, price, quantity
    public String[] inVendingmachine() {
        String[] str_arr = new String[goods.size()];
        for (int i = 0; i < goods.size(); i++) {
            // Code, Name, Price, Quantity
            str_arr[i] = goods.get(i).code + ", " + goods.get(i).name + ", " + goods.get(i).price + ", "
                    + goods.get(i).quantity;
        }

        return str_arr;
    }

    @Generated
    // Get all goods in this category
    public List<Goods> getbyCategory(String str) {
        Category c;
        if (str.equals("Drink")) {
            c = Category.DRINK;
        } else if (str.equals("Chocolates")) {
            c = Category.CHOCOLATES;
        } else if (str.equals("Chips")) {
            c = Category.CHIPS;
        } else if (str.equals("Candies")) {
            c = Category.CANDIES;
        } else {
            return null;
        }
        List<Goods> ls = new ArrayList<Goods>();

        for (int i = 0; i < goods.size(); i++) {
            // Is that correct using == to compare two enums?
            if (goods.get(i).getType() == c) {
                ls.add(goods.get(i));
            }
        }

        return ls;
    }

    @Generated
    // Last 5 items
    public String[] lastitems() {
        if (currentuser == null) {
            return last5;
        } else {
            return currentuser.getLast();
        }
    }

    public HashMap<Goods, Integer> getsold() {
        return sold;
    }

    // Append a new transaction instance.
    // Please use this method when someone start to shopping
    @Generated
    public void newTransaction() {
        this.transctionsList.add(new Transaction());
    }

    // Reasons for cancelling the transaction:
    // timeout = -1; user canncelled = -2; change not available = -3; other = -4
    @Generated
    public void canncelTs(int reason) {
        transctionsList.add(currenTransction);
        transctionsList.get(transctionsList.size() - 1).cancellTs(reason);
        clearCart();
        currentuser = null;
    }

    @Documented
    public @interface Generated {
    }

    @Generated
    public boolean savePurchaseInfo(String info, String path) {
        ArrayList<String> purchaseInfo = getPurchaseInfo(path);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        purchaseInfo.add(dateFormat.format(date) + "," + info);
        BufferedWriter bufferedWriter = null;
        File file = new File(path);
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
            for (String arr : purchaseInfo) {
                bufferedWriter.write(arr + "\n");
            }
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Generated
    public ArrayList<String> getPurchaseInfo(String filePath) {
        File csv = new File(filePath);
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
}
