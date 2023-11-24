package Assignment2;

import org.junit.jupiter.api.Test;

public class TransactionTest {
    Transaction t1 = new Transaction();

    @Test
    public void TestGetDate() {
        t1.getDate_time();
    }

    @Test
    public void TestGetStatus() {
        t1.getStatus();
    }

    @Test
    public void TestGetPrice() {
        t1.getTotalPrice();
        t1.cancellTs(1);
    }

    @Test
    public void TestGetSold() {
        t1.getsold();
        t1.cancellTs(-2);
    }

    @Test
    public void TestGetStatus1() {
        t1.getStatus();
        t1.finisheTs("Coca", 3.0, 2.0, 1.0, false);
    }

}
