package ict.db;

import ict.Bean.BookingBean;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class BookingDB {

    private String dburl;
    private String dbUser;
    private String dbPassword;

    public BookingDB(String dburl, String dbUser, String dbPassword) {
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

    public boolean createBooking(BookingBean booking) {
        String sql = "INSERT INTO bookings (UserID, EquipmentID, BookingDate, PickupDate, ReturnDate, Status, TechnicianID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, booking.getUserID());
            ps.setInt(2, booking.getEquipmentID());
            ps.setTimestamp(3, Timestamp.valueOf(booking.getBookingDate()));
            ps.setTimestamp(4, Timestamp.valueOf(booking.getPickupDate()));
            ps.setTimestamp(5, Timestamp.valueOf(booking.getReturnDate()));
            ps.setString(6, booking.getStatus());
            ps.setInt(7, booking.getTechnicianID());
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public BookingBean readBooking(int bookingID) {
        String sql = "SELECT * FROM bookings WHERE BookingID = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                BookingBean booking = new BookingBean();
                booking.setBookingID(rs.getInt("bookingID"));
                booking.setUserID(rs.getInt("userID"));
                booking.setEquipmentID(rs.getInt("equipmentID"));
                booking.setBookingDate(rs.getTimestamp("bookingDate").toLocalDateTime());
                booking.setPickupDate(rs.getTimestamp("pickupDate").toLocalDateTime());
                booking.setReturnDate(rs.getTimestamp("returnDate").toLocalDateTime());
                booking.setStatus(rs.getString("status"));
                booking.setTechnicianID(rs.getInt("technicianID"));
                return booking;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateBooking(BookingBean booking) {
        String sql = "UPDATE bookings SET UserID=?, EquipmentID=?, BookingDate=?, PickupDate=?, ReturnDate=?, Status=?, TechnicianID=? WHERE BookingID=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, booking.getUserID());
            ps.setInt(2, booking.getEquipmentID());
            ps.setTimestamp(3, Timestamp.valueOf(booking.getBookingDate()));
            ps.setTimestamp(4, Timestamp.valueOf(booking.getPickupDate()));
            ps.setTimestamp(5, Timestamp.valueOf(booking.getReturnDate()));
            ps.setString(6, booking.getStatus());
            ps.setInt(7, booking.getTechnicianID());
            ps.setInt(8, booking.getBookingID());
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteBooking(int bookingID) {
        String sql = "DELETE FROM bookings WHERE BookingID = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingID);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
