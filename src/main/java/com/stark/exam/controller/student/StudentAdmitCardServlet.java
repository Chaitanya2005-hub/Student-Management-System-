package com.stark.exam.controller.student;

import com.stark.exam.model.AdmitCard;
import com.stark.exam.model.User;
import com.stark.exam.service.StudentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/student/admit-card")
public class StudentAdmitCardServlet extends HttpServlet {

    private final StudentService studentService = new StudentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        String action = request.getParameter("action");

        System.out.println("DEBUG AdmitCardServlet: User = " + (user != null ? user.getId() : "null"));
        System.out.println("DEBUG AdmitCardServlet: Action = " + action);

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=not_logged_in");
            return;
        }

        if ("download".equals(action)) {
            // Handle download
            handleDownload(request, response, user);
        } else {
            // Show admit card page
            AdmitCard admitCard = studentService.getAdmitCardForStudent(user.getId());
            System.out.println("DEBUG AdmitCardServlet: AdmitCard = " + (admitCard != null ? admitCard.getStatus() : "null"));

            request.setAttribute("admitCard", admitCard);
            request.getRequestDispatcher("/WEB-INF/views/student/admit_card.jsp").forward(request, response);
        }
    }

    private void handleDownload(HttpServletRequest request, HttpServletResponse response, User user)
            throws IOException {
        System.out.println("DEBUG AdmitCardServlet: Download request for user " + user.getId());

        AdmitCard admitCard = studentService.getAdmitCardForStudent(user.getId());
        System.out.println("DEBUG AdmitCardServlet: AdmitCard for download = " + (admitCard != null ? admitCard.getStatus() : "null"));

        if (admitCard == null) {
            System.out.println("DEBUG AdmitCardServlet: No admit card found, redirecting with error");
            response.sendRedirect(request.getContextPath() + "/student/admit-card?error=no_admit_card");
            return;
        }

        if (!"Released".equalsIgnoreCase(admitCard.getStatus())) {
            System.out.println("DEBUG AdmitCardServlet: Admit card not released, status = " + admitCard.getStatus());
            response.sendRedirect(request.getContextPath() + "/student/admit-card?error=not_released");
            return;
        }

        // Generate HTML-based admit card for download
        response.setContentType("text/html");
        response.setHeader("Content-Disposition", "attachment; filename=\"admit_card_" + user.getId() + ".html\"");

        String htmlContent = generateAdmitCardHTML(user, admitCard);
        response.getWriter().write(htmlContent);
        System.out.println("DEBUG AdmitCardServlet: Admit card downloaded successfully");
    }

    private String generateAdmitCardHTML(User user, AdmitCard admitCard) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        html.append("<head>\n");
        html.append("<title>Examination Admit Card</title>\n");
        html.append("<style>\n");
        html.append("body { font-family: Arial, sans-serif; margin: 40px; }\n");
        html.append(".header { text-align: center; border-bottom: 2px solid #333; padding-bottom: 20px; margin-bottom: 20px; }\n");
        html.append(".header h1 { margin: 0; color: #333; }\n");
        html.append(".content { border: 2px solid #333; padding: 30px; margin: 20px 0; }\n");
        html.append(".row { margin: 15px 0; }\n");
        html.append(".label { font-weight: bold; width: 200px; display: inline-block; }\n");
        html.append(".value { display: inline-block; }\n");
        html.append(".status { font-size: 24px; font-weight: bold; color: green; text-align: center; margin: 20px 0; }\n");
        html.append(".footer { text-align: center; margin-top: 30px; font-size: 12px; color: #666; }\n");
        html.append("</style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("<div class=\"header\">\n");
        html.append("<h1>EXAMINATION ADMIT CARD</h1>\n");
        html.append("<p>Online Examination & Student Management System</p>\n");
        html.append("</div>\n");

        html.append("<div class=\"content\">\n");
        html.append("<div class=\"status\">✅ ADMIT CARD RELEASED</div>\n");
        html.append("<div class=\"row\"><span class=\"label\">Student Name:</span><span class=\"value\">" + (user.getFullName() != null ? user.getFullName() : "N/A") + "</span></div>\n");
        html.append("<div class=\"row\"><span class=\"label\">ERP ID:</span><span class=\"value\">" + (user.getErpId() != null ? user.getErpId() : "N/A") + "</span></div>\n");
        html.append("<div class=\"row\"><span class=\"label\">User ID:</span><span class=\"value\">" + user.getId() + "</span></div>\n");
        html.append("<div class=\"row\"><span class=\"label\">Department:</span><span class=\"value\">" + (user.getDepartment() != null ? user.getDepartment() : "N/A") + "</span></div>\n");
        html.append("<div class=\"row\"><span class=\"label\">Section:</span><span class=\"value\">" + (user.getSection() != null ? user.getSection() : "N/A") + "</span></div>\n");
        html.append("<div class=\"row\"><span class=\"label\">Year:</span><span class=\"value\">" + (user.getYear() != null ? user.getYear() : "N/A") + "</span></div>\n");
        html.append("<div class=\"row\"><span class=\"label\">Status:</span><span class=\"value\">" + admitCard.getStatus() + "</span></div>\n");
        html.append("</div>\n");

        html.append("<div class=\"footer\">\n");
        html.append("<p>This is an official admit card for online examinations.</p>\n");
        html.append("<p>Generated on: " + new java.util.Date() + "</p>\n");
        html.append("<p>Please bring this admit card (printed or digital) during examination.</p>\n");
        html.append("</div>\n");

        html.append("</body>\n");
        html.append("</html>");

        return html.toString();
    }
}
