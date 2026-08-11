package com.stark.exam.controller.student;

import com.stark.exam.model.Grade;
import com.stark.exam.model.Result;
import com.stark.exam.model.User;
import com.stark.exam.service.AcademicService;
import com.stark.exam.service.ExamService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/student/performance")
public class StudentPerformanceServlet extends HttpServlet {

    private final ExamService examService = new ExamService();
    private final AcademicService academicService = new AcademicService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        List<Result> examResults = examService.getResultsForStudent(user.getId());
        List<Grade> courseGrades = academicService.getGradesForStudent(user.getId());

        request.setAttribute("examResults", examResults);
        request.setAttribute("courseGrades", courseGrades);

        request.getRequestDispatcher("/WEB-INF/views/student/performance.jsp").forward(request, response);
    }
}
