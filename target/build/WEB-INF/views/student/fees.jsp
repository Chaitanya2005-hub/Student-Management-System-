<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Fee Details" />
</jsp:include>
<jsp:include page="../common/sidebar.jsp" />

<div class="main-content page-enter">
    <div class="glass-panel glass-panel--strong">
        <h2>💳 Fee Details</h2>
        <p>Review your tuition, exam, and laboratory fees.</p>
    </div>

    <!-- Search Bar -->
    <div class="search-bar-container">
        <input type="text" id="feeSearchInput" class="search-input" placeholder="🔍 Search fees by type, status or amount..." onkeyup="filterTable('feeSearchInput', 'feesTable')">
        <button class="search-btn" onclick="filterTable('feeSearchInput', 'feesTable')">Search</button>
    </div>

    <div class="glass-panel">
        <div class="table-responsive">
            <!-- Debug Info -->
            <div style="background: #f0f0f0; padding: 10px; margin-bottom: 10px; border-radius: 5px;">
                <strong>DEBUG: Fees List Size: ${fees != null ? fees.size() : 'null'}</strong><br>
                <strong>DEBUG: Session User: ${sessionScope.user != null ? sessionScope.user.username : 'null'}</strong><br>
                <strong>DEBUG: Session User ID: ${sessionScope.user != null ? sessionScope.user.id : 'null'}</strong>
            </div>
            
            <c:if test="${fees == null or fees.size() == 0}">
                <div style="text-align: center; padding: 40px; background: #fff; border-radius: 10px;">
                    <h3 style="color: #666;">No fee records found</h3>
                    <p style="color: #999;">Please contact administration if you believe this is an error.</p>
                </div>
            </c:if>
            
            <c:if test="${fees != null and fees.size() > 0}">
                <table class="glass-table" id="feesTable">
                    <thead>
                        <tr>
                            <th>Fee ID</th>
                            <th>Fee Type</th>
                            <th>Amount (INR)</th>
                            <th>Due Date</th>
                            <th>Payment Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="f" items="${fees}">
                            <tr>
                                <td>#${f.id}</td>
                                <td><strong>${f.feeType}</strong></td>
                                <td>₹ ${f.amount}</td>
                                <td>${f.dueDate}</td>
                                <td>
                                    <span class="badge badge--${f.status}">${f.status}</span>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
