package com.stark.exam.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Grade implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int studentId;
    private int courseId;
    private String examType; // 'midterm', 'final', 'assignment', 'quiz'
    private BigDecimal marksObtained;
    private BigDecimal maxMarks;
    private String grade;
    private int semester;

    public Grade() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public String getExamType() { return examType; }
    public void setExamType(String examType) { this.examType = examType; }

    public BigDecimal getMarksObtained() { return marksObtained; }
    public void setMarksObtained(BigDecimal marksObtained) { this.marksObtained = marksObtained; }

    public BigDecimal getMaxMarks() { return maxMarks; }
    public void setMaxMarks(BigDecimal maxMarks) { this.maxMarks = maxMarks; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }
}
