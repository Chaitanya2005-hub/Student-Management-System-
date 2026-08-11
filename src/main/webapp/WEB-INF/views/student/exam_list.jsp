<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Online Exams" />
</jsp:include>
<jsp:include page="../common/sidebar.jsp" />

<div class="main-content page-enter">
    <div class="glass-panel glass-panel--strong">
        <h2>📝 Online Examinations</h2>
        <p>View active, scheduled, and completed exams.</p>
    </div>

    <div class="glass-panel">
        <div class="table-responsive">
            <table class="glass-table">
                <thead>
                    <tr>
                        <th>Exam Title</th>
                        <th>Date</th>
                        <th>Start Time</th>
                        <th>Duration</th>
                        <th>Exam Status</th>
                        <th>Your Result</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="ex" items="${exams}">
                        <tr>
                            <td><strong>${ex.title}</strong></td>
                            <td>${ex.examDate}</td>
                            <td>${ex.startTime}</td>
                            <td>${ex.durationMinutes} mins</td>
                            <td>
                                <span class="badge badge--${ex.status}">${ex.status}</span>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty resultMap[ex.id]}">
                                        <span style="color: #34d399; font-weight: bold;">Score: ${resultMap[ex.id].score} / ${resultMap[ex.id].totalMarks}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: var(--text-muted);">Not Taken</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty resultMap[ex.id]}">
                                        <button class="btn" disabled style="opacity: 0.5;">Completed</button>
                                    </c:when>
                                    <c:when test="${ex.status == 'active'}">
                                        <a href="${pageContext.request.contextPath}/student/take-exam?id=${ex.id}" class="btn btn--primary">Enter Exam</a>
                                    </c:when>
                                    <c:otherwise>
                                        <button class="btn" disabled style="opacity: 0.5;">Not Active</button>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
