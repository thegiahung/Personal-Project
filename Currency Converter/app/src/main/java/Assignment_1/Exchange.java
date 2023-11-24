package Assignment_1;

import java.util.List;
import java.util.HashMap;

public class Exchange {
    private String date; // record date
    private String currency_1; // first currency
    private String currency_2; // second currency
    private double exchangeRate; // currency rate
    // private String upOrDown; //checking increase or decrease compared to before

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public Exchange(String date, String currency_1, String currency_2, Double exchangeRate) {
        this.date = date;
        this.currency_1 = currency_1;
        this.currency_2 = currency_2;
        this.exchangeRate = exchangeRate;
        // this.upOrDown = upOrDown;
    }

    public Exchange(String digit[]) {
        this.date = digit[0];
        this.currency_1 = digit[1];
        this.currency_2 = digit[2];
        this.exchangeRate = Double.parseDouble(digit[3]);
    }

    public Exchange() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCurrency_1() {
        return currency_1;
    }

    public void setCurrency_1(String currency_1) {
        this.currency_1 = currency_1;
    }

    public String getCurrency_2() {
        return currency_2;
    }

    public void setCurrency_2(String currency_2) {
        this.currency_2 = currency_2;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Updating the correspond currencies simultaneously
    public Exchange getReverseCurrency() {
        Exchange exchange = new Exchange();
        exchange.setDate(this.getDate());
        exchange.setCurrency_1(this.getCurrency_2());
        exchange.setCurrency_2(this.getCurrency_1());
        double reverse = 1 / this.getExchangeRate(); // reverse the exchange rate by 1 / currency rate
        if ((reverse + "").contains(".")) {
            String strArr[] = (reverse + "").split("\\.");
            String newStr = "";
            if (strArr[1].length() > 2) {
                newStr = strArr[0] + "." + strArr[1].substring(0, 2);
            } else {
                newStr = strArr[0] + "." + strArr[1];
            }
            exchange.setExchangeRate(Double.parseDouble(newStr));
        }
        exchange.setExchangeRate(reverse);
        return exchange;
    }

    public String getInfo() {
        return this.date + "," + this.currency_1 + "," + this.currency_2 + "," + this.exchangeRate;
    }

    @Override
    public String toString() {
        return "{" +
                "insertDate='" + date + '\'' +
                ", country1='" + currency_1 + '\'' +
                ", country2='" + currency_2 + '\'' +
                ", exchangeRate=" + exchangeRate +
                '}';
    }

}
