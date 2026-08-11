package com.stark.exam.controller;

import com.stark.exam.model.User;
import com.stark.exam.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = authService.login(username, password);

        if (user != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("userRole", user.getRole());
            session.setAttribute("userName", user.getFullName());

            switch (user.getRole().toLowerCase()) {
                case "student":
                    response.sendRedirect(request.getContextPath() + "/student/dashboard");
                    break;
                case "teacher":
                    response.sendRedirect(request.getContextPath() + "/teacher/dashboard");
                    break;
                case "author":
                    response.sendRedirect(request.getContextPath() + "/author/dashboard");
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/index.jsp?error=invalid_role");
                    break;
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=invalid_credentials");
        }
    }
}
