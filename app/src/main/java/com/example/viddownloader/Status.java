package com.example.viddownloader;
import android.net.Uri;

public class Status {
    String fileName,filePath;
    Uri uri;

    public Status() {
    }

    public Status(String fileName, String filePath, Uri uri) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.uri = uri;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
