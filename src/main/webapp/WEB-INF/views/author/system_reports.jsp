<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="System Reports" />
</jsp:include>
<jsp:include page="../common/sidebar.jsp" />

<div class="main-content page-enter">
    <div class="glass-panel glass-panel--strong">
        <h2>📋 System Reports & Audit Desk</h2>
        <p>Comprehensive audit of user statistics, exam catalog, and student grievances.</p>
    </div>

    <!-- Search Bar -->
    <div class="search-bar-container">
        <input type="text" id="reportGrievanceSearch" class="search-input" placeholder="🔍 Search grievances by category, student ID or status..." onkeyup="filterTable('reportGrievanceSearch', 'reportGrievanceTable')">
        <button class="search-btn" onclick="filterTable('reportGrievanceSearch', 'reportGrievanceTable')">Search</button>
    </div>

    <div class="glass-panel">
        <h3>Student Grievances Report</h3>
        <div class="table-responsive">
            <table class="glass-table" id="reportGrievanceTable">
                <thead>
                    <tr>
                        <th>Grievance ID</th>
                        <th>Student User ID</th>
                        <th>Category</th>
                        <th>Description</th>
                        <th>Date</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="g" items="${grievances}">
                        <tr>
                            <td>#${g.id}</td>
                            <td>ERP: ${g.studentErpId != null ? g.studentErpId : 'N/A'}</td>
                            <td><strong>${g.category}</strong></td>
                            <td>${g.description}</td>
                            <td>${g.submissionDate}</td>
                            <td>
                                <span class="badge badge--${g.status == 'Resolved' ? 'active' : 'pending'}">${g.status}</span>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
