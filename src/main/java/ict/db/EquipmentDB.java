package ict.db;

import ict.Bean.EquipmentBean;
import ict.Bean.UserBean;
import java.io.IOException;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EquipmentDB {

    private String dburl;
    private String dbUser;
    private String dbPassword;

    public EquipmentDB(String dburl, String dbUser, String dbPassword) {
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

    // Create operation
    public boolean addEquipment(EquipmentBean equipment) {
        String sql = "INSERT INTO equipment (EquipmentID, EquipmentName, EquipmentType, Description, SerialNumber, AcquisitionDate, Status, Campus) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, equipment.getEquipmentID());
            pstmt.setString(2, equipment.getEquipmentName());
            pstmt.setString(3, equipment.getEquipmentType());
            pstmt.setString(4, equipment.getDescription());
            pstmt.setString(5, equipment.getSerialNumber());
            pstmt.setDate(6, new Date(equipment.getAcquisitionDate().getTime()));
            pstmt.setString(7, equipment.getStatus());
            pstmt.setString(8, equipment.getCampus());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Read operation
    public EquipmentBean getEquipment(String equipmentID) {
        String sql = "SELECT * FROM equipment WHERE EquipmentID = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, equipmentID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                EquipmentBean equipment = new EquipmentBean();  // Instantiate using default constructor

                // Set properties using setters
                equipment.setEquipmentID(rs.getString("EquipmentBeanID"));
                equipment.setEquipmentName(rs.getString("EquipmentBeanName"));
                equipment.setEquipmentType(rs.getString("EquipmentBeanType"));
                equipment.setDescription(rs.getString("Description"));
                equipment.setSerialNumber(rs.getString("SerialNumber"));
                equipment.setAcquisitionDate(rs.getDate("AcquisitionDate")); // Assuming this setter accepts java.sql.Date
                equipment.setStatus(rs.getString("Status"));
                equipment.setCampus(rs.getString("Campus"));
                return equipment;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update operation
    public boolean updateEquipment(EquipmentBean equipment) {
        String sql = "UPDATE equipment SET EquipmentName = ?, EquipmentType = ?, Description = ?, SerialNumber = ?, AcquisitionDate = ?, Status = ?, Campus = ? WHERE EquipmentID = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, equipment.getEquipmentName());
            pstmt.setString(2, equipment.getEquipmentType());
            pstmt.setString(3, equipment.getDescription());
            pstmt.setString(4, equipment.getSerialNumber());
            pstmt.setDate(5, new Date(equipment.getAcquisitionDate().getTime()));
            pstmt.setString(6, equipment.getStatus());
            pstmt.setString(7, equipment.getCampus());
            pstmt.setString(8, equipment.getEquipmentID());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete operation
    public boolean deleteEquipment(String equipmentID) {
        String sql = "DELETE FROM equipment WHERE EquipmentID = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, equipmentID);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
