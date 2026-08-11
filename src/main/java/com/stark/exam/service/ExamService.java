package com.stark.exam.service;

import com.stark.exam.dao.*;
import com.stark.exam.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamService {

    private final ExamDAO examDAO = new ExamDAO();
    private final QuestionDAO questionDAO = new QuestionDAO();
    private final QuestionBankDAO questionBankDAO = new QuestionBankDAO();
    private final ResultDAO resultDAO = new ResultDAO();
    private final ExamProctorDAO proctorDAO = new ExamProctorDAO();
    private final StudentServiceDAO studentServiceDAO = new StudentServiceDAO();

    public List<Exam> getAllExams() {
        return examDAO.getAllExams();
    }

    public List<Exam> getScheduledExams() {
        return examDAO.getExamsByStatus("scheduled");
    }

    public List<Exam> getActiveExams() {
        return examDAO.getExamsByStatus("active");
    }

    public Exam getExamById(int id) {
        return examDAO.getExamById(id);
    }

    public boolean createExam(Exam exam) {
        return examDAO.createExam(exam);
    }

    public boolean updateExamStatus(int examId, String status) {
        return examDAO.updateExamStatus(examId, status);
    }

    public List<Question> getQuestionsForExam(int examId) {
        return questionDAO.getQuestionsForExam(examId);
    }

    public boolean addQuestionToExam(Question q) {
        return questionDAO.createQuestion(q);
    }

    public boolean deleteQuestion(int id) {
        return questionDAO.deleteQuestion(id);
    }

    public List<QuestionBank> getAllBankQuestions() {
        return questionBankDAO.getAllQuestionBankItems();
    }

    public boolean addQuestionToBank(QuestionBank qb) {
        return questionBankDAO.createQuestionBankItem(qb);
    }

    public boolean importBankQuestionToExam(int bankQuestionId, int examId) {
        List<QuestionBank> all = questionBankDAO.getAllQuestionBankItems();
        QuestionBank found = null;
        for (QuestionBank qb : all) {
            if (qb.getId() == bankQuestionId) {
                found = qb;
                break;
            }
        }
        if (found != null) {
            Question q = new Question();
            q.setExamId(examId);
            q.setQuestionText(found.getQuestionText());
            q.setOptionA(found.getOptionA());
            q.setOptionB(found.getOptionB());
            q.setOptionC(found.getOptionC());
            q.setOptionD(found.getOptionD());
            q.setCorrectAnswer(found.getCorrectAnswer());
            return questionDAO.createQuestion(q);
        }
        return false;
    }

    public boolean isStudentAllowedToTakeExam(int userId, int examId) {
        AdmitCard card = studentServiceDAO.getAdmitCardForStudent(userId);
        if (card != null && "Blocked".equalsIgnoreCase(card.getStatus())) {
            return false; // Blanket admit card blocked
        }
        Result existing = resultDAO.getResultByStudentAndExam(userId, examId);
        if (existing != null) {
            return false; // Already completed
        }
        return true;
    }

    public Result evaluateAndSubmitExam(int userId, int examId, Map<Integer, String> userAnswers, int securityWarnings) {
        List<Question> questions = questionDAO.getQuestionsForExam(examId);
        int totalMarks = questions.size();
        int score = 0;

        for (Question q : questions) {
            String selected = userAnswers.get(q.getId());
            boolean isCorrect = false;

            if (selected != null && selected.equalsIgnoreCase(q.getCorrectAnswer())) {
                isCorrect = true;
                score++;
            }

            StudentResponse resp = new StudentResponse();
            resp.setStudentId(userId);
            resp.setExamId(examId);
            resp.setQuestionId(q.getId());
            resp.setSelectedOption(selected);
            resp.setIsCorrect(isCorrect);
            resultDAO.saveStudentResponse(resp);
        }

        Result res = new Result();
        res.setStudentId(userId);
        res.setExamId(examId);
        res.setScore(score);
        res.setTotalMarks(totalMarks);
        res.setSecurityWarnings(securityWarnings);
        resultDAO.saveResult(res);

        // Update live status to Submitted
        proctorDAO.updateLiveStatus(userId, examId, questions.size(), securityWarnings, "Submitted");
        proctorDAO.logEvent(userId, examId, "Submitted exam. Score: " + score + "/" + totalMarks);

        return res;
    }

    public Result getResult(int userId, int examId) {
        return resultDAO.getResultByStudentAndExam(userId, examId);
    }

    public List<Result> getResultsForStudent(int userId) {
        return resultDAO.getResultsForStudent(userId);
    }

    public List<Result> getResultsForExam(int examId) {
        return resultDAO.getResultsForExam(examId);
    }
}
