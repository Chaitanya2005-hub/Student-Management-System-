package com.stark.exam.service;

import com.stark.exam.dao.ExamProctorDAO;
import com.stark.exam.model.ExamBroadcast;
import com.stark.exam.model.ExamLiveStatus;
import com.stark.exam.model.ExamLog;

import java.util.List;

public class ProctoringService {

    private final ExamProctorDAO proctorDAO = new ExamProctorDAO();

    public boolean recordHeartbeat(int studentId, int examId, int currentQuestion, int warningsCount, String status) {
        return proctorDAO.updateLiveStatus(studentId, examId, currentQuestion, warningsCount, status);
    }

    public ExamLiveStatus getLiveStatus(int studentId, int examId) {
        return proctorDAO.getLiveStatus(studentId, examId);
    }

    public List<ExamLiveStatus> getLiveStatusesForExam(int examId) {
        return proctorDAO.getLiveStatusesForExam(examId);
    }

    public boolean logEvent(int studentId, int examId, String eventType) {
        return proctorDAO.logEvent(studentId, examId, eventType);
    }

    public List<ExamLog> getLogsForExam(int examId) {
        return proctorDAO.getLogsForExam(examId);
    }

    public boolean sendBroadcast(int examId, String message) {
        return proctorDAO.sendBroadcast(examId, message);
    }

    public List<ExamBroadcast> getBroadcastsForExam(int examId) {
        return proctorDAO.getBroadcastsForExam(examId);
    }
}
