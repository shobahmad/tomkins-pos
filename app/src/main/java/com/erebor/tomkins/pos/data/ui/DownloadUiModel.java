package com.erebor.tomkins.pos.data.ui;

public class DownloadUiModel {
    private boolean downloading;
    private int progress;
    private String title;
    private String messsage;
    private String lastDownloadTime;

    public DownloadUiModel() {
    }

    public DownloadUiModel(String title) {
        this.title = title;
    }

    public boolean isDownloading() {
        return downloading;
    }

    public void setDownloading(boolean downloading) {
        this.downloading = downloading;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }

    public String getLastDownloadTime() {
        return lastDownloadTime;
    }

    public void setLastDownloadTime(String lastDownloadTime) {
        this.lastDownloadTime = lastDownloadTime;
    }
}
