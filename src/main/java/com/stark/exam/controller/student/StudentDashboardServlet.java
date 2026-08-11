package com.stark.exam.controller.student;

import com.stark.exam.model.Announcement;
import com.stark.exam.model.Exam;
import com.stark.exam.model.User;
import com.stark.exam.service.CommunicationService;
import com.stark.exam.service.ExamService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/student/dashboard")
public class StudentDashboardServlet extends HttpServlet {

    private final ExamService examService = new ExamService();
    private final CommunicationService commService = new CommunicationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        // Get all exams and categorize them
        List<Exam> allExams = examService.getAllExams();
        List<Exam> activeExams = examService.getActiveExams();
        List<Exam> scheduledExams = examService.getScheduledExams();
        List<Announcement> announcements = commService.getActiveAnnouncements("students");

        // For debugging - log exam counts
        System.out.println("DEBUG: Total exams: " + allExams.size());
        System.out.println("DEBUG: Active exams: " + activeExams.size());
        System.out.println("DEBUG: Scheduled exams: " + scheduledExams.size());
        System.out.println("DEBUG: Announcements: " + announcements.size());

        // If no active/scheduled exams, show all exams for debugging
        if (activeExams.isEmpty() && scheduledExams.isEmpty() && !allExams.isEmpty()) {
            System.out.println("DEBUG: No active/scheduled exams found, showing all exams as scheduled");
            scheduledExams = allExams;
        }

        // Debug announcement details
        for (Announcement a : announcements) {
            System.out.println("DEBUG: Announcement - ID: " + a.getId() + ", Title: " + a.getTitle() + ", Target: " + a.getTargetAudience() + ", Active: " + a.isActive());
        }

        request.setAttribute("user", user);
        request.setAttribute("activeExams", activeExams);
        request.setAttribute("scheduledExams", scheduledExams);
        request.setAttribute("announcements", announcements);
        request.setAttribute("allExams", allExams); // Add for debugging

        request.getRequestDispatcher("/WEB-INF/views/student/dashboard.jsp").forward(request, response);
    }
}
