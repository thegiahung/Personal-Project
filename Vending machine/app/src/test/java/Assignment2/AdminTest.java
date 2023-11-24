package Assignment2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {
    Admin a1 = new Admin("A", "SOFT1234", 0);
    Admin a2 = new Admin("B", "SOFT2345", 1);
    String last5 [] = {"pringle", "coke", "chips", "chocolate", "water"};
    
    Admin a3 = new Admin("C", "SOFT3456", "hash", 2, last5);
    // Test if the user is right or not
    @Test
    public void TestGetUserName() {
        a1.getUsername().equals("A");
    }

    // Test if it get the right authority
    @Test
    public void TestGetAuth() {
        assertEquals(a2.getAuthority(), 1);
    }

    // Test if it get the right authority
    @Test
    public void TestGetHashValue() {
        assertEquals(a3.getHasvalue(), "hash");
    }

    // Test if it get the right authority
    @Test
    public void TestGetSalt() {
        assertEquals(a3.getSalt(), "SOFT3456");
    }

    // Test if it get the right five last item
    @Test
    public void TestGetLast() {
        assertEquals(a3.getLast(), last5);
    }
}
