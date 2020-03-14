package com.erebor.tomkins.pos.viewmodel.sync;

import com.erebor.tomkins.pos.base.BaseViewState;

public class DownloadViewState extends BaseViewState<String> {

    public static int STATE_LOADING = -1;
    public static int STATE_SUCCESS = 1;
    public static int STATE_FAILED = 2;
    public static int STATE_WAITING = 3;

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
        public int value;

        State(int val) {
            value = val;
        }
    }

    private DownloadViewState(int currentState) {
        this.currentState = currentState;
    }

    public static DownloadViewState ERROR_STATE = new DownloadViewState(State.FAILED.value);
    public static DownloadViewState LOADING_STATE = new DownloadViewState(State.LOADING.value);
    public static DownloadViewState SUCCESS_STATE = new DownloadViewState(State.SUCCESS.value);
    public static DownloadViewState WAITING_STATE = new DownloadViewState(State.WAITING.value);
}
