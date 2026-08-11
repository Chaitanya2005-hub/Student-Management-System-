package com.stark.exam.dao;

import com.stark.exam.model.QuestionBank;
import com.stark.exam.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionBankDAO {

    public List<QuestionBank> getAllQuestionBankItems() {
        List<QuestionBank> list = new ArrayList<>();
        String sql = "SELECT * FROM question_bank ORDER BY id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapQuestionBank(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<QuestionBank> search(String subject, String difficulty) {
        List<QuestionBank> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM question_bank WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (subject != null && !subject.isBlank()) {
            sql.append(" AND subject LIKE ?");
            params.add("%" + subject + "%");
        }
        if (difficulty != null && !difficulty.isBlank()) {
            sql.append(" AND difficulty = ?");
            params.add(difficulty);
        }
        sql.append(" ORDER BY id DESC");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapQuestionBank(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean createQuestionBankItem(QuestionBank qb) {
        String sql = "INSERT INTO question_bank (subject, difficulty, question_text, option_a, option_b, option_c, option_d, correct_answer) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, qb.getSubject());
            ps.setString(2, qb.getDifficulty());
            ps.setString(3, qb.getQuestionText());
            ps.setString(4, qb.getOptionA());
            ps.setString(5, qb.getOptionB());
            ps.setString(6, qb.getOptionC());
            ps.setString(7, qb.getOptionD());
            ps.setString(8, qb.getCorrectAnswer());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        qb.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private QuestionBank mapQuestionBank(ResultSet rs) throws SQLException {
        QuestionBank qb = new QuestionBank();
        qb.setId(rs.getInt("id"));
        qb.setSubject(rs.getString("subject"));
        qb.setDifficulty(rs.getString("difficulty"));
        qb.setQuestionText(rs.getString("question_text"));
        qb.setOptionA(rs.getString("option_a"));
        qb.setOptionB(rs.getString("option_b"));
        qb.setOptionC(rs.getString("option_c"));
        qb.setOptionD(rs.getString("option_d"));
        qb.setCorrectAnswer(rs.getString("correct_answer"));
        return qb;
    }
}
