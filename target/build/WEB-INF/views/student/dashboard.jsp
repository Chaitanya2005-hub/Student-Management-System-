<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Student Dashboard" />
</jsp:include>
<jsp:include page="../common/sidebar.jsp" />

<div class="main-content page-enter">
    <div class="glass-panel glass-panel--strong">
        <h2>Welcome back, ${user.fullName}! 👋</h2>
        <p>Department: <strong>${user.department != null ? user.department : 'General'}</strong> | ERP ID: <strong>${user.erpId}</strong></p>
    </div>

    <!-- Debug Info -->
    <div class="glass-panel" style="margin-bottom: 16px; font-size: 0.85rem; color: var(--text-muted);">
        <p><strong>Debug Info:</strong></p>
        <p>Total Exams: ${allExams != null ? allExams.size() : 0}</p>
        <p>Active Exams: ${activeExams != null ? activeExams.size() : 0}</p>
        <p>Scheduled Exams: ${scheduledExams != null ? scheduledExams.size() : 0}</p>
        <p>Announcements: ${announcements != null ? announcements.size() : 0}</p>
    </div>

    <div style="display: grid; grid-template-columns: 2fr 1fr; gap: 24px;">
        <div>
            <div class="glass-panel">
                <h3>⚡ Active & Upcoming Exams</h3>
                <c:choose>
                    <c:when test="${not empty activeExams && !activeExams.isEmpty()}">
                        <c:forEach var="exam" items="${activeExams}">
                            <div style="background: rgba(127, 156, 245, 0.15); border: 1px solid var(--accent-student); border-radius: 12px; padding: 16px; margin-bottom: 12px; display: flex; justify-content: space-between; align-items: center;">
                                <div>
                                    <h4 style="margin: 0; color: #fff;">${exam.title}</h4>
                                    <p style="margin: 4px 0 0 0; font-size: 0.85rem;">Date: ${exam.examDate} | Duration: ${exam.durationMinutes} mins</p>
                                </div>
                                <a href="${pageContext.request.contextPath}/student/take-exam?id=${exam.id}" class="btn btn--primary">Start Exam Now 🚀</a>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p>No active exams right now.</p>
                        <c:if test="${not empty allExams && !allExams.isEmpty()}">
                            <p style="color: var(--text-muted); font-size: 0.85rem; margin-top: 8px;">
                                💡 There are ${allExams.size()} total exams in the system. Check "Scheduled Exams" below or contact administrator to activate exams.
                            </p>
                        </c:if>
                        <c:if test="${allExams == null || allExams.isEmpty()}">
                            <p style="color: var(--text-muted); font-size: 0.85rem; margin-top: 8px;">
                                💡 No exams found in the system. Please contact administrator to create exams.
                            </p>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="glass-panel">
                <h3>📅 Scheduled Exams</h3>
                <c:choose>
                    <c:when test="${not empty scheduledExams && !scheduledExams.isEmpty()}">
                        <div class="table-responsive">
                            <table class="glass-table">
                                <thead>
                                    <tr>
                                        <th>Exam Title</th>
                                        <th>Date</th>
                                        <th>Start Time</th>
                                        <th>Duration</th>
                                        <th>Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="ex" items="${scheduledExams}">
                                        <tr>
                                            <td>${ex.title}</td>
                                            <td>${ex.examDate}</td>
                                            <td>${ex.startTime}</td>
                                            <td>${ex.durationMinutes} mins</td>
                                            <td><span class="badge badge--${ex.status}">${ex.status}</span></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <p>No scheduled exams found.</p>
                        <c:if test="${not empty allExams && !allExams.isEmpty()}">
                            <p style="color: var(--text-muted); font-size: 0.85rem; margin-top: 8px;">
                                💡 There are ${allExams.size()} total exams in the system. Contact administrator to schedule exams.
                            </p>
                        </c:if>
                        <c:if test="${allExams == null || allExams.isEmpty()}">
                            <p style="color: var(--text-muted); font-size: 0.85rem; margin-top: 8px;">
                                💡 No exams found in the system. Please contact administrator to create exams.
                            </p>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <div>
            <div class="glass-panel">
                <h3>📢 Announcements</h3>
                <c:choose>
                    <c:when test="${not empty announcements && !announcements.isEmpty()}">
                        <c:forEach var="anc" items="${announcements}">
                            <div style="border-bottom: 1px solid rgba(255,255,255,0.1); padding-bottom: 12px; margin-bottom: 12px;">
                                <h4 style="font-size: 1rem; color: var(--accent);">${anc.title}</h4>
                                <p style="font-size: 0.85rem;">${anc.content}</p>
                                <span style="font-size: 0.75rem; color: var(--text-muted);">${anc.createdAt}</span>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p>No announcements at this time.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
