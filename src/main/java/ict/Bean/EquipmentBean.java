/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Beans/Bean.java to edit this template
 */
package ict.Bean;

import java.beans.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author user
 */
public class EquipmentBean implements Serializable {

    // Properties
    private String equipmentID;
    private String equipmentName;
    private String equipmentType;
    private String description;
    private String serialNumber;
    private Date acquisitionDate;
    private String status;
    private String campus;

    // Constructors
    public EquipmentBean() {
    }

    public String getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(String equipmentID) {
        this.equipmentID = equipmentID;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(Date acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    // Methods for updating equipment details
    public void updateStatus(String newStatus) {
        setStatus(newStatus);
    }

    public void updateDetails(String newDescription, String newCampus) {
        setDescription(newDescription);
        setCampus(newCampus);
    }
}
