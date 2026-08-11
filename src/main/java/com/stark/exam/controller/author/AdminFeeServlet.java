package com.stark.exam.controller.author;

import com.stark.exam.model.Fee;
import com.stark.exam.model.User;
import com.stark.exam.service.AuthService;
import com.stark.exam.service.StudentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@WebServlet("/author/admin-fees")
public class AdminFeeServlet extends HttpServlet {

    private final StudentService studentService = new StudentService();
    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Fee> fees = studentService.getAllFees();
        List<User> users = authService.getAllUsers();

        System.out.println("DEBUG AdminFeeServlet: Total fees loaded = " + fees.size());
        for (Fee f : fees) {
            System.out.println("DEBUG AdminFeeServlet: Fee ID=" + f.getId() + ", Student ID=" + f.getStudentId() + ", ERP ID=" + f.getStudentErpId() + ", Type=" + f.getFeeType());
        }

        request.setAttribute("fees", fees);
        request.setAttribute("users", users);

        request.getRequestDispatcher("/WEB-INF/views/author/admin_fees.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("updateStatus".equalsIgnoreCase(action)) {
            int feeId = Integer.parseInt(request.getParameter("feeId"));
            String status = request.getParameter("status");
            studentService.updateFeeStatus(feeId, status);
            response.sendRedirect(request.getContextPath() + "/author/admin-fees?msg=updated");
            return;
        }

        int studentId = Integer.parseInt(request.getParameter("studentId"));
        String feeType = request.getParameter("feeType");
        BigDecimal amount = new BigDecimal(request.getParameter("amount"));
        String dueDateStr = request.getParameter("dueDate");

        // Get student ERP ID
        User student = authService.getUserById(studentId);
        String studentErpId = student != null ? student.getErpId() : null;

        Fee f = new Fee();
        f.setStudentId(studentId);
        f.setStudentErpId(studentErpId);
        f.setFeeType(feeType);
        f.setAmount(amount);
        f.setStatus("unpaid");
        f.setDueDate(Date.valueOf(dueDateStr));

        studentService.createFee(f);
        response.sendRedirect(request.getContextPath() + "/author/admin-fees?msg=created");
    }
}
