package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.erebor.tomkins.pos.base.BaseDatabaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

@Entity(tableName = "TRXJUAL")
public class TrxJualDBModel implements BaseDatabaseModel {
    /*
    CREATE TABLE [TRXJUAL](
  [NoBon] VARCHAR(20) PRIMARY KEY ON CONFLICT ROLLBACK NOT NULL,
  [Tanggal] DATE,
  [KodeSPG] VARCHAR(20))
     */

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "NoBon")
    @SerializedName("NoBon")
    @Expose
    private String noBon;

    @NonNull
    @ColumnInfo(name = "Tanggal")
    @SerializedName("Tanggal")
    @Expose
    private Date tanggal;

    @NonNull
    @ColumnInfo(name = "KodeSPG")
    @SerializedName("kodeSPG")
    @Expose
    private String kodeSPG;


    @Nullable
    @ColumnInfo(name = "TanggalUpload")
    private Date tanggalUpload;

    @NonNull
    @ColumnInfo(name = "isUploaded", defaultValue = "0")
    private Boolean isUploaded;

    @Ignore
    @Expose
    @SerializedName("listDetail")
    private List<TrxJualDetDBModel> listDetail;

    @NonNull
    public String getNoBon() {
        return noBon;
    }

    public void setNoBon(@NonNull String noBon) {
        this.noBon = noBon;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getKodeSPG() {
        return kodeSPG;
    }

    public void setKodeSPG(String kodeSPG) {
        this.kodeSPG = kodeSPG;
    }


    public List<TrxJualDetDBModel> getListDetail() {
        return listDetail;
    }

    public void setListDetail(List<TrxJualDetDBModel> listDetail) {
        this.listDetail = listDetail;
    }

    @NonNull
    public Date getTanggalUpload() {
        return tanggalUpload;
    }

    public void setTanggalUpload(@NonNull Date tanggalUpload) {
        this.tanggalUpload = tanggalUpload;
    }

    @NonNull
    public Boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(@NonNull Boolean uploaded) {
        isUploaded = uploaded;
    }

    @Override
    public void setLastUpdate(Date lastUpdate) {
    }

    @Override
    public Date getLastUpdate() {
        return null;
    }
}
