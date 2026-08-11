<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Grievances" />
</jsp:include>
<jsp:include page="../common/sidebar.jsp" />

<div class="main-content page-enter">
    <div class="glass-panel glass-panel--strong">
        <h2>💬 Student Grievance Redressal</h2>
        <p>Submit inquiries or issues regarding exams, marks, or fees.</p>
    </div>

    <div style="display: grid; grid-template-columns: 1fr 1.5fr; gap: 24px;">
        <div class="glass-panel">
            <h3>Submit New Grievance</h3>
            <form action="${pageContext.request.contextPath}/student/grievance" method="post">
                <div class="form-group">
                    <label for="category">Category</label>
                    <select id="category" name="category" class="form-select" required>
                        <option value="Exam Dispute">Exam Dispute</option>
                        <option value="Attendance Discrepancy">Attendance Discrepancy</option>
                        <option value="Fee Query">Fee Query</option>
                        <option value="Technical Issue">Technical Issue</option>
                        <option value="Other">Other</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea id="description" name="description" class="form-control" rows="4" placeholder="Describe your issue in detail..." required></textarea>
                </div>
                <button type="submit" class="btn btn--primary" style="width: 100%;">Submit Grievance</button>
            </form>
        </div>

        <div>
            <!-- Search Bar -->
            <div class="search-bar-container">
                <input type="text" id="grievanceSearchInput" class="search-input" placeholder="🔍 Search your grievances..." onkeyup="filterTable('grievanceSearchInput', 'grievanceTable')">
                <button class="search-btn" onclick="filterTable('grievanceSearchInput', 'grievanceTable')">Search</button>
            </div>

            <div class="glass-panel">
                <h3>Your Grievance History</h3>
                <div class="table-responsive">
                    <table class="glass-table" id="grievanceTable">
                        <thead>
                            <tr>
                                <th>Category</th>
                                <th>Description</th>
                                <th>Submitted On</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="g" items="${grievances}">
                                <tr>
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
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
