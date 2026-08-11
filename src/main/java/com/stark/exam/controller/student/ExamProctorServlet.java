package com.stark.exam.controller.student;

import com.stark.exam.model.ExamBroadcast;
import com.stark.exam.model.User;
import com.stark.exam.service.ProctoringService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/student/proctor-ping")
public class ExamProctorServlet extends HttpServlet {

    private final ProctoringService proctoringService = new ProctoringService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        try {
            int examId = Integer.parseInt(request.getParameter("examId"));
            int currentQuestion = Integer.parseInt(request.getParameter("currentQuestion"));
            int warningsCount = Integer.parseInt(request.getParameter("warningsCount"));
            String status = request.getParameter("status");
            String logEvent = request.getParameter("logEvent");

            proctoringService.recordHeartbeat(user.getId(), examId, currentQuestion, warningsCount, status != null ? status : "Active");

            if (logEvent != null && !logEvent.isBlank()) {
                proctoringService.logEvent(user.getId(), examId, logEvent);
            }

            List<ExamBroadcast> broadcasts = proctoringService.getBroadcastsForExam(examId);
            JSONArray broadcastArr = new JSONArray();
            for (ExamBroadcast b : broadcasts) {
                JSONObject obj = new JSONObject();
                obj.put("message", b.getMessage());
                obj.put("sentAt", b.getSentAt().toString());
                broadcastArr.put(obj);
            }

            jsonResponse.put("status", "ok");
            jsonResponse.put("broadcasts", broadcastArr);
        } catch (Exception e) {
            jsonResponse.put("status", "error");
            jsonResponse.put("message", e.getMessage());
        }

        out.print(jsonResponse.toString());
        out.flush();
    }
}
