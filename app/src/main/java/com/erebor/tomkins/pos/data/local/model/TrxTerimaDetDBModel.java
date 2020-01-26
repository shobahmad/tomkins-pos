package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "TRXTERIMADET",
primaryKeys = {"NoDO", "KodeArt", "Ukuran"})
public class TrxTerimaDetDBModel {
    /*
    CREATE TABLE [TRXTERIMADET](
  [NoDO] VARCHAR(20) NOT NULL,
  [KodeArt] VARCHAR(20) NOT NULL,
  [Ukuran] VARCHAR(5),
  [QtyDO] INTEGER,
  [QtyTerima] INTEGER,
  PRIMARY KEY([NoDO], [KodeArt], [Ukuran]) ON CONFLICT ROLLBACK)
     */

    @NonNull
    @ColumnInfo(name = "NoDO")
    private String noDO;

    @NonNull
    @ColumnInfo(name = "KodeArt")
    private String kodeArt;

    @NonNull
    @ColumnInfo(name = "Ukuran")
    private String ukuran;

    @ColumnInfo(name = "QtyDO")
    private Integer qtyDO;

    @ColumnInfo(name = "QtyTerima")
    private Integer qtyTerima;

    @NonNull
    public String getNoDO() {
        return noDO;
    }

    public void setNoDO(@NonNull String noDO) {
        this.noDO = noDO;
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

    public Integer getQtyDO() {
        return qtyDO;
    }

    public void setQtyDO(Integer qtyDO) {
        this.qtyDO = qtyDO;
    }

    public Integer getQtyTerima() {
        return qtyTerima;
    }

    public void setQtyTerima(Integer qtyTerima) {
        this.qtyTerima = qtyTerima;
    }
}
