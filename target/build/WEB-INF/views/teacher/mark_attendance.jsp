<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Mark Attendance" />
</jsp:include>
<jsp:include page="../common/sidebar.jsp" />

<div class="main-content page-enter">
    <div class="glass-panel glass-panel--strong">
        <h2>✅ Mark Student Attendance</h2>
        <p>Record daily class presence and absence.</p>
    </div>

    <div style="display: grid; grid-template-columns: 1fr 1.5fr; gap: 24px;">
        <div class="glass-panel">
            <h3>Mark Entry</h3>
            <form action="${pageContext.request.contextPath}/teacher/mark-attendance" method="post">
                <div class="form-group">
                    <label for="studentId">Select Student</label>
                    <select id="studentId" name="studentId" class="form-select" required>
                        <c:forEach var="u" items="${users}">
                            <c:if test="${u.role == 'student'}">
                                <option value="${u.id}">${u.fullName} (${u.username} / ERP: ${u.erpId})</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="date">Date</label>
                    <input type="date" id="date" name="date" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="status">Status</label>
                    <select id="status" name="status" class="form-select" required>
                        <option value="Present">Present</option>
                        <option value="Absent">Absent</option>
                    </select>
                </div>
                <button type="submit" class="btn btn--primary" style="width: 100%;">Save Attendance Record</button>
            </form>
        </div>

        <div>
            <!-- Search Bar -->
            <div class="search-bar-container">
                <input type="text" id="studentRosterSearch" class="search-input" placeholder="🔍 Search student roster by name, ERP or role..." onkeyup="filterTable('studentRosterSearch', 'studentRosterTable')">
                <button class="search-btn" onclick="filterTable('studentRosterSearch', 'studentRosterTable')">Search</button>
            </div>

            <div class="glass-panel">
                <h3>Student Roster Reference</h3>
                <div class="table-responsive">
                    <table class="glass-table" id="studentRosterTable">
                        <thead>
                            <tr>
                                <th>User ID</th>
                                <th>Full Name</th>
                                <th>ERP ID</th>
                                <th>Department</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="u" items="${users}">
                                <c:if test="${u.role == 'student'}">
                                    <tr>
                                        <td>#${u.id}</td>
                                        <td><strong>${u.fullName}</strong></td>
                                        <td>${u.erpId}</td>
                                        <td>${u.department}</td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
