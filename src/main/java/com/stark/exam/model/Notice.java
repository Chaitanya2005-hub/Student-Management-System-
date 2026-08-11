package com.stark.exam.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Notice implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private String message;
    private Integer postedBy;
    private Timestamp date;

    public Notice() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Integer getPostedBy() { return postedBy; }
    public void setPostedBy(Integer postedBy) { this.postedBy = postedBy; }

    public Timestamp getDate() { return date; }
    public void setDate(Timestamp date) { this.date = date; }
}
