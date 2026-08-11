package com.stark.exam.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Announcement implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private String content;
    private String targetAudience; // 'all', 'students', 'teacher', 'admin'
    private int authorId;
    private boolean isActive;
    private Timestamp createdAt;

    public Announcement() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getTargetAudience() { return targetAudience; }
    public void setTargetAudience(String targetAudience) { this.targetAudience = targetAudience; }

    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
