package com.stark.exam.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class LiveCode implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String code;
    private Timestamp updatedAt;

    public LiveCode() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}
