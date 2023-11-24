package Assignment_1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RateDateTest {
    Rate_date rate1 = new Rate_date(0.9, "2022-09-13");
    Rate_date rate2 = new Rate_date(1.1, "2022-09-13");

    // Test if return the rate
    @Test
    public void TestGetRate() {
        assertNotNull(rate1.getRate());
        assertNotNull(rate2.getRate());
    }

     // Test if return the date
     @Test
     public void TestGetDate() {
         rate2.getDate();
         rate1.getDate();
     }

}
