/* Exam Engine & Proctoring Client Script */

let warningsCount = 0;
let timerInterval = null;
let heartbeatInterval = null;
let currentQuestionIndex = 1;

function initExamEngine(durationMinutes, examId, contextPath) {
    let totalSeconds = durationMinutes * 60;
    const timerDisplay = document.getElementById('timer-display');
    const warningsDisplay = document.getElementById('warnings-count');
    const examForm = document.getElementById('exam-form');
    const warningsInput = document.getElementById('warningsCountInput');

    // Timer countdown
    timerInterval = setInterval(() => {
        totalSeconds--;
        if (totalSeconds <= 0) {
            clearInterval(timerInterval);
            alert('Time is up! Submitting your exam automatically.');
            if (warningsInput) warningsInput.value = warningsCount;
            examForm.submit();
            return;
        }

        const mins = Math.floor(totalSeconds / 60);
        const secs = totalSeconds % 60;
        if (timerDisplay) {
            timerDisplay.textContent = `${mins.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;
            if (totalSeconds < 300) {
                timerDisplay.style.color = 'var(--warning)';
            }
            if (totalSeconds < 60) {
                timerDisplay.style.color = 'var(--danger)';
            }
        }
    }, 1000);

    // Proctoring: Window Focus / Visibility monitoring
    window.addEventListener('blur', () => {
        registerWarning(examId, contextPath, 'Window focus lost / Tab switched');
    });

    document.addEventListener('visibilitychange', () => {
        if (document.hidden) {
            registerWarning(examId, contextPath, 'Tab switched / Minimized');
        }
    });

    // Heartbeat & Broadcast polling
    heartbeatInterval = setInterval(() => {
        sendHeartbeat(examId, contextPath, 'Active', null);
    }, 10000);
}

function registerWarning(examId, contextPath, reason) {
    warningsCount++;
    const warningsDisplay = document.getElementById('warnings-count');
    const warningsInput = document.getElementById('warningsCountInput');
    if (warningsDisplay) warningsDisplay.textContent = warningsCount;
    if (warningsInput) warningsInput.value = warningsCount;

    const alertBanner = document.getElementById('security-warning-banner');
    if (alertBanner) {
        alertBanner.style.display = 'block';
        alertBanner.textContent = `⚠️ Security Warning (${warningsCount}): ${reason}`;
    }

    sendHeartbeat(examId, contextPath, 'Active', `Security Warning (${warningsCount}): ${reason}`);
}

function sendHeartbeat(examId, contextPath, status, logEvent) {
    const params = new URLSearchParams({
        examId: examId,
        currentQuestion: currentQuestionIndex,
        warningsCount: warningsCount,
        status: status
    });
    if (logEvent) params.append('logEvent', logEvent);

    fetch(`${contextPath}/student/proctor-ping`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: params.toString()
    })
    .then(res => res.json())
    .then(data => {
        if (data.status === 'ok' && data.broadcasts && data.broadcasts.length > 0) {
            const broadcastBox = document.getElementById('live-broadcast-box');
            if (broadcastBox) {
                broadcastBox.style.display = 'block';
                broadcastBox.innerHTML = `<strong>📢 Teacher Broadcast:</strong> ${data.broadcasts[0].message}`;
            }
        }
    })
    .catch(err => console.error('Heartbeat error:', err));
}
