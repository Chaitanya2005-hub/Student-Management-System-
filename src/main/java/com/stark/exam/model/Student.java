package com.stark.exam.model;

import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int userId;
    private String rollNo;
    private String fullName;
    private int departmentId;
    private int semester;
    private int batchYear;
    private String section;

    public Student() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public int getBatchYear() { return batchYear; }
    public void setBatchYear(int batchYear) { this.batchYear = batchYear; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }
}
