package com.stark.exam.controller.teacher;

import com.stark.exam.model.Assignment;
import com.stark.exam.model.Submission;
import com.stark.exam.model.User;
import com.stark.exam.service.AcademicService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/teacher/assignments")
public class TeacherAssignmentServlet extends HttpServlet {

    private final AcademicService academicService = new AcademicService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String assignIdStr = request.getParameter("assignmentId");
        List<Assignment> assignments = academicService.getAllAssignments();
        request.setAttribute("assignments", assignments);

        if (assignIdStr != null && !assignIdStr.isBlank()) {
            int assignId = Integer.parseInt(assignIdStr);
            List<Submission> submissions = academicService.getSubmissionsForAssignment(assignId);
            request.setAttribute("selectedAssignmentId", assignId);
            request.setAttribute("submissions", submissions);
        }

        request.getRequestDispatcher("/WEB-INF/views/teacher/assignments.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String dueDateStr = request.getParameter("dueDate");

        Assignment assign = new Assignment();
        assign.setTitle(title);
        assign.setDescription(description);
        assign.setDueDate(Date.valueOf(dueDateStr));
        assign.setCreatedBy(user.getId());

        academicService.createAssignment(assign);
        response.sendRedirect(request.getContextPath() + "/teacher/assignments?msg=created");
    }
}
