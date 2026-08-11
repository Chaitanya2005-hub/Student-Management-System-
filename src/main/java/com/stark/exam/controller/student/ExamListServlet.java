package com.stark.exam.controller.student;

import com.stark.exam.model.Exam;
import com.stark.exam.model.Result;
import com.stark.exam.model.User;
import com.stark.exam.service.ExamService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/student/exams")
public class ExamListServlet extends HttpServlet {

    private final ExamService examService = new ExamService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        List<Exam> allExams = examService.getAllExams();
        List<Result> myResults = examService.getResultsForStudent(user.getId());

        Map<Integer, Result> resultMap = new HashMap<>();
        for (Result r : myResults) {
            resultMap.put(r.getExamId(), r);
        }

        request.setAttribute("exams", allExams);
        request.setAttribute("resultMap", resultMap);

        request.getRequestDispatcher("/WEB-INF/views/student/exam_list.jsp").forward(request, response);
    }
}
