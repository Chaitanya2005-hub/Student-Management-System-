<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Post Notice & Announcement" />
</jsp:include>
<jsp:include page="../common/sidebar.jsp" />

<div class="main-content page-enter">
    <div class="glass-panel glass-panel--strong">
        <h2>📢 Notices & Announcements Desk</h2>
        <p>Broadcast campus notices and target-audience announcements.</p>
    </div>

    <div style="display: grid; grid-template-columns: 1fr 1.5fr; gap: 24px;">
        <div class="glass-panel">
            <h3>Publish Notice / Announcement</h3>
            <form action="${pageContext.request.contextPath}/author/post-notice" method="post">
                <div class="form-group">
                    <label for="type">Publish Type</label>
                    <select id="type" name="type" class="form-select" required>
                        <option value="notice">General Notice</option>
                        <option value="announcement">Targeted Announcement</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="targetAudience">Target Audience (for Announcements)</label>
                    <select id="targetAudience" name="targetAudience" class="form-select">
                        <option value="all">all</option>
                        <option value="students">students</option>
                        <option value="teacher">teacher</option>
                        <option value="admin">admin</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="title">Title</label>
                    <input type="text" id="title" name="title" class="form-control" required placeholder="Notice subject header...">
                </div>
                <div class="form-group">
                    <label for="content">Message Content</label>
                    <textarea id="content" name="content" class="form-control" rows="4" required placeholder="Detailed message body..."></textarea>
                </div>
                <button type="submit" class="btn btn--primary" style="width: 100%;">Publish 📢</button>
            </form>
        </div>

        <div>
            <!-- Search Bar -->
            <div class="search-bar-container">
                <input type="text" id="noticeSearchInput" class="search-input" placeholder="🔍 Search posted notices..." onkeyup="filterTable('noticeSearchInput', 'noticeTable')">
                <button class="search-btn" onclick="filterTable('noticeSearchInput', 'noticeTable')">Search</button>
            </div>

            <div class="glass-panel">
                <h3>Campus Notice History</h3>
                <div class="table-responsive">
                    <table class="glass-table" id="noticeTable">
                        <thead>
                            <tr>
                                <th>Title</th>
                                <th>Message</th>
                                <th>Date Posted</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="n" items="${notices}">
                                <tr>
                                    <td><strong>${n.title}</strong></td>
                                    <td>${n.message}</td>
                                    <td>${n.date}</td>
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
