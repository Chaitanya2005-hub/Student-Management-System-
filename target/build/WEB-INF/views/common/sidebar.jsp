<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="sidebar" id="sidebar">
    <button class="sidebar-toggle" id="sidebar-toggle" title="Toggle Sidebar">
        ◀
    </button>
    
    <div class="sidebar-logo">
        <c:choose>
            <c:when test="${sessionScope.userRole == 'student'}">
                <a href="${pageContext.request.contextPath}/student/dashboard" class="sidebar-logo-link">
                    <span>🎓</span>
                    <span>Stark System</span>
                </a>
            </c:when>
            <c:when test="${sessionScope.userRole == 'teacher'}">
                <a href="${pageContext.request.contextPath}/teacher/dashboard" class="sidebar-logo-link">
                    <span>🎓</span>
                    <span>Stark System</span>
                </a>
            </c:when>
            <c:when test="${sessionScope.userRole == 'author'}">
                <a href="${pageContext.request.contextPath}/author/dashboard" class="sidebar-logo-link">
                    <span>🎓</span>
                    <span>Stark System</span>
                </a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/index.jsp" class="sidebar-logo-link">
                    <span>🎓</span>
                    <span>Stark System</span>
                </a>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="sidebar-nav">
        <c:choose>
            <c:when test="${sessionScope.userRole == 'student'}">
                <a href="${pageContext.request.contextPath}/student/dashboard" class="nav-item">
                    <span>📊</span>
                    <span>Dashboard</span>
                </a>
                <a href="${pageContext.request.contextPath}/student/exams" class="nav-item">
                    <span>📝</span>
                    <span>Online Exams</span>
                </a>
                <a href="${pageContext.request.contextPath}/student/performance" class="nav-item">
                    <span>📈</span>
                    <span>Performance</span>
                </a>
                <a href="${pageContext.request.contextPath}/student/attendance" class="nav-item">
                    <span>📅</span>
                    <span>Attendance</span>
                </a>
                <a href="${pageContext.request.contextPath}/student/assignments" class="nav-item">
                    <span>📚</span>
                    <span>Assignments</span>
                </a>
                <a href="${pageContext.request.contextPath}/student/fees" class="nav-item">
                    <span>💳</span>
                    <span>Fee Details</span>
                </a>
                <a href="${pageContext.request.contextPath}/student/grievance" class="nav-item">
                    <span>💬</span>
                    <span>Grievances</span>
                </a>
                <a href="${pageContext.request.contextPath}/student/admit-card" class="nav-item">
                    <span>🎫</span>
                    <span>Admit Card</span>
                </a>
            </c:when>

            <c:when test="${sessionScope.userRole == 'teacher'}">
                <a href="${pageContext.request.contextPath}/teacher/dashboard" class="nav-item">
                    <span>📊</span>
                    <span>Dashboard</span>
                </a>
                <a href="${pageContext.request.contextPath}/teacher/schedule-exam" class="nav-item">
                    <span>📅</span>
                    <span>Schedule Exam</span>
                </a>
                <a href="${pageContext.request.contextPath}/teacher/manage-questions" class="nav-item">
                    <span>❓</span>
                    <span>Manage Questions</span>
                </a>
                <a href="${pageContext.request.contextPath}/teacher/live-monitor" class="nav-item">
                    <span>🔴</span>
                    <span>Live Proctoring</span>
                </a>
                <a href="${pageContext.request.contextPath}/teacher/exam-results" class="nav-item">
                    <span>📊</span>
                    <span>Exam Results</span>
                </a>
                <a href="${pageContext.request.contextPath}/teacher/qr-attendance" class="nav-item">
                    <span>📱</span>
                    <span>QR Attendance</span>
                </a>
                <a href="${pageContext.request.contextPath}/teacher/mark-attendance" class="nav-item">
                    <span>✅</span>
                    <span>Attendance</span>
                </a>
                <a href="${pageContext.request.contextPath}/teacher/assignments" class="nav-item">
                    <span>📚</span>
                    <span>Assignments</span>
                </a>
            </c:when>

            <c:when test="${sessionScope.userRole == 'author'}">
                <a href="${pageContext.request.contextPath}/author/dashboard" class="nav-item">
                    <span>📊</span>
                    <span>Dashboard</span>
                </a>
                <a href="${pageContext.request.contextPath}/author/manage-users" class="nav-item">
                    <span>👥</span>
                    <span>Manage Users</span>
                </a>
                <a href="${pageContext.request.contextPath}/author/manage-admit-cards" class="nav-item">
                    <span>🎫</span>
                    <span>Admit Cards</span>
                </a>
                <a href="${pageContext.request.contextPath}/author/post-notice" class="nav-item">
                    <span>📢</span>
                    <span>Notices</span>
                </a>
                <a href="${pageContext.request.contextPath}/author/manage-subjects" class="nav-item">
                    <span>📖</span>
                    <span>Subjects</span>
                </a>
                <a href="${pageContext.request.contextPath}/author/admin-fees" class="nav-item">
                    <span>💳</span>
                    <span>Fee Management</span>
                </a>
                <a href="${pageContext.request.contextPath}/author/system-reports" class="nav-item">
                    <span>📋</span>
                    <span>System Reports</span>
                </a>
            </c:when>
        </c:choose>
    </div>

    <div class="user-profile-badge">
        <div>
            <div style="font-weight: var(--fw-semibold); font-size: 0.9rem;">${sessionScope.userName}</div>
            <div style="font-size: 0.75rem; color: var(--text-muted); text-transform: uppercase;">${sessionScope.userRole}</div>
        </div>
        <div style="display: flex; gap: 8px;">
            <button id="theme-toggle" class="btn" style="padding: 6px 10px; font-size: 0.75rem;" title="Toggle Theme">
                🌙
            </button>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn--danger" style="padding: 6px 10px; font-size: 0.75rem;">Exit</a>
        </div>
    </div>
</div>

<script>
    // Sidebar toggle functionality
    document.getElementById('sidebar-toggle').addEventListener('click', function() {
        const sidebar = document.getElementById('sidebar');
        sidebar.classList.toggle('collapsed');
        
        // Update toggle button direction
        this.textContent = sidebar.classList.contains('collapsed') ? '▶' : '◀';
        
        // Save sidebar state to localStorage
        localStorage.setItem('sidebarCollapsed', sidebar.classList.contains('collapsed'));
    });

    // Restore sidebar state from localStorage
    document.addEventListener('DOMContentLoaded', function() {
        const sidebar = document.getElementById('sidebar');
        const sidebarToggle = document.getElementById('sidebar-toggle');
        const isCollapsed = localStorage.getItem('sidebarCollapsed') === 'true';
        
        if (isCollapsed && sidebar) {
            sidebar.classList.add('collapsed');
            if (sidebarToggle) {
                sidebarToggle.textContent = '▶';
            }
        }
    });

    // Theme toggle functionality
    document.getElementById('theme-toggle').addEventListener('click', function() {
        const html = document.documentElement;
        const currentTheme = html.getAttribute('data-theme');
        const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
        
        html.setAttribute('data-theme', newTheme);
        localStorage.setItem('theme', newTheme);
        
        // Update button icon
        this.textContent = newTheme === 'dark' ? '🌙' : '☀️';
    });

    // Set initial button icon based on current theme
    document.addEventListener('DOMContentLoaded', function() {
        const currentTheme = document.documentElement.getAttribute('data-theme');
        const themeToggle = document.getElementById('theme-toggle');
        if (themeToggle) {
            themeToggle.textContent = currentTheme === 'dark' ? '🌙' : '☀️';
        }
    });
</script>
