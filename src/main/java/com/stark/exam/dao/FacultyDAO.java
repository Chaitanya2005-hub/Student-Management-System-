package com.stark.exam.dao;

import com.stark.exam.model.Faculty;
import com.stark.exam.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacultyDAO {

    public Faculty findByUserId(int userId) {
        String sql = "SELECT * FROM faculty WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapFaculty(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Faculty> getAllFaculty() {
        List<Faculty> list = new ArrayList<>();
        String sql = "SELECT * FROM faculty ORDER BY id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapFaculty(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean createFaculty(Faculty f) {
        String sql = "INSERT INTO faculty (user_id, employee_id, full_name, department_id, designation, phone) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, f.getUserId());
            ps.setString(2, f.getEmployeeId());
            ps.setString(3, f.getFullName());
            ps.setInt(4, f.getDepartmentId());
            ps.setString(5, f.getDesignation());
            ps.setString(6, f.getPhone());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        f.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Faculty mapFaculty(ResultSet rs) throws SQLException {
        Faculty f = new Faculty();
        f.setId(rs.getInt("id"));
        f.setUserId(rs.getInt("user_id"));
        f.setEmployeeId(rs.getString("employee_id"));
        f.setFullName(rs.getString("full_name"));
        f.setDepartmentId(rs.getInt("department_id"));
        f.setDesignation(rs.getString("designation"));
        f.setPhone(rs.getString("phone"));
        f.setCreatedAt(rs.getTimestamp("created_at"));
        return f;
    }
}
