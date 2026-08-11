package com.stark.exam.controller.teacher;

import com.stark.exam.model.Exam;
import com.stark.exam.model.Result;
import com.stark.exam.service.ExamService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/teacher/exam-results")
public class ExamResultsServlet extends HttpServlet {

    private final ExamService examService = new ExamService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String examIdStr = request.getParameter("examId");
        List<Exam> exams = examService.getAllExams();
        request.setAttribute("exams", exams);

        if (examIdStr != null && !examIdStr.isBlank()) {
            int examId = Integer.parseInt(examIdStr);
            Exam selectedExam = examService.getExamById(examId);
            List<Result> results = examService.getResultsForExam(examId);

            int totalStudents = results.size();
            int passedCount = 0;
            int totalScore = 0;
            for (Result r : results) {
                if (r.getScore() != null) {
                    totalScore += r.getScore();
                    if (r.getTotalMarks() != null && r.getTotalMarks() > 0) {
                        double percentage = (r.getScore() * 100.0) / r.getTotalMarks();
                        if (percentage >= 40.0) passedCount++;
                    }
                }
            }
            double averageScore = totalStudents > 0 ? (double) totalScore / totalStudents : 0.0;

            request.setAttribute("selectedExam", selectedExam);
            request.setAttribute("results", results);
            request.setAttribute("totalStudents", totalStudents);
            request.setAttribute("passedCount", passedCount);
            request.setAttribute("averageScore", String.format("%.2f", averageScore));
        }

        request.getRequestDispatcher("/WEB-INF/views/teacher/exam_results.jsp").forward(request, response);
    }
}
