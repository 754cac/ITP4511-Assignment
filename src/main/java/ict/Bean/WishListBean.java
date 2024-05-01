package ict.Bean;

import java.io.Serializable;

/**
 * This class represents a user's wish list of equipment items they are
 * interested in borrowing.
 */
public class WishListBean implements Serializable {

    private int wishListID;
    private int userID;  // Assuming 'User' is represented by an integer user ID
    private int equipmentID;  // Assuming 'Equipment' is represented by an integer equipment ID

    public WishListBean() {
        // Default constructor
    }

    public WishListBean(int wishListID, int userID, int equipmentID) {
        this.wishListID = wishListID;
        this.userID = userID;
        this.equipmentID = equipmentID;
    }

    public int getWishListID() {
        return wishListID;
    }

    public void setWishListID(int wishListID) {
        this.wishListID = wishListID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }

}
