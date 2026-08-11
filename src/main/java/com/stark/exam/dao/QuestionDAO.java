package com.stark.exam.dao;

import com.stark.exam.model.Question;
import com.stark.exam.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    public List<Question> getQuestionsForExam(int examId) {
        List<Question> list = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE exam_id = ? ORDER BY id ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, examId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapQuestion(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Question getQuestionById(int id) {
        String sql = "SELECT * FROM questions WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapQuestion(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createQuestion(Question q) {
        String sql = "INSERT INTO questions (exam_id, question_text, option_a, option_b, option_c, option_d, correct_answer) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, q.getExamId());
            ps.setString(2, q.getQuestionText());
            ps.setString(3, q.getOptionA());
            ps.setString(4, q.getOptionB());
            ps.setString(5, q.getOptionC());
            ps.setString(6, q.getOptionD());
            ps.setString(7, q.getCorrectAnswer());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        q.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteQuestion(int id) {
        String sql = "DELETE FROM questions WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Question mapQuestion(ResultSet rs) throws SQLException {
        Question q = new Question();
        q.setId(rs.getInt("id"));
        q.setExamId(rs.getInt("exam_id"));
        q.setQuestionText(rs.getString("question_text"));
        q.setOptionA(rs.getString("option_a"));
        q.setOptionB(rs.getString("option_b"));
        q.setOptionC(rs.getString("option_c"));
        q.setOptionD(rs.getString("option_d"));
        q.setCorrectAnswer(rs.getString("correct_answer"));
        return q;
    }
}
