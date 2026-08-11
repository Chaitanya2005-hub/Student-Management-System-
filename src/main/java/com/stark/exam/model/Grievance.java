package com.stark.exam.model;

import java.io.Serializable;
import java.sql.Date;

public class Grievance implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int studentId;
    private String studentErpId;
    private String category;
    private String description;
    private String status; // 'Pending', 'Resolved'
    private Date submissionDate;

    public Grievance() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getStudentErpId() { return studentErpId; }
    public void setStudentErpId(String studentErpId) { this.studentErpId = studentErpId; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(Date submissionDate) { this.submissionDate = submissionDate; }
}
