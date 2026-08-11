package com.stark.exam.dao;

import com.stark.exam.model.Department;
import com.stark.exam.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {

    public List<Department> getAllDepartments() {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT * FROM departments ORDER BY name ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapDepartment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Department getById(int id) {
        String sql = "SELECT * FROM departments WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapDepartment(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createDepartment(Department d) {
        String sql = "INSERT INTO departments (name, code) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, d.getName());
            ps.setString(2, d.getCode());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        d.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Department mapDepartment(ResultSet rs) throws SQLException {
        Department d = new Department();
        d.setId(rs.getInt("id"));
        d.setName(rs.getString("name"));
        d.setCode(rs.getString("code"));
        d.setCreatedAt(rs.getTimestamp("created_at"));
        return d;
    }
}
