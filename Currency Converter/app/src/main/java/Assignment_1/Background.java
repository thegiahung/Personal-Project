package Assignment_1;

import java.util.Date;
import java.util.List;

import java.io.IOException;
import java.text.SimpleDateFormat;
// import java.text.NumberFormat.Style;
import java.util.ArrayList;
import java.util.Arrays;

public class Background {
    // Contain all the Currencies instance in database
    public List<Currency> allCurrencies = new ArrayList<Currency>();

    // Contain all the 4 popular currencies' name
    private String[] popular_name_arr = new String[4];

    // An arraylist store all the User instance
    public List<User> Users_ls = new ArrayList<User>();

    private User current_user;
    private FileUtil fileUtil;

    private java.text.SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD ");

    // Gain currency instance by a String
    private Currency getCurrency(String currency_name) {
        Currency currency;
        // Find currency
        for (int i = 0; i < allCurrencies.size(); i++) {
            if (this.allCurrencies.get(i).getName().compareTo(currency_name) == 0) {
                currency = this.allCurrencies.get(i);
                return currency;
            }
        }
        return null;
    }

    /*
     * Search the current exchange rate between 2 currencies in database
     * Return = currency2 / currency1
     * Return false if can not find the currency
     */
    private double current_rate(String currency1_name, String currency2_name) {

        Currency currency1 = getCurrency(currency1_name);
        Currency currency2 = getCurrency(currency2_name);

        // If can not find currency1 or currency2, return false
        if ((currency1 == null) | (currency2 == null)) {
            return -1;
        }

        // Other errors
        try {
            return currency1.getCurrentRate(currency2, true);
        } catch (Exception e) {
            return -1;
        }
    }

    /*
     * Return increase or decrease trend
     * Increasing: I
     * Decreasing: D
     * No change: N
     * Error:
     */
    private String current_upDown(String currency1_name, String currency2_name) {
        Currency currency1 = getCurrency(currency1_name);
        Currency currency2 = getCurrency(currency2_name);

        // If can not find currency1 or currency2, return false
        if ((currency1 == null) | (currency2 == null)) {
            return "  ";
        }

        // Other errors
        try {
            double current = currency1.getCurrentRate(currency2, true);
            double previous = currency1.getCurrentRate(currency2, false);

            // Compare exhange rate in 2 days
            if (current > previous) {
                return "I";
            } else if (current < previous) {
                return "D";
            } else {
                return " ";
            }

        } catch (Exception e) {
            return "  ";
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Normal users function 1 : Display most popular 4 currencies

    // Return a 4x4 two dimensional array
    public String[][] popular() {

        String[][] table = new String[5][9];

        for (int i = 1; i < 5; i++) {
            table[0][2 * i - 1] = this.popular_name_arr[i - 1];
            table[i][0] = this.popular_name_arr[i - 1];
        }

        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                if (i == j) {
                    continue;
                } else {
                    // Using function toString() in class Double convert the number to a string
                    Double fill = current_rate(popular_name_arr[i - 1], popular_name_arr[j - 1]);
                    table[i][2 * j - 1] = fill.toString();
                    if (table[i][2 * j - 1].length() > 7) {
                        table[i][2 * j - 1] = table[i][2 * j - 1].substring(0, 7);
                    }

                    table[i][2 * j] = current_upDown(popular_name_arr[i - 1], popular_name_arr[j - 1]);
                }
            }
        }

        return table;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Normal users function 2 : Display the history of rate between 2 currencies

    // Return history of exchange rate in date order
    public double[] history_rate(String currency_symbol_1, String currency_symbol_2, String start, String end) {
        double[] h_rate;
        // Start date should be earlier than end date
        if (start.compareTo(end) == 0) {
            return null;
        }

        Currency currency1 = getCurrency(currency_symbol_1);
        Currency currency2 = getCurrency(currency_symbol_2);

        // If can not find currency1 or currency2, return false
        if ((currency1 == null) | (currency2 == null)) {
            return null;
        }

        h_rate = new double[currency1.rate.get(currency_symbol_2).size()];

        // Fill the array using data store in currceny1's hashmap
        for (int i = 0; i < h_rate.length; i++) {
            // If this rate inside the interval
            if (currency1.rate.get(currency_symbol_2).get(i).date.compareTo(end) < 0
                    && currency1.rate.get(currency_symbol_2).get(i).date.compareTo(start) > 0) {
                h_rate[i] = currency1.rate.get(currency_symbol_2).get(i).exchange_rate;
            }

        }

        return h_rate;
    }

    /*
     * Return history of exchange rate modified date in date order
     * Date format: YYYY-MM-DD
     */
    public String[] history_date(String currency_symbol_1, String currency_symbol_2, String start, String end) {
        String[] h_date;

        if (start.compareTo(end) == 0) {
            return null;
        }

        Currency currency1 = getCurrency(currency_symbol_1);
        Currency currency2 = getCurrency(currency_symbol_2);

        // If can not find currency1 or currency2, return false
        if ((currency1 == null) | (currency2 == null)) {
            return null;
        }

        h_date = new String[currency1.rate.get(currency_symbol_2).size()];

        // Fill the array using data store in currceny1's hashmap
        for (int i = 0; i < h_date.length; i++) {
            // If this rate inside the interval
            if (currency1.rate.get(currency_symbol_2).get(i).date.compareTo(end) < 0
                    && currency1.rate.get(currency_symbol_2).get(i).date.compareTo(start) > 0) {
                h_date[i] = currency1.rate.get(currency_symbol_2).get(i).date;
            }

        }

        return h_date;
    }

    // Average rate in the period
    public double average(double[] h_date) {
        double mean = 0;

        for (int i = 0; i < h_date.length; i++) {
            mean += h_date[i];
        }

        mean = mean / h_date.length;

        return mean;
    }

    public double max(double[] h_date) {
        double max = 0;
        Arrays.sort(h_date);
        max = h_date[h_date.length - 1];
        return max;
    }

    public double min(double[] h_date) {
        double min = 0;
        Arrays.sort(h_date);
        min = h_date[0];
        return min;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /*
     * Normal Users function 3: calculator
     * Currency2 is destination currency
     */
    public double calculator(String currency1_name, String currency2_name, double amount) {
        double rate = current_rate(currency1_name, currency2_name);
        return amount * rate;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /*
     * Admin function 1 : Add new currency type
     * currency1: the new currency type
     * Ture: success add new currency type
     * False: Invalid input
     */
    public boolean add_new(String currency1) throws IOException {
        for (int i = 0; i < allCurrencies.size(); i++) {
            // This currency already exists
            if (allCurrencies.get(i).getName().compareTo(currency1) == 0) {
                return false;
            }
        }

        fileUtil.addNewCurrency(currency1);
        return true;
    }

    // Admin function 2 : Modify exchange rate between 2 currencies
    public boolean new_rate(String currency_symbol_1, String currency_symbol_2, double new_rate) throws IOException {

        // If can not find currency1 or currency2, return false
        if ((getCurrency(currency_symbol_1) == null) | (getCurrency(currency_symbol_2) == null)) {
            return false;
        }

        getCurrency(currency_symbol_1).setnewRate(new_rate, getCurrency(currency_symbol_2));

        System.out.println(fileUtil == null);
        fileUtil.WriteIn(
                new Exchange(formatter.format(new Date()), currency_symbol_1, currency_symbol_2, new_rate));

        return true;
    }

    /*
     * Admin function 3: Change popular list
     * Currency1: incoming
     * Currency2: current
     * return ture: success
     * return false: invalid input
     */
    public boolean change_popular(String currency1, String currency2) {
        boolean isPopular2 = false;
        boolean exist1 = false;
        // Make suer currency1 not in list
        for (int i = 0; i < popular_name_arr.length; i++) {
            if (popular_name_arr[i].compareTo(currency1) == 0) {
                return false;
            }
        }

        // Currency2 in the list
        for (int i = 0; i < popular_name_arr.length; i++) {
            if (popular_name_arr[i].compareTo(currency2) == 0) {
                isPopular2 = true;
                break;
            }
        }

        // Currency 1 exist in allCurrencies list
        for (int i = 0; i < allCurrencies.size(); i++) {
            if (currency1.compareTo(allCurrencies.get(i).getName()) == 0) {
                exist1 = true;
                break;
            }
        }

        // Case 1: currency 2 is not popular currency | currency 1 do not exists
        if (!isPopular2 | !exist1) {
            return false;
        }
        // Case 2: Both currency 1 and 2 are valid input
        else {
            // Using currency 1 replace currency 2 in popular list
            for (int i = 0; i < popular_name_arr.length; i++) {
                if (popular_name_arr[i].compareTo(currency2) == 0) {
                    popular_name_arr[i] = currency1;
                    break;
                }
            }

            // Reset attribute: currency.isPopular
            getCurrency(currency1).setIsPopular(true);
            getCurrency(currency2).setIsPopular(false);

            fileUtil.changePopular(currency1, currency2);
        }
        return true;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /*
     * ture -> sign up new user correctly
     * false -> username existed
     */
    public boolean new_user(String usrname, String password, boolean authority) {
        for (int i = 0; i < Users_ls.size(); i++) {
            // Username already existed
            if (Users_ls.get(i).getName().compareTo(usrname) == 0) {
                return false;
            }
        }

        // Append new User instance in User list
        this.Users_ls.add(new User(usrname, password, authority));

        // Using fileUtil write the information of new user into database
        fileUtil.writeUserInFile(Users_ls.get(Users_ls.size() - 1));

        return true;
    }

    // Return 1 for normal user, return -1 for admin, return 0 for wrong
    // password/username
    public int login(String usrname, String password) {
        if (usrname == null || usrname == "" || password == null || password == "") {
            return 0;
        }
        for (int i = 0; i < Users_ls.size(); i++) {
            // Find the username in list and password is correct
            if (Users_ls.get(i).getName().compareTo(usrname) == 0
                    && Users_ls.get(i).getPsw().compareTo(password) == 0) {
                current_user = Users_ls.get(i);
                if (current_user.getauthority()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }
        // Incorrect password or username
        return 0;
    }

    public void logout() {
        current_user = null;
    }

    public boolean is_admin() {
        return current_user.getauthority();
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public Background() {
        fileUtil = new FileUtil();
        // Load users' information
        this.Users_ls = fileUtil.readUserFromFile();
        System.out.println(this.Users_ls);
        fileUtil.ReadAllInfo();
        List<String> ls = fileUtil.getAllCurrencies(true);
        // Load popular_name_arr
        this.popular_name_arr = fileUtil.getAllCurrencies(false).toArray(new String[4]);

        // Load allCurrencies part 1: initialize instances
        for (int i = 0; i < ls.size(); i++) {
            for (int j = 0; j < 4; j++) {
                if (ls.get(i).compareTo(popular_name_arr[j]) == 0) {
                    this.allCurrencies.add(new Currency(ls.get(i), true));
                    break;
                } else {
                    this.allCurrencies.add(new Currency(ls.get(i), false));
                    break;
                }
            }
        }

        // Load allCurrencies part 2: build hasmap rate of each instance
        for (int i = 0; i < fileUtil.getListLength(); i++) {
            Exchange tmp = fileUtil.getAllHistory().get(i);
            try {
                getCurrency(tmp.getCurrency_1()).readRate(tmp.getExchangeRate(), getCurrency(tmp.getCurrency_2()),
                        tmp.getDate());
            }
            // If the key string (currency name) of rate hashmap do not exist yet
            catch (NullPointerException e) {
                getCurrency(tmp.getCurrency_1()).rate.put(tmp.getCurrency_2(), new ArrayList<Rate_date>());
                getCurrency(tmp.getCurrency_2()).rate.put(tmp.getCurrency_1(), new ArrayList<Rate_date>());

                getCurrency(tmp.getCurrency_1()).readRate(tmp.getExchangeRate(), getCurrency(tmp.getCurrency_2()),
                        tmp.getDate());
            }

        }
    }
}