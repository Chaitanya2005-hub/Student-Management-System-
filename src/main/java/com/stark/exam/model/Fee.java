package com.stark.exam.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class Fee implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int studentId;
    private String studentErpId;
    private String feeType;
    private BigDecimal amount;
    private String status; // 'paid', 'unpaid', 'partial'
    private Date dueDate;
    private Timestamp createdAt;

    public Fee() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getStudentErpId() { return studentErpId; }
    public void setStudentErpId(String studentErpId) { this.studentErpId = studentErpId; }

    public String getFeeType() { return feeType; }
    public void setFeeType(String feeType) { this.feeType = feeType; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
