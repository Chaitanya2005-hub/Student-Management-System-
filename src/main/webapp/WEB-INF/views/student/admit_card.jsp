<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Admit Card" />
</jsp:include>
<jsp:include page="../common/sidebar.jsp" />

<div class="main-content page-enter">
    <div class="glass-panel glass-panel--strong">
        <h2>🎫 Examination Admit Card</h2>
        <p>Check your official admit status for examination clearance.</p>
    </div>

    <!-- Debug Info -->
    <div class="glass-panel" style="margin-bottom: 16px; font-size: 0.85rem; color: var(--text-muted);">
        <p><strong>Debug Info:</strong></p>
        <p>ERP ID: ${sessionScope.userErpId}</p>
        <p>Admit Card: ${not empty admitCard ? 'Found' : 'Not Found'}</p>
        <c:if test="${not empty admitCard}">
            <p>Admit Card Status: ${admitCard.status}</p>
        </c:if>
        <p>Error Parameter: ${param.error}</p>
    </div>

    <div class="glass-panel" style="max-width: 600px; margin: 0 auto; text-align: center; padding: 40px;">
        <c:choose>
            <c:when test="${not empty admitCard && admitCard.status == 'Released'}">
                <div style="font-size: 4rem; margin-bottom: 12px;">✅</div>
                <h3 style="color: var(--success);">ADMIT CARD RELEASED</h3>
                <p>Your examination hall ticket is active. You are cleared to take all scheduled online exams.</p>
                <div style="background: rgba(0,0,0,0.2); border: 1px dashed var(--success); padding: 16px; border-radius: 12px; margin-top: 20px;">
                    <p style="margin: 0;">Student Name: <strong>${sessionScope.userName}</strong></p>
                    <p style="margin: 4px 0 0 0;">Status: <span class="badge badge--released">RELEASED</span></p>
                </div>
                <div style="margin-top: 24px;">
                    <a href="${pageContext.request.contextPath}/student/admit-card?action=download" class="btn btn--primary" style="display: inline-block; text-decoration: none;">
                        📥 Download Admit Card
                    </a>
                </div>
            </c:when>
            <c:when test="${not empty admitCard && admitCard.status == 'Blocked'}">
                <div style="font-size: 4rem; margin-bottom: 12px;">🔒</div>
                <h3 style="color: var(--danger);">ADMIT CARD BLOCKED</h3>
                <p>Your examination admit card is blocked due to pending fees/verification.</p>
                <div style="background: rgba(248, 113, 113, 0.15); border: 1px dashed var(--danger); padding: 16px; border-radius: 12px; margin-top: 20px;">
                    <p style="margin: 0; color: #f87171;">Contact the institution administration / author desk to clear your hold.</p>
                </div>
            </c:when>
            <c:otherwise>
                <div style="font-size: 4rem; margin-bottom: 12px;">⚠️</div>
                <h3 style="color: var(--warning);">ADMIT CARD NOT FOUND</h3>
                <p>No admit card record exists for your account. This might be because:</p>
                <ul style="text-align: left; display: inline-block;">
                    <li>Your admit card hasn't been generated yet</li>
                    <li>Your student record is not properly linked</li>
                    <li>Database setup is incomplete</li>
                </ul>
                <div style="background: rgba(251, 191, 36, 0.15); border: 1px dashed var(--warning); padding: 16px; border-radius: 12px; margin-top: 20px;">
                    <p style="margin: 0; color: #fbbf24;">Please contact the administrator to generate your admit card.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
