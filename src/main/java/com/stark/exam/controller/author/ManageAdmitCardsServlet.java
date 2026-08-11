package com.stark.exam.controller.author;

import com.stark.exam.model.AdmitCard;
import com.stark.exam.model.User;
import com.stark.exam.service.AuthService;
import com.stark.exam.service.StudentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/author/manage-admit-cards")
public class ManageAdmitCardsServlet extends HttpServlet {

    private final StudentService studentService = new StudentService();
    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<AdmitCard> admitCards = studentService.getAllAdmitCards();
        List<User> allUsers = authService.getAllUsers();

        request.setAttribute("admitCards", admitCards);
        request.setAttribute("users", allUsers);

        request.getRequestDispatcher("/WEB-INF/views/author/manage_admit_cards.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        String status = request.getParameter("status");

        studentService.updateAdmitCardStatus(studentId, status);
        response.sendRedirect(request.getContextPath() + "/author/manage-admit-cards?msg=updated");
    }
}
