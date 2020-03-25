package com.erebor.tomkins.pos.viewmodel.splash;

import com.erebor.tomkins.pos.base.BaseViewState;

public class SplashViewState extends BaseViewState<String> {

    private int progress = 0;
    private String message = "";

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

    public enum State {
        LOADING("loading"), SESSION_VALID("valid"), SESSION_EMPTY("empty"), ERROR("error");
        public String value;

        State(String val) {
            value = val;
        }
    }

    private SplashViewState(String currentState) {
        this.currentState = currentState;
    }

    public SplashViewState(int progress, String message) {
        this.progress = progress;
        this.message = message;
    }

    public static SplashViewState LOADING_STATE = new SplashViewState(State.LOADING.value);
    public static SplashViewState SESSION_VALID_STATE = new SplashViewState(State.SESSION_VALID.value);
    public static SplashViewState SESSION_EMPTY_STATE = new SplashViewState(State.SESSION_EMPTY.value);
    public static SplashViewState ERROR_STATE = new SplashViewState(State.ERROR.value);
}
