<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Schedule Exam" />
</jsp:include>
<jsp:include page="../common/sidebar.jsp" />

<div class="main-content page-enter">
    <div class="glass-panel glass-panel--strong">
        <h2>📅 Schedule & Manage Exams</h2>
        <p>Create new exams and switch exam statuses (Scheduled → Active → Completed).</p>
    </div>

    <div style="display: grid; grid-template-columns: 1fr 1.5fr; gap: 24px;">
        <div class="glass-panel">
            <h3>Schedule New Exam</h3>
            <form action="${pageContext.request.contextPath}/teacher/schedule-exam" method="post">
                <div class="form-group">
                    <label for="title">Exam Title</label>
                    <input type="text" id="title" name="title" class="form-control" placeholder="e.g. Midterm Java Programming" required>
                </div>
                <div class="form-group">
                    <label for="examDate">Exam Date</label>
                    <input type="date" id="examDate" name="examDate" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="startTime">Start Time</label>
                    <input type="time" id="startTime" name="startTime" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="durationMinutes">Duration (Minutes)</label>
                    <input type="number" id="durationMinutes" name="durationMinutes" class="form-control" value="60" required min="5" max="300">
                </div>
                <div class="form-group" style="display: flex; align-items: center; gap: 10px;">
                    <input type="checkbox" id="requiresApproval" name="requiresApproval" checked>
                    <label for="requiresApproval" style="margin: 0;">Requires Student Approval Request</label>
                </div>
                <button type="submit" class="btn btn--primary" style="width: 100%;">Create Scheduled Exam</button>
            </form>
        </div>

        <div>
            <!-- Search Bar -->
            <div class="search-bar-container">
                <input type="text" id="scheduleSearchInput" class="search-input" placeholder="🔍 Search scheduled exams..." onkeyup="filterTable('scheduleSearchInput', 'scheduleTable')">
                <button class="search-btn" onclick="filterTable('scheduleSearchInput', 'scheduleTable')">Search</button>
            </div>

            <div class="glass-panel">
                <h3>Existing Exams Lifecycle</h3>
                <div class="table-responsive">
                    <table class="glass-table" id="scheduleTable">
                        <thead>
                            <tr>
                                <th>Title</th>
                                <th>Date & Time</th>
                                <th>Status</th>
                                <th>Status Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="ex" items="${exams}">
                                <tr>
                                    <td><strong>${ex.title}</strong></td>
                                    <td>${ex.examDate}<br><span style="font-size:0.8rem; color:var(--text-muted);">${ex.startTime} (${ex.durationMinutes}m)</span></td>
                                    <td><span class="badge badge--${ex.status}">${ex.status}</span></td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/teacher/schedule-exam" method="post" style="display: flex; gap: 6px;">
                                            <input type="hidden" name="action" value="updateStatus">
                                            <input type="hidden" name="examId" value="${ex.id}">
                                            <select name="status" class="form-select" style="padding: 4px 8px; font-size: 0.8rem;">
                                                <option value="scheduled" ${ex.status == 'scheduled' ? 'selected' : ''}>Scheduled</option>
                                                <option value="active" ${ex.status == 'active' ? 'selected' : ''}>Active (Live)</option>
                                                <option value="completed" ${ex.status == 'completed' ? 'selected' : ''}>Completed</option>
                                            </select>
                                            <button type="submit" class="btn btn--primary" style="padding: 4px 8px; font-size: 0.75rem;">Set</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
