package com.stark.exam.controller.student;

import com.stark.exam.model.Exam;
import com.stark.exam.model.Question;
import com.stark.exam.model.Result;
import com.stark.exam.model.User;
import com.stark.exam.service.ExamService;
import com.stark.exam.service.ProctoringService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/student/take-exam")
public class TakeExamServlet extends HttpServlet {

    private final ExamService examService = new ExamService();
    private final ProctoringService proctoringService = new ProctoringService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        String examIdStr = request.getParameter("id");

        if (examIdStr == null) {
            response.sendRedirect(request.getContextPath() + "/student/exams?error=missing_id");
            return;
        }

        int examId = Integer.parseInt(examIdStr);
        Exam exam = examService.getExamById(examId);

        if (exam == null) {
            response.sendRedirect(request.getContextPath() + "/student/exams?error=not_found");
            return;
        }

        if (!examService.isStudentAllowedToTakeExam(user.getId(), examId)) {
            response.sendRedirect(request.getContextPath() + "/student/exams?error=not_allowed");
            return;
        }

        List<Question> questions = examService.getQuestionsForExam(examId);
        proctoringService.recordHeartbeat(user.getId(), examId, 1, 0, "Active");
        proctoringService.logEvent(user.getId(), examId, "Started exam");

        request.setAttribute("exam", exam);
        request.setAttribute("questions", questions);

        request.getRequestDispatcher("/WEB-INF/views/student/take_exam.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        int examId = Integer.parseInt(request.getParameter("examId"));
        int warningsCount = 0;
        try {
            warningsCount = Integer.parseInt(request.getParameter("warningsCount"));
        } catch (Exception ignored) {}

        Map<Integer, String> answers = new HashMap<>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();
            if (name.startsWith("q_")) {
                int qId = Integer.parseInt(name.substring(2));
                String option = request.getParameter(name);
                answers.put(qId, option);
            }
        }

        Result result = examService.evaluateAndSubmitExam(user.getId(), examId, answers, warningsCount);

        request.setAttribute("result", result);
        request.setAttribute("exam", examService.getExamById(examId));
        request.getRequestDispatcher("/WEB-INF/views/student/performance.jsp").forward(request, response);
    }
}
