package com.stark.exam.dao;

import com.stark.exam.model.ExamBroadcast;
import com.stark.exam.model.ExamLiveStatus;
import com.stark.exam.model.ExamLog;
import com.stark.exam.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamProctorDAO {

    public boolean updateLiveStatus(int studentId, int examId, int currentQuestion, int warningsCount, String status) {
        String sql = "INSERT INTO exam_live_status (student_id, exam_id, current_question, warnings_count, status, last_heartbeat) " +
                     "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP) " +
                     "ON DUPLICATE KEY UPDATE current_question = ?, warnings_count = ?, status = ?, last_heartbeat = CURRENT_TIMESTAMP";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, examId);
            ps.setInt(3, currentQuestion);
            ps.setInt(4, warningsCount);
            ps.setString(5, status);

            ps.setInt(6, currentQuestion);
            ps.setInt(7, warningsCount);
            ps.setString(8, status);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ExamLiveStatus getLiveStatus(int studentId, int examId) {
        String sql = "SELECT * FROM exam_live_status WHERE student_id = ? AND exam_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, examId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapLiveStatus(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ExamLiveStatus> getLiveStatusesForExam(int examId) {
        List<ExamLiveStatus> list = new ArrayList<>();
        String sql = "SELECT els.*, u.erp_id FROM exam_live_status els LEFT JOIN users u ON els.student_id = u.id WHERE els.exam_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, examId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapLiveStatus(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean logEvent(int studentId, int examId, String eventType) {
        String sql = "INSERT INTO exam_logs (student_id, exam_id, event_type, event_time) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, examId);
            ps.setString(3, eventType);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<ExamLog> getLogsForExam(int examId) {
        List<ExamLog> list = new ArrayList<>();
        String sql = "SELECT el.*, u.erp_id FROM exam_logs el LEFT JOIN users u ON el.student_id = u.id WHERE el.exam_id = ? ORDER BY el.event_time DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, examId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ExamLog log = new ExamLog();
                    log.setId(rs.getInt("id"));
                    log.setStudentId(rs.getInt("student_id"));
                    log.setStudentErpId(rs.getString("erp_id"));
                    log.setExamId(rs.getInt("exam_id"));
                    log.setEventType(rs.getString("event_type"));
                    log.setEventTime(rs.getTimestamp("event_time"));
                    list.add(log);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean sendBroadcast(int examId, String message) {
        String sql = "INSERT INTO exam_broadcasts (exam_id, message, sent_at) VALUES (?, ?, CURRENT_TIMESTAMP)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, examId);
            ps.setString(2, message);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<ExamBroadcast> getBroadcastsForExam(int examId) {
        List<ExamBroadcast> list = new ArrayList<>();
        String sql = "SELECT * FROM exam_broadcasts WHERE exam_id = ? ORDER BY sent_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, examId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ExamBroadcast b = new ExamBroadcast();
                    b.setId(rs.getInt("id"));
                    b.setExamId(rs.getInt("exam_id"));
                    b.setMessage(rs.getString("message"));
                    b.setSentAt(rs.getTimestamp("sent_at"));
                    list.add(b);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private ExamLiveStatus mapLiveStatus(ResultSet rs) throws SQLException {
        ExamLiveStatus status = new ExamLiveStatus();
        status.setStudentId(rs.getInt("student_id"));
        status.setStudentErpId(rs.getString("erp_id"));
        status.setExamId(rs.getInt("exam_id"));
        status.setCurrentQuestion(rs.getInt("current_question"));
        status.setWarningsCount(rs.getInt("warnings_count"));
        status.setStatus(rs.getString("status"));
        status.setLastHeartbeat(rs.getTimestamp("last_heartbeat"));
        return status;
    }
}
