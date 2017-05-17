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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by Hazard on 17.05.2017.
 */
public class BalanceService {

    private BalanceDAO balanceDAO;
    public BalanceService(){
        balanceDAO = new BalanceDAO();
    }

    public void add(String date,String amount,String currency,String product)  {
        SimpleDateFormat sdf = new SimpleDateFormat();
        try {
            Balance balance = new Balance((Date) sdf.parse(date),Currency.valueOf(currency), Double.parseDouble(amount), product);
            balanceDAO.add(balance);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    public void delete(String date){
        SimpleDateFormat sdf = new SimpleDateFormat();
        try {
            balanceDAO.deleteByDate((Date) sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void total(Currency currency){

        String url = "http://api.fixer.io/latest";
        URL obj;
        try {
            obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            Gson gson= new Gson();
            BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
            StringBuilder sb = new StringBuilder();
            while(bis.available()>0) {
                sb.append((char)bis.read());
            }
            CurrencyAPIResponse response = gson.fromJson(sb.toString(),CurrencyAPIResponse.class);
            System.out.println(response);
            System.out.println(response.rates.get("USD"));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
