package com.stark.exam.dao;

import com.stark.exam.model.Exam;
import com.stark.exam.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamDAO {

    public List<Exam> getAllExams() {
        List<Exam> list = new ArrayList<>();
        String sql = "SELECT * FROM exams ORDER BY exam_date DESC, start_time DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapExam(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Exam> getExamsByStatus(String status) {
        List<Exam> list = new ArrayList<>();
        String sql = "SELECT * FROM exams WHERE status = ? ORDER BY exam_date DESC, start_time DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapExam(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Exam getExamById(int id) {
        String sql = "SELECT * FROM exams WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapExam(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createExam(Exam exam) {
        String sql = "INSERT INTO exams (title, exam_date, start_time, duration_minutes, status, requires_approval) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, exam.getTitle());
            ps.setDate(2, exam.getExamDate());
            ps.setTime(3, exam.getStartTime());
            if (exam.getDurationMinutes() != null) ps.setInt(4, exam.getDurationMinutes()); else ps.setNull(4, Types.INTEGER);
            ps.setString(5, exam.getStatus() != null ? exam.getStatus() : "scheduled");
            ps.setBoolean(6, exam.isRequiresApproval());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        exam.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateExamStatus(int examId, String status) {
        String sql = "UPDATE exams SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, examId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Exam mapExam(ResultSet rs) throws SQLException {
        Exam e = new Exam();
        e.setId(rs.getInt("id"));
        e.setTitle(rs.getString("title"));
        e.setExamDate(rs.getDate("exam_date"));
        e.setStartTime(rs.getTime("start_time"));
        int dur = rs.getInt("duration_minutes");
        e.setDurationMinutes(rs.wasNull() ? null : dur);
        e.setStatus(rs.getString("status"));
        e.setRequiresApproval(rs.getBoolean("requires_approval"));
        return e;
    }
}
