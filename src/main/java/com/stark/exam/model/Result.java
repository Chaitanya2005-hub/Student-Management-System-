package com.stark.exam.model;

import java.io.Serializable;

public class Result implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int studentId;
    private String studentErpId;
    private int examId;
    private Integer score;
    private Integer totalMarks;
    private int securityWarnings;

    public Result() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getStudentErpId() { return studentErpId; }
    public void setStudentErpId(String studentErpId) { this.studentErpId = studentErpId; }

    public int getExamId() { return examId; }
    public void setExamId(int examId) { this.examId = examId; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public Integer getTotalMarks() { return totalMarks; }
    public void setTotalMarks(Integer totalMarks) { this.totalMarks = totalMarks; }

    public int getSecurityWarnings() { return securityWarnings; }
    public void setSecurityWarnings(int securityWarnings) { this.securityWarnings = securityWarnings; }
}
