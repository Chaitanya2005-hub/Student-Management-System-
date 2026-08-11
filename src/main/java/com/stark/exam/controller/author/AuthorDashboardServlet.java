package com.stark.exam.controller.author;

import com.stark.exam.model.User;
import com.stark.exam.service.AuthService;
import com.stark.exam.service.ExamService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/author/dashboard")
public class AuthorDashboardServlet extends HttpServlet {

    private final AuthService authService = new AuthService();
    private final ExamService examService = new ExamService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        List<User> users = authService.getAllUsers();
        int totalExams = examService.getAllExams().size();

        request.setAttribute("user", user);
        request.setAttribute("totalUsers", users.size());
        request.setAttribute("totalExams", totalExams);

        request.getRequestDispatcher("/WEB-INF/views/author/dashboard.jsp").forward(request, response);
    }
}
