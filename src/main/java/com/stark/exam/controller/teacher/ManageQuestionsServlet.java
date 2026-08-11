package com.stark.exam.controller.teacher;

import com.stark.exam.model.Exam;
import com.stark.exam.model.Question;
import com.stark.exam.model.QuestionBank;
import com.stark.exam.service.ExamService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/teacher/manage-questions")
public class ManageQuestionsServlet extends HttpServlet {

    private final ExamService examService = new ExamService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String examIdStr = request.getParameter("examId");
        List<Exam> exams = examService.getAllExams();
        request.setAttribute("exams", exams);

        if (examIdStr != null && !examIdStr.isBlank()) {
            int examId = Integer.parseInt(examIdStr);
            Exam selectedExam = examService.getExamById(examId);
            List<Question> questions = examService.getQuestionsForExam(examId);
            List<QuestionBank> bankQuestions = examService.getAllBankQuestions();

            request.setAttribute("selectedExam", selectedExam);
            request.setAttribute("questions", questions);
            request.setAttribute("bankQuestions", bankQuestions);
        }

        request.getRequestDispatcher("/WEB-INF/views/teacher/manage_questions.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("import".equalsIgnoreCase(action)) {
            int bankId = Integer.parseInt(request.getParameter("bankId"));
            int examId = Integer.parseInt(request.getParameter("examId"));
            examService.importBankQuestionToExam(bankId, examId);
            response.sendRedirect(request.getContextPath() + "/teacher/manage-questions?examId=" + examId + "&msg=imported");
            return;
        }

        if ("delete".equalsIgnoreCase(action)) {
            int qId = Integer.parseInt(request.getParameter("questionId"));
            int examId = Integer.parseInt(request.getParameter("examId"));
            examService.deleteQuestion(qId);
            response.sendRedirect(request.getContextPath() + "/teacher/manage-questions?examId=" + examId + "&msg=deleted");
            return;
        }

        int examId = Integer.parseInt(request.getParameter("examId"));
        String qText = request.getParameter("questionText");
        String optA = request.getParameter("optionA");
        String optB = request.getParameter("optionB");
        String optC = request.getParameter("optionC");
        String optD = request.getParameter("optionD");
        String correct = request.getParameter("correctAnswer");

        Question q = new Question();
        q.setExamId(examId);
        q.setQuestionText(qText);
        q.setOptionA(optA);
        q.setOptionB(optB);
        q.setOptionC(optC);
        q.setOptionD(optD);
        q.setCorrectAnswer(correct);

        examService.addQuestionToExam(q);

        // Option to add to question bank as well
        if ("on".equalsIgnoreCase(request.getParameter("saveToBank"))) {
            QuestionBank qb = new QuestionBank();
            qb.setSubject(request.getParameter("subject"));
            qb.setDifficulty(request.getParameter("difficulty"));
            qb.setQuestionText(qText);
            qb.setOptionA(optA);
            qb.setOptionB(optB);
            qb.setOptionC(optC);
            qb.setOptionD(optD);
            qb.setCorrectAnswer(correct);
            examService.addQuestionToBank(qb);
        }

        response.sendRedirect(request.getContextPath() + "/teacher/manage-questions?examId=" + examId + "&msg=added");
    }
}
