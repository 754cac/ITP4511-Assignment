package ict.Bean;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * This class represents the delivery of equipment to a specific location,
 * handled by a courier.
 */
public class DeliveryBean implements Serializable {

    private String deliveryID;
    private String bookingID;
    private String courierID;
    private String pickupLocation;
    private String deliveryLocation;
    private LocalDate pickupDate;
    private LocalDate deliveryDate;
    private String status;

    /**
     * Default constructor.
     */
    public DeliveryBean() {
    }

    // Getters and Setters
    public String getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(String deliveryID) {
        this.deliveryID = deliveryID;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getCourierID() {
        return courierID;
    }

    public void setCourierID(String courierID) {
        this.courierID = courierID;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Business methods
    /**
     * Updates the status of the delivery.
     *
     * @param status The new status of the delivery.
     */
    public void updateStatus(String status) {
        this.status = status;
    }

    /**
     * Assigns a courier to the delivery.
     *
     * @param courierID The ID of the courier to be assigned.
     */
    public void assignCourier(String courierID) {
        this.courierID = courierID;
    }

    @Override
    public String toString() {
        return "DeliveryBean{"
                + "deliveryID='" + deliveryID + '\''
                + ", booking=" + bookingID
                + ", courierID='" + courierID + '\''
                + ", pickupLocation='" + pickupLocation + '\''
                + ", deliveryLocation='" + deliveryLocation + '\''
                + ", pickupDate=" + pickupDate
                + ", deliveryDate=" + deliveryDate
                + ", status='" + status + '\''
                + '}';
    }
}
