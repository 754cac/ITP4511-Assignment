package ict.db;

import ict.Bean.UserBean;
import java.io.IOException;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB {

    private DBConnection connector;

    public UserDB(DBConnection connector) {
        this.connector = connector;
    }

    public UserBean isValidUser(String email, String pwd) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        String preQueryStatement = "SELECT * FROM users WHERE Email=? and Password=?";
        try {
            cnnct = connector.getConnection();
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, email);
            pStmnt.setString(2, pwd);
            ResultSet rs = pStmnt.executeQuery();
            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (pStmnt != null) pStmnt.close();
                if (cnnct != null) cnnct.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public UserBean getUserById(String id) {
        String query = "SELECT * FROM users WHERE UserID=?";
        try (Connection conn = connector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public UserBean getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE Email=?";
        try (Connection conn = connector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean addUser(UserBean user) {
        String query = "INSERT INTO users (Name, Email, Password, Role, CampusId) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
//set the initial password as email, let user update password at latter time
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getRole());
            stmt.setString(5, user.getCampusId());
            int rowCount = stmt.executeUpdate();
            return rowCount > 0;
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean deleteUserById(String id) {
        String query = "DELETE FROM users WHERE UserID=?";
        try (Connection conn = connector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            int rowCount = stmt.executeUpdate();
            return rowCount > 0;
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean deleteUserByEmail(String email) {
        String query = "DELETE FROM users WHERE Email=?";
        try (Connection conn = connector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            int rowCount = stmt.executeUpdate();
            return rowCount > 0;
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private UserBean extractUserFromResultSet(ResultSet rs) throws SQLException {
        UserBean user = new UserBean();
        user.setUserId(rs.getString("UserId"));
        user.setName(rs.getString("Name"));
        user.setEmail(rs.getString("Email"));
        user.setPassword(rs.getString("Password"));
        user.setRole(rs.getString("Role"));
        user.setCampusId(rs.getString("CampusId"));
        return user;
    }

    public boolean updateUser(UserBean user) {
        // SQL query to update user details
        String query = "UPDATE users SET Name=?, Email=?, Password=?, Role=?, CampusId=? WHERE UserID=?";
        try (Connection conn = connector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set parameters for the prepared statement
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            stmt.setString(5, user.getCampusId());
            stmt.setString(6, user.getUserId()); // Include UserID at the end for the WHERE clause

            // Execute the update
            int rowCount = stmt.executeUpdate();
            return rowCount > 0; // Return true if the update was successful
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
