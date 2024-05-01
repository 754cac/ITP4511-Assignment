package ict.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static String dburl;
    private static String dbUser;
    private static String dbPassword;

    public DBConnection(String url, String user, String password) {
        dburl = url;
        dbUser = user;
        dbPassword = password;
    }

    public static Connection getConnection() throws SQLException, IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Driver not found");
        }
        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }
}
