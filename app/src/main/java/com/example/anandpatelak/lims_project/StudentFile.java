package com.example.anandpatelak.lims_project;

/**
 * Created by Hena on 2018-03-26.
 */

public class StudentFile {
    String fileID, folderName, email, fileReference, fileName;

    public StudentFile(String fileID, String folderName, String email, String fileReference, String fileName) {
        this.fileID = fileID;
        this.folderName = folderName;
        this.email = email;
        this.fileReference = fileReference;
        this.fileName = fileName;
    }

    public String getFileID() {
        return fileID;
    }

    public void setID(String fileID) {
        this.fileID = fileID;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFileReference() {
        return fileReference;
    }

    public void setFileReference(String fileReference) {
        this.fileReference = fileReference;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
