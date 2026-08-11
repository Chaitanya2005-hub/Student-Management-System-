package com.stark.exam.model;

import java.io.Serializable;

public class ExamRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int studentId;
    private int examId;
    private String status; // 'Requested', 'Approved', 'Rejected'

    public ExamRequest() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getExamId() { return examId; }
    public void setExamId(int examId) { this.examId = examId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
