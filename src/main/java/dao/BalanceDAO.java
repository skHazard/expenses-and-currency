package dao;

import com.mysql.jdbc.JDBC4Connection;
import entity.Balance;

import java.sql.*;

/**
 * Created by Hazard on 17.05.2017.
 */
public class BalanceDAO {

    private Connection connection;

    public BalanceDAO(){
        try {
            connection =  DriverManager.getConnection("jdbc:mysql://localhost:3306");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int add(Balance balance){
        int resid = 0;
        try {

            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO TABLE Balance (date,currency,amount,product) VALUES (?,?,?,?)");
            ps.setDate(1,balance.getDate());
            ps.setString(2,balance.getCurrency().toString());
            ps.setDouble(3,balance.getAmount());
            ps.setString(4,balance.getProduct());
            resid = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resid;
    }

    public int deleteByDate(Date date){
        int resid = 0;
        try {
            PreparedStatement ps = connection
                    .prepareStatement("DELETE FROM Balance WHERE date=?");
            ps.setDate(1,date);
            resid = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resid;
    }
}
