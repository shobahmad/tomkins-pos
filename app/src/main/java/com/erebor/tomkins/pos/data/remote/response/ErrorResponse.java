package com.erebor.tomkins.pos.data.remote.response;

import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.net.ssl.HttpsURLConnection;

public class ErrorResponse {
    @SerializedName("error_code")
    @Expose
    @Ignore
    private Integer errorCode;

    @SerializedName("error_message")
    @Expose
    @Ignore
    private String errorMessage;

    public ErrorResponse(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isInvalidSignature() {
        return errorCode.equals(HttpsURLConnection.HTTP_FORBIDDEN);
    }
}
