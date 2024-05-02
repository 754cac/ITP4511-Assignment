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

INSERT INTO Campus (CampusName, Location) VALUES
    ('Sha Tin Campus', 'Shatin Street'),
    ('Chai Wan Campus', 'ChaiWan Street'),
    ('Lee Wai Lee Campus', 'LeeWaiLee Street'),
    ('Tuen Mun Campus', 'TuenMun Street'),
    ('Tsing Yi Campus', 'TsingYi Street');

-- Create the EquipmentType table
CREATE TABLE EquipmentType (
    EquipmentTypeID INT AUTO_INCREMENT PRIMARY KEY,
    TypeName VARCHAR(100) NOT NULL,
    Description VARCHAR(200)
);

INSERT INTO EquipmentType (TypeName, Description) VALUES
    ('Cameras', 'High-resolution video cameras'),
    ('Drones', 'Ultra-portable drones'),
    ('Laser Pointers', 'Portable laser pointers'),
    ('Projectors', 'Portable projectors'),
    ('Whiteboards', 'Ergonomic whiteboards');

-- Create the Users table
CREATE TABLE Users (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Email VARCHAR(100) NOT NULL UNIQUE,
    Password VARCHAR(100) NOT NULL,
    Role ENUM('User', 'Staff', 'Technician', 'Courier', 'Administrator') NOT NULL,
    CampusID INT NOT NULL,
    FOREIGN KEY (CampusID) REFERENCES Campus(CampusID)
);

INSERT INTO Users (Name, Email, Password, Role, CampusID) VALUES
    ('John Doe', 'johndoe@example.com', 'password123', 'User', 1),
    ('Jane Smith', 'janesmith@example.com', 'password456', 'Technician', 2),
    ('Admin User', 'admin@example.com', 'admin123', 'Administrator', 3),
    ('Courier User', 'courier@example.com', 'courier123', 'Courier', 4);

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


INSERT INTO Equipment (EquipmentName, EquipmentTypeID, Description, SerialNumber, AcquisitionDate, Status, CampusID) VALUES
    ('Camera', 1, 'High-resolution video camera', 'CAM123', '2022-01-01', 'Available', 1),
    ('Drone', 2, 'Ultra-portable drone', 'DRN123', '2021-12-31', 'Available', 2),
    ('Laser Pointer', 3, 'Portable laser pointer', 'LSP123', '2022-06-15', 'Available', 3),
    ('Projector', 4, 'Portable projector', 'PRJ123', '2022-05-01', 'Booked', 4),
    ('Whiteboard', 5, 'Ergonomic whiteboard', 'WHT123', '2022-03-15', 'Damaged', 5);

-- Create the Bookings table
CREATE TABLE Bookings (
    BookingID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT NOT NULL,
    EquipmentID INT NOT NULL,
    BookingDate DATETIME NOT NULL,
    PickupDate DATETIME NOT NULL,
    ReturnDate DATETIME,
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
    PickupDate DATETIME,
    DeliveryDate DATETIME,
    Status ENUM('Pending', 'In Transit', 'Delivered') NOT NULL,
    FOREIGN KEY (BookingID) REFERENCES Bookings(BookingID),
    FOREIGN KEY (CourierID) REFERENCES Users(UserID)
);