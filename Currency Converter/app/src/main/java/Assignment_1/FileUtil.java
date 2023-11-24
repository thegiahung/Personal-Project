package Assignment_1;

import java.io.*;
import java.nio.file.Paths;

import java.util.*;
import java.util.ArrayList;
import java.util.List;

import java.lang.annotation.*;

public class FileUtil {

    public ArrayList<Exchange> exchanges = new ArrayList<>();

    /*
     * Write in username and password into userInfo csv file
     * Information columns: Name, password, authority(0: normal user, 1: admin user)
     */
    @Generated
    public void writeUserInFile(User user) {
        try {

            String abs_path = new java.io.File(".").getCanonicalPath();
            File file = new File(abs_path + "src/main/java/Assignment_1/database/userInfo.csv");
            if (!file.exists()) {
                throw new IOException("Relative file: userInfo.csv");
            }
            FileWriter writer = new FileWriter(file, true);
            String isadmin;
            if (user.getauthority()) {
                isadmin = "1";
            } else {
                isadmin = "0";
            }
            writer.write(user.getName() + "," + user.getPsw() + "," + isadmin + "\n");
            writer.close();
        } catch (Exception e) {
            return;
        }
    }

    /*
     * Read through the user txt file then return it as a list
     * Format of users' information: name, password, authority(0: normal user, 1:
     * admin user)
     */
    @Generated
    public List<User> readUserFromFile() {
        try {
            File file = new File("src/main/java/Assignment_1/database/userInfo.csv");

            FileReader reader = new FileReader(file);

            try (BufferedReader bufferedReader = new BufferedReader(reader)) {
                String line = "";
                List<User> list = new ArrayList<>();
                while ((line = bufferedReader.readLine()) != null) {
                    String[] split = line.split(",");

                    // Attribute 3: User have admin authority or not

                    boolean authority;
                    if (split[2].compareTo("0") == 0) {
                        authority = false;
                    } else {
                        authority = true;
                    }
                    User user = new User(split[0], split[1], authority);
                    list.add(user);
                }
                bufferedReader.close();
                return list;
            } catch (Exception e) {
                throw new IOException("Relative file: userInfo.csv");
            }
        } catch (Exception e) {
            return null;
        }

    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /*
     * Reading through the whole csv file, format for every single line[date,
     * currencyname_1, currencyname_2, rate]
     * Read the whole txt file line by line
     * Columns: Date, currency1's name, currency2's name, exchange rate
     */
    @Generated
    public void ReadAllInfo() {
        try {
            String filePath = "src/main/java/Assignment_1/database/CurrencyInfo.csv";
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String line = ""; // Read through every whole line

            while ((line = bufferedReader.readLine()) != null) { // return null when it finishes reading
                // String: [date, currencyname_1, currencyname_2, rate]
                String currencyInfo[] = line.split(",");
                Exchange exchange = new Exchange(currencyInfo);
                exchanges.add(exchange);
            }

            bufferedReader.close();
        } catch (Exception e) {
            return;
        }

    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Write in all currency information into the txt file. New record will be a new
    // line
    @Generated
    public void WriteIn(Exchange newExchange) {
        try {
            FileWriter fw = new FileWriter("src/main/java/Assignment_1/database/CurrencyInfo.csv", true);
            BufferedWriter writer = new BufferedWriter(fw);

            writer.write(newExchange.getInfo());
            writer.close();

            // Add this new exchange instance into exchanges list
            this.exchanges.add(newExchange);
        } catch (Exception e) {
            return;
        }

    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Get all the history data
    public ArrayList<Exchange> getAllHistory() {
        return this.exchanges;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /*
     * Boolean flag == True: return currencies' name list
     * Boolean flag == False: return popular currencies' name list (length = 4)
     */
    @Generated
    public List<String> getAllCurrencies(boolean flag) {
        try {
            List<String> list = new ArrayList<>();
            String path = "src/main/java/Assignment_1/database/CurencyName.csv";
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line = "";

            // Model 1: all currencies' name
            if (flag) {
                while ((line = reader.readLine()) != null) {
                    list.add(line.split(",")[0]);
                }
            }
            // Model 2: popluar currencies name
            // In the csv file, 0: is not popular currency, 1: is popular currcency]
            else {
                while ((line = reader.readLine()) != null) {

                    if (line.split(",")[1].compareTo("1") == 0) {
                        list.add(line.split(",")[0]);
                    }
                }
            }

            reader.close();
            return list;
        } catch (Exception e) {
            return null;
        }

    }

    // 2.Return the total number of lines in the txt file
    public int getListLength() {
        return this.exchanges.size();
    }

    // 3.According to the input line number to return the required content e.g. line
    // 32nd, {2022-09-03,AUD,USD,1.5}
    public Exchange getByLine(int line) {
        return this.exchanges.get(line);
    }

    /*
     * currency1: new popular currcency's name
     * currency2: name of currency which will be replaced in popular list
     */
    public boolean changePopular(String currency1, String currency2) {
        try {
            // Part 1: Read the csv file
            BufferedReader reader = new BufferedReader(
                    new FileReader("src/main/java/Assignment_1/database/CurencyName.csv"));

            String line = "";

            List<String> ls = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                ls.add(line);
            }

            reader.close();

            // Part 2: Rewrite csv file
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter("src/main/java/Assignment_1/database/CurencyName.csv"));

            for (int i = 0; i < ls.size(); i++) {
                // Found currceny1
                if (ls.get(i).split(",")[0].compareTo(currency1) == 0) {
                    writer.write(ls.get(i).split(",")[0] + "," + "1");
                }
                // Found currency2
                else if (ls.get(i).split(",")[0].compareTo(currency2) == 0) {
                    writer.write(ls.get(i).split(",")[0] + "," + "0");
                }
                // Other rows
                else {
                    writer.write(ls.get(i));
                }
                writer.newLine();
            }
            writer.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    // Append a new currency to the end of CurrencyName.csv
    public void addNewCurrency(String currcenyNameorSymbol) {
        try {
            FileWriter fw = new FileWriter("src/main/java/Assignment_1/database/CurencyName.csv", true);
            BufferedWriter writer = new BufferedWriter(fw);

            writer.write(currcenyNameorSymbol + ",0\n");
            writer.close();
        } catch (Exception e) {
            return;
        }

    }

    @Documented

    public @interface Generated {
    }

}
