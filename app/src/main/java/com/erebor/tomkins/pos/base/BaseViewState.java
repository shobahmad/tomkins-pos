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

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    protected T data;
    protected Throwable error;
    protected int currentState;

    @NonNull
    @Override
    public String toString() {
        return currentState + "";
    }
}
