package com.erebor.tomkins.pos.data.remote.response;

public interface ResponseCallback<R, E> {
    void onSuccess(R success);
    void onError(E error);
    void onLoading();
}
