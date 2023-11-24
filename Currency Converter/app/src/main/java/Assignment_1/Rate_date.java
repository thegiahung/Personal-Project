package Assignment_1;

import java.util.Date;

import Assignment_1.Currency;

import java.text.SimpleDateFormat;

// This object is storing in
public class Rate_date {
    double exchange_rate;
    // Date format: YYYY-MM-DD
    String date;

    public Rate_date(double exchange_rate, String date) {
        this.exchange_rate = exchange_rate;
        this.date = date;
    }

    public double getRate() {
        return this.exchange_rate;
    }

    // Returned string in YYYY-MM-DD format
    public String getDate() {
        return this.date;
    }
}