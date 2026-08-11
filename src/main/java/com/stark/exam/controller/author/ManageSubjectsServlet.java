package com.stark.exam.controller.author;

import com.stark.exam.model.Course;
import com.stark.exam.model.Subject;
import com.stark.exam.service.AcademicService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/author/manage-subjects")
public class ManageSubjectsServlet extends HttpServlet {

    private final AcademicService academicService = new AcademicService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Subject> subjects = academicService.getAllSubjects();
        List<Course> courses = academicService.getAllCourses();

        request.setAttribute("subjects", subjects);
        request.setAttribute("courses", courses);

        request.getRequestDispatcher("/WEB-INF/views/author/manage_subjects.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String code = request.getParameter("code");
        String department = request.getParameter("department");

        Subject s = new Subject();
        s.setName(name);
        s.setCode(code);
        s.setDepartment(department);

        academicService.createSubject(s);
        response.sendRedirect(request.getContextPath() + "/author/manage-subjects?msg=created");
    }
}
