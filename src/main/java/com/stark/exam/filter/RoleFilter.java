package com.stark.exam.filter;

import com.stark.exam.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class RoleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = uri.substring(contextPath.length());

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user != null) {
            String role = user.getRole();

            if (path.startsWith("/student/") && !"student".equalsIgnoreCase(role)) {
                res.sendRedirect(contextPath + getHomeForRole(role) + "?error=unauthorized");
                return;
            }
            if (path.startsWith("/teacher/") && !"teacher".equalsIgnoreCase(role) && !"author".equalsIgnoreCase(role)) {
                res.sendRedirect(contextPath + getHomeForRole(role) + "?error=unauthorized");
                return;
            }
            if (path.startsWith("/author/") && !"author".equalsIgnoreCase(role)) {
                res.sendRedirect(contextPath + getHomeForRole(role) + "?error=unauthorized");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private String getHomeForRole(String role) {
        if ("student".equalsIgnoreCase(role)) return "/student/dashboard";
        if ("teacher".equalsIgnoreCase(role)) return "/teacher/dashboard";
        if ("author".equalsIgnoreCase(role)) return "/author/dashboard";
        return "/index.jsp";
    }
}
