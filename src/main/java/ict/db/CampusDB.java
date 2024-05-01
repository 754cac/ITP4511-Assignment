package ict.db;

import ict.Bean.CampusBean;
import java.io.IOException;
import java.sql.*;

public class CampusDB {

    private String dburl;
    private String dbUser;
    private String dbPassword;

    public CampusDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public Connection getConnection() throws SQLException, IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            throw new SQLException("Driver not found");
        }
        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }

    // Create a new campus
    public boolean createCampus(CampusBean campus) {
        String sql = "INSERT INTO campus (CampusName, Location) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, campus.getCampusName());
            ps.setString(2, campus.getLocation());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating campus failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    campus.setCampusID(generatedKeys.getString(1));
                } else {
                    throw new SQLException("Creating campus failed, no ID obtained.");
                }
            }
            return true;
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // Read a campus by ID
    public CampusBean getCampus(int campusID) {
        String sql = "SELECT * FROM campus WHERE CampusID = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, campusID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                CampusBean campus = new CampusBean();
                campus.setCampusID(rs.getString("CampusID"));
                campus.setCampusName(rs.getString("CampusName"));
                campus.setLocation(rs.getString("Location"));
                return campus;
            }
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Update an existing campus
    public boolean updateCampus(CampusBean campus) {
        String sql = "UPDATE campus SET CampusName = ?, Location = ? WHERE CampusID = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, campus.getCampusName());
            ps.setString(2, campus.getLocation());
            ps.setInt(3, Integer.parseInt(campus.getCampusID()));

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // Delete a campus
    public boolean deleteCampus(int campusID) {
        String sql = "DELETE FROM campus WHERE CampusID = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, campusID);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
