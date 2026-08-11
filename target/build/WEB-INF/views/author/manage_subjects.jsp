<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Manage Subjects & Courses" />
</jsp:include>
<jsp:include page="../common/sidebar.jsp" />

<div class="main-content page-enter">
    <div class="glass-panel glass-panel--strong">
        <h2>📖 Academic Subjects & Courses</h2>
        <p>Manage curriculum subjects and course catalogs.</p>
    </div>

    <div style="display: grid; grid-template-columns: 1fr 1.5fr; gap: 24px;">
        <div class="glass-panel">
            <h3>Add New Subject</h3>
            <form action="${pageContext.request.contextPath}/author/manage-subjects" method="post">
                <div class="form-group">
                    <label for="name">Subject Name</label>
                    <input type="text" id="name" name="name" class="form-control" required placeholder="e.g. Data Structures">
                </div>
                <div class="form-group">
                    <label for="code">Subject Code</label>
                    <input type="text" id="code" name="code" class="form-control" required placeholder="e.g. CS201">
                </div>
                <div class="form-group">
                    <label for="department">Department</label>
                    <input type="text" id="department" name="department" class="form-control" placeholder="Computer Science">
                </div>
                <button type="submit" class="btn btn--primary" style="width: 100%;">Create Subject</button>
            </form>
        </div>

        <div>
            <!-- Search Bar -->
            <div class="search-bar-container">
                <input type="text" id="subjectSearchInput" class="search-input" placeholder="🔍 Search subjects by name, code or department..." onkeyup="filterTable('subjectSearchInput', 'subjectTable')">
                <button class="search-btn" onclick="filterTable('subjectSearchInput', 'subjectTable')">Search</button>
            </div>

            <div class="glass-panel">
                <h3>Curriculum Subjects</h3>
                <div class="table-responsive">
                    <table class="glass-table" id="subjectTable">
                        <thead>
                            <tr>
                                <th>Code</th>
                                <th>Name</th>
                                <th>Department</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="s" items="${subjects}">
                                <tr>
                                    <td><span class="badge badge--active">${s.code}</span></td>
                                    <td><strong>${s.name}</strong></td>
                                    <td>${s.department}</td>
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
