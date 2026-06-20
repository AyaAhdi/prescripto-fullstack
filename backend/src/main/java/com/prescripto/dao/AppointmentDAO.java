package com.prescripto.dao;

import com.prescripto.models.Appointment;
import com.prescripto.models.Doctor;
import com.prescripto.models.User;
import com.prescripto.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    public boolean bookAppointment(Appointment app) {
        String query = "INSERT INTO appointments (userId, docId, slotDate, slotTime, amount) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, app.getUserId());
            stmt.setInt(2, app.getDocId());
            stmt.setString(3, app.getSlotDate());
            stmt.setString(4, app.getSlotTime());
            stmt.setInt(5, app.getAmount());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Appointment> getAppointmentsByUser(int userId) {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT a.*, d.name as docName, d.speciality, d.image as docImage, d.addressLine1 as docAddr1, d.addressLine2 as docAddr2 " +
                       "FROM appointments a LEFT JOIN doctors d ON a.docId = d.id WHERE a.userId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Appointment app = mapAppointment(rs);
                    Doctor doc = new Doctor();
                    doc.setName(rs.getString("docName"));
                    doc.setSpeciality(rs.getString("speciality"));
                    doc.setImage(rs.getString("docImage"));
                    doc.setAddressLine1(rs.getString("docAddr1"));
                    doc.setAddressLine2(rs.getString("docAddr2"));
                    app.setDocData(doc);
                    appointments.add(app);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public List<Appointment> getAppointmentsByDoctor(int docId) {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT a.*, u.name as userName, u.image as userImage, u.dob, u.age as userAge " +
                       "FROM appointments a JOIN users u ON a.userId = u.id WHERE a.docId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, docId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Appointment app = mapAppointment(rs);
                    User user = new User();
                    user.setName(rs.getString("userName"));
                    user.setImage(rs.getString("userImage"));
                    user.setDob(rs.getString("dob"));
                    user.setAge(rs.getInt("userAge"));
                    app.setUserData(user);
                    appointments.add(app);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT a.*, u.name as userName, u.image as userImage, u.age as userAge, d.name as docName, d.image as docImage " +
                       "FROM appointments a JOIN users u ON a.userId = u.id JOIN doctors d ON a.docId = d.id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Appointment app = mapAppointment(rs);
                User user = new User();
                user.setName(rs.getString("userName"));
                user.setImage(rs.getString("userImage"));
                user.setAge(rs.getInt("userAge"));
                app.setUserData(user);
                Doctor doc = new Doctor();
                doc.setName(rs.getString("docName"));
                doc.setImage(rs.getString("docImage"));
                app.setDocData(doc);
                appointments.add(app);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public boolean cancelAppointment(int id) {
        String query = "UPDATE appointments SET cancelled = TRUE WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean completeAppointment(int id) {
        String query = "UPDATE appointments SET isCompleted = TRUE WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePaymentStatus(int id, boolean status) {
        String query = "UPDATE appointments SET payment = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBoolean(1, status);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Appointment mapAppointment(ResultSet rs) throws SQLException {
        Appointment app = new Appointment();
        app.setId(rs.getInt("id"));
        app.setUserId(rs.getInt("userId"));
        app.setDocId(rs.getInt("docId"));
        app.setSlotDate(rs.getString("slotDate"));
        app.setSlotTime(rs.getString("slotTime"));
        app.setAmount(rs.getInt("amount"));
        app.setBookingDate(rs.getString("bookingDate"));
        app.setCancelled(rs.getBoolean("cancelled"));
        app.setCompleted(rs.getBoolean("isCompleted"));
        app.setPayment(rs.getBoolean("payment"));
        app.setPaymentMethod(rs.getString("paymentMethod"));
        return app;
    }

    public boolean updatePaymentMethod(int id, String method) {
        String query = "UPDATE appointments SET paymentMethod = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, method);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
