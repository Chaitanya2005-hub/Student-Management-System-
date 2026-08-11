package com.stark.exam.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ExamBroadcast implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int examId;
    private String message;
    private Timestamp sentAt;

    public ExamBroadcast() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getExamId() { return examId; }
    public void setExamId(int examId) { this.examId = examId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Timestamp getSentAt() { return sentAt; }
    public void setSentAt(Timestamp sentAt) { this.sentAt = sentAt; }
}
