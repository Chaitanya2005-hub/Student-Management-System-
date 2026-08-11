<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Assignments" />
</jsp:include>
<jsp:include page="../common/sidebar.jsp" />

<div class="main-content page-enter">
    <div class="glass-panel glass-panel--strong">
        <h2>📚 Class Assignments</h2>
        <p>View active assignments and submit your work.</p>
    </div>

    <!-- Search Bar -->
    <div class="search-bar-container">
        <input type="text" id="assignSearchInput" class="search-input" placeholder="🔍 Search assignments by title or description..." onkeyup="filterTable('assignSearchInput', 'assignmentsTable')">
        <button class="search-btn" onclick="filterTable('assignSearchInput', 'assignmentsTable')">Search</button>
    </div>

    <div class="glass-panel">
        <div class="table-responsive">
            <table class="glass-table" id="assignmentsTable">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Description</th>
                        <th>Due Date</th>
                        <th>Submit Answer</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="a" items="${assignments}">
                        <tr>
                            <td>#${a.id}</td>
                            <td><strong>${a.title}</strong></td>
                            <td>${a.description}</td>
                            <td><span style="color: var(--warning);">${a.dueDate}</span></td>
                            <td>
                                <form action="${pageContext.request.contextPath}/student/assignments" method="post" style="display: flex; gap: 8px;">
                                    <input type="hidden" name="assignmentId" value="${a.id}">
                                    <input type="text" name="submissionText" class="form-control" placeholder="Enter answer or file link..." required style="padding: 6px 12px; font-size: 0.85rem;">
                                    <button type="submit" class="btn btn--primary" style="padding: 6px 14px; font-size: 0.85rem;">Submit</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
