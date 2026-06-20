CREATE DATABASE IF NOT EXISTS prescripto;
USE prescripto;

-- Table for Users (Patients and Admins)
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    image LONGTEXT,
    phone VARCHAR(20),
    addressLine1 VARCHAR(255),
    addressLine2 VARCHAR(255),
    gender VARCHAR(20),
    dob DATE,
    age INT,
    role ENUM('PATIENT', 'ADMIN') DEFAULT 'PATIENT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table for Doctors
CREATE TABLE IF NOT EXISTS doctors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    image LONGTEXT,
    speciality VARCHAR(100),
    degree VARCHAR(100),
    experience VARCHAR(50),
    about TEXT,
    fees INT,
    addressLine1 VARCHAR(255),
    addressLine2 VARCHAR(255),
    available BOOLEAN DEFAULT TRUE,
    slots_booked JSON, -- Storing booked slots as JSON for simplicity in this example
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table for Appointments
CREATE TABLE IF NOT EXISTS appointments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId INT NOT NULL,
    docId INT NOT NULL,
    slotDate VARCHAR(20) NOT NULL,
    slotTime VARCHAR(20) NOT NULL,
    amount INT NOT NULL,
    bookingDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    cancelled BOOLEAN DEFAULT FALSE,
    isCompleted BOOLEAN DEFAULT FALSE,
    payment BOOLEAN DEFAULT FALSE,
    paymentMethod VARCHAR(50) DEFAULT 'CASH',
    FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (docId) REFERENCES doctors(id) ON DELETE CASCADE
);

-- Initial Admin Account
INSERT INTO users (name, email, password, role) 
VALUES ('Admin', 'admin@prescripto.com', 'admin123', 'ADMIN');

-- Sample Doctors (Optional, but helps for testing)
INSERT INTO doctors (name, email, password, speciality, degree, experience, about, fees, addressLine1, addressLine2)
VALUES ('Dr. Richard James', 'richard@gmail.com', 'doc123', 'General physician', 'MBBS', '4 Years', 'Dr. James has a strong commitment...', 50, '17th Cross', 'Richmond');
