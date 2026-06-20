package com.prescripto.dao;

import com.prescripto.models.Doctor;
import com.prescripto.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String query = "SELECT * FROM doctors";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Doctor doc = new Doctor();
                doc.setId(rs.getInt("id"));
                doc.setName(rs.getString("name"));
                doc.setEmail(rs.getString("email"));
                doc.setImage(rs.getString("image"));
                doc.setSpeciality(rs.getString("speciality"));
                doc.setDegree(rs.getString("degree"));
                doc.setExperience(rs.getString("experience"));
                doc.setAbout(rs.getString("about"));
                doc.setFees(rs.getInt("fees"));
                doc.setAddressLine1(rs.getString("addressLine1"));
                doc.setAddressLine2(rs.getString("addressLine2"));
                doc.setAvailable(rs.getBoolean("available"));
                doc.setSlots_booked(rs.getString("slots_booked"));
                doctors.add(doc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    public Doctor getDoctorById(int id) {
        String query = "SELECT * FROM doctors WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Doctor doc = new Doctor();
                    doc.setId(rs.getInt("id"));
                    doc.setName(rs.getString("name"));
                    doc.setEmail(rs.getString("email"));
                    doc.setImage(rs.getString("image"));
                    doc.setSpeciality(rs.getString("speciality"));
                    doc.setDegree(rs.getString("degree"));
                    doc.setExperience(rs.getString("experience"));
                    doc.setAbout(rs.getString("about"));
                    doc.setFees(rs.getInt("fees"));
                    doc.setAddressLine1(rs.getString("addressLine1"));
                    doc.setAddressLine2(rs.getString("addressLine2"));
                    doc.setAvailable(rs.getBoolean("available"));
                    doc.setSlots_booked(rs.getString("slots_booked"));
                    return doc;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean changeAvailability(int id) {
        String query = "UPDATE doctors SET available = NOT available WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addDoctor(Doctor doc) {
        String query = "INSERT INTO doctors (name, email, password, speciality, degree, experience, about, fees, addressLine1, addressLine2, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, doc.getName());
            stmt.setString(2, doc.getEmail());
            stmt.setString(3, doc.getPassword());
            stmt.setString(4, doc.getSpeciality());
            stmt.setString(5, doc.getDegree());
            stmt.setString(6, doc.getExperience());
            stmt.setString(7, doc.getAbout());
            stmt.setInt(8, doc.getFees());
            stmt.setString(9, doc.getAddressLine1());
            stmt.setString(10, doc.getAddressLine2());
            stmt.setString(11, doc.getImage());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Doctor login(String email, String password) {
        String query = "SELECT * FROM doctors WHERE email = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Doctor doc = new Doctor();
                    doc.setId(rs.getInt("id"));
                    doc.setName(rs.getString("name"));
                    doc.setEmail(rs.getString("email"));
                    doc.setImage(rs.getString("image"));
                    doc.setSpeciality(rs.getString("speciality"));
                    doc.setDegree(rs.getString("degree"));
                    doc.setExperience(rs.getString("experience"));
                    doc.setAbout(rs.getString("about"));
                    doc.setFees(rs.getInt("fees"));
                    doc.setAddressLine1(rs.getString("addressLine1"));
                    doc.setAddressLine2(rs.getString("addressLine2"));
                    doc.setAvailable(rs.getBoolean("available"));
                    doc.setAbout(rs.getString("about"));
                    doc.setSlots_booked(rs.getString("slots_booked"));
                    return doc;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateProfile(Doctor doc) {
        String query = "UPDATE doctors SET fees = ?, addressLine1 = ?, addressLine2 = ?, available = ?, image = ?, about = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, doc.getFees());
            stmt.setString(2, doc.getAddressLine1());
            stmt.setString(3, doc.getAddressLine2());
            stmt.setBoolean(4, doc.isAvailable());
            stmt.setString(5, doc.getImage());
            stmt.setString(6, doc.getAbout());
            stmt.setInt(7, doc.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
