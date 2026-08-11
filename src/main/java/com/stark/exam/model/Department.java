package com.stark.exam.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Department implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String code;
    private Timestamp createdAt;

    public Department() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
