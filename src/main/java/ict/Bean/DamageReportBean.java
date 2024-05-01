package ict.Bean;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * This class represents a damage report filed by a technician for damaged
 * equipment.
 */
public class DamageReportBean implements Serializable {

    private String damageReportID;
    private String equipmentID;  // Equipment ID as a string
    private String bookingID;    // Booking ID as a string
    private String technicianID; // Technician ID as a string
    private String damageDescription;
    private LocalDate damageDate;
    private String status;

    public DamageReportBean() {
        // Default constructor
    }

    // Getters and setters
    public String getDamageReportID() {
        return damageReportID;
    }

    public void setDamageReportID(String damageReportID) {
        this.damageReportID = damageReportID;
    }

    public String getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(String equipmentID) {
        this.equipmentID = equipmentID;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getTechnicianID() {
        return technicianID;
    }

    public void setTechnicianID(String technicianID) {
        this.technicianID = technicianID;
    }

    public String getDamageDescription() {
        return damageDescription;
    }

    public void setDamageDescription(String damageDescription) {
        this.damageDescription = damageDescription;
    }

    public LocalDate getDamageDate() {
        return damageDate;
    }

    public void setDamageDate(LocalDate damageDate) {
        this.damageDate = damageDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Method to confirm damage - this might typically involve updating the status
    public void confirmDamage() {
        this.status = "Confirmed";
    }

    // Method to update the status of the damage report
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }
}
