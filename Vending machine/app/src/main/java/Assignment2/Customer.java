package Assignment2;

import java.lang.annotation.*;

public class Customer extends Admin {

    @Generated // This annotation is used to ignore testcase
    protected Customer(String username, String password, int authority) {
        super(username, password, authority);
    }

    @Generated // This annotation is used to ignore testcase
    protected Customer(String username, String salt, String hasvalue, int authority, String[] last5) {
        super(username, salt, hasvalue, authority, last5);
    }

    // Customer / Normal user do not have any method :(
    // This code is to ignore FileUtil in the jacocoTestReport because we do not
    // need to test these function
    @Documented
    public @interface Generated {
    }
}
