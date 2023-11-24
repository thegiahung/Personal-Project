package Assignment2;

// Tsai
public class Admin {
    private String hasvalue;
    private String username;
    private String salt;
    private int authority;
    public String[] last5 = new String[5];

    // Authority, 0: Owner, 1: Seller, 2: Cashier, 3: Normal customer
    protected Admin(String username, String password, int authority) {
        this.username = username;
        this.salt = Encrypter.randomString(16);
        this.hasvalue = Encrypter.get_SHA_512_SecurePassword(password, salt);
        this.authority = authority;
        for (int i = 0; i < 5; i++) {
            this.last5[i] = " ";
        }
    }

    /*
     * Constructor for read a existed user from database
     * Authority, 0: Owner, 1: Seller, 2: Cashier, 3: Normal customer
     */
    protected Admin(String username, String salt, String hasvalue, int authority, String[] last5) {
        this.username = username;
        this.salt = salt;
        this.hasvalue = hasvalue;
        this.authority = authority;
        this.last5 = last5;
    }

    public int getAuthority() {
        return authority;
    }

    public String getUsername() {
        return username;
    }

    public String getHasvalue() {
        return hasvalue;
    }

    public String getSalt() {
        return salt;
    }

    public String[] getLast() {
        return last5;
    }
}
