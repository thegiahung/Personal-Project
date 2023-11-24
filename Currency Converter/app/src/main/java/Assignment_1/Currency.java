package Assignment_1;

import java.util.List;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;

public class Currency {

    private String name;
    public HashMap<String, List<Rate_date>> rate = new HashMap<String, List<Rate_date>>();
    private Boolean isPopular;

    private java.text.SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD ");

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public Currency(String name, Boolean isPopular) {
        this.name = name;
        this.isPopular = isPopular;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // return and get name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Y means it is popular, N means not
    public Boolean getIsPopular() {
        return isPopular;
    }

    public void setIsPopular(Boolean isPopular) {
        this.isPopular = isPopular;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Exchange rate = destination_currceny / this currency

    /*
     * Date format: YYYY-MM-DD
     * This function is used to read exchange rate in database. In that case, the
     * rate is set by reading a YYYY-MM-DD string.
     */
    public void readRate(Double new_rate, Currency destination_currency, String YYYY_MM_DD) {

        rate.get(destination_currency.getName()).add(new Rate_date(new_rate, YYYY_MM_DD));
        destination_currency.rate.get(this.getName()).add(new Rate_date(1 / new_rate, YYYY_MM_DD));
    }

    /*
     * This format is used to set a new rate, so the date gain from "new Date()"
     */
    public void setnewRate(double new_rate, Currency destination_currency) {
        String YYYY_MM_DD;

        YYYY_MM_DD = formatter.format(new Date());
        try {
            rate.get(destination_currency.getName()).add(new Rate_date(new_rate, YYYY_MM_DD));
            destination_currency.rate.get(this.getName()).add(new Rate_date(1 / new_rate, YYYY_MM_DD));
        } catch (Exception e) {
            rate.put(destination_currency.getName(), new ArrayList<Rate_date>());
            destination_currency.rate.put(this.getName(), new ArrayList<Rate_date>());
            rate.get(destination_currency.getName()).add(new Rate_date(new_rate, YYYY_MM_DD));
            destination_currency.rate.get(this.getName()).add(new Rate_date(1 / new_rate, YYYY_MM_DD));
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /*
     * Exchange rate = destination_currceny / this currency
     * If want to get current exchange rate, current == ture
     * If want to get last exchange rate, current = false
     */
    public double getCurrentRate(Currency destination_currency, Boolean current) {
        int i;
        if (current) {
            i = 1;
        } else {
            i = 2;
        }
        List<Rate_date> h_rate_date = rate.get(destination_currency.getName());
        double current_rate = h_rate_date.get(h_rate_date.size() - i).getRate();

        return current_rate;
    }

    // Return a list store history data of the the exchange rate
    public List<Rate_date> getHistoryData(Currency destination_currency) {
        return this.rate.get(destination_currency.getName());
    }
}