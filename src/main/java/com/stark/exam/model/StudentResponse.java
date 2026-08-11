package com.stark.exam.model;

import java.io.Serializable;

public class StudentResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int studentId;
    private int examId;
    private int questionId;
    private String selectedOption; // 'a', 'b', 'c', 'd'
    private Boolean isCorrect;

    public StudentResponse() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getExamId() { return examId; }
    public void setExamId(int examId) { this.examId = examId; }

    public int getQuestionId() { return questionId; }
    public void setQuestionId(int questionId) { this.questionId = questionId; }

    public String getSelectedOption() { return selectedOption; }
    public void setSelectedOption(String selectedOption) { this.selectedOption = selectedOption; }

    public Boolean getIsCorrect() { return isCorrect; }
    public void setIsCorrect(Boolean isCorrect) { this.isCorrect = isCorrect; }
}
