package com.erebor.tomkins.pos.viewmodel.sync;

import com.erebor.tomkins.pos.base.BaseViewState;

public class SyncDownloadViewState extends BaseViewState<String> {

    public static String STATE_LOADING = "loading";
    public static String STATE_SUCCESS = "success";
    public static String STATE_FAILED = "failed";
    public static String STATE_WAITING = "waiting";

    private int progress = 0;
    private String message = "";
    private long lastDownloadTime = 0;

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getLastDownloadTime() {
        return lastDownloadTime;
    }

    public void setLastDownloadTime(long lastDownloadTime) {
        this.lastDownloadTime = lastDownloadTime;
    }

    public enum State {
        LOADING(STATE_LOADING), SUCCESS(STATE_SUCCESS), FAILED(STATE_FAILED), WAITING(STATE_WAITING);
        public String value;

        State(String val) {
            value = val;
        }
    }

    private SyncDownloadViewState(String currentState) {
        this.currentState = currentState;
    }

    public static SyncDownloadViewState ERROR_STATE = new SyncDownloadViewState(State.FAILED.value);
    public static SyncDownloadViewState LOADING_STATE = new SyncDownloadViewState(State.LOADING.value);
    public static SyncDownloadViewState SUCCESS_STATE = new SyncDownloadViewState(State.SUCCESS.value);
    public static SyncDownloadViewState WAITING_STATE = new SyncDownloadViewState(State.WAITING.value);
}
