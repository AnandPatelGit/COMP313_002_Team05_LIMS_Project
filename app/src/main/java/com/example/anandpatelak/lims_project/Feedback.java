package com.example.anandpatelak.lims_project;

public class Feedback {
    String feedbackId, professorEmail, studentEmail, grade, score, comment, fileId, fileRef;


    public Feedback(String feedbackId, String professorEmail, String studentEmail, String grade, String score, String comment, String fileId, String fileRef) {
        this.feedbackId = feedbackId;
        this.professorEmail = professorEmail;
        this.studentEmail = studentEmail;
        this.grade = grade;
        this.score = score;
        this.comment = comment;
        this.fileId = fileId;
        this.fileRef = fileRef;

    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getProfessorEmail() {
        return professorEmail;
    }

    public void setProfessorEmail(String professorEmail) {
        this.professorEmail = professorEmail;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileRef() {
        return fileRef;
    }

    public void setFileRef(String fileRef) {
        this.fileRef = fileRef;
    }
}
