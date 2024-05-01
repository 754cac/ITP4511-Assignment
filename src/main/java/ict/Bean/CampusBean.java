package ict.Bean;

import java.io.Serializable;

/**
 * This class represents a campus of the Hong Kong Institute of Professional
 * Education (IPE).
 */
public class CampusBean implements Serializable {

    private String campusID;
    private String campusName;
    private String location;

    // Default constructor
    public CampusBean() {
    }
    // Getters and Setters
    public String getCampusID() {
        return campusID;
    }

    public void setCampusID(String campusID) {
        this.campusID = campusID;
    }

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Override toString for easy printing of CampusBean details
    @Override
    public String toString() {
        return "CampusBean{"
                + "campusID='" + campusID + '\''
                + ", campusName='" + campusName + '\''
                + ", location='" + location + '\''
                + '}';
    }
}
