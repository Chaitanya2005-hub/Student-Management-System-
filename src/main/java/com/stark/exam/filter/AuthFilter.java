package com.stark.exam.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = uri.substring(contextPath.length());

        // Allow static resources, login page, and login servlet
        if (path.startsWith("/static/") || path.equals("/index.jsp") || path.equals("/") || path.equals("/login") || path.equals("/logout")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("user") != null);

        if (loggedIn) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(contextPath + "/index.jsp?error=please_login");
        }
    }
}
