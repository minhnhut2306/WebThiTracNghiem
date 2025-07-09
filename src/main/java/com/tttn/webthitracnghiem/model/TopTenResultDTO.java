package com.tttn.webthitracnghiem.model;

public class TopTenResultDTO {

    private double mark;
    private String userId;
    private String userFullName;
    private String examName;

    public TopTenResultDTO(double mark, String userId, String userFullName, String examName) {
        this.mark = mark;
        this.userId = userId;
        this.userFullName = userFullName;
        this.examName = examName;
    }

    // Getter and Setter
    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }
}
