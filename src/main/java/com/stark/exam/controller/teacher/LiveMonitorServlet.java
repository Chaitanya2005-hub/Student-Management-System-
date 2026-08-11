package com.stark.exam.controller.teacher;

import com.stark.exam.model.Exam;
import com.stark.exam.model.ExamBroadcast;
import com.stark.exam.model.ExamLiveStatus;
import com.stark.exam.model.ExamLog;
import com.stark.exam.service.ExamService;
import com.stark.exam.service.ProctoringService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/teacher/live-monitor")
public class LiveMonitorServlet extends HttpServlet {

    private final ExamService examService = new ExamService();
    private final ProctoringService proctoringService = new ProctoringService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String examIdStr = request.getParameter("examId");
        List<Exam> activeExams = examService.getActiveExams();
        request.setAttribute("activeExams", activeExams);

        if (examIdStr != null && !examIdStr.isBlank()) {
            int examId = Integer.parseInt(examIdStr);
            Exam selectedExam = examService.getExamById(examId);
            List<ExamLiveStatus> statuses = proctoringService.getLiveStatusesForExam(examId);
            List<ExamLog> logs = proctoringService.getLogsForExam(examId);
            List<ExamBroadcast> broadcasts = proctoringService.getBroadcastsForExam(examId);

            request.setAttribute("selectedExam", selectedExam);
            request.setAttribute("statuses", statuses);
            request.setAttribute("logs", logs);
            request.setAttribute("broadcasts", broadcasts);
        }

        request.getRequestDispatcher("/WEB-INF/views/teacher/live_monitor.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int examId = Integer.parseInt(request.getParameter("examId"));
        String message = request.getParameter("message");

        if (message != null && !message.isBlank()) {
            proctoringService.sendBroadcast(examId, message);
        }

        response.sendRedirect(request.getContextPath() + "/teacher/live-monitor?examId=" + examId + "&msg=broadcast_sent");
    }
}
