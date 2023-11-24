package Assignment_1;

import java.util.Scanner;

public class ABCSystem {

    String Current_user;
    Background bg = new Background();

    public ABCSystem() {
        String Current_user = null;
        // Scanner scanner = new Scanner(System.in);
    }

    private String retain_decimal(int decimal, Double value) {
        return String.format("%." + String.valueOf(decimal) + "f", value).toString();
    }

    // easier printer
    public static void show(String s) {
        System.out.println(s);
    }

    // clean up the terminal output
    public void clean() {
        show("\033[H\033[2J");
    }

    // close the whole process
    private void exit() {
        System.exit(0);
    }

    public void login_loop() {
        try {
            show("----------Welcom to Currency System----------");
            show("          Type number for operation          ");
            show("          1. Log in                          ");
            show("          2. Sign up                         ");
            show("          3. Exit                            ");
            show("Next step: ");
            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();
            show(choice);

            show("choice working");
            switch (choice) {
                case "1":
                    show("Username: ");
                    String username = scanner.nextLine();
                    show("Password: ");
                    String pwd = scanner.nextLine();
                    // show(username + "///" + pwd);

                    // To login method
                    // If true, check the access, break and come to next step; else invalid and
                    // again.
                    if (bg.login(username, pwd) == 1) {
                        Current_user = "user";
                    } else if (bg.login(username, pwd) == -1) {
                        Current_user = "admin";
                    } else {
                        show("Invalid username or password, please try again.");
                    }
                    break;

                case "2":
                    show("Username: ");
                    String new_username = scanner.nextLine();
                    show("Password: ");
                    String new_pwd = scanner.nextLine();
                    show("Is this an admin account?(Y/N)");
                    String authority = scanner.nextLine();
                    boolean authority_b = false;
                    if (authority.equals("Y")) {
                        authority_b = true;
                    }
                    if (authority.equals("N")) {
                        authority_b = false;
                    } else {
                        show("Please enter a valid type(Y/N).");

                        break;
                    }

                    // To sign up method
                    // should check if this user already exist
                    // show(new_username + new_pwd);
                    if (bg.new_user(new_username, new_pwd, authority_b)) {
                        show("Successfully created. Now you can login with this account.");
                    } else {
                        show("This account already exists. Please try another username or login directly.");
                    }
                    break;

                case "3":
                    show("See you.");
                    exit();
                    break;

                default:
                    clean();
                    show("Invalid number for input. Please enter a new one.");
                    break;
            }

            // scanner.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            show("error!");
        }
    }

    public void user_system_loop() {
        // After login to the System
        try {
            show("---------------Welcom to Currency System---------------");
            show("               Type number for operation               ");
            show("               1. Show pupolar currencies              ");
            show("               2. Currency converter                   ");
            show("               3. Search for currency history          ");
            show("               4. Exit                                 ");
            show("Next step: ");

            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();
            show(choice);
            show("choice working");
            switch (choice) {
                case "1":
                    // To show 4*4 table include popular currency
                    // show(Background.popular());
                    String[][] output_table;
                    output_table = bg.popular();
                    for (int i = 0; i < 5; i++) {
                        String output = "";
                        for (int j = 0; j < 9; j++) {
                            if (output_table[i][j] == null) {
                                output = output + "        ";

                            } else {
                                output = output + String.format("%-8s", output_table[i][j]);
                            }

                        }
                        show("----------------------------------------------------------------------");
                        show(output);
                    }
                    break;

                case "2":
                    // To convert between 2 currency
                    // Format be like 100 AUD USD
                    show("Please enter your amount for convertion: ");
                    String amount = scanner.nextLine();
                    show("Please enter your original currency: ");
                    String from = scanner.nextLine();
                    show("Please enter your target currency: ");
                    String to = scanner.nextLine();
                    // After convertion and show
                    Double result = bg.calculator(from, to, Double.parseDouble(amount));

                    if (Double.parseDouble(amount) <= 0) {
                        show("Please enter a valid amount. Amount could not be negative.");
                    } else if (amount == null || from == null || to == null || amount == "" || from == "" || to == "") {
                        show("Invalid input. Input could not be blank.");
                    } else if (result < 0) {
                        show("Invalid input currency, currency doesn't exist in database.");
                    } else {
                        String result_str = Double.toString(result);
                        show(amount + " " + from + " is equals to " + result_str + to + "\n");
                    }
                    break;

                case "3":
                    // To show histry data for specific currency
                    show("Please enter the origin currency you want to search: ");
                    String currency1 = scanner.nextLine();
                    show("Please enter the target currency you want to search: ");
                    String currency2 = scanner.nextLine();
                    show("Please enter the begin date: ");
                    String begin = scanner.nextLine();
                    show("Please enter the end date: ");
                    String end = scanner.nextLine();

                    // show data below
                    // show(currency1 + currency2 + begin + end);
                    double[] data = bg.history_rate(currency1, currency2, begin, end);
                    String[] date = bg.history_date(currency1, currency2, begin, end);
                    show("The history rate from " + begin + " to " + end + " is: ");

                    for (int i = 0; i < data.length; i++) {
                        String string_i = retain_decimal(4, data[i]);
                        // String string_i = String.valueOf(data[i]);

                        show(date[i]);
                        show(string_i + " " + date[i]);
                    }

                    String max = retain_decimal(4, bg.max(data));
                    String min = retain_decimal(4, bg.min(data));
                    String aver = retain_decimal(4, bg.average(data));

                    show("Max: " + max + " \n");
                    show("Min: " + min + " \n");
                    show("Average: " + aver + " \n");
                    break;

                case "4":
                    show("See you.");
                    exit();

                default:
                    clean();
                    show("Invalid operation number. Please enter a new one.");
                    break;
            }
            // scanner.close();
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public void admin_system_loop() {
        // After login to the System
        try {

            show("---------------Welcom to Currency System---------------");
            show("               Type number for operation               ");
            show("               1. Show pupolar currencies              ");
            show("               2. Currency converter                   ");
            show("               3. Search for currency history          ");
            show("-------------------Admin authority---------------------");
            show("               4. Set up the 4 popular currencies      ");
            show("               5. Modify today's new currency rate     ");
            show("               6. Add a new currency                   ");
            show("               7. Exit                                 ");
            show("Next step: ");

            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();
            show(choice);
            show("choice working");
            switch (choice) {
                case "1":
                    // To show 4*4 table include popular currency
                    // show(Background.popular());
                    String[][] output_table;
                    output_table = bg.popular();
                    for (int i = 0; i < 5; i++) {
                        String output = "";
                        for (int j = 0; j < 9; j++) {
                            if (output_table[i][j] == null) {
                                output = output + "        ";

                            } else {
                                output = output + String.format("%-8s", output_table[i][j]);
                            }

                        }
                        show("----------------------------------------------------------------------");
                        show(output);
                    }
                    break;

                case "2":
                    // To convert between 2 currency
                    // Format be like 100 AUD USD
                    show("Please enter your amount for convertion: ");
                    String amount = scanner.nextLine();
                    double amount_d = Double.parseDouble(amount);
                    show("Please enter your original currency: ");
                    String from = scanner.nextLine();
                    show("Please enter your target currency: ");
                    String to = scanner.nextLine();
                    // After convertion and show
                    Double result = bg.calculator(from, to, Double.parseDouble(amount));

                    if (Double.parseDouble(amount) <= 0) {
                        show("Please enter a valid amount. Amount could not be negative.");
                    } else if (amount == null || from == null || to == null || amount == "" || from == "" || to == "") {
                        show("Invalid input. Input could not be blank.");
                    } else if (result < 0) {
                        show("Invalid input currency, currency doesn't exist in database.");
                    } else {
                        String result_str = Double.toString(result);
                        show(amount + " " + from + " is equals to " + result_str + to + "\n");
                    }
                    break;

                case "3":
                    // To show histry data for specific currency
                    show("Please enter the origin currency you want to search: ");
                    String currency1 = scanner.nextLine();
                    show("Please enter the target currency you want to search: ");
                    String currency2 = scanner.nextLine();
                    show("Please enter the begin date: ");
                    String begin = scanner.nextLine();
                    show("Please enter the end date: ");
                    String end = scanner.nextLine();

                    // show data below
                    // show(currency1 + currency2 + begin + end);
                    double[] data = bg.history_rate(currency1, currency2, begin, end);
                    String[] date = bg.history_date(currency1, currency2, begin, end);
                    show("The history rate from " + begin + " to " + end + " is: ");

                    for (int i = 0; i < data.length; i++) {
                        String string_i = retain_decimal(4, data[i]);
                        // String string_i = String.valueOf(data[i]);

                        show(date[i]);
                        show(string_i + " " + date[i]);
                    }

                    String max = retain_decimal(4, bg.max(data));
                    String min = retain_decimal(4, bg.min(data));
                    String aver = retain_decimal(4, bg.average(data));

                    show("Max: " + max + " \n");
                    show("Min: " + min + " \n");
                    show("Average: " + aver + " \n");
                    break;

                case "4":
                    show("Enter the new currency, which is not the most popular currceny yet: (eg.NZD)");
                    String popular_currency1 = scanner.nextLine();
                    show("Enter the target currency, which is the most popular currceny yet: (eg.AUD)");
                    String popular_currency2 = scanner.nextLine();
                    if (bg.change_popular(popular_currency1, popular_currency2)) {
                        show("Success.");
                    } else {
                        show("Invalid input, please check if currency is already in popular/currency doesn't exist.");
                    }
                    break;

                case "5":
                    show("Enter origin currency: ");
                    String from_Currency = scanner.nextLine();
                    show("Enter target: ");
                    String to_Currency = scanner.nextLine();
                    show("Enter today's rate: ");
                    String today = scanner.nextLine();
                    double today_d = Double.parseDouble(today);

                    if (bg.new_rate(from_Currency, to_Currency, today_d)) {
                        show("Success.");
                    } else {
                        show("Invalid input. Please check if currency not exists.");
                    }
                    // show("Finish.");
                    break;

                case "6":
                    show("Add a new currency: ");
                    String new_currency = scanner.nextLine();

                    if (bg.add_new(new_currency)) {
                        show("Success.");
                    } else {
                        show("Invalid input. Please check if currency already exists.");
                    }

                    break;

                case "7":
                    show("See you.");
                    exit();

                default:
                    clean();
                    show("Invalid number. Please enter a new one.");
                    break;
            }
            // scanner.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            show("Invalid input.");

        }
    }

    public void run() {
        while (Current_user == null) {
            login_loop();
        }

        // TODO: get the status of current_user, admin or not.
        while (Current_user.equals("admin")) {
            admin_system_loop();
        }

        while (Current_user.equals("user")) {
            user_system_loop();
        }
    }
}