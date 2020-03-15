package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

import com.erebor.tomkins.pos.base.BaseDatabaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(tableName = "MSBARCODE",
        primaryKeys = {"NoBarcode", "KodeArt"},
        indices = @Index(value = "NoBarcode", unique = true)
)
public class MsBarcodeDBModel implements BaseDatabaseModel {

    @NonNull
    @SerializedName("NoBarcode")
    @Expose
    @ColumnInfo(name = "NoBarcode")
    private String noBarcode;

    @NonNull
    @SerializedName("KodeArt")
    @Expose
    @ColumnInfo(name = "KodeArt")
    private String kodeArt;

    @NonNull
    @SerializedName("Ukuran")
    @Expose
    @ColumnInfo(name = "Ukuran")
    private String ukuran;

    @NonNull
    @ColumnInfo(name = "last_update")
    @SerializedName("last_update")
    @Expose
    private Date lastUpdate;

    @NonNull
    public String getNoBarcode() {
        return noBarcode;
    }

    public void setNoBarcode(@NonNull String noBarcode) {
        this.noBarcode = noBarcode;
    }

    @NonNull
    public String getKodeArt() {
        return kodeArt;
    }

    public void setKodeArt(@NonNull String kodeArt) {
        this.kodeArt = kodeArt;
    }

    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }

    @NonNull
    @Override
    public Date getLastUpdate() {
        return lastUpdate;
    }

    @Override
    public void setLastUpdate(@NonNull Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
