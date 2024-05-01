package ict.db;

import ict.Bean.DamageReportBean;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;

public class DamageReportDB {

    private String dburl;
    private String dbUser;
    private String dbPassword;

    public DamageReportDB(String dburl, String dbUser, String dbPassword) {
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

    // Create a new damage report
    public boolean createDamageReport(DamageReportBean damageReport) {
        String sql = "INSERT INTO damagereports (EquipmentID, BookingID, TechnicianID, DamageDescription, DamageDate, Status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, damageReport.getEquipmentID());
            stmt.setString(2, damageReport.getBookingID());
            stmt.setString(3, damageReport.getTechnicianID());
            stmt.setString(4, damageReport.getDamageDescription());
            stmt.setDate(5, Date.valueOf(damageReport.getDamageDate()));
            stmt.setString(6, damageReport.getStatus());

            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // Read a damage report by ID
    public DamageReportBean getDamageReport(String damageReportID) {
        String sql = "SELECT * FROM damagereports WHERE DamageReportID = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, damageReportID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                DamageReportBean report = new DamageReportBean();
                report.setDamageReportID(rs.getString("DamageReportID"));
                report.setEquipmentID(rs.getString("EquipmentID"));
                report.setBookingID(rs.getString("BookingID"));
                report.setTechnicianID(rs.getString("TechnicianID"));
                report.setDamageDescription(rs.getString("DamageDescription"));
                report.setDamageDate(rs.getDate("DamageDate").toLocalDate());
                report.setStatus(rs.getString("Status"));
                return report;
            }
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Update an existing damage report
    public boolean updateDamageReport(DamageReportBean damageReport) {
        String sql = "UPDATE damagereports SET EquipmentID = ?, BookingID = ?, TechnicianID = ?, DamageDescription = ?, DamageDate = ?, Status = ? WHERE DamageReportID = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, damageReport.getEquipmentID());
            stmt.setString(2, damageReport.getBookingID());
            stmt.setString(3, damageReport.getTechnicianID());
            stmt.setString(4, damageReport.getDamageDescription());
            stmt.setDate(5, Date.valueOf(damageReport.getDamageDate()));
            stmt.setString(6, damageReport.getStatus());
            stmt.setString(7, damageReport.getDamageReportID());

            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // Delete a damage report
    public boolean deleteDamageReport(String damageReportID) {
        String sql = "DELETE FROM damagereports WHERE DamageReportID = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, damageReportID);
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
