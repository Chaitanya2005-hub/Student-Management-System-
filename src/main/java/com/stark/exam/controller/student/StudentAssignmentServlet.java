package com.stark.exam.controller.student;

import com.stark.exam.model.Assignment;
import com.stark.exam.model.Submission;
import com.stark.exam.model.User;
import com.stark.exam.service.AcademicService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/student/assignments")
public class StudentAssignmentServlet extends HttpServlet {

    private final AcademicService academicService = new AcademicService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Assignment> assignments = academicService.getAllAssignments();
        request.setAttribute("assignments", assignments);
        request.getRequestDispatcher("/WEB-INF/views/student/assignments.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));
        String submissionText = request.getParameter("submissionText");

        // Get student_id from user_id using the students table
        int studentId = academicService.getStudentIdByUserId(user.getId());

        Submission sub = new Submission();
        sub.setAssignmentId(assignmentId);
        sub.setStudentId(studentId);
        sub.setStudentErpId(user.getErpId());
        sub.setSubmissionText(submissionText);

        academicService.submitAssignment(sub);
        response.sendRedirect(request.getContextPath() + "/student/assignments?msg=submitted");
    }
}
