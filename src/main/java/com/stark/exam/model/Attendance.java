package com.stark.exam.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class Attendance implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int studentId;
    private String studentErpId;
    private Date date;
    private String status; // 'Present', 'Absent', 'Late'
    private Integer markedBy;
    private Timestamp markedTime;
    private String section;
    private String qrCodeHash;
    private String locationIp;
    private Double latitude;
    private Double longitude;
    private String deviceFingerprint;
    private String antiProxyScore;

    public Attendance() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getStudentErpId() { return studentErpId; }
    public void setStudentErpId(String studentErpId) { this.studentErpId = studentErpId; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getMarkedBy() { return markedBy; }
    public void setMarkedBy(Integer markedBy) { this.markedBy = markedBy; }

    public Timestamp getMarkedTime() { return markedTime; }
    public void setMarkedTime(Timestamp markedTime) { this.markedTime = markedTime; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public String getQrCodeHash() { return qrCodeHash; }
    public void setQrCodeHash(String qrCodeHash) { this.qrCodeHash = qrCodeHash; }

    public String getLocationIp() { return locationIp; }
    public void setLocationIp(String locationIp) { this.locationIp = locationIp; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getDeviceFingerprint() { return deviceFingerprint; }
    public void setDeviceFingerprint(String deviceFingerprint) { this.deviceFingerprint = deviceFingerprint; }

    public String getAntiProxyScore() { return antiProxyScore; }
    public void setAntiProxyScore(String antiProxyScore) { this.antiProxyScore = antiProxyScore; }
}
