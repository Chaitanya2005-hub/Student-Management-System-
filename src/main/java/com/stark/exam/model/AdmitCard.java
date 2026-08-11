package com.stark.exam.model;

import java.io.Serializable;

public class AdmitCard implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int studentId;
    private String status; // 'Blocked', 'Released'

    public AdmitCard() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
