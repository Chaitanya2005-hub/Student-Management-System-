<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Manage Admit Cards" />
</jsp:include>
<jsp:include page="../common/sidebar.jsp" />

<div class="main-content page-enter">
    <div class="glass-panel glass-panel--strong">
        <h2>🎫 Admit Card Clearance Control</h2>
        <p>Release or block student exam permit hall tickets.</p>
    </div>

    <!-- Search Bar -->
    <div class="search-bar-container">
        <input type="text" id="admitCardSearch" class="search-input" placeholder="🔍 Search student admit status or user ID..." onkeyup="filterTable('admitCardSearch', 'admitCardTable')">
        <button class="search-btn" onclick="filterTable('admitCardSearch', 'admitCardTable')">Search</button>
    </div>

    <div class="glass-panel">
        <h3>Student Admit Clearances</h3>
        <div class="table-responsive">
            <table class="glass-table" id="admitCardTable">
                <thead>
                    <tr>
                        <th>Student User ID</th>
                        <th>Student Name</th>
                        <th>Current Admit Status</th>
                        <th>Set Status Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="u" items="${users}">
                        <c:if test="${u.role == 'student'}">
                            <tr>
                                <td>Student User #${u.id}</td>
                                <td><strong>${u.fullName} (${u.erpId})</strong></td>
                                <td>
                                    <span class="badge badge--released">Active Clearance Check</span>
                                </td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/author/manage-admit-cards" method="post" style="display: flex; gap: 8px;">
                                        <input type="hidden" name="studentId" value="${u.id}">
                                        <select name="status" class="form-select" style="padding: 4px 8px; font-size: 0.8rem;">
                                            <option value="Released">Released (Clear)</option>
                                            <option value="Blocked">Blocked (Hold)</option>
                                        </select>
                                        <button type="submit" class="btn btn--primary" style="padding: 4px 10px; font-size: 0.75rem;">Update</button>
                                    </form>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
