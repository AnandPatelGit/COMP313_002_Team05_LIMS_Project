package com.example.anandpatelak.lims_project;

/**
 * Created by Hena on 2018-03-24.
 */

public class Folder {
    private String ID;
    private String folderName;
    private String instructorId;
    private String subjectName;
    public Folder(String ID, String folderName, String instructorId, String subjectName) {
        this.ID = ID;
        this.folderName = folderName;
        this.instructorId = instructorId;
        this.subjectName = subjectName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
