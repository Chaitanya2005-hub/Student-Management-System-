package com.stark.exam.dao;

import com.stark.exam.model.AdmitCard;
import com.stark.exam.model.Fee;
import com.stark.exam.model.Grievance;
import com.stark.exam.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentServiceDAO {

    public boolean submitGrievance(Grievance g) {
        String sql = "INSERT INTO grievances (student_id, student_erp_id, category, description, status, submission_date) VALUES (?, ?, ?, ?, ?, CURRENT_DATE)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, g.getStudentId());
            ps.setString(2, g.getStudentErpId());
            ps.setString(3, g.getCategory());
            ps.setString(4, g.getDescription());
            ps.setString(5, g.getStatus() != null ? g.getStatus() : "Pending");
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) g.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Grievance> getGrievancesForStudent(int studentId) {
        List<Grievance> list = new ArrayList<>();
        String sql = "SELECT g.*, u.erp_id FROM grievances g LEFT JOIN users u ON g.student_id = u.id WHERE g.student_id = ? ORDER BY g.submission_date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapGrievance(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Grievance> getAllGrievances() {
        List<Grievance> list = new ArrayList<>();
        String sql = "SELECT g.*, u.erp_id FROM grievances g LEFT JOIN users u ON g.student_id = u.id ORDER BY g.submission_date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapGrievance(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateGrievanceStatus(int id, String status) {
        String sql = "UPDATE grievances SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Fee> getFeesForStudent(int studentId) {
        List<Fee> list = new ArrayList<>();
        String sql = "SELECT f.*, u.erp_id FROM fees f LEFT JOIN students s ON f.student_id = s.id LEFT JOIN users u ON s.user_id = u.id WHERE f.student_id = ? ORDER BY f.due_date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapFee(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getStudentIdByUserId(int userId) {
        String sql = "SELECT id FROM students WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Not found
    }

    public List<Fee> getAllFees() {
        List<Fee> list = new ArrayList<>();
        String sql = "SELECT f.*, u.erp_id FROM fees f LEFT JOIN students s ON f.student_id = s.id LEFT JOIN users u ON s.user_id = u.id ORDER BY f.due_date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapFee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean createFee(Fee f) {
        String sql = "INSERT INTO fees (student_id, student_erp_id, fee_type, amount, status, due_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, f.getStudentId());
            ps.setString(2, f.getStudentErpId());
            ps.setString(3, f.getFeeType());
            ps.setBigDecimal(4, f.getAmount());
            ps.setString(5, f.getStatus() != null ? f.getStatus() : "unpaid");
            ps.setDate(6, f.getDueDate());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) f.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateFeeStatus(int feeId, String status) {
        String sql = "UPDATE fees SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, feeId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public AdmitCard getAdmitCardForStudent(int studentId) {
        String sql = "SELECT * FROM admit_cards WHERE student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    AdmitCard ac = new AdmitCard();
                    ac.setId(rs.getInt("id"));
                    ac.setStudentId(rs.getInt("student_id"));
                    ac.setStatus(rs.getString("status"));
                    return ac;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<AdmitCard> getAllAdmitCards() {
        List<AdmitCard> list = new ArrayList<>();
        String sql = "SELECT * FROM admit_cards ORDER BY student_id ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                AdmitCard ac = new AdmitCard();
                ac.setId(rs.getInt("id"));
                ac.setStudentId(rs.getInt("student_id"));
                ac.setStatus(rs.getString("status"));
                list.add(ac);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateAdmitCardStatus(int studentId, String status) {
        String sql = "INSERT INTO admit_cards (student_id, status) VALUES (?, ?) ON DUPLICATE KEY UPDATE status = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setString(2, status);
            ps.setString(3, status);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Grievance mapGrievance(ResultSet rs) throws SQLException {
        Grievance g = new Grievance();
        g.setId(rs.getInt("id"));
        g.setStudentId(rs.getInt("student_id"));
        g.setStudentErpId(rs.getString("erp_id"));
        g.setCategory(rs.getString("category"));
        g.setDescription(rs.getString("description"));
        g.setStatus(rs.getString("status"));
        g.setSubmissionDate(rs.getDate("submission_date"));
        return g;
    }

    private Fee mapFee(ResultSet rs) throws SQLException {
        Fee f = new Fee();
        f.setId(rs.getInt("id"));
        f.setStudentId(rs.getInt("student_id"));
        f.setStudentErpId(rs.getString("erp_id"));
        f.setFeeType(rs.getString("fee_type"));
        f.setAmount(rs.getBigDecimal("amount"));
        f.setStatus(rs.getString("status"));
        f.setDueDate(rs.getDate("due_date"));
        f.setCreatedAt(rs.getTimestamp("created_at"));
        return f;
    }
}
