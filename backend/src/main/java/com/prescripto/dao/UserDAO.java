package com.prescripto.dao;

import com.prescripto.models.User;
import com.prescripto.utils.DBConnection;

import java.sql.*;

public class UserDAO {

    public User login(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setRole(rs.getString("role"));
                    user.setImage(rs.getString("image"));
                    user.setPhone(rs.getString("phone"));
                    user.setAddressLine1(rs.getString("addressLine1"));
                    user.setAddressLine2(rs.getString("addressLine2"));
                    user.setGender(rs.getString("gender"));
                    user.setDob(rs.getString("dob"));
                    user.setAge(rs.getInt("age"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean register(User user) {
        String query = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserById(int id) {
        String query = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setRole(rs.getString("role"));
                    user.setImage(rs.getString("image"));
                    user.setPhone(rs.getString("phone"));
                    user.setAddressLine1(rs.getString("addressLine1"));
                    user.setAddressLine2(rs.getString("addressLine2"));
                    user.setGender(rs.getString("gender"));
                    user.setDob(rs.getString("dob"));
                    user.setAge(rs.getInt("age"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateProfile(User user) {
        String query = "UPDATE users SET name = ?, phone = ?, addressLine1 = ?, addressLine2 = ?, gender = ?, dob = ?, image = ?, age = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPhone());
            stmt.setString(3, user.getAddressLine1());
            stmt.setString(4, user.getAddressLine2());
            stmt.setString(5, user.getGender());
            stmt.setString(6, user.getDob());
            stmt.setString(7, user.getImage());
            stmt.setInt(8, user.getAge());
            stmt.setInt(9, user.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
