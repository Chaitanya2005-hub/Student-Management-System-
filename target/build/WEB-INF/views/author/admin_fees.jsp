<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Fee Management" />
</jsp:include>
<jsp:include page="../common/sidebar.jsp" />

<div class="main-content page-enter">
    <div class="glass-panel glass-panel--strong">
        <h2>💳 Fee Management & Verification</h2>
        <p>Issue fee invoices and record student payment statuses.</p>
    </div>

    <div style="display: grid; grid-template-columns: 1fr 1.5fr; gap: 24px;">
        <div class="glass-panel">
            <h3>Issue Fee Invoice</h3>
            <form action="${pageContext.request.contextPath}/author/admin-fees" method="post">
                <div class="form-group">
                    <label for="studentId">Select Student</label>
                    <select id="studentId" name="studentId" class="form-select" required>
                        <c:forEach var="u" items="${users}">
                            <c:if test="${u.role == 'student'}">
                                <option value="${u.id}">${u.fullName} (ERP: ${u.erpId})</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="feeType">Fee Type</label>
                    <input type="text" id="feeType" name="feeType" class="form-control" required placeholder="e.g. Semester Tuition Fee">
                </div>
                <div class="form-group">
                    <label for="amount">Amount (INR)</label>
                    <input type="number" step="0.01" id="amount" name="amount" class="form-control" required placeholder="15000.00">
                </div>
                <div class="form-group">
                    <label for="dueDate">Due Date</label>
                    <input type="date" id="dueDate" name="dueDate" class="form-control" required>
                </div>
                <button type="submit" class="btn btn--primary" style="width: 100%;">Create Fee Invoice</button>
            </form>
        </div>

        <div>
            <!-- Search Bar -->
            <div class="search-bar-container">
                <input type="text" id="adminFeeSearch" class="search-input" placeholder="🔍 Search fee records by student, type or status..." onkeyup="filterTable('adminFeeSearch', 'adminFeeTable')">
                <button class="search-btn" onclick="filterTable('adminFeeSearch', 'adminFeeTable')">Search</button>
            </div>

            <div class="glass-panel">
                <h3>Global Fee Records</h3>
                
                <!-- Debug Info -->
                <div style="background: #f0f0f0; padding: 10px; margin-bottom: 10px; border-radius: 5px;">
                    <strong>DEBUG: Fees List Size: ${fees != null ? fees.size() : 'null'}</strong><br>
                    <strong>DEBUG: First Fee ERP ID: ${fees != null and fees.size() > 0 ? fees[0].studentErpId : 'null'}</strong>
                </div>
                
                <div class="table-responsive">
                    <table class="glass-table" id="adminFeeTable">
                        <thead>
                            <tr>
                                <th>Student ERP ID</th>
                                <th>Fee Type</th>
                                <th>Amount</th>
                                <th>Status</th>
                                <th>Update Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="f" items="${fees}">
                                <tr>
                                    <td>ERP: ${f.studentErpId != null ? f.studentErpId : 'N/A'}</td>
                                    <td><strong>${f.feeType}</strong></td>
                                    <td>₹ ${f.amount}</td>
                                    <td><span class="badge badge--${f.status}">${f.status}</span></td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/author/admin-fees" method="post" style="display: flex; gap: 6px;">
                                            <input type="hidden" name="action" value="updateStatus">
                                            <input type="hidden" name="feeId" value="${f.id}">
                                            <select name="status" class="form-select" style="padding: 4px 8px; font-size: 0.8rem;">
                                                <option value="paid" ${f.status == 'paid' ? 'selected' : ''}>paid</option>
                                                <option value="unpaid" ${f.status == 'unpaid' ? 'selected' : ''}>unpaid</option>
                                                <option value="partial" ${f.status == 'partial' ? 'selected' : ''}>partial</option>
                                            </select>
                                            <button type="submit" class="btn btn--primary" style="padding: 4px 8px; font-size: 0.75rem;">Set</button>
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
