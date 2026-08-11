package com.stark.exam.controller.teacher;

import com.stark.exam.model.Attendance;
import com.stark.exam.model.User;
import com.stark.exam.service.AcademicService;
import com.stark.exam.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/teacher/qr-attendance")
public class QRAttendanceServlet extends HttpServlet {

    private final AuthService authService = new AuthService();
    private final AcademicService academicService = new AcademicService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("generate-qr".equals(action)) {
            generateQRCode(request, response);
        } else if ("scan-qr".equals(action)) {
            scanQRCode(request, response);
        } else {
            // Show QR attendance page
            List<User> allUsers = authService.getAllUsers();
            Map<String, List<User>> usersBySection = organizeUsersBySection(allUsers);
            
            request.setAttribute("usersBySection", usersBySection);
            request.setAttribute("todayDate", LocalDate.now().toString());
            request.getRequestDispatcher("/WEB-INF/views/teacher/qr_attendance.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("mark-attendance".equals(action)) {
            markQRAttendance(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/teacher/qr-attendance?error=invalid_action");
        }
    }

    private void generateQRCode(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        String date = request.getParameter("date");
        
        // Generate QR code data
        String qrData = generateQRData(studentId, date);
        
        // Generate simple visual QR code representation
        String visualQR = generateVisualQR(qrData);
        
        // Return JSON response
        response.setContentType("application/json");
        response.getWriter().write("{\"success\":true, \"qrCode\":\"" + visualQR + "\", \"qrData\":\"" + qrData + "\"}");
    }

    private void scanQRCode(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String qrData = request.getParameter("qrData");
        String studentIp = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        
        // Validate QR code
        Map<String, Object> validationResult = validateQRCode(qrData, studentIp, userAgent);
        
        response.setContentType("application/json");
        response.getWriter().write("{\"success\":" + validationResult.get("valid") + 
            ", \"studentId\":" + validationResult.get("studentId") + 
            ", \"studentName\":\"" + validationResult.get("studentName") + 
            ", \"section\":\"" + validationResult.get("section") + 
            ", \"antiProxyScore\":\"" + validationResult.get("antiProxyScore") + "\"}");
    }

    private void markQRAttendance(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        User teacher = (User) request.getSession().getAttribute("user");
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        String dateStr = request.getParameter("date");
        String status = request.getParameter("status");
        String qrCodeHash = request.getParameter("qrCodeHash");
        String studentIp = request.getParameter("studentIp");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        String deviceFingerprint = request.getParameter("deviceFingerprint");

        // Get student ERP ID
        User student = authService.getUserById(studentId);
        String studentErpId = student != null ? student.getErpId() : null;

        Attendance a = new Attendance();
        a.setStudentId(studentId);
        a.setStudentErpId(studentErpId);
        a.setDate(Date.valueOf(dateStr));
        a.setStatus(status);
        a.setMarkedBy(teacher.getId());
        a.setMarkedTime(new Timestamp(System.currentTimeMillis()));
        a.setQrCodeHash(qrCodeHash);
        a.setLocationIp(studentIp);
        a.setLatitude(latitude != null ? Double.parseDouble(latitude) : null);
        a.setLongitude(longitude != null ? Double.parseDouble(longitude) : null);
        a.setDeviceFingerprint(deviceFingerprint);
        a.setAntiProxyScore(calculateAntiProxyScore(studentIp, deviceFingerprint));

        boolean success = academicService.markAttendance(a);
        
        response.setContentType("application/json");
        response.getWriter().write("{\"success\":" + success + ", \"message\":\"Attendance marked successfully\"}");
    }

    private String generateQRData(int studentId, String date) {
        // QR code format: STUDENT_ID|DATE|TIMESTAMP|SECRET
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String secret = "STARK_ATTENDANCE_2026"; // In production, use proper encryption
        return studentId + "|" + date + "|" + timestamp + "|" + secret;
    }

    private String generateVisualQR(String qrData) {
        // Generate a simple visual QR code pattern (this is a simplified version)
        // In production, you would use a proper QR code library
        StringBuilder qr = new StringBuilder();
        qr.append("╔════════════════════════════╗\n");
        qr.append("║   ATTENDANCE QR CODE       ║\n");
        qr.append("╠════════════════════════════╣\n");
        qr.append("║ ID: ").append(qrData.substring(0, Math.min(10, qrData.length()))).append("               ║\n");
        qr.append("║ ").append("● ● ● ● ● ● ● ● ● ● ● ●").append(" ║\n");
        qr.append("║ ● ● ● ● ● ● ● ● ● ● ● ║\n");
        qr.append("║ ● ● ● ● ● ● ● ● ● ● ● ║\n");
        qr.append("║ ● ● ● ● ● ● ● ● ● ● ● ║\n");
        qr.append("║ ● ● ● ● ● ● ● ● ● ● ● ║\n");
        qr.append("║ ● ● ● ● ● ● ● ● ● ● ● ║\n");
        qr.append("║ ● ● ● ● ● ● ● ● ● ● ● ║\n");
        qr.append("║ ● ● ● ● ● ● ● ● ● ● ● ║\n");
        qr.append("╚════════════════════════════╝\n");
        
        // Encode as base64 for display
        return Base64.getEncoder().encodeToString(qr.toString().getBytes());
    }

    private Map<String, Object> validateQRCode(String qrData, String studentIp, String userAgent) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            String[] parts = qrData.split("\\|");
            if (parts.length != 4) {
                result.put("valid", false);
                return result;
            }

            int studentId = Integer.parseInt(parts[0]);
            String date = parts[1];
            String timestamp = parts[2];
            String secret = parts[3];

            // Validate secret
            if (!"STARK_ATTENDANCE_2026".equals(secret)) {
                result.put("valid", false);
                return result;
            }

            // Validate date (QR code should be from today)
            String today = LocalDate.now().toString();
            if (!today.equals(date)) {
                result.put("valid", false);
                return result;
            }

            // Get student info - use User since we need section info
            User user = authService.getUserById(studentId);
            if (user == null || !"student".equalsIgnoreCase(user.getRole())) {
                result.put("valid", false);
                return result;
            }

            // Calculate anti-proxy score
            String antiProxyScore = calculateAntiProxyScore(studentIp, userAgent);

            result.put("valid", true);
            result.put("studentId", studentId);
            result.put("studentName", user.getFullName());
            result.put("section", user.getSection() != null ? user.getSection() : "Unassigned");
            result.put("antiProxyScore", antiProxyScore);

        } catch (Exception e) {
            result.put("valid", false);
        }

        return result;
    }

    private String calculateAntiProxyScore(String ip, String deviceFingerprint) {
        // Simple anti-proxy scoring algorithm
        int score = 100;
        
        // Deduct points for suspicious patterns
        if (ip != null && (ip.startsWith("10.") || ip.startsWith("192.168."))) {
            score -= 10; // VPN-like IP
        }
        
        if (deviceFingerprint != null && deviceFingerprint.length() < 20) {
            score -= 20; // Suspicious device fingerprint
        }
        
        // Ensure score is between 0-100
        score = Math.max(0, Math.min(100, score));
        
        if (score >= 80) return "HIGH";
        if (score >= 50) return "MEDIUM";
        return "LOW";
    }

    private Map<String, List<User>> organizeUsersBySection(List<User> users) {
        Map<String, List<User>> usersBySection = new HashMap<>();
        
        for (User user : users) {
            if ("student".equalsIgnoreCase(user.getRole())) {
                String section = user.getSection() != null ? user.getSection() : "Unassigned";
                usersBySection.computeIfAbsent(section, k -> new java.util.ArrayList<>()).add(user);
            }
        }
        
        return usersBySection;
    }
}