package com.stark.exam.controller.author;

import com.stark.exam.model.Exam;
import com.stark.exam.model.Grievance;
import com.stark.exam.model.User;
import com.stark.exam.service.AuthService;
import com.stark.exam.service.ExamService;
import com.stark.exam.service.StudentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/author/system-reports")
public class SystemReportsServlet extends HttpServlet {

    private final AuthService authService = new AuthService();
    private final ExamService examService = new ExamService();
    private final StudentService studentService = new StudentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> users = authService.getAllUsers();
        List<Exam> exams = examService.getAllExams();
        List<Grievance> grievances = studentService.getAllGrievances();

        request.setAttribute("users", users);
        request.setAttribute("exams", exams);
        request.setAttribute("grievances", grievances);

        request.getRequestDispatcher("/WEB-INF/views/author/system_reports.jsp").forward(request, response);
    }
}
