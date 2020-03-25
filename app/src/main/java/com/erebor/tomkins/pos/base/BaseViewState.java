package com.erebor.tomkins.pos.base;

import androidx.annotation.NonNull;

public class BaseViewState<T> {
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    protected T data;
    protected Throwable error;
    protected String currentState;

    @NonNull
    @Override
    public String toString() {
        return currentState;
    }
}
