package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.erebor.tomkins.pos.base.BaseDatabaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(tableName = "STOKREAL",
primaryKeys = {"KodeArt", "Ukuran"})
public class StokRealDBModel implements BaseDatabaseModel {
    /*
    CREATE TABLE [STOKREAL](
  [KodeArt] VARCHAR(20) NOT NULL REFERENCES [MSART]([KodeArt]) ON DELETE RESTRICT ON UPDATE RESTRICT,
  [Ukuran] VARCHAR(5) NOT NULL,
  [QtyStok] INTEGER NOT NULL DEFAULT 0,
  PRIMARY KEY([KodeArt], [Ukuran]) ON CONFLICT ROLLBACK)
     */

    @NonNull
    @ColumnInfo(name = "KodeArt")
    @SerializedName("KodeArt")
    @Expose
    private String kodeArt;

    @NonNull
    @ColumnInfo(name = "Ukuran")
    @SerializedName("Ukuran")
    @Expose
    private String ukuran;

    @NonNull
    @ColumnInfo(name = "QtyStok", defaultValue = "0")
    @SerializedName("QtyStok")
    @Expose
    private Integer qtyStok;

    @NonNull
    @ColumnInfo(name = "last_update")
    @SerializedName("last_update")
    @Expose
    private Date lastUpdate;

    @NonNull
    @ColumnInfo(name = "isUploaded", defaultValue = "1")
    private Boolean isUploaded;

    @NonNull
    public String getKodeArt() {
        return kodeArt;
    }

    public void setKodeArt(@NonNull String kodeArt) {
        this.kodeArt = kodeArt;
    }

    @NonNull
    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(@NonNull String ukuran) {
        this.ukuran = ukuran;
    }

    @NonNull
    public Integer getQtyStok() {
        return qtyStok;
    }

    public void setQtyStok(@NonNull Integer qtyStok) {
        this.qtyStok = qtyStok;
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

    @NonNull
    public Boolean getUploaded() {
        return isUploaded;
    }

    public void setUploaded(@NonNull Boolean uploaded) {
        isUploaded = uploaded;
    }
}
