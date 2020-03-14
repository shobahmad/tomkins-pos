package com.erebor.tomkins.pos.data.remote;

import androidx.annotation.NonNull;
import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DownloadResponse<T> {
    public static final String RESULT_SERIALIZED_NAME = "data";

    @NonNull
    @SerializedName("last_update")
    @Expose
    @Ignore
    private Date lastUpdate;

    @NonNull
    @SerializedName(RESULT_SERIALIZED_NAME)
    @Expose
    @Ignore
    private T data;

    public DownloadResponse(@NonNull Date lastUpdate, @NonNull T data) {
        this.lastUpdate = lastUpdate;
        this.data = data;
    }

    @NonNull
    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(@NonNull Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @NonNull
    public T getData() {
        return data;
    }

    public void setData(@NonNull T data) {
        this.data = data;
    }
}
