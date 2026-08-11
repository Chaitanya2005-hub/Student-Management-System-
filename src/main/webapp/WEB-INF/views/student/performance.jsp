<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Performance & Results" />
</jsp:include>
<jsp:include page="../common/sidebar.jsp" />

<div class="main-content page-enter">
    <div class="glass-panel glass-panel--strong">
        <h2>📈 Performance & Results Overview</h2>
        <p>Review your exam scores and course grades.</p>
    </div>

    <div class="glass-panel">
        <h3>📝 Online Exam Results</h3>
        <div class="table-responsive">
            <table class="glass-table">
                <thead>
                    <tr>
                        <th>Result ID</th>
                        <th>Exam ID</th>
                        <th>Score Obtained</th>
                        <th>Total Marks</th>
                        <th>Percentage</th>
                        <th>Security Warnings</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="res" items="${examResults}">
                        <tr>
                            <td>#${res.id}</td>
                            <td>Exam #${res.examId}</td>
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
                                        <span class="badge badge--unpaid">${res.securityWarnings} Warnings</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge badge--active">Clean (0)</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <div class="glass-panel">
        <h3>🎓 Course Grades</h3>
        <div class="table-responsive">
            <table class="glass-table">
                <thead>
                    <tr>
                        <th>Course ID</th>
                        <th>Exam Type</th>
                        <th>Semester</th>
                        <th>Marks Obtained</th>
                        <th>Max Marks</th>
                        <th>Grade</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="gr" items="${courseGrades}">
                        <tr>
                            <td>Course #${gr.courseId}</td>
                            <td>${gr.examType}</td>
                            <td>Sem ${gr.semester}</td>
                            <td>${gr.marksObtained}</td>
                            <td>${gr.maxMarks}</td>
                            <td><strong>${gr.grade}</strong></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
