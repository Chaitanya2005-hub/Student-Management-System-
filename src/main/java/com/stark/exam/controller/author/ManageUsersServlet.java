package com.stark.exam.controller.author;

import com.stark.exam.model.Department;
import com.stark.exam.model.User;
import com.stark.exam.service.AcademicService;
import com.stark.exam.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/author/manage-users")
public class ManageUsersServlet extends HttpServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> users = authService.getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/views/author/manage_users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equalsIgnoreCase(action)) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            authService.deleteUser(userId);
            response.sendRedirect(request.getContextPath() + "/author/manage-users?msg=deleted");
            return;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String role = request.getParameter("role");
        String erpId = request.getParameter("erpId");
        String department = request.getParameter("department");
        String section = request.getParameter("section");
        String yearStr = request.getParameter("year");

        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        u.setFullName(fullName);
        u.setRole(role);
        u.setErpId(erpId);
        u.setDepartment(department);
        u.setSection(section);
        if (yearStr != null && !yearStr.isBlank()) {
            u.setYear(Integer.parseInt(yearStr));
        }

        boolean ok = authService.createUser(u);
        if (ok) {
            response.sendRedirect(request.getContextPath() + "/author/manage-users?msg=created");
        } else {
            response.sendRedirect(request.getContextPath() + "/author/manage-users?error=username_exists");
        }
    }
}
