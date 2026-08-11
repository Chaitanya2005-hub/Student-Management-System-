package com.stark.exam.model;

import java.io.Serializable;

public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String code;
    private String title;
    private int departmentId;
    private int semester;
    private int credits;

    public Course() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
}
