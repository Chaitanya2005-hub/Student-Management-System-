package com.stark.exam.model;

import java.io.Serializable;
import java.sql.Date;

public class Assignment implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private String description;
    private Date dueDate;
    private int createdBy;

    public Assignment() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }
}
