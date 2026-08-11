package com.stark.exam.service;

import com.stark.exam.dao.AcademicDAO;
import com.stark.exam.model.*;

import java.util.List;

public class AcademicService {

    private final AcademicDAO academicDAO = new AcademicDAO();

    public List<Course> getAllCourses() {
        return academicDAO.getAllCourses();
    }

    public List<Subject> getAllSubjects() {
        return academicDAO.getAllSubjects();
    }

    public boolean createSubject(Subject s) {
        return academicDAO.createSubject(s);
    }

    public boolean markAttendance(Attendance a) {
        return academicDAO.markAttendance(a);
    }

    public List<Attendance> getAttendanceForStudent(int studentId) {
        return academicDAO.getAttendanceForStudent(studentId);
    }

    public boolean createAssignment(Assignment assign) {
        return academicDAO.createAssignment(assign);
    }

    public List<Assignment> getAllAssignments() {
        return academicDAO.getAllAssignments();
    }

    public boolean submitAssignment(Submission sub) {
        return academicDAO.submitAssignment(sub);
    }

    public List<Submission> getSubmissionsForAssignment(int assignmentId) {
        return academicDAO.getSubmissionsForAssignment(assignmentId);
    }

    public List<Grade> getGradesForStudent(int studentId) {
        return academicDAO.getGradesForStudent(studentId);
    }

    public int getStudentIdByUserId(int userId) {
        return academicDAO.getStudentIdByUserId(userId);
    }
}
