package com.erebor.tomkins.pos.data.remote.response;

import androidx.annotation.NonNull;
import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestResponse<T> {
    public static final String RESULT_SERIALIZED_NAME = "result";

    @NonNull
    @SerializedName("status")
    @Expose
    @Ignore
    private ResponseStatusConstant status;

    @NonNull
    @SerializedName("message")
    @Expose
    @Ignore
    private String message;

    @NonNull
    @SerializedName(RESULT_SERIALIZED_NAME)
    @Expose
    @Ignore
    private T result;

    public RestResponse(@NonNull ResponseStatusConstant status, @NonNull T result) {
        this.status = status;
        this.result = result;
    }

    public void setStatus(@NonNull ResponseStatusConstant status) {
        this.status = status;
    }

    public void setResult(@NonNull T result) {
        this.result = result;
    }

    @NonNull
    public ResponseStatusConstant getStatus() {
        return status;
    }

    @NonNull
    public T getResult() {
        return result;
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    public void setMessage(@NonNull String message) {
        this.message = message;
    }

    public boolean statusIsError() {
        return status.equals(ResponseStatusConstant.ERROR);
    }

    public Exception asException() {
        if (statusIsError())
            return new Exception(((Throwable) this.result).getMessage());

        return null;
    }

    public static <T> RestResponse success(@NonNull T data) {
        return new RestResponse<>(ResponseStatusConstant.SUCCESS, data);
    }

    public static <T> RestResponse error(@NonNull Throwable error) {
        return new RestResponse<>(ResponseStatusConstant.ERROR, error);
    }
}
