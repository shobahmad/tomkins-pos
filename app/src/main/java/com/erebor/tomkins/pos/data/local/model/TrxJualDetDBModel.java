package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.RESTRICT;

@Entity(tableName = "TRXJUALDET",
        primaryKeys = {"NoBon", "IndTrx", "KodeArt", "Ukuran"}
)
public class TrxJualDetDBModel {
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
    @ForeignKey(
            entity = TrxJualDBModel.class,
            parentColumns = "NoBon",
            childColumns = "NoBon",
            onDelete = RESTRICT,
            onUpdate = RESTRICT
    )
    @ColumnInfo(name = "NoBon")
    private String noBon;

    @NonNull
    @ColumnInfo(name = "IndTrx")
    private Integer indTrx;

    @NonNull
    @ForeignKey(
            entity = MsArtDBModel.class,
            parentColumns = "KodeArt",
            childColumns = "KodeArt",
            onDelete = RESTRICT,
            onUpdate = RESTRICT
    )
    @ColumnInfo(name = "KodeArt")
    private String kodeArt;

    @NonNull
    @ColumnInfo(name = "Ukuran")
    private String ukuran;

    @ColumnInfo(name = "HargaNormal")
    private double hargaNormal;

    @ColumnInfo(name = "KodeEvent")
    private String kodeEvent;

    @NonNull
    @ColumnInfo(name = "QtyJual")
    private Integer qtyJual;

    @ColumnInfo(name = "Diskon")
    private double diskon;

    @ColumnInfo(name = "HargaJual")
    private double hrgaJual;

    @ColumnInfo(name = "Catatan")
    private String catatan;

    @NonNull
    public String getNoBon() {
        return noBon;
    }

    public void setNoBon(@NonNull String noBon) {
        this.noBon = noBon;
    }

    @NonNull
    public Integer getIndTrx() {
        return indTrx;
    }

    public void setIndTrx(@NonNull Integer indTrx) {
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
}
