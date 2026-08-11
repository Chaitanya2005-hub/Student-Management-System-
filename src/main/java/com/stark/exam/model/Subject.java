package com.stark.exam.model;

import java.io.Serializable;

public class Subject implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String code;
    private String department;

    public Subject() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}
