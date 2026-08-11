package com.stark.exam.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class CalendarEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int userId;
    private String role;
    private String title;
    private Date eventDate;
    private Time startTime;
    private String location;
    private String eventType;
    private Timestamp createdAt;

    public CalendarEvent() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Date getEventDate() { return eventDate; }
    public void setEventDate(Date eventDate) { this.eventDate = eventDate; }

    public Time getStartTime() { return startTime; }
    public void setStartTime(Time startTime) { this.startTime = startTime; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
