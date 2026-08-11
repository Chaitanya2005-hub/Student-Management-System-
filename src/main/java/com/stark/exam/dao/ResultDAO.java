package com.stark.exam.dao;

import com.stark.exam.model.Result;
import com.stark.exam.model.StudentResponse;
import com.stark.exam.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResultDAO {

    public boolean saveStudentResponse(StudentResponse resp) {
        String sql = "INSERT INTO student_responses (student_id, exam_id, question_id, selected_option, is_correct) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, resp.getStudentId());
            ps.setInt(2, resp.getExamId());
            ps.setInt(3, resp.getQuestionId());
            ps.setString(4, resp.getSelectedOption());
            if (resp.getIsCorrect() != null) ps.setBoolean(5, resp.getIsCorrect()); else ps.setNull(5, Types.BOOLEAN);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) resp.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<StudentResponse> getResponsesForStudentAndExam(int studentId, int examId) {
        List<StudentResponse> list = new ArrayList<>();
        String sql = "SELECT * FROM student_responses WHERE student_id = ? AND exam_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, examId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StudentResponse r = new StudentResponse();
                    r.setId(rs.getInt("id"));
                    r.setStudentId(rs.getInt("student_id"));
                    r.setExamId(rs.getInt("exam_id"));
                    r.setQuestionId(rs.getInt("question_id"));
                    r.setSelectedOption(rs.getString("selected_option"));
                    boolean isCorrect = rs.getBoolean("is_correct");
                    r.setIsCorrect(rs.wasNull() ? null : isCorrect);
                    list.add(r);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean saveResult(Result res) {
        String sql = "INSERT INTO results (student_id, exam_id, score, total_marks, security_warnings) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, res.getStudentId());
            ps.setInt(2, res.getExamId());
            if (res.getScore() != null) ps.setInt(3, res.getScore()); else ps.setNull(3, Types.INTEGER);
            if (res.getTotalMarks() != null) ps.setInt(4, res.getTotalMarks()); else ps.setNull(4, Types.INTEGER);
            ps.setInt(5, res.getSecurityWarnings());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) res.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Result getResultByStudentAndExam(int studentId, int examId) {
        String sql = "SELECT * FROM results WHERE student_id = ? AND exam_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, examId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResult(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Result> getResultsForStudent(int studentId) {
        List<Result> list = new ArrayList<>();
        String sql = "SELECT * FROM results WHERE student_id = ? ORDER BY id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResult(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Result> getResultsForExam(int examId) {
        List<Result> list = new ArrayList<>();
        String sql = "SELECT r.*, u.erp_id FROM results r LEFT JOIN users u ON r.student_id = u.id WHERE r.exam_id = ? ORDER BY r.score DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, examId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResult(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Result mapResult(ResultSet rs) throws SQLException {
        Result r = new Result();
        r.setId(rs.getInt("id"));
        r.setStudentId(rs.getInt("student_id"));
        r.setStudentErpId(rs.getString("erp_id"));
        r.setExamId(rs.getInt("exam_id"));
        int score = rs.getInt("score");
        r.setScore(rs.wasNull() ? null : score);
        int total = rs.getInt("total_marks");
        r.setTotalMarks(rs.wasNull() ? null : total);
        r.setSecurityWarnings(rs.getInt("security_warnings"));
        return r;
    }
}
