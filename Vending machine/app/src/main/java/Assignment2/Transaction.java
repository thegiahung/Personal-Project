package Assignment2;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Data in each transaction instance:
 * 1. Date & Time
 * 2. item sold
 * 3. Status (Finished or cancelled)
 * 4. Amount of money paid
 * 5. Returned change (for card it is 0)
 * 6. Payment method
 */
public class Transaction {
    public String date;
    /*
     * Finished = 1;
     * have not finished yet = 0;
     * timeout = -1;
     * user canncelled = -2
     * change not available = -3
     * other = -4
     */
    private int status;
    private double paid;
    private double total_price;
    private double returned;
    // True: card, False: cash
    private boolean paymentMethod;
    private String date_time;

    // Items sold
    private String sold;

    private java.text.SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");

    public Transaction() {
        this.date_time = formatter.format(new Date());
        this.status = 0;
        this.paid = 0;
        this.total_price = 0;
        this.returned = 0;
    }

    // timeout = -1; user canncelled = -2; change not available = -3; other = -4
    public void cancellTs(int reason) {
        if (reason >= 0) {
            return;
        }

        this.status = reason;
        return;
        // The only attributes available in a cancelled transaction were date&time and
        // status
    }

    public void finisheTs(String sold, double paid, double total_price, double returned, boolean method) {
        this.sold = sold;
        this.paid = paid;
        this.total_price = total_price;
        this.returned = returned;
        this.status = 1;
        this.paymentMethod = method;
    }

    public int getStatus() {
        return this.status;
    }

    public String getDate_time() {
        return this.date_time;
    }

    public double getPaid() {
        return this.paid;
    }

    public double getTotalPrice() {
        return this.total_price;
    }

    public double getReturned() {
        return this.returned;
    }

    public String getsold() {
        return sold;
    }

    // True: card, False: cash
    public boolean getPaymentMethod() {
        return paymentMethod;
    }
}
