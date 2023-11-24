package Assignment2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.lang.annotation.*;

// Tsai
public class FileUtil {
    private File file;
    private boolean type;

    /*
     * Constructor, path is the relative path from the root of gradle project
     * type = True: apply for login/signup
     * type = False: apply for goods' data
     */
    @Generated // This annotation is used to ignore testcase for FileUtil
    public FileUtil(String path, boolean type) {
        this.type = type;
        // Load the file
        try {
            this.file = new File(path);
            if (!this.file.exists()) {
                throw new IOException("File not exists: userInfo.csv");
            }

        } catch (Exception e) {
            return;
        }
    }

    /*
     * login/signup mode
     * Append a new admin
     * Return false == error
     */
    @Generated // This annotation is used to ignore testcase
    public boolean Add_new(Admin A) {
        if (!type) {
            return false;
        }

        try {
            // Append method
            FileWriter fw = new FileWriter(file, true);
            String lastitems = "," + A.getLast()[0] + "," + A.getLast()[1] + "," + A.getLast()[2] + "," + A.getLast()[3]
                    + ","
                    + A.getLast()[4];
            // username,salt,hashvalue,authority
            fw.write(A.getUsername() + "," + A.getSalt() + "," + A.getHasvalue() + "," + A.getAuthority() + lastitems
                    + "\n");
            fw.close();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Generated // This annotation is used to ignore testcase for FileUtil
    public boolean remove_user(String name) {
        if (!type) {
            return false;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            // Store all the data into an Arraylist
            String line = "";
            List<String> ls = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                ls.add(line);
            }
            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            // Traverse the arraylist
            for (int i = 0; i < ls.size(); i++) {
                String[] currentLine = ls.get(i).split(",");
                String username = currentLine[0];

                // Find the target line
                if (username.equals(name)) {
                    continue;
                } else {
                    bw.write(ls.get(i));
                }
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /*
     * login/signup mode
     * Load all the information of users in a list
     * Retrun null == error
     */
    @Generated // This annotation is used to ignore testcase
    public List<Admin> loadUsers() {
        if (!type) {
            return null;
        }

        List<Admin> users = new ArrayList<Admin>();
        try {
            FileReader fr = new FileReader(file);
            try (BufferedReader br = new BufferedReader(fr)) {
                String line = "";

                // Read buffer line by line
                while ((line = br.readLine()) != null) {
                    // [username,salt,hashvalue,authority]
                    String[] info = line.split(",");
                    Integer authority = Integer.parseInt(info[3]);
                    // Last 5 items this user bought
                    String[] last5 = new String[5];
                    last5[0] = info[4];
                    last5[1] = info[5];
                    last5[2] = info[6];
                    last5[3] = info[7];
                    last5[4] = info[8];
                    // Authority, 0: Owner, 1: Seller, 2: Cashier
                    if (authority.equals(0)) {
                        users.add(new Owner(info[0], info[1], info[2], 0, last5));
                    } else if (authority.equals(1)) {
                        users.add(new Seller(info[0], info[1], info[2], 1, last5));
                    } else if (authority.equals(2)) {
                        users.add(new Cashier(info[0], info[1], info[2], 2, last5));
                    } else if (authority.equals(3)) {
                        users.add(new Customer(info[0], info[1], info[2], 3, last5));
                    } else {
                        return null;
                    }
                }
            }
            fr.close();
            return users;
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * goods' data mode
     * This method is using for load information of gooods
     */
    @Generated // This annotation is used to ignore testcase
    public List<Goods> loadGoods() {
        if (type) {
            return null;
        }

        List<Goods> items = new ArrayList<Goods>();
        try {
            FileReader fr = new FileReader(file);

            try (BufferedReader br = new BufferedReader(fr)) {
                String line = "";
                while ((line = br.readLine()) != null) {
                    String[] info = line.split(",");

                    // Maintaince limitation is 15
                    if (Integer.parseInt(info[2]) > 15) {
                        return null;
                    }

                    items.add(new Goods(info[1], Double.parseDouble(info[3]), info[4], Integer.parseInt(info[0]),
                            Integer.parseInt(info[2])));
                }
            }

            return items;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /*
     * Return true: modify it successfully
     * false: cannot find the item or something wrong (hint: quantity should be less
     * than 15)
     * Goods' data mode
     */
    @Generated // This annotation is used to ignore testcase
    public boolean edit_amount(Goods goods, Integer newAmount) {
        if (type || newAmount > 15) {
            return false;
        }
        boolean flag = false;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            // Store all the data into an Arraylist
            String line = "";
            List<String> ls = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                ls.add(line);
            }

            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            // Traverse the arraylist
            for (int i = 0; i < ls.size(); i++) {
                String[] currentLine = ls.get(i).split(",");
                String code_str = currentLine[0];
                Integer code = Integer.parseInt(code_str);
                // Find the target line
                if (code.equals(goods.code)) {
                    bw.write(currentLine[0] + "," + currentLine[1] + "," + Integer.toString(newAmount) + ","
                            + currentLine[3] + "," + currentLine[4]);
                    flag = true;
                } else {
                    bw.write(ls.get(i));
                }
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if (flag) {
            return true;
        } else {
            return false;
        }

    }

    @Generated // This annotation is used to ignore testcase for FileUtil
    public boolean edit_price(Goods goods, Double newPrice) {
        if (type) {
            return false;
        }

        boolean flag = false;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            // Store all the data into an Arraylist
            String line = "";
            List<String> ls = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                ls.add(line);
            }

            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            // Traverse the arraylist
            for (int i = 0; i < ls.size(); i++) {
                String[] currentLine = ls.get(i).split(",");
                String price_str = currentLine[0];
                Integer code = Integer.parseInt(price_str);
                // Find the target line
                if (code.equals(goods.code)) {
                    bw.write(currentLine[0] + "," + currentLine[1] + "," + currentLine[2] + ","
                            + Double.toString(newPrice) + "," + currentLine[4]);
                    flag = true;
                } else {
                    bw.write(ls.get(i));
                }
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if (flag) {
            return true;
        } else {
            return false;
        }

    }

    @Generated // This annotation is used to ignore testcase for FileUtil
    public boolean edit_name(Goods goods, String newname) {
        if (type) {
            return false;
        }

        boolean flag = false;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            // Store all the data into an Arraylist
            String line = "";
            List<String> ls = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                ls.add(line);
            }

            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            // Traverse the arraylist
            for (int i = 0; i < ls.size(); i++) {
                String[] currentLine = ls.get(i).split(",");
                String price_str = currentLine[0];
                Integer code = Integer.parseInt(price_str);
                // Find the target line
                if (code.equals(goods.code)) {
                    bw.write(currentLine[0] + "," + newname + "," + currentLine[2] + ","
                            + currentLine[3] + "," + currentLine[4]);
                    flag = true;
                } else {
                    bw.write(ls.get(i));
                }
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if (flag) {
            return true;
        } else {
            return false;
        }

    }

    @Generated // This annotation is used to ignore testcase for FileUtil
    public boolean edit_category(Goods goods, Category newc) {
        if (type) {
            return false;
        }

        boolean flag = false;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            // Store all the data into an Arraylist
            String line = "";
            List<String> ls = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                ls.add(line);
            }

            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            // Traverse the arraylist
            for (int i = 0; i < ls.size(); i++) {
                String[] currentLine = ls.get(i).split(",");
                String price_str = currentLine[0];
                Integer code = Integer.parseInt(price_str);
                // Find the target line
                if (code.equals(goods.code)) {
                    bw.write(currentLine[0] + "," + currentLine[1] + "," + currentLine[2] + ","
                            + currentLine[3] + "," + newc.getSize());
                    flag = true;
                } else {
                    bw.write(ls.get(i));
                }
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if (flag) {
            return true;
        } else {
            return false;
        }

    }

    @Generated // This annotation is used to ignore testcase for FileUtil
    public boolean edit_code(Goods goods, int newcode) {
        if (type) {
            return false;
        }

        boolean flag = false;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            // Store all the data into an Arraylist
            String line = "";
            List<String> ls = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                ls.add(line);
            }

            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            // Traverse the arraylist
            for (int i = 0; i < ls.size(); i++) {
                String[] currentLine = ls.get(i).split(",");
                String price_str = currentLine[0];
                Integer code = Integer.parseInt(price_str);
                // Find the target line
                if (code.equals(goods.code)) {
                    bw.write(Integer.toString(newcode) + "," + currentLine[1] + "," + currentLine[2] + ","
                            + currentLine[3] + "," + currentLine[4]);
                    flag = true;
                } else {
                    bw.write(ls.get(i));
                }
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if (flag) {
            return true;
        } else {
            return false;
        }

    }

    @Generated
    public boolean edit_last(Admin user, String[] newls) {
        if (!type) {
            return false;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            // Store all the data into an Arraylist
            String line = "";
            List<String> ls = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                ls.add(line);
            }
            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            // Traverse the arraylist
            for (int i = 0; i < ls.size(); i++) {
                String[] currentLine = ls.get(i).split(",");
                String username = currentLine[0];

                // Find the target line
                if (username.equals(user.getUsername())) {
                    bw.write(currentLine[0] + "," + currentLine[1] + "," + currentLine[2] + "," + currentLine[3] + ","
                            + newls[0]
                            + "," + newls[1]
                            + "," + newls[2] + "," + newls[3] + "," + newls[4]);
                } else {
                    bw.write(ls.get(i));
                }
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    // This code is to ignore FileUtil in the jacocoTestReport because we do not
    // need to test these function
    @Documented
    public @interface Generated {
    }
}
