package com.stark.exam.service;

import com.stark.exam.dao.CommunicationDAO;
import com.stark.exam.model.Announcement;
import com.stark.exam.model.CalendarEvent;
import com.stark.exam.model.Notice;

import java.util.List;

public class CommunicationService {

    private final CommunicationDAO communicationDAO = new CommunicationDAO();

    public boolean createAnnouncement(Announcement a) {
        return communicationDAO.createAnnouncement(a);
    }

    public List<Announcement> getActiveAnnouncements(String role) {
        return communicationDAO.getActiveAnnouncements(role);
    }

    public List<Announcement> getAllAnnouncements() {
        return communicationDAO.getAllAnnouncements();
    }

    public boolean createNotice(Notice n) {
        return communicationDAO.createNotice(n);
    }

    public List<Notice> getAllNotices() {
        return communicationDAO.getAllNotices();
    }

    public List<CalendarEvent> getCalendarEvents() {
        return communicationDAO.getCalendarEvents();
    }
}
