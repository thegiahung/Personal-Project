package Assignment_1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class UserTest {
//    App apptest1 = new App();
    User user1 = new User("Adam", "soft1111", false);
    User user2 = new User("Eva", "soft1234", false);
    User user3 = new User("Jack", "soft2222", true);
    User user4 = new User();

    // Test if the user is existed or not
    @Test
    public void TestGetUserNotNull() {
        assertNotNull(user4);
        assertNotNull(user2);
    }

    // Test if the user name return null or not and whether it work properly
    @Test
    public void TestGetUserName() {
        assertNull(user4.getName());
        assertNull(user4.getName());
        assertNotNull((user3.getName()));
    }

    // Test if the user name is change or not after setting the name
    @Test
    public void TestsetName() {
        user1.setName("Anna");
        user1.getName().equals("Adam");
        user1.getName().equals("Anna");
        assertNotNull(user1.getName());
    }

    // Test if the password return null or not and whether it work properly
    @Test
    public void TestGetPwd() {
        assertNotNull(user2.getPsw());
        user2.getPsw().equals("info1113");
        user2.getPsw().equals("soft1234");
        assertNotNull(user2.getPsw());
    }

    // Test if the password is change or not after setting the name
    @Test
    public void TestSetPwd() {
        user2.setPsw("comp2123");
        user2.getPsw().equals("soft1234");
        user2.getPsw().equals("comp2123");
        assertNotNull(user2.getPsw());
    }

    // Test if authority to know whether user is admin or not is
    @Test
    public void TestGetAuthority() {
        Boolean a = user1.getauthority();
        a.equals(false);
    }

}
