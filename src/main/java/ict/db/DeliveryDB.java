package ict.db;

import ict.Bean.BookingBean;
import ict.Bean.DeliveryBean;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class DeliveryDB {

    private DBConnection connector;

    public DeliveryDB(DBConnection connector) {
        this.connector = connector;
    }

    // Create a new delivery
    public boolean createDelivery(DeliveryBean delivery) {
        String sql = "INSERT INTO deliveries (BookingID, CourierID, PickupLocation, DeliveryLocation, PickupDate, DeliveryDate, Status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connector.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, delivery.getBookingID());
            ps.setString(2, delivery.getCourierID());
            ps.setString(3, delivery.getPickupLocation());
            ps.setString(4, delivery.getDeliveryLocation());
            ps.setDate(5, Date.valueOf(delivery.getPickupDate()));
            ps.setDate(6, Date.valueOf(delivery.getDeliveryDate()));
            ps.setString(7, delivery.getStatus());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating delivery failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    delivery.setDeliveryID(generatedKeys.getString(1));
                } else {
                    throw new SQLException("Creating delivery failed, no ID obtained.");
                }
            }
            return true;
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // Read a delivery by ID
    public DeliveryBean getDelivery(String deliveryID) {
        String sql = "SELECT * FROM deliveries WHERE DeliveryID = ?";
        try (Connection conn = connector.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, deliveryID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                DeliveryBean delivery = new DeliveryBean();
                delivery.setDeliveryID(rs.getString("DeliveryID"));
                // Assuming we have a method to fetch a BookingBean by its ID
                delivery.setBookingID(rs.getString("BookingID"));
                delivery.setCourierID(rs.getString("CourierID"));
                delivery.setPickupLocation(rs.getString("PickupLocation"));
                delivery.setDeliveryLocation(rs.getString("DeliveryLocation"));
                delivery.setPickupDate(rs.getDate("PickupDate").toLocalDate());
                delivery.setDeliveryDate(rs.getDate("DeliveryDate").toLocalDate());
                delivery.setStatus(rs.getString("Status"));
                return delivery;
            }
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Update an existing delivery
    public boolean updateDelivery(DeliveryBean delivery) {
        String sql = "UPDATE deliveries SET BookingID = ?, CourierID = ?, PickupLocation = ?, DeliveryLocation = ?, PickupDate = ?, DeliveryDate = ?, Status = ? WHERE DeliveryID = ?";
        try (Connection conn = connector.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, delivery.getBookingID());
            ps.setString(2, delivery.getCourierID());
            ps.setString(3, delivery.getPickupLocation());
            ps.setString(4, delivery.getDeliveryLocation());
            ps.setDate(5, Date.valueOf(delivery.getPickupDate()));
            ps.setDate(6, Date.valueOf(delivery.getDeliveryDate()));
            ps.setString(7, delivery.getStatus());
            ps.setString(8, delivery.getDeliveryID());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // Delete a delivery
    public boolean deleteDelivery(String deliveryID) {
        String sql = "DELETE FROM deliveries WHERE DeliveryID = ?";
        try (Connection conn = connector.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, deliveryID);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
            return false;
        }

    }
}
