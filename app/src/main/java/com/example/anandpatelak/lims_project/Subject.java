package com.example.anandpatelak.lims_project;

public class Subject {
    private String subjectId;
    private String subjectName;
    private String subjectProfessor;

    public Subject(String subjectId, String subjectName, String subjectProfessor) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectProfessor = subjectProfessor;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectProfessor() {
        return subjectProfessor;
    }

    public void setSubjectProfessor(String subjectProfessor) {
        this.subjectProfessor = subjectProfessor;
    }
}
