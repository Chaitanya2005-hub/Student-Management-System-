package com.stark.exam.model;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String username;
    private String password;
    private String fullName;
    private String role; // 'student', 'teacher', 'author'
    private String erpId;
    private Integer year;
    private String department;
    private String section;
    private String photoPath;

    public User() {}

    public User(int id, String username, String password, String fullName, String role, String erpId, Integer year, String department, String section, String photoPath) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.erpId = erpId;
        this.year = year;
        this.department = department;
        this.section = section;
        this.photoPath = photoPath;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getErpId() { return erpId; }
    public void setErpId(String erpId) { this.erpId = erpId; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }
}
