<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Teacher Dashboard" />
</jsp:include>
<jsp:include page="../common/sidebar.jsp" />

<div class="main-content page-enter">
    <div class="glass-panel glass-panel--strong">
        <h2>Welcome, Prof. ${user.fullName}! 👨‍🏫</h2>
        <p>Faculty Management & Online Examination Control Desk.</p>
    </div>

    <div style="display: grid; grid-template-columns: repeat(3, 1fr); gap: 20px; margin-bottom: 24px;">
        <div class="glass-panel" style="text-align: center;">
            <span style="font-size: 0.85rem; color: var(--text-muted);">TOTAL EXAMS</span>
            <h3 style="font-size: 2rem; color: var(--accent-teacher); margin: 8px 0;">${exams.size()}</h3>
        </div>
        <div class="glass-panel" style="text-align: center;">
            <span style="font-size: 0.85rem; color: var(--text-muted);">ACTIVE LIVE EXAMS</span>
            <h3 style="font-size: 2rem; color: var(--success); margin: 8px 0;">${activeExamsCount}</h3>
        </div>
        <div class="glass-panel" style="text-align: center;">
            <span style="font-size: 0.85rem; color: var(--text-muted);">QUICK ACTIONS</span>
            <div style="margin-top: 10px; display: flex; gap: 8px; justify-content: center;">
                <a href="${pageContext.request.contextPath}/teacher/schedule-exam" class="btn btn--primary" style="font-size: 0.8rem; padding: 6px 12px;">+ New Exam</a>
                <a href="${pageContext.request.contextPath}/teacher/live-monitor" class="btn btn--danger" style="font-size: 0.8rem; padding: 6px 12px;">🔴 Live Proctor</a>
            </div>
        </div>
    </div>

    <!-- Search Bar -->
    <div class="search-bar-container">
        <input type="text" id="teacherExamSearch" class="search-input" placeholder="🔍 Search exam titles..." onkeyup="filterTable('teacherExamSearch', 'teacherExamTable')">
        <button class="search-btn" onclick="filterTable('teacherExamSearch', 'teacherExamTable')">Search</button>
    </div>

    <div class="glass-panel">
        <h3>Exam Control Center</h3>
        <div class="table-responsive">
            <table class="glass-table" id="teacherExamTable">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Exam Title</th>
                        <th>Date</th>
                        <th>Start Time</th>
                        <th>Duration</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="ex" items="${exams}">
                        <tr>
                            <td>#${ex.id}</td>
                            <td><strong>${ex.title}</strong></td>
                            <td>${ex.examDate}</td>
                            <td>${ex.startTime}</td>
                            <td>${ex.durationMinutes} mins</td>
                            <td>
                                <span class="badge badge--${ex.status}">${ex.status}</span>
                            </td>
                            <td style="display: flex; gap: 6px;">
                                <a href="${pageContext.request.contextPath}/teacher/manage-questions?examId=${ex.id}" class="btn" style="padding: 4px 10px; font-size: 0.75rem;">Questions</a>
                                <a href="${pageContext.request.contextPath}/teacher/exam-results?examId=${ex.id}" class="btn" style="padding: 4px 10px; font-size: 0.75rem;">Results</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
