package service;

import com.google.gson.Gson;
import dao.BalanceDAO;
import entity.Balance;
import enums.Currency;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by Hazard on 17.05.2017.
 */
public class BalanceService {

    private BalanceDAO balanceDAO;

    public BalanceService() {
        balanceDAO = new BalanceDAO();
    }

    public void add(String date, String amount, String currency, String product) {
        try {
            Balance balance =
                    new Balance(parseDate(date)
                            , Currency.valueOf(currency)
                            , Double.parseDouble(amount)
                            , product);
            balanceDAO.add(balance);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void printExpenses() {
        List<Balance> result = balanceDAO.list();
        Date curDate = null;
        for (Balance balance : result) {
            if (!balance.getDate().equals(curDate)) {
                curDate = balance.getDate();
                System.out.println("\n" + curDate);
            }
            System.out.println(balance.getProduct()
                    + " " + balance.getAmount()
                    + " " + balance.getCurrency());

        }


    }

    public void delete(String date) {
        try {
            balanceDAO.deleteByDate(parseDate(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private Date parseDate(String dateToParse) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return new Date(formatter.parse(dateToParse).getTime());
    }

    public Double total(Currency currency) {
        Map<Currency, Double> result = balanceDAO.getAmountAndCurrency();
        Double sum = 0.0;
        CurrencyAPIResponse currencyAPIResponse = getCurrencies(currency);
        for (Map.Entry<Currency, Double> entry : result.entrySet()) {
            sum = sum +
                    entry.getValue()
                            / (!currency.equals(entry.getKey()) ?
                            Double.parseDouble(
                                    currencyAPIResponse.rates.get(entry.getKey().toString())) : 1);
        }
        return sum;
    }

    //request to fixer.io API to get ratio for base currency
    private CurrencyAPIResponse getCurrencies(Currency currency) {
        String url = "http://api.fixer.io/latest?base=" + currency;
        URL obj;
        CurrencyAPIResponse result = null;
        try {
            obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            Gson gson = new Gson();
            BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
            StringBuilder sb = new StringBuilder();
            while (bis.available() > 0) {
                sb.append((char) bis.read());
            }
            result = gson.fromJson(sb.toString(), CurrencyAPIResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Balance> list() {
        return balanceDAO.list();
    }

    private class CurrencyAPIResponse {

        public String base;
        public String date;
        public Map<String, String> rates;

        @Override
        public String toString() {
            return "CurrencyAPIResponse{" +
                    "base='" + base + '\'' +
                    ", date='" + date + '\'' +
                    ", rates=" + rates +
                    '}';
        }
    }


}
