package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.erebor.tomkins.pos.base.BaseDatabaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;


@Entity(tableName = "EVENTHARGADET",
        primaryKeys = {"KodeEvent", "KodeArt"})
public class EventHargaDetDBModel implements BaseDatabaseModel {

    @NonNull
    @ColumnInfo(name = "KodeEvent")
    @SerializedName("KodeEvent")
    @Expose
    private String kodeEvent;

    @NonNull
    @ColumnInfo(name = "KodeArt")
    @SerializedName("KodeArt")
    @Expose
    private String kodeArt;

    @NonNull
    @ColumnInfo(name = "HargaKhusus", defaultValue = "0")
    @SerializedName("HargaKhusus")
    @Expose
    private double hargaKhusus;

    @Nullable
    @ColumnInfo(name = "Diskon", defaultValue = "0")
    @SerializedName("Diskon")
    @Expose
    private double diskon;

    @NonNull
    @ColumnInfo(name = "last_update")
    @SerializedName("last_update")
    @Expose
    private Date lastUpdate;

    @NonNull
    public String getKodeEvent() {
        return kodeEvent;
    }

    public void setKodeEvent(@NonNull String kodeEvent) {
        this.kodeEvent = kodeEvent;
    }

    @NonNull
    public String getKodeArt() {
        return kodeArt;
    }

    public void setKodeArt(@NonNull String kodeArt) {
        this.kodeArt = kodeArt;
    }

    public double getHargaKhusus() {
        return hargaKhusus;
    }

    public void setHargaKhusus(double hargaKhusus) {
        this.hargaKhusus = hargaKhusus;
    }

    public double getDiskon() {
        return diskon;
    }

    public void setDiskon(double diskon) {
        this.diskon = diskon;
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
