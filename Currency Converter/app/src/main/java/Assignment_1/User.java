package Assignment_1;

public class User {

    private String name;
    private String psw;
    // Ture -> is admin user, False -> is normal user
    private boolean isAdmin;

    public User() {
    }

    public User(String name, String psw, boolean isAdmin) {
        this.name = name;
        this.psw = psw;
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    /*
     * Ture: is administrator
     * False: is not administrator
     */
    public boolean getauthority() {
        return this.isAdmin;
    }
}
