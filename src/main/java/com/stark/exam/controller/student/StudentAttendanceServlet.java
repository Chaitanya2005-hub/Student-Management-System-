package com.stark.exam.controller.student;

import com.stark.exam.model.Attendance;
import com.stark.exam.model.User;
import com.stark.exam.service.AcademicService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/student/attendance")
public class StudentAttendanceServlet extends HttpServlet {

    private final AcademicService academicService = new AcademicService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        List<Attendance> attendanceList = academicService.getAttendanceForStudent(user.getId());

        int presentCount = 0;
        for (Attendance a : attendanceList) {
            if ("Present".equalsIgnoreCase(a.getStatus())) presentCount++;
        }
        double percentage = attendanceList.isEmpty() ? 0.0 : (presentCount * 100.0 / attendanceList.size());

        request.setAttribute("attendanceList", attendanceList);
        request.setAttribute("presentCount", presentCount);
        request.setAttribute("totalCount", attendanceList.size());
        request.setAttribute("percentage", String.format("%.2f", percentage));

        request.getRequestDispatcher("/WEB-INF/views/student/attendance.jsp").forward(request, response);
    }
}
