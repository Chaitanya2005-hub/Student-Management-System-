package com.stark.exam.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class Exam implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private Date examDate;
    private Time startTime;
    private Integer durationMinutes;
    private String status; // 'scheduled', 'active', 'completed'
    private boolean requiresApproval;

    public Exam() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Date getExamDate() { return examDate; }
    public void setExamDate(Date examDate) { this.examDate = examDate; }

    public Time getStartTime() { return startTime; }
    public void setStartTime(Time startTime) { this.startTime = startTime; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean isRequiresApproval() { return requiresApproval; }
    public void setRequiresApproval(boolean requiresApproval) { this.requiresApproval = requiresApproval; }
}
