package com.stark.exam.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ExamLiveStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    private int studentId;
    private String studentErpId;
    private int examId;
    private int currentQuestion;
    private int warningsCount;
    private String status; // 'Active', 'Idle', 'Disconnected', 'Terminated', 'Submitted'
    private Timestamp lastHeartbeat;

    public ExamLiveStatus() {}

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getStudentErpId() { return studentErpId; }
    public void setStudentErpId(String studentErpId) { this.studentErpId = studentErpId; }

    public int getExamId() { return examId; }
    public void setExamId(int examId) { this.examId = examId; }

    public int getCurrentQuestion() { return currentQuestion; }
    public void setCurrentQuestion(int currentQuestion) { this.currentQuestion = currentQuestion; }

    public int getWarningsCount() { return warningsCount; }
    public void setWarningsCount(int warningsCount) { this.warningsCount = warningsCount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getLastHeartbeat() { return lastHeartbeat; }
    public void setLastHeartbeat(Timestamp lastHeartbeat) { this.lastHeartbeat = lastHeartbeat; }
}
