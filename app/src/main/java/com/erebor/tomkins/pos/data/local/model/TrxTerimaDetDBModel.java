package com.erebor.tomkins.pos.data.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.erebor.tomkins.pos.base.BaseDatabaseModel;

import java.util.Date;

@Entity(tableName = "TRXTERIMADET",
primaryKeys = {"NoDO", "KodeArt", "Ukuran"})
public class TrxTerimaDetDBModel implements BaseDatabaseModel {
    /*
    CREATE TABLE [TRXTERIMADET](
  [NoDO] VARCHAR(20) NOT NULL,
  [KodeArt] VARCHAR(20) NOT NULL,
  [Ukuran] VARCHAR(5),
  [QtyDO] INTEGER,
  [QtyTerima] INTEGER,
  PRIMARY KEY([NoDO], [KodeArt], [Ukuran]) ON CONFLICT ROLLBACK)
     */

    public TrxTerimaDetDBModel() {
    }

    public TrxTerimaDetDBModel(@NonNull String noDO, @NonNull String kodeArt, @NonNull String ukuran, Integer qtyDO, Integer qtyTerima) {
        this.noDO = noDO;
        this.kodeArt = kodeArt;
        this.ukuran = ukuran;
        this.qtyDO = qtyDO;
        this.qtyTerima = qtyTerima;
    }

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

    @Override
    public void setLastUpdate(Date lastUpdate) {

    }

    @Override
    public Date getLastUpdate() {
        return null;
    }
}
