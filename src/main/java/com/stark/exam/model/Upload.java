package com.stark.exam.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Upload implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int userId;
    private String role;
    private String category;
    private String originalName;
    private String storedName;
    private String mimeType;
    private Long sizeBytes;
    private String path;
    private String relatedType;
    private Integer relatedId;
    private String accessLevel;
    private Timestamp createdAt;

    public Upload() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getOriginalName() { return originalName; }
    public void setOriginalName(String originalName) { this.originalName = originalName; }

    public String getStoredName() { return storedName; }
    public void setStoredName(String storedName) { this.storedName = storedName; }

    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }

    public Long getSizeBytes() { return sizeBytes; }
    public void setSizeBytes(Long sizeBytes) { this.sizeBytes = sizeBytes; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public String getRelatedType() { return relatedType; }
    public void setRelatedType(String relatedType) { this.relatedType = relatedType; }

    public Integer getRelatedId() { return relatedId; }
    public void setRelatedId(Integer relatedId) { this.relatedId = relatedId; }

    public String getAccessLevel() { return accessLevel; }
    public void setAccessLevel(String accessLevel) { this.accessLevel = accessLevel; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
