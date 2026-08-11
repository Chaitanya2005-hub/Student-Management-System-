<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Exam Results Analytics" />
</jsp:include>
<jsp:include page="../common/sidebar.jsp" />

<div class="main-content page-enter">
    <div class="glass-panel glass-panel--strong">
        <h2>📊 Exam Results & Score Analytics</h2>
        <p>Review student evaluation summaries, pass rates, and security flag counts.</p>
    </div>

    <!-- Exam Selector -->
    <div class="glass-panel">
        <form action="${pageContext.request.contextPath}/teacher/exam-results" method="get" style="display: flex; gap: 12px; align-items: center;">
            <label for="examSelect" style="margin: 0; font-weight: bold;">Select Exam:</label>
            <select id="examSelect" name="examId" class="form-select" style="max-width: 400px;" onchange="this.form.submit()">
                <option value="">-- Choose Exam --</option>
                <c:forEach var="ex" items="${exams}">
                    <option value="${ex.id}" ${selectedExam != null && selectedExam.id == ex.id ? 'selected' : ''}>
                        ${ex.title} (${ex.examDate})
                    </option>
                </c:forEach>
            </select>
            <button type="submit" class="btn btn--primary">View Analytics</button>
        </form>
    </div>

    <c:if test="${selectedExam != null}">
        <div style="display: grid; grid-template-columns: repeat(3, 1fr); gap: 20px; margin-bottom: 24px;">
            <div class="glass-panel" style="text-align: center;">
                <span style="font-size: 0.85rem; color: var(--text-muted);">TOTAL SUBMISSIONS</span>
                <h3 style="font-size: 2rem; color: var(--accent); margin: 8px 0;">${totalStudents}</h3>
            </div>
            <div class="glass-panel" style="text-align: center;">
                <span style="font-size: 0.85rem; color: var(--text-muted);">PASSED STUDENTS (>=40%)</span>
                <h3 style="font-size: 2rem; color: var(--success); margin: 8px 0;">${passedCount}</h3>
            </div>
            <div class="glass-panel" style="text-align: center;">
                <span style="font-size: 0.85rem; color: var(--text-muted);">AVERAGE SCORE</span>
                <h3 style="font-size: 2rem; color: var(--warning); margin: 8px 0;">${averageScore}</h3>
            </div>
        </div>

        <!-- Search Bar -->
        <div class="search-bar-container">
            <input type="text" id="resultSearchInput" class="search-input" placeholder="🔍 Search student score, ID or security warnings..." onkeyup="filterTable('resultSearchInput', 'resultTable')">
            <button class="search-btn" onclick="filterTable('resultSearchInput', 'resultTable')">Search</button>
        </div>

        <div class="glass-panel">
            <h3>Student Performance Roster</h3>
            <div class="table-responsive">
                <table class="glass-table" id="resultTable">
                    <thead>
                        <tr>
                            <th>Result ID</th>
                            <th>Student User ID</th>
                            <th>Score</th>
                            <th>Total Marks</th>
                            <th>Percentage</th>
                            <th>Security Warnings</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="res" items="${results}">
                            <tr>
                                <td>#${res.id}</td>
                                <td>ERP: ${res.studentErpId != null ? res.studentErpId : 'N/A'}</td>
                                <td><strong style="color: var(--success);">${res.score}</strong></td>
                                <td>${res.totalMarks}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${res.totalMarks > 0}">
                                            ${String.format("%.1f", (res.score * 100.0) / res.totalMarks)}%
                                        </c:when>
                                        <c:otherwise>N/A</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${res.securityWarnings > 0}">
                                            <span class="badge badge--blocked">${res.securityWarnings} Warnings</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge--active">0</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </c:if>
</div>

<jsp:include page="../common/footer.jsp" />
