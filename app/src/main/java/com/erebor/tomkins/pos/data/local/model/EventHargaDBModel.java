package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.erebor.tomkins.pos.base.BaseDatabaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(tableName = "EVENTHARGA")
public class EventHargaDBModel implements BaseDatabaseModel {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "KodeEvent")
    @SerializedName("KodeEvent")
    @Expose
    private String kodeEvent;

    @NonNull
    @ColumnInfo(name = "TglDari")
    @SerializedName("TglDari")
    @Expose
    private Date TglDari;

    @NonNull
    @ColumnInfo(name = "TglSampai")
    @SerializedName("TglSampai")
    @Expose
    private Date TglSampai;


    @NonNull
    @ColumnInfo(name = "last_update")
    @SerializedName("last_update")
    @Expose
    private Date lastUpdate;

    public String getKodeEvent() {
        return kodeEvent;
    }

    public void setKodeEvent(@NonNull String kodeEvent) {
        this.kodeEvent = kodeEvent;
    }

    @NonNull
    public Date getTglDari() {
        return TglDari;
    }

    public void setTglDari(@NonNull Date tglDari) {
        TglDari = tglDari;
    }

    @NonNull
    public Date getTglSampai() {
        return TglSampai;
    }

    public void setTglSampai(@NonNull Date tglSampai) {
        TglSampai = tglSampai;
    }

    @Override
    @NonNull
    public Date getLastUpdate() {
        return lastUpdate;
    }

    @Override
    public void setLastUpdate(@NonNull Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
