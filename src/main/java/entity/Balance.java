package entity;

import enums.Currency;

import java.sql.Date;



/**
 * Created by Hazard on 17.05.2017.
 */
public class Balance {

    private Date date;
    private Currency currency;
    private double amount;
    private String product;

    public Balance(Date date, Currency currency, double amount, String product) {
        this.date = date;
        this.currency = currency;
        this.amount = amount;
        this.product = product;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return date +
                " " + product +
                " " + amount +
                " " + currency ;
    }
}
