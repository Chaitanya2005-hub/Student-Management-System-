package com.stark.exam.controller.author;

import com.stark.exam.model.Announcement;
import com.stark.exam.model.Notice;
import com.stark.exam.model.User;
import com.stark.exam.service.CommunicationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/author/post-notice")
public class PostNoticeServlet extends HttpServlet {

    private final CommunicationService commService = new CommunicationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Notice> notices = commService.getAllNotices();
        List<Announcement> announcements = commService.getActiveAnnouncements("all");

        request.setAttribute("notices", notices);
        request.setAttribute("announcements", announcements);

        request.getRequestDispatcher("/WEB-INF/views/author/post_notice.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        String type = request.getParameter("type"); // "notice" or "announcement"
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        if ("announcement".equalsIgnoreCase(type)) {
            String audience = request.getParameter("targetAudience");
            Announcement a = new Announcement();
            a.setTitle(title);
            a.setContent(content);
            a.setTargetAudience(audience != null ? audience : "all");
            a.setAuthorId(user.getId());
            a.setActive(true);
            commService.createAnnouncement(a);
        } else {
            Notice n = new Notice();
            n.setTitle(title);
            n.setMessage(content);
            n.setPostedBy(user.getId());
            commService.createNotice(n);
        }

        response.sendRedirect(request.getContextPath() + "/author/post-notice?msg=posted");
    }
}
