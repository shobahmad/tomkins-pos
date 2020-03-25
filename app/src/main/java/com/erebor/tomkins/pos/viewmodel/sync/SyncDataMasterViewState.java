package com.erebor.tomkins.pos.viewmodel.sync;

import com.erebor.tomkins.pos.base.BaseViewState;

public class SyncDataMasterViewState extends BaseViewState<String> {

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

    private SyncDataMasterViewState(String currentState) {
        this.currentState = currentState;
    }

    public static SyncDataMasterViewState ERROR_STATE = new SyncDataMasterViewState(State.FAILED.value);
    public static SyncDataMasterViewState LOADING_STATE = new SyncDataMasterViewState(State.LOADING.value);
    public static SyncDataMasterViewState SUCCESS_STATE = new SyncDataMasterViewState(State.SUCCESS.value);
    public static SyncDataMasterViewState WAITING_STATE = new SyncDataMasterViewState(State.WAITING.value);
}
