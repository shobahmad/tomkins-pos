package com.erebor.tomkins.pos.data.remote;

import androidx.annotation.Nullable;
import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DownloadResponse<T> {
    public static final String RESULT_SERIALIZED_NAME = "data";

    @Nullable
    @SerializedName("last_update")
    @Expose
    @Ignore
    private Date lastUpdate;

    @Nullable
    @SerializedName(RESULT_SERIALIZED_NAME)
    @Expose
    @Ignore
    private T data;

    public DownloadResponse(@Nullable Date lastUpdate, @Nullable T data) {
        this.lastUpdate = lastUpdate;
        this.data = data;
    }

    @Nullable
    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(@Nullable Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Nullable
    public T getData() {
        return data;
    }

    public void setData(@Nullable T data) {
        this.data = data;
    }
}
