package com.erebor.tomkins.pos.viewmodel.sync;

import com.erebor.tomkins.pos.base.BaseViewState;

public class SyncUploadViewState extends BaseViewState<String> {

    public static String STATE_LOADING = "loading";
    public static String STATE_SUCCESS = "success";
    public static String STATE_FAILED = "failed";
    public static String STATE_WAITING = "waiting";

    public enum State {
        LOADING(STATE_LOADING), SUCCESS(STATE_SUCCESS), FAILED(STATE_FAILED), WAITING(STATE_WAITING);
        public String value;

        State(String val) {
            value = val;
        }
    }

    private SyncUploadViewState(String currentState) {
        this.currentState = currentState;
    }

    public static SyncUploadViewState ERROR_STATE = new SyncUploadViewState(State.FAILED.value);
    public static SyncUploadViewState LOADING_STATE = new SyncUploadViewState(State.LOADING.value);
    public static SyncUploadViewState SUCCESS_STATE = new SyncUploadViewState(State.SUCCESS.value);
    public static SyncUploadViewState WAITING_STATE = new SyncUploadViewState(State.WAITING.value);
}
