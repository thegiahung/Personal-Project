package Assignment_1;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyTest {
    Currency curr1 = new Currency("USD", true );
    Currency curr2 = new Currency("AUD", true );
    Currency curr3 = new Currency("JPY", false );

    // Test if return the country of the currency
    @Test
    public void TestGetName() {
        assertNotNull(curr1.getName());
        assertNotNull(curr2.getName());
    }

    // Test if return the currency is set to new name
    @Test
    public void TestSetName() {
        curr1.setName("VND");
        curr1.getName().equals("VND");
        assertFalse(curr1.getName().equals(curr2.getName()));
    }

    // Test if the currency is popular
    @Test
    public void TestGetIsPopular() {
        assertTrue(curr1.getIsPopular());
        assertFalse(curr3.getIsPopular());
    }

    // Test if the currency is already set to popular
    @Test
    public void TestSetIsPopular() {
        curr3.setIsPopular(true);
        assertTrue(curr3.getIsPopular());
    }

    // Test if we can read the rate from the currency
    @Test
    public void TestReadRate() {
        Currency curr1 = new Currency("USD", true );
        Currency curr2 = new Currency("AUD", true);
        Rate_date rate1 = new Rate_date(0.9, "2022-09-13");
        Rate_date rate2 = new Rate_date(1.1, "2022-09-13");
        List<Rate_date> l = new ArrayList<>();
        l.add(rate1);
        l.add(rate2);
        curr1.rate.put("USD", l);
        curr1.readRate(0.2, curr1, "2022-09-11" );
    }

    // Test if we cna set the new rate for the currency
    @Test
    public void TestSetRate() {
        Rate_date rate1 = new Rate_date(0.9, "2022-09-13");
        Rate_date rate2 = new Rate_date(1.1, "2022-09-13");
        Currency curr1 = new Currency("USD", true );
        List<Rate_date> l = new ArrayList<>();
        l.add(rate1);
        l.add(rate2);
        curr1.rate.put("USD", l);

        curr1.setnewRate(0.2, curr1);
    }

    // Test get the current rate of currency
    @Test
    public void TestGetCurrentRate() {
        Rate_date rate1 = new Rate_date(0.9, "2022-09-13");
        Rate_date rate2 = new Rate_date(1.1, "2022-09-13");
        Currency curr1 = new Currency("USD", true);
        List<Rate_date> l = new ArrayList<>();
        l.add(rate1);
        l.add(rate2);
        curr1.rate.put("USD", l);
        curr1.getCurrentRate(curr1, true);
    }

    // Test get the current rate of currency
    @Test
    public void TestGetCurrentRate2() {
        Rate_date rate1 = new Rate_date(0.9, "2022-09-13");
        Rate_date rate2 = new Rate_date(1.1, "2022-09-13");
        Currency curr1 = new Currency("USD", false);
        List<Rate_date> l = new ArrayList<>();
        l.add(rate1);
        l.add(rate2);
        curr1.rate.put("USD", l);
        curr1.getCurrentRate(curr1, false);
    }

//    // Test get the history rate of currency
    @Test
    public void TestGetHistory() {
        Rate_date rate1 = new Rate_date(0.9, "2022-09-13");
        Rate_date rate2 = new Rate_date(1.1, "2022-09-13");
        Currency curr1 = new Currency("USD", true );
        List<Rate_date> l = new ArrayList<>();
        l.add(rate1);
        l.add(rate2);
        curr1.rate.put("USD", l);
        curr1.setnewRate(0.2, curr1);
        curr1.getHistoryData(curr1);
    }


}
