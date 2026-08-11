package com.stark.exam.controller.student;

import com.stark.exam.model.Fee;
import com.stark.exam.model.User;
import com.stark.exam.service.StudentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/student/fees")
public class StudentFeeServlet extends HttpServlet {

    private final StudentService studentService = new StudentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        System.out.println("DEBUG StudentFeeServlet: User ID = " + (user != null ? user.getId() : "null"));
        System.out.println("DEBUG StudentFeeServlet: User ERP ID = " + (user != null ? user.getErpId() : "null"));
        
        // Get student_id from user_id using the students table
        int studentId = studentService.getStudentIdByUserId(user.getId());
        System.out.println("DEBUG StudentFeeServlet: Student ID = " + studentId);
        
        List<Fee> fees = studentService.getFeesForStudent(studentId);
        System.out.println("DEBUG StudentFeeServlet: Fees found = " + fees.size());
        
        for (Fee f : fees) {
            System.out.println("DEBUG StudentFeeServlet: Fee ID=" + f.getId() + ", Type=" + f.getFeeType() + ", Status=" + f.getStatus());
        }

        request.setAttribute("fees", fees);
        request.getRequestDispatcher("/WEB-INF/views/student/fees.jsp").forward(request, response);
    }
}
