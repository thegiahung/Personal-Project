package Assignment_1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExchangeTest {
    Exchange e1 = new Exchange("2022/09/13", "JPY", "USD", 0.9);
    Exchange e2 = new Exchange("2022/09/12", "VND", "AUD",0.6);

    // Test if the user is existed or not
    @Test
    public void TestGetInfo() {
        e1.getInfo();
        e2.getInfo();
    }

    // Test if the user is existed or not
    @Test
    public void TestToString() {

        e1.toString();
    }

    // Test if the user is existed or not
    @Test
    public void TestConstructor() {
        String[] digit = new String[5];
        digit[0] = "2022-09-09";
        digit[1] = "JPY";
        digit[2] = "AUD";
        digit[3] = "0.6";
        digit[4] = "y";
        Exchange e1 = new Exchange(digit);
    }

    // Test if the user is existed or not
    @Test
    public void TestConstructor2() {
        String[] digit = new String[5];
        digit[0] = "2022-09-09";
        digit[1] = "JPY";
        digit[2] = "AUD";
        digit[3] = "0.6";
        digit[4] = "n";
        Exchange e1 = new Exchange(digit);
    }

    // Test if it show the date
    @Test
    public void TestGetDate() {
        e1.getDate();
    }

    // Test if we can set the date
    @Test
    public void TestSetDate() {
        e1.setDate("2022-09-12");
    }

    // Test if it show curr 1
    @Test
    public void TestGetCurr1() {
        e1.getCurrency_1();
    }

    // Test if we can set the curr 1
    @Test
    public void TestSetCurr1() {
        e1.setCurrency_1("VND");
    }

    // Test if it show curr 2
    @Test
    public void TestGetCurr2() {
        e1.getCurrency_2();
    }

    // Test if we can set the curr 2
    @Test
    public void TestSetCurr2() {
        e1.setCurrency_2("VND");
    }

    // Test if we can get exchange rate
    @Test
    public void TestGetExRate() {
        e1.getExchangeRate();
    }

    // Test if we can get exchange rate
    @Test
    public void TestSetExRate() {
        e1.setExchangeRate(0.6);
    }

    // Test if we can get reverse curr
    @Test
    public void TestGetReverse() {
        e1.getReverseCurrency();
    }
}
