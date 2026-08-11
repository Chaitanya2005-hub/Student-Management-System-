<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="User Management" />
</jsp:include>
<jsp:include page="../common/sidebar.jsp" />

<div class="main-content page-enter">
    <div class="glass-panel glass-panel--strong">
        <h2>👥 System User Management</h2>
        <p>Create, manage, and remove system users (students, teachers, authors).</p>
    </div>

    <div style="display: grid; grid-template-columns: 1fr 2fr; gap: 24px;">
        <div class="glass-panel">
            <h3>Add New User</h3>
            <form action="${pageContext.request.contextPath}/author/manage-users" method="post">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="fullName">Full Name</label>
                    <input type="text" id="fullName" name="fullName" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="role">Role</label>
                    <select id="role" name="role" class="form-select" required>
                        <option value="student">student</option>
                        <option value="teacher">teacher</option>
                        <option value="author">author</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="erpId">ERP ID</label>
                    <input type="text" id="erpId" name="erpId" class="form-control" placeholder="e.g. ERP2026-001">
                </div>
                <div class="form-group">
                    <label for="department">Department</label>
                    <input type="text" id="department" name="department" class="form-control" placeholder="e.g. Computer Science">
                </div>
                <div class="form-group">
                    <label for="section">Section</label>
                    <input type="text" id="section" name="section" class="form-control" maxlength="1" placeholder="A">
                </div>
                <div class="form-group">
                    <label for="year">Year</label>
                    <input type="number" id="year" name="year" class="form-control" placeholder="1-4">
                </div>
                <button type="submit" class="btn btn--primary" style="width: 100%;">Create Account</button>
            </form>
        </div>

        <div>
            <!-- Search Bar -->
            <div class="search-bar-container">
                <input type="text" id="userDirectorySearch" class="search-input" placeholder="🔍 Search users by name, username, role or ERP ID..." onkeyup="filterTable('userDirectorySearch', 'userDirectoryTable')">
                <button class="search-btn" onclick="filterTable('userDirectorySearch', 'userDirectoryTable')">Search</button>
            </div>

            <div class="glass-panel">
                <h3>User Directory (${users.size()})</h3>
                <div class="table-responsive">
                    <table class="glass-table" id="userDirectoryTable">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Full Name</th>
                                <th>Username</th>
                                <th>Role</th>
                                <th>ERP ID</th>
                                <th>Department</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="u" items="${users}">
                                <tr>
                                    <td>#${u.id}</td>
                                    <td><strong>${u.fullName}</strong></td>
                                    <td>${u.username}</td>
                                    <td>
                                        <span class="badge badge--${u.role == 'author' ? 'active' : (u.role == 'teacher' ? 'scheduled' : 'requested')}">${u.role}</span>
                                    </td>
                                    <td>${u.erpId}</td>
                                    <td>${u.department}</td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/author/manage-users" method="post" onsubmit="return confirm('Delete this user?');">
                                            <input type="hidden" name="action" value="delete">
                                            <input type="hidden" name="userId" value="${u.id}">
                                            <button type="submit" class="btn btn--danger" style="padding: 4px 8px; font-size: 0.75rem;">Delete</button>
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
