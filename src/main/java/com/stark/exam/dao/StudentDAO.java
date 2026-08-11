package com.stark.exam.dao;

import com.stark.exam.model.Student;
import com.stark.exam.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public Student findByUserId(int userId) {
        String sql = "SELECT * FROM students WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapStudent(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Student findById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapStudent(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY roll_no ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapStudent(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean createStudent(Student s) {
        String sql = "INSERT INTO students (user_id, roll_no, full_name, department_id, semester, batch_year) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, s.getUserId());
            ps.setString(2, s.getRollNo());
            ps.setString(3, s.getFullName());
            ps.setInt(4, s.getDepartmentId());
            ps.setInt(5, s.getSemester());
            ps.setInt(6, s.getBatchYear());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        s.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Student mapStudent(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setId(rs.getInt("id"));
        s.setUserId(rs.getInt("user_id"));
        s.setRollNo(rs.getString("roll_no"));
        s.setFullName(rs.getString("full_name"));
        s.setDepartmentId(rs.getInt("department_id"));
        s.setSemester(rs.getInt("semester"));
        s.setBatchYear(rs.getInt("batch_year"));
        return s;
    }
}
