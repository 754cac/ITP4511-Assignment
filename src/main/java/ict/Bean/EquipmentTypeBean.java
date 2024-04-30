package ict.Bean;

import java.io.Serializable;

/**
 * This class represents a type of equipment, such as laptops, projectors,
 * cameras, etc. It includes properties for identification, naming, and
 * description of the equipment type.
 */
public class EquipmentTypeBean implements Serializable {

    private String equipmentTypeID;
    private String typeName;
    private String description;

    /**
     * Default constructor.
     */
    public EquipmentTypeBean() {
    }

    /**
     * Constructor with all properties.
     */
    public EquipmentTypeBean(String equipmentTypeID, String typeName, String description) {
        this.equipmentTypeID = equipmentTypeID;
        this.typeName = typeName;
        this.description = description;
    }

    // Getters and setters for each property
    public String getEquipmentTypeID() {
        return equipmentTypeID;
    }

    public void setEquipmentTypeID(String equipmentTypeID) {
        this.equipmentTypeID = equipmentTypeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a string representation of the object. In this case, it returns a
     * string that lists the equipment type ID, name, and description.
     */
    @Override
    public String toString() {
        return "EquipmentTypeBean{"
                + "equipmentTypeID='" + equipmentTypeID + '\''
                + ", typeName='" + typeName + '\''
                + ", description='" + description + '\''
                + '}';
    }
}
