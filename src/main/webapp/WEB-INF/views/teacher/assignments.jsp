<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Manage Assignments" />
</jsp:include>
<jsp:include page="../common/sidebar.jsp" />

<div class="main-content page-enter">
    <div class="glass-panel glass-panel--strong">
        <h2>📚 Assignment Management & Submissions</h2>
        <p>Post new coursework assignments and grade student submissions.</p>
    </div>

    <div style="display: grid; grid-template-columns: 1fr 1.5fr; gap: 24px;">
        <div class="glass-panel">
            <h3>Post New Assignment</h3>
            <form action="${pageContext.request.contextPath}/teacher/assignments" method="post">
                <div class="form-group">
                    <label for="title">Title</label>
                    <input type="text" id="title" name="title" class="form-control" required placeholder="e.g. Chapter 4 Quiz Submission">
                </div>
                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea id="description" name="description" class="form-control" rows="3" required placeholder="Enter instructions..."></textarea>
                </div>
                <div class="form-group">
                    <label for="dueDate">Due Date</label>
                    <input type="date" id="dueDate" name="dueDate" class="form-control" required>
                </div>
                <button type="submit" class="btn btn--primary" style="width: 100%;">Create Assignment</button>
            </form>
        </div>

        <div>
            <!-- Search Bar -->
            <div class="search-bar-container">
                <input type="text" id="teacherAssignSearch" class="search-input" placeholder="🔍 Search assignments by title or due date..." onkeyup="filterTable('teacherAssignSearch', 'teacherAssignTable')">
                <button class="search-btn" onclick="filterTable('teacherAssignSearch', 'teacherAssignTable')">Search</button>
            </div>

            <div class="glass-panel">
                <h3>Assignments & Submissions</h3>
                <div class="table-responsive">
                    <table class="glass-table" id="teacherAssignTable">
                        <thead>
                            <tr>
                                <th>Title</th>
                                <th>Due Date</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="a" items="${assignments}">
                                <tr>
                                    <td><strong>${a.title}</strong></td>
                                    <td>${a.dueDate}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/teacher/assignments?assignmentId=${a.id}" class="btn btn--primary" style="padding: 4px 10px; font-size: 0.75rem;">View Submissions</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <c:if test="${submissions != null}">
                <div class="glass-panel">
                    <h3>Submissions for Assignment #${selectedAssignmentId}</h3>
                    <div class="table-responsive">
                        <table class="glass-table">
                            <thead>
                                <tr>
                                    <th>Student User ID</th>
                                    <th>Submission Text / Answer</th>
                                    <th>Submitted Date</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="sub" items="${submissions}">
                                    <tr>
                                        <td>ERP: ${sub.studentErpId != null ? sub.studentErpId : 'N/A'}</td>
                                        <td>${sub.submissionText}</td>
                                        <td>${sub.submissionDate}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
