package com.stark.exam.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Submission implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int assignmentId;
    private int studentId;
    private String studentErpId;
    private String submissionText;
    private Timestamp submissionDate;

    public Submission() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAssignmentId() { return assignmentId; }
    public void setAssignmentId(int assignmentId) { this.assignmentId = assignmentId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getStudentErpId() { return studentErpId; }
    public void setStudentErpId(String studentErpId) { this.studentErpId = studentErpId; }

    public String getSubmissionText() { return submissionText; }
    public void setSubmissionText(String submissionText) { this.submissionText = submissionText; }

    public Timestamp getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(Timestamp submissionDate) { this.submissionDate = submissionDate; }
}
