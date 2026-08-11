<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Live Proctoring Desk" />
</jsp:include>
<jsp:include page="../common/sidebar.jsp" />

<div class="main-content page-enter">
    <div class="glass-panel glass-panel--strong">
        <h2>🔴 Real-Time Proctoring & Broadcast Desk</h2>
        <p>Monitor student heartbeats, security warnings, and send live instructions during active exams.</p>
    </div>

    <!-- Exam Selector -->
    <div class="glass-panel">
        <form action="${pageContext.request.contextPath}/teacher/live-monitor" method="get" style="display: flex; gap: 12px; align-items: center;">
            <label for="examSelect" style="margin: 0; font-weight: bold;">Select Active Exam:</label>
            <select id="examSelect" name="examId" class="form-select" style="max-width: 400px;" onchange="this.form.submit()">
                <option value="">-- Choose Live Exam --</option>
                <c:forEach var="ex" items="${activeExams}">
                    <option value="${ex.id}" ${selectedExam != null && selectedExam.id == ex.id ? 'selected' : ''}>
                        🔴 ${ex.title} (${ex.startTime})
                    </option>
                </c:forEach>
            </select>
            <button type="submit" class="btn btn--primary">Monitor Live Session</button>
        </form>
    </div>

    <c:if test="${selectedExam != null}">
        <div style="display: grid; grid-template-columns: 2fr 1fr; gap: 24px;">
            <div>
                <!-- Search Bar -->
                <div class="search-bar-container">
                    <input type="text" id="liveSearchInput" class="search-input" placeholder="🔍 Search student ID or proctoring status..." onkeyup="filterTable('liveSearchInput', 'proctorTable')">
                    <button class="search-btn" onclick="filterTable('liveSearchInput', 'proctorTable')">Search</button>
                </div>

                <div class="glass-panel">
                    <h3>Active Takers Status (Exam #${selectedExam.id})</h3>
                    <div class="table-responsive">
                        <table class="glass-table" id="proctorTable">
                            <thead>
                                <tr>
                                    <th>Student User ID</th>
                                    <th>Current Question</th>
                                    <th>Warnings</th>
                                    <th>Status</th>
                                    <th>Last Heartbeat</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="st" items="${statuses}">
                                    <tr>
                                        <td>ERP: ${st.studentErpId != null ? st.studentErpId : 'N/A'}</td>
                                        <td>Q${st.currentQuestion}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${st.warningsCount > 0}">
                                                    <span class="badge badge--blocked">${st.warningsCount} Warnings</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge badge--active">0</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><span class="badge badge--${st.status}">${st.status}</span></td>
                                        <td>${st.lastHeartbeat}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="glass-panel">
                    <h3>Proctoring Security Logs</h3>
                    <div style="max-height: 250px; overflow-y: auto;">
                        <c:forEach var="lg" items="${logs}">
                            <div style="font-size: 0.85rem; padding: 6px 0; border-bottom: 1px solid rgba(255,255,255,0.08);">
                                <span style="color: var(--text-muted);">${lg.eventTime}</span> |
                                <strong>ERP: ${lg.studentErpId != null ? lg.studentErpId : 'N/A'}:</strong> ${lg.eventType}
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <div>
                <div class="glass-panel">
                    <h3>📢 Push Broadcast Message</h3>
                    <form action="${pageContext.request.contextPath}/teacher/live-monitor" method="post">
                        <input type="hidden" name="examId" value="${selectedExam.id}">
                        <div class="form-group">
                            <label for="message">Broadcast Content</label>
                            <textarea id="message" name="message" class="form-control" rows="4" placeholder="Enter urgent announcement or correction for all active exam takers..." required></textarea>
                        </div>
                        <button type="submit" class="btn btn--danger" style="width: 100%;">Broadcast to Students 📢</button>
                    </form>
                </div>

                <div class="glass-panel">
                    <h3>Sent Broadcasts</h3>
                    <c:forEach var="bc" items="${broadcasts}">
                        <div style="background: rgba(245,158,11,0.15); border-left: 3px solid var(--warning); padding: 10px; margin-bottom: 8px; font-size: 0.85rem;">
                            <strong>Sent:</strong> ${bc.message}<br>
                            <span style="font-size: 0.75rem; color: var(--text-muted);">${bc.sentAt}</span>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </c:if>
</div>

<jsp:include page="../common/footer.jsp" />
