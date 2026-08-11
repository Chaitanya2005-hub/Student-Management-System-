<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Live Exam: ${exam.title}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/glassmorphism.css">
    <script src="${pageContext.request.contextPath}/static/js/exam.js"></script>
</head>
<body onload="initExamEngine(${exam.durationMinutes}, ${exam.id}, '${pageContext.request.contextPath}')">
    <div style="max-width: 900px; margin: 30px auto; padding: 0 20px;">
        
        <!-- Header Bar with Timer & Security Warning Indicator -->
        <div class="glass-panel glass-panel--strong" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
            <div>
                <h2 style="margin: 0;">${exam.title}</h2>
                <p style="margin: 0; font-size: 0.85rem;">Live Proctoring Active</p>
            </div>
            <div style="display: flex; gap: 20px; align-items: center;">
                <div style="background: rgba(0,0,0,0.3); padding: 8px 16px; border-radius: 12px; border: 1px solid var(--glass-border); text-align: center;">
                    <span style="font-size: 0.75rem; color: var(--text-muted); display: block;">TIME REMAINING</span>
                    <span id="timer-display" style="font-size: 1.4rem; font-weight: bold; color: var(--success); font-family: monospace;">--:--</span>
                </div>
                <div class="exam-warning-indicator" style="background: rgba(248, 113, 113, 0.2); border: 1px solid var(--danger); padding: 8px 16px; border-radius: 12px; text-align: center;">
                    <span style="font-size: 0.75rem; color: #f87171; display: block;">SECURITY WARNINGS</span>
                    <span id="warnings-count" style="font-size: 1.4rem; font-weight: bold; color: #f87171;">0</span>
                </div>
            </div>
        </div>

        <!-- Live Broadcast Message Box (Hidden by default) -->
        <div id="live-broadcast-box" style="display: none; background: rgba(245, 158, 11, 0.2); border: 1px solid var(--warning); padding: 14px; border-radius: 12px; margin-bottom: 20px; color: #f59e0b;"></div>

        <!-- Security Warning Banner (Hidden by default) -->
        <div id="security-warning-banner" style="display: none; background: rgba(248, 113, 113, 0.25); border: 1px solid var(--danger); padding: 14px; border-radius: 12px; margin-bottom: 20px; color: #f87171; font-weight: bold;"></div>

        <!-- Question Paper Form -->
        <form id="exam-form" action="${pageContext.request.contextPath}/student/take-exam" method="post">
            <input type="hidden" name="examId" value="${exam.id}">
            <input type="hidden" id="warningsCountInput" name="warningsCount" value="0">

            <c:forEach var="q" items="${questions}" varStatus="status">
                <div class="glass-panel" style="margin-bottom: 20px;">
                    <h3 style="font-size: 1.1rem; margin-bottom: 16px;">
                        Q${status.index + 1}. ${q.questionText}
                    </h3>
                    <div style="display: flex; flex-direction: column; gap: 12px;">
                        <label style="background: rgba(255,255,255,0.05); padding: 12px; border-radius: 10px; border: 1px solid var(--glass-border); cursor: pointer; display: flex; align-items: center; gap: 10px;">
                            <input type="radio" name="q_${q.id}" value="a">
                            <span>A. ${q.optionA}</span>
                        </label>
                        <label style="background: rgba(255,255,255,0.05); padding: 12px; border-radius: 10px; border: 1px solid var(--glass-border); cursor: pointer; display: flex; align-items: center; gap: 10px;">
                            <input type="radio" name="q_${q.id}" value="b">
                            <span>B. ${q.optionB}</span>
                        </label>
                        <label style="background: rgba(255,255,255,0.05); padding: 12px; border-radius: 10px; border: 1px solid var(--glass-border); cursor: pointer; display: flex; align-items: center; gap: 10px;">
                            <input type="radio" name="q_${q.id}" value="c">
                            <span>C. ${q.optionC}</span>
                        </label>
                        <label style="background: rgba(255,255,255,0.05); padding: 12px; border-radius: 10px; border: 1px solid var(--glass-border); cursor: pointer; display: flex; align-items: center; gap: 10px;">
                            <input type="radio" name="q_${q.id}" value="d">
                            <span>D. ${q.optionD}</span>
                        </label>
                    </div>
                </div>
            </c:forEach>

            <div style="text-align: right; margin-top: 24px;">
                <button type="submit" class="btn btn--success" style="padding: 14px 32px; font-size: 1.1rem;">
                    Submit Exam ✅
                </button>
            </div>
        </form>
    </div>
</body>
</html>
