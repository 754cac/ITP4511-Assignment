package ict.db;

import java.io.IOException;
import java.sql.*;

public class WishListDB {

    private DBConnection connector;

    public WishListDB(DBConnection connector) {
        this.connector = connector;
    }

    // CREATE
    public boolean addToWishList(int userID, int equipmentID) {
        String sql = "INSERT INTO wishlist (UserID, EquipmentID) VALUES (?, ?)";
        try (Connection conn = connector.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ps.setInt(2, equipmentID);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // READ
    public ResultSet getWishListByUser(int userID) {
        String sql = "SELECT * FROM wishlist WHERE UserID = ?";
        try (Connection conn = connector.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userID);
            return ps.executeQuery();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // UPDATE - Typically not applicable for wishlist items, but could be used to change equipmentID for a given userID and wishlistID
    public boolean updateWishListItem(int wishListID, int newEquipmentID) {
        String sql = "UPDATE wishlist SET EquipmentID = ? WHERE WishListID = ?";
        try (Connection conn = connector.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newEquipmentID);
            ps.setInt(2, wishListID);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELETE
    public boolean removeFromWishList(int wishListID) {
        String sql = "DELETE FROM wishlist WHERE WishListID = ?";
        try (Connection conn = connector.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, wishListID);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
