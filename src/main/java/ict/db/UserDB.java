package ict.db;

import java.io.IOException;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB {

    private String dburl;
    private String dbUser;
    private String dbPassword;

    public UserDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;

    }

    public Connection getConnection() throws SQLException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }

    public boolean isValidUser(String user, String pwd) {
        boolean isValid = false;
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        String preQueryStatement
                = "SELECT * FROM users WHERE Email=? and Password=?";
        try {
            cnnct = getConnection();
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, user);
            pStmnt.setString(2, pwd);
            ResultSet rs = pStmnt.executeQuery();
            if (rs.next()) {
                isValid = true;
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return isValid;
    }

    public boolean addUser(
            String name,
            String email,
            String password,
            String role,
            int campusId
    ) {
        String query
                = "INSERT INTO Users (Name, Email, Password, Role, CampusID) VALUES (?, ?, ?, ?, ?)";
        try (
                Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, role);
            stmt.setInt(5, campusId);
            int rowCount = stmt.executeUpdate();
            return rowCount > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(
            int userId,
            String name,
            String email,
            String password,
            String role,
            int campusId
    ) {
        String query
                = "UPDATE Users SET Name=?, Email=?, Password=?, Role=?, CampusID=? WHERE UserID=?";
        try (
                Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, role);
            stmt.setInt(5, campusId);
            stmt.setInt(6, userId);
            int rowCount = stmt.executeUpdate();
            return rowCount > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(int userId) {
        String query = "DELETE FROM Users WHERE UserID=?";
        try (
                Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            int rowCount = stmt.executeUpdate();
            return rowCount > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return false;
    }

    public ResultSet getUser(int userId) {
        String query = "SELECT * FROM Users WHERE UserID=?";
        try (
                Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            return stmt.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return null;
    }
}
