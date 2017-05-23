package dao;

import com.mysql.jdbc.JDBC4Connection;
import entity.Balance;

import java.sql.*;
import java.util.ArrayList;

import enums.Currency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hazard on 17.05.2017.
 */
public class BalanceDAO {

    private Connection connection;

    public BalanceDAO() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wallet", "root", "1234");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int add(Balance balance) {
        int resid = 0;
        try {

            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO balance (date,currency,amount,product) VALUES (?,?,?,?)");
            ps.setDate(1, balance.getDate());
            ps.setString(2, balance.getCurrency().toString());
            ps.setDouble(3, balance.getAmount());
            ps.setString(4, balance.getProduct());
            resid = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resid;
    }

    public Map<Currency, Double> getAmountAndCurrency() {
        Map<Currency, Double> result = new HashMap<Currency, Double>();
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT amount,currency FROM balance");
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Currency key = Currency.valueOf(rs.getString("currency"));
                result
                        .put(key
                                , rs.getDouble("amount") + (result.containsKey(key) ? result.get(key) : 0));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Balance> list() {
        List<Balance> result = new ArrayList<Balance>();
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM balance ORDER BY date");
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                result.add(
                        new Balance(
                                rs.getDate("date")
                                , Currency.valueOf(rs.getString("currency"))
                                , rs.getDouble("amount")
                                , rs.getString("product")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int deleteByDate(Date date) {
        int rowsDeleted = 0;
        try {
            PreparedStatement ps = connection
                    .prepareStatement("DELETE FROM balance WHERE date=?");
            ps.setDate(1, date);
            rowsDeleted = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsDeleted;
    }
}
