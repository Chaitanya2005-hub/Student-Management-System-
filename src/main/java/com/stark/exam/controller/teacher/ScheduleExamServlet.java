package com.stark.exam.controller.teacher;

import com.stark.exam.model.Exam;
import com.stark.exam.service.ExamService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@WebServlet("/teacher/schedule-exam")
public class ScheduleExamServlet extends HttpServlet {

    private final ExamService examService = new ExamService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Exam> exams = examService.getAllExams();
        request.setAttribute("exams", exams);
        request.getRequestDispatcher("/WEB-INF/views/teacher/schedule_exam.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("updateStatus".equalsIgnoreCase(action)) {
            int examId = Integer.parseInt(request.getParameter("examId"));
            String status = request.getParameter("status");
            examService.updateExamStatus(examId, status);
            response.sendRedirect(request.getContextPath() + "/teacher/schedule-exam?msg=status_updated");
            return;
        }

        String title = request.getParameter("title");
        String dateStr = request.getParameter("examDate");
        String timeStr = request.getParameter("startTime");
        int duration = Integer.parseInt(request.getParameter("durationMinutes"));
        boolean requiresApproval = "on".equalsIgnoreCase(request.getParameter("requiresApproval"));

        Exam exam = new Exam();
        exam.setTitle(title);
        exam.setExamDate(Date.valueOf(dateStr));
        exam.setStartTime(Time.valueOf(timeStr.length() == 5 ? timeStr + ":00" : timeStr));
        exam.setDurationMinutes(duration);
        exam.setStatus("scheduled");
        exam.setRequiresApproval(requiresApproval);

        examService.createExam(exam);
        response.sendRedirect(request.getContextPath() + "/teacher/schedule-exam?msg=created");
    }
}
