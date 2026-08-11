<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Attendance Record" />
</jsp:include>
<jsp:include page="../common/sidebar.jsp" />

<div class="main-content page-enter">
    <div class="glass-panel glass-panel--strong" style="display: flex; justify-content: space-between; align-items: center;">
        <div>
            <h2>📅 Attendance Record</h2>
            <p>Track your daily class attendance.</p>
        </div>
        <div style="background: rgba(52, 211, 153, 0.15); border: 1px solid var(--success); padding: 12px 24px; border-radius: 14px; text-align: center;">
            <span style="font-size: 0.75rem; color: var(--text-muted); display: block;">ATTENDANCE PERCENTAGE</span>
            <span style="font-size: 1.6rem; font-weight: bold; color: var(--success);">${percentage}%</span>
        </div>
    </div>

    <div class="glass-panel">
        <div class="table-responsive">
            <table class="glass-table">
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Status</th>
                        <th>Marked By (Faculty ID)</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="att" items="${attendanceList}">
                        <tr>
                            <td>${att.date}</td>
                            <td>
                                <span class="badge badge--${att.status == 'Present' ? 'active' : 'blocked'}">${att.status}</span>
                            </td>
                            <td>Faculty #${att.markedBy != null ? att.markedBy : 'System'}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
