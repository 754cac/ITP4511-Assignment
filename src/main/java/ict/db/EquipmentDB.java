package ict.db;

import ict.Bean.EquipmentBean;
import ict.Bean.EquipmentTypeBean;
import ict.Bean.UserBean;
import java.io.IOException;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDB {

    private DBConnection connector;

    public EquipmentDB(DBConnection connector) {
        this.connector = connector;
    }

    // Create operation
    public boolean addEquipment(EquipmentBean equipment) {
        String sql = "INSERT INTO equipment (EquipmentID, EquipmentName, EquipmentType, Description, SerialNumber, AcquisitionDate, Status, Campus) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
        try (Connection conn = connector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
        try (Connection conn = connector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
        try (Connection conn = connector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, equipmentID);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public EquipmentTypeBean readEquipmentType(String equipmentID) {
        // Correct the SQL query to fetch both TypeName and Description
        String sql = "SELECT et.EquipmentTypeID, et.TypeName, et.Description FROM equipment e "
                + "JOIN equipment_types et ON e.EquipmentTypeID = et.EquipmentTypeID "
                + "WHERE e.EquipmentID = ?";

        try (Connection conn = connector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, equipmentID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // Create a new EquipmentTypeBean object and set its properties
                EquipmentTypeBean equipmentType = new EquipmentTypeBean();
                equipmentType.setEquipmentTypeID(rs.getString("EquipmentTypeID"));
                equipmentType.setTypeName(rs.getString("TypeName"));
                equipmentType.setDescription(rs.getString("Description"));
                return equipmentType;  // Return the fully populated EquipmentTypeBean
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null; // Return null if no records found or if there's an exception
    }

    public boolean createEquipmentType(EquipmentTypeBean equipmentType) {
        // Notice EquipmentTypeID is not included in the SQL statement
        String sql = "INSERT INTO equipmenttype (TypeName, Description) VALUES (?, ?)";
        try {
            Connection conn = connector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, equipmentType.getTypeName());
            pstmt.setString(2, equipmentType.getDescription());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Handle SQL exceptions and return false if operation fails
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Handle SQL exceptions and return false if operation fails
        }
    }

    public boolean updateEquipmentType(EquipmentTypeBean equipmentType) {
        String sql = "UPDATE equipmenttype SET TypeName = ?, Description = ? WHERE EquipmentTypeID = ?";
        try (Connection conn = connector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, equipmentType.getTypeName());
            pstmt.setString(2, equipmentType.getDescription());
            pstmt.setString(3, equipmentType.getEquipmentTypeID());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Handle SQL exceptions and return false if operation fails
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Handle SQL exceptions and return false if operation fails
        }
    }

    public boolean deleteEquipmentType(String equipmentTypeID) {
        String sql = "DELETE FROM equipmenttype WHERE EquipmentTypeID = ?";
        try (Connection conn = connector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, equipmentTypeID);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Handle SQL exceptions and return false if operation fails
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Handle SQL exceptions and return false if operation fails
        }
    }
    
    public List<EquipmentBean> getAllEquipments() {
        List<EquipmentBean> equipments = new ArrayList<>();
        String sql = "SELECT * FROM equipment";
        try (Connection conn = connector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                EquipmentBean equipment = new EquipmentBean();
                equipment.setEquipmentID(rs.getString("EquipmentID"));
                equipment.setEquipmentName(rs.getString("EquipmentName"));
                equipment.setDescription(rs.getString("Description"));
                equipment.setSerialNumber(rs.getString("SerialNumber"));
                equipment.setAcquisitionDate(rs.getDate("AcquisitionDate"));
                equipment.setStatus(rs.getString("Status"));
                equipment.setCampus(rs.getString("Campus"));
                equipments.add(equipment);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return equipments;
    }
    
    public List<EquipmentBean> getAllAvailableEquipments() {
        List<EquipmentBean> equipments = new ArrayList<>();
        String sql = "SELECT Equipment.*, Campus.CampusName " +
                        "FROM Equipment " +
                        "JOIN Campus ON Equipment.CampusID = Campus.CampusID " +
                        "WHERE Equipment.Status = 'Available';";
        try (Connection conn = connector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                EquipmentBean equipment = new EquipmentBean();
                equipment.setEquipmentID(rs.getString("EquipmentID"));
                equipment.setEquipmentName(rs.getString("EquipmentName"));
                equipment.setDescription(rs.getString("Description"));
                equipment.setSerialNumber(rs.getString("SerialNumber"));
                equipment.setAcquisitionDate(rs.getDate("AcquisitionDate"));
                equipment.setStatus(rs.getString("Status"));
                equipment.setCampus(rs.getString("CampusName"));
                equipments.add(equipment);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return equipments;
    }
}
