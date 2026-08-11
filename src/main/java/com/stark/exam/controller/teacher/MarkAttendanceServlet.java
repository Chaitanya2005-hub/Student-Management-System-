package com.stark.exam.controller.teacher;

import com.stark.exam.model.Attendance;
import com.stark.exam.model.Student;
import com.stark.exam.model.User;
import com.stark.exam.service.AcademicService;
import com.stark.exam.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/teacher/mark-attendance")
public class MarkAttendanceServlet extends HttpServlet {

    private final AuthService authService = new AuthService();
    private final AcademicService academicService = new AcademicService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> allUsers = authService.getAllUsers();
        request.setAttribute("users", allUsers);
        request.getRequestDispatcher("/WEB-INF/views/teacher/mark_attendance.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User teacher = (User) request.getSession().getAttribute("user");
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        String dateStr = request.getParameter("date");
        String status = request.getParameter("status");

        Attendance a = new Attendance();
        a.setStudentId(studentId);
        a.setDate(Date.valueOf(dateStr));
        a.setStatus(status);
        a.setMarkedBy(teacher.getId());

        academicService.markAttendance(a);
        response.sendRedirect(request.getContextPath() + "/teacher/mark-attendance?msg=marked");
    }
}
