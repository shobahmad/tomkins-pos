package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.erebor.tomkins.pos.base.BaseDatabaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(tableName = "TRXJUALDET",
        primaryKeys = {"NoBon", "IndTrx", "KodeArt", "Ukuran"}
)
public class TrxJualDetDBModel implements BaseDatabaseModel {
    /*
    CREATE TABLE [TRXJUALDET](
  [NoBon] VARCHAR(20) NOT NULL REFERENCES [TRXJUAL]([NoBon]) ON DELETE RESTRICT ON UPDATE RESTRICT,
  [IndTrx] INTEGER NOT NULL,
  [KodeArt] VARCHAR(20) NOT NULL REFERENCES [MSART]([KodeArt]) ON DELETE RESTRICT ON UPDATE RESTRICT,
  [Ukuran] VARCHAR(5) NOT NULL,
  [HargaNormal] MONEY,
  [KodeEvent] VARCHAR(10),
  [QtyJual] INTEGER NOT NULL,
  [Diskon] FLOAT,
  [HargaJual] MONEY,
  [Catatan] VARCHAR(20),
  PRIMARY KEY([NoBon], [IndTrx], [KodeArt], [Ukuran]) ON CONFLICT ROLLBACK)
     */

    @NonNull
    @ColumnInfo(name = "NoBon")
    @SerializedName("NoBon")
    @Expose
    private String noBon;

    @NonNull
    @ColumnInfo(name = "IndTrx")
    @SerializedName("IndTrx")
    @Expose
    private String indTrx;

    @NonNull
    @ColumnInfo(name = "KodeArt")
    @SerializedName("KodeArt")
    @Expose
    private String kodeArt;

    @NonNull
    @ColumnInfo(name = "NoBarcode")
    @SerializedName("NoBarcode")
    @Expose
    private String noBarcode;

    @NonNull
    @ColumnInfo(name = "Grade", defaultValue = "A")
    @SerializedName("Grade")
    @Expose
    private String grade;

    @NonNull
    @ColumnInfo(name = "Ukuran")
    @SerializedName("Ukuran")
    @Expose
    private String ukuran;

    @ColumnInfo(name = "HargaNormal")
    @SerializedName("HargaNormal")
    @Expose
    private double hargaNormal;

    @ColumnInfo(name = "KodeEvent")
    @SerializedName("KodeEvent")
    @Expose
    private String kodeEvent;

    @NonNull
    @ColumnInfo(name = "QtyJual")
    @SerializedName("QtyJual")
    @Expose
    private Integer qtyJual;

    @ColumnInfo(name = "Diskon")
    @SerializedName("Diskon")
    @Expose
    private double diskon;

    @ColumnInfo(name = "HargaJual")
    @SerializedName("HargaJual")
    @Expose
    private double hrgaJual;

    @ColumnInfo(name = "Catatan")
    @SerializedName("Catatan")
    @Expose
    private String catatan;

    @NonNull
    public String getNoBon() {
        return noBon;
    }

    public void setNoBon(@NonNull String noBon) {
        this.noBon = noBon;
    }

    @NonNull
    public String getIndTrx() {
        return indTrx;
    }

    public void setIndTrx(@NonNull String indTrx) {
        this.indTrx = indTrx;
    }

    @NonNull
    public String getKodeArt() {
        return kodeArt;
    }

    public void setKodeArt(@NonNull String kodeArt) {
        this.kodeArt = kodeArt;
    }


    @NonNull
    public String getNoBarcode() {
        return noBarcode;
    }

    public void setNoBarcode(@NonNull String noBarcode) {
        this.noBarcode = noBarcode;
    }

    @NonNull
    public String getGrade() {
        return grade;
    }

    public void setGrade(@NonNull String grade) {
        this.grade = grade;
    }

    @NonNull
    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(@NonNull String ukuran) {
        this.ukuran = ukuran;
    }

    public double getHargaNormal() {
        return hargaNormal;
    }

    public void setHargaNormal(double hargaNormal) {
        this.hargaNormal = hargaNormal;
    }

    public String getKodeEvent() {
        return kodeEvent;
    }

    public void setKodeEvent(String kodeEvent) {
        this.kodeEvent = kodeEvent;
    }

    @NonNull
    public Integer getQtyJual() {
        return qtyJual;
    }

    public void setQtyJual(@NonNull Integer qtyJual) {
        this.qtyJual = qtyJual;
    }

    public double getDiskon() {
        return diskon;
    }

    public void setDiskon(double diskon) {
        this.diskon = diskon;
    }

    public double getHrgaJual() {
        return hrgaJual;
    }

    public void setHrgaJual(double hrgaJual) {
        this.hrgaJual = hrgaJual;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    @Override
    public void setLastUpdate(Date lastUpdate) {

    }

    @Override
    public Date getLastUpdate() {
        return null;
    }
}
