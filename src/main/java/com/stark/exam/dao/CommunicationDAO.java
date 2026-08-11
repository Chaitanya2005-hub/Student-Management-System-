package com.stark.exam.dao;

import com.stark.exam.model.Announcement;
import com.stark.exam.model.CalendarEvent;
import com.stark.exam.model.Notice;
import com.stark.exam.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommunicationDAO {

    public boolean createAnnouncement(Announcement a) {
        String sql = "INSERT INTO announcements (title, content, target_audience, author_id, is_active) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, a.getTitle());
            ps.setString(2, a.getContent());
            ps.setString(3, a.getTargetAudience() != null ? a.getTargetAudience() : "all");
            ps.setInt(4, a.getAuthorId());
            ps.setBoolean(5, a.isActive());
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

    public List<Announcement> getActiveAnnouncements(String role) {
        List<Announcement> list = new ArrayList<>();
        String sql = "SELECT * FROM announcements WHERE is_active = 1 AND (target_audience = 'all' OR target_audience = ? OR target_audience = ?) ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, role);
            ps.setString(2, role + "s"); // Handle both "student" and "students"
            System.out.println("DEBUG: Fetching announcements for role: " + role + " or " + role + "s");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Announcement a = new Announcement();
                    a.setId(rs.getInt("id"));
                    a.setTitle(rs.getString("title"));
                    a.setContent(rs.getString("content"));
                    a.setTargetAudience(rs.getString("target_audience"));
                    a.setAuthorId(rs.getInt("author_id"));
                    a.setActive(rs.getBoolean("is_active"));
                    a.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(a);
                }
            }
            System.out.println("DEBUG: Found " + list.size() + " announcements");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Announcement> getAllAnnouncements() {
        List<Announcement> list = new ArrayList<>();
        String sql = "SELECT * FROM announcements ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Announcement a = new Announcement();
                a.setId(rs.getInt("id"));
                a.setTitle(rs.getString("title"));
                a.setContent(rs.getString("content"));
                a.setTargetAudience(rs.getString("target_audience"));
                a.setAuthorId(rs.getInt("author_id"));
                a.setActive(rs.getBoolean("is_active"));
                a.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(a);
            }
            System.out.println("DEBUG: Total announcements in database: " + list.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean createNotice(Notice n) {
        String sql = "INSERT INTO notices (title, message, posted_by, date) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, n.getTitle());
            ps.setString(2, n.getMessage());
            if (n.getPostedBy() != null) ps.setInt(3, n.getPostedBy()); else ps.setNull(3, Types.INTEGER);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) n.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Notice> getAllNotices() {
        List<Notice> list = new ArrayList<>();
        String sql = "SELECT * FROM notices ORDER BY date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Notice n = new Notice();
                n.setId(rs.getInt("id"));
                n.setTitle(rs.getString("title"));
                n.setMessage(rs.getString("message"));
                int pb = rs.getInt("posted_by");
                n.setPostedBy(rs.wasNull() ? null : pb);
                n.setDate(rs.getTimestamp("date"));
                list.add(n);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<CalendarEvent> getCalendarEvents() {
        List<CalendarEvent> list = new ArrayList<>();
        String sql = "SELECT * FROM calendar_events ORDER BY event_date ASC, start_time ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CalendarEvent ce = new CalendarEvent();
                ce.setId(rs.getInt("id"));
                ce.setUserId(rs.getInt("user_id"));
                ce.setRole(rs.getString("role"));
                ce.setTitle(rs.getString("title"));
                ce.setEventDate(rs.getDate("event_date"));
                ce.setStartTime(rs.getTime("start_time"));
                ce.setLocation(rs.getString("location"));
                ce.setEventType(rs.getString("event_type"));
                ce.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(ce);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
