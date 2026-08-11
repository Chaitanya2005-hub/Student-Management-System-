package com.stark.exam.controller.teacher;

import com.stark.exam.model.Exam;
import com.stark.exam.model.User;
import com.stark.exam.service.ExamService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/teacher/dashboard")
public class TeacherDashboardServlet extends HttpServlet {

    private final ExamService examService = new ExamService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        List<Exam> exams = examService.getAllExams();
        List<Exam> activeExams = examService.getActiveExams();

        request.setAttribute("user", user);
        request.setAttribute("exams", exams);
        request.setAttribute("activeExamsCount", activeExams.size());

        request.getRequestDispatcher("/WEB-INF/views/teacher/dashboard.jsp").forward(request, response);
    }
}
