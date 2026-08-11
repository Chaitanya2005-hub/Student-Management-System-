package com.stark.exam.controller.student;

import com.stark.exam.model.Grievance;
import com.stark.exam.model.User;
import com.stark.exam.service.StudentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/student/grievance")
public class StudentGrievanceServlet extends HttpServlet {

    private final StudentService studentService = new StudentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        List<Grievance> grievances = studentService.getGrievancesForStudent(user.getId());

        request.setAttribute("grievances", grievances);
        request.getRequestDispatcher("/WEB-INF/views/student/grievance.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        String category = request.getParameter("category");
        String description = request.getParameter("description");

        Grievance g = new Grievance();
        g.setStudentId(user.getId());
        g.setStudentErpId(user.getErpId());
        g.setCategory(category);
        g.setDescription(description);
        g.setStatus("Pending");

        studentService.submitGrievance(g);
        response.sendRedirect(request.getContextPath() + "/student/grievance?msg=submitted");
    }
}
