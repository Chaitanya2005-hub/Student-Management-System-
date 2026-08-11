<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Author & Admin Dashboard" />
</jsp:include>
<jsp:include page="../common/sidebar.jsp" />

<div class="main-content page-enter">
    <div class="glass-panel glass-panel--strong">
        <h2>Welcome, Administrator / Author ${user.fullName}! 👑</h2>
        <p>System Administration, User Directory, Notice Desk & Global Oversight.</p>
    </div>

    <div style="display: grid; grid-template-columns: repeat(3, 1fr); gap: 20px; margin-bottom: 24px;">
        <div class="glass-panel" style="text-align: center;">
            <span style="font-size: 0.85rem; color: var(--text-muted);">TOTAL SYSTEM USERS</span>
            <h3 style="font-size: 2rem; color: var(--accent-author); margin: 8px 0;">${totalUsers}</h3>
        </div>
        <div class="glass-panel" style="text-align: center;">
            <span style="font-size: 0.85rem; color: var(--text-muted);">TOTAL EXAMS REGISTERED</span>
            <h3 style="font-size: 2rem; color: var(--accent-student); margin: 8px 0;">${totalExams}</h3>
        </div>
        <div class="glass-panel" style="text-align: center;">
            <span style="font-size: 0.85rem; color: var(--text-muted);">SYSTEM ACTIONS</span>
            <div style="margin-top: 10px; display: flex; gap: 8px; justify-content: center;">
                <a href="${pageContext.request.contextPath}/author/manage-users" class="btn btn--primary" style="font-size: 0.8rem; padding: 6px 12px;">+ User</a>
                <a href="${pageContext.request.contextPath}/author/post-notice" class="btn btn--danger" style="font-size: 0.8rem; padding: 6px 12px;">📢 Notice</a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
