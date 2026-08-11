package com.stark.exam.service;

import com.stark.exam.dao.StudentServiceDAO;
import com.stark.exam.model.AdmitCard;
import com.stark.exam.model.Fee;
import com.stark.exam.model.Grievance;

import java.util.List;

public class StudentService {

    private final StudentServiceDAO studentServiceDAO = new StudentServiceDAO();

    public boolean submitGrievance(Grievance g) {
        return studentServiceDAO.submitGrievance(g);
    }

    public List<Grievance> getGrievancesForStudent(int studentId) {
        return studentServiceDAO.getGrievancesForStudent(studentId);
    }

    public List<Grievance> getAllGrievances() {
        return studentServiceDAO.getAllGrievances();
    }

    public boolean updateGrievanceStatus(int id, String status) {
        return studentServiceDAO.updateGrievanceStatus(id, status);
    }

    public List<Fee> getFeesForStudent(int studentId) {
        return studentServiceDAO.getFeesForStudent(studentId);
    }

    public List<Fee> getAllFees() {
        return studentServiceDAO.getAllFees();
    }

    public boolean createFee(Fee f) {
        return studentServiceDAO.createFee(f);
    }

    public boolean updateFeeStatus(int feeId, String status) {
        return studentServiceDAO.updateFeeStatus(feeId, status);
    }

    public AdmitCard getAdmitCardForStudent(int studentId) {
        return studentServiceDAO.getAdmitCardForStudent(studentId);
    }

    public List<AdmitCard> getAllAdmitCards() {
        return studentServiceDAO.getAllAdmitCards();
    }

    public boolean updateAdmitCardStatus(int studentId, String status) {
        return studentServiceDAO.updateAdmitCardStatus(studentId, status);
    }

    public int getStudentIdByUserId(int userId) {
        return studentServiceDAO.getStudentIdByUserId(userId);
    }
}
