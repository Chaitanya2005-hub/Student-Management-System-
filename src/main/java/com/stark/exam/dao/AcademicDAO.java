package com.stark.exam.dao;

import com.stark.exam.model.*;
import com.stark.exam.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AcademicDAO {

    public List<Course> getAllCourses() {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM courses ORDER BY code ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Course c = new Course();
                c.setId(rs.getInt("id"));
                c.setCode(rs.getString("code"));
                c.setTitle(rs.getString("title"));
                c.setDepartmentId(rs.getInt("department_id"));
                c.setSemester(rs.getInt("semester"));
                c.setCredits(rs.getInt("credits"));
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Subject> getAllSubjects() {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT * FROM subjects ORDER BY code ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Subject s = new Subject();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setCode(rs.getString("code"));
                s.setDepartment(rs.getString("department"));
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean createSubject(Subject s) {
        String sql = "INSERT INTO subjects (name, code, department) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getCode());
            ps.setString(3, s.getDepartment());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) s.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean markAttendance(Attendance a) {
        String sql = "INSERT INTO attendance (student_id, student_erp_id, date, status, marked_by, marked_time, section, qr_code_hash, location_ip, latitude, longitude, device_fingerprint, anti_proxy_score) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, a.getStudentId());
            ps.setString(2, a.getStudentErpId());
            ps.setDate(3, a.getDate());
            ps.setString(4, a.getStatus());
            if (a.getMarkedBy() != null) ps.setInt(5, a.getMarkedBy()); else ps.setNull(5, Types.INTEGER);
            if (a.getMarkedTime() != null) ps.setTimestamp(6, a.getMarkedTime()); else ps.setNull(6, Types.TIMESTAMP);
            ps.setString(7, a.getSection());
            ps.setString(8, a.getQrCodeHash());
            ps.setString(9, a.getLocationIp());
            if (a.getLatitude() != null) ps.setDouble(10, a.getLatitude()); else ps.setNull(10, Types.DOUBLE);
            if (a.getLongitude() != null) ps.setDouble(11, a.getLongitude()); else ps.setNull(11, Types.DOUBLE);
            ps.setString(12, a.getDeviceFingerprint());
            ps.setString(13, a.getAntiProxyScore());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) a.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Attendance> getAttendanceForStudent(int studentId) {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT a.*, u.erp_id FROM attendance a LEFT JOIN users u ON a.student_id = u.id WHERE a.student_id = ? ORDER BY a.date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Attendance a = new Attendance();
                    a.setId(rs.getInt("id"));
                    a.setStudentId(rs.getInt("student_id"));
                    a.setStudentErpId(rs.getString("erp_id"));
                    a.setDate(rs.getDate("date"));
                    a.setStatus(rs.getString("status"));
                    int mb = rs.getInt("marked_by");
                    a.setMarkedBy(rs.wasNull() ? null : mb);
                    a.setMarkedTime(rs.getTimestamp("marked_time"));
                    a.setSection(rs.getString("section"));
                    a.setQrCodeHash(rs.getString("qr_code_hash"));
                    a.setLocationIp(rs.getString("location_ip"));
                    double lat = rs.getDouble("latitude");
                    a.setLatitude(rs.wasNull() ? null : lat);
                    double lon = rs.getDouble("longitude");
                    a.setLongitude(rs.wasNull() ? null : lon);
                    a.setDeviceFingerprint(rs.getString("device_fingerprint"));
                    a.setAntiProxyScore(rs.getString("anti_proxy_score"));
                    list.add(a);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean createAssignment(Assignment assign) {
        String sql = "INSERT INTO assignments (title, description, due_date, created_by) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, assign.getTitle());
            ps.setString(2, assign.getDescription());
            ps.setDate(3, assign.getDueDate());
            ps.setInt(4, assign.getCreatedBy());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) assign.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Assignment> getAllAssignments() {
        List<Assignment> list = new ArrayList<>();
        String sql = "SELECT * FROM assignments ORDER BY due_date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Assignment a = new Assignment();
                a.setId(rs.getInt("id"));
                a.setTitle(rs.getString("title"));
                a.setDescription(rs.getString("description"));
                a.setDueDate(rs.getDate("due_date"));
                a.setCreatedBy(rs.getInt("created_by"));
                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean submitAssignment(Submission sub) {
        String sql = "INSERT INTO submissions (assignment_id, student_id, student_erp_id, submission_text, submission_date) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, sub.getAssignmentId());
            ps.setInt(2, sub.getStudentId());
            ps.setString(3, sub.getStudentErpId());
            ps.setString(4, sub.getSubmissionText());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) sub.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Submission> getSubmissionsForAssignment(int assignmentId) {
        List<Submission> list = new ArrayList<>();
        String sql = "SELECT s.*, u.erp_id FROM submissions s LEFT JOIN users u ON s.student_id = u.id WHERE s.assignment_id = ? ORDER BY s.submission_date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, assignmentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Submission s = new Submission();
                    s.setId(rs.getInt("id"));
                    s.setAssignmentId(rs.getInt("assignment_id"));
                    s.setStudentId(rs.getInt("student_id"));
                    s.setStudentErpId(rs.getString("erp_id"));
                    s.setSubmissionText(rs.getString("submission_text"));
                    s.setSubmissionDate(rs.getTimestamp("submission_date"));
                    list.add(s);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Grade> getGradesForStudent(int studentId) {
        List<Grade> list = new ArrayList<>();
        String sql = "SELECT * FROM grades WHERE student_id = ? ORDER BY semester DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Grade g = new Grade();
                    g.setId(rs.getInt("id"));
                    g.setStudentId(rs.getInt("student_id"));
                    g.setCourseId(rs.getInt("course_id"));
                    g.setExamType(rs.getString("exam_type"));
                    g.setMarksObtained(rs.getBigDecimal("marks_obtained"));
                    g.setMaxMarks(rs.getBigDecimal("max_marks"));
                    g.setGrade(rs.getString("grade"));
                    g.setSemester(rs.getInt("semester"));
                    list.add(g);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getStudentIdByUserId(int userId) {
        String sql = "SELECT id FROM students WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Not found
    }
}
