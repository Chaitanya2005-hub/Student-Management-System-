<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="QR Attendance" />
</jsp:include>
<jsp:include page="../common/sidebar.jsp" />

<div class="main-content page-enter">
    <div class="glass-panel glass-panel--strong">
        <h2>📱 QR-Based Attendance System</h2>
        <p>Section-wise attendance with anti-proxy validation using QR codes</p>
    </div>

    <div class="glass-panel">
        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 24px;">
            <!-- QR Scanner Section -->
            <div>
                <h3>📷 Scan QR Code</h3>
                <div id="qr-scanner" style="background: rgba(0,0,0,0.2); border: 2px dashed var(--accent); border-radius: 12px; padding: 40px; text-align: center; min-height: 300px; display: flex; flex-direction: column; align-items: center; justify-content: center;">
                    <div style="font-size: 3rem; margin-bottom: 16px;">📱</div>
                    <p style="color: var(--text-muted);">Click here to scan QR code</p>
                    <input type="file" id="qr-input" accept="image/*" style="display: none;">
                    <button id="scan-btn" class="btn btn--primary" style="margin-top: 16px;">📷 Upload QR Code</button>
                </div>
                
                <div id="scan-result" style="margin-top: 16px; display: none;">
                    <div class="glass-panel" style="padding: 16px;">
                        <h4>Scan Result:</h4>
                        <p><strong>Student:</strong> <span id="student-name">-</span></p>
                        <p><strong>Section:</strong> <span id="student-section">-</span></p>
                        <p><strong>Anti-Proxy Score:</strong> <span id="anti-proxy-score">-</span></p>
                        <div style="margin-top: 12px;">
                            <button id="mark-present-btn" class="btn btn--success">✅ Mark Present</button>
                            <button id="mark-absent-btn" class="btn btn--danger">❌ Mark Absent</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Section-wise Attendance -->
            <div>
                <h3>📋 Attendance by Section</h3>
                <div style="margin-bottom: 16px;">
                    <label style="font-weight: var(--fw-medium);">Date:</label>
                    <input type="date" id="attendance-date" class="form-control" value="${todayDate}">
                </div>
                
                <div id="sections-container">
                    <c:forEach var="entry" items="${usersBySection}">
                        <div class="glass-panel" style="margin-bottom: 16px;">
                            <h4>📚 Section: ${entry.key}</h4>
                            <div class="table-responsive">
                                <table class="glass-table">
                                    <thead>
                                        <tr>
                                            <th>Student</th>
                                            <th>ERP ID</th>
                                            <th>Attendance</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="student" items="${entry.value}">
                                            <tr>
                                                <td>${student.fullName}</td>
                                                <td>${student.erpId}</td>
                                                <td>
                                                    <span class="badge badge--scheduled" id="status-${student.id}">Not Marked</span>
                                                </td>
                                                <td>
                                                    <button class="btn btn--primary" onclick="generateQR(${student.id})">📱 Generate QR</button>
                                                    <button class="btn btn--success" onclick="markAttendance(${student.id}, 'Present')">✅ Present</button>
                                                    <button class="btn btn--danger" onclick="markAttendance(${student.id}, 'Absent')">❌ Absent</button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>

    <!-- QR Code Modal -->
    <div id="qr-modal" style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.7); z-index: 1000; align-items: center; justify-content: center;">
        <div class="glass-panel" style="max-width: 400px; width: 90%; padding: 32px; text-align: center;">
            <h3>📱 Student QR Code</h3>
            <div id="qr-code-display" style="margin: 24px 0;">
                <div style="width: 200px; height: 200px; background: white; margin: 0 auto; display: flex; align-items: center; justify-content: center;">
                    <span style="color: #333;">Loading...</span>
                </div>
            </div>
            <p style="color: var(--text-muted); font-size: 0.85rem;">Scan this QR code to mark attendance</p>
            <button onclick="closeQRModal()" class="btn btn--primary" style="margin-top: 16px;">Close</button>
        </div>
    </div>
</div>

<script>
    let currentStudentId = null;

    // QR Code generation
    function generateQR(studentId) {
        currentStudentId = studentId;
        const date = document.getElementById('attendance-date').value;
        
        fetch(`${pageContext.request.contextPath}/teacher/qr-attendance?action=generate-qr&studentId=${studentId}&date=${date}`)
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    document.getElementById('qr-code-display').innerHTML = `<img src="${data.qrCode}" alt="QR Code" style="width: 200px; height: 200px;">`;
                    document.getElementById('qr-modal').style.display = 'flex';
                } else {
                    alert('Failed to generate QR code');
                }
            });
    }

    function closeQRModal() {
        document.getElementById('qr-modal').style.display = 'none';
    }

    // Manual attendance marking
    function markAttendance(studentId, status) {
        const date = document.getElementById('attendance-date').value;
        
        fetch(`${pageContext.request.contextPath}/teacher/mark-attendance?studentId=${studentId}&date=${date}&status=${status}`, {
            method: 'POST'
        })
        .then(response => response.text())
        .then(() => {
            const statusBadge = document.getElementById(`status-${studentId}`);
            if (status === 'Present') {
                statusBadge.className = 'badge badge--active';
                statusBadge.textContent = 'Present';
            } else {
                statusBadge.className = 'badge badge--blocked';
                statusBadge.textContent = 'Absent';
            }
        });
    }

    // QR Code scanning (simulated)
    document.getElementById('scan-btn').addEventListener('click', function() {
        document.getElementById('qr-input').click();
    });

    document.getElementById('qr-input').addEventListener('change', function(e) {
        const file = e.target.files[0];
        if (file) {
            // In a real implementation, you would use a QR code scanning library
            // For now, we'll simulate the scan
            simulateQRScan();
        }
    });

    function simulateQRScan() {
        // Simulate QR scan result
        const studentIp = '192.168.1.100'; // Would be actual IP
        const userAgent = navigator.userAgent;
        
        // Generate sample QR data
        const sampleStudentId = 1; // Would be actual student ID from QR
        const sampleDate = document.getElementById('attendance-date').value;
        const timestamp = new Date().getTime();
        const secret = 'STARK_ATTENDANCE_2026';
        const qrData = `${sampleStudentId}|${sampleDate}|${timestamp}|${secret}`;
        
        fetch(`${pageContext.request.contextPath}/teacher/qr-attendance?action=scan-qr&qrData=${encodeURIComponent(qrData)}&studentIp=${studentIp}`)
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    document.getElementById('student-name').textContent = data.studentName;
                    document.getElementById('student-section').textContent = data.section;
                    document.getElementById('anti-proxy-score').textContent = data.antiProxyScore;
                    document.getElementById('scan-result').style.display = 'block';
                    currentStudentId = data.studentId;
                } else {
                    alert('Invalid QR code');
                }
            });
    }

    // Mark attendance from scan result
    document.getElementById('mark-present-btn').addEventListener('click', function() {
        if (currentStudentId) {
            markAttendance(currentStudentId, 'Present');
            document.getElementById('scan-result').style.display = 'none';
        }
    });

    document.getElementById('mark-absent-btn').addEventListener('click', function() {
        if (currentStudentId) {
            markAttendance(currentStudentId, 'Absent');
            document.getElementById('scan-result').style.display = 'none';
        }
    });

    // Collect device fingerprint for anti-proxy
    function collectDeviceFingerprint() {
        const fingerprint = {
            userAgent: navigator.userAgent,
            language: navigator.language,
            platform: navigator.platform,
            screen: `${screen.width}x${screen.height}`,
            timezone: Intl.DateTimeFormat().resolvedOptions().timeZone
        };
        return btoa(JSON.stringify(fingerprint));
    }

    // Get geolocation (requires user permission)
    function getGeolocation() {
        return new Promise((resolve, reject) => {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(
                    (position) => {
                        resolve({
                            latitude: position.coords.latitude,
                            longitude: position.coords.longitude
                        });
                    },
                    (error) => {
                        resolve(null); // Don't block if location denied
                    }
                );
            } else {
                resolve(null);
            }
        });
    }
</script>

<jsp:include page="../common/footer.jsp" />