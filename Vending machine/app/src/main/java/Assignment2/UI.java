package Assignment2;

import java.util.*;
import java.io.*;
import java.lang.annotation.*;
// Ed

import Assignment2.App.Generated;

public class UI {
    @Generated // This annotation is used to ignore testcase for UI
    public enum Status {
        DEFAULT, SELLER, CASHIER, OWNER, END;
    }

    Admin Current_user;
    Seller Current_seller;
    Cashier Curret_cashier;
    Owner Current_owner;
    VendingMachine vm;
    Status status;
    Scanner input;

    @Generated // This annotation is used to ignore testcase for UI
    // NEED TO CONFIRM: Does vending machine needs to be initialize by a default
    // amount of money?
    public UI() {
        Current_user = null;
        Current_seller = null;
        Curret_cashier = null;
        Current_owner = null;
        vm = new VendingMachine();
        status = Status.DEFAULT;
        input = new Scanner(System.in);
    }

    @Generated // This annotation is used to ignore testcase for UI
    // easier printer
    public void show(String s) {
        System.out.print(s + "\n");
    }

    @Generated // This annotation is used to ignore testcase for UI
    // clean up the terminal output
    public void clean() {
        show("\033[H\033[2J");
    }

    @Generated // This annotation is used to ignore testcase for UI
    // close the whole process
    private void exit() {
        show("See you.");
        System.exit(0);
    }

    @Generated // This annotation is used to ignore testcase for UI
    public String masking_scanner() {
        Scanner scanner = new Scanner(System.in);
        String password = null;
        while (true) {
            // System.out.print("Name: ");
            // String name = scanner.next();
            System.out.print("pwd: ");
            EraserThread eraserThread = new EraserThread('*');
            eraserThread.start();
            password = scanner.next();
            eraserThread.setActive(false);
            // System.out.println(name + "\n" + password);
            break;
        }
        return password;
    }

    @Generated // This annotation is used to ignore testcase for UI
    private String normal_scanner() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = null;
        // System.out.println("Enter your value:");
        str = br.readLine();
        // show(str);
        // System.out.println("your value is :"+str);
        return str;
    }

    @Generated // This annotation is used to ignore testcase for UI
    public void default_loop() throws IOException {
        // Scanner input = new Scanner(System.in);

        switch (status) {
            case DEFAULT:
                show("----------Welcom to Vending Machine----------");
                show("          Type number for operation          ");
                if (Current_user == null) {
                    show("          1. Login                           ");
                    show("          2. Sign up                         ");
                } else {
                    show("Current login as " + Current_user.getUsername());
                }
                show("Shopping history: ");
                String[] last5 = vm.lastitems();
                if (last5.length == 0 || last5[0] == null) {
                    show("Currently no shopping history.");
                } else {
                    for (String i : last5) {
                        show(i);
                    }
                }
                show("------------------Item list------------------");
                List<Goods> drink_list = vm.getbyCategory("Drink");
                List<Goods> chocolate_list = vm.getbyCategory("Chocolates");
                List<Goods> chips_list = vm.getbyCategory("Chips");
                List<Goods> candy_list = vm.getbyCategory("Candies");
                show("          3. Drink list                      ");
                for (Goods dkg : drink_list) {
                    show(dkg.code + " " + dkg.name + " " + dkg.price);
                }
                show("          4. Chocolates list                 ");
                for (Goods clg : chocolate_list) {
                    show(clg.code + " " + clg.name + " " + clg.price);
                }
                show("          5. Chips list                      ");
                for (Goods cpg : chips_list) {
                    show(cpg.code + " " + cpg.name + " " + cpg.price);
                }
                show("          6. Candies list                    ");
                for (Goods cdg : candy_list) {
                    show(cdg.code + " " + cdg.name + " " + cdg.price);
                }
                show("---------------------------------------------");
                show("          7. Your cart                       ");
                // TODO: Show come back to this page after 2 minutes stay, not exit.
                show("          8. Exit                            ");
                if (Current_user != null) {
                    show("          9. Log out                         ");
                }
                show("Choice: \n");

                String customer_choice = normal_scanner();
                // show("Choice is " + customer_choice);

                switch (customer_choice) {
                    case "1":
                        show("Please enter your account name: ");
                        String account_name = normal_scanner();

                        // show("Password: ");
                        // String pwd = normal_scanner();
                        String pwd = masking_scanner();

                        // show(account_name + pwd);

                        int result = -1;
                        if (pwd.length() > 0 && account_name.length() > 0) {
                            Current_user = vm.Login(account_name, pwd);
                            if (Current_user == null) {
                                show("Invalid account.");
                                // break;
                            } else {
                                result = Current_user.getAuthority();
                            }
                        } else {
                            show("Input is empty. Please try again.");
                        }

                        if (result >= 0) {
                            show("Success.");
                            // 0: Owner, 1: Seller, 2: Cashier, 3: Normal customer
                            String t = String.valueOf(result);
                            show("result is " + t);
                            switch (result) {
                                case 1:
                                    status = Status.SELLER;
                                    break;
                                case 2:
                                    status = Status.CASHIER;
                                    break;
                                case 3:
                                    status = Status.DEFAULT;
                                    break;
                                case 0:
                                    status = Status.OWNER;
                                    break;
                                default:
                                    break;
                            }
                        } else if (result == -2) {
                            show("Invalid password.");
                        } else if (result == -3) {
                            show("User does not exist.");
                        }
                        break;

                    case "2":
                        show("Please enter username for this new account: ");
                        String new_account_name = normal_scanner();
                        // show("Please enter your password: ");
                        // String new_password = normal_scanner();
                        String new_password = masking_scanner();

                        int new_result = vm.Add_new(new_account_name, new_password, 3);

                        if (new_result == 1) {
                            show("Success create.");
                            Current_user = vm.Login(new_account_name, new_password);
                            int new_login_result = Current_user.getAuthority();
                            if (new_login_result > 0) {
                                show("Now you're login as customer by this account automatically.");
                            }
                        } else if (new_result == -1) {
                            show("Username already exists.");
                        }
                        break;

                    case "3":
                        show("Please enter the item code and quantity you need: (eg. 1001 1)");
                        vm.start();
                        if (timer() == null) {
                            return;
                        }
                        String drink_raw = normal_scanner();
                        String[] splited_drink_raw = drink_raw.split(" ");
                        if (splited_drink_raw.length != 2) {
                            show("Invalid input");
                            break;
                        }
                        int drink_code = Integer.parseInt(splited_drink_raw[0]);
                        int drink_quantity = Integer.parseInt(splited_drink_raw[1]);
                        int drink_result = vm.inOutCart(drink_code, drink_quantity);

                        if (drink_result == 0) {
                            show("Successfully added to cart :)");
                        } else if (drink_result == -1) {
                            show("Sorry, we don'e have enough stock for doing that :(");
                        }
                        break;

                    case "4":
                        show("Please enter the item code and quantity you need: (eg. 1101 1)");
                        vm.start();
                        if (timer() == null) {
                            return;
                        }
                        String chocolate_raw = normal_scanner();
                        String[] splited_chocolate_raw = chocolate_raw.split(" ");
                        if (splited_chocolate_raw.length != 2) {
                            show("Invalid input");
                            break;
                        }

                        int chocolate_code = Integer.parseInt(splited_chocolate_raw[0]);
                        int chocolate_quantity = Integer.parseInt(splited_chocolate_raw[1]);
                        int chocolate_result = vm.inOutCart(chocolate_code, chocolate_quantity);

                        if (chocolate_result == 0) {
                            show("Successfully added to cart :)");
                        } else if (chocolate_result == -1) {
                            show("Sorry, we don'e have enough stock for doing that :(");
                        }
                        break;

                    case "5":
                        show("Please enter the item code and quantity you need: (eg. 1201 1)");
                        vm.start();
                        if (timer() == null) {
                            return;
                        }
                        String chip_raw = normal_scanner();
                        String[] splited_chip_raw = chip_raw.split(" ");
                        if (splited_chip_raw.length != 2) {
                            show("Invalid input");
                            break;
                        }

                        int chip_code = Integer.parseInt(splited_chip_raw[0]);
                        int chip_quantity = Integer.parseInt(splited_chip_raw[1]);
                        int chip_result = vm.inOutCart(chip_code, chip_quantity);

                        if (chip_result == 0) {
                            show("Successfully added to cart :)");
                        } else if (chip_result == -1) {
                            show("Sorry, we don'e have enough stock for doing that :(");
                        }
                        break;

                    case "6":
                        show("Please enter the item code and quantity you need: (eg. 1301 1)");
                        vm.start();
                        if (timer() == null) {
                            return;
                        }
                        String candy_raw = normal_scanner();
                        String[] splited_candy_raw = candy_raw.split(" ");
                        if (splited_candy_raw.length != 2) {
                            show("Invalid input");
                            break;
                        }

                        int candy_code = Integer.parseInt(splited_candy_raw[0]);
                        int candy_quantity = Integer.parseInt(splited_candy_raw[1]);
                        int candy_result = vm.inOutCart(candy_code, candy_quantity);

                        if (candy_result == 0) {
                            show("Successfully added to cart :)");
                        } else if (candy_result == -1) {
                            show("Sorry, we don'e have enough stock for doing that :(");
                        }
                        break;

                    case "7":
                        // show(Integer.toString(vm.inCart().size()));
                        // show(Integer.toString(vm.cart.get(vm.getGoodsbyCode(1001))));
                        show("code | name | price | quantity | total_price");
                        if (vm.inCart().size() > 0) {
                            if (vm.inCart().size() > 0) {
                                for (String i : vm.inCart()) {
                                    show(i);
                                }
                            } else {
                                show("Nothing.");
                                break;
                            }
                            show("1. checkout on cash");
                            show("2. checkout on card");
                            show("3. remove item");
                            show("4. Cancell this order");
                            show("5. continue shopping");

                            // Simple timer, needs to record the cancelled transaction.
                            if (timer() == null) {
                                vm.canncelTs(-1);
                                return;
                            }
                            String cart_choice = normal_scanner();

                            switch (cart_choice) {
                                case "1":
                                    show("Please enter your cash amount:(eg. 50 20 20 1 0.5)");
                                    if (timer() == null) {
                                        return;
                                    }
                                    String[] cash_in = normal_scanner().split(" ");
                                    List<Double> double_cash = new ArrayList<Double>();

                                    if (cash_in.length < 1) {
                                        show("Please enter valid number of notes/coins.");
                                    }

                                    for (String each_note : cash_in) {
                                        Double double_each = Double.parseDouble(each_note);
                                        double_cash.add(double_each);
                                    }
                                    Map<String, Integer> cash_result = vm.spendThroughCash(double_cash);

                                    // Cash not avaiable
                                    if (cash_result == null) {
                                        vm.canncelTs(-3);
                                        return;
                                    }

                                    for (Map.Entry<String, Integer> entry : cash_result.entrySet()) {
                                        show("Amount: " + entry.getKey() + " Num: " + entry.getValue().toString());
                                    }
                                    break;

                                case "2":
                                    show("Please enter your account name: ");
                                    if (timer() == null) {
                                        return;
                                    }
                                    String card_account = normal_scanner();
                                    // show("Please enter your pasword: ");
                                    // String card_password = normal_scanner();
                                    String card_password = masking_scanner();

                                    int card_result = vm.CardCheckout(card_account, card_password);
                                    if (card_result == -1) {
                                        show("Success! Thanks for your payment ;)");
                                    } else if (card_result == -2) {
                                        show("Wrong account or password, please try again.");
                                    }
                                    break;

                                case "3":
                                    show("Please enter the item code and quantity you want to remove: (eg. 1301 -1)");
                                    if (timer() == null) {
                                        return;
                                    }
                                    String raw = normal_scanner();
                                    String[] splited_raw = raw.split(" ");

                                    int raw_code = Integer.parseInt(splited_raw[0]);
                                    int raw_quantity = Integer.parseInt(splited_raw[1]);
                                    int raw_result = vm.inOutCart(raw_code, raw_quantity);

                                    if (raw_result == 0) {
                                        show("Successfully added :)");
                                    } else if (raw_quantity == -1) {
                                        show("Sorry, stock is not enough :(");
                                    }
                                    break;

                                case "4":
                                    // TODO
                                    break;

                                case "5":
                                    break;

                                default:
                                    break;
                            }
                        } else {
                            show("Nothing.");
                        }
                        break;

                    case "8":
                        exit();
                        break;

                    case "9":
                        Current_user = null;
                        status = Status.DEFAULT;
                        vm.logout();
                        break;

                    default:
                        break;
                }

                break;

            case SELLER:
                // TODO generate report
                // A list of the current available items that include the item details. What is
                // item detail??
                // A summary that includes items codes, item names and the total number of
                // quantity sold for each item (e.g. "1001; Mineral Water; 12", "1002; Mars; 1",
                // etc.)
                show("Current login as seller");
                show("1. Modify item amount");
                show("2. Modify item price");
                show("3. Modify item name");
                show("4. Modify item category");
                show("5. Modify item code");
                show("6. Generate simple item report");
                show("7. Generate full item report");
                show("------------------------------");
                show("8. exit");
                show("9. Sign out(will return to default page)");
                show("choice: ");
                String seller_choice = normal_scanner();

                switch (seller_choice) {
                    case "1":
                        show("Enter the target item and new amount:(eg. 1001 10)");
                        String[] amount_in = normal_scanner().split(" ");
                        if (amount_in.length < 2) {
                            show("Invalid input, please check if you have entered BOTH item code and amount.");
                            break;
                        }
                        int amount_code = Integer.parseInt(amount_in[0]);
                        Integer amount_amount = Integer.valueOf(amount_in[1]);

                        Goods amount_good = vm.getGoodsbyCode(amount_code);
                        if (amount_good == null) {
                            show("Cannot find this item, please check.");
                            break;
                        }

                        // boolean amount_result = false;
                        if (vm.currentuser instanceof Seller) {
                            boolean amount_result = ((Seller) vm.currentuser).editAmount(amount_good, amount_amount);
                            if (amount_result) {
                                show("Success.");
                            } else {
                                show("Invalid amount, amount should not larger than 15.(They have limitation on it)");
                            }
                        }

                        break;

                    case "2":
                        show("Enter the target item and new price:(eg. 1001 1.5)");
                        String[] price_in = normal_scanner().split(" ");
                        if (price_in.length < 2) {
                            show("Invalid input. Please check if you have entered BOTH item code and price.");
                            break;
                        }
                        int price_code = Integer.parseInt(price_in[0]);
                        Double price_price = Double.parseDouble(price_in[1]);

                        Goods price_good = vm.getGoodsbyCode(price_code);
                        if (price_good == null) {
                            show("Cannot find this item, please check.");
                            break;
                        }

                        if (vm.currentuser instanceof Seller) {
                            boolean price_result = ((Seller) vm.currentuser).editPrice(price_good, price_price);
                            if (price_result) {
                                show("Success.");
                            } else {
                                show("Invalid price, amount should be positive.(Or at least for free...? Just kidding)");
                            }
                        }

                        break;

                    case "3":
                        show("Enter the target item and new name:(eg. 1001 not water)");
                        String[] name_in = normal_scanner().split(" ");
                        if (name_in.length < 2) {
                            show("Not enough input, please check if you have enter BOTH of item code and new name.");
                            break;
                        }
                        int name_code = Integer.parseInt(name_in[0]);
                        String name_name = "";
                        for (int i = 1; i < name_in.length; i++) {
                            if (i == name_in.length) {
                                break;
                            }
                            name_name = name_name + " " + name_in[i];
                        }

                        // Delete the space at beginning
                        name_name = name_name.substring(1);

                        Goods name_good = vm.getGoodsbyCode(name_code);
                        if (name_good == null) {
                            show("Cannot find this item, please check.");
                            break;
                        }

                        if (vm.currentuser instanceof Seller) {
                            show(name_name);
                            boolean name_result = ((Seller) vm.currentuser).editName(name_good, name_name);
                            if (name_result) {
                                show("Success.");
                            } else {
                                show("Invalid name, try other input.(How could you make this invalid??)");
                            }
                        }

                        break;

                    case "4":
                        show("Enter the target item and new category:(eg. 1001 Fruit)");
                        String[] category_in = normal_scanner().split(" ");
                        if (category_in.length < 2) {
                            show("Invalid input. Please check if you have entered BOTH item code and category.");
                            break;
                        }
                        int category_code = Integer.parseInt(category_in[0]);
                        String category_category = "";
                        for (int i = 1; i < category_in.length; i++) {
                            if (i == category_in.length) {
                                break;
                            }
                            category_category = category_category + " " + category_in[i];
                        }

                        category_category = category_category.substring(1);

                        Goods category_good = vm.getGoodsbyCode(category_code);
                        if (category_good == null) {
                            show("Cannot find this item, please check.");
                            break;
                        }

                        if (vm.currentuser instanceof Seller) {
                            boolean category_result = ((Seller) vm.currentuser).editCategory(category_good,
                                    category_category);
                            if (category_result) {
                                show("Success.");
                            } else {
                                show("Invalid category, try other input.(How could you make this invalid??)");
                            }
                        }

                        break;

                    case "5":
                        show("Enter the target item and new code:(eg. 1001 1002)");
                        String[] code_in = normal_scanner().split(" ");
                        if (code_in.length != 2) {
                            show("Invalid input. Please enter exact 2 code as input.");
                            break;
                        }
                        int origin_code = Integer.parseInt(code_in[0]);
                        int new_code = Integer.parseInt(code_in[1]);

                        Goods target_good = vm.getGoodsbyCode(origin_code);
                        if (target_good == null) {
                            show("Cannot find this item. Please check.");
                            break;
                        }

                        if (vm.currentuser instanceof Seller) {
                            boolean code_result = ((Seller) vm.currentuser).editItem(target_good, new_code, vm.goods);
                            if (code_result) {
                                show("Success.");
                            } else {
                                show("Invalid new code, try other input. Each item code should be unique.");
                            }
                        }
                        break;

                    case "6":
                        if (vm.currentuser instanceof Seller) {
                            boolean report1_result = ((Seller) vm.currentuser).report1(vm.goods);
                            if (report1_result) {
                                show("Success.");
                            } else {
                                show("Error.");
                            }
                        }

                        break;

                    case "7":
                        if (vm.currentuser instanceof Seller) {
                            boolean report2_result = ((Seller) vm.currentuser).report2(vm.goods, vm.sold);
                            if (report2_result) {
                                show("Success.");
                            } else {
                                show("Error.");
                            }
                        }
                        break;

                    case "8":
                        exit();
                        break;

                    case "9":
                        status = Status.DEFAULT;
                        Current_user = null;
                        vm.logout();
                        break;

                    default:
                        break;
                }

                break;

            case CASHIER:
                // TODO generate report
                // Current notes and coins
                show("Current login as cashier.");
                show("1. Modify cash/notes");
                show("2. Show cash report");
                show("3. Show transaction report");
                show("------------------------------");
                show("4. exit");
                show("5. Sign out(will return to default page)");
                show("choice: ");
                String cashier_choice = normal_scanner();

                switch (cashier_choice) {
                    case "1":
                        show("Please enter the money you want to modify:(eg. 50.0 5)");
                        String[] cash_raw = normal_scanner().split(" ");
                        if (cash_raw.length != 2) {
                            show("Invalid input. Please enter exact one pair of note/coin and amount each time.");
                            break;
                        }
                        double cash_value = Double.parseDouble(cash_raw[0]);
                        int cash_amount = Integer.parseInt(cash_raw[1]);

                        int cash_modify_result = Cashier.modifyCash(cash_value, cash_amount);
                        if (cash_modify_result == -1) {
                            show("Success.");
                        } else if (cash_modify_result == -2) {
                            show("Wrong input. Please enter a valid value of money.");
                        }

                        break;

                    case "2":
                        Map<String, Integer> current_cash = Cashier.currentList();
                        for (Map.Entry<String, Integer> entry : current_cash.entrySet()) {
                            show("value: " + entry.getKey() + "  num: " + entry.getValue().toString());
                        }
                        break;

                    case "3":
                        // ArrayList<String> transaction_report = Cashier.getPurchaseInfo();
                        // for (String each_line : transaction_report) {
                        // show(each_line);
                        // }
                        break;

                    case "4":
                        exit();
                        break;

                    case "5":
                        status = Status.DEFAULT;
                        Current_user = null;
                        vm.logout();
                        break;

                    default:
                        break;
                }

                break;

            case OWNER:
                show("See my power!");
                show("1. Add a new user");
                show("2. Remove an user");
                show("3. Show list of all user");
                show("4. Show list of cancelled transaction");
                show("----------Seller options for item----------");
                show("1.1 Modify item amount");
                show("2.1 Modify item price");
                show("3.1 Modify item name");
                show("4.1 Modify item category");
                show("5.1 Modify item code");
                show("6.1 Generate simple item report");
                show("7.1 Generate full item report");
                show("----------Cashier options for cash----------");
                show("1.2 Modify cash/notes");
                show("2.2 Show cash report");
                show("3.2 Show transaction report");
                show("------------------------------");
                show("1.3 exit");
                show("2.3 Sign out");
                show("Choice: ");
                String owner_choice = normal_scanner();

                switch (owner_choice) {
                    case "1":
                        show("Please enter user name: ");
                        String new_admin_name = normal_scanner();
                        show("Please enter user password: ");
                        String new_admin_password = normal_scanner();
                        show("Please enter the authority:(0: Owner, 1: Seller, 2: Cashier)");
                        String new_admin_authority = normal_scanner();

                        int new_admin_authority_intType = Integer.parseInt(new_admin_authority);

                        int new_admin_result = -3;
                        if (new_admin_name.length() > 0 && new_admin_password.length() > 0
                                && new_admin_authority.length() > 0) {
                            if (new_admin_authority_intType == 0 || new_admin_authority_intType == 1
                                    || new_admin_authority_intType == 2) {
                                new_admin_result = vm.Add_new(new_admin_name, new_admin_password,
                                        new_admin_authority_intType);
                                if (new_admin_result == 1) {
                                    show("Success.");
                                } else if (new_admin_result == -1) {
                                    show("User name already exists, try another one.");
                                    break;
                                }
                            } else {
                                show("Invalid authority. Can noly create owner, cashier and seller account.");
                            }
                        } else {
                            show("Invalid input. Should not leave it empty.");
                            break;
                        }
                        break;

                    case "2":
                        show("Please enter the user you want to delete: ");
                        String remove_admin = normal_scanner();

                        int remove_result = -2;
                        if (remove_admin.length() > 0) {
                            remove_result = vm.remove(remove_admin);
                            if (remove_result == 1) {
                                show("Success.");
                            } else if (remove_result == -1) {
                                show("Owner cannot remove each other.");
                                break;
                            } else if (remove_result == -2) {
                                show("Account does not exist");
                                break;
                            }
                        } else {
                            show("Empty input.");
                            break;
                        }
                        break;

                    case "3":
                        if (vm.currentuser instanceof Owner) {
                            boolean admin_report_result = ((Owner) vm.currentuser).adminReport(vm.users);
                            if (admin_report_result) {
                                show("Success.");
                            } else {
                                show("Error.");
                            }
                        }
                        break;

                    case "4":
                        if (vm.currentuser instanceof Owner) {
                            boolean transaction_report_result = ((Owner) vm.currentuser)
                                    .cancelledTransctionReport(vm.transctionsList);
                            if (transaction_report_result) {
                                show("Success.");
                            } else {
                                show("Error.");
                            }
                        }
                        break;

                    case "1.1":
                        show("Enter the target item and new amount:(eg. 1001 10)");
                        String[] amount_in = normal_scanner().split(" ");
                        if (amount_in.length < 2) {
                            show("Invalid input, please check if you have entered BOTH item code and amount.");
                            break;
                        }
                        int amount_code = Integer.parseInt(amount_in[0]);
                        Integer amount_amount = Integer.valueOf(amount_in[1]);

                        Goods amount_good = vm.getGoodsbyCode(amount_code);
                        if (amount_good == null) {
                            show("Cannot find this item, please check.");
                            break;
                        }

                        // boolean amount_result = false;
                        if (vm.currentuser instanceof Owner) {
                            boolean amount_result = ((Owner) vm.currentuser).editAmount(amount_good, amount_amount);
                            if (amount_result) {
                                show("Success.");
                            } else {
                                show("Invalid amount, amount should not larger than 15.(They have limitation on it)");
                            }
                        }

                        break;

                    case "2.1":
                        show("Enter the target item and new price:(eg. 1001 1.5)");
                        String[] price_in = normal_scanner().split(" ");
                        if (price_in.length < 2) {
                            show("Invalid input. Please check if you have entered BOTH item code and price.");
                            break;
                        }
                        int price_code = Integer.parseInt(price_in[0]);
                        Double price_price = Double.parseDouble(price_in[1]);

                        Goods price_good = vm.getGoodsbyCode(price_code);
                        if (price_good == null) {
                            show("Cannot find this item, please check.");
                            break;
                        }

                        if (vm.currentuser instanceof Owner) {
                            boolean price_result = ((Owner) vm.currentuser).editPrice(price_good, price_price);
                            if (price_result) {
                                show("Success.");
                            } else {
                                show("Invalid price, amount should be positive.(Or at least for free...? Just kidding)");
                            }
                        }

                        break;

                    case "3.1":
                        show("Enter the target item and new name:(eg. 1001 not water)");
                        String[] name_in = normal_scanner().split(" ");
                        if (name_in.length < 2) {
                            show("Not enough input, please check if you have enter BOTH of item code and new name.");
                            break;
                        }
                        int name_code = Integer.parseInt(name_in[0]);
                        String name_name = "";
                        for (int i = 1; i < name_in.length; i++) {
                            if (i == name_in.length) {
                                break;
                            }
                            name_name = name_name + " " + name_in[i];
                        }
                        name_name = name_name.substring(1);

                        Goods name_good = vm.getGoodsbyCode(name_code);
                        if (name_good == null) {
                            show("Cannot find this item, please check.");
                            break;
                        }

                        if (vm.currentuser instanceof Owner) {
                            boolean name_result = ((Owner) vm.currentuser).editName(name_good, name_name);
                            if (name_result) {
                                show("Success.");
                            } else {
                                show("Invalid name, try other input.(How could you make this invalid??)");
                            }
                        }

                        break;

                    case "4.1":
                        show("Enter the target item and new category:(eg. 1001 Fruit)");
                        String[] category_in = normal_scanner().split(" ");
                        if (category_in.length < 2) {
                            show("Invalid input. Please check if you have entered BOTH item code and category.");
                            break;
                        }
                        int category_code = Integer.parseInt(category_in[0]);
                        String category_category = "";
                        for (int i = 1; i < category_in.length; i++) {
                            if (i == category_in.length) {
                                break;
                            }
                            category_category = category_category + " " + category_in[i];
                        }
                        category_category = category_category.substring(1);

                        Goods category_good = vm.getGoodsbyCode(category_code);
                        if (category_good == null) {
                            show("Cannot find this item, please check.");
                            break;
                        }

                        if (vm.currentuser instanceof Owner) {
                            boolean category_result = ((Owner) vm.currentuser).editCategory(category_good,
                                    category_category);
                            if (category_result) {
                                show("Success.");
                            } else {
                                show("Invalid category, try other input.(How could you make this invalid??)");
                            }
                        }

                        break;

                    case "5.1":
                        show("Enter the target item and new code:(eg. 1001 1002)");
                        String[] code_in = normal_scanner().split(" ");
                        if (code_in.length != 2) {
                            show("Invalid input. Please enter exact 2 code as input.");
                            break;
                        }
                        int origin_code = Integer.parseInt(code_in[0]);
                        int new_code = Integer.parseInt(code_in[1]);

                        Goods target_good = vm.getGoodsbyCode(origin_code);
                        if (target_good == null) {
                            show("Cannot find this item. Please check.");
                            break;
                        }

                        if (vm.currentuser instanceof Owner) {
                            boolean code_result = ((Owner) vm.currentuser).editItem(target_good, new_code, vm.goods);
                            if (code_result) {
                                show("Success.");
                            } else {
                                show("Invalid new code, try other input. Each item code should be unique.");
                            }
                        }
                        break;

                    case "6.1":
                        if (vm.currentuser instanceof Owner) {
                            boolean report1_result = ((Owner) vm.currentuser).report1(vm.goods);
                            if (report1_result) {
                                show("Success.");
                            } else {
                                show("Error.");
                            }
                        }

                        break;

                    case "7.1":
                        if (vm.currentuser instanceof Owner) {
                            boolean report2_result = ((Owner) vm.currentuser).report2(vm.goods, vm.sold);
                            if (report2_result) {
                                show("Success.");
                            } else {
                                show("Error.");
                            }
                        }
                        break;

                    case "1.2":
                        show("Please enter the money you want to modify:(eg. 50.0 5)");
                        String[] cash_raw = normal_scanner().split(" ");
                        if (cash_raw.length != 2) {
                            show("Invalid input. Please enter exact one pair of note/coin and amount each time.");
                            break;
                        }
                        double cash_value = Double.parseDouble(cash_raw[0]);
                        int cash_amount = Integer.parseInt(cash_raw[1]);

                        int cash_modify_result = Owner.modifyCash(cash_value, cash_amount);
                        if (cash_modify_result == -1) {
                            show("Success.");
                        } else if (cash_modify_result == -2) {
                            show("Wrong input. Please enter a valid value of money.");
                        }

                        break;

                    case "2.2":
                        Map<String, Integer> current_cash = Owner.currentList();
                        for (Map.Entry<String, Integer> entry : current_cash.entrySet()) {
                            show("value: " + entry.getKey() + "  num: " + entry.getValue().toString());
                        }
                        break;

                    case "3.2":
                        ArrayList<String> transaction_report = Owner.getPurchaseInfo();
                        for (String each_line : transaction_report) {
                            show(each_line);
                        }
                        break;

                    case "1.3":
                        exit();
                        break;

                    case "2.3":
                        status = Status.DEFAULT;
                        Current_user = null;
                        vm.logout();
                        break;

                    default:
                        break;
                }
                // Should have all function like cashier and seller
                // Report1 list of all user
                // Report2 cancelled transaction

                break;

            case END:
                show("fuck");
                status = Status.DEFAULT;
                // exit();
                break;

            default:
                break;
        }

        // input.close();
    }

    @Generated // This annotation is used to ignore testcase for UI
    public void begin() {
        try {
            while (true) {
                this.default_loop();
            }
        }
        // avoid scanner error
        catch (NoSuchElementException nsee) {
            status = Status.END;
            try {
                this.default_loop();
            } catch (IOException e) {
                show("error");
            }
        } catch (IOException ioe) {
            show("error");
        } catch (NumberFormatException e) {
            show("Type error!");
        }
    }

    @Generated
    public static String timer() {
        try {
            int x = 20; // wait 2 mins at most
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            long startTime = System.currentTimeMillis();
            while ((System.currentTimeMillis() - startTime) < x * 1000
                    && !in.ready()) {
            }
            if (in.ready()) {
                // no need to actually read things there, should leave the system.in pipe clear.
                return "reading";
            } else {
                System.out.println("time out");
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

}

class EraserThread extends Thread {

    private boolean active;

    private String mask;

    @Generated // This annotation is used to ignore testcase for UI
    public EraserThread() {
        this('*');
    }

    @Generated // This annotation is used to ignore testcase for UI
    public EraserThread(char maskChar) {
        active = true;
        mask = "\010" + maskChar;
    }

    @Generated // This annotation is used to ignore testcase for UI
    public void setActive(boolean active) {
        this.active = active;
    }

    @Generated // This annotation is used to ignore testcase for UI
    public boolean isActive() {
        return active;
    }

    @Generated // This annotation is used to ignore testcase for UI
    @Override
    public void run() {
        while (isActive()) {
            System.out.print(mask);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // This code is to ignore FileUtil in the jacocoTestReport because we do not
    // need to test these function
    @Documented
    public @interface Generated {
    }
}