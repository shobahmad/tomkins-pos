package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.sql.Date;

import static androidx.room.ForeignKey.RESTRICT;

@Entity(tableName = "MUTASIART",
primaryKeys = {"Tanggal", "KodeArt", "Ukuran"})
public class MutasiArtDBModel {
    /*
    CREATE TABLE [MUTASIART](
  [Tanggal] DATE NOT NULL,
  [KodeArt] VARCHAR(20) NOT NULL REFERENCES [MSART]([KodeArt]) ON DELETE RESTRICT ON UPDATE RESTRICT,
  [Ukuran] VARCHAR(5) NOT NULL,
  [QtyOpn] INTEGER DEFAULT 0,
  [QtyTerima] INTEGER DEFAULT 0,
  [QtyReturJual] INTEGER DEFAULT 0,
  [QtyReturGBJ] INTEGER DEFAULT 0,
  [QtyJual] INTEGER DEFAULT 0,
  PRIMARY KEY([Tanggal], [KodeArt], [Ukuran]) ON CONFLICT ROLLBACK)
     */

    @NonNull
    @ColumnInfo(name = "Tanggal")
    private Date Tanggal;

    @NonNull
    @ColumnInfo(name = "KodeArt")
    @ForeignKey(
            entity = MsArtDBModel.class,
            parentColumns = "KodeArt",
            childColumns = "KodeArt",
            onDelete = RESTRICT,
            onUpdate = RESTRICT
    )
    private String KodeArt;

    @NonNull
    @ColumnInfo(name = "Ukuran")
    private String Ukuran;

    @ColumnInfo(name = "QtyOpn", defaultValue = "0")
    private Integer qtyOpn;

    @ColumnInfo(name = "QtyTerima", defaultValue = "0")
    private Integer qtyTerima;

    @ColumnInfo(name = "QtyReturJual", defaultValue = "0")
    private Integer qtyReturJual;

    @ColumnInfo(name = "QtyReturGBJ", defaultValue = "0")
    private Integer qtyReturGBJ;

    @ColumnInfo(name = "QtyJual", defaultValue = "0")
    private Integer qtyJual;

    @NonNull
    public Date getTanggal() {
        return Tanggal;
    }

    public void setTanggal(@NonNull Date tanggal) {
        Tanggal = tanggal;
    }

    @NonNull
    public String getKodeArt() {
        return KodeArt;
    }

    public void setKodeArt(@NonNull String kodeArt) {
        KodeArt = kodeArt;
    }

    @NonNull
    public String getUkuran() {
        return Ukuran;
    }

    public void setUkuran(@NonNull String ukuran) {
        Ukuran = ukuran;
    }

    public Integer getQtyOpn() {
        return qtyOpn;
    }

    public void setQtyOpn(Integer qtyOpn) {
        this.qtyOpn = qtyOpn;
    }

    public Integer getQtyTerima() {
        return qtyTerima;
    }

    public void setQtyTerima(Integer qtyTerima) {
        this.qtyTerima = qtyTerima;
    }

    public Integer getQtyReturJual() {
        return qtyReturJual;
    }

    public void setQtyReturJual(Integer qtyReturJual) {
        this.qtyReturJual = qtyReturJual;
    }

    public Integer getQtyReturGBJ() {
        return qtyReturGBJ;
    }

    public void setQtyReturGBJ(Integer qtyReturGBJ) {
        this.qtyReturGBJ = qtyReturGBJ;
    }

    public Integer getQtyJual() {
        return qtyJual;
    }

    public void setQtyJual(Integer qtyJual) {
        this.qtyJual = qtyJual;
    }
}
