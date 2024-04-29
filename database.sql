-- MySQL Script to create the database and tables
-- Create the database
CREATE DATABASE IF NOT EXISTS hkiti_equipment_system;

USE hkiti_equipment_system;

-- Create the Campus table
CREATE TABLE Campus (
    CampusID INT AUTO_INCREMENT PRIMARY KEY,
    CampusName VARCHAR(100) NOT NULL,
    Location VARCHAR(200)
);

-- Create the EquipmentType table
CREATE TABLE EquipmentType (
    EquipmentTypeID INT AUTO_INCREMENT PRIMARY KEY,
    TypeName VARCHAR(100) NOT NULL,
    Description VARCHAR(200)
);

-- Create the Users table
CREATE TABLE Users (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Email VARCHAR(100) NOT NULL UNIQUE,
    Password VARCHAR(100) NOT NULL,
    Role ENUM('User', 'Technician', 'Courier', 'Administrator') NOT NULL,
    CampusID INT NOT NULL,
    FOREIGN KEY (CampusID) REFERENCES Campus(CampusID)
);

-- Create the Equipment table
CREATE TABLE Equipment (
    EquipmentID INT AUTO_INCREMENT PRIMARY KEY,
    EquipmentName VARCHAR(100) NOT NULL,
    EquipmentTypeID INT NOT NULL,
    Description VARCHAR(200),
    SerialNumber VARCHAR(50) NOT NULL UNIQUE,
    AcquisitionDate DATE,
    Status ENUM('Available', 'Booked', 'Damaged', 'Maintenance') NOT NULL,
    CampusID INT NOT NULL,
    FOREIGN KEY (EquipmentTypeID) REFERENCES EquipmentType(EquipmentTypeID),
    FOREIGN KEY (CampusID) REFERENCES Campus(CampusID)
);

-- Create the Bookings table
CREATE TABLE Bookings (
    BookingID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT NOT NULL,
    EquipmentID INT NOT NULL,
    BookingDate DATE NOT NULL,
    PickupDate DATE NOT NULL,
    ReturnDate DATE,
    Status ENUM('Pending', 'Approved', 'Rejected', 'Returned') NOT NULL,
    TechnicianID INT,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (EquipmentID) REFERENCES Equipment(EquipmentID),
    FOREIGN KEY (TechnicianID) REFERENCES Users(UserID)
);

-- Create the WishList table
CREATE TABLE WishList (
    WishListID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT NOT NULL,
    EquipmentID INT NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (EquipmentID) REFERENCES Equipment(EquipmentID)
);

-- Create the DamageReports table
CREATE TABLE DamageReports (
    DamageReportID INT AUTO_INCREMENT PRIMARY KEY,
    EquipmentID INT NOT NULL,
    BookingID INT NOT NULL,
    TechnicianID INT NOT NULL,
    DamageDescription VARCHAR(200) NOT NULL,
    DamageDate DATE NOT NULL,
    Status ENUM('Pending', 'Confirmed') NOT NULL,
    FOREIGN KEY (EquipmentID) REFERENCES Equipment(EquipmentID),
    FOREIGN KEY (BookingID) REFERENCES Bookings(BookingID),
    FOREIGN KEY (TechnicianID) REFERENCES Users(UserID)
);

-- Create the Deliveries table
CREATE TABLE Deliveries (
    DeliveryID INT AUTO_INCREMENT PRIMARY KEY,
    BookingID INT NOT NULL,
    CourierID INT NOT NULL,
    PickupLocation VARCHAR(200) NOT NULL,
    DeliveryLocation VARCHAR(200) NOT NULL,
    PickupDate DATE,
    DeliveryDate DATE,
    Status ENUM('Pending', 'In Transit', 'Delivered') NOT NULL,
    FOREIGN KEY (BookingID) REFERENCES Bookings(BookingID),
    FOREIGN KEY (CourierID) REFERENCES Users(UserID)
);