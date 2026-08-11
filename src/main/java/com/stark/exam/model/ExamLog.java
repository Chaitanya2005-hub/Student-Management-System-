package com.stark.exam.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ExamLog implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int studentId;
    private String studentErpId;
    private int examId;
    private String eventType;
    private Timestamp eventTime;

    public ExamLog() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getStudentErpId() { return studentErpId; }
    public void setStudentErpId(String studentErpId) { this.studentErpId = studentErpId; }

    public int getExamId() { return examId; }
    public void setExamId(int examId) { this.examId = examId; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public Timestamp getEventTime() { return eventTime; }
    public void setEventTime(Timestamp eventTime) { this.eventTime = eventTime; }
}
